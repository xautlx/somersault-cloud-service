package xyz.entdiy.somersault.module.iot.dal.mysql.deviceinfo;

import java.util.*;

import xyz.entdiy.somersault.framework.common.pojo.PageResult;
import xyz.entdiy.somersault.framework.mybatis.core.query.LambdaQueryWrapperX;
import xyz.entdiy.somersault.framework.mybatis.core.mapper.BaseMapperX;
import xyz.entdiy.somersault.module.iot.dal.dataobject.deviceinfo.DeviceInfoDO;
import org.apache.ibatis.annotations.Mapper;
import xyz.entdiy.somersault.module.iot.controller.admin.deviceinfo.vo.*;

/**
 * 设备信息 Mapper
 *
 * @author entdiy.xyz
 */
@Mapper
public interface DeviceInfoMapper extends BaseMapperX<DeviceInfoDO> {

    default PageResult<DeviceInfoDO> selectPage(DeviceInfoPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DeviceInfoDO>()
                .eqIfPresent(DeviceInfoDO::getUniCode, reqVO.getUniCode())
                .likeIfPresent(DeviceInfoDO::getName, reqVO.getName())
                .eqIfPresent(DeviceInfoDO::getConnToken, reqVO.getConnToken())
                .betweenIfPresent(DeviceInfoDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(DeviceInfoDO::getMqttServerId, reqVO.getMqttServerId())
                .eqIfPresent(DeviceInfoDO::getStatus, reqVO.getStatus())
                .orderByDesc(DeviceInfoDO::getId));
    }

    default List<DeviceInfoDO> selectList(DeviceInfoExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<DeviceInfoDO>()
                .eqIfPresent(DeviceInfoDO::getUniCode, reqVO.getUniCode())
                .likeIfPresent(DeviceInfoDO::getName, reqVO.getName())
                .eqIfPresent(DeviceInfoDO::getConnToken, reqVO.getConnToken())
                .betweenIfPresent(DeviceInfoDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(DeviceInfoDO::getMqttServerId, reqVO.getMqttServerId())
                .eqIfPresent(DeviceInfoDO::getStatus, reqVO.getStatus())
                .orderByDesc(DeviceInfoDO::getId));
    }

}
