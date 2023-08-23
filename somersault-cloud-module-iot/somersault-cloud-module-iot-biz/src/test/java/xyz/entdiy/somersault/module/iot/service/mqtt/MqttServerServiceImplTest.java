package xyz.entdiy.somersault.module.iot.service.mqtt;

import xyz.entdiy.somersault.framework.common.pojo.PageResult;
import xyz.entdiy.somersault.framework.test.core.ut.BaseDbUnitTest;
import xyz.entdiy.somersault.module.iot.controller.admin.mqttserver.vo.MqttServerCreateReqVO;
import xyz.entdiy.somersault.module.iot.controller.admin.mqttserver.vo.MqttServerExportReqVO;
import xyz.entdiy.somersault.module.iot.controller.admin.mqttserver.vo.MqttServerPageReqVO;
import xyz.entdiy.somersault.module.iot.controller.admin.mqttserver.vo.MqttServerUpdateReqVO;
import xyz.entdiy.somersault.module.iot.dal.dataobject.mqttserver.MqttServerDO;
import xyz.entdiy.somersault.module.iot.dal.mysql.mqttserver.MqttServerMapper;
import xyz.entdiy.somersault.module.iot.service.mqttserver.MqttServerServiceImpl;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;
import java.util.List;

import static xyz.entdiy.somersault.framework.common.util.date.LocalDateTimeUtils.buildBetweenTime;
import static xyz.entdiy.somersault.framework.common.util.object.ObjectUtils.cloneIgnoreId;
import static xyz.entdiy.somersault.framework.test.core.util.AssertUtils.assertPojoEquals;
import static xyz.entdiy.somersault.framework.test.core.util.AssertUtils.assertServiceException;
import static xyz.entdiy.somersault.framework.test.core.util.RandomUtils.randomLongId;
import static xyz.entdiy.somersault.framework.test.core.util.RandomUtils.randomPojo;
import static xyz.entdiy.somersault.module.iot.enums.ErrorCodeConstants.MQTT_SERVER_NOT_EXISTS;
import static org.junit.jupiter.api.Assertions.*;

/**
 * {@link MqttServerServiceImpl} 的单元测试类
 *
 * @author entdiy.xyz
 */
@Import(MqttServerServiceImpl.class)
public class MqttServerServiceImplTest extends BaseDbUnitTest {

    @Resource
    private MqttServerServiceImpl mqttServerService;

    @Resource
    private MqttServerMapper mqttServerMapper;

    @Test
    public void testCreateMqttServer_success() {
        // 准备参数
        MqttServerCreateReqVO reqVO = randomPojo(MqttServerCreateReqVO.class);

        // 调用
        Long mqttServerId = mqttServerService.createMqttServer(reqVO);
        // 断言
        assertNotNull(mqttServerId);
        // 校验记录的属性是否正确
        MqttServerDO mqttServer = mqttServerMapper.selectById(mqttServerId);
        assertPojoEquals(reqVO, mqttServer);
    }

    @Test
    public void testUpdateMqttServer_success() {
        // mock 数据
        MqttServerDO dbMqttServer = randomPojo(MqttServerDO.class);
        mqttServerMapper.insert(dbMqttServer);// @Sql: 先插入出一条存在的数据
        // 准备参数
        MqttServerUpdateReqVO reqVO = randomPojo(MqttServerUpdateReqVO.class, o -> {
            o.setId(dbMqttServer.getId()); // 设置更新的 ID
        });

        // 调用
        mqttServerService.updateMqttServer(reqVO);
        // 校验是否更新正确
        MqttServerDO mqttServer = mqttServerMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, mqttServer);
    }

    @Test
    public void testUpdateMqttServer_notExists() {
        // 准备参数
        MqttServerUpdateReqVO reqVO = randomPojo(MqttServerUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> mqttServerService.updateMqttServer(reqVO), MQTT_SERVER_NOT_EXISTS);
    }

    @Test
    public void testDeleteMqttServer_success() {
        // mock 数据
        MqttServerDO dbMqttServer = randomPojo(MqttServerDO.class);
        mqttServerMapper.insert(dbMqttServer);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbMqttServer.getId();

        // 调用
        mqttServerService.deleteMqttServer(id);
       // 校验数据不存在了
       assertNull(mqttServerMapper.selectById(id));
    }

    @Test
    public void testDeleteMqttServer_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> mqttServerService.deleteMqttServer(id), MQTT_SERVER_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetMqttServerPage() {
       // mock 数据
       MqttServerDO dbMqttServer = randomPojo(MqttServerDO.class, o -> { // 等会查询到
           o.setCode(null);
           o.setEndpoint(null);
           o.setPort(null);
           o.setProtocol(null);
           o.setCreateTime(null);
           o.setAppId(null);
           o.setAppClientId(null);
           o.setAppClientSecret(null);
           o.setAppUsername(null);
           o.setAppPassword(null);
       });
       mqttServerMapper.insert(dbMqttServer);
       // 测试 code 不匹配
       mqttServerMapper.insert(cloneIgnoreId(dbMqttServer, o -> o.setCode(null)));
       // 测试 endpoint 不匹配
       mqttServerMapper.insert(cloneIgnoreId(dbMqttServer, o -> o.setEndpoint(null)));
       // 测试 port 不匹配
       mqttServerMapper.insert(cloneIgnoreId(dbMqttServer, o -> o.setPort(null)));
       // 测试 protocol 不匹配
       mqttServerMapper.insert(cloneIgnoreId(dbMqttServer, o -> o.setProtocol(null)));
       // 测试 createTime 不匹配
       mqttServerMapper.insert(cloneIgnoreId(dbMqttServer, o -> o.setCreateTime(null)));
       // 测试 appId 不匹配
       mqttServerMapper.insert(cloneIgnoreId(dbMqttServer, o -> o.setAppId(null)));
       // 测试 appClientId 不匹配
       mqttServerMapper.insert(cloneIgnoreId(dbMqttServer, o -> o.setAppClientId(null)));
       // 测试 appClientSecret 不匹配
       mqttServerMapper.insert(cloneIgnoreId(dbMqttServer, o -> o.setAppClientSecret(null)));
       // 测试 appUsername 不匹配
       mqttServerMapper.insert(cloneIgnoreId(dbMqttServer, o -> o.setAppUsername(null)));
       // 测试 appPassword 不匹配
       mqttServerMapper.insert(cloneIgnoreId(dbMqttServer, o -> o.setAppPassword(null)));
       // 准备参数
       MqttServerPageReqVO reqVO = new MqttServerPageReqVO();
       reqVO.setCode(null);
       reqVO.setEndpoint(null);
       reqVO.setPort(null);
       reqVO.setProtocol(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setAppId(null);
       reqVO.setAppClientId(null);
       reqVO.setAppClientSecret(null);
       reqVO.setAppUsername(null);

       // 调用
       PageResult<MqttServerDO> pageResult = mqttServerService.getMqttServerPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbMqttServer, pageResult.getList().get(0));
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetMqttServerList() {
       // mock 数据
       MqttServerDO dbMqttServer = randomPojo(MqttServerDO.class, o -> { // 等会查询到
           o.setCode(null);
           o.setEndpoint(null);
           o.setPort(null);
           o.setProtocol(null);
           o.setCreateTime(null);
           o.setAppId(null);
           o.setAppClientId(null);
           o.setAppClientSecret(null);
           o.setAppUsername(null);
           o.setAppPassword(null);
       });
       mqttServerMapper.insert(dbMqttServer);
       // 测试 code 不匹配
       mqttServerMapper.insert(cloneIgnoreId(dbMqttServer, o -> o.setCode(null)));
       // 测试 endpoint 不匹配
       mqttServerMapper.insert(cloneIgnoreId(dbMqttServer, o -> o.setEndpoint(null)));
       // 测试 port 不匹配
       mqttServerMapper.insert(cloneIgnoreId(dbMqttServer, o -> o.setPort(null)));
       // 测试 protocol 不匹配
       mqttServerMapper.insert(cloneIgnoreId(dbMqttServer, o -> o.setProtocol(null)));
       // 测试 createTime 不匹配
       mqttServerMapper.insert(cloneIgnoreId(dbMqttServer, o -> o.setCreateTime(null)));
       // 测试 appId 不匹配
       mqttServerMapper.insert(cloneIgnoreId(dbMqttServer, o -> o.setAppId(null)));
       // 测试 appClientId 不匹配
       mqttServerMapper.insert(cloneIgnoreId(dbMqttServer, o -> o.setAppClientId(null)));
       // 测试 appClientSecret 不匹配
       mqttServerMapper.insert(cloneIgnoreId(dbMqttServer, o -> o.setAppClientSecret(null)));
       // 测试 appUsername 不匹配
       mqttServerMapper.insert(cloneIgnoreId(dbMqttServer, o -> o.setAppUsername(null)));
       // 测试 appPassword 不匹配
       mqttServerMapper.insert(cloneIgnoreId(dbMqttServer, o -> o.setAppPassword(null)));
       // 准备参数
       MqttServerExportReqVO reqVO = new MqttServerExportReqVO();
       reqVO.setCode(null);
       reqVO.setEndpoint(null);
       reqVO.setPort(null);
       reqVO.setProtocol(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setAppId(null);
       reqVO.setAppClientId(null);
       reqVO.setAppClientSecret(null);
       reqVO.setAppUsername(null);

       // 调用
       List<MqttServerDO> list = mqttServerService.getMqttServerList(reqVO);
       // 断言
       assertEquals(1, list.size());
       assertPojoEquals(dbMqttServer, list.get(0));
    }

}
