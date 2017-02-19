package cn.e3mall.search.service;

import cn.e3mall.common.po.E3Result;
import cn.e3mall.manager.mapper.ext.TbItemExtMapper;
import cn.e3mall.manager.po.TbItem;
import cn.e3mall.manager.po.ext.TbItemExt;
import com.alibaba.dubbo.common.utils.StringUtils;
import org.elasticsearch.action.ListenableActionFuture;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * Java API [2.4] | Elastic
 * https://www.elastic.co/guide/en/elasticsearch/client/java-api/2.4/index.html
 * Created by wangji on 2017/1/18.
 */
@Service
public class SearchServiceImpl implements SearchService {
    //private static int progressPercentageValue = 0;
    private static final int PAGE_SIZE = 20;
    @Autowired
    private TbItemExtMapper extMapper;

    @Resource(name="client")
    private Client client;

    @Override
    public E3Result importAll() {
        //设置处理进度
        //progressPercentageValue = 0;
        //TransportClient client = null;
        try {
            //配置集群
            /*Settings settings = Settings.settingsBuilder()
                    .put("cluster.name", "elasticsearch")
                    .put("node.name","node").build();*/
            //TransportClient client = TransportClient.builder().settings(settings).build();
            //开启嗅探
            /*Settings settings = Settings.settingsBuilder()
                    .put("client.transport.sniff", true).build();
            TransportClient client = TransportClient.builder().settings(settings).build();*/

            //获取客户端
/*            Settings settings = Settings.settingsBuilder()
                    .put("cluster.name", "elasticsearch")
                    .put("node.name","node").build();
            client = TransportClient.builder().settings(settings).build();
            client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.116.138"),9300));
*/

            //查询数据库
            List<TbItemExt> list = extMapper.queryItemAll();
            for (TbItemExt itemExt : list) {
                //取第一张图片
                String image = "";
                String[] split = itemExt.getImage().split(",");
                if (split!=null && split.length>0) {
                    image = split[0];
                }
                //创建文档
                XContentBuilder builder = jsonBuilder()
                        .startObject()
                            .field("title",itemExt.getTitle())
                            .field("sell_point",itemExt.getSellPoint())
                            .field("price",itemExt.getPrice())
                            .field("description",itemExt.getDescription())
                            .field("category_name",itemExt.getCatName())
                            .field("image",image)
                        .endObject();

                //索引文档
                IndexResponse response = client
                        .prepareIndex("e3mall", "item", itemExt.getId().toString())
                        .setSource(builder).get();
                /*System.err.println(response.getIndex()+"/"+response.getType()+"/"+response.getId()
                        +";Version:"+response.getVersion()+";Created:"+response.isCreated());*/
                //System.out.println(response.toString());
            }

        } catch (IOException e) {
            E3Result.build(500,"导入索引失败，请联系管理员");
            e.printStackTrace();
        } /*finally {
            if (client!=null) {
                client.close();
            }
        }*/

        return E3Result.ok();
    }

    @Override
    public Map<String, Object> search(String q, Integer page) throws Exception {

        //TransportClient client = null;
        try {
            //获取客户端
/*            Settings settings = Settings.settingsBuilder()
                    .put("cluster.name", "elasticsearch")
                    .put("node.name","node").build();
            client = TransportClient.builder().settings(settings).build();
            client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.116.138"),9300));*/

            SearchRequestBuilder query = client.prepareSearch("e3mall").setTypes("item");

            if (StringUtils.isBlank(q)) {
                //这个没有解决
                query.setQuery(QueryBuilders.queryStringQuery("*:*"));
            } else {
                //尝试_all影响高亮域
                //query.setQuery(matchQuery("title",q));
                //query.setQuery(QueryBuilders.queryStringQuery(q));
                QueryStringQueryBuilder queryBuilder = new QueryStringQueryBuilder(q)
                        .analyzer("ik_smart")
                        .field("title")
                        .field("description")
                        .field("sell_point");
                query.setQuery(queryBuilder);
            }
            //按查询匹配度排序
            query.setExplain(true);

            //设置分页
            if (page == null || page <1 ) {
                page = 1;
            }
            query.setFrom((page-1)*PAGE_SIZE);
            query.setSize(PAGE_SIZE);

            //设置高亮
            query.setHighlighterFilter(true);
            query.addHighlightedField("title");
            query.setHighlighterPreTags("<span style='color:red'>");
            query.setHighlighterPostTags("</span>");

            //执行搜索
            ListenableActionFuture<SearchResponse> execute = query.execute();
            SearchResponse response = execute.actionGet();
            System.out.println(query.toString());

            //返回结果
            SearchHits hits = response.getHits();

            //封装返回结果
            Map<String,Object> map = new HashMap<>();

            //保存返回结果
            List<TbItem> itemList = new ArrayList<>();
            TbItem item = null;
            for (SearchHit hit : hits) {
                item = new TbItem();
                if (hit.getId() != null) {
                    try {
                        item.setId(Long.valueOf(hit.getId()));
                    } catch (Exception e) {
                        System.err.println(hit.getId()+" can't convert to Long");
                        continue;
                    }
                }

                //商品名称高亮
                Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                System.err.println("highlightFields:"+highlightFields);
                if (highlightFields != null && highlightFields.size()>0) {
                    item.setTitle(highlightFields.get("title").fragments()[0].toString());
                } else {
                    item.setTitle(hit.getSource().get("title").toString());
                }

                item.setPrice(Long.valueOf(hit.getSource().get("price").toString()));
                item.setImage(hit.getSource().get("image").toString());
                itemList.add(item);
            }
            map.put("itemList",itemList);

            //获取总记录数
            long totalHits = hits.getTotalHits();
            map.put("totalRecords",totalHits);

            //计算页数
            int totalPages = (int)(totalHits / PAGE_SIZE);
            if (totalPages % PAGE_SIZE > 0) {
                totalPages++;
            }
            map.put("totalPages",totalPages);

            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}
