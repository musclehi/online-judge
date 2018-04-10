package cn.idealismxxm.onlinejudge.judger.core.test;

import cn.idealismxxm.onlinejudge.domain.entity.Description;
import cn.idealismxxm.onlinejudge.domain.entity.Problem;
import cn.idealismxxm.onlinejudge.domain.entity.Submission;
import cn.idealismxxm.onlinejudge.domain.entity.TestCase;
import cn.idealismxxm.onlinejudge.domain.enums.OnlineJudgeEnum;
import cn.idealismxxm.onlinejudge.domain.enums.PublicStatusEnum;
import cn.idealismxxm.onlinejudge.domain.util.JsonUtil;
import cn.idealismxxm.onlinejudge.judger.core.Preprocessor;
import cn.idealismxxm.onlinejudge.service.SubmissionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Preprocessor 测试类
 *
 * @author idealism
 * @date 2018/4/3
 */
@RunWith(SpringJUnit4ClassRunner.class)
// 必须加载 mvc 配置文件，否则无法正常处理请求
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
