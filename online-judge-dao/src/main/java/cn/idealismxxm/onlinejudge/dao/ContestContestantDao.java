package cn.idealismxxm.onlinejudge.dao;
import cn.idealismxxm.onlinejudge.domain.entity.ContestContestant;
import org.apache.ibatis.annotations.Param;

import java.util.List;
public interface ContestContestantDao{
	/**
	 * 获得ContestContestant数据的总行数
	 * @return
	 */
    long getContestContestantRowCount();
	/**
	 * 获得ContestContestant数据集合
	 * @return
	 */
    List<ContestContestant> selectContestContestant();
	/**
	 * 获得一个ContestContestant对象,以参数ContestContestant对象中不为空的属性作为条件进行查询
	 * @param obj
	 * @return
	 */
    ContestContestant selectContestContestantByObj(ContestContestant obj);
	/**
	 * 通过ContestContestant的id获得ContestContestant对象
	 * @param id
	 * @return
	 */
    ContestContestant selectContestContestantById(Integer id);
	/**
	 * 插入ContestContestant到数据库,包括null值
	 * @param value
	 * @return
	 */
    int insertContestContestant(ContestContestant value);
	/**
	 * 插入ContestContestant中属性值不为null的数据到数据库
	 * @param value
	 * @return
	 */
    int insertNonEmptyContestContestant(ContestContestant value);
	/**
	 * 批量插入ContestContestant到数据库,包括null值
	 * @param value
	 * @return
	 */
    int insertContestContestantByBatch(List<ContestContestant> value);
	/**
	 * 通过ContestContestant的id删除ContestContestant
	 * @param id
	 * @return
	 */
    int deleteContestContestantById(Integer id);
	/**
	 * 通过ContestContestant的id更新ContestContestant中的数据,包括null值
	 * @param enti
	 * @return
	 */
    int updateContestContestantById(ContestContestant enti);
	/**
	 * 通过ContestContestant的id更新ContestContestant中属性不为null的数据
	 * @param enti
	 * @return
	 */
    int updateNonEmptyContestContestantById(ContestContestant enti);

    /**
     * 统计contestId下所有username的参赛信息个数
     *
     * @param contestId 比赛id
     * @param username 用户名
     * @return 参赛信息个数
     */
    Integer countByContestIdAndUsername(@Param("contestId")Integer contestId, @Param("username") String username);

	/**
	 * 获取contestId下所有参赛人用户名列表
	 * @param contestId 比赛id
	 * @return 参赛人用户名列表
	 */
	List<String> listContestContestantUsernameByContestId(Integer contestId);
}