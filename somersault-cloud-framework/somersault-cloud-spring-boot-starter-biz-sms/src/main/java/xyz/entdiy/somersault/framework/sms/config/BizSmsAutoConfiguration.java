package xyz.entdiy.somersault.framework.sms.config;

import xyz.entdiy.somersault.framework.sms.core.client.SmsClientFactory;
import xyz.entdiy.somersault.framework.sms.core.client.impl.SmsClientFactoryImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.core.Ordered;
import org.springframework.context.annotation.Bean;

/**
 * 短信配置类
 *
 * @author theMonkeyKing
 */
@AutoConfiguration
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
public class BizSmsAutoConfiguration {

    @Bean
    public SmsClientFactory smsClientFactory() {
        return new SmsClientFactoryImpl();
    }

}
