package xyz.entdiy.somersault.framework.tracer.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * BizTracer配置类
 *
 * @author entdiy.xyz
 */
@ConfigurationProperties("biz.tracer")
@Data
public class TracerProperties {
}
