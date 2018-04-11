package cn.idealismxxm.onlinejudge.judger.core;

import cn.idealismxxm.onlinejudge.domain.entity.Submission;
import cn.idealismxxm.onlinejudge.domain.enums.CommonConstant;
import cn.idealismxxm.onlinejudge.domain.enums.ResultEnum;
import cn.idealismxxm.onlinejudge.domain.util.JsonUtil;
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
    private SubmissionService submissionService;

    @Resource
    private Compiler compiler;

    @Value("${judger.basePath}")
    private String basePath;

    @Value("${judger.workspacePrefix}")
    private String workspacePrefix;

    /**
     * 开始评测
     *
     * @param submission 提交记录
     */
    public void startJudge(Submission submission) {
        try {
            String workspacePath = basePath + "/" + workspacePrefix + submission.getId();

            // 1. 预处理
            preprocessor.doPreprocess(workspacePath, submission);

            // 2. 编译
            Integer compilationResult = compiler.doCompile(submission.getLanguage(), workspacePath);
            eventHandler.onCompileFinished(submission, compilationResult, workspacePath);
            // 编译失败，则结束本次评测
            if (!CommonConstant.COMPILATION_SUCCESS.equals(compilationResult)) {
                return;
            }

            // 3. 运行

            // 4. 结果分析
        } catch (Exception e) {
            LOGGER.error("#startJudge error, submission: {}", JsonUtil.objectToJson(submission), e);
            // 更新该提交记录的结果
            Submission newSubmission = new Submission();
            newSubmission.setId(submission.getId());
            newSubmission.setResult(ResultEnum.SYSTEM_ERROR.getCode());

            submissionService.modifySubmission(newSubmission);
        }
    }
}
