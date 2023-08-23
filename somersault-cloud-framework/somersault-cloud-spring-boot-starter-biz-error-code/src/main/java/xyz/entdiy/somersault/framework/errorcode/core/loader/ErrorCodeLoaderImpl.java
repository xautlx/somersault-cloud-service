package xyz.entdiy.somersault.framework.errorcode.core.loader;

import cn.hutool.core.collection.CollUtil;
import xyz.entdiy.somersault.framework.common.util.date.DateUtils;
import xyz.entdiy.somersault.module.system.api.errorcode.ErrorCodeApi;
import xyz.entdiy.somersault.module.system.api.errorcode.dto.ErrorCodeRespDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.List;

/**
 * ErrorCodeLoader 的实现类，从 infra 的数据库中，加载错误码。
 *
 * 考虑到错误码会刷新，所以按照 {@link #REFRESH_ERROR_CODE_PERIOD} 频率，增量加载错误码。
 *
 * @author entdiy.xyz
 */
@RequiredArgsConstructor
@Slf4j
public class ErrorCodeLoaderImpl implements ErrorCodeLoader {

    /**
     * 刷新错误码的频率，单位：毫秒
     */
    private static final int REFRESH_ERROR_CODE_PERIOD = 60 * 1000;

    /**
     * 应用分组
     */
    private final String applicationName;
    /**
     * 错误码 Api
     */
    private final ErrorCodeApi errorCodeApi;

    /**
     * 缓存错误码的最大更新时间，用于后续的增量轮询，判断是否有更新
     */
    private LocalDateTime maxUpdateTime;

    @EventListener(ApplicationReadyEvent.class)
    public void loadErrorCodes() {
        try {
            this.loadErrorCodes0();
        } catch (Exception e) {
            log.warn("[execute][初始化调用 system 服务获取错误代码异常，稍后定时任务重试]");
        }
    }

    @Scheduled(fixedDelay = REFRESH_ERROR_CODE_PERIOD, initialDelay = REFRESH_ERROR_CODE_PERIOD)
    public void refreshErrorCodes() {
        this.loadErrorCodes0();
    }

    private void loadErrorCodes0() {
        // 加载错误码
        List<ErrorCodeRespDTO> errorCodeRespDTOs = errorCodeApi.getErrorCodeList(applicationName, maxUpdateTime).getCheckedData();
        if (CollUtil.isEmpty(errorCodeRespDTOs)) {
            return;
        }
        log.info("[loadErrorCodes0][加载到 ({}) 个错误码]", errorCodeRespDTOs.size());

        // 刷新错误码的缓存
        errorCodeRespDTOs.forEach(errorCodeRespDTO -> {
            // 写入到错误码的缓存
            putErrorCode(errorCodeRespDTO.getCode(), errorCodeRespDTO.getMessage());
            // 记录下更新时间，方便增量更新
            maxUpdateTime = DateUtils.max(maxUpdateTime, errorCodeRespDTO.getUpdateTime());
        });
    }

}
