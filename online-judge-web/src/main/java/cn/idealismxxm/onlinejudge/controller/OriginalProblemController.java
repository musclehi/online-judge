package cn.idealismxxm.onlinejudge.controller;

import cn.idealismxxm.onlinejudge.util.AjaxResult;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 原创题目相关操作
 *
 * @author idealism
 * @date 2018/3/23
 */
@RequestMapping("originalProblem")
public class OriginalProblemController {

    /**
     * 添加原创题目
     *
     * @return 原创题目的id
     */
    @RequestMapping("add")
    public AjaxResult<Integer> add() {
        return new AjaxResult<>("", "success", 1);
    }
}
