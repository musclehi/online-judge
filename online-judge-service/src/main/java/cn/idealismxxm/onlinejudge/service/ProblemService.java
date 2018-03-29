package cn.idealismxxm.onlinejudge.service;

import cn.idealismxxm.onlinejudge.entity.Problem;

/**
 * 题目相关操作接口
 *
 * @author idealism
 * @date 2018/3/29
 */
public interface ProblemService {

    /**
     * 通过 题目id 返回题目实例
     *
     * @param problemId 题目id
     * @return 题目实例
     */
    Problem getProblemById(Integer problemId);

    /**
     * 添加题目
     *
     * @param problemJson     题目json
     * @param descriptionJson 题目描述json
     * @return 主键
     */
    Integer addProblem(String problemJson, String descriptionJson);

    /**
     * 编辑题目
     *
     * @param problemJson     题目json
     * @param descriptionJson 题目描述json
     * @return true / false
     */
    Boolean editProblem(String problemJson, String descriptionJson);
}