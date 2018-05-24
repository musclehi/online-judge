package cn.idealismxxm.onlinejudge.web.controller;

import cn.idealismxxm.onlinejudge.domain.annotation.RequirePrivilege;
import cn.idealismxxm.onlinejudge.domain.entity.Description;
import cn.idealismxxm.onlinejudge.domain.entity.Problem;
import cn.idealismxxm.onlinejudge.domain.entity.TestCase;
import cn.idealismxxm.onlinejudge.domain.enums.ErrorCodeEnum;
import cn.idealismxxm.onlinejudge.domain.enums.PrivilegeEnum;
import cn.idealismxxm.onlinejudge.domain.util.AjaxResult;
import cn.idealismxxm.onlinejudge.domain.util.JsonUtil;
import cn.idealismxxm.onlinejudge.domain.util.Pagination;
import cn.idealismxxm.onlinejudge.domain.util.QueryParam;
import cn.idealismxxm.onlinejudge.service.DescriptionService;
import cn.idealismxxm.onlinejudge.service.ProblemService;
import cn.idealismxxm.onlinejudge.service.TestCaseService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 题目相关操作
 *
 * @author idealism
 * @date 2018/3/23
 */
@Controller
@RequestMapping("problem")
public class ProblemController {
    @Resource
    private ProblemService problemService;

    @Resource
    private DescriptionService descriptionService;

    @Resource
    private TestCaseService testCaseService;

    /**
     * 添加题目
     *
     * @param problemJson     题目信息json（题目原始OJ 和 题目原始题号 不为空）
     * @param descriptionJson 题目描述json（原创题目不为空）
     * @param testCasesJson   测试用例json（原创题目不为空）
     * @return 题目的id
     */
    @RequirePrivilege(privilegeEnum = {PrivilegeEnum.SIGN_IN, PrivilegeEnum.MANAGE_PROBLEM})
    @ResponseBody
    @RequestMapping(value = "add", method = {RequestMethod.POST})
    public AjaxResult<Integer> add(String problemJson, String descriptionJson, String testCasesJson) {
        Problem problem = JsonUtil.jsonToObject(problemJson, Problem.class);
        Description description = JsonUtil.jsonToObject(descriptionJson, Description.class);
        List<TestCase> testCases = JsonUtil.jsonToList(testCasesJson, TestCase.class);
        Integer id = problemService.addProblem(problem, description, testCases);
        return new AjaxResult<>(ErrorCodeEnum.SUCCESS.getMsg(), id);
    }

    /**
     * 编辑题目
     *
     * @param problemJson     题目信息json（主键、题目原始OJ 和 题目原始题号 不为空）
     * @param descriptionJson 题目描述json（原创题目不为空）
     * @param testCasesJson   测试用例json（原创题目不为空）
     * @return true / false
     */
    @RequirePrivilege(privilegeEnum = {PrivilegeEnum.SIGN_IN, PrivilegeEnum.MANAGE_PROBLEM})
    @ResponseBody
    @RequestMapping(value = "edit", method = {RequestMethod.POST})
    public AjaxResult<Boolean> edit(String problemJson, String descriptionJson, String testCasesJson) {
        Problem problem = JsonUtil.jsonToObject(problemJson, Problem.class);
        Description description = JsonUtil.jsonToObject(descriptionJson, Description.class);
        List<TestCase> testCases = JsonUtil.jsonToList(testCasesJson, TestCase.class);
        Boolean result = problemService.editProblem(problem, description, testCases);
        return new AjaxResult<>(ErrorCodeEnum.SUCCESS.getErrorCode(), result);
    }

    /**
     * 获取题目主要信息
     *
     * @param problemId 题目id
     * @return 题目全部信息
     */
    @RequirePrivilege
    @ResponseBody
    @RequestMapping(value = "get", method = {RequestMethod.GET})
    public AjaxResult<Map<String, Object>> get(Integer problemId) {
        Problem problem = problemService.getProblemById(problemId);
        Description description = descriptionService.getDescriptionById(problem.getDescriptionId());

        Map<String, Object> result = new HashMap<>(4);
        result.put("problem", problem);
        result.put("description", description);
        return new AjaxResult<>(ErrorCodeEnum.SUCCESS.getMsg(), result);
    }

    /**
     * 获取题目全部信息（编辑题目用）
     *
     * @param problemId 题目id
     * @return 题目主要信息
     */
    @RequirePrivilege(privilegeEnum = {PrivilegeEnum.SIGN_IN, PrivilegeEnum.MANAGE_PROBLEM})
    @ResponseBody
    @RequestMapping(value = "getAllInfo", method = {RequestMethod.GET})
    public AjaxResult<Map<String, Object>> getAllInfo(Integer problemId) {
        Problem problem = problemService.getProblemById(problemId);
        Description description = descriptionService.getDescriptionById(problem.getDescriptionId());
        List<TestCase> testCases = testCaseService.listTestCaseByProblemId(problemId);

        Map<String, Object> result = new HashMap<>(4);
        result.put("problem", problem);
        result.put("description", description);
        result.put("testCases", testCases);
        return new AjaxResult<>(ErrorCodeEnum.SUCCESS.getMsg(), result);
    }

    /**
     * 分页获取题目列表
     *
     * @param queryParamJson 查询条件 的 json串
     * @return 题目列表分页封装
     */
    @RequirePrivilege
    @ResponseBody
    @RequestMapping(value = "list", method = {RequestMethod.GET})
    public AjaxResult<Pagination<Problem>> list(String queryParamJson) {
        QueryParam queryParam = JsonUtil.jsonToObject(queryParamJson, QueryParam.class);
        Pagination<Problem> result = problemService.pageProblemByQueryParam(queryParam);
        return new AjaxResult<>(ErrorCodeEnum.SUCCESS.getMsg(), result);
    }
}
