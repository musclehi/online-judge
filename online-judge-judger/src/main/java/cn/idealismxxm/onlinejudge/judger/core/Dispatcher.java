package cn.idealismxxm.onlinejudge.judger.core;

import cn.idealismxxm.onlinejudge.domain.entity.Problem;
import cn.idealismxxm.onlinejudge.domain.entity.Submission;
import cn.idealismxxm.onlinejudge.domain.enums.CommonConstant;
import cn.idealismxxm.onlinejudge.domain.enums.ResultEnum;
import cn.idealismxxm.onlinejudge.domain.exception.BusinessException;
import cn.idealismxxm.onlinejudge.domain.util.JsonUtil;
import cn.idealismxxm.onlinejudge.service.ProblemService;
import cn.idealismxxm.onlinejudge.service.SubmissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

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

    @Resource
    private EventHandler eventHandler;

    @Resource
    private Preprocessor preprocessor;

    @Resource
    private Compiler compiler;

    @Resource
    private Executor executor;

    @Resource
    private Analyzer analyzer;

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

    @Value("${judger.testCaseOutputFileNameSuffix}")
    private String testCaseOutputFileNameSuffix;

    @Value("${judger.testCaseUserOutputFileNameSuffix}")
    private String testCaseUserOutputFileNameSuffix;

    /**
     * 开始评测
     *
     * @param submission 提交记录
     */
    public void startJudge(Submission submission) {
        try {
            String workspacePath = basePath + "/" + workspacePrefix + submission.getId();

            // 1. 预处理
            Integer testCaseNum = preprocessor.doPreprocess(workspacePath, submission);

            // 2. 编译
            Integer compilationResult = compiler.doCompile(submission.getLanguage(), workspacePath);
            eventHandler.onCompileFinished(submission, compilationResult, workspacePath);
            // 编译失败，则结束本次评测
            if (!CommonConstant.COMPILATION_SUCCESS.equals(compilationResult)) {
                return;
            }

            // 获取题目信息
            Problem problem = problemService.getProblemById(submission.getProblemId());

            int result = ResultEnum.ACCEPTED.getCode();
            for (int i = 0; i < testCaseNum && !ResultEnum.WRONG_ANSWER.getCode().equals(result); ++i) {
                // 获取当前测试数据名
                String testCaseFileName = this.testCaseFileNamePrefix + i;

                // 3. 运行
                executor.doExecute(submission.getLanguage(), workspacePath, testCaseFileName, problem.getTimeLimit(), problem.getMemoryLimit());

                // 4. 结果分析
                String outputFilePath = workspacePath + "/" + testCaseFileName + this.testCaseOutputFileNameSuffix;
                String userOutputFilePath = workspacePath + "/" + testCaseFileName + this.testCaseUserOutputFileNameSuffix;
                result = analyzer.doAnalyze(outputFilePath, userOutputFilePath);
            }
            this.modifySubmissionResult(submission.getId(), result);
        } catch (Exception e) {
            LOGGER.error("#startJudge error, submission: {}", JsonUtil.objectToJson(submission), e);
            // 更新该提交记录的结果
            try {
                this.modifySubmissionResult(submission.getId(), ResultEnum.SYSTEM_ERROR.getCode());
            } catch (BusinessException be) {
                LOGGER.error("#modifySubmissionResult error, submissionId: {}, result: {}", submission.getId(), ResultEnum.SYSTEM_ERROR.getCode(), be);
            }
        }
    }

    private void modifySubmissionResult(Integer submissionId, Integer result) {
        Submission newSubmission = new Submission();
        newSubmission.setId(submissionId);
        newSubmission.setResult(result);

        submissionService.modifySubmission(newSubmission);
    }
}
