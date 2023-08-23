package xyz.entdiy.somersault.module.iot.controller.admin.deviceinfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;
import xyz.entdiy.somersault.framework.excel.core.annotations.DictFormat;
import xyz.entdiy.somersault.framework.excel.core.convert.DictConvert;


/**
 * 设备信息 Excel VO
 *
 * @author entdiy.xyz
 */
@Data
public class DeviceInfoExcelVO {

    @ExcelProperty("ID")
    private Long id;

    @ExcelProperty("设备唯一编号")
    private String uniCode;

    @ExcelProperty("设备名称")
    private String name;

    @ExcelProperty("连接Token")
    private String connToken;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("连接配置ID")
    private Long mqttServerId;

    @ExcelProperty(value = "状态（0正常 1停用）", converter = DictConvert.class)
    @DictFormat("common_status") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private Byte status;

}
