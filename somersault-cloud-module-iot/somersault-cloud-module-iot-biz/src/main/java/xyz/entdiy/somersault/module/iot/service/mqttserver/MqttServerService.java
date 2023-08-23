package xyz.entdiy.somersault.module.iot.service.mqttserver;

import java.util.*;
import javax.validation.*;

import cn.hutool.core.collection.CollUtil;
import xyz.entdiy.somersault.framework.common.util.collection.CollectionUtils;
import xyz.entdiy.somersault.module.iot.controller.admin.mqttserver.vo.*;
import xyz.entdiy.somersault.module.iot.dal.dataobject.mqttserver.MqttServerDO;
import xyz.entdiy.somersault.framework.common.pojo.PageResult;
import org.eclipse.paho.client.mqttv3.MqttClient;

/**
 * MQTT服务节点配置信息 Service 接口
 *
 * @author entdiy.xyz
 */
public interface MqttServerService {

    /**
     * 创建MQTT服务节点配置信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createMqttServer(@Valid MqttServerCreateReqVO createReqVO);

    /**
     * 更新MQTT服务节点配置信息
     *
     * @param updateReqVO 更新信息
     */
    void updateMqttServer(@Valid MqttServerUpdateReqVO updateReqVO);

    /**
     * 删除MQTT服务节点配置信息
     *
     * @param id 编号
     */
    void deleteMqttServer(Long id);

    /**
     * 获得MQTT服务节点配置信息
     *
     * @param id 编号
     * @return MQTT服务节点配置信息
     */
    MqttServerDO getMqttServer(Long id);

    /**
     * 获得MQTT服务节点配置信息列表
     *
     * @param ids 编号
     * @return MQTT服务节点配置信息列表
     */
    List<MqttServerDO> getMqttServerList(Collection<Long> ids);

    /**
     * 获得MQTT服务节点配置信息分页
     *
     * @param pageReqVO 分页查询
     * @return MQTT服务节点配置信息分页
     */
    PageResult<MqttServerDO> getMqttServerPage(MqttServerPageReqVO pageReqVO);

    /**
     * 获得MQTT服务节点配置信息列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return MQTT服务节点配置信息列表
     */
    List<MqttServerDO> getMqttServerList(MqttServerExportReqVO exportReqVO);

    /**
     * 初始化MQTT服务注册连接
     *
     * @return MQTT对象主键和对于连接Client
     */
    Map<Long, MqttClient> buildMqttClients();

    /**
     * 获得所有数据 Map
     *
     * @return MQTT服务 Map
     */
    Map<Long, MqttServerDO> getMqttServerMap();

    /**
     * 获得指定状态的MQTT服务节点配置信息列表
     *
     * @param status 状态
     * @return MQTT服务节点配置信息列表
     */
    List<MqttServerDO> getMqttServerListByStatus(Integer status);

}
