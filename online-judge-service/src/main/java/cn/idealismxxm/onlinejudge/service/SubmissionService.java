package cn.idealismxxm.onlinejudge.service;

import cn.idealismxxm.onlinejudge.domain.entity.Submission;

/**
 * 提交记录相关操作接口
 *
 * @author idealism
 * @date 2018/3/30
 */
public interface SubmissionService {
    /**
     * 通过 提交记录id 返回提交记录实例
     *
     * @param submissionId 提交记录id
     * @return 提交记录实例
     */
    Submission getSubmissionById(Integer submissionId);

    /**
     * 提交代码
     *
     * @param submission 提交记录
     * @return 提交记录的id
     */
    Integer submit(Submission submission);
}