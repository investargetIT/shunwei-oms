package com.yeebotech.shunweioms.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResult<T> {
    private String status;   // "success" 或 "error"
    private int code;        // HTTP 状态码或自定义业务码
    private String message;  // 错误信息或成功信息
    private T data;          // 返回的数据
    private String errorDetails; // 详细错误信息（可选）

    // 静态工厂方法用于成功响应
    public static <T> ApiResult<T> success(T data, int code, String message) {
        return ApiResult.<T>builder()
                .status("success")
                .code(code)
                .message(message)
                .data(data)
                .build();
    }

    // 静态工厂方法用于错误响应
    public static <T> ApiResult<T> error(int code, String message, String errorDetails) {
        return ApiResult.<T>builder()
                .status("error")
                .code(code)
                .message(message)
                .errorDetails(errorDetails)
                .build();
    }
}
