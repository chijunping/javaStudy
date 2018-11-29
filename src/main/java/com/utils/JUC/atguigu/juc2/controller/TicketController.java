package com.utils.JUC.atguigu.juc2.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tulun.study.JUC.atguigu.juc2.service.ITicketService;

/*
 * 一、用于解决多线程安全问题的方式：
 * 
 * synchronized:隐式锁
 *   1. 同步代码块
 *   2. 同步方法
 * 
 * jdk 1.5 后：
 *   3. 同步锁 Lock
 * 注意：Lock是一个显示锁，需要通过 lock() 方法上锁，必须通过 unlock() 方法进行释放锁
 */
@Controller
public class TicketController {

	@Autowired
	private ITicketService ticketService;

	@RequestMapping("/buyTicket")
	@ResponseBody
	public Map<String, Object> buyTicket(
			@RequestParam(value="tick_num",required=false) int tick_num,
			@RequestParam(value="place_from",required=false) String place_from,
			@RequestParam(value="place_to",required=false) String place_to
			
			) {
		Map<String,Object> paramMap=new HashMap<>();
		paramMap.put("tick_num", tick_num);
		paramMap.put("buyer_name",Thread.currentThread().getName());
		paramMap.put("place_from", place_from);
		paramMap.put("place_to", place_to);
		return ticketService.buyTicket(paramMap);
	}

}
