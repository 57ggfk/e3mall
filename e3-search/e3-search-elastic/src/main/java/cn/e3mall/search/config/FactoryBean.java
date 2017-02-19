package cn.e3mall.search.config;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by wangji on 2017/2/10.
 */
@Configuration
public class FactoryBean {
    //注入实例
    @Bean(name="client")
    public Client getESClient() throws UnknownHostException {

        Settings settings = Settings.settingsBuilder()
                .put("cluster.name", "elasticsearch")
                .put("node.name","node").build();
        TransportClient client = TransportClient.builder().settings(settings).build();
        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.116.138"),9300));

        return client;

    }
}
