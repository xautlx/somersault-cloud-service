package xyz.entdiy.somersault.module.iot.enums;

import xyz.entdiy.somersault.framework.common.exception.ErrorCode;

/**
 * tempate 模块错误码枚举类
 *
 * @see ErrorCode
 * iot 系统，使用 1-100-001-001 段
 */
public interface ErrorCodeConstants {

    ErrorCode MQTT_SERVER_NOT_EXISTS = new ErrorCode(1100001001, "MQTT服务信息不存在");

    ErrorCode DEVICE_INFO_NOT_EXISTS = new ErrorCode(1100001002, "设备信息不存在");

    ErrorCode DEVICE_TOKEN_INVALID = new ErrorCode(1100001003, "设备注册码不正确");
}
