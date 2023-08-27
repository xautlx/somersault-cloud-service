package xyz.entdiy.somersault.module.system.convert.sensitiveword;

import xyz.entdiy.somersault.framework.common.pojo.PageResult;
import xyz.entdiy.somersault.module.system.controller.admin.sensitiveword.vo.SensitiveWordCreateReqVO;
import xyz.entdiy.somersault.module.system.controller.admin.sensitiveword.vo.SensitiveWordExcelVO;
import xyz.entdiy.somersault.module.system.controller.admin.sensitiveword.vo.SensitiveWordRespVO;
import xyz.entdiy.somersault.module.system.controller.admin.sensitiveword.vo.SensitiveWordUpdateReqVO;
import xyz.entdiy.somersault.module.system.dal.dataobject.sensitiveword.SensitiveWordDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 敏感词 Convert
 *
 * @author theMonkeyKing
 */
@Mapper
public interface SensitiveWordConvert {

    SensitiveWordConvert INSTANCE = Mappers.getMapper(SensitiveWordConvert.class);

    SensitiveWordDO convert(SensitiveWordCreateReqVO bean);

    SensitiveWordDO convert(SensitiveWordUpdateReqVO bean);

    SensitiveWordRespVO convert(SensitiveWordDO bean);

    List<SensitiveWordRespVO> convertList(List<SensitiveWordDO> list);

    PageResult<SensitiveWordRespVO> convertPage(PageResult<SensitiveWordDO> page);

    List<SensitiveWordExcelVO> convertList02(List<SensitiveWordDO> list);

}
