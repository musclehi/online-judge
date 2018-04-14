package cn.idealismxxm.onlinejudge.judger.core;

import cn.idealismxxm.onlinejudge.domain.entity.Submission;
import cn.idealismxxm.onlinejudge.domain.enums.CommonConstant;
import cn.idealismxxm.onlinejudge.domain.enums.ExtensionKeyEnum;
import cn.idealismxxm.onlinejudge.domain.enums.ResultEnum;
import cn.idealismxxm.onlinejudge.domain.exception.BusinessException;
import cn.idealismxxm.onlinejudge.domain.util.JsonUtil;
import cn.idealismxxm.onlinejudge.service.SubmissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

/**
 * 事件处理器
 * 对评测整个过程的各事件进行处理
 *
 * @author idealism
 * @date 2018/3/31
 */
@Component("eventHandler")
public class EventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventHandler.class);

    @Resource
    private Dispatcher dispatcher;

    @Resource
    private SubmissionService submissionService;

    @Value("${judger.compilationInfoFileName}")
    private String compilationInfoFileName;

    /**
     * 处理提交的代码
     *
     * @param submission 提交记录
     */
    public void onSubmissionSubmitted(Submission submission) {
        try {
            dispatcher.startJudge(submission);
        } catch (BusinessException e) {
            LOGGER.error("#onSubmissionSubmitted error, submission: {}", JsonUtil.objectToJson(submission), e);
            throw e;
        }
    }

    /**
     * 编译完成时
     *
     * @param submission      提交记录
     * @param compilationResult 编译结果
     * @param workspacePath     工作空间路径
     */
    public void onCompileFinished(Submission submission, Integer compilationResult, String workspacePath) {
        try {
            // 编译失败，则更新数据库
            if (!CommonConstant.COMPILATION_SUCCESS.equals(compilationResult)) {
                // 得到编译信息
                String compilationInfoFilePath = workspacePath + "/" + this.compilationInfoFileName;
                String compilationInfo = this.readString(compilationInfoFilePath);

                // 初始化提交记录需更新的字段
                Submission newSubmission = new Submission();
                newSubmission.setId(newSubmission.getId());
                newSubmission.setResult(ResultEnum.COMPILATION_ERROR.getCode());

                Map<String, String> extension= JsonUtil.jsonToMap(submission.getExtension(), String.class, String.class);
                extension.put(ExtensionKeyEnum.COMPILATION_INFO.getKey(), compilationInfo);
                newSubmission.setExtension(JsonUtil.objectToJson(extension));

                // 更新数据库
                submissionService.modifySubmission(newSubmission);
            }
            // TODO 编译成功以后可以直接通知用户
        } catch (Exception e) {
            LOGGER.error("#onCompileFinished error, submission: {}, compilationResult: {}, workspacePath: {}", JsonUtil.objectToJson(submission), compilationResult, workspacePath, e);
        }
    }

    /**
     * 读取文件的所有内容
     *
     * @param filePath 文件路径
     * @return 文件内的内容
     */
    private String readString(String filePath) {
        File file = new File(filePath);
        Long fileLength = file.length();
        byte[] content = new byte[fileLength.intValue()];
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            fileInputStream.read(content);
        } catch (Exception e) {
            LOGGER.error("#readString error, filePath: {}", filePath, e);
        }
        return new String(content);
    }
}
