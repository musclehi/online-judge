package cn.idealismxxm.onlinejudge.service;

import cn.idealismxxm.onlinejudge.entity.OriginalProblem;

public interface OriginalProblemService {
    /**
     * 添加原创题目（同时会添加题目）
     *
     * @return 原创题目的id
     */
    Integer addOriginalProblem(OriginalProblem originalProblem);

    /**
     * 编辑原创题目（同时会编辑题目）
     *
     * @return true / false
     */
    Boolean editOriginalProblemById(OriginalProblem originalProblem);

}