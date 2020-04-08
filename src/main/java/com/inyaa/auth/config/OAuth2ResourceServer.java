package com.inyaa.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * 资源服务器
 */
@Configuration
@EnableResourceServer
public class OAuth2ResourceServer extends ResourceServerConfigurerAdapter {

    @Resource
    private DataSource dataSource;
    @Resource
    private AuthenticationEntryPoint authenticationEntryPoint;
    @Resource
    private OAuth2WebSecurityExpressionHandler expressionHandler;
    @Resource
    private OAuth2AccessDeniedHandler oAuth2AccessDeniedHandler;

    @Bean
    public TokenStore jdbcTokenStore() {
        return new JdbcTokenStore(dataSource);
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenStore(jdbcTokenStore());
        // resources.authenticationManager(oAuth2AuthenticationManager);
        //resources.resourceId(DEMO_RESOURCE_ID).stateless(true);
        resources.authenticationEntryPoint(authenticationEntryPoint);
        resources.expressionHandler(expressionHandler);
        resources.accessDeniedHandler(oAuth2AccessDeniedHandler);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                //http.authorizeRequests()方法有多个子节点，每个macher按照他们的声明顺序执行
                .authorizeRequests()
                //我们指定任何用户都可以访问多个URL的模式。
                //任何用户都可以访问以"/actuator/**"开头的URL。
                .antMatchers("/**").permitAll()
                //以 "/admin/" 开头的URL只能让拥有 "ROLE_ADMIN"角色的用户访问。
                //请注意我们使用 hasRole 方法，没有使用 "ROLE_" 前缀。
                //.antMatchers("/**/add", "/**/update", "/**/save").hasRole("ADMIN")

                //任何以"/db/" 开头的URL需要同时具有 "ROLE_ADMIN" 和 "ROLE_DBA"权限的用户才可以访问。
                //和上面一样我们的 hasRole 方法也没有使用 "ROLE_" 前缀。
                //.antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")

                //任何以"/db/" 开头的URL只需要拥有 "ROLE_ADMIN" 和 "ROLE_DBA"其中一个权限的用户才可以访问。
                //和上面一样我们的 hasRole 方法也没有使用 "ROLE_" 前缀。
                //.antMatchers("/db/**").hasAnyRole("ADMIN", "DBA")
                //尚未匹配的任何URL都要求用户进行身份验证
                .anyRequest().authenticated();
    }
}
