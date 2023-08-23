package xyz.entdiy.somersault.module.iot.controller.admin.deviceinfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 设备信息 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DeviceInfoRespVO extends DeviceInfoBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    /**
     * 关联MQTT服务
     */
    private MqttServer mqttServer;

    /**
     * 参照 MqttServerRespVO 定义必要的显示属性对象，避免直接使用 MqttServerRespVO 导致的AK/SK等属性数据泄露
     */
    @Schema(description = "MQTT服务")
    @Data
    public static class MqttServer {

        @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED)
        private Long id;

        @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED)
        private String type;

    }
}
