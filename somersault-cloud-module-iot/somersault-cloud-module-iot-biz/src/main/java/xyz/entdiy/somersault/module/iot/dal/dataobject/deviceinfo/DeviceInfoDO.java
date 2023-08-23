package xyz.entdiy.somersault.module.iot.dal.dataobject.deviceinfo;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import xyz.entdiy.somersault.framework.mybatis.core.dataobject.BaseDO;
import lombok.*;

/**
 * 设备信息 DO
 *
 * @author entdiy.xyz
 */
@TableName("iot_device_info")
@KeySequence("iot_device_info_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceInfoDO extends BaseDO {

    /**
     * ID
     */
    @TableId
    private Long id;
    /**
     * 设备唯一编号
     */
    private String uniCode;
    /**
     * 设备名称
     */
    private String name;
    /**
     * 连接Token
     */
    private String connToken;
    /**
     * 连接配置ID
     */
    private Long mqttServerId;
    /**
     * 状态（0正常 1停用）
     *
     * 枚举 {@link xyz.entdiy.somersault.framework.common.enums.CommonStatusEnum}
     */
    private Integer status;
}
