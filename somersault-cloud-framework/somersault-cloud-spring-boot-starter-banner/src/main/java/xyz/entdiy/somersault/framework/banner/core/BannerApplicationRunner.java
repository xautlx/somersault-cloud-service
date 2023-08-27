package xyz.entdiy.somersault.framework.banner.core;

import cn.hutool.core.thread.ThreadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.util.concurrent.TimeUnit;

/**
 * 项目启动成功后，提供文档相关的地址
 *
 * @author xyz.entdiy
 */
@Slf4j
public class BannerApplicationRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) {
        ThreadUtil.execute(() -> {
            ThreadUtil.sleep(1, TimeUnit.SECONDS); // 延迟 1 秒，保证输出到结尾
            log.info("\n----------------------------------------------------------" +
                            "\n\t项目启动成功！" +
                            "\n\t接口文档: \t{} " +
                            "\n\t开发文档: \t{} " +
                            "\n----------------------------------------------------------",
                    "https://127.0.0.1/api-doc/",
                    "https://127.0.0.1");
        });
    }

}
