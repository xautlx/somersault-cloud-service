package xyz.entdiy.somersault.framework.operatelog.config;

import xyz.entdiy.somersault.module.system.api.logger.OperateLogApi;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.core.Ordered;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 操作日志使用到 Feign 的配置项
 *
 * @author theMonkeyKing
 */
@AutoConfiguration
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
@EnableFeignClients(clients = OperateLogApi.class) // 主要是引入相关的 API 服务
public class BizOperateLogRpcAutoConfiguration {

}
