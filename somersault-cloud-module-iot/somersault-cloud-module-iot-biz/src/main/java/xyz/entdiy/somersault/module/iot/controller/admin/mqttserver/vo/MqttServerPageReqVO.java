package xyz.entdiy.somersault.module.iot.controller.admin.mqttserver.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import xyz.entdiy.somersault.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static xyz.entdiy.somersault.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - MQTT服务节点配置信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MqttServerPageReqVO extends PageParam {

    @Schema(description = "类型")
    private String type;

    @Schema(description = "唯一标识")
    private String code;

    @Schema(description = "地址")
    private String endpoint;

    @Schema(description = "端口")
    private Integer port;

    @Schema(description = "协议")
    private String protocol;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

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
