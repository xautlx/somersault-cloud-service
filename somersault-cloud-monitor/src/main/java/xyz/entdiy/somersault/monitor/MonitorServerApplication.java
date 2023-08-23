package xyz.entdiy.somersault.monitor;

import com.alibaba.druid.admin.config.DruidProperties;
import com.alibaba.druid.admin.servlet.MonitorViewServlet;
import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
import de.codecentric.boot.admin.server.config.AdminServerProperties;
import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.lang.Nullable;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@SpringBootApplication(scanBasePackages = "com.alibaba.druid.admin")
@EnableConfigurationProperties({DruidProperties.class, AdminServerProperties.class})
@EnableAdminServer
public class MonitorServerApplication {

    public static void main(String[] args) {
        // 启动 Spring Boot 应用
        SpringApplication.run(MonitorServerApplication.class, args);
    }

    @Bean
    public ServletRegistrationBean statViewServletRegistrationBean(DruidProperties properties) {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean();
        registrationBean.setServlet(new MonitorViewServlet());
        registrationBean.addUrlMappings(getUrlMappings(properties));
        if (!StringUtils.isEmpty(properties.getLoginUsername())) {
            registrationBean.addInitParameter("loginUsername", properties.getLoginUsername());
        }
        if (!StringUtils.isEmpty(properties.getLoginPassword())) {
            registrationBean.addInitParameter("loginPassword", properties.getLoginPassword());
        }
        return registrationBean;
    }


    private String getUrlMappings(DruidProperties properties) {
        String urlMapping = "/druid/*";
        if (StringUtils.isEmpty(properties.getContextPath())) {
            return urlMapping;
        }
        if (!properties.getContextPath().startsWith("/") || properties.getContextPath().endsWith("/")) {
            throw new IllegalArgumentException("Druid ContextPath must start with '/' and not end with '/'");
        }
        return properties.getContextPath() + "/*";
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            @Nullable HttpSecurity http,
            AdminServerProperties adminServerProperties,
            DruidProperties druidProperties) throws Exception {
        String adminContextPath = adminServerProperties.getContextPath();
        SavedRequestAwareAuthenticationSuccessHandler handler = new SavedRequestAwareAuthenticationSuccessHandler();
        handler.setTargetUrlParameter("redirectTo");
        handler.setDefaultTargetUrl(adminContextPath + "/");

        String druidPath = getUrlMappings(druidProperties) + "*";
        // 启用HTTP-Basic支持。这是Spring Boot Admin Client注册所必需的
        http.httpBasic().and()
                // 授予对所有静态资产的公共访问权限
                .authorizeRequests().antMatchers(adminContextPath + "/assets/**").permitAll()
                // Druid访问放行，使用Druid内置登录认证
                .antMatchers(druidPath, "/actuator/**").permitAll()
                // 授予对登录页面的公共访问权限
                .antMatchers(adminContextPath + "/login").permitAll().and()
                // 所有请求都需要验证登录
                .authorizeRequests().anyRequest().authenticated().and()
                // 登录表单
                .formLogin().loginPage(adminContextPath + "/login").successHandler(handler).and()
                // 登出表单
                .logout().logoutUrl(adminContextPath + "/logout").and()
                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).ignoringAntMatchers(
                        // 将服务注册的接口暴露出去.
                        adminContextPath + "/instances",
                        adminContextPath + "/actuator/**",
                        druidPath);

        return http.build();
    }
}
