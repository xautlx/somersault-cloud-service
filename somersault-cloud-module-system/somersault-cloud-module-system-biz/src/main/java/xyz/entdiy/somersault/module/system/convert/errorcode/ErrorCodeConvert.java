package xyz.entdiy.somersault.module.system.convert.errorcode;

import xyz.entdiy.somersault.framework.common.pojo.PageResult;
import xyz.entdiy.somersault.module.system.api.errorcode.dto.ErrorCodeAutoGenerateReqDTO;
import xyz.entdiy.somersault.module.system.api.errorcode.dto.ErrorCodeRespDTO;
import xyz.entdiy.somersault.module.system.controller.admin.errorcode.vo.ErrorCodeCreateReqVO;
import xyz.entdiy.somersault.module.system.controller.admin.errorcode.vo.ErrorCodeExcelVO;
import xyz.entdiy.somersault.module.system.controller.admin.errorcode.vo.ErrorCodeRespVO;
import xyz.entdiy.somersault.module.system.dal.dataobject.errorcode.ErrorCodeDO;
import xyz.entdiy.somersault.module.system.controller.admin.errorcode.vo.ErrorCodeUpdateReqVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 错误码 Convert
 *
 * @author theMonkeyKing
 */
@Mapper
public interface ErrorCodeConvert {

    ErrorCodeConvert INSTANCE = Mappers.getMapper(ErrorCodeConvert.class);

    ErrorCodeDO convert(ErrorCodeCreateReqVO bean);

    ErrorCodeDO convert(ErrorCodeUpdateReqVO bean);

    ErrorCodeRespVO convert(ErrorCodeDO bean);

    List<ErrorCodeRespVO> convertList(List<ErrorCodeDO> list);

    PageResult<ErrorCodeRespVO> convertPage(PageResult<ErrorCodeDO> page);

    List<ErrorCodeExcelVO> convertList02(List<ErrorCodeDO> list);

    ErrorCodeDO convert(ErrorCodeAutoGenerateReqDTO bean);

    List<ErrorCodeRespDTO> convertList03(List<ErrorCodeDO> list);

}
