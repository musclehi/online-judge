package cn.idealismxxm.onlinejudge.service.impl;

import cn.idealismxxm.onlinejudge.dao.SubmissionDao;
import cn.idealismxxm.onlinejudge.domain.entity.Problem;
import cn.idealismxxm.onlinejudge.domain.entity.Submission;
import cn.idealismxxm.onlinejudge.domain.enums.ActiveMQQueueEnum;
import cn.idealismxxm.onlinejudge.domain.enums.ErrorCodeEnum;
import cn.idealismxxm.onlinejudge.domain.enums.ResultEnum;
import cn.idealismxxm.onlinejudge.domain.exception.BusinessException;
import cn.idealismxxm.onlinejudge.domain.util.JsonUtil;
import cn.idealismxxm.onlinejudge.service.ProblemService;
import cn.idealismxxm.onlinejudge.service.SubmissionService;
import cn.idealismxxm.onlinejudge.service.jms.producer.MessageProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 提交记录相关操作接口实现
 *
 * @author idealism
 * @date 2018/3/30
 */
@Service("submissionService")
public class SubmissionServiceImpl implements SubmissionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubmissionServiceImpl.class);

    @Resource
    private SubmissionDao submissionDao;

    @Resource
    private ProblemService problemService;

    @Resource
    private MessageProducer messageProducer;

    @Override
    public Submission getSubmissionById(Integer submissionId) {
        // 参数校验
        if (submissionId == null || submissionId <= 0) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.ILLEGAL_ARGUMENT);
        }

        // 读库
        Submission submission;
        try {
            submission = submissionDao.selectSubmissionById(submissionId);
        } catch (Exception e) {
            LOGGER.error("#getSubmissionById error, submissionId: {}", submissionId, e);
            throw BusinessException.buildBusinessException(ErrorCodeEnum.DAO_CALL_ERROR);
        }

        // 验证数据是否存在
        if (submission == null) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.SUBMISSION_NOT_EXIST);
        }
        return submission;
    }

    @Override
    public Integer submit(Submission submission) {
        if (submission == null || submission.getProblemId() == null) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.ILLEGAL_ARGUMENT);
        }

        // 获取题目信息
        Problem problem = problemService.getProblemById(submission.getProblemId());
        try {
            // 未判题时没有其他OJ提交帐号，默认设为 -1
            submission.setRemoteAccountId(-1);
            // 未判题时没有其他OJ提交记录，默认设为 -1
            submission.setRemoteSubmissionId(-1);
            // 未判题时没有执行时间，默认设为 -1
            submission.setTime(-1);
            // 未判题时没有使用空间，默认设为 -1
            submission.setMemory(-1);
            submission.setResult(ResultEnum.QUEUING.getCode());

            // 数据入库
            submissionDao.insertSubmission(submission);

            // 发送 mq 给判题端
            messageProducer.sendTextMessage(ActiveMQQueueEnum.SUBMISSION_SUBMIT_QUEUE.getName(), submission.getId().toString());

            return submission.getId();
        } catch (Exception e) {
            LOGGER.error("#submit error, submission: {}", JsonUtil.objectToJson(submission), e);
            throw BusinessException.buildBusinessException(ErrorCodeEnum.DAO_CALL_ERROR);
        }
    }
}