package jedis;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import javax.print.attribute.HashPrintServiceAttributeSet;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by wangji on 2017/1/16.
 */
public class TestJedis {
    private int port = 6379;
    private String host = "192.168.116.138";

    /**
     * 单实例连接
     */
    @Test
    public void testJedis() {
        Jedis jedis = new Jedis(host,port);
        jedis.set("xx","xyz");
        String xx = jedis.get("xx");
        System.err.println(xx);
        jedis.close();
    }

    @Test
    public void testJedisPool() {
        JedisPool pool = new JedisPool(host,port);
        Jedis jedis = pool.getResource();
        jedis.set("pool","jedispool");
        String value = jedis.get("pool");
        System.err.println(value);
    }

    @Test
    public void testCluster () {
        Set<HostAndPort> nodes = new HashSet<>();
        for (int i = 7001; i < 7007; i++) {
            nodes.add(new HostAndPort("192.168.116.138",i));
        }
        JedisCluster cluster = new JedisCluster(nodes);
        //用法通jedis
        cluster.set("cluster","xxxxx");
        String value = cluster.get("cluster");
        System.err.println(value);
    }
    
    @Test
    public void testSpringJedis() {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
        JedisPool pool = context.getBean(JedisPool.class);
        Jedis jedis = pool.getResource();
        jedis.set("xx","xzy");
        String xx = jedis.get("xx");
        System.out.println(xx);

    }
    
    @Test
    public void testSpring4JedisCluster() {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
        JedisCluster cluster = context.getBean(JedisCluster.class);
        cluster.set("springCluster","springCluster");
        String value = cluster.get("springCluster");
        System.out.println(value);
    }

}
