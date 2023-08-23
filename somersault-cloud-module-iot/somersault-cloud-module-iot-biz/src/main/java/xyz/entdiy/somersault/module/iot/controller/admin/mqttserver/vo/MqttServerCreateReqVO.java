package xyz.entdiy.somersault.module.iot.controller.admin.mqttserver.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - MQTT服务节点配置信息创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MqttServerCreateReqVO extends MqttServerBaseVO {

}
