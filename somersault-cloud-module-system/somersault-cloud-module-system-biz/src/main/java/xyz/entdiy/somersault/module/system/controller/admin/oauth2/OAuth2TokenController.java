package xyz.entdiy.somersault.module.system.controller.admin.oauth2;

import xyz.entdiy.somersault.framework.common.pojo.CommonResult;
import xyz.entdiy.somersault.framework.common.pojo.PageResult;
import xyz.entdiy.somersault.module.system.controller.admin.oauth2.vo.token.OAuth2AccessTokenPageReqVO;
import xyz.entdiy.somersault.module.system.controller.admin.oauth2.vo.token.OAuth2AccessTokenRespVO;
import xyz.entdiy.somersault.module.system.convert.auth.OAuth2TokenConvert;
import xyz.entdiy.somersault.module.system.dal.dataobject.oauth2.OAuth2AccessTokenDO;
import xyz.entdiy.somersault.module.system.enums.logger.LoginLogTypeEnum;
import xyz.entdiy.somersault.module.system.service.auth.AdminAuthService;
import xyz.entdiy.somersault.module.system.service.oauth2.OAuth2TokenService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static xyz.entdiy.somersault.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - OAuth2.0 令牌")
@RestController
@RequestMapping("/system/oauth2-token")
public class OAuth2TokenController {

    @Resource
    private OAuth2TokenService oauth2TokenService;
    @Resource
    private AdminAuthService authService;

    @GetMapping("/page")
    @Operation(summary = "获得访问令牌分页", description = "只返回有效期内的")
    @PreAuthorize("@ss.hasPermission('system:oauth2-token:page')")
    public CommonResult<PageResult<OAuth2AccessTokenRespVO>> getAccessTokenPage(@Valid OAuth2AccessTokenPageReqVO reqVO) {
        PageResult<OAuth2AccessTokenDO> pageResult = oauth2TokenService.getAccessTokenPage(reqVO);
        return success(OAuth2TokenConvert.INSTANCE.convert(pageResult));
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除访问令牌")
    @Parameter(name = "accessToken", description = "访问令牌", required = true, example = "tudou")
    @PreAuthorize("@ss.hasPermission('system:oauth2-token:delete')")
    public CommonResult<Boolean> deleteAccessToken(@RequestParam("accessToken") String accessToken) {
        authService.logout(accessToken, LoginLogTypeEnum.LOGOUT_DELETE.getType());
        return success(true);
    }

}
