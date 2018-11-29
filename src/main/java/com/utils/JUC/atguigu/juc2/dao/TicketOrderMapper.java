package com.utils.JUC.atguigu.juc2.dao;

import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface TicketOrderMapper {

	/** 新增指定票数num */
	int increaseByNum(int num);

	/** 设置票数为num */
	int setTickNum(int num);

	/** 票数-1 */
	int decrease();

	/** 票数+1 */
	int increase();

	/** 获取余票数 */
	int getTickNum();

	/** 添加购票订单记录 */
	int insertOrder(Map<String, Object> paramMap);

}
