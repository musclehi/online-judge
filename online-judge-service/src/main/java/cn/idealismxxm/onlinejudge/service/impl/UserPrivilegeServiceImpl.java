package cn.idealismxxm.onlinejudge.service.impl;

import cn.idealismxxm.onlinejudge.dao.UserPrivilegeDao;
import cn.idealismxxm.onlinejudge.domain.entity.UserPrivilege;
import cn.idealismxxm.onlinejudge.domain.enums.DeletedStatusEnum;
import cn.idealismxxm.onlinejudge.domain.enums.ErrorCodeEnum;
import cn.idealismxxm.onlinejudge.domain.enums.PrivilegeEnum;
import cn.idealismxxm.onlinejudge.domain.exception.BusinessException;
import cn.idealismxxm.onlinejudge.service.UserPrivilegeService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户权限相关操作接口实现
 *
 * @author idealism
 * @date 2018/5/22
 */
@Service("userPrivilegeService")
public class UserPrivilegeServiceImpl implements UserPrivilegeService {

    public static final Logger LOGGER = LoggerFactory.getLogger(UserPrivilegeServiceImpl.class);

    @Resource
    private UserPrivilegeDao userPrivilegeDao;

    @Override
    public UserPrivilege getUserPrivilegeById(Integer id) {
        if (id == null || id <= 0) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.ILLEGAL_ARGUMENT);
        }

        UserPrivilege userPrivilege;
        try {
            userPrivilege = userPrivilegeDao.selectUserPrivilegeById(id);
        } catch (Exception e) {
            LOGGER.error("#getUserPrivilegeById error, id: {}", id, e);
            throw BusinessException.buildBusinessException(ErrorCodeEnum.DAO_CALL_ERROR, e);
        }

        if (userPrivilege == null) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.USER_PRIVILEGE_NOT_EXIST);
        }
        return userPrivilege;
    }

    @Override
    public UserPrivilege getUserPrivilegeByUsernameAndPrivilege(String username, Integer privilege) {
        if (StringUtils.isBlank(username) || PrivilegeEnum.getLanguageEnumByCode(privilege) != null) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.ILLEGAL_ARGUMENT);
        }

        try {
            return userPrivilegeDao.getUserPrivilegeByUsernameAndPrivilege(username, privilege);
        } catch (Exception e) {
            LOGGER.error("#getUserPrivilegeByUsernameAndPrivilege error, username: {}, privilege: {}", username, privilege, e);
            throw BusinessException.buildBusinessException(ErrorCodeEnum.DAO_CALL_ERROR, e);
        }
    }

    @Override
    public List<UserPrivilege> listUserPrivilegeByUsername(String username) {
        if (StringUtils.isBlank(username)) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.ILLEGAL_ARGUMENT);
        }

        try {
            return userPrivilegeDao.listUserPrivilegeByUsername(username);
        } catch (Exception e) {
            LOGGER.error("#listUserPrivilegeByUsername error, username: {}", username, e);
            throw BusinessException.buildBusinessException(ErrorCodeEnum.DAO_CALL_ERROR, e);
        }
    }

    @Override
    public Integer addUserPrivilege(String username, Integer privilege, String updator) {
        if (StringUtils.isBlank(updator)) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.ILLEGAL_ARGUMENT);
        }

        // 1. 获取用户权限实例
        UserPrivilege userPrivilege = this.getUserPrivilegeByUsernameAndPrivilege(username, privilege);

        // 2. 若用户权限实例不存在，则将当前用户权限写入数据库
        if (userPrivilege == null) {
            userPrivilege = new UserPrivilege();
            userPrivilege.setUsername(username);
            userPrivilege.setPrivilege(privilege);
            userPrivilege.setUpdator(updator);
            userPrivilege.setDeletedStatus(DeletedStatusEnum.VALID.getCode());

            try {
                userPrivilegeDao.insertNonEmptyUserPrivilege(userPrivilege);
            } catch (Exception e) {
                throw BusinessException.buildBusinessException(ErrorCodeEnum.DAO_CALL_ERROR, e);
            }
            return userPrivilege.getId();
        }

        // 3. 若用户权限实例存在，且已逻辑删除，则更新其删除状态为有效
        if (DeletedStatusEnum.DELETED.getCode().equals(userPrivilege.getPrivilege())) {
            userPrivilege.setUpdator(updator);
            userPrivilege.setDeletedStatus(DeletedStatusEnum.VALID.getCode());

            try {
                userPrivilegeDao.updateNonEmptyUserPrivilegeById(userPrivilege);
            } catch (Exception e) {
                throw BusinessException.buildBusinessException(ErrorCodeEnum.DAO_CALL_ERROR, e);
            }
            return userPrivilege.getId();
        }

        // 4. 若用户权限实例存在，且有效，则抛出用户 权限已存在 异常
        throw BusinessException.buildBusinessException(ErrorCodeEnum.USER_PRIVILEGE_ALREADY_EXIST);
    }

    @Override
    public Boolean cancelUserPrivilege(Integer userPrivilegeId, String updator) {
        UserPrivilege userPrivilege = this.getUserPrivilegeById(userPrivilegeId);
        // 当前权限已经逻辑删除，则抛出用户权限已取消异常
        if (DeletedStatusEnum.DELETED.getCode().equals(userPrivilege.getDeletedStatus())) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.USER_PRIVILEGE_ALREADY_CANCEL);
        }

        // 组装用户权限实例，并标记删除状态为 删除
        UserPrivilege newUserPrivilege = new UserPrivilege();
        newUserPrivilege.setId(userPrivilegeId);
        newUserPrivilege.setUpdator(updator);
        newUserPrivilege.setDeletedStatus(DeletedStatusEnum.DELETED.getCode());

        try {
            userPrivilegeDao.updateNonEmptyUserPrivilegeById(newUserPrivilege);
            return true;
        } catch (Exception e) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.DAO_CALL_ERROR, e);
        }
    }
}