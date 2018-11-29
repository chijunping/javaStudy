package com.utils.reg;

import org.junit.Test;

public class Reg {

	@Test
	public void testReg(){
		String reg="[0-9]\\d*\\.?\\d*|-[0-9]\\d*\\.?\\d*";
		String  str="-0111234234242.0123";
		boolean matches = str.matches(reg);
		System.out.println(matches);
	}
}
