package cn.idealismxxm.onlinejudge.judger.core;

import cn.idealismxxm.onlinejudge.domain.enums.ErrorCodeEnum;
import cn.idealismxxm.onlinejudge.domain.exception.BusinessException;
import cn.idealismxxm.onlinejudge.domain.util.NativeLibraryLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 执行器
 * 运行编译后的程序
 *
 * @author idealism
 * @date 2018/3/31
 */
@Component("executor")
public class Executor {
    private static final Logger LOGGER = LoggerFactory.getLogger(Executor.class);

    @Value("${judger.testCaseInputFileNameSuffix}")
    private String testCaseInputFileNameSuffix;

    @Value("${judger.testCaseOutputFileNameSuffix}")
    private String testCaseOutputFileNameSuffix;

    @Value("${judger.testCaseUserOutputFileNameSuffix}")
    private String testCaseUserOutputFileNameSuffix;

    @Value("${judger.errorFileName}")
    private String errorFileName;

    public native String execute(int language, String workspacePath,
                                 String inputFilePath, String outputFilePath,
                                 String userOutputFilePath, String errorFilePath,
                                 int timeLimit, int memoryLimit);

    static {
        try {
            NativeLibraryLoader.loadLibrary("executor");
        } catch (Exception e) {
            LOGGER.error("#loadLibrary error, libraryName: executor", e);
            throw BusinessException.buildBusinessException(ErrorCodeEnum.LIBRARY_LOAD_ERROR);
        }
    }

    /**
     * 运行编译后的程序
     *
     * @param language         源代码语言
     * @param workspacePath    工作空间路径
     * @param testCaseFileName 测试数据名
     * @param timeLimit        运行时间限制
     * @param memoryLimit      最大空间限制
     * @return 编译结果 json 串
     */
    public String doExecute(Integer language, String workspacePath, String testCaseFileName, Integer timeLimit, Integer memoryLimit) {
        String inputFilePath = workspacePath + "/" + testCaseFileName + this.testCaseInputFileNameSuffix;
        String outputFilePath = workspacePath + "/" + testCaseFileName + this.testCaseOutputFileNameSuffix;
        String userOutputFilePath = workspacePath + "/" + testCaseFileName + this.testCaseUserOutputFileNameSuffix;
        String errorFilePath = workspacePath + "/" + this.errorFileName;

        return this.execute(language, workspacePath, inputFilePath, outputFilePath, userOutputFilePath, errorFilePath, timeLimit, memoryLimit);
    }
}
