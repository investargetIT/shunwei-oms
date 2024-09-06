package com.yeebotech.shunweioms.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCheckResponse {
    private int code;
    private String msg;
    private boolean success;
    private Data data;

    @Getter
    @Setter
    public static class Data {
        private boolean hasLoginUser;
        private String username;
        private String service;
    }
}

