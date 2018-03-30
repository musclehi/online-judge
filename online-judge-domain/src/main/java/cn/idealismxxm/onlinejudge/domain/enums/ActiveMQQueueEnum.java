package cn.idealismxxm.onlinejudge.domain.enums;

/**
 * @author idealism
 * @date 2018/3/30
 */
public enum ActiveMQQueueEnum {
    SUBMISSION_SUBMIT_QUEUE("submissionSubmitQueue"),
    ;

    /**
     * amq队列 名称
     */
    private String name;

    ActiveMQQueueEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
