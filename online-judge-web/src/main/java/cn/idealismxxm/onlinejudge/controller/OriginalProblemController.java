package cn.idealismxxm.onlinejudge.controller;

import cn.idealismxxm.onlinejudge.enums.ErrorCodeEnum;
import cn.idealismxxm.onlinejudge.entity.OriginalProblem;
import cn.idealismxxm.onlinejudge.service.OriginalProblemService;
import cn.idealismxxm.onlinejudge.util.AjaxResult;
import cn.idealismxxm.onlinejudge.util.JsonUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 原创题目相关操作
 *
 * @author idealism
 * @date 2018/3/23
 */
@Controller
@RequestMapping("originalProblem")
public class OriginalProblemController {

    @Resource
    private OriginalProblemService originalProblemService;

    /**
     * 添加原创题目（同时会添加题目）
     *
     * @return 原创题目的id
     */
    @ResponseBody
    @RequestMapping(value = "add", method = {RequestMethod.POST})
    public AjaxResult<Integer> add(HttpServletRequest request, String originalProblemJson) {
        OriginalProblem originalProblem = JsonUtil.jsonToObject(originalProblemJson, OriginalProblem.class);
        Integer id = originalProblemService.addOriginalProblem(originalProblem);
        return new AjaxResult<>(ErrorCodeEnum.SUCCESS.getErrorCode(), id);
    }

    /**
     * 编辑原创题目（同时会编辑题目）
     *
     * @return true / false
     */
    @ResponseBody
    @RequestMapping(value = "edit", method = {RequestMethod.POST})
    public AjaxResult<Boolean> edit(String originalProblemJson) {
        OriginalProblem originalProblem = JsonUtil.jsonToObject(originalProblemJson, OriginalProblem.class);
        Boolean result = originalProblemService.editOriginalProblemById(originalProblem);
        return new AjaxResult<>(ErrorCodeEnum.SUCCESS.getErrorCode(), result);
    }
}
