package com.yeebotech.shunweioms.dto;

public class ApiResult<T> {
    private String status;   // "success" 或 "error"
    private int code;        // HTTP 状态码或自定义业务码
    private String message;  // 错误信息或成功信息
    private T data;          // 返回的数据
    private String errorDetails; // 详细错误信息（可选）

    public ApiResult() {
    }

    public ApiResult(String status, int code, String message, T data, String errorDetails) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.data = data;
        this.errorDetails = errorDetails;
    }

    // 静态工厂方法用于成功响应
    public static <T> ApiResult<T> success(T data,int code, String message) {
        return new ApiResult<>("success", code, message, data, null);
    }

    // 静态工厂方法用于错误响应
    public static <T> ApiResult<T> error(int code, String message, String errorDetails) {
        return new ApiResult<>("error", code, message, null, errorDetails);
    }

    // Getters and Setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrorDetails() {
        return errorDetails;
    }

    public void setErrorDetails(String errorDetails) {
        this.errorDetails = errorDetails;
    }
}
