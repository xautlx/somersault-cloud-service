package xyz.entdiy.somersault.framework.sms.config;

import xyz.entdiy.somersault.framework.sms.core.client.SmsClientFactory;
import xyz.entdiy.somersault.framework.sms.core.client.impl.SmsClientFactoryImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * 短信配置类
 *
 * @author entdiy.xyz
 */
@AutoConfiguration
public class CloudSmsAutoConfiguration {

    @Bean
    public SmsClientFactory smsClientFactory() {
        return new SmsClientFactoryImpl();
    }

}
