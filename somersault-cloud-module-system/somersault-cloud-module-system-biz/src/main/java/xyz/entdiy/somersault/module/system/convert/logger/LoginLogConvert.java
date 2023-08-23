package xyz.entdiy.somersault.module.system.convert.logger;

import xyz.entdiy.somersault.framework.common.pojo.PageResult;
import xyz.entdiy.somersault.module.system.api.logger.dto.LoginLogCreateReqDTO;
import xyz.entdiy.somersault.module.system.controller.admin.logger.vo.loginlog.LoginLogExcelVO;
import xyz.entdiy.somersault.module.system.controller.admin.logger.vo.loginlog.LoginLogRespVO;
import xyz.entdiy.somersault.module.system.dal.dataobject.logger.LoginLogDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface LoginLogConvert {

    LoginLogConvert INSTANCE = Mappers.getMapper(LoginLogConvert.class);

    PageResult<LoginLogRespVO> convertPage(PageResult<LoginLogDO> page);

    List<LoginLogExcelVO> convertList(List<LoginLogDO> list);

    LoginLogDO convert(LoginLogCreateReqDTO bean);

}
