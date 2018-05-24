package cn.idealismxxm.onlinejudge.dao;
import cn.idealismxxm.onlinejudge.domain.entity.Contest;
import java.util.List;
import java.util.Map;
public interface ContestDao{
	/**
	 * 获得Contest数据的总行数
	 * @return
	 */
	long getContestRowCount();
	/**
	 * 获得Contest数据集合
	 * @return
	 */
	List<Contest> selectContest();
	/**
	 * 获得一个Contest对象,以参数Contest对象中不为空的属性作为条件进行查询
	 * @param obj
	 * @return
	 */
	Contest selectContestByObj(Contest obj);
	/**
	 * 通过Contest的id获得Contest对象
	 * @param id
	 * @return
	 */
	Contest selectContestById(Integer id);
	/**
	 * 插入Contest到数据库,包括null值
	 * @param value
	 * @return
	 */
	int insertContest(Contest value);
	/**
	 * 插入Contest中属性值不为null的数据到数据库
	 * @param value
	 * @return
	 */
	int insertNonEmptyContest(Contest value);
	/**
	 * 批量插入Contest到数据库,包括null值
	 * @param value
	 * @return
	 */
	int insertContestByBatch(List<Contest> value);
	/**
	 * 通过Contest的id删除Contest
	 * @param id
	 * @return
	 */
	int deleteContestById(Integer id);
	/**
	 * 通过Contest的id更新Contest中的数据,包括null值
	 * @param enti
	 * @return
	 */
	int updateContestById(Contest enti);
	/**
	 * 通过Contest的id更新Contest中属性不为null的数据
	 * @param enti
	 * @return
	 */
	int updateNonEmptyContestById(Contest enti);

	/**
	 * 统计 符合查询条件 的比赛总数
	 *
	 * @param queryMap 查询条件的 map
	 * @return 比赛总数
	 */
	Integer countContestByQueryMap(Map<String, Object> queryMap);

	/**
	 * 分页查询 符合查询条件 的比赛
	 *
	 * @param queryMap 查询条件的 map
	 * @return 比赛列表
	 */
    List<Contest> pageContestByQueryMap(Map<String, Object> queryMap);
}