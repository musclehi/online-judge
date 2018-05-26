package cn.idealismxxm.onlinejudge.web.controller;

import cn.idealismxxm.onlinejudge.domain.annotation.RequirePrivilege;
import cn.idealismxxm.onlinejudge.domain.entity.Submission;
import cn.idealismxxm.onlinejudge.domain.entity.User;
import cn.idealismxxm.onlinejudge.domain.enums.CommonConstant;
import cn.idealismxxm.onlinejudge.domain.enums.ErrorCodeEnum;
import cn.idealismxxm.onlinejudge.domain.enums.PrivilegeEnum;
import cn.idealismxxm.onlinejudge.domain.util.*;
import cn.idealismxxm.onlinejudge.service.SubmissionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 题目相关操作
 *
 * @author idealism
 * @date 2018/3/23
 */
@Controller
@RequestMapping("submission")
public class SubmissionController {
    @Resource
    private SubmissionService submissionService;

    /**
     * 提交代码
     *
     * @param submissionJson  提交记录json
     * @return 提交记录的id
     */
    @RequirePrivilege(privilegeEnum = {PrivilegeEnum.SIGN_IN})
    @ResponseBody
    @RequestMapping(value = "submit", method = {RequestMethod.POST})
    public AjaxResult<Integer> submit(String submissionJson) {
        User user = RequestUtil.getAttribute(CommonConstant.SESSION_ATTRIBUTE_USER);
        Submission submission = JsonUtil.jsonToObject(submissionJson, Submission.class);
        Integer id = submissionService.submit(submission, user.getUsername());
        return new AjaxResult<>(ErrorCodeEnum.SUCCESS.getMsg(), id);
    }

    /**
     * 分页获取提交记录列表
     *
     * @param queryParamJson 查询条件 的 json串
     * @return 提交列表分页封装
     */
    @RequirePrivilege
    @ResponseBody
    @RequestMapping(value = "list", method = {RequestMethod.GET})
    public AjaxResult<Pagination<Submission>> list(String queryParamJson) {
        // TODO 屏蔽敏感信息
        QueryParam queryParam = JsonUtil.jsonToObject(queryParamJson, QueryParam.class);
        Pagination<Submission> result = submissionService.pageSubmissionByQueryParam(queryParam);
        return new AjaxResult<>(ErrorCodeEnum.SUCCESS.getMsg(), result);
    }
}
