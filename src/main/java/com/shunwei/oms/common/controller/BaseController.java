package com.shunwei.oms.common.controller;

import com.shunwei.oms.common.constants.ApiConstants;
import com.shunwei.oms.common.dto.ApiResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class BaseController {

    protected <T> ResponseEntity<ApiResult<T>> handleRequest(RequestHandler<T> handler) {
        try {
            return new ResponseEntity<>(handler.handle(), HttpStatus.OK);
        } catch (Exception e) {
            ApiResult<T> errorResponse = ApiResult.error(
                    ApiConstants.CODE_INTERNAL_SERVER_ERROR,
                    "An error occurred while processing the request.",
                    e.getMessage()
            );
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @FunctionalInterface
    protected interface RequestHandler<T> {
        ApiResult<T> handle();
    }
}
