package xyz.entdiy.somersault.module.iot.controller.admin.deviceinfo.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import xyz.entdiy.somersault.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static xyz.entdiy.somersault.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 设备信息 Excel 导出 Request VO，参数和 DeviceInfoPageReqVO 是一致的")
@Data
public class DeviceInfoExportReqVO {

    @Schema(description = "设备唯一编号")
    private String uniCode;

    @Schema(description = "设备名称")
    private String name;

    @Schema(description = "连接Token")
    private String connToken;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "连接配置ID")
    private Long mqttServerId;

    @Schema(description = "状态（0正常 1停用）")
    private Byte status;

}
