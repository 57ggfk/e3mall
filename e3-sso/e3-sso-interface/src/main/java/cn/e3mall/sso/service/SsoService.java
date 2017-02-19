package cn.e3mall.sso.service;

import cn.e3mall.common.po.E3Result;
import cn.e3mall.manager.po.TbUser;

/**
 * Created by wangji on 2017/2/5.
 */
public interface SsoService {
    /**
     * 注册操作
     * @param user
     * @return
     */
    public E3Result register(TbUser user);

    /**
     * 登录操作
     * @param username
     * @param password
     * @return
     */
    public E3Result login(String username,String password);

    /**
     * 登录检测
     * @param token
     * @return
     */
    public E3Result loginCheck(String token);
}
