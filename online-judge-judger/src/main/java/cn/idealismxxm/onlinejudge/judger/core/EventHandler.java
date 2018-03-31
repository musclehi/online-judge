package cn.idealismxxm.onlinejudge.judger.core;

import cn.idealismxxm.onlinejudge.domain.entity.Submission;
import org.springframework.stereotype.Component;

/**
 * 事件处理器
 * 对评测整个过程的各事件进行处理
 *
 * @author idealism
 * @date 2018/3/31
 */
@Component("eventHandler")
public class EventHandler {
    /**
     * 处理提交的代码
     *
     * @param submission 提交记录
     */
    public void onSubmissionSubmitted(Submission submission) {

    }
}
