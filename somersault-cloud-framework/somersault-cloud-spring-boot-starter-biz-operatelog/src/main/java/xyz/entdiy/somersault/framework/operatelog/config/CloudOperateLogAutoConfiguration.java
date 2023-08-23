package xyz.entdiy.somersault.framework.operatelog.config;

import xyz.entdiy.somersault.framework.operatelog.core.aop.OperateLogAspect;
import xyz.entdiy.somersault.framework.operatelog.core.service.OperateLogFrameworkService;
import xyz.entdiy.somersault.framework.operatelog.core.service.OperateLogFrameworkServiceImpl;
import xyz.entdiy.somersault.module.system.api.logger.OperateLogApi;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
public class CloudOperateLogAutoConfiguration {

    @Bean
    public OperateLogAspect operateLogAspect() {
        return new OperateLogAspect();
    }

    @Bean
    public OperateLogFrameworkService operateLogFrameworkService(OperateLogApi operateLogApi) {
        return new OperateLogFrameworkServiceImpl(operateLogApi);
    }

}
