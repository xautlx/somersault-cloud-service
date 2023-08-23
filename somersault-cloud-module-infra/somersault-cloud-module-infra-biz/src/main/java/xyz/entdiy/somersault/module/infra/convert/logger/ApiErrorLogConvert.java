package xyz.entdiy.somersault.module.infra.convert.logger;

import xyz.entdiy.somersault.framework.apilog.core.service.ApiErrorLog;
import xyz.entdiy.somersault.framework.common.pojo.PageResult;
import xyz.entdiy.somersault.module.infra.api.logger.dto.ApiErrorLogCreateReqDTO;
import xyz.entdiy.somersault.module.infra.controller.admin.logger.vo.apierrorlog.ApiErrorLogExcelVO;
import xyz.entdiy.somersault.module.infra.controller.admin.logger.vo.apierrorlog.ApiErrorLogRespVO;
import xyz.entdiy.somersault.module.infra.dal.dataobject.logger.ApiErrorLogDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * API 错误日志 Convert
 *
 * @author entdiy.xyz
 */
@Mapper
public interface ApiErrorLogConvert {

    ApiErrorLogConvert INSTANCE = Mappers.getMapper(ApiErrorLogConvert.class);

    ApiErrorLogRespVO convert(ApiErrorLogDO bean);

    PageResult<ApiErrorLogRespVO> convertPage(PageResult<ApiErrorLogDO> page);

    List<ApiErrorLogExcelVO> convertList02(List<ApiErrorLogDO> list);

    ApiErrorLogDO convert(ApiErrorLogCreateReqDTO bean);

}
