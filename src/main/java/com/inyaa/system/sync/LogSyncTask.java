package com.inyaa.system.sync;

import com.inyaa.log.domain.vo.AuthUserLogVO;
import com.inyaa.log.service.AuthUserLogService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class LogSyncTask {

    @Resource
    private AuthUserLogService sysLogServiceImpl;

    @Async(value = "asyncExecutor")
    public void addLog(AuthUserLogVO sysLog) {
        this.sysLogServiceImpl.saveLogs(sysLog);
    }
}
