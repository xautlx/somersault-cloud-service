package xyz.entdiy.somersault.framework.captcha.config;

import xyz.entdiy.somersault.framework.captcha.core.service.RedisCaptchaServiceImpl;
import com.xingyuv.captcha.properties.AjCaptchaProperties;
import com.xingyuv.captcha.service.CaptchaCacheService;
import com.xingyuv.captcha.service.impl.CaptchaServiceFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.core.Ordered;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;

@AutoConfiguration
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
public class BizCaptchaConfiguration {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Bean
    public CaptchaCacheService captchaCacheService(AjCaptchaProperties config) {
        // 缓存类型 redis/local/....
        CaptchaCacheService ret = CaptchaServiceFactory.getCache(config.getCacheType().name());
        if (ret instanceof RedisCaptchaServiceImpl) {
            ((RedisCaptchaServiceImpl) ret).setStringRedisTemplate(stringRedisTemplate);
        }
        return ret;
    }

}
