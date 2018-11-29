package com.utils.String;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class TestString_Contains {
	/**
	 * 否包含指定字符串
	 */
	@Test
	public void contains() {
		String s1 = "this is a string:s1";
		String s2 = "string:s1";
		boolean contains = s1.contains(s2);
		System.out.println(contains);
	}

	/**
	 * 是否包含指定集合的所有元素
	 */
	@Test
	public void containsAll() {
		List<String> list1 = Arrays.asList("aa", "bb", "cc");
		List<String> list2 = Arrays.asList("bb", "aa", "cc");
		boolean flag = list1.containsAll(list2) && list2.containsAll(list1);
		System.out.println(flag ? "想等！" : "不想等！");
	}

	/**
	 * 比较字符串：考略大小写
	 */
	@Test
	public void equals() {
		boolean equalsIgnoreCase = "HHaa".equals("hhAa");
		System.out.println(equalsIgnoreCase);
	}

	/**
	 * 比较字符串：忽略大小写
	 */
	@Test
	public void equalsIgnoreCase() {
		boolean equalsIgnoreCase = "HHaa".equalsIgnoreCase("hhAa");
		System.out.println(equalsIgnoreCase);
	}
}
