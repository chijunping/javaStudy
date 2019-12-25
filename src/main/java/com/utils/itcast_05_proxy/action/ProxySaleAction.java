package com.utils.itcast_05_proxy.action;

import com.utils.itcast_05_proxy.proxyclass.ProxyBoss;
import com.utils.itcast_05_proxy.service.IBoss;
import com.utils.itcast_05_proxy.service.impl.Boss;
import org.junit.Test;


/**
 * 什么是动态代理？ 简单的写一个模板接口，剩下的个性化工作，好给动态代理来完成！
 * cjp个人理解：接口——声明业务；接口实现——业务逻辑具体实现；动态代理——
 * 当某个接口实现的某个具体方法的逻辑足够其他场景使用，而不够新增需求使用时就意味着需要重写这个方法（或者动态地在原来的方法实现中只为这个需求新增逻辑补充），
 * 当经常性的需要对某个方法的处理逻辑做动态补充时，就只能用动态代理的方式组织代码了。
 * 步骤：
 * 1.定义接口
 * 2.实现接口、方法（日常通用逻辑）
 * 3.动态补充“接口实现”
 */
public class ProxySaleAction {

    /**
     * 使用代理，在这个代理中，只代理了Boss的yifu方法 定制化业务，可以改变原接口的参数、返回值等
     */
    @Test
    public void saleByProxy() throws Exception {
        IBoss boss = ProxyBoss.getProxy(IBoss.class, Boss.class);// 将代理的方法实例化成接口
        // IBoss boss = new Boss();// 将代理的方法实例化成接口
        System.out.println("代理经营！");
        int money = boss.yifu("xxl");// 调用接口的方法，实际上调用方式没有变
        System.out.println("衣服成交价：" + money);
        int kuzi = boss.kuzi();
        System.out.println("裤子成交价：" + kuzi);
        String sayHi = boss.sayHi("cjp");
        System.out.println(sayHi);

    }

    /**
     * 不使用代理，直接调用方法 方法中规定什么业务，就只能调用什么业务，规定什么返回值，就只能输出什么返回值
     */
    @Test
    public void saleByBossSelf() throws Exception {
        IBoss boss = new Boss();// 将代理的方法实例化成接口
        System.out.println("老板自营！");
        int money = boss.yifu("xxl");// 调用接口的方法，实际上调用方式没有变
        System.out.println("衣服成交价：" + money);
        int kuzi = boss.kuzi();
        System.out.println("裤子成交价：" + kuzi);
        String sayHi = boss.sayHi("cjp");
        System.out.println(sayHi);
    }
}
