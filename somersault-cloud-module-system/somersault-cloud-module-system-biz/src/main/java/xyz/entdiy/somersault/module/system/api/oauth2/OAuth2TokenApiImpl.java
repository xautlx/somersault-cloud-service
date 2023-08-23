package xyz.entdiy.somersault.module.system.api.oauth2;

import xyz.entdiy.somersault.framework.common.pojo.CommonResult;
import xyz.entdiy.somersault.module.system.api.oauth2.dto.OAuth2AccessTokenCheckRespDTO;
import xyz.entdiy.somersault.module.system.api.oauth2.dto.OAuth2AccessTokenCreateReqDTO;
import xyz.entdiy.somersault.module.system.api.oauth2.dto.OAuth2AccessTokenRespDTO;
import xyz.entdiy.somersault.module.system.convert.auth.OAuth2TokenConvert;
import xyz.entdiy.somersault.module.system.dal.dataobject.oauth2.OAuth2AccessTokenDO;
import xyz.entdiy.somersault.module.system.service.oauth2.OAuth2TokenService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static xyz.entdiy.somersault.framework.common.pojo.CommonResult.success;

@RestController // 提供 RESTful API 接口，给 Feign 调用
@Validated
public class OAuth2TokenApiImpl implements OAuth2TokenApi {

    @Resource
    private OAuth2TokenService oauth2TokenService;

    @Override
    @Operation(description = "创建访问令牌")
    public CommonResult<OAuth2AccessTokenRespDTO> createAccessToken(OAuth2AccessTokenCreateReqDTO reqDTO) {
        OAuth2AccessTokenDO accessTokenDO = oauth2TokenService.createAccessToken(
                reqDTO.getUserId(), reqDTO.getUserType(), reqDTO.getClientId(), reqDTO.getScopes());
        return success(OAuth2TokenConvert.INSTANCE.convert2(accessTokenDO));
    }

    @Override
    public CommonResult<OAuth2AccessTokenCheckRespDTO> checkAccessToken(String accessToken) {
        return success(OAuth2TokenConvert.INSTANCE.convert(oauth2TokenService.checkAccessToken(accessToken)));
    }

    @Override
    public CommonResult<OAuth2AccessTokenRespDTO> removeAccessToken(String accessToken) {
        OAuth2AccessTokenDO accessTokenDO = oauth2TokenService.removeAccessToken(accessToken);
        return success(OAuth2TokenConvert.INSTANCE.convert2(accessTokenDO));
    }

    @Override
    public CommonResult<OAuth2AccessTokenRespDTO> refreshAccessToken(String refreshToken, String clientId) {
        OAuth2AccessTokenDO accessTokenDO = oauth2TokenService.refreshAccessToken(refreshToken, clientId);
        return success(OAuth2TokenConvert.INSTANCE.convert2(accessTokenDO));
    }

}
