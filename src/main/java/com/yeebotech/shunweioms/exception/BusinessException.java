package com.yeebotech.shunweioms.exception;

public class BusinessException extends RuntimeException {
    private final int code;        // 自定义业务码
    private final String errorDetails; // 详细错误信息（可选）

    public BusinessException(int code, String message, String errorDetails) {
        super(message);
        this.code = code;
        this.errorDetails = errorDetails;
    }

    public int getCode() {
        return code;
    }

    public String getErrorDetails() {
        return errorDetails;
    }
}
