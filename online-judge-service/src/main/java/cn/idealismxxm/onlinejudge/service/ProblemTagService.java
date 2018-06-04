package cn.idealismxxm.onlinejudge.service;

import cn.idealismxxm.onlinejudge.domain.entity.ProblemTag;
import cn.idealismxxm.onlinejudge.domain.entity.Tag;

import java.util.List;

/**
 * 题目标签关系接口
 *
 * @author idealism
 * @date 2018/6/3
 */
public interface ProblemTagService {

    /**
     * 保存题目标签关系
     *
     * @param problemId   题目id
     * @param problemTags 题目标签关系列表
     * @param updator     修改人的用户名
     * @return true / false
     */
    Boolean saveProblemTags(Integer problemId, List<ProblemTag> problemTags, String updator);

    /**
     * 列出 题目id 下 删除状态 为 deleteStatus 的所有标签
     *
     * @param problemId 题目id
     * @param deletedStatus 删除状态（null 表示查询所有标签）
     * @return 标签列表
     */
    List<Tag> listTagByProblemIdAndDeletedStatus(Integer problemId, Integer deletedStatus);

    /**
     * 添加题目标签关系
     *
     * @param problemId 题目id
     * @param tagId     标签id
     * @param updator   修改人的用户名
     * @return true / false
     */
    Integer addProblemTag(Integer problemId, Integer tagId, String updator);

    /**
     * 删除题目标签关系
     *
     * @param problemTagId 题目标签关系id
     * @param updator      修改人的用户名
     * @return true / false
     */
    Boolean deleteProblemTag(Integer problemTagId, String updator);

    /**
     * 根据id获取题目标签关系
     *
     * @param id 主键
     * @return 题目标签关系实例
     */
    ProblemTag getProblemTagById(Integer id);

    /**
     * 通过 题目id 和 标签id 获取题目标签关系实例
     *
     * @param problemId 题目id
     * @param tagId     标签id
     * @return 题目标签关系实例
     */
    ProblemTag getProblemTagByProblemIdAndTagId(Integer problemId, Integer tagId);
}