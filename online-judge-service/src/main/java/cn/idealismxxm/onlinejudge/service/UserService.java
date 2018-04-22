package cn.idealismxxm.onlinejudge.service;

import cn.idealismxxm.onlinejudge.domain.entity.User;

/**
 * 用户相关操作接口
 *
 * @author idealism
 * @date 2018/4/21
 */
public interface UserService {
    /**
     * 注册用户
     *
     * @param user 用户对象
     * @return 用户的id
     */
    Integer signUp(User user);

    /**
     * 通过用户名或邮箱 和 密码获取用户实例
     * @param account 用户名或邮箱
     * @param password 密码
     * @return 用户实例
     */
    User getUserByAccountAndPassword(String account, String password);
}