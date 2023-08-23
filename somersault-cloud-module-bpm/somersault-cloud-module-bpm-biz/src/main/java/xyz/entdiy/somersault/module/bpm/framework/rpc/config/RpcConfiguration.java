package xyz.entdiy.somersault.module.bpm.framework.rpc.config;

import xyz.entdiy.somersault.module.system.api.dept.DeptApi;
import xyz.entdiy.somersault.module.system.api.dept.PostApi;
import xyz.entdiy.somersault.module.system.api.dict.DictDataApi;
import xyz.entdiy.somersault.module.system.api.permission.RoleApi;
import xyz.entdiy.somersault.module.system.api.sms.SmsSendApi;
import xyz.entdiy.somersault.module.system.api.user.AdminUserApi;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableFeignClients(clients = {RoleApi.class, DeptApi.class, PostApi.class, AdminUserApi.class, SmsSendApi.class, DictDataApi.class})
public class RpcConfiguration {
}
