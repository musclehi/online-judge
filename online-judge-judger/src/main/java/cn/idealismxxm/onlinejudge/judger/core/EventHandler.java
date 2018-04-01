package cn.idealismxxm.onlinejudge.judger.core;

import cn.idealismxxm.onlinejudge.domain.entity.Submission;
import cn.idealismxxm.onlinejudge.domain.exception.BusinessException;
import cn.idealismxxm.onlinejudge.domain.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 事件处理器
 * 对评测整个过程的各事件进行处理
 *
 * @author idealism
 * @date 2018/3/31
 */
@Component("eventHandler")
public class EventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventHandler.class);

    @Resource
    private Dispatcher dispatcher;

    /**
     * 处理提交的代码
     *
     * @param submission 提交记录
     */
    public void onSubmissionSubmitted(Submission submission) {
        try {
            dispatcher.startJudge(submission);
        } catch (BusinessException e) {
            LOGGER.error("#onSubmissionSubmitted error, submission: {}", JsonUtil.objectToJson(submission), e);
            throw e;
        }
    }
}
