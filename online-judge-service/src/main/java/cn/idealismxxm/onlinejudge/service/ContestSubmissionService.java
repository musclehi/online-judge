package cn.idealismxxm.onlinejudge.service;

import cn.idealismxxm.onlinejudge.domain.entity.Submission;

/**
 * 比赛提交记录相关操作接口
 *
 * @author idealism
 * @date 2018/4/25
 */
public interface ContestSubmissionService {

    /**
     * 提交比赛代码
     *
     * @param contestId  比赛id
     * @param submission 提交记录
     * @param username   提交者用户名
     * @return 提交记录的id
     */
    Integer submit(Integer contestId, Submission submission, String username);
}