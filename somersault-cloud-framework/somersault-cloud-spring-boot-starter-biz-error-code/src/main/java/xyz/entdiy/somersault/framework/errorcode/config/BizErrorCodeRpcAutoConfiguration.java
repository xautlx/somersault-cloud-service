package xyz.entdiy.somersault.framework.errorcode.config;

import xyz.entdiy.somersault.module.system.api.errorcode.ErrorCodeApi;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.core.Ordered;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 错误码用到 Feign 的配置项
 *
 * @author theMonkeyKing
 */
@AutoConfiguration
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
@EnableFeignClients(clients = ErrorCodeApi.class) // 主要是引入相关的 API 服务
public class BizErrorCodeRpcAutoConfiguration {
}
