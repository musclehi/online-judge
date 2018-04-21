package cn.idealismxxm.onlinejudge.service.impl;

import cn.idealismxxm.onlinejudge.dao.UserDao;
import cn.idealismxxm.onlinejudge.domain.entity.User;
import cn.idealismxxm.onlinejudge.domain.enums.ErrorCodeEnum;
import cn.idealismxxm.onlinejudge.domain.exception.BusinessException;
import cn.idealismxxm.onlinejudge.domain.util.JsonUtil;
import cn.idealismxxm.onlinejudge.service.UserService;
import org.apache.commons.lang3.StringUtils;
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
        if (user == null || StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())
                || StringUtils.isBlank(user.getEmail()) || !user.getEmail().matches("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$")) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.ILLEGAL_ARGUMENT);
        }

        // 2. 数据入库
        try {
            userDao.insertUser(user);
            return user.getId();
        } catch (Exception e) {
            LOGGER.error("#signUp error, user: {}", JsonUtil.objectToJson(user));
            throw BusinessException.buildBusinessException(ErrorCodeEnum.DAO_CALL_ERROR, e);
        }
    }
}