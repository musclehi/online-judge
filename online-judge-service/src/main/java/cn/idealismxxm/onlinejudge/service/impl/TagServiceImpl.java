package cn.idealismxxm.onlinejudge.service.impl;

import cn.idealismxxm.onlinejudge.dao.TagDao;
import cn.idealismxxm.onlinejudge.domain.entity.Tag;
import cn.idealismxxm.onlinejudge.domain.enums.ErrorCodeEnum;
import cn.idealismxxm.onlinejudge.domain.exception.BusinessException;
import cn.idealismxxm.onlinejudge.domain.util.Pagination;
import cn.idealismxxm.onlinejudge.domain.util.QueryParam;
import cn.idealismxxm.onlinejudge.service.TagService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 标签接口实现
 *
 * @author idealism
 * @date 2018/6/3
 */
@Service("tagService")
public class TagServiceImpl implements TagService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TagServiceImpl.class);

    @Resource
    private TagDao tagDao;

    @Override
    public Tag getTagById(Integer tagId) {
        // 参数校验
        if (tagId == null || tagId < 0) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.ILLEGAL_ARGUMENT);
        }

        // 返回根标签
        if (tagId == 0) {
            return Tag.getRootTag();
        }

        // 读库
        Tag tag;
        try {
            tag = tagDao.selectTagById(tagId);
        } catch (Exception e) {
            LOGGER.error("#getTagById error, tagId: {}", tagId, e);
            throw BusinessException.buildBusinessException(ErrorCodeEnum.DAO_CALL_ERROR, e);
        }

        // 验证数据是否存在
        if (tag == null) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.TAG_NOT_EXIST);
        }
        return tag;
    }

    @Override
    public Integer addTag(Tag tag, String updator) {
        if (tag == null || StringUtils.isBlank(tag.getName()) || StringUtils.isBlank(updator)) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.ILLEGAL_ARGUMENT);
        }

        // 1. 验证父级标签是否存在
        this.getTagById(tag.getParentId());

        // 2. 数据入库
        tag.setUpdator(updator);
        try {
            tagDao.insertNonEmptyTag(tag);
        } catch (Exception e) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.DAO_CALL_ERROR, e);
        }
        return tag.getId();
    }

    @Override
    public Boolean editTag(Tag tag, String updator) {
        if (tag == null || StringUtils.isBlank(tag.getName()) || StringUtils.isBlank(updator)) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.ILLEGAL_ARGUMENT);
        }

        // 1. 验证父级标签是否存在
        this.getTagById(tag.getParentId());
        if (tag.getId() == 0) {
            throw BusinessException.buildCustomizedMessageException("根标签不能编辑");
        }

        // 2. 数据入库
        tag.setUpdator(updator);
        try {
            int updatedRow = tagDao.updateNonEmptyTagById(tag);
            return updatedRow == 1;
        } catch (Exception e) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.DAO_CALL_ERROR, e);
        }
    }

    @Override
    public Boolean deleteTag(Integer tagId, String updator) {
        if (StringUtils.isBlank(updator)) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.ILLEGAL_ARGUMENT);
        }

        // 1. 验证标签是否存在
        Tag tag = this.getTagById(tagId);
        if (tag.getId() == 0) {
            throw BusinessException.buildCustomizedMessageException("根标签不能删除");
        }

        // 2. 修改状态
        Tag newTag = new Tag();
        tag.setId(tagId);
        tag.setUpdator(updator);
        try {
            int updatedRow = tagDao.updateNonEmptyTagById(newTag);
            return updatedRow == 1;
        } catch (Exception e) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.DAO_CALL_ERROR, e);
        }
    }

    @Override
    public Pagination<Tag> pageTagByQueryParam(QueryParam queryParam) {
        // 1. 参数校验
        if(queryParam == null) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.ILLEGAL_ARGUMENT);
        }

        // 2. 设置查询条件的map
        Map<String, Object> queryMap = new HashMap<>(16);
        queryMap.putAll(queryParam.getParam());

        // 3. 获取数据总数，并设置相关的分页信息
        Pagination<Tag> tagPagination = new Pagination<>();
        tagPagination.setPageSize(queryParam.getPageSize());
        try {
            Integer totalCount = tagDao.countTagByQueryMap(queryMap);
            Integer totalPage = totalCount / tagPagination.getPageSize();
            if (totalCount % tagPagination.getPageSize() != 0) {
                totalPage = totalPage + 1;
            }
            tagPagination.setTotalCount(totalCount);
            tagPagination.setTotalPage(totalPage);

            // 4. 如果查询页号超过页数，则设置当前页为最大页
            if (queryParam.getPageNum() > tagPagination.getTotalPage()) {
                queryParam.setPageNum(tagPagination.getTotalPage());
            }
            tagPagination.setPageNum(queryParam.getPageNum());

            queryMap.put("offset", queryParam.getOffset());
            queryMap.put("limit", queryParam.getLimit());

            // 5. 若存在数据，则获取本页数据
            if (totalCount != 0) {
                tagPagination.setData(tagDao.pageTagByQueryMap(queryMap));
            }
        } catch (Exception e) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.DAO_CALL_ERROR);
        }

        return tagPagination;
    }
}