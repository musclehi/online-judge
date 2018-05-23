package cn.idealismxxm.onlinejudge.service;

import cn.idealismxxm.onlinejudge.domain.entity.UserPrivilege;

import java.util.List;

/**
 * 用户权限相关操作接口
 *
 * @author idealism
 * @date 2018/5/22
 */
public interface UserPrivilegeService {

    /**
     * 根据id获取用户权限实例
     *
     * @param id 主键
     * @return 用户权限实例
     */
    UserPrivilege getUserPrivilegeById(Integer id);

    /**
     * 根据 username 和 privilege 获取用户权限实例（可以是逻辑删除的）
     *
     * @param username  用户名
     * @param privilege 权限标识
     * @return 用户权限实例
     */
    UserPrivilege getUserPrivilegeByUsernameAndPrivilege(String username, Integer privilege);

    /**
     * 获取 username 的用户权限列表（包含逻辑删除的）
     *
     * @param username  用户名
     * @return 用户权限列表
     */
    List<UserPrivilege> listUserPrivilegeByUsername(String username);

    /**
     * 给 username 添加 privilege 权限
     *
     * @param username  用户名
     * @param privilege 权限标识
     * @param updator   更新人的 username
     * @return 用户权限id
     */
    Integer addUserPrivilege(String username, Integer privilege, String updator);

    /**
     * 取消 主键为 userPrivilegeId 的用户权限
     *
     * @param userPrivilegeId 用户权限id
     * @param updator         更新人的 username
     * @return true / false
     */
    Boolean cancelUserPrivilege(Integer userPrivilegeId, String updator);
}