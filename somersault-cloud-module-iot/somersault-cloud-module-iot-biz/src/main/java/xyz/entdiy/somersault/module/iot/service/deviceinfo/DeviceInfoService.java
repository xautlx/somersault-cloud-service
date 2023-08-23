package xyz.entdiy.somersault.module.iot.service.deviceinfo;

import java.util.*;
import javax.validation.*;
import xyz.entdiy.somersault.module.iot.controller.admin.deviceinfo.vo.*;
import xyz.entdiy.somersault.module.iot.dal.dataobject.deviceinfo.DeviceInfoDO;
import xyz.entdiy.somersault.framework.common.pojo.PageResult;

/**
 * 设备信息 Service 接口
 *
 * @author entdiy.xyz
 */
public interface DeviceInfoService {

    /**
     * 创建设备信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createDeviceInfo(@Valid DeviceInfoCreateReqVO createReqVO);

    /**
     * 更新设备信息
     *
     * @param updateReqVO 更新信息
     */
    void updateDeviceInfo(@Valid DeviceInfoUpdateReqVO updateReqVO);

    /**
     * 删除设备信息
     *
     * @param id 编号
     */
    void deleteDeviceInfo(Long id);

    /**
     * 获得设备信息
     *
     * @param id 编号
     * @return 设备信息
     */
    DeviceInfoDO getDeviceInfo(Long id);

    /**
     * 获得设备信息列表
     *
     * @param ids 编号
     * @return 设备信息列表
     */
    List<DeviceInfoDO> getDeviceInfoList(Collection<Long> ids);

    /**
     * 获得设备信息分页
     *
     * @param pageReqVO 分页查询
     * @return 设备信息分页
     */
    PageResult<DeviceInfoDO> getDeviceInfoPage(DeviceInfoPageReqVO pageReqVO);

    /**
     * 获得设备信息列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 设备信息列表
     */
    List<DeviceInfoDO> getDeviceInfoList(DeviceInfoExportReqVO exportReqVO);

    /**
     * 基于设备编码查询设备信息
     *
     * @param uniCode 设备编码
     * @return 设备对象
     */
    DeviceInfoDO getDeviceInfo(String uniCode);
}
