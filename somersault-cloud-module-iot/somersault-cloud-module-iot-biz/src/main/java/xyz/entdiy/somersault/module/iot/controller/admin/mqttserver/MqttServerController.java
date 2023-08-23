package xyz.entdiy.somersault.module.iot.controller.admin.mqttserver;

import xyz.entdiy.somersault.framework.common.enums.CommonStatusEnum;
import xyz.entdiy.somersault.framework.common.pojo.CommonResult;
import xyz.entdiy.somersault.framework.common.pojo.PageResult;
import xyz.entdiy.somersault.framework.excel.core.util.ExcelUtils;
import xyz.entdiy.somersault.framework.operatelog.core.annotations.OperateLog;
import xyz.entdiy.somersault.module.iot.controller.admin.mqttserver.vo.*;
import xyz.entdiy.somersault.module.iot.convert.mqttserver.MqttServerConvert;
import xyz.entdiy.somersault.module.iot.dal.dataobject.mqttserver.MqttServerDO;
import xyz.entdiy.somersault.module.iot.service.mqttserver.MqttServerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import static xyz.entdiy.somersault.framework.common.pojo.CommonResult.success;
import static xyz.entdiy.somersault.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;

@Tag(name = "管理后台 - MQTT服务节点配置信息")
@RestController
@RequestMapping("/iot/mqtt-server")
@Validated
public class MqttServerController {

    @Resource
    private MqttServerService mqttServerService;

    @PostMapping("/create")
    @Operation(summary = "创建MQTT服务节点配置信息")
    @PreAuthorize("@ss.hasPermission('iot:mqtt-server:create')")
    public CommonResult<Long> createMqttServer(@Valid @RequestBody MqttServerCreateReqVO createReqVO) {
        return success(mqttServerService.createMqttServer(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新MQTT服务节点配置信息")
    @PreAuthorize("@ss.hasPermission('iot:mqtt-server:update')")
    public CommonResult<Boolean> updateMqttServer(@Valid @RequestBody MqttServerUpdateReqVO updateReqVO) {
        mqttServerService.updateMqttServer(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除MQTT服务节点配置信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('iot:mqtt-server:delete')")
    public CommonResult<Boolean> deleteMqttServer(@RequestParam("id") Long id) {
        mqttServerService.deleteMqttServer(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得MQTT服务节点配置信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('iot:mqtt-server:query')")
    public CommonResult<MqttServerRespVO> getMqttServer(@RequestParam("id") Long id) {
        MqttServerDO mqttServer = mqttServerService.getMqttServer(id);
        return success(MqttServerConvert.INSTANCE.convert(mqttServer));
    }

    @GetMapping("/list")
    @Operation(summary = "获得MQTT服务节点配置信息列表")
    @Parameter(name = "ids", description = "编号列表", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('iot:mqtt-server:query')")
    public CommonResult<List<MqttServerRespVO>> getMqttServerList(@RequestParam("ids") Collection<Long> ids) {
        List<MqttServerDO> list = mqttServerService.getMqttServerList(ids);
        return success(MqttServerConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "获得MQTT服务节点配置信息分页")
    @PreAuthorize("@ss.hasPermission('iot:mqtt-server:query')")
    public CommonResult<PageResult<MqttServerRespVO>> getMqttServerPage(@Valid MqttServerPageReqVO pageVO) {
        PageResult<MqttServerDO> pageResult = mqttServerService.getMqttServerPage(pageVO);
        return success(MqttServerConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出MQTT服务节点配置信息 Excel")
    @PreAuthorize("@ss.hasPermission('iot:mqtt-server:export')")
    @OperateLog(type = EXPORT)
    public void exportMqttServerExcel(@Valid MqttServerExportReqVO exportReqVO,
                                      HttpServletResponse response) throws IOException {
        List<MqttServerDO> list = mqttServerService.getMqttServerList(exportReqVO);
        // 导出 Excel
        List<MqttServerExcelVO> datas = MqttServerConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "MQTT服务节点配置信息.xls", "数据", MqttServerExcelVO.class, datas);
    }

    @GetMapping("/list-simple")
    @Operation(summary = "获得MQTT服务节点配置信息列表")
    @PreAuthorize("@ss.hasPermission('iot:mqtt-server:query')")
    public CommonResult<List<MqttServerSimpleRespVO>> getMqttServerList() {
        // 获得列表，只要开启状态的
        List<MqttServerDO> list = mqttServerService.getMqttServerListByStatus(CommonStatusEnum.ENABLE.getStatus());
        return success(MqttServerConvert.INSTANCE.convertSimpleList(list));
    }
}
