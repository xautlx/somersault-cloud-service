package xyz.entdiy.somersault.module.system.api.errorcode;

import xyz.entdiy.somersault.framework.common.pojo.CommonResult;
import xyz.entdiy.somersault.module.system.api.errorcode.dto.ErrorCodeAutoGenerateReqDTO;
import xyz.entdiy.somersault.module.system.api.errorcode.dto.ErrorCodeRespDTO;
import xyz.entdiy.somersault.module.system.service.errorcode.ErrorCodeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

import static xyz.entdiy.somersault.framework.common.pojo.CommonResult.success;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class ErrorCodeApiImpl implements ErrorCodeApi {

    @Resource
    private ErrorCodeService errorCodeService;

    @Override
    public CommonResult<Boolean> autoGenerateErrorCodeList(List<ErrorCodeAutoGenerateReqDTO> autoGenerateDTOs) {
        errorCodeService.autoGenerateErrorCodes(autoGenerateDTOs);
        return success(true);
    }

    @Override
    public CommonResult<List<ErrorCodeRespDTO>> getErrorCodeList(String applicationName, LocalDateTime minUpdateTime) {
        return success(errorCodeService.getErrorCodeList(applicationName, minUpdateTime));
    }
}
