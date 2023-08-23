package xyz.entdiy.somersault.framework.security.config;

import xyz.entdiy.somersault.framework.security.core.rpc.LoginUserRequestInterceptor;
import xyz.entdiy.somersault.module.system.api.oauth2.OAuth2TokenApi;
import xyz.entdiy.somersault.module.system.api.permission.PermissionApi;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

/**
 * Security 使用到 Feign 的配置项
 *
 * @author entdiy.xyz
 */
@AutoConfiguration
@EnableFeignClients(clients = {OAuth2TokenApi.class, // 主要是引入相关的 API 服务
        PermissionApi.class})
public class CloudSecurityRpcAutoConfiguration {

    @Bean
    public LoginUserRequestInterceptor loginUserRequestInterceptor() {
        return new LoginUserRequestInterceptor();
    }

}
