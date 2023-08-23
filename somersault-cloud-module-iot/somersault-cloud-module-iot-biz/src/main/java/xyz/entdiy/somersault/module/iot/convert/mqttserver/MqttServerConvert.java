package xyz.entdiy.somersault.module.iot.convert.mqttserver;

import java.util.*;

import xyz.entdiy.somersault.framework.common.pojo.PageResult;

import xyz.entdiy.somersault.module.iot.controller.admin.deviceinfo.vo.DeviceInfoRespVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import xyz.entdiy.somersault.module.iot.controller.admin.mqttserver.vo.*;
import xyz.entdiy.somersault.module.iot.dal.dataobject.mqttserver.MqttServerDO;

/**
 * MQTT服务节点配置信息 Convert
 *
 * @author entdiy.xyz
 */
@Mapper
public interface MqttServerConvert {

    MqttServerConvert INSTANCE = Mappers.getMapper(MqttServerConvert.class);

    MqttServerDO convert(MqttServerCreateReqVO bean);

    MqttServerDO convert(MqttServerUpdateReqVO bean);

    MqttServerRespVO convert(MqttServerDO bean);

    List<MqttServerRespVO> convertList(List<MqttServerDO> list);

    PageResult<MqttServerRespVO> convertPage(PageResult<MqttServerDO> page);

    List<MqttServerExcelVO> convertList02(List<MqttServerDO> list);

    List<MqttServerSimpleRespVO> convertSimpleList(List<MqttServerDO> list);
}
