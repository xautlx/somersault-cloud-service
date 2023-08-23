package xyz.entdiy.somersault.framework.file.config;

import xyz.entdiy.somersault.framework.file.core.client.FileClientFactory;
import xyz.entdiy.somersault.framework.file.core.client.FileClientFactoryImpl;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * 文件配置类
 *
 * @author entdiy.xyz
 */
@AutoConfiguration
public class CloudFileAutoConfiguration {

    @Bean
    public FileClientFactory fileClientFactory() {
        return new FileClientFactoryImpl();
    }

}
