package xyz.entdiy.somersault.module.iot.dal.dataobject.mqttserver;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import xyz.entdiy.somersault.framework.mybatis.core.dataobject.BaseDO;

/**
 * MQTT服务节点配置信息 DO
 *
 * @author entdiy.xyz
 */
@TableName("iot_mqtt_server")
@KeySequence("iot_mqtt_server_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MqttServerDO extends BaseDO {

    /**
     * ID
     */
    @TableId
    private Long id;
    /**
     * 类型
     */
    private String type;
    /**
     * 唯一标识
     */
    private String code;
    /**
     * 地址
     */
    private String endpoint;
    /**
     * 端口
     */
    private Integer port;
    /**
     * 协议
     */
    private String protocol;
    /**
     * APPID
     */
    private String appId;
    /**
     * AppClientID
     */
    private String appClientId;
    /**
     * AppClientSecret
     */
    private String appClientSecret;
    /**
     * 访问账号
     */
    private String appUsername;
    /**
     * 访问密码
     */
    private String appPassword;
    /**
     * 状态（0正常 1停用）
     *
     * 枚举 {@link xyz.entdiy.somersault.framework.common.enums.CommonStatusEnum}
     */
    private Integer status;

}
