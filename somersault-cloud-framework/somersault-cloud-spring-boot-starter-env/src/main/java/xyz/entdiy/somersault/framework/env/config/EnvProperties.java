package xyz.entdiy.somersault.framework.env.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 环境配置
 *
 * @author entdiy.xyz
 */
@ConfigurationProperties(prefix = "biz.env")
@Data
public class EnvProperties {

    public static final String TAG_KEY = "biz.env.tag";

    /**
     * 环境标签
     */
    private String tag;

}
