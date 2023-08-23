package xyz.entdiy.somersault.framework.idempotent.config;

import xyz.entdiy.somersault.framework.idempotent.core.aop.IdempotentAspect;
import xyz.entdiy.somersault.framework.idempotent.core.keyresolver.impl.DefaultIdempotentKeyResolver;
import xyz.entdiy.somersault.framework.idempotent.core.keyresolver.impl.ExpressionIdempotentKeyResolver;
import xyz.entdiy.somersault.framework.idempotent.core.keyresolver.IdempotentKeyResolver;
import xyz.entdiy.somersault.framework.idempotent.core.redis.IdempotentRedisDAO;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import xyz.entdiy.somersault.framework.redis.config.CloudRedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;

@AutoConfiguration(after = CloudRedisAutoConfiguration.class)
public class CloudIdempotentConfiguration {

    @Bean
    public IdempotentAspect idempotentAspect(List<IdempotentKeyResolver> keyResolvers, IdempotentRedisDAO idempotentRedisDAO) {
        return new IdempotentAspect(keyResolvers, idempotentRedisDAO);
    }

    @Bean
    public IdempotentRedisDAO idempotentRedisDAO(StringRedisTemplate stringRedisTemplate) {
        return new IdempotentRedisDAO(stringRedisTemplate);
    }

    // ========== 各种 IdempotentKeyResolver Bean ==========

    @Bean
    public DefaultIdempotentKeyResolver defaultIdempotentKeyResolver() {
        return new DefaultIdempotentKeyResolver();
    }

    @Bean
    public ExpressionIdempotentKeyResolver expressionIdempotentKeyResolver() {
        return new ExpressionIdempotentKeyResolver();
    }

}
