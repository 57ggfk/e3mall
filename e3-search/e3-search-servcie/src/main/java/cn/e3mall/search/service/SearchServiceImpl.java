package cn.e3mall.search.service;

import cn.e3mall.common.po.E3Result;
import cn.e3mall.manager.mapper.ext.TbItemExtMapper;
import cn.e3mall.manager.po.TbItem;
import cn.e3mall.manager.po.ext.TbItemExt;
import com.alibaba.dubbo.common.utils.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangji on 2017/1/17.
 */
@Service
public class SearchServiceImpl implements SearchService {
    public static final int PAGE_SIZE = 20;
    @Autowired
    private TbItemExtMapper extMapper;
    @Autowired
    private SolrServer solrServer;
    @Override
    public E3Result importAll() {
        //查询所有从数据库
        List<TbItemExt> list = extMapper.queryItemAll();

        //solrService的api实现导入
        List<SolrInputDocument> docs = new ArrayList<>();
        SolrInputDocument doc;
        for (TbItemExt item : list) {
            doc = new SolrInputDocument();
            doc.addField("id",item.getId());
            doc.addField("item_title", item.getTitle());
            doc.addField("item_sell_point", item.getSellPoint());
            doc.addField("item_price", item.getPrice());
            doc.addField("item_description", item.getDescription());
            doc.addField("item_category_name", item.getCatName());
            //处理多图片，只要第一张
            if (item.getImage() != null) {
                String[] arr = item.getImage().split(",");
                doc.addField("item_image",arr[0]);
            }
            docs.add(doc);
        }

        //调用solrServer导入
        try {
            solrServer.add(docs);
            //提交
            solrServer.commit();
        } catch (Exception e) {
            e.printStackTrace();
            return E3Result.build(500,"添加索引失败");
        }
        //返回导入成功状态
        return E3Result.ok();
    }

    @Override
    public Map<String, Object> search(String q, Integer page) throws Exception{
        //solrQuery
        SolrQuery query = new SolrQuery();

        //关键字
        if (StringUtils.isBlank(q)) {
            query.setQuery("*:*");
        } else {
            query.setQuery(q);
        }

        //默认域
        query.set("df","item_keywords");
        //设置分页条件
        if (page ==null || page<1) {
            page = 1;
        }
        query.setStart((page-1)* PAGE_SIZE);
        query.setRows(PAGE_SIZE);

        //高亮
        query.setHighlight(true);
        query.addHighlightField("item_title");
        query.setHighlightSimplePre("<span style='color:red'>");
        query.setHighlightSimplePost("</span>");

        //执行搜索返回结果
        QueryResponse response = solrServer.query(query);
        SolrDocumentList results = response.getResults();

        //封装查询结果
        List<TbItem> itemList = new ArrayList<>();

        TbItem item;

        //获取高亮
        Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();

        for (SolrDocument result : results) {
            item = new TbItem();
            //设置商品id
            try {
                item.setId(Long.parseLong(result.get("id").toString()));
            } catch (Exception e) {
                System.err.println(result.get("id")+" can't convert to Long");
                continue;
            }

            //商品名称高亮
            List<String> list = highlighting.get(result.get("id")).get("item_title");
            if (list != null && list.size() > 0) {
                item.setTitle(list.get(0));
            } else {
                item.setTitle(result.get("item_title").toString());
            }
            item.setPrice(Long.parseLong(result.get("item_price").toString()));
            item.setImage(result.get("item_image").toString());
            itemList.add(item);
        }
        //封装结果map
        Map<String,Object> map = new HashMap<>();
        map.put("itemList",itemList);

        //获取总记录数
        long totalRecords = results.getNumFound();
        map.put("totalRecords",totalRecords);

        //calculate pages
        int totalPages = (int) (totalRecords / PAGE_SIZE);
        if (totalRecords % PAGE_SIZE >0) {
            totalPages++ ;
        }
        map.put("totalPages",totalPages);

        //返回map
        return map;
    }

}
