package cn.idealismxxm.onlinejudge.web.controller;

import cn.idealismxxm.onlinejudge.domain.entity.User;
import cn.idealismxxm.onlinejudge.domain.enums.ErrorCodeEnum;
import cn.idealismxxm.onlinejudge.domain.util.AjaxResult;
import cn.idealismxxm.onlinejudge.domain.util.JsonUtil;
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
    @ResponseBody
    @RequestMapping(value = "signUp", method = {RequestMethod.POST})
    public AjaxResult<Integer> signUp(String userJson) {
        User user = JsonUtil.jsonToObject(userJson, User.class);
        Integer id = userService.signUp(user);
        return new AjaxResult<>(ErrorCodeEnum.SUCCESS.getMsg(), id);
    }
}
