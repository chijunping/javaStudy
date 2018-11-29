package com.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class IsHappyNum {

	@Test
	public void testIsHappyNum() {
		// 1, 7, 10, 13, 19, 23, 28, 31, 32,37,44, 49, 68, 70, 79, 82, 86, 91,
		// 94, 97, 100.
		boolean happy = isHappy(10);
		System.out.println(happy);
	}

	public boolean isHappy(int n) {
		Set<Integer> set = new HashSet<Integer>();
		while (mul(n) != 1) {
			if (set.contains(mul(n))) {
				return false;
			}
			set.add(mul(n));
			mul(n);
			n = mul(n);
		}
		return true;
	}

	public int mul(int n) {
		int sum = 0;
		while (n != 0) {
			int x = n % 10;
			sum = sum + (int) Math.pow(x, 2);
			n = n / 10;
		}
		return sum;
	}

	@Test
	public void tets() throws UnsupportedEncodingException {
		String decode = URLDecoder.decode("%E2%9D%A4%EF%B8%8F%E2%9D%A4%EF%B8%8F%F0%9F%98%84%F0%9F%A4%A3%E2%98%BA%EF%B8%8F%F0%9F%A4%A3");
		System.out.println(decode);
	}
}
