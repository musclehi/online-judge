package cn.idealismxxm.onlinejudge.web.controller;

import cn.idealismxxm.onlinejudge.domain.annotation.RequirePrivilege;
import cn.idealismxxm.onlinejudge.domain.entity.Submission;
import cn.idealismxxm.onlinejudge.domain.enums.ErrorCodeEnum;
import cn.idealismxxm.onlinejudge.domain.enums.PrivilegeEnum;
import cn.idealismxxm.onlinejudge.service.SubmissionService;
import cn.idealismxxm.onlinejudge.domain.util.AjaxResult;
import cn.idealismxxm.onlinejudge.domain.util.JsonUtil;
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
        Submission submission = JsonUtil.jsonToObject(submissionJson, Submission.class);
        Integer id = submissionService.submit(submission);
        return new AjaxResult<>(ErrorCodeEnum.SUCCESS.getMsg(), id);
    }
}
