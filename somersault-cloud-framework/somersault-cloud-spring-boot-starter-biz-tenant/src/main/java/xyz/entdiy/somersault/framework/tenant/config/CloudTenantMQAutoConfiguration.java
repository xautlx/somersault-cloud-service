package xyz.entdiy.somersault.framework.tenant.config;

import xyz.entdiy.somersault.framework.common.enums.WebFilterOrderEnum;
import xyz.entdiy.somersault.framework.mybatis.core.util.MyBatisUtils;
import xyz.entdiy.somersault.framework.tenant.core.aop.TenantIgnoreAspect;
import xyz.entdiy.somersault.framework.tenant.core.db.TenantDatabaseInterceptor;
import xyz.entdiy.somersault.framework.tenant.core.job.TenantJobAspect;
import xyz.entdiy.somersault.framework.tenant.core.mq.TenantChannelInterceptor;
import xyz.entdiy.somersault.framework.tenant.core.mq.TenantFunctionAroundWrapper;
import xyz.entdiy.somersault.framework.tenant.core.redis.TenantRedisCacheManager;
import xyz.entdiy.somersault.framework.tenant.core.security.TenantSecurityWebFilter;
import xyz.entdiy.somersault.framework.tenant.core.service.TenantFrameworkService;
import xyz.entdiy.somersault.framework.tenant.core.service.TenantFrameworkServiceImpl;
import xyz.entdiy.somersault.framework.tenant.core.web.TenantContextWebFilter;
import xyz.entdiy.somersault.framework.web.config.WebProperties;
import xyz.entdiy.somersault.framework.web.core.handler.GlobalExceptionHandler;
import xyz.entdiy.somersault.module.system.api.tenant.TenantApi;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.function.context.catalog.FunctionAroundWrapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.integration.config.GlobalChannelInterceptor;

import java.util.Objects;

@AutoConfiguration
@ConditionalOnProperty(prefix = "biz.tenant", value = "enable", matchIfMissing = true) // 允许使用 biz.tenant.enable=false 禁用多租户
@ConditionalOnClass(name = {
        "org.springframework.messaging.support.ChannelInterceptor",
        "org.springframework.cloud.function.context.catalog.FunctionAroundWrapper"
})
@EnableConfigurationProperties(TenantProperties.class)
public class CloudTenantMQAutoConfiguration {

    @Bean
    @GlobalChannelInterceptor // 必须添加在方法上，否则无法生效
    public TenantChannelInterceptor tenantChannelInterceptor() {
        return new TenantChannelInterceptor();
    }

    @Bean
    public FunctionAroundWrapper functionAroundWrapper() {
        return new TenantFunctionAroundWrapper();
    }

}
