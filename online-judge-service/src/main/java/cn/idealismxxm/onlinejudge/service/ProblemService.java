package cn.idealismxxm.onlinejudge.service;

import cn.idealismxxm.onlinejudge.domain.entity.Description;
import cn.idealismxxm.onlinejudge.domain.entity.Problem;
import cn.idealismxxm.onlinejudge.domain.entity.TestCase;

import java.util.List;

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
     * @param problem     题目
     * @param description 题目描述
     * @param testCases   测试用例列表
     * @return 主键
     */
    Integer addProblem(Problem problem, Description description, List<TestCase> testCases);

    /**
     * 编辑题目
     *
     * @param problem     题目
     * @param description 题目描述
     * @param testCases   测试用例列表
     * @return true / false
     */
    Boolean editProblem(Problem problem, Description description, List<TestCase> testCases);
}