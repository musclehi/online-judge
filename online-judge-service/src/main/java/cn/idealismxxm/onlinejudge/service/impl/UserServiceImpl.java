package cn.idealismxxm.onlinejudge.service.impl;

import cn.idealismxxm.onlinejudge.dao.UserDao;
import cn.idealismxxm.onlinejudge.domain.entity.User;
import cn.idealismxxm.onlinejudge.domain.enums.ErrorCodeEnum;
import cn.idealismxxm.onlinejudge.domain.enums.RegexEnum;
import cn.idealismxxm.onlinejudge.domain.exception.BusinessException;
import cn.idealismxxm.onlinejudge.domain.util.JsonUtil;
import cn.idealismxxm.onlinejudge.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户相关操作接口实现
 *
 * @author idealism
 * @date 2018/4/21
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource
    private UserDao userDao;

    @Override
    public Integer signUp(User user) {
        // 1. 参数校验
        if (user == null) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.ILLEGAL_ARGUMENT);
        }
        RegexEnum.USERNAME.validate(user.getUsername());
        RegexEnum.EMAIL.validate(user.getEmail());
        RegexEnum.PASSWORD.validate(user.getPassword());

        // 2. 查看是否存在重复数据
        if(userDao.getUserByUsername(user.getUsername()) != null) {
            throw BusinessException.buildCustomizedMessageException("用户名已存在！");
        }
        if(userDao.getUserByEmail(user.getEmail()) != null) {
            throw BusinessException.buildCustomizedMessageException("邮箱已注册！");
        }

        // 3. 数据入库
        try {
            userDao.insertUser(user);
            return user.getId();
        } catch (Exception e) {
            LOGGER.error("#signUp error, user: {}", JsonUtil.objectToJson(user));
            throw BusinessException.buildBusinessException(ErrorCodeEnum.DAO_CALL_ERROR, e);
        }
    }

    @Override
    public User getUserByAccountAndPassword(String account, String password) {
        // 1. 参数校验
        RegexEnum.PASSWORD.validate(password);

        // 2. 获取用户实例
        User user;
        try {
            if(RegexEnum.USERNAME.test(account)) {
                user = userDao.getUserByUsernameAndPassword(account, password);
            } else if(RegexEnum.EMAIL.test(account)) {
                user = userDao.getUserByEmailAndPassword(account, password);
            } else {
                throw BusinessException.buildBusinessException(ErrorCodeEnum.ILLEGAL_ARGUMENT);
            }
        } catch (BusinessException e) {
            LOGGER.error("#getUserByAccountAndPassword error, account: {}, password: {}", account, password, e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("#getUserByAccountAndPassword error, account: {}, password: {}", account, password, e);
            throw BusinessException.buildBusinessException(ErrorCodeEnum.DAO_CALL_ERROR, e);
        }

        // 验证数据是否存在
        if (user == null) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.USER_NOT_EXIST);
        }
        return user;
    }

    @Override
    public User getUserByUsername(String username) {
        // 1. 参数校验
        RegexEnum.USERNAME.validate(username);

        // 2. 获取用户实例
        User user;
        try {
            user = userDao.getUserByUsername(username);
        } catch (Exception e) {
            LOGGER.error("#getUserByUsername error, username: {}", username, e);
            throw BusinessException.buildBusinessException(ErrorCodeEnum.DAO_CALL_ERROR, e);
        }

        // 验证数据是否存在
        if (user == null) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.USER_NOT_EXIST);
        }
        return user;
    }
}