package cn.idealismxxm.onlinejudge.web.controller;

import cn.idealismxxm.onlinejudge.domain.annotation.RequirePrivilege;
import cn.idealismxxm.onlinejudge.domain.entity.Contest;
import cn.idealismxxm.onlinejudge.domain.entity.Problem;
import cn.idealismxxm.onlinejudge.domain.entity.Submission;
import cn.idealismxxm.onlinejudge.domain.entity.User;
import cn.idealismxxm.onlinejudge.domain.enums.CommonConstant;
import cn.idealismxxm.onlinejudge.domain.enums.ErrorCodeEnum;
import cn.idealismxxm.onlinejudge.domain.enums.PrivilegeEnum;
import cn.idealismxxm.onlinejudge.domain.util.*;
import cn.idealismxxm.onlinejudge.service.ContestContestantService;
import cn.idealismxxm.onlinejudge.service.ContestService;
import cn.idealismxxm.onlinejudge.service.ContestSubmissionService;
import cn.idealismxxm.onlinejudge.service.ProblemService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 比赛相关操作
 *
 * @author idealism
 * @date 2018/4/24
 */
@Controller
@RequestMapping("contest")
public class ContestController {
    @Resource
    private ContestService contestService;

    @Resource
    private ContestContestantService contestContestantService;

    @Resource
    private ContestSubmissionService contestSubmissionService;

    @Resource
    private ProblemService problemService;

    /**
     * 添加新比赛
     *
     * @param contestJson 比赛实例json
     * @return 比赛的id
     */
    @RequirePrivilege(privilegeEnum = {PrivilegeEnum.SIGN_IN, PrivilegeEnum.MANAGE_CONTEST})
    @ResponseBody
    @RequestMapping(value = "addContest", method = {RequestMethod.POST})
    public AjaxResult<Integer> addContest(String contestJson) {
        Contest contest = JsonUtil.jsonToObject(contestJson, Contest.class);
        Integer id = contestService.addContest(contest);
        return new AjaxResult<>(ErrorCodeEnum.SUCCESS.getMsg(), id);
    }

    /**
     * 编辑比赛
     *
     * @param contestJson 比赛实例json
     * @return true / false
     */
    @RequirePrivilege(privilegeEnum = {PrivilegeEnum.SIGN_IN, PrivilegeEnum.MANAGE_CONTEST})
    @ResponseBody
    @RequestMapping(value = "editContest", method = {RequestMethod.POST})
    public AjaxResult<Boolean> editContest(String contestJson) {
        Contest contest = JsonUtil.jsonToObject(contestJson, Contest.class);
        Boolean result = contestService.editContest(contest);
        return new AjaxResult<>(ErrorCodeEnum.SUCCESS.getMsg(), result);
    }

    /**
     * 报名比赛
     *
     * @param contestId 比赛id
     * @return 报名信息的id
     */
    @RequirePrivilege(privilegeEnum = {PrivilegeEnum.SIGN_IN})
    @ResponseBody
    @RequestMapping(value = "register", method = {RequestMethod.POST})
    public AjaxResult<Integer> register(Integer contestId) {
        User user = (User) RequestUtil.getAttribute(CommonConstant.SESSION_ATTRIBUTE_USER);
        Integer id = contestContestantService.register(contestId, user.getUsername());
        return new AjaxResult<>(ErrorCodeEnum.SUCCESS.getMsg(), id);
    }

    /**
     * 提交代码
     *
     * @param contestId      比赛id
     * @param submissionJson 提交记录json
     * @return 提交记录的id
     */
    @RequirePrivilege(privilegeEnum = {PrivilegeEnum.SIGN_IN})
    @ResponseBody
    @RequestMapping(value = "submit", method = {RequestMethod.POST})
    public AjaxResult<Integer> submit(Integer contestId, String submissionJson) {
        User user = (User) RequestUtil.getAttribute(CommonConstant.SESSION_ATTRIBUTE_USER);
        Submission submission = JsonUtil.jsonToObject(submissionJson, Submission.class);
        Objects.requireNonNull(submission).setUsername(user.getUsername());

        Integer id = contestSubmissionService.submit(contestId, submission);
        return new AjaxResult<>(ErrorCodeEnum.SUCCESS.getMsg(), id);
    }

    /**
     * 获取比赛题目页信息
     *
     * @param contestId 题目id
     * @return 比赛题目也信息
     */
    @RequirePrivilege
    @ResponseBody
    @RequestMapping(value = "get", method = {RequestMethod.GET})
    public AjaxResult<Map<String, Object>> get(Integer contestId) {
        Contest contest = contestService.getContestById(contestId);
        List<Integer> ids = JsonUtil.jsonToList(contest.getProblemIds(), Integer.class);
        List<Problem> problems = problemService.listProblemByIds(ids);
        Map<String, Object> result = new HashMap<>(4);
        result.put("contest", contest);
        result.put("problems", problems);
        return new AjaxResult<>(ErrorCodeEnum.SUCCESS.getMsg(), result);
    }

    /**
     * 分页获取比赛列表
     *
     * @param queryParamJson 查询条件 的 json串
     * @return 题目列表分页封装
     */
    @RequirePrivilege
    @ResponseBody
    @RequestMapping(value = "list", method = {RequestMethod.GET})
    public AjaxResult<Pagination<Contest>> list(String queryParamJson) {
        QueryParam queryParam = JsonUtil.jsonToObject(queryParamJson, QueryParam.class);
        Pagination<Contest> result = contestService.pageContestByQueryParam(queryParam);
        return new AjaxResult<>(ErrorCodeEnum.SUCCESS.getMsg(), result);
    }
}
