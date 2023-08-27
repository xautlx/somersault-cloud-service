package xyz.entdiy.somersault.framework.tracer.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * BizTracer配置类
 *
 * @author theMonkeyKing
 */
@ConfigurationProperties("biz.tracer")
@Data
public class TracerProperties {
}
