package xyz.entdiy.somersault.module.iot.controller.admin.deviceinfo;

import xyz.entdiy.somersault.module.iot.convert.mqttserver.MqttServerConvert;
import xyz.entdiy.somersault.module.iot.dal.dataobject.mqttserver.MqttServerDO;
import xyz.entdiy.somersault.module.iot.service.mqttserver.MqttServerService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.constraints.*;
import javax.validation.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.IOException;

import xyz.entdiy.somersault.framework.common.pojo.PageResult;
import xyz.entdiy.somersault.framework.common.pojo.CommonResult;

import static xyz.entdiy.somersault.framework.common.pojo.CommonResult.success;

import xyz.entdiy.somersault.framework.excel.core.util.ExcelUtils;

import xyz.entdiy.somersault.framework.operatelog.core.annotations.OperateLog;

import static xyz.entdiy.somersault.framework.operatelog.core.enums.OperateTypeEnum.*;
import static xyz.entdiy.somersault.module.iot.enums.ErrorCodeConstants.DEVICE_INFO_NOT_EXISTS;

import xyz.entdiy.somersault.module.iot.controller.admin.deviceinfo.vo.*;
import xyz.entdiy.somersault.module.iot.dal.dataobject.deviceinfo.DeviceInfoDO;
import xyz.entdiy.somersault.module.iot.convert.deviceinfo.DeviceInfoConvert;
import xyz.entdiy.somersault.module.iot.service.deviceinfo.DeviceInfoService;

@Tag(name = "管理后台 - 设备信息")
@RestController
@RequestMapping("/iot/device-info")
@Validated
@Slf4j
public class DeviceInfoController {

    @Resource
    private DeviceInfoService deviceInfoService;

    @Resource
    private MqttServerService mqttServerService;

    @PostMapping("/create")
    @Operation(summary = "创建设备信息")
    @PreAuthorize("@ss.hasPermission('iot:device-info:create')")
    public CommonResult<Long> createDeviceInfo(@Valid @RequestBody DeviceInfoCreateReqVO createReqVO) {
        return success(deviceInfoService.createDeviceInfo(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新设备信息")
    @PreAuthorize("@ss.hasPermission('iot:device-info:update')")
    public CommonResult<Boolean> updateDeviceInfo(@Valid @RequestBody DeviceInfoUpdateReqVO updateReqVO) {
        deviceInfoService.updateDeviceInfo(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除设备信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('iot:device-info:delete')")
    public CommonResult<Boolean> deleteDeviceInfo(@RequestParam("id") Long[] ids) {
        Arrays.stream(ids).forEach(id -> deviceInfoService.deleteDeviceInfo(id));
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得设备信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('iot:device-info:query')")
    public CommonResult<DeviceInfoRespVO> getDeviceInfo(@RequestParam("id") Long id) {
        DeviceInfoDO deviceInfo = deviceInfoService.getDeviceInfo(id);
        return success(DeviceInfoConvert.INSTANCE.convert(deviceInfo));
    }

    @GetMapping("/list")
    @Operation(summary = "获得设备信息列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('iot:device-info:query')")
    public CommonResult<List<DeviceInfoRespVO>> getDeviceInfoList(@RequestParam("ids") Collection<Long> ids) {
        List<DeviceInfoDO> list = deviceInfoService.getDeviceInfoList(ids);
        return success(DeviceInfoConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得设备信息分页")
    @PreAuthorize("@ss.hasPermission('iot:device-info:query')")
    public CommonResult<PageResult<DeviceInfoRespVO>> getDeviceInfoPage(@Valid DeviceInfoPageReqVO pageVO) {
        PageResult<DeviceInfoDO> pageResult = deviceInfoService.getDeviceInfoPage(pageVO);

        // 拼接结果返回
        Map<Long, MqttServerDO> mqttServerMap = mqttServerService.getMqttServerMap();
        List<DeviceInfoRespVO> pageList = new ArrayList<>(pageResult.getList().size());
        pageResult.getList().forEach(item -> {
            DeviceInfoRespVO respVO = DeviceInfoConvert.INSTANCE.convert(item);
            respVO.setMqttServer(DeviceInfoConvert.INSTANCE.convert(mqttServerMap.get(item.getMqttServerId())));
            pageList.add(respVO);
        });
        return success(new PageResult<>(pageList, pageResult.getTotal()));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出设备信息 Excel")
    @PreAuthorize("@ss.hasPermission('iot:device-info:export')")
    @OperateLog(type = EXPORT)
    public void exportDeviceInfoExcel(@Valid DeviceInfoExportReqVO exportReqVO,
                                      HttpServletResponse response) throws IOException {
        List<DeviceInfoDO> list = deviceInfoService.getDeviceInfoList(exportReqVO);
        // 导出 Excel
        List<DeviceInfoExcelVO> datas = DeviceInfoConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "设备信息.xls", "数据", DeviceInfoExcelVO.class, datas);
    }

    @PutMapping("/update-status/{status}")
    @Operation(summary = "更新设备状态")
    @PreAuthorize("@ss.hasPermission('iot:device-info:update')")
    public CommonResult<Boolean> updateDeviceInfoStatus(@PathVariable("status") Byte status,
                                                        @RequestParam("id") Long[] ids) {
        for (Long id : ids) {
            DeviceInfoUpdateReqVO updateReqVO = new DeviceInfoUpdateReqVO();
            updateReqVO.setId(id);
            updateReqVO.setStatus(status);
            deviceInfoService.updateDeviceInfo(updateReqVO);
        }
        return success(true);
    }

    @SneakyThrows
    @Operation(summary = "执行设备指令")
    @RequestMapping(value = "/exec/{cmd}", method = {RequestMethod.POST, RequestMethod.GET})
    @PreAuthorize("@ss.hasPermission('iot:device-info:exec')")
    @OperateLog(type = EXPORT)
    public CommonResult<Boolean> run(@PathVariable("cmd") String cmd,
                                     @RequestParam(value = "data", required = false) String data,
                                     @RequestParam("id") Long[] ids
    ) {
        for (Long id : ids) {
            DeviceInfoDO deviceInfoDO = deviceInfoService.getDeviceInfo(id);
            if (deviceInfoDO == null) {
                return CommonResult.error(DEVICE_INFO_NOT_EXISTS);
            }
            log.info("Exec {} for device {}", cmd, deviceInfoDO.getUniCode());
            MqttMessage message = new MqttMessage(cmd.getBytes());
            message.setQos(0);
            /**
             * 发送普通消息时，Topic必须和接收方订阅的Topic一致，或者符合通配符匹配规则。
             */
            mqttServerService.buildMqttClients().get(deviceInfoDO.getMqttServerId()).publish("/raspi/" + deviceInfoDO.getUniCode(), message);
        }
        return CommonResult.success(true);
    }


}
