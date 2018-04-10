package cn.idealismxxm.onlinejudge.judger.core;

import cn.idealismxxm.onlinejudge.domain.enums.ErrorCodeEnum;
import cn.idealismxxm.onlinejudge.domain.enums.LanguageEnum;
import cn.idealismxxm.onlinejudge.domain.exception.BusinessException;
import cn.idealismxxm.onlinejudge.domain.util.NativeLibraryLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 编译器
 * 对代码进行编译等相关操作
 *
 * @author idealism
 * @date 2018/3/31
 */
@Component("compiler")
public class Compiler {

    private static final Logger LOGGER = LoggerFactory.getLogger(Compiler.class);

    @Value("${judger.sourceFileName}")
    private String sourceFileName;

    public native int compile(int language, String compilationCommand);

    static {
        try {
            NativeLibraryLoader.loadLibrary("compiler");
        } catch (Exception e) {
            LOGGER.error("#loadLibrary error, libraryName: compiler", e);
            BusinessException.buildBusinessException(ErrorCodeEnum.LIBRARY_LOAD_ERROR);
        }
    }

    public Integer doCompile(Integer language, String workspacePath) {
        LanguageEnum languageEnum = Objects.requireNonNull(LanguageEnum.getLanguageEnumByCode(language));
        String sourceFilePath = workspacePath + "/" + sourceFileName + languageEnum.getSuffix();
        String compilationCommand = languageEnum.getCompilationCommand(sourceFilePath);
        return this.compile(language, compilationCommand);
    }
}
