package com.utils.JUC.atguigu.juc2.service.impl;

import com.utils.JUC.atguigu.juc2.dao.TicketOrderMapper;
import com.utils.JUC.atguigu.juc2.service.ITicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


/*并发环境一：设置jmeter线程数1000，时间60s，
 * 		1.本程序不加锁：票数正常为0 后就不会出现负数（把并发时间改小到20s会出现负数）
 *   总结：1.锁，还需要学习；2.并发共享数据只有在并发量达到一定限度时才会出现问题
 *
 * 并发环境二：设置jmeter线程数1000，时间10s，
 * 		1.本程序不加锁：票数正常为0 后还会继续减，会出现负数
 * 		2.按照本方法的方式添加“读写锁”后，反而负数现象严重
 * 		3.synchronize关键字，有效解决
 *      4.lock1.lock();有效解决
 *   总结：1.读写锁还需要学习；2.并发共享数据只有在并发量达到一定限度时才会出现问题
 */
@Service
public class TicketServiceImpl implements ITicketService {

    @Autowired
    private TicketOrderMapper tickDao;
    private ReadWriteLock lock = new ReentrantReadWriteLock();
    private Lock lock1 = new ReentrantLock();
    private int tick = 0;

    @Override
    public Map<String, Object> buyTicket(Map<String, Object> paramMap) {
        Map<String, Object> buyticket111 = buyticket111(paramMap);
        return buyticket111;
    }

    /**
     * @param paramMap
     */
    public Map<String, Object> buyticket111(Map<String, Object> paramMap) {
        Map<String, Object> resMap = new ConcurrentHashMap<>();

        while (true) {
            // lock.readLock().lock();//此程序，按照这个方式添加读写锁，无法解决余票负数问题，反而加大问题了。
            lock1.lock();
            try {
                tick = tickDao.getTickNum();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // lock.readLock().unlock();
            }
            // lock.writeLock().lock();
            try {
                if (tick > 0) {
                    // Thread.sleep(20);
                    // 数据库余票-1
                    int decrease = tickDao.decrease();
                    // 数据库添加购票记录
                    paramMap.put("tick_order_id", System.currentTimeMillis() + String.valueOf(new Random(1000000L).nextInt()));
                    Thread.currentThread().sleep(20);// 扩大问题发生概率，测试结果为：正常计算
                    int insertOrder = tickDao.insertOrder(paramMap);
                    resMap.put("code", 0);
                    resMap.put("msg", "够票成功！");
                    return resMap;
                } else {
                    resMap.put("code", 1);
                    resMap.put("msg", "余票不足！");
                    return resMap;
                }
            } catch (Exception e) {
                e.printStackTrace();
                resMap.put("code", 1);
                resMap.put("msg", "程序异常！");
                return resMap;
            } finally {
                // lock.writeLock().unlock();
                lock1.unlock();
            }
        }
    }
}
