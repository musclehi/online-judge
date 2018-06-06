package cn.idealismxxm.onlinejudge.service.impl;

import cn.idealismxxm.onlinejudge.dao.ContestSubmissionDao;
import cn.idealismxxm.onlinejudge.domain.entity.ContestSubmission;
import cn.idealismxxm.onlinejudge.domain.entity.Submission;
import cn.idealismxxm.onlinejudge.domain.enums.ErrorCodeEnum;
import cn.idealismxxm.onlinejudge.domain.exception.BusinessException;
import cn.idealismxxm.onlinejudge.domain.util.JsonUtil;
import cn.idealismxxm.onlinejudge.service.ContestSubmissionService;
import cn.idealismxxm.onlinejudge.service.SubmissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 比赛提交记录相关操作接口实现
 *
 * @author idealism
 * @date 2018/4/25
 */
@Service("contestSubmissionService")
public class ContestSubmissionServiceImpl implements ContestSubmissionService {

    public static final Logger LOGGER = LoggerFactory.getLogger(ContestSubmissionServiceImpl.class);

    @Resource
    private SubmissionService submissionService;

    @Resource
    private ContestSubmissionDao contestSubmissionDao;

    @Override
    public Integer submit(Integer contestId, Submission submission, String username) {
        // 1. 参数校验
        if (contestId == null || contestId <= 0) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.ILLEGAL_ARGUMENT);
        }

        // 2. 提交记录入库
        Integer submissionId = submissionService.submit(submission, username);

        // 3. 比赛提交记录入库
        try {
            ContestSubmission contestSubmission = new ContestSubmission();
            contestSubmission.setContestId(contestId);
            contestSubmission.setProblemId(submission.getProblemId());
            contestSubmission.setSubmissionId(submissionId);

            contestSubmissionDao.insertContestSubmission(contestSubmission);
        } catch (Exception e) {
            LOGGER.error("#submit error, contestId: {}, submission: {}", contestId, JsonUtil.objectToJson(submission), e);
            throw BusinessException.buildBusinessException(ErrorCodeEnum.DAO_CALL_ERROR, e);
        }
        return submissionId;
    }

    @Override
    public List<Integer> listSubmissionIdByContestId(Integer contestId) {
        if (contestId == null || contestId <= 0) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.ILLEGAL_ARGUMENT);
        }

        try {
            return contestSubmissionDao.listSubmissionIdByContestId(contestId);
        } catch (Exception e) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.DAO_CALL_ERROR, e);
        }
    }
}