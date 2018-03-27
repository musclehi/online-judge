package cn.idealismxxm.onlinejudge.exception;

import cn.idealismxxm.onlinejudge.enums.ErrorCodeEnum;

/**
 * 业务异常
 *
 * @author idealism
 * @date 2018/3/27
 */
public class BusinessException extends RuntimeException {
    /**
     * 错误代码
     */
    private String errorCode;

    private BusinessException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    private BusinessException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public static BusinessException buildBusinessException(ErrorCodeEnum errorCodeEnum) {
        return new BusinessException(errorCodeEnum.getErrorCode(), errorCodeEnum.getMsg());
    }

    public static BusinessException buildBusinessException(ErrorCodeEnum errorCodeEnum, Throwable cause) {
        return new BusinessException(errorCodeEnum.getErrorCode(), errorCodeEnum.getMsg(), cause);
    }

    public static BusinessException buildCustomizedMessageException(String message) {
        return new BusinessException(ErrorCodeEnum.CUSTOM.getErrorCode(), message);
    }

    public static BusinessException buildCustomizedMessageException(String message, Throwable cause) {
        return new BusinessException(ErrorCodeEnum.CUSTOM.getErrorCode(), message, cause);
    }

    public String getErrorCode() {
        return errorCode;
    }
}
