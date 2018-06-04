package cn.idealismxxm.onlinejudge.service;

import cn.idealismxxm.onlinejudge.domain.entity.Tag;
import cn.idealismxxm.onlinejudge.domain.util.Pagination;
import cn.idealismxxm.onlinejudge.domain.util.QueryParam;

import java.util.List;

/**
 * 标签接口
 *
 * @author idealism
 * @date 2018/6/3
 */
public interface TagService {

    /**
     * 通过 标签id 返回标签实例
     *
     * @param tagId 标签id
     * @return 标签实例
     */
    Tag getTagById(Integer tagId);

    /**
     * 添加标签
     *
     * @param tag     标签
     * @param updator 修改人的用户名
     * @return 主键
     */
    Integer addTag(Tag tag, String updator);

    /**
     * 编辑标签
     *
     * @param tag     标签
     * @param updator 修改人的用户名
     * @return true / false
     */
    Boolean editTag(Tag tag, String updator);

    /**
     * 删除标签
     *
     * @param tagId   标签主键
     * @param updator 修改人的用户名
     * @return 主键
     */
    Boolean deleteTag(Integer tagId, String updator);

    /**
     * 分页获取标签列表
     *
     * @param queryParam 查询条件
     * @return 标签列表分页封装
     */
    Pagination<Tag> pageTagByQueryParam(QueryParam queryParam);

    /**
     * 获取标签id 在 ids中的 标签列表
     *
     * @param ids id列表
     * @return 标签列表
     */
    List<Tag> listTagByIds(List<Integer> ids);
}