package cn.idealismxxm.onlinejudge.judger.core.test;

import cn.idealismxxm.onlinejudge.domain.enums.ResultEnum;
import cn.idealismxxm.onlinejudge.judger.core.Analyzer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Analyzer 测试类
 *
 * @author idealism
 * @date 2018/4/15
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-config.xml")
// 所有对数据库的增删改都会回滚，防止产生脏数据，便于重复测试
@Rollback
@Transactional
public class AnalyzerTest {

    @Resource
    private Analyzer analyzer;

    @Value("${judger.basePath}")
    private String basePath;

    @Value("${judger.workspacePrefix}")
    private String workspacePrefix;

    @Value("${judger.testCaseFileNamePrefix}")
    private String testCaseFileNamePrefix;

    @Value("${judger.testCaseUserOutputFileNameSuffix}")
    private String testCaseUserOutputFileNameSuffix;

    @Value("${judger.testCaseOutputFileNameSuffix}")
    private String testCaseOutputFileNameSuffix;

    @Test
    public void doAnalyzeTest() {
        Integer submissionId = 1;
        String workspacePath = this.basePath + "/" + this.workspacePrefix + submissionId;
        Integer testCaseNo = 0;
        String outputFilePath = workspacePath + "/" + this.testCaseFileNamePrefix + testCaseNo + this.testCaseOutputFileNameSuffix;
        String userOutputFilePath = workspacePath + "/" + this.testCaseFileNamePrefix + testCaseNo + this.testCaseUserOutputFileNameSuffix;
        Integer result = analyzer.doAnalyze(outputFilePath, userOutputFilePath);
        System.out.println("doAnalyze result: " + ResultEnum.getResultEnumByCode(result));
    }
}
