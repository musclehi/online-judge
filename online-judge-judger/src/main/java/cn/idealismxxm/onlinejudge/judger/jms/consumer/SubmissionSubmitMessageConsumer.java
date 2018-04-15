package cn.idealismxxm.onlinejudge.judger.jms.consumer;

import cn.idealismxxm.onlinejudge.domain.entity.Problem;
import cn.idealismxxm.onlinejudge.domain.entity.Submission;
import cn.idealismxxm.onlinejudge.domain.enums.ErrorCodeEnum;
import cn.idealismxxm.onlinejudge.domain.enums.OnlineJudgeEnum;
import cn.idealismxxm.onlinejudge.domain.exception.BusinessException;
import cn.idealismxxm.onlinejudge.domain.util.JsonUtil;
import cn.idealismxxm.onlinejudge.judger.core.EventHandler;
import cn.idealismxxm.onlinejudge.service.ProblemService;
import cn.idealismxxm.onlinejudge.service.SubmissionService;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * 代码提交的消息 消费者
 *
 * @author idealism
 * @date 2018/3/30
 */
@Component("submissionSubmitMessageConsumer")
public class SubmissionSubmitMessageConsumer implements MessageListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(SubmissionSubmitMessageConsumer.class);

    @Resource
    private ProblemService problemService;

    @Resource
    private SubmissionService submissionService;

    @Resource
    private EventHandler eventHandler;

    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                // 获取提交记录和题目
                Integer submissionId = NumberUtils.toInt(((TextMessage) message).getText(), -1);
                if (submissionId.equals(-1)) {
                    throw BusinessException.buildBusinessException(ErrorCodeEnum.MESSAGE_CONTENT_NOT_SUPPORT);
                }
                Submission submission = submissionService.getSubmissionById(submissionId);
                Problem problem = problemService.getProblemById(submission.getProblemId());

                // 本平台题目直接进行评测
                if (OnlineJudgeEnum.THIS.getCode().equals(problem.getOriginalOj())) {
                    eventHandler.onSubmissionSubmitted(submission);
                } // 其他平台题目进行远程提交
                else if (OnlineJudgeEnum.getOnlineJudgeEnumByCode(problem.getOriginalOj()) != null) {
                    // TODO remote模块进行远程提交
                } // 不支持的OJ
                else {
                    throw BusinessException.buildBusinessException(ErrorCodeEnum.OJ_NOT_SUPPORT);
                }
            } // 其他消息类型
            else {
                throw BusinessException.buildBusinessException(ErrorCodeEnum.MESSAGE_TYPE_NOT_SUPPORT);
            }
        } catch (BusinessException e) {
            LOGGER.error("#onMessage error, message: {}", JsonUtil.objectToJson(message), e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("#onMessage error, message: {}", JsonUtil.objectToJson(message), e);
            throw BusinessException.buildBusinessException(ErrorCodeEnum.MESSAGE_CONSUME_ERROR, e);
        }
    }
}
