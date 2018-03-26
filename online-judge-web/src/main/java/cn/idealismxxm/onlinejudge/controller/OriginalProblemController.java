package cn.idealismxxm.onlinejudge.controller;

import cn.idealismxxm.onlinejudge.enums.ResultCodeEnum;
import cn.idealismxxm.onlinejudge.entity.OriginalProblem;
import cn.idealismxxm.onlinejudge.service.OriginalProblemService;
import cn.idealismxxm.onlinejudge.util.AjaxResult;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * 原创题目相关操作
 *
 * @author idealism
 * @date 2018/3/23
 */
@RequestMapping("originalProblem")
public class OriginalProblemController {

    @Resource
    private OriginalProblemService originalProblemService;

    /**
     * 添加原创题目
     *
     * @return 原创题目的id
     */
    @RequestMapping("add")
    public AjaxResult<Integer> add(String originalProblemJson) {
        OriginalProblem originalProblem = null;
        originalProblemService.insertOriginalProblem(originalProblem);
        return new AjaxResult<Integer>(ResultCodeEnum.SUCCESS, 1);
    }
}
