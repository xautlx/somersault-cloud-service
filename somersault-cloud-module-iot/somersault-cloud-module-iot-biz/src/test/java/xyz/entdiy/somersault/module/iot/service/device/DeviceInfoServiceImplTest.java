package xyz.entdiy.somersault.module.iot.service.device;

import xyz.entdiy.somersault.module.iot.controller.admin.deviceinfo.vo.DeviceInfoCreateReqVO;
import xyz.entdiy.somersault.module.iot.controller.admin.deviceinfo.vo.DeviceInfoExportReqVO;
import xyz.entdiy.somersault.module.iot.controller.admin.deviceinfo.vo.DeviceInfoPageReqVO;
import xyz.entdiy.somersault.module.iot.controller.admin.deviceinfo.vo.DeviceInfoUpdateReqVO;
import xyz.entdiy.somersault.module.iot.dal.dataobject.deviceinfo.DeviceInfoDO;
import xyz.entdiy.somersault.module.iot.dal.mysql.deviceinfo.DeviceInfoMapper;
import xyz.entdiy.somersault.module.iot.service.deviceinfo.DeviceInfoServiceImpl;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.annotation.Resource;

import xyz.entdiy.somersault.framework.test.core.ut.BaseDbUnitTest;

import xyz.entdiy.somersault.framework.common.pojo.PageResult;

import javax.annotation.Resource;
import org.springframework.context.annotation.Import;
import java.util.*;
import java.time.LocalDateTime;

import static cn.hutool.core.util.RandomUtil.*;
import static xyz.entdiy.somersault.module.iot.enums.ErrorCodeConstants.*;
import static xyz.entdiy.somersault.framework.test.core.util.AssertUtils.*;
import static xyz.entdiy.somersault.framework.test.core.util.RandomUtils.*;
import static xyz.entdiy.somersault.framework.common.util.date.LocalDateTimeUtils.*;
import static xyz.entdiy.somersault.framework.common.util.object.ObjectUtils.*;
import static xyz.entdiy.somersault.framework.common.util.date.DateUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * {@link DeviceInfoServiceImpl} 的单元测试类
 *
 * @author entdiy.xyz
 */
@Import(DeviceInfoServiceImpl.class)
public class DeviceInfoServiceImplTest extends BaseDbUnitTest {

    @Resource
    private DeviceInfoServiceImpl deviceInfoService;

    @Resource
    private DeviceInfoMapper deviceInfoMapper;

    @Test
    public void testCreateDeviceInfo_success() {
        // 准备参数
        DeviceInfoCreateReqVO reqVO = randomPojo(DeviceInfoCreateReqVO.class);

        // 调用
        Long deviceInfoId = deviceInfoService.createDeviceInfo(reqVO);
        // 断言
        assertNotNull(deviceInfoId);
        // 校验记录的属性是否正确
        DeviceInfoDO deviceInfo = deviceInfoMapper.selectById(deviceInfoId);
        assertPojoEquals(reqVO, deviceInfo);
    }

    @Test
    public void testUpdateDeviceInfo_success() {
        // mock 数据
        DeviceInfoDO dbDeviceInfo = randomPojo(DeviceInfoDO.class);
        deviceInfoMapper.insert(dbDeviceInfo);// @Sql: 先插入出一条存在的数据
        // 准备参数
        DeviceInfoUpdateReqVO reqVO = randomPojo(DeviceInfoUpdateReqVO.class, o -> {
            o.setId(dbDeviceInfo.getId()); // 设置更新的 ID
        });

        // 调用
        deviceInfoService.updateDeviceInfo(reqVO);
        // 校验是否更新正确
        DeviceInfoDO deviceInfo = deviceInfoMapper.selectById(reqVO.getId()); // 获取最新的
        assertPojoEquals(reqVO, deviceInfo);
    }

    @Test
    public void testUpdateDeviceInfo_notExists() {
        // 准备参数
        DeviceInfoUpdateReqVO reqVO = randomPojo(DeviceInfoUpdateReqVO.class);

        // 调用, 并断言异常
        assertServiceException(() -> deviceInfoService.updateDeviceInfo(reqVO), DEVICE_INFO_NOT_EXISTS);
    }

    @Test
    public void testDeleteDeviceInfo_success() {
        // mock 数据
        DeviceInfoDO dbDeviceInfo = randomPojo(DeviceInfoDO.class);
        deviceInfoMapper.insert(dbDeviceInfo);// @Sql: 先插入出一条存在的数据
        // 准备参数
        Long id = dbDeviceInfo.getId();

        // 调用
        deviceInfoService.deleteDeviceInfo(id);
       // 校验数据不存在了
       assertNull(deviceInfoMapper.selectById(id));
    }

    @Test
    public void testDeleteDeviceInfo_notExists() {
        // 准备参数
        Long id = randomLongId();

        // 调用, 并断言异常
        assertServiceException(() -> deviceInfoService.deleteDeviceInfo(id), DEVICE_INFO_NOT_EXISTS);
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetDeviceInfoPage() {
       // mock 数据
       DeviceInfoDO dbDeviceInfo = randomPojo(DeviceInfoDO.class, o -> { // 等会查询到
           o.setUniCode(null);
           o.setCreateTime(null);
           o.setMqttServerId(null);
           o.setName(null);
       });
       deviceInfoMapper.insert(dbDeviceInfo);
       // 测试 uniCode 不匹配
       deviceInfoMapper.insert(cloneIgnoreId(dbDeviceInfo, o -> o.setUniCode(null)));
       // 测试 createTime 不匹配
       deviceInfoMapper.insert(cloneIgnoreId(dbDeviceInfo, o -> o.setCreateTime(null)));
       // 测试 mqttServerId 不匹配
       deviceInfoMapper.insert(cloneIgnoreId(dbDeviceInfo, o -> o.setMqttServerId(null)));
       // 测试 name 不匹配
       deviceInfoMapper.insert(cloneIgnoreId(dbDeviceInfo, o -> o.setName(null)));
       // 准备参数
       DeviceInfoPageReqVO reqVO = new DeviceInfoPageReqVO();
       reqVO.setUniCode(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setMqttServerId(null);
       reqVO.setName(null);

       // 调用
       PageResult<DeviceInfoDO> pageResult = deviceInfoService.getDeviceInfoPage(reqVO);
       // 断言
       assertEquals(1, pageResult.getTotal());
       assertEquals(1, pageResult.getList().size());
       assertPojoEquals(dbDeviceInfo, pageResult.getList().get(0));
    }

    @Test
    @Disabled  // TODO 请修改 null 为需要的值，然后删除 @Disabled 注解
    public void testGetDeviceInfoList() {
       // mock 数据
       DeviceInfoDO dbDeviceInfo = randomPojo(DeviceInfoDO.class, o -> { // 等会查询到
           o.setUniCode(null);
           o.setCreateTime(null);
           o.setMqttServerId(null);
           o.setName(null);
       });
       deviceInfoMapper.insert(dbDeviceInfo);
       // 测试 uniCode 不匹配
       deviceInfoMapper.insert(cloneIgnoreId(dbDeviceInfo, o -> o.setUniCode(null)));
       // 测试 createTime 不匹配
       deviceInfoMapper.insert(cloneIgnoreId(dbDeviceInfo, o -> o.setCreateTime(null)));
       // 测试 mqttServerId 不匹配
       deviceInfoMapper.insert(cloneIgnoreId(dbDeviceInfo, o -> o.setMqttServerId(null)));
       // 测试 name 不匹配
       deviceInfoMapper.insert(cloneIgnoreId(dbDeviceInfo, o -> o.setName(null)));
       // 准备参数
       DeviceInfoExportReqVO reqVO = new DeviceInfoExportReqVO();
       reqVO.setUniCode(null);
       reqVO.setCreateTime(buildBetweenTime(2023, 2, 1, 2023, 2, 28));
       reqVO.setMqttServerId(null);
       reqVO.setName(null);

       // 调用
       List<DeviceInfoDO> list = deviceInfoService.getDeviceInfoList(reqVO);
       // 断言
       assertEquals(1, list.size());
       assertPojoEquals(dbDeviceInfo, list.get(0));
    }

}
