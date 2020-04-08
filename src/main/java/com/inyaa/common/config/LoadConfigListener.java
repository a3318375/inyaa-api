package com.inyaa.common.config;

import com.inyaa.common.cache.ConfigCache;
import com.inyaa.common.context.BeanTool;
import com.inyaa.config.dao.ConfigDao;
import com.inyaa.config.domain.po.Config;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
//@Component
//@DependsOn({"dataSource"})
public class LoadConfigListener {

    //    @PostConstruct
    public void init() {
        final ConfigDao configDao = BeanTool.getBean(ConfigDao.class);
        final List<Config> configList = configDao.selectList(null);
        configList.forEach(config -> {
            log.debug("config_key: {}, config_vlaue: {}", config.getConfigKey(), config.getConfigValue());
            ConfigCache.putConfig(config.getConfigKey(), config.getConfigValue());
        });
    }

}
