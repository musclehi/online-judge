package cn.idealismxxm.onlinejudge.service.impl;

import cn.idealismxxm.onlinejudge.dao.ProblemTagDao;
import cn.idealismxxm.onlinejudge.domain.entity.ProblemTag;
import cn.idealismxxm.onlinejudge.domain.entity.Tag;
import cn.idealismxxm.onlinejudge.domain.enums.DeletedStatusEnum;
import cn.idealismxxm.onlinejudge.domain.enums.ErrorCodeEnum;
import cn.idealismxxm.onlinejudge.domain.exception.BusinessException;
import cn.idealismxxm.onlinejudge.service.ProblemService;
import cn.idealismxxm.onlinejudge.service.ProblemTagService;
import cn.idealismxxm.onlinejudge.service.TagService;
import httl.util.StringUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 题目标签关系接口
 *
 * @author idealism
 * @date 2018/6/3
 */
@Service("problemTagService")
public class ProblemTagServiceImpl implements ProblemTagService {
    @Resource
    private ProblemTagDao problemTagDao;

    @Resource
    private ProblemService problemService;

    @Resource
    private TagService tagService;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public Boolean saveProblemTags(Integer problemId, List<ProblemTag> problemTags, String updator) {
        if (CollectionUtils.isEmpty(problemTags)) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.ILLEGAL_ARGUMENT);
        }
        // 将题目标签关系分为待保存的和待添加的
        List<ProblemTag> toBeInsertedProblemTags = new ArrayList<>(problemTags.size());
        List<ProblemTag> toBeUpdatedProblemTags = new ArrayList<>(problemTags.size());
        problemTags.forEach(problemTag -> {
            problemTag.setProblemId(problemId);
            problemTag.setUpdator(updator);
            if (problemTag.getId() == null) {
                problemTag.setDeletedStatus(DeletedStatusEnum.VALID.getCode());
                toBeInsertedProblemTags.add(problemTag);
            } else {
                toBeUpdatedProblemTags.add(problemTag);
            }
        });

        // 数据入库
        try {
            if (CollectionUtils.isNotEmpty(toBeInsertedProblemTags)) {
                int insertedRow = problemTagDao.insertProblemTagByBatch(toBeInsertedProblemTags);
                if (insertedRow != toBeInsertedProblemTags.size()) {
                    throw BusinessException.buildBusinessException(ErrorCodeEnum.DATA_SAVE_ERROR);
                }
            }
            if (CollectionUtils.isNotEmpty(toBeUpdatedProblemTags)) {
                int updatedRow = 0;
                for (ProblemTag problemTag : toBeUpdatedProblemTags) {
                    updatedRow += problemTagDao.updateNonEmptyProblemTagById(problemTag);
                }
                if (updatedRow != toBeUpdatedProblemTags.size()) {
                    throw BusinessException.buildBusinessException(ErrorCodeEnum.DATA_SAVE_ERROR);
                }
            }
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.DAO_CALL_ERROR, e);
        }

        return true;
    }

    @Override
    public List<Tag> listTagByProblemIdAndDeletedStatus(Integer problemId, Integer deletedStatus) {
        // 1. 参数校验
        if(problemId == null || problemId <= 0) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.ILLEGAL_ARGUMENT);
        }
        if(deletedStatus != null && DeletedStatusEnum.getDeletedStatusEnumByCode(deletedStatus) == null) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.ILLEGAL_ARGUMENT);
        }

        // 2. 获取 problemId 下 符合条件的 标签id 列表
        List<Integer> tagIds;
        try {
            tagIds = problemTagDao.listTagIdByProblemIdAndDeletedStatus(problemId, deletedStatus);
        } catch (Exception e) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.DAO_CALL_ERROR, e);
        }


        // 3. 获取 标签实例 列表
        return tagService.listTagByIds(tagIds);
    }

    @Override
    public Integer addProblemTag(Integer problemId, Integer tagId, String updator) {
        if (StringUtils.isBlank(updator)) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.ILLEGAL_ARGUMENT);
        }

        // 1. 判断题目和标签是否存在
        problemService.getProblemById(problemId);
        tagService.getTagById(tagId);

        // 2. 获取题目标签关系实例
        ProblemTag problemTag = this.getProblemTagByProblemIdAndTagId(problemId, tagId);

        // 2. 若题目标签关系实例不存在，则将当前题目标签关系写入数据库
        if (problemTag == null) {
            problemTag = new ProblemTag();
            problemTag.setProblemId(problemId);
            problemTag.setTagId(tagId);
            problemTag.setUpdator(updator);
            problemTag.setDeletedStatus(DeletedStatusEnum.VALID.getCode());

            try {
                problemTagDao.insertNonEmptyProblemTag(problemTag);
            } catch (Exception e) {
                throw BusinessException.buildBusinessException(ErrorCodeEnum.DAO_CALL_ERROR, e);
            }
            return problemTag.getId();
        }

        // 3. 若题目标签关系实例存在，且已逻辑删除，则更新其删除状态为有效
        if (DeletedStatusEnum.DELETED.getCode().equals(problemTag.getDeletedStatus())) {
            problemTag.setUpdator(updator);
            problemTag.setDeletedStatus(DeletedStatusEnum.VALID.getCode());

            try {
                problemTagDao.updateNonEmptyProblemTagById(problemTag);
            } catch (Exception e) {
                throw BusinessException.buildBusinessException(ErrorCodeEnum.DAO_CALL_ERROR, e);
            }
            return problemTag.getId();
        }

        // 4. 若题目标签关系实例存在，且有效，则抛出 题目标签关系已存在 异常
        throw BusinessException.buildBusinessException(ErrorCodeEnum.PROBLEM_TAG_ALREADY_EXIST);
    }

    @Override
    public Boolean deleteProblemTag(Integer problemTagId, String updator) {
        ProblemTag problemTag = this.getProblemTagById(problemTagId);
        // 当前 题目标签关系 已经逻辑删除，则抛出 题目标签关系不存在 异常
        if (DeletedStatusEnum.DELETED.getCode().equals(problemTag.getDeletedStatus())) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.PROBLEM_TAG_NOT_EXIST);
        }

        // 组装 题目标签关系 实例，并标记删除状态为 删除
        ProblemTag newProblemTag = new ProblemTag();
        newProblemTag.setId(problemTagId);
        newProblemTag.setUpdator(updator);
        newProblemTag.setDeletedStatus(DeletedStatusEnum.DELETED.getCode());

        try {
            int updatedRow = problemTagDao.updateNonEmptyProblemTagById(newProblemTag);
            return updatedRow == 1;
        } catch (Exception e) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.DAO_CALL_ERROR, e);
        }
    }

    @Override
    public ProblemTag getProblemTagById(Integer id) {
        if (id == null || id <= 0) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.ILLEGAL_ARGUMENT);
        }

        ProblemTag problemTag;
        try {
            problemTag = problemTagDao.selectProblemTagById(id);
        } catch (Exception e) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.DAO_CALL_ERROR, e);
        }

        if (problemTag == null) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.PROBLEM_TAG_NOT_EXIST);
        }
        return problemTag;
    }

    @Override
    public ProblemTag getProblemTagByProblemIdAndTagId(Integer problemId, Integer tagId) {
        if (problemId == null || problemId <= 0 || tagId == null || tagId <= 0) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.ILLEGAL_ARGUMENT);
        }

        try {
            return problemTagDao.getProblemTagByProblemIdAndTagId(problemId, tagId);
        } catch (Exception e) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.DAO_CALL_ERROR, e);
        }
    }
}