package com.utils.SpringScheduledTask;//package com.tulun.study.SpringScheduledTask;
//
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//@Component
//public class SpringScheduledTask {
//	@Scheduled(cron = "0/10 * *  * * ? ")
//	// 每10秒执行一次
//	public void job1() {
//		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		System.out.println(sdf.format(System.currentTimeMillis()) + "*********A任务每10秒执行一次进入测试");
//	}
//
//	@Scheduled(cron = "0/5 * *  * * ? ")
//	// 每5秒执行一次
//	public void bTask() {
//		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		System.out.println(sdf.format(System.currentTimeMillis()) + "*********B任务每5秒执行一次进入测试");
//	}
//}