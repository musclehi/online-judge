package cn.idealismxxm.onlinejudge.dao;
import cn.idealismxxm.onlinejudge.domain.entity.Submission;
import java.util.List;
public interface SubmissionDao{
	/**
	 * 获得Submission数据的总行数
	 * @return
	 */
    long getSubmissionRowCount();
	/**
	 * 获得Submission数据集合
	 * @return
	 */
    List<Submission> selectSubmission();
	/**
	 * 获得一个Submission对象,以参数Submission对象中不为空的属性作为条件进行查询
	 * @param obj
	 * @return
	 */
    Submission selectSubmissionByObj(Submission obj);
	/**
	 * 通过Submission的id获得Submission对象
	 * @param id
	 * @return
	 */
    Submission selectSubmissionById(Integer id);
	/**
	 * 插入Submission到数据库,包括null值
	 * @param value
	 * @return
	 */
    int insertSubmission(Submission value);
	/**
	 * 插入Submission中属性值不为null的数据到数据库
	 * @param value
	 * @return
	 */
    int insertNonEmptySubmission(Submission value);
	/**
	 * 批量插入Submission到数据库,包括null值
	 * @param value
	 * @return
	 */
    int insertSubmissionByBatch(List<Submission> value);
	/**
	 * 通过Submission的id删除Submission
	 * @param id
	 * @return
	 */
    int deleteSubmissionById(Integer id);
	/**
	 * 通过Submission的id更新Submission中的数据,包括null值
	 * @param enti
	 * @return
	 */
    int updateSubmissionById(Submission enti);
	/**
	 * 通过Submission的id更新Submission中属性不为null的数据
	 * @param enti
	 * @return
	 */
    int updateNonEmptySubmissionById(Submission enti);
}