package cn.idealismxxm.onlinejudge.judger.core.test;

import cn.idealismxxm.onlinejudge.domain.entity.Submission;
import cn.idealismxxm.onlinejudge.judger.core.Preprocessor;
import cn.idealismxxm.onlinejudge.service.SubmissionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Preprocessor 测试类
 *
 * @author idealism
 * @date 2018/4/3
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-config.xml"})
// 所有对数据库的增删改都会回滚，防止产生脏数据，便于重复测试
@Rollback
@Transactional
public class PreprocessorTest {

    @Resource
    private Preprocessor preprocessor;

    @Resource
    private SubmissionService submissionService;

    @Value("${judger.basePath}")
    private String basePath;

    @Value("${judger.workspacePrefix}")
    private String workspacePrefix;

    @Test
    public void doPreprocessTest() {
        Integer submissionId = 1;
        Submission submission = submissionService.getSubmissionById(submissionId);
        String workspacePath = basePath + "/" + workspacePrefix + submission.getId();
        preprocessor.doPreprocess(workspacePath, submission);
    }
}
