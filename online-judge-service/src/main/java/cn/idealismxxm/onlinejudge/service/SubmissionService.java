package cn.idealismxxm.onlinejudge.service;

import cn.idealismxxm.onlinejudge.domain.entity.Submission;
import cn.idealismxxm.onlinejudge.domain.util.Pagination;
import cn.idealismxxm.onlinejudge.domain.util.QueryParam;

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
     * @param username   提交者用户名
     * @return 提交记录的id
     */
    Integer submit(Submission submission, String username);

    /**
     * 更新提交记录
     *
     * @param submission 提交记录
     * @return true / false
     */
    Boolean modifySubmission(Submission submission);

    /**
     * 分页获取提交记录列表
     *
     * @param queryParam 查询条件
     * @return 提交列表分页封装
     */
    Pagination<Submission> pageSubmissionByQueryParam(QueryParam queryParam);
}