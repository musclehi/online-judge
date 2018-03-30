package cn.idealismxxm.onlinejudge.web.controller;

import cn.idealismxxm.onlinejudge.domain.entity.Description;
import cn.idealismxxm.onlinejudge.domain.entity.Problem;
import cn.idealismxxm.onlinejudge.domain.enums.ErrorCodeEnum;
import cn.idealismxxm.onlinejudge.service.ProblemService;
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
@RequestMapping("problem")
public class ProblemController {
    @Resource
    private ProblemService problemService;

    /**
     * 添加题目
     *
     * @param problemJson     题目信息json（题目原始OJ 和 题目原始题号 不为空）
     * @param descriptionJson 题目描述json（原创题目不为空）
     * @return 题目的id
     */
    @ResponseBody
    @RequestMapping(value = "add", method = {RequestMethod.POST})
    public AjaxResult<Integer> add(String problemJson, String descriptionJson) {
        Problem problem = JsonUtil.jsonToObject(problemJson, Problem.class);
        Description description = JsonUtil.jsonToObject(descriptionJson, Description.class);
        Integer id = problemService.addProblem(problem, description);
        return new AjaxResult<>(ErrorCodeEnum.SUCCESS.getMsg(), id);
    }

    /**
     * 编辑题目
     *
     * @param problemJson     题目信息json（主键、题目原始OJ 和 题目原始题号 不为空）
     * @param descriptionJson 题目描述json（原创题目不为空）
     * @return true / false
     */
    @ResponseBody
    @RequestMapping(value = "edit", method = {RequestMethod.POST})
    public AjaxResult<Boolean> edit(String problemJson, String descriptionJson) {
        Problem problem = JsonUtil.jsonToObject(problemJson, Problem.class);
        Description description = JsonUtil.jsonToObject(descriptionJson, Description.class);
        Boolean result = problemService.editProblem(problem, description);
        return new AjaxResult<>(ErrorCodeEnum.SUCCESS.getErrorCode(), result);
    }
}
