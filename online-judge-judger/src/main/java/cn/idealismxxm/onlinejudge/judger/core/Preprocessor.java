package cn.idealismxxm.onlinejudge.judger.core;

import cn.idealismxxm.onlinejudge.domain.entity.Submission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

/**
 * 预处理器
 * 做一些评测前需要的准备工作
 *
 * @author idealism
 * @date 2018/3/31
 */
public class Preprocessor {

    private static Logger LOGGER = LoggerFactory.getLogger(Preprocessor.class);

    @Value("${judger.basePath}")
    private String basePath;

    public void doPreprocess (Submission submission) {
        // 1. 保存代码

        // 2. 保存测试用例
    }
}
