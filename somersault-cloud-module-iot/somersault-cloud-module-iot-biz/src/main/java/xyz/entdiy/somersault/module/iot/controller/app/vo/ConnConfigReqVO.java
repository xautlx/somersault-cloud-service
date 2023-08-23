package xyz.entdiy.somersault.module.iot.controller.app.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Schema(description = "管理后台 - 账号密码登录 Request VO,如果登录并绑定社交用户，需要传递 social 开头的参数")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConnConfigReqVO {

    @Schema(description = "设备ID", required = true)
    @NotEmpty(message = "设备ID不能为空")
    private String deviceId;

    @Schema(description = "设备主机名称", required = false)
    private String hostName;

    @Schema(description = "设备主机IP", required = false)
    private String hostId;
}