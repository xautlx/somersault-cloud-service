package xyz.entdiy.somersault.framework.sms.core.client;

import xyz.entdiy.somersault.framework.common.exception.ErrorCode;
import xyz.entdiy.somersault.framework.sms.core.enums.SmsFrameworkErrorCodeConstants;

import java.util.function.Function;

/**
 * 将 API 的错误码，转换为通用的错误码
 *
 * @see SmsCommonResult
 * @see SmsFrameworkErrorCodeConstants
 *
 * @author entdiy.xyz
 */
public interface SmsCodeMapping extends Function<String, ErrorCode> {
}
