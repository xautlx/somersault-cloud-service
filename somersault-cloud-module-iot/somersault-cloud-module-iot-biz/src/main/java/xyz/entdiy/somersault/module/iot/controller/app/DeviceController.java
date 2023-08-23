package xyz.entdiy.somersault.module.iot.controller.app;

import xyz.entdiy.somersault.framework.common.pojo.CommonResult;
import xyz.entdiy.somersault.module.iot.controller.admin.deviceinfo.vo.DeviceInfoUpdateReqVO;
import xyz.entdiy.somersault.module.iot.controller.app.vo.ConnConfigRespVO;
import xyz.entdiy.somersault.module.iot.dal.dataobject.deviceinfo.DeviceInfoDO;
import xyz.entdiy.somersault.module.iot.dal.dataobject.mqttserver.MqttServerDO;
import xyz.entdiy.somersault.module.iot.enums.ErrorCodeConstants;
import xyz.entdiy.somersault.module.iot.service.deviceinfo.DeviceInfoService;
import xyz.entdiy.somersault.module.iot.service.mqttserver.MqttServerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static xyz.entdiy.somersault.module.iot.enums.ErrorCodeConstants.DEVICE_INFO_NOT_EXISTS;

@Tag(name = "前端接口 - 设备服务")
@RestController
@RequestMapping("/iot/device")
@Validated
@Slf4j
public class DeviceController {

    @Resource
    private MqttServerService mqttServerService;

    @Resource
    private DeviceInfoService deviceInfoService;

    @GetMapping("/conn-config/{uniCode}/{connToken}")
    public CommonResult<ConnConfigRespVO> fetchConnConfig(
            @PathVariable(value = "uniCode", required = true) String uniCode,
            @PathVariable(value = "connToken", required = true) String connToken
    ) {
        DeviceInfoDO deviceInfoDO = deviceInfoService.getDeviceInfo(uniCode);
        if (StringUtils.isBlank(deviceInfoDO.getConnToken())) {
            DeviceInfoUpdateReqVO updateReqVO = new DeviceInfoUpdateReqVO();
            updateReqVO.setId(deviceInfoDO.getId());
            updateReqVO.setConnToken(connToken);
            deviceInfoService.updateDeviceInfo(updateReqVO);
        } else {
            if (!deviceInfoDO.getConnToken().equals(connToken)) {
                return CommonResult.error(ErrorCodeConstants.DEVICE_TOKEN_INVALID);
            }
        }
        MqttServerDO mqttServerDO = mqttServerService.getMqttServer(deviceInfoDO.getMqttServerId());
        ConnConfigRespVO resp = new ConnConfigRespVO();
        resp.setServerHost(mqttServerDO.getEndpoint());
        resp.setServerPort(mqttServerDO.getPort());
        resp.setClientId(uniCode + "@" + mqttServerDO.getCode());
        resp.setDeviceId(uniCode);
        resp.setUsername(mqttServerDO.getAppUsername());
        resp.setPassword(mqttServerDO.getAppPassword());
        return CommonResult.success(resp);
    }

    @SneakyThrows
    @RequestMapping(value = "/exec/{cmd}/{uniCode}", method = {RequestMethod.POST, RequestMethod.GET})
    public CommonResult<Boolean> run(@PathVariable("cmd") String cmd,
                                     @PathVariable("uniCode") String uniCode,
                                     @RequestParam(value = "data", required = false) String data) {
        log.info("Exec {} for device {}", cmd, uniCode);
        DeviceInfoDO deviceInfoDO = deviceInfoService.getDeviceInfo(uniCode);
        if (deviceInfoDO == null) {
            return CommonResult.error(DEVICE_INFO_NOT_EXISTS);
        }
        MqttMessage message = new MqttMessage(cmd.getBytes());
        message.setQos(0);
        /**
         * 发送普通消息时，Topic必须和接收方订阅的Topic一致，或者符合通配符匹配规则。
         */
        mqttServerService.buildMqttClients().get(deviceInfoDO.getMqttServerId()).publish("/raspi/" + uniCode, message);
        return CommonResult.success(true);
    }
}
