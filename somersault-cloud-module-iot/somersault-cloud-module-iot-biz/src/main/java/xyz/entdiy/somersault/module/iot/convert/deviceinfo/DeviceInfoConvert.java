package xyz.entdiy.somersault.module.iot.convert.deviceinfo;

import java.util.*;

import xyz.entdiy.somersault.framework.common.pojo.PageResult;

import xyz.entdiy.somersault.module.iot.dal.dataobject.mqttserver.MqttServerDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import xyz.entdiy.somersault.module.iot.controller.admin.deviceinfo.vo.*;
import xyz.entdiy.somersault.module.iot.dal.dataobject.deviceinfo.DeviceInfoDO;

/**
 * 设备信息 Convert
 *
 * @author entdiy.xyz
 */
@Mapper
public interface DeviceInfoConvert {

    DeviceInfoConvert INSTANCE = Mappers.getMapper(DeviceInfoConvert.class);

    DeviceInfoDO convert(DeviceInfoCreateReqVO bean);

    DeviceInfoDO convert(DeviceInfoUpdateReqVO bean);

    DeviceInfoRespVO convert(DeviceInfoDO bean);

    List<DeviceInfoRespVO> convertList(List<DeviceInfoDO> list);

    PageResult<DeviceInfoRespVO> convertPage(PageResult<DeviceInfoDO> page);

    List<DeviceInfoExcelVO> convertList02(List<DeviceInfoDO> list);

    DeviceInfoRespVO.MqttServer convert(MqttServerDO bean);
}
