package xyz.entdiy.somersault.framework.apilog.config;

import xyz.entdiy.somersault.module.infra.api.logger.ApiAccessLogApi;
import xyz.entdiy.somersault.module.infra.api.logger.ApiErrorLogApi;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.core.Ordered;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * API 日志使用到 Feign 的配置项
 *
 * @author theMonkeyKing
 */
@AutoConfiguration
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
@EnableFeignClients(clients = {ApiAccessLogApi.class, // 主要是引入相关的 API 服务
        ApiErrorLogApi.class})
public class BizApiLogRpcAutoConfiguration {
}
