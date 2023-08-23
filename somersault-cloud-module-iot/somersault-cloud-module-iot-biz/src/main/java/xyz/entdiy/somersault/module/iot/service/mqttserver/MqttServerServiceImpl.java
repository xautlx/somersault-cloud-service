package xyz.entdiy.somersault.module.iot.service.mqttserver;

import xyz.entdiy.somersault.framework.common.util.collection.CollectionUtils;
import xyz.entdiy.somersault.framework.env.core.util.EnvUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;
import xyz.entdiy.somersault.module.iot.controller.admin.mqttserver.vo.*;
import xyz.entdiy.somersault.module.iot.dal.dataobject.mqttserver.MqttServerDO;
import xyz.entdiy.somersault.framework.common.pojo.PageResult;

import xyz.entdiy.somersault.module.iot.convert.mqttserver.MqttServerConvert;
import xyz.entdiy.somersault.module.iot.dal.mysql.mqttserver.MqttServerMapper;

import static xyz.entdiy.somersault.framework.common.exception.util.ServiceExceptionUtil.exception;
import static xyz.entdiy.somersault.module.iot.enums.ErrorCodeConstants.*;
import static org.eclipse.paho.client.mqttv3.MqttConnectOptions.MQTT_VERSION_3_1_1;

/**
 * MQTT服务节点配置信息 Service 实现类
 *
 * @author entdiy.xyz
 */
@Service
@Validated
@Slf4j
public class MqttServerServiceImpl implements MqttServerService {

    @Resource
    private MqttServerMapper mqttServerMapper;

    private Map<Long, MqttClient> mqttClients;

    @Override
    public Long createMqttServer(MqttServerCreateReqVO createReqVO) {
        // 插入
        MqttServerDO mqttServer = MqttServerConvert.INSTANCE.convert(createReqVO);
        mqttServerMapper.insert(mqttServer);
        // 返回
        return mqttServer.getId();
    }

    @Override
    public void updateMqttServer(MqttServerUpdateReqVO updateReqVO) {
        // 校验存在
        validateMqttServerExists(updateReqVO.getId());
        // 更新
        MqttServerDO updateObj = MqttServerConvert.INSTANCE.convert(updateReqVO);
        mqttServerMapper.updateById(updateObj);
    }

    @Override
    public void deleteMqttServer(Long id) {
        // 校验存在
        validateMqttServerExists(id);
        // 删除
        mqttServerMapper.deleteById(id);
    }

    private void validateMqttServerExists(Long id) {
        if (mqttServerMapper.selectById(id) == null) {
            throw exception(MQTT_SERVER_NOT_EXISTS);
        }
    }

    @Override
    public MqttServerDO getMqttServer(Long id) {
        return mqttServerMapper.selectById(id);
    }

    @Override
    public List<MqttServerDO> getMqttServerList(Collection<Long> ids) {
        return mqttServerMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<MqttServerDO> getMqttServerPage(MqttServerPageReqVO pageReqVO) {
        return mqttServerMapper.selectPage(pageReqVO);
    }

    @Override
    public List<MqttServerDO> getMqttServerList(MqttServerExportReqVO exportReqVO) {
        return mqttServerMapper.selectList(exportReqVO);
    }

    @Override
    public Map<Long, MqttClient> buildMqttClients() {
        if (mqttClients == null) {
            mqttClients = new HashMap<>();
            List<MqttServerDO> mqttServerDOList = mqttServerMapper.selectList();
            for (MqttServerDO mqttServerDO : mqttServerDOList) {
                if ("easemob".equals(mqttServerDO.getType())) {
                    try {
                        String endpoint = mqttServerDO.getEndpoint(); //环信MQTT服务器地址 通过console后台[MQTT]->[服务概览]->[服务配置]下[连接地址]获取
                        Integer port = mqttServerDO.getPort(); // 协议服务端口 通过console后台[MQTT]->[服务概览]->[服务配置]下[连接端口]获取
                        String appID = mqttServerDO.getAppId(); // appID 通过console后台[MQTT]->[服务概览]->[服务配置]下[AppID]获取
                        //ServerClient_
                        String deviceId = "SC_" + StringUtils.replace(EnvUtils.getHostName(), ".", "_"); // 自定义deviceID
                        String restapi = "https://api.cn1.mqtt.chat/app/" + appID; //环信MQTT REST API地址 通过console后台[MQTT]->[服务概览]->[服务配置]下[REST API地址]获取
                        String appClientId = mqttServerDO.getAppClientId(); //开发者ID 通过console后台[应用概览]->[应用详情]->[开发者ID]下[ Client ID]获取
                        String appClientSecret = mqttServerDO.getAppClientSecret(); // 开发者密钥 通过console后台[应用概览]->[应用详情]->[开发者ID]下[ ClientSecret]获取
                        String clientId = deviceId + "@" + appID;
                        String username = mqttServerDO.getAppUsername(); //自定义用户名 长度不超过64位即可

                        String address = mqttServerDO.getProtocol() + "://" + endpoint + ":" + port;
                        log.debug("Start connecting MQTT server= {}, client: {}", address, clientId);

                        // 获取token的URL
                        //https://{restapi}/openapi/rm/app/token
                        // 获取token
                        String token = "";
                        // 取token
                        try (final CloseableHttpClient httpClient = HttpClients.createDefault()) {
                            final HttpPost httpPost = new HttpPost(restapi + "/openapi/rm/app/token");
//                            Map<String, String> params = new HashMap<>();
//                            params.put("appClientId", appClientId);
//                            params.put("appClientSecret", appClientSecret);
                            String param = "{\"appClientId\":\"" + appClientId + "\",\"appClientSecret\":\"" + appClientSecret + "\"}";
                            //设置请求体参数
                            StringEntity entity = new StringEntity(param, Charset.forName("utf-8"));
                            entity.setContentEncoding("utf-8");
                            httpPost.setEntity(entity);
                            //设置请求头部
                            httpPost.setHeader("Content-Type", "application/json");
                            //执行请求，返回请求响应
                            try (final CloseableHttpResponse response = httpClient.execute(httpPost)) {
                                //请求返回状态码
                                int statusCode = response.getStatusLine().getStatusCode();
                                //请求成功
                                if (statusCode == HttpStatus.SC_OK && statusCode <= HttpStatus.SC_TEMPORARY_REDIRECT) {
                                    //取出响应体
                                    final HttpEntity entity2 = response.getEntity();
                                    //从响应体中解析出token
                                    String responseBody = EntityUtils.toString(entity2, "utf-8");
//                                    JSONObject jsonObject = JSONObject.parseObject(responseBody);
                                    token = StringUtils.substringBefore(StringUtils.substringAfter(responseBody, "access_token\":\""), "\"");
                                } else {
                                    //请求失败
                                    throw new ClientProtocolException("请求失败，响应码为：" + statusCode);
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        String mqtt_token = "";
                        // 取token
                        try (final CloseableHttpClient httpClient = HttpClients.createDefault()) {
                            final HttpPost httpPost = new HttpPost(restapi + "/openapi/rm/user/token");
//                            Map<String, String> params = new HashMap<>();
//                            params.put("username", username);
//                            params.put("cid", clientId);
                            //设置请求体参数
                            StringEntity entity = new StringEntity("{\"username\":\"" + username + "\",\"cid\":\"" + clientId + "\"}", Charset.forName("utf-8"));
                            entity.setContentEncoding("utf-8");
                            httpPost.setEntity(entity);
                            //设置请求头部
                            httpPost.setHeader("Content-Type", "application/json");
                            httpPost.setHeader("Authorization", token);
                            //请求响应
                            try (final CloseableHttpResponse response = httpClient.execute(httpPost)) {
                                //请求返回状态码
                                int statusCode = response.getStatusLine().getStatusCode();
                                //请求成功
                                if (statusCode == HttpStatus.SC_OK && statusCode <= HttpStatus.SC_TEMPORARY_REDIRECT) {
                                    //取出响应体
                                    final HttpEntity entity2 = response.getEntity();
                                    //从响应体中解析出token
                                    String responseBody = EntityUtils.toString(entity2, "utf-8");
//                                    JSONObject jsonObject = JSONObject.parseObject(responseBody);
//                                    mqtt_token = jsonObject.getJSONObject("body").getString("access_token");
                                    mqtt_token = StringUtils.substringBefore(StringUtils.substringAfter(responseBody, "access_token\":\""), "\"");
                                } else {
                                    //请求失败
                                    throw new ClientProtocolException("请求失败，响应码为：" + statusCode);
                                }
                            }
                            //执行请求，返回请求响应
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        /**
                         * QoS参数代表传输质量，可选0，1，2。详细信息，请参见名词解释。
                         */
                        final int qosLevel = 0;
                        final MemoryPersistence memoryPersistence = new MemoryPersistence();

                        /**
                         * 客户端协议和端口。客户端使用的协议和端口必须匹配，如果是ws或者wss使用http://，如果是mqtt或者mqtts使用tcp://
                         */
                        final MqttClient mqttClient = new MqttClient(address, clientId, memoryPersistence);
                        /**
                         * 设置客户端发送超时时间，防止无限阻塞。
                         */
                        mqttClient.setTimeToWait(5000);


                        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
                        /**
                         * 用户名，在console中注册
                         */
                        mqttConnectOptions.setUserName(username);
                        /**
                         * 用户密码为第一步中申请的token
                         */
                        mqttConnectOptions.setPassword(mqtt_token.toCharArray());
                        mqttConnectOptions.setCleanSession(true);
                        mqttConnectOptions.setKeepAliveInterval(45);
                        mqttConnectOptions.setAutomaticReconnect(true);
                        mqttConnectOptions.setMqttVersion(MQTT_VERSION_3_1_1);
                        mqttConnectOptions.setConnectionTimeout(5000);
                        mqttClient.connect(mqttConnectOptions);
                        log.info("Connected to MQTT server= {}, client: {}", address, clientId);

                        mqttClients.put(mqttServerDO.getId(), mqttClient);
                    } catch (Exception e) {
                        log.error("Build MQTT Client " + mqttServerDO.getType() + ":" + mqttServerDO.getCode() + " ERROR", e);
                    }
                }
            }
        }
        return mqttClients;
    }

    @Override
    public Map<Long, MqttServerDO> getMqttServerMap() {
        List<MqttServerDO> list = mqttServerMapper.selectList();
        return CollectionUtils.convertMap(list, MqttServerDO::getId);
    }

    @Override
    public List<MqttServerDO> getMqttServerListByStatus(Integer status) {
        return mqttServerMapper.selectListByStatus(status);
    }
}
