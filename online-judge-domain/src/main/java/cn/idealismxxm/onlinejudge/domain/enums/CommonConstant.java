package cn.idealismxxm.onlinejudge.domain.enums;

/**
 * 常量类
 *
 * @author idealism
 * @date 2018/4/11
 */
public interface CommonConstant {
    /**
     * 编译成功
     */
    Integer COMPILATION_SUCCESS = 0;

    /**
     * session 中存储的 attribute 的 键
     */
    String SESSION_ATTRIBUTE_USER = "user";

    /**
     * 比赛开始时间至少比当前时间晚：5min（单位：ms）
     */
    Long CONTEST_MIN_START_INTERVAL = 1000 * 60 * 5L;

    /**
     * 比赛最短持续时间为：1小时（单位：ms）
     */
    Long CONTEST_MIN_DURATION = 1000 * 60 * 60L;
}
