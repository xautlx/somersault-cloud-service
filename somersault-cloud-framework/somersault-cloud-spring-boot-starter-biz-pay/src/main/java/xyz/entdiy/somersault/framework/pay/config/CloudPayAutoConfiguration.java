package xyz.entdiy.somersault.framework.pay.config;

import xyz.entdiy.somersault.framework.pay.core.client.PayClientFactory;
import xyz.entdiy.somersault.framework.pay.core.client.impl.PayClientFactoryImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * 支付配置类
 *
 * @author entdiy.xyz
 */
@AutoConfiguration
public class CloudPayAutoConfiguration {

    @Bean
    public PayClientFactory payClientFactory() {
        return new PayClientFactoryImpl();
    }

}
