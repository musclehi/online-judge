package cn.idealismxxm.onlinejudge.dao;
import cn.idealismxxm.onlinejudge.domain.entity.ProblemTag;
import org.apache.ibatis.annotations.Param;

import java.util.List;
public interface ProblemTagDao{
	/**
	 * 获得ProblemTag数据的总行数
	 * @return
	 */
    long getProblemTagRowCount();
	/**
	 * 获得ProblemTag数据集合
	 * @return
	 */
    List<ProblemTag> selectProblemTag();
	/**
	 * 获得一个ProblemTag对象,以参数ProblemTag对象中不为空的属性作为条件进行查询
	 * @param obj
	 * @return
	 */
    ProblemTag selectProblemTagByObj(ProblemTag obj);
	/**
	 * 通过ProblemTag的id获得ProblemTag对象
	 * @param id
	 * @return
	 */
    ProblemTag selectProblemTagById(Integer id);
	/**
	 * 插入ProblemTag到数据库,包括null值
	 * @param value
	 * @return
	 */
    int insertProblemTag(ProblemTag value);
	/**
	 * 插入ProblemTag中属性值不为null的数据到数据库
	 * @param value
	 * @return
	 */
    int insertNonEmptyProblemTag(ProblemTag value);
	/**
	 * 批量插入ProblemTag到数据库,包括null值
	 * @param value
	 * @return
	 */
    int insertProblemTagByBatch(List<ProblemTag> value);
	/**
	 * 通过ProblemTag的id删除ProblemTag
	 * @param id
	 * @return
	 */
    int deleteProblemTagById(Integer id);
	/**
	 * 通过ProblemTag的id更新ProblemTag中的数据,包括null值
	 * @param enti
	 * @return
	 */
    int updateProblemTagById(ProblemTag enti);
	/**
	 * 通过ProblemTag的id更新ProblemTag中属性不为null的数据
	 * @param enti
	 * @return
	 */
    int updateNonEmptyProblemTagById(ProblemTag enti);

    /**
     * 通过 题目id 和 标签id 获取题目标签关系实例
     *
     * @param problemId 题目id
     * @param tagId     标签id
     * @return 题目标签关系实例
     */
    ProblemTag getProblemTagByProblemIdAndTagId(@Param("problemId") Integer problemId, @Param("tagId") Integer tagId);

    /**
     * 列出 题目id 下 删除状态 为 deleteStatus 的所有标签
     *
     * @param problemId     题目id
     * @param deletedStatus 删除状态（null 表示查询所有标签）
     * @return 标签列表
     */
    List<Integer> listTagIdByProblemIdAndDeletedStatus(@Param("problemId") Integer problemId, @Param("deletedStatus") Integer deletedStatus);
}