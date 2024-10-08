package com.shunwei.oms.auth.dto;

import lombok.Data;

@Data
public class LoginResponse {
    private String status;
    private String type;
    private String currentAuthority;

    public LoginResponse(String status, String type, String currentAuthority) {
        this.status = status;
        this.type = type;
        this.currentAuthority = currentAuthority;
    }
}
