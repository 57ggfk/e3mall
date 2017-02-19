package cn.e3mall.search.listener;

import cn.e3mall.manager.mapper.ext.TbItemExtMapper;
import cn.e3mall.manager.po.ext.TbItemExt;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.net.InetAddress;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * 商品添加监听用户es索引同步
 * Created by wangji on 2017/2/4.
 */
public class ItemMessageListener implements MessageListener {

    @Autowired
    private TbItemExtMapper extMapper;

    @Resource(name="client")
    private Client client;

    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            ActiveMQTextMessage txtMessage = (ActiveMQTextMessage) message;
            //TransportClient client = null;
            try {
                //获取客户端
/*
                Settings settings = Settings.settingsBuilder()
                        .put("cluster.name", "elasticsearch")
                        .put("node.name","node").build();
                         client = TransportClient.builder().settings(settings).build();
                client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.116.138"),9300));
*/

                //获取商品id
                Long itemId = Long.valueOf(txtMessage.getText());
                //查询商品信息
                TbItemExt item = extMapper.queryItemById(itemId);
                //获取第一张图片
                String image = "";
                String[] images = item.getImages();
                if (images != null && images.length > 0) {
                    image = images[0];
                }
                //创建文档
                XContentBuilder builder = jsonBuilder()
                        .startObject()
                            .field("title",item.getTitle())
                            .field("sell_point",item.getSellPoint())
                            .field("price",item.getPrice())
                            .field("description",item.getDescription())
                            .field("category_name",item.getCatName())
                            .field("image",image)
                        .endObject();
                //索引文档
                IndexResponse response = client.prepareIndex("e3mall", "item", item.getId().toString())
                        .setSource(builder).get();
                System.err.println(response.toString());
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
