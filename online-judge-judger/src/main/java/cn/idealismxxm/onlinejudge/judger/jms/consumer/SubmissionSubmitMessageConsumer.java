package cn.idealismxxm.onlinejudge.judger.jms.consumer;

import cn.idealismxxm.onlinejudge.domain.enums.ErrorCodeEnum;
import cn.idealismxxm.onlinejudge.domain.exception.BusinessException;
import cn.idealismxxm.onlinejudge.domain.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * 消息消费者
 *
 * @author idealism
 * @date 2018/3/30
 */
@Component("submissionSubmitMessageConsumer")
public class SubmissionSubmitMessageConsumer implements MessageListener{

    private static final Logger LOGGER = LoggerFactory.getLogger(SubmissionSubmitMessageConsumer.class);

    @Override
    public void onMessage(Message message) {
        try {
            if(message instanceof TextMessage) {
                System.out.println("--------------------" + ((TextMessage) message).getText());
            }
            else {
                throw BusinessException.buildCustomizedMessageException("不支持的消息");
            }
        } catch (BusinessException e) {
            LOGGER.error("#onMessage error, message: {}", JsonUtil.objectToJson(message), e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("#onMessage error, message: {}", JsonUtil.objectToJson(message), e);
            throw BusinessException.buildBusinessException(ErrorCodeEnum.MESSAGE_CONSUME_ERROR);
        }
    }
}
