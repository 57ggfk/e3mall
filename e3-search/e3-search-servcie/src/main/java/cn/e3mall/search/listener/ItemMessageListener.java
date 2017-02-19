package cn.e3mall.search.listener;

import cn.e3mall.manager.mapper.ext.TbItemExtMapper;
import cn.e3mall.manager.po.TbItem;
import cn.e3mall.manager.po.ext.TbItemExt;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * 商品添加监听用户Solr索引同步
 * Created by wangji on 2017/1/20.
 */
public class ItemMessageListener implements MessageListener {

    @Autowired
    private SolrServer solrServer;

    @Autowired
    private TbItemExtMapper extMapper;

    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            ActiveMQTextMessage txtMessage = (ActiveMQTextMessage) message;
            try {
                //获取商品id
                Long itemId = Long.valueOf(txtMessage.getText());
                //查询商品信息
                TbItemExt item = extMapper.queryItemById(itemId);
                //创建文档对象
                SolrInputDocument doc = new SolrInputDocument();
                doc.addField("id",itemId);
                doc.addField("item_title", item.getTitle());
                doc.addField("item_sell_point", item.getSellPoint());
                doc.addField("item_category_name", item.getCatName());
                doc.addField("item_price", item.getPrice());
                //数据库中的image字段可以存储多张图片，而页面中只需要一张图片，需要进行处理
                if( item.getImage() != null){
                    String[] arr = item.getImage().split(",");
                    doc.addField("item_image", arr[0]);
                }
                doc.addField("item_desc", item.getDescription());
                // 添加到索引库
                solrServer.add(doc);
                // 提交
                solrServer.commit();

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
