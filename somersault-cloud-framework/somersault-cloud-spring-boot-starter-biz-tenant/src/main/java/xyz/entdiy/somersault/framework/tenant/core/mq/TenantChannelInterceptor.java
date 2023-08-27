package xyz.entdiy.somersault.framework.tenant.core.mq;

import cn.hutool.core.util.ReflectUtil;
import xyz.entdiy.somersault.framework.tenant.core.context.TenantContextHolder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;

import java.util.Map;

import static xyz.entdiy.somersault.framework.web.core.util.WebFrameworkUtils.HEADER_TENANT_ID;

/**
 * 多租户的 {@link ChannelInterceptor} 实现类
 * 发送消息时，设置租户编号到 Header 上
 *
 * @author theMonkeyKing
 */
public class TenantChannelInterceptor implements ChannelInterceptor {

    @Override
    @SuppressWarnings({"unchecked", "NullableProblems"})
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        Long tenantId = TenantContextHolder.getTenantId();
        if (tenantId != null) {
            Map<String, Object> headers = (Map<String, Object>) ReflectUtil.getFieldValue(message.getHeaders(), "headers");
            headers.put(HEADER_TENANT_ID, tenantId);
        }
        return message;
    }

}
