package cn.idealismxxm.onlinejudge.web.controller;

import cn.idealismxxm.onlinejudge.domain.entity.User;
import cn.idealismxxm.onlinejudge.domain.enums.CommonConstant;
import cn.idealismxxm.onlinejudge.domain.enums.ErrorCodeEnum;
import cn.idealismxxm.onlinejudge.domain.util.AjaxResult;
import cn.idealismxxm.onlinejudge.domain.util.RequestUtil;
import cn.idealismxxm.onlinejudge.service.UserPrivilegeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 用户权限相关操作
 *
 * @author idealism
 * @date 2018/5/22
 */
@Controller
@RequestMapping("user")
public class UserPrivilegeController {
    @Resource
    private UserPrivilegeService userPrivilegeService;

    /**
     * 添加权限
     *
     * @param username  用户名
     * @param privilege 权限标识
     * @return 用户权限id
     */
    @ResponseBody
    @RequestMapping(value = "addUserPrivilege", method = {RequestMethod.POST})
    public AjaxResult<Integer> addUserPrivilege(String username, Integer privilege) {
        User user = (User) RequestUtil.getAttribute(CommonConstant.SESSION_ATTRIBUTE_USER);
        String updator = user.getUsername();
        Integer id = userPrivilegeService.addUserPrivilege(username, privilege, updator);
        return new AjaxResult<>(ErrorCodeEnum.SUCCESS.getMsg(), id);
    }

    /**
     * 取消权限
     *
     * @param userPrivilegeId  用户权限id
     * @return true / false
     */
    @ResponseBody
    @RequestMapping(value = "cancelUserPrivilege", method = {RequestMethod.POST})
    public AjaxResult<Boolean> cancelUserPrivilege(Integer userPrivilegeId) {
        User user = (User) RequestUtil.getAttribute(CommonConstant.SESSION_ATTRIBUTE_USER);
        String updator = user.getUsername();
        Boolean result = userPrivilegeService.cancelUserPrivilege(userPrivilegeId, updator);
        return new AjaxResult<>(ErrorCodeEnum.SUCCESS.getMsg(), result);
    }
}
