package cn.idealismxxm.onlinejudge.service.jms.producer;

import cn.idealismxxm.onlinejudge.domain.enums.ErrorCodeEnum;
import cn.idealismxxm.onlinejudge.domain.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 消息生产者
 *
 * @author idealism
 * @date 2018/3/30
 */
@Component
public class MessageProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageProducer.class);

    @Resource
    private JmsTemplate jmsTemplate;

    public void sendTextMessage(String destinationName, String text) {
        try {
            jmsTemplate.send(destinationName, session -> session.createTextMessage(text));
        } catch (Exception e) {
            LOGGER.error("#sendMessage error, message: {}", text, e);
            throw BusinessException.buildBusinessException(ErrorCodeEnum.MESSAGE_PRODUCE_ERROR);
        }
    }
}
