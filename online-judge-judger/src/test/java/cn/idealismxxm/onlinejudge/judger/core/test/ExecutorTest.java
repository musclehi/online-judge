package cn.idealismxxm.onlinejudge.judger.core.test;

import cn.idealismxxm.onlinejudge.domain.entity.Problem;
import cn.idealismxxm.onlinejudge.domain.entity.Submission;
import cn.idealismxxm.onlinejudge.judger.core.Executor;
import cn.idealismxxm.onlinejudge.service.ProblemService;
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
 * Executor 测试类
 *
 * @author idealism
 * @date 2018/4/13
 */
@RunWith(SpringJUnit4ClassRunner.class)
// 必须加载 mvc 配置文件，否则无法正常处理请求
@ContextConfiguration("classpath:spring/spring-config.xml")
// 所有对数据库的增删改都会回滚，防止产生脏数据，便于重复测试
@Rollback
@Transactional
public class ExecutorTest {

    @Resource
    private Executor executor;

    @Resource
    private SubmissionService submissionService;

    @Resource
    private ProblemService problemService;

    @Value("${judger.basePath}")
    private String basePath;

    @Value("${judger.workspacePrefix}")
    private String workspacePrefix;

    @Value("${judger.testCaseFileNamePrefix}")
    private String testCaseFileNamePrefix;

    @Test
    public void doExecutorTest() {
        Integer submissionId = 1;
        Submission submission = submissionService.getSubmissionById(submissionId);
        Problem problem = problemService.getProblemById(submission.getProblemId());
        String workspacePath = basePath + "/" + workspacePrefix + submission.getId();
        String testCaseFileName = this.testCaseFileNamePrefix + 0;
        System.out.println("doExecute result: " + executor.doExecute(submission.getLanguage(), workspacePath, testCaseFileName, problem.getTimeLimit(), problem.getMemoryLimit()));
    }
}
