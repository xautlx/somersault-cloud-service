package xyz.entdiy.somersault.framework.sms.core.client.impl.debug;

import xyz.entdiy.somersault.framework.common.exception.ErrorCode;
import xyz.entdiy.somersault.framework.common.exception.enums.GlobalErrorCodeConstants;
import xyz.entdiy.somersault.framework.sms.core.client.SmsCodeMapping;
import xyz.entdiy.somersault.framework.sms.core.enums.SmsFrameworkErrorCodeConstants;

import java.util.Objects;

/**
 * 钉钉的 SmsCodeMapping 实现类
 *
 * @author theMonkeyKing
 */
public class DebugDingTalkCodeMapping implements SmsCodeMapping {

    @Override
    public ErrorCode apply(String apiCode) {
        return Objects.equals(apiCode, "0") ? GlobalErrorCodeConstants.SUCCESS : SmsFrameworkErrorCodeConstants.SMS_UNKNOWN;
    }

}
