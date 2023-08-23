package xyz.entdiy.somersault.module.iot.controller.admin.mqttserver.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;
import xyz.entdiy.somersault.framework.excel.core.annotations.DictFormat;
import xyz.entdiy.somersault.framework.excel.core.convert.DictConvert;


/**
 * MQTT服务节点配置信息 Excel VO
 *
 * @author entdiy.xyz
 */
@Data
public class MqttServerExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("类型")
    private String type;

    @ExcelProperty("唯一标识")
    private String code;

    @ExcelProperty("地址")
    private String endpoint;

    @ExcelProperty("端口")
    private Integer port;

    @ExcelProperty("协议")
    private String protocol;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("APPID")
    private String appId;

    @ExcelProperty("AppClientID")
    private String appClientId;

    @ExcelProperty("AppClientSecret")
    private String appClientSecret;

    @ExcelProperty("访问账号")
    private String appUsername;

    @ExcelProperty("访问密码")
    private String appPassword;

    @ExcelProperty(value = "状态（0正常 1停用）", converter = DictConvert.class)
    @DictFormat("common_status") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private Byte status;

}
