package cn.idealismxxm.onlinejudge.dao;
import cn.idealismxxm.onlinejudge.domain.entity.User;
import java.util.List;
public interface UserDao{
	/**
	 * 获得User数据的总行数
	 * @return
	 */
	long getUserRowCount();
	/**
	 * 获得User数据集合
	 * @return
	 */
	List<User> selectUser();
	/**
	 * 获得一个User对象,以参数User对象中不为空的属性作为条件进行查询
	 * @param obj
	 * @return
	 */
	User selectUserByObj(User obj);
	/**
	 * 通过User的id获得User对象
	 * @param id
	 * @return
	 */
	User selectUserById(Integer id);
	/**
	 * 插入User到数据库,包括null值
	 * @param value
	 * @return
	 */
	int insertUser(User value);
	/**
	 * 插入User中属性值不为null的数据到数据库
	 * @param value
	 * @return
	 */
	int insertNonEmptyUser(User value);
	/**
	 * 批量插入User到数据库,包括null值
	 * @param value
	 * @return
	 */
	int insertUserByBatch(List<User> value);
	/**
	 * 通过User的id删除User
	 * @param id
	 * @return
	 */
	int deleteUserById(Integer id);
	/**
	 * 通过User的id更新User中的数据,包括null值
	 * @param enti
	 * @return
	 */
	int updateUserById(User enti);
	/**
	 * 通过User的id更新User中属性不为null的数据
	 * @param enti
	 * @return
	 */
	int updateNonEmptyUserById(User enti);
}