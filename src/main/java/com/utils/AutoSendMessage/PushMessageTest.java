package com.utils.AutoSendMessage;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.Arrays;

import org.junit.Test;

public class PushMessageTest {

	@Test
	public void testSMS() {
		String strReg = "101100-WEB-HUAX-831352";   //注册号（由华兴软通提供）
        String strPwd = "UCHAKMTB";                 //密码（由华兴软通提供）
        String strSourceAdd = "";                   //子通道号，可为空（预留参数一般为空）
        String strPhone = "13530597873";            //手机号码，多个手机号用半角逗号分开，最多1000个
        String prototypeContent = "您好，您此次验证码为{0}，2分钟内有效。";//这是要求的参数：短信模板（需要联系短信提供商客服设定自己需要的模板）
        String a[]={"5506"};                        //将模板”{0}“中{}内的”0“替换为”5506“后发送给知道号码
        try {
        	//
			String strContent = NoteUtils.paraTo16(MessageFormat.format(prototypeContent,a));
			String strSmsUrl = "http://www.stongnet.com/sdkhttp/sendsms.aspx";
			String strSmsParam = "reg=" + strReg + "&pwd=" + strPwd + "&sourceadd=" + strSourceAdd + "&phone=" + strPhone + "&content=" + strContent;
			NoteUtils.postSend(strSmsUrl, strSmsParam);
        } catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testArrays(){
		System.out.println(Arrays.asList());
	}
}
