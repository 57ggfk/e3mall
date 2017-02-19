package freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangji on 2017/1/20.
 */
public class FreemarkerTest {
    @Test
    public void test01() throws Exception {
        Configuration cfg = new Configuration(Configuration.getVersion());
        cfg.setDirectoryForTemplateLoading(
                new File("D:\\Java\\IdeaProjects\\e3mall\\e3-item-web\\src\\main\\resources\\ftl")
        );
        cfg.setDefaultEncoding("utf-8");
        Template template = cfg.getTemplate("hello.ftl");

        Map map = new HashMap<>();
        map.put("hello","hello freemarker");
        //添加以日期类型
        map.put("today", new Date());

        //写对象
        Writer writer = new FileWriter(new File("D:\\freemarker\\out\\hello.html"));
        //数据填充
        template.process(map,writer);
        writer.close();
    }

    @Test
    public void test02() throws Exception {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:applicationContext-freemarker.xml");
        FreeMarkerConfigurer cfg = ctx.getBean(FreeMarkerConfigurer.class);
        Configuration configuration = cfg.getConfiguration();

        Template template = configuration.getTemplate("hello.ftl");

        Map map = new HashMap<>();
        map.put("hello","hello freemarker");
        //添加以日期类型
        map.put("today", new Date());

        //写对象
        Writer writer = new FileWriter(new File("D:\\freemarker\\out\\hello.html"));
        //数据填充
        template.process(map,writer);
        writer.close();

    }
}
