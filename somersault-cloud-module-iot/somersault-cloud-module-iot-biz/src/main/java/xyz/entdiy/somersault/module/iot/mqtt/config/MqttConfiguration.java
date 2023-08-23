package xyz.entdiy.somersault.module.iot.mqtt.config;

import xyz.entdiy.somersault.module.iot.service.mqttserver.MqttServerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Slf4j
@Configuration
public class MqttConfiguration {

    @Resource
    public MqttServerService mqttServerService;

    @PostConstruct
    public void init() {
        mqttServerService.buildMqttClients();
    }
}
