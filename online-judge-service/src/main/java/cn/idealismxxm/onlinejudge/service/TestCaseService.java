package cn.idealismxxm.onlinejudge.service;

import cn.idealismxxm.onlinejudge.domain.entity.TestCase;

import java.util.List;

/**
 * 测试用例相关操作接口
 *
 * @author idealism
 * @date 2018/3/31
 */
public interface TestCaseService {

    /**
     * 返回 problemId 下的所有测试用例
     *
     * @param problemId 题目id
     * @return 测试用例列表
     */
    List<TestCase> listTestCaseByProblemId(Integer problemId);
}