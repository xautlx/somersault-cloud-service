package xyz.entdiy.somersault.module.iot.service.deviceinfo;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.*;

import xyz.entdiy.somersault.module.iot.controller.admin.deviceinfo.vo.*;
import xyz.entdiy.somersault.module.iot.dal.dataobject.deviceinfo.DeviceInfoDO;
import xyz.entdiy.somersault.framework.common.pojo.PageResult;

import xyz.entdiy.somersault.module.iot.convert.deviceinfo.DeviceInfoConvert;
import xyz.entdiy.somersault.module.iot.dal.mysql.deviceinfo.DeviceInfoMapper;

import static xyz.entdiy.somersault.framework.common.exception.util.ServiceExceptionUtil.exception;
import static xyz.entdiy.somersault.module.iot.enums.ErrorCodeConstants.*;

/**
 * 设备信息 Service 实现类
 *
 * @author entdiy.xyz
 */
@Service
@Validated
public class DeviceInfoServiceImpl implements DeviceInfoService {

    @Resource
    private DeviceInfoMapper deviceInfoMapper;

    @Override
    public Long createDeviceInfo(DeviceInfoCreateReqVO createReqVO) {
        // 插入
        DeviceInfoDO deviceInfo = DeviceInfoConvert.INSTANCE.convert(createReqVO);
        deviceInfoMapper.insert(deviceInfo);
        // 返回
        return deviceInfo.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDeviceInfo(DeviceInfoUpdateReqVO updateReqVO) {
        // 校验存在
        validateDeviceInfoExists(updateReqVO.getId());
        // 更新
        DeviceInfoDO updateObj = DeviceInfoConvert.INSTANCE.convert(updateReqVO);
        deviceInfoMapper.updateById(updateObj);
    }

    @Override
    public void deleteDeviceInfo(Long id) {
        // 校验存在
        validateDeviceInfoExists(id);
        // 删除
        deviceInfoMapper.deleteById(id);
    }

    private void validateDeviceInfoExists(Long id) {
        if (deviceInfoMapper.selectById(id) == null) {
            throw exception(DEVICE_INFO_NOT_EXISTS);
        }
    }

    @Override
    public DeviceInfoDO getDeviceInfo(Long id) {
        return deviceInfoMapper.selectById(id);
    }

    @Override
    public List<DeviceInfoDO> getDeviceInfoList(Collection<Long> ids) {
        return deviceInfoMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<DeviceInfoDO> getDeviceInfoPage(DeviceInfoPageReqVO pageReqVO) {
        return deviceInfoMapper.selectPage(pageReqVO);
    }

    @Override
    public List<DeviceInfoDO> getDeviceInfoList(DeviceInfoExportReqVO exportReqVO) {
        return deviceInfoMapper.selectList(exportReqVO);
    }

    @Override
    public DeviceInfoDO getDeviceInfo(String uniCode) {
        return deviceInfoMapper.selectOne(DeviceInfoDO::getUniCode, uniCode);
    }
}
