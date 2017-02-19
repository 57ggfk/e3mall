package cn.e3mall.item.listener;

import cn.e3mall.manager.po.TbItem;
import cn.e3mall.manager.po.ext.TbItemExt;
import cn.e3mall.manager.service.ItemService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangji on 2017/2/5.
 */
public class ItemMessageListener implements MessageListener{

    @Autowired
    private ItemService service;

    @Autowired
    private FreeMarkerConfigurer configurer;

    @Value(value = "${ITEM_TEMPLATE_NAME:item.ftl}")
    private String ITEM_TEMPLATE_NAME;
    @Value(value = "${ITEM_HTML_PATH:D:/freemarker/out/item/}")
    private String ITEM_HTML_PATH;
    @Value(value = "${ITEM_HTML_EXT:.html}")
    private String ITEM_HTML_EXT;

    @Override
    public void onMessage(Message message) {
        //获取商品id
        Long itemId = 0L;
        if(message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
            if (textMessage != null) {
                try {
                    itemId = Long.parseLong(textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
        //查询商品信息
        TbItem item = service.queryItemById(itemId);
        TbItemExt itemExt = new TbItemExt();
        if (item != null) {
            BeanUtils.copyProperties(item,itemExt);
        }

        //封装商品数据
        Map<String, Object> map = new HashMap<>();
        map.put("item",itemExt);

        try {
            // 获取商品详情模板
            Configuration configuration = configurer.getConfiguration();
            Template template = configuration.getTemplate(ITEM_TEMPLATE_NAME);

            //指定详情页输出路径
            String htmlPath = ITEM_HTML_PATH+itemId+ITEM_HTML_EXT;
            FileWriter writer = new FileWriter(new File(htmlPath));
            template.process(map,writer);
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
