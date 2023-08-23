package xyz.entdiy.somersault.module.iot.controller.admin.mqttserver.vo;

import xyz.entdiy.somersault.framework.common.core.IdLabel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.collections4.keyvalue.AbstractKeyValue;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - MQTT服务节点配置信息 Response VO")
@Data
@EqualsAndHashCode
@ToString(callSuper = true)
public class MqttServerSimpleRespVO implements IdLabel {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "类型", requiredMode = Schema.RequiredMode.REQUIRED)
    private String type;

    @Override
    public String getLabel() {
        return type;
    }
}
