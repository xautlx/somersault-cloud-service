package xyz.entdiy.somersault.module.iot.controller.admin.mqttserver.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * MQTT服务节点配置信息 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class MqttServerBaseVO {

    @Schema(description = "类型", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "类型不能为空")
    private String type;

    @Schema(description = "唯一标识", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "唯一标识不能为空")
    private String code;

    @Schema(description = "地址")
    private String endpoint;

    @Schema(description = "端口")
    private Integer port;

    @Schema(description = "协议")
    private String protocol;

    @Schema(description = "APPID")
    private String appId;

    @Schema(description = "AppClientID")
    private String appClientId;

    @Schema(description = "AppClientSecret")
    private String appClientSecret;

    @Schema(description = "访问账号")
    private String appUsername;

    @Schema(description = "访问密码")
    private String appPassword;

    @Schema(description = "状态（0正常 1停用）")
    private Byte status;

}
