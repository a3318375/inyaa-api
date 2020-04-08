package com.inyaa.monitor.controller;

import com.inyaa.common.annotation.LoginRequired;
import com.inyaa.common.base.domain.Result;
import com.inyaa.system.enums.RoleEnum;
import com.inyaa.util.RuntimeUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/monitor")
public class MonitorController {

    @GetMapping("/system/v1/get")
    public Result getSystem() {
        return RuntimeUtil.getProperty();
    }

    @GetMapping("/memory/v1/get")
    @LoginRequired(role = RoleEnum.ADMIN)
    public Result getMemory(){
        return RuntimeUtil.getMemory();
    }

    @GetMapping("/cpu/v1/get")
    public Result getCpu() {
        return RuntimeUtil.getCpu();
    }

    @GetMapping("/file/v1/get")
    public Result getFile() {
        return RuntimeUtil.getFile();
    }

    @GetMapping("/net/v1/get")
    public Result getNet() {
        return RuntimeUtil.getNet();
    }

    @GetMapping("/ethernet/v1/get")
    public Result getEthernet() {
        return RuntimeUtil.getEthernet();
    }

    @GetMapping("/user/v1/count")
    public Result getUserCount() {
        return RuntimeUtil.getUserCount();
    }
    @GetMapping("/user/v1/chart")
    public Result getUserChat() {
        return RuntimeUtil.getUserChat();
    }
}
