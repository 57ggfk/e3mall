package test;

import javafx.application.Application;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangji on 2017/1/20.
 */
public class SolrCloudTest {

    private SolrServer solrServer;

    @Before
    public void getServer() {
        String zkHost = "192.168.116.138:2281,192.168.116.138:2282,192.168.116.138:2283";
        String defaultCollection = "collection2";
        CloudSolrServer solrCloudServer = new CloudSolrServer(zkHost);
        solrCloudServer.setDefaultCollection(defaultCollection);
        solrServer = solrCloudServer;
    }

    @Test
    public void createIndexTest() throws Exception {
        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("id","1000000001");
        doc.addField("name","solrj connnection to solrcloud");
        solrServer.add(doc);
        solrServer.commit();
    }

    @Test
    public void spring4solrcloud() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-solr.xml");
        CloudSolrServer solrServer = context.getBean(CloudSolrServer.class);
        SolrInputDocument doc = new SolrInputDocument();
        doc.addField("id","1000000002");
        doc.addField("name","solrj integration spring connnection to solrcloud");
        solrServer.add(doc);
        solrServer.commit();
    }

    @Test
    public void testDelete() throws Exception {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-solr.xml");
        CloudSolrServer solrServer = ctx.getBean(CloudSolrServer.class);
        SolrInputDocument doc = new SolrInputDocument();
        List<String> ids = new ArrayList<>();
        ids.add("solrcloud01");
        ids.add("solrcloud02");
        ids.add("solrcloud03");
        ids.add("change.me");
        solrServer.deleteById(ids);
        solrServer.commit();
    }

}
