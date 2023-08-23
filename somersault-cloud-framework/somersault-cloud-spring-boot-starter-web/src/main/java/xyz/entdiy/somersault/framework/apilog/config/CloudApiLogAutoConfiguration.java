package xyz.entdiy.somersault.framework.apilog.config;

import xyz.entdiy.somersault.framework.apilog.core.filter.ApiAccessLogFilter;
import xyz.entdiy.somersault.framework.apilog.core.service.ApiAccessLogFrameworkService;
import xyz.entdiy.somersault.framework.apilog.core.service.ApiAccessLogFrameworkServiceImpl;
import xyz.entdiy.somersault.framework.apilog.core.service.ApiErrorLogFrameworkService;
import xyz.entdiy.somersault.framework.apilog.core.service.ApiErrorLogFrameworkServiceImpl;
import xyz.entdiy.somersault.framework.common.enums.WebFilterOrderEnum;
import xyz.entdiy.somersault.framework.web.config.WebProperties;
import xyz.entdiy.somersault.framework.web.config.CloudWebAutoConfiguration;
import xyz.entdiy.somersault.module.infra.api.logger.ApiAccessLogApi;
import xyz.entdiy.somersault.module.infra.api.logger.ApiErrorLogApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.servlet.Filter;

@AutoConfiguration(after = CloudWebAutoConfiguration.class)
public class CloudApiLogAutoConfiguration {

    @Bean
    public ApiAccessLogFrameworkService apiAccessLogFrameworkService(ApiAccessLogApi apiAccessLogApi) {
        return new ApiAccessLogFrameworkServiceImpl(apiAccessLogApi);
    }

    @Bean
    public ApiErrorLogFrameworkService apiErrorLogFrameworkService(ApiErrorLogApi apiErrorLogApi) {
        return new ApiErrorLogFrameworkServiceImpl(apiErrorLogApi);
    }

    /**
     * 创建 ApiAccessLogFilter Bean，记录 API 请求日志
     */
    @Bean
    @ConditionalOnProperty(prefix = "biz.access-log", value = "enable", matchIfMissing = true) // 允许使用 biz.access-log.enable=false 禁用访问日志
    public FilterRegistrationBean<ApiAccessLogFilter> apiAccessLogFilter(WebProperties webProperties,
                                                                         @Value("${spring.application.name}") String applicationName,
                                                                         ApiAccessLogFrameworkService apiAccessLogFrameworkService) {
        ApiAccessLogFilter filter = new ApiAccessLogFilter(webProperties, applicationName, apiAccessLogFrameworkService);
        return createFilterBean(filter, WebFilterOrderEnum.API_ACCESS_LOG_FILTER);
    }

    private static <T extends Filter> FilterRegistrationBean<T> createFilterBean(T filter, Integer order) {
        FilterRegistrationBean<T> bean = new FilterRegistrationBean<>(filter);
        bean.setOrder(order);
        return bean;
    }

}
