package com.utils.itcast_04_reflect.socket;

import java.util.ArrayList;

public class TestBusiness implements IBusiness{
	@Override
	public int getPrice(String good){
		return good.equals("yifu")?10:20;
	}
}
