package xyz.entdiy.somersault.module.iot.controller.admin.deviceinfo.vo;

import xyz.entdiy.somersault.module.iot.dal.dataobject.mqttserver.MqttServerDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 设备信息 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class DeviceInfoBaseVO {

    @Schema(description = "设备唯一编号")
    private String uniCode;

    @Schema(description = "设备名称")
    private String name;

    @Schema(description = "连接Token")
    private String connToken;

    @Schema(description = "连接配置ID")
    private Long mqttServerId;

    @Schema(description = "状态（0正常 1停用）")
    private Byte status;
}
