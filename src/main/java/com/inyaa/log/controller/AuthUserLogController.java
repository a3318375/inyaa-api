package com.inyaa.log.controller;


import com.inyaa.common.base.domain.Result;
import com.inyaa.log.domain.vo.AuthUserLogVO;
import com.inyaa.log.service.AuthUserLogService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户行为日志记录表: 后端controller类
 * @author 青涩知夏
 */
@RestController
@RequestMapping("/logs")
public class AuthUserLogController {
    
    
    @Resource
    private AuthUserLogService authUserLogServiceImpl;
    
    /**
     * 查询用户行为日志记录表
     */
    @GetMapping("/logs/v1/{id}")
    public Result<AuthUserLogVO> query(@PathVariable Long id){
        return authUserLogServiceImpl.getLogs(id);
    }

    @DeleteMapping("/logs/v1/{id}")
    public Result<AuthUserLogVO> deleteLogs(@PathVariable Long id){
        return authUserLogServiceImpl.deleteLogs(id);
    }


    /**
     * 分页查询用户行为日志记录表
     */
    @GetMapping("/logs/v1/list")
    public Result<AuthUserLogVO> queryPage(AuthUserLogVO authUserLogVO){
        return authUserLogServiceImpl.getLogsList(authUserLogVO);
    }
}