package xyz.entdiy.somersault.module.iot.controller.app.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "返回给客户端的连接配置参数")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConnConfigRespVO {

    @Schema(description = "服务主机", required = true)
    private String serverHost;

    @Schema(description = "服务端口", required = true)
    private Integer serverPort;

    @Schema(description = "设备ID", required = true)
    private String deviceId;

    @Schema(description = "客户端ID", required = true)
    private String clientId;

    @Schema(description = "连接账号", required = true)
    private String username;

    @Schema(description = "连接密码", required = true)
    private String password;

}