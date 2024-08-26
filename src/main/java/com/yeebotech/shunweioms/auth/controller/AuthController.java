package com.yeebotech.shunweioms.auth.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import com.yeebotech.shunweioms.auth.dto.CurrentUserResponse;
import com.yeebotech.shunweioms.auth.dto.LoginRequest;
import com.yeebotech.shunweioms.auth.dto.LoginResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Operation(summary = "User login",
            description = "Authenticate the user with provided username and password.")
    @PostMapping("/login/account")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        // 处理登录逻辑，例如验证用户名和密码

        // 创建并返回响应对象
        return new LoginResponse("ok", "account", "admin");
    }

    @Operation(summary = "Get current user information",
            description = "Retrieve information about the currently authenticated user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved current user",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CurrentUserResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/currentUser")
    public CurrentUserResponse getCurrentUser() {
        // 模拟当前用户信息
        CurrentUserResponse response = new CurrentUserResponse();
        response.setSuccess(true);

        CurrentUserResponse.UserData userData = new CurrentUserResponse.UserData();
        userData.setName("Serati Ma");
        userData.setAvatar("https://gw.alipayobjects.com/zos/antfincdn/XAosXuNZyF/BiazfanxmamNRoxxVxka.png");
        userData.setUserid("00000001");
        userData.setEmail("antdesign@alipay.com");
        userData.setSignature("海纳百川，有容乃大");
        userData.setTitle("交互专家");
        userData.setGroup("蚂蚁金服－某某某事业群－某某平台部－某某技术部－UED");
        userData.setTags(Arrays.asList(
                new CurrentUserResponse.UserData.Tag("0", "很有想法的"),
                new CurrentUserResponse.UserData.Tag("1", "专注设计"),
                new CurrentUserResponse.UserData.Tag("2", "辣~"),
                new CurrentUserResponse.UserData.Tag("3", "大长腿"),
                new CurrentUserResponse.UserData.Tag("4", "川妹子"),
                new CurrentUserResponse.UserData.Tag("5", "海纳百川")
        ));
        userData.setNotifyCount(12);
        userData.setUnreadCount(11);
        userData.setCountry("China");

        CurrentUserResponse.UserData.Geographic geographic = new CurrentUserResponse.UserData.Geographic();
        geographic.setProvince(new CurrentUserResponse.UserData.Geographic.Province("浙江省", "330000"));
        geographic.setCity(new CurrentUserResponse.UserData.Geographic.City("杭州市", "330100"));
        userData.setGeographic(geographic);

        userData.setAddress("西湖区工专路 77 号");
        userData.setPhone("0752-268888888");

        response.setData(userData);

        return response;
    }


}
