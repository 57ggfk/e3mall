package cn.e3mall.sso.service;

import cn.e3mall.common.po.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.manager.mapper.TbUserMapper;
import cn.e3mall.manager.po.TbUser;
import cn.e3mall.manager.po.TbUserExample;
import com.alibaba.dubbo.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by wangji on 2017/2/5.
 */
@Service
public class SsoServiceImpl implements SsoService {
    @Autowired
    private TbUserMapper mapper;

    @Override
    public E3Result register(TbUser user) {
        //校验
        if (user == null) {
            return E3Result.build(400,"非法操作");
        }
        if (StringUtils.isBlank(user.getUsername())) {
            return E3Result.build(400,"用户名不能为空");
        }
        if (StringUtils.isBlank(user.getPassword())) {
            return E3Result.build(400,"密码不能为空");
        }
        //唯一性校验，用户名和手机号
        TbUserExample example = new TbUserExample();
        example.createCriteria().andUsernameEqualTo(user.getUsername());
        List<TbUser> uniqueUsername = mapper.selectByExample(example);
        if (uniqueUsername != null && uniqueUsername.size() > 0) {
            return E3Result.build(400,"用户名已被占用");
        }
        //TbUserExample phoneExample = new TbUserExample();
        //phoneExample.createCriteria().andPhoneEqualTo(user.getPhone());
        example.clear();    //清空原来的条件
        example.createCriteria().andPhoneEqualTo(user.getPhone());
        List<TbUser> uniquePhone = mapper.selectByExample(example);
        if (uniquePhone != null && uniquePhone.size() > 0) {
            return E3Result.build(400,"手机号已注册");
        }
        //密码加密
        String md5Password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(md5Password);
        //属性补全
        Date date = new Date();
        user.setCreated(date);
        user.setUpdated(date);
        mapper.insert(user);
        return E3Result.ok();
    }

//    @Autowired
//    private JedisPool pool;
    @Autowired
    private JedisCluster jedis;

    @Value("${E3_TOKEN:E3_TOKEN}")
    private String E3_TOKEN;

    @Value("${SESSION_EXPIRE:7200}")
    private int SESSION_EXPIRE;

    @Value("${USER_KEY:user")
    private String USER_KEY;
    @Override
    public E3Result login(String username,String password) {
        if (StringUtils.isEmpty(username))
            return E3Result.build(500, "用户名称");
        if (StringUtils.isEmpty(password))
            return E3Result.build(500, "密码为空");

        //根据用户名查询
        TbUserExample example = new TbUserExample();
        example.createCriteria().andUsernameEqualTo(username);

        List<TbUser> list = mapper.selectByExample(example);
        if (list==null || list.size()<1) {
            return E3Result.build(500,"用户名不存在");
        }

        //密码加密后比较
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());

        //获取用户信息
        TbUser user = list.get(0);

        //加密后的密码对比
        if (!md5Password.equals(user.getPassword())) {
            return E3Result.build(500,"密码不正确");
        }
        //生成Token
        String token = UUID.randomUUID().toString();
        //存储到redis
        //Jedis jedis = pool.getResource();
        jedis.hset(E3_TOKEN+token,USER_KEY, JsonUtils.objectToJson(user));
        //设置有效期
        jedis.expire(E3_TOKEN+token,SESSION_EXPIRE);

        //jedis.close(); //集群不需要关闭
        //返回token，需要写入cookie;
        return E3Result.ok(token);
    }

    @Override
    public E3Result loginCheck(String token) {
        if(StringUtils.isBlank(token)) {
            return E3Result.build(500,"token为空，请重新登录");
        }
        //根据token，从jedis获取用户信息
        //Jedis jedis = pool.getResource();
        String json = jedis.hget(E3_TOKEN + token, USER_KEY);
        if (json==null) {
            return E3Result.build(500,"登录信息已经失效");

        }
        //重置session有效期
        jedis.expire(E3_TOKEN+token,SESSION_EXPIRE);
        //jedis.close(); //集群不需要关闭

        //将json字符串转成TbUser对象
        TbUser user = JsonUtils.jsonToPojo(json, TbUser.class);

        return E3Result.ok(user);
    }


}
