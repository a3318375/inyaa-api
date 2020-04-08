package com.inyaa.config.controller;

import com.inyaa.common.annotation.LoginRequired;
import com.inyaa.common.base.domain.Result;
import com.inyaa.config.domain.validator.QueryConfigList;
import com.inyaa.config.domain.validator.UpdateConfig;
import com.inyaa.config.domain.vo.ConfigVO;
import com.inyaa.config.service.ConfigService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author inyaa
 * @since 2019-08-28
 */
@RestController
@RequestMapping("/config")
@Validated({UpdateConfig.class})
public class ConfigController {

    @Resource
    private ConfigService configService;

    @LoginRequired
    @PutMapping("/config/v1/update")
    public Result updateConfig(@RequestBody List<ConfigVO> configList) {
        return configService.updateConfig(configList);
    }

    @LoginRequired
    @GetMapping("/config/v1/list")
    public Result getConfigList(@Validated({QueryConfigList.class}) ConfigVO configVO) {
        return configService.getConfigList(configVO);
    }

    @GetMapping("/config-base/v1/list")
    public Result getConfigBaseList() {
        return configService.getConfigBaseList();
    }
}
