package xyz.entdiy.somersault.module.iot.dal.mysql.mqttserver;

import java.util.*;

import xyz.entdiy.somersault.framework.common.pojo.PageResult;
import xyz.entdiy.somersault.framework.mybatis.core.query.LambdaQueryWrapperX;
import xyz.entdiy.somersault.framework.mybatis.core.mapper.BaseMapperX;
import xyz.entdiy.somersault.module.iot.dal.dataobject.mqttserver.MqttServerDO;
import org.apache.ibatis.annotations.Mapper;
import xyz.entdiy.somersault.module.iot.controller.admin.mqttserver.vo.*;

/**
 * MQTT服务节点配置信息 Mapper
 *
 * @author entdiy.xyz
 */
@Mapper
public interface MqttServerMapper extends BaseMapperX<MqttServerDO> {

    default PageResult<MqttServerDO> selectPage(MqttServerPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<MqttServerDO>()
                .eqIfPresent(MqttServerDO::getType, reqVO.getType())
                .eqIfPresent(MqttServerDO::getCode, reqVO.getCode())
                .eqIfPresent(MqttServerDO::getEndpoint, reqVO.getEndpoint())
                .eqIfPresent(MqttServerDO::getPort, reqVO.getPort())
                .eqIfPresent(MqttServerDO::getProtocol, reqVO.getProtocol())
                .betweenIfPresent(MqttServerDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(MqttServerDO::getAppId, reqVO.getAppId())
                .eqIfPresent(MqttServerDO::getAppClientId, reqVO.getAppClientId())
                .eqIfPresent(MqttServerDO::getAppClientSecret, reqVO.getAppClientSecret())
                .likeIfPresent(MqttServerDO::getAppUsername, reqVO.getAppUsername())
                .eqIfPresent(MqttServerDO::getAppPassword, reqVO.getAppPassword())
                .eqIfPresent(MqttServerDO::getStatus, reqVO.getStatus())
                .orderByDesc(MqttServerDO::getId));
    }

    default List<MqttServerDO> selectList(MqttServerExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<MqttServerDO>()
                .eqIfPresent(MqttServerDO::getType, reqVO.getType())
                .eqIfPresent(MqttServerDO::getCode, reqVO.getCode())
                .eqIfPresent(MqttServerDO::getEndpoint, reqVO.getEndpoint())
                .eqIfPresent(MqttServerDO::getPort, reqVO.getPort())
                .eqIfPresent(MqttServerDO::getProtocol, reqVO.getProtocol())
                .betweenIfPresent(MqttServerDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(MqttServerDO::getAppId, reqVO.getAppId())
                .eqIfPresent(MqttServerDO::getAppClientId, reqVO.getAppClientId())
                .eqIfPresent(MqttServerDO::getAppClientSecret, reqVO.getAppClientSecret())
                .likeIfPresent(MqttServerDO::getAppUsername, reqVO.getAppUsername())
                .eqIfPresent(MqttServerDO::getAppPassword, reqVO.getAppPassword())
                .eqIfPresent(MqttServerDO::getStatus, reqVO.getStatus())
                .orderByDesc(MqttServerDO::getId));
    }

    default List<MqttServerDO> selectListByStatus(Integer status) {
        return selectList(MqttServerDO::getStatus, status);
    }
}
