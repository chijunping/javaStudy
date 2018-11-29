package com.utils.controller;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/index")
public class IndexController {

	@RequestMapping("/toIndex")
	public String toIndex() {
		return "/login";
	}
	
	@RequestMapping("/testSpringDateFormat")
	@ResponseBody
	public DateTest testSpringDateFormat(){
		return new DateTest("蜘蛛精", 18, new Date());
	}
	
	private class DateTest{
		private String name;
		private Integer age;
		private Date bornDateTime;
		
		public DateTest(String name, Integer age, Date bornDateTime) {
			super();
			this.name = name;
			this.age = age;
			this.bornDateTime = bornDateTime;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Integer getAge() {
			return age;
		}
		public void setAge(Integer age) {
			this.age = age;
		}
		public Date getBornDateTime() {
			return bornDateTime;
		}
		public void setBornDateTime(Date bornDateTime) {
			this.bornDateTime = bornDateTime;
		}
		
	}
	
	public static void main(String[] args) {
		Date date = new Date();
		System.out.println(date);
	}

}
