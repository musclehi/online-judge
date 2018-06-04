package cn.idealismxxm.onlinejudge.web.controller;

import cn.idealismxxm.onlinejudge.domain.annotation.RequirePrivilege;
import cn.idealismxxm.onlinejudge.domain.entity.User;
import cn.idealismxxm.onlinejudge.domain.enums.CommonConstant;
import cn.idealismxxm.onlinejudge.domain.enums.ErrorCodeEnum;
import cn.idealismxxm.onlinejudge.domain.enums.PrivilegeEnum;
import cn.idealismxxm.onlinejudge.domain.exception.BusinessException;
import cn.idealismxxm.onlinejudge.domain.util.AjaxResult;
import cn.idealismxxm.onlinejudge.domain.util.JsonUtil;
import cn.idealismxxm.onlinejudge.domain.util.RequestUtil;
import cn.idealismxxm.onlinejudge.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 用户相关操作
 *
 * @author idealism
 * @date 2018/4/21
 */
@Controller
@RequestMapping("user")
public class UserController {
    @Resource
    private UserService userService;

    /**
     * 注册用户
     *
     * @param userJson 用户对象json
     * @return 用户的id
     */
    @RequirePrivilege
    @ResponseBody
    @RequestMapping(value = "signUp", method = {RequestMethod.POST})
    public AjaxResult<Integer> signUp(String userJson) {
        User user = JsonUtil.jsonToObject(userJson, User.class);
        Integer id = userService.signUp(user);
        return new AjaxResult<>(ErrorCodeEnum.SUCCESS.getMsg(), id);
    }

    /**
     * 用户登录
     *
     * @param account  用户名或邮箱
     * @param password 密码
     * @return 用户的昵称
     */
    @RequirePrivilege
    @ResponseBody
    @RequestMapping(value = "signIn", method = {RequestMethod.POST})
    public AjaxResult<String> signIn(String account, String password) {
        // 登录状态拒绝登录操作
        if (RequestUtil.getAttribute(CommonConstant.SESSION_ATTRIBUTE_USER) != null) {
            throw BusinessException.buildCustomizedMessageException("您已登录，请先注销后再登录！");
        }

        // 获取用户实例，并放入session
        User user = userService.getUserByAccountAndPassword(account, password);
        RequestUtil.setAttribute(CommonConstant.SESSION_ATTRIBUTE_USER, user);
        return new AjaxResult<>(ErrorCodeEnum.SUCCESS.getMsg(), user.getNickname());
    }

    /**
     * 用户注销
     *
     * @return true
     */
    @RequirePrivilege
    @ResponseBody
    @RequestMapping(value = "signOut", method = {RequestMethod.POST})
    public AjaxResult<Boolean> signOut() {
        RequestUtil.removeAttribute(CommonConstant.SESSION_ATTRIBUTE_USER);
        return new AjaxResult<>(ErrorCodeEnum.SUCCESS.getMsg(), true);
    }

    /**
     * 列出当前用户的信息
     *
     * @return 用户信息
     */
    @RequirePrivilege(privilegeEnum = {PrivilegeEnum.SIGN_IN})
    @ResponseBody
    @RequestMapping(value = "getCurrentUserInfo", method = {RequestMethod.GET})
    public AjaxResult<User> getCurrentUserInfo() {
        User user = RequestUtil.getAttribute(CommonConstant.SESSION_ATTRIBUTE_USER);
        // 获取最新数据，并去除敏感信息
        user = userService.getUserByUsername(user.getUsername());
        user.setPassword(null);
        return new AjaxResult<>(ErrorCodeEnum.SUCCESS.getMsg(), user);
    }
}
