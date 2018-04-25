package cn.idealismxxm.onlinejudge.dao;
import cn.idealismxxm.onlinejudge.domain.entity.ContestSubmission;
import java.util.List;
public interface ContestSubmissionDao{
	/**
	 * 获得ContestSubmission数据的总行数
	 * @return
	 */
    long getContestSubmissionRowCount();
	/**
	 * 获得ContestSubmission数据集合
	 * @return
	 */
    List<ContestSubmission> selectContestSubmission();
	/**
	 * 获得一个ContestSubmission对象,以参数ContestSubmission对象中不为空的属性作为条件进行查询
	 * @param obj
	 * @return
	 */
    ContestSubmission selectContestSubmissionByObj(ContestSubmission obj);
	/**
	 * 通过ContestSubmission的id获得ContestSubmission对象
	 * @param id
	 * @return
	 */
    ContestSubmission selectContestSubmissionById(Integer id);
	/**
	 * 插入ContestSubmission到数据库,包括null值
	 * @param value
	 * @return
	 */
    int insertContestSubmission(ContestSubmission value);
	/**
	 * 插入ContestSubmission中属性值不为null的数据到数据库
	 * @param value
	 * @return
	 */
    int insertNonEmptyContestSubmission(ContestSubmission value);
	/**
	 * 批量插入ContestSubmission到数据库,包括null值
	 * @param value
	 * @return
	 */
    int insertContestSubmissionByBatch(List<ContestSubmission> value);
	/**
	 * 通过ContestSubmission的id删除ContestSubmission
	 * @param id
	 * @return
	 */
    int deleteContestSubmissionById(Integer id);
	/**
	 * 通过ContestSubmission的id更新ContestSubmission中的数据,包括null值
	 * @param enti
	 * @return
	 */
    int updateContestSubmissionById(ContestSubmission enti);
	/**
	 * 通过ContestSubmission的id更新ContestSubmission中属性不为null的数据
	 * @param enti
	 * @return
	 */
    int updateNonEmptyContestSubmissionById(ContestSubmission enti);
}