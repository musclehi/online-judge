package cn.idealismxxm.onlinejudge.judger.core;

import cn.idealismxxm.onlinejudge.domain.entity.Submission;
import cn.idealismxxm.onlinejudge.domain.entity.TestCase;
import cn.idealismxxm.onlinejudge.domain.enums.ErrorCodeEnum;
import cn.idealismxxm.onlinejudge.domain.enums.LanguageEnum;
import cn.idealismxxm.onlinejudge.domain.exception.BusinessException;
import cn.idealismxxm.onlinejudge.service.TestCaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Objects;

/**
 * 预处理器
 * 做一些评测前需要的准备工作
 *
 * @author idealism
 * @date 2018/3/31
 */
@Component("preprocessor")
public class Preprocessor {

    private static Logger LOGGER = LoggerFactory.getLogger(Preprocessor.class);

    @Resource
    private TestCaseService testCaseService;

    @Value("${judger.sourceFileName}")
    private String sourceFileName;

    @Value("${judger.testCaseFileNamePrefix}")
    private String testCaseFileNamePrefix;

    @Value("${judger.testCaseInputFileNameSuffix}")
    private String testCaseInputFileNameSuffix;

    @Value("${judger.testCaseOutputFileNameSuffix}")
    private String testCaseOutputFileNameSuffix;

    /**
     * 评测前的预处理
     *
     * @param workspacePath 评测目录路径
     * @param submission    提交记录
     */
    public void doPreprocess(String workspacePath, Submission submission) {
        // 1. 创建评测目录
        this.createWorkspace(workspacePath);

        // 2. 创建代码文件
        this.createSourceFile(workspacePath, submission.getLanguage(), submission.getSource());

        // 3. 创建测试用例文件
        this.createTestCaseFile(workspacePath, submission.getProblemId());
    }

    /**
     * 创建评测目录
     *
     * @param workspacePath 评测目录路径
     * @return 创建成功的评测目录
     */
    private void createWorkspace(String workspacePath) {
        File workspace = new File(workspacePath);

        // 如果文件夹不存在且未创建成功，则抛出异常
        if (!workspace.exists() && !workspace.mkdirs()) {
            throw BusinessException.buildBusinessException(ErrorCodeEnum.FILE_CREATE_ERROR);
        }
    }

    /**
     * 创建代码文件
     *
     * @param workspacePath 本次评测路径
     * @param language      源代码语言
     * @param source        源代码
     */
    private void createSourceFile(String workspacePath, Integer language, String source) {
        String suffix = Objects.requireNonNull(LanguageEnum.getLanguageEnumByCode(language)).getSuffix();
        String sourceFilePath = workspacePath + "/" + this.sourceFileName + suffix;

        try (FileOutputStream sourceStream = new FileOutputStream(new File(sourceFilePath))) {
            sourceStream.write(source.getBytes());
        } catch (Exception e) {
            LOGGER.error("#createSourceFile error, workspacePath: {}, language: {}, source: {}", workspacePath, language, source);
            throw BusinessException.buildBusinessException(ErrorCodeEnum.FILE_WRITE_ERROR, e);
        }
    }

    /**
     * 创建测试用例文件
     *
     * @param workspacePath 本次评测路径
     * @param problemId     题目id
     */
    private void createTestCaseFile(String workspacePath, Integer problemId) {
        // 获取测试用例
        List<TestCase> testCases = testCaseService.listTestCaseByProblemId(problemId);
        int index = 0;
        try {
            for (TestCase testCase : testCases) {
                // 创建测试用例输入文件
                String testCaseInputFilePath = workspacePath + "/" + this.testCaseFileNamePrefix + index + this.testCaseInputFileNameSuffix;
                FileOutputStream testCaseInputStream = new FileOutputStream(new File(testCaseInputFilePath));
                testCaseInputStream.write(testCase.getInput().getBytes());
                testCaseInputStream.close();

                // 创建测试用例输出文件
                String testCaseOutputFilePath = workspacePath + "/" + this.testCaseFileNamePrefix + index + this.testCaseInputFileNameSuffix;
                FileOutputStream testCaseOutputStream = new FileOutputStream(new File(testCaseOutputFilePath));
                testCaseOutputStream.write(testCase.getOutput().getBytes());
                testCaseOutputStream.close();

                ++index;
            }
        } catch (Exception e) {
            LOGGER.error("#createTestCaseFile error, workspacePath: {}, problemId: {}", workspacePath, problemId);
            throw BusinessException.buildBusinessException(ErrorCodeEnum.FILE_WRITE_ERROR, e);
        }
    }
}
