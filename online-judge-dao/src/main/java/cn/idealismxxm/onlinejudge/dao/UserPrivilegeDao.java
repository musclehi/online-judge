package cn.idealismxxm.onlinejudge.dao;
import cn.idealismxxm.onlinejudge.domain.entity.UserPrivilege;
import org.apache.ibatis.annotations.Param;

import java.util.List;
public interface UserPrivilegeDao{
	/**
	 * 获得UserPrivilege数据的总行数
	 * @return
	 */
    long getUserPrivilegeRowCount();
	/**
	 * 获得UserPrivilege数据集合
	 * @return
	 */
    List<UserPrivilege> selectUserPrivilege();
	/**
	 * 获得一个UserPrivilege对象,以参数UserPrivilege对象中不为空的属性作为条件进行查询
	 * @param obj
	 * @return
	 */
    UserPrivilege selectUserPrivilegeByObj(UserPrivilege obj);
	/**
	 * 通过UserPrivilege的id获得UserPrivilege对象
	 * @param id
	 * @return
	 */
    UserPrivilege selectUserPrivilegeById(Integer id);
	/**
	 * 插入UserPrivilege到数据库,包括null值
	 * @param value
	 * @return
	 */
    int insertUserPrivilege(UserPrivilege value);
	/**
	 * 插入UserPrivilege中属性值不为null的数据到数据库
	 * @param value
	 * @return
	 */
    int insertNonEmptyUserPrivilege(UserPrivilege value);
	/**
	 * 批量插入UserPrivilege到数据库,包括null值
	 * @param value
	 * @return
	 */
    int insertUserPrivilegeByBatch(List<UserPrivilege> value);
	/**
	 * 通过UserPrivilege的id删除UserPrivilege
	 * @param id
	 * @return
	 */
    int deleteUserPrivilegeById(Integer id);
	/**
	 * 通过UserPrivilege的id更新UserPrivilege中的数据,包括null值
	 * @param enti
	 * @return
	 */
    int updateUserPrivilegeById(UserPrivilege enti);
	/**
	 * 通过UserPrivilege的id更新UserPrivilege中属性不为null的数据
	 * @param enti
	 * @return
	 */
    int updateNonEmptyUserPrivilegeById(UserPrivilege enti);

    /**
     * 根据 username 和 privilege 获取用户权限实例
     *
     * @param username  用户名
     * @param privilege 权限标识
     * @return 用户权限实例
     */
    UserPrivilege getUserPrivilegeByUsernameAndPrivilege(@Param("username") String username, @Param("privilege") Integer privilege);
}