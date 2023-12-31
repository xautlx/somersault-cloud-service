package xyz.entdiy.somersault.gateway.filter.security;

import lombok.Data;

import java.util.List;

/**
 * 登录用户信息
 *
 * copy from somersault-cloud-spring-boot-starter-security 的 LoginUser 类
 *
 * @author theMonkeyKing
 */
@Data
public class LoginUser {

    /**
     * 用户编号
     */
    private Long id;
    /**
     * 用户类型
     */
    private Integer userType;
    /**
     * 租户编号
     */
    private Long tenantId;
    /**
     * 授权范围
     */
    private List<String> scopes;

}
