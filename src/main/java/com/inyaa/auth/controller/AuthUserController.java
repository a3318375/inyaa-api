package com.inyaa.auth.controller;

import com.inyaa.auth.bean.UserInfo;
import com.inyaa.common.annotation.LoginRequired;
import com.inyaa.common.base.domain.Result;
import com.inyaa.auth.domain.vo.AuthUserVO;
import com.inyaa.auth.service.AuthUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.Principal;

/**
 * @author inyaa
 * @since 2019-08-28
 */
@RestController
@RequestMapping("/auth")
public class AuthUserController {

    @Resource
    private AuthUserService authUserService;

    @GetMapping("/user/v1/get")
    public Result<UserInfo> getUserInfo(Principal principal) {
        return authUserService.getUserInfo(principal.getName());
    }

    @LoginRequired
    @GetMapping("/user/v1/list")
    public Result<UserInfo> getUserList(AuthUserVO authUserVO) {
        return authUserService.getUserList(authUserVO);
    }

}
