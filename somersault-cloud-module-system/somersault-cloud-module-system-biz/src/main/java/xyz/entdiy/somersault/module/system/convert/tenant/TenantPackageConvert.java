package xyz.entdiy.somersault.module.system.convert.tenant;

import xyz.entdiy.somersault.framework.common.pojo.PageResult;
import xyz.entdiy.somersault.module.system.controller.admin.permission.vo.role.RoleSimpleRespVO;
import xyz.entdiy.somersault.module.system.controller.admin.tenant.vo.packages.TenantPackageCreateReqVO;
import xyz.entdiy.somersault.module.system.controller.admin.tenant.vo.packages.TenantPackageRespVO;
import xyz.entdiy.somersault.module.system.controller.admin.tenant.vo.packages.TenantPackageSimpleRespVO;
import xyz.entdiy.somersault.module.system.controller.admin.tenant.vo.packages.TenantPackageUpdateReqVO;
import xyz.entdiy.somersault.module.system.dal.dataobject.tenant.TenantPackageDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 租户套餐 Convert
 *
 * @author entdiy.xyz
 */
@Mapper
public interface TenantPackageConvert {

    TenantPackageConvert INSTANCE = Mappers.getMapper(TenantPackageConvert.class);

    TenantPackageDO convert(TenantPackageCreateReqVO bean);

    TenantPackageDO convert(TenantPackageUpdateReqVO bean);

    TenantPackageRespVO convert(TenantPackageDO bean);

    List<TenantPackageRespVO> convertList(List<TenantPackageDO> list);

    PageResult<TenantPackageRespVO> convertPage(PageResult<TenantPackageDO> page);

    List<TenantPackageSimpleRespVO> convertList02(List<TenantPackageDO> list);

}
