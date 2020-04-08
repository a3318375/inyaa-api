package com.inyaa.common.config;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.inyaa.common.constant.Constants;
import com.inyaa.config.dao.ConfigDao;
import com.inyaa.config.domain.po.Config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    @Resource
    private ConfigDao configDao;

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {

        String defaultPath = null;
        try {
            Config config = configDao.selectOne(new LambdaQueryWrapper<Config>().eq(Config::getConfigKey, Constants.DEFAULT_PATH));
            defaultPath = config.getConfigValue();
        } catch (Exception e) {
        }
        registry.addResourceHandler("/files/**").addResourceLocations("file:///" + defaultPath);
    }
}