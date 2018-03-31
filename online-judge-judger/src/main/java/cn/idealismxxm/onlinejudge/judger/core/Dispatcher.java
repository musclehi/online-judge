package cn.idealismxxm.onlinejudge.judger.core;

import cn.idealismxxm.onlinejudge.domain.entity.Submission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 调度器
 * 调度评测相关操作，控制评测流程
 *
 * @author idealism
 * @date 2018/3/31
 */
@Component("dispatcher")
public class Dispatcher {

    private static Logger LOGGER = LoggerFactory.getLogger(Dispatcher.class);



    /**
     * 为编译做好准备工作
     *
     * @param submission 提交记录
     */
    private void prepare(Submission submission) {

    }
}
