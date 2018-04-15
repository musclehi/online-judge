package cn.idealismxxm.onlinejudge.judger.core;

import cn.idealismxxm.onlinejudge.domain.enums.ResultEnum;
import cn.idealismxxm.onlinejudge.domain.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 分析器
 * 对输出的结果进行分析，返回评测结果
 *
 * @author idealism
 * @date 2018/3/31
 */
@Component("analyzer")
public class Analyzer {

    private static final Logger LOGGER = LoggerFactory.getLogger(Analyzer.class);

    /**
     * 通过标准输出和用户输出的分析，得到结果
     *
     * @param outputFilePath     标准输出文件路径
     * @param userOutputFilePath 用户输出文件路径
     * @return AC / PE / WA 三种结果代码之一
     */
    public Integer doAnalyze(String outputFilePath, String userOutputFilePath) {
        String output = FileUtil.readString(outputFilePath);
        String userOutput = FileUtil.readString(userOutputFilePath);
        if (this.isAccepted(output, userOutput)) {
            return ResultEnum.ACCEPTED.getCode();
        }
        if (this.isPresentationError(output, userOutput)) {
            return ResultEnum.PRESENTATION_ERROR.getCode();
        }
        return ResultEnum.WRONG_ANSWER.getCode();
    }

    private Boolean isAccepted(String output, String userOutput) {
        return output.equals(userOutput);
    }

    private Boolean isPresentationError(String output, String userOutput) {
        output = output.replaceAll("[ \t\n]*", "");
        userOutput = userOutput.replaceAll("[ \t\n]*", "");
        return output.equals(userOutput);
    }
}
