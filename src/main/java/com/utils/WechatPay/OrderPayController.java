package com.utils.WechatPay;///**
// * 
// */
//package com.tulun.study.WechatPay;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.SortedMap;
//import java.util.TreeMap;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import net.sf.json.JSONObject;
//
//import org.apache.log4j.Logger;
//import org.junit.Test;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import com.tulun.study.WechatPay.utils.CommonsUtil;
//import com.tulun.study.WechatPay.utils.GetWxOrderno;
//import com.tulun.study.WechatPay.utils.HttpConnect;
//import com.tulun.study.WechatPay.utils.HttpResponse;
//import com.tulun.study.WechatPay.utils.RequestHandler;
//import com.tulun.study.WechatPay.utils.Sha1Util;
//import com.tulun.study.WechatPay.utils.TenpayUtil;
//
//
//@Controller
//@RequestMapping("/orderpay")
//public class OrderPayController {
//	private Logger logger=Logger.getLogger(this.getClass());
//	
//	@SuppressWarnings("static-access")
//	@RequestMapping("/orderpay")
//	public Map<String,String> weChatGzh(HttpServletRequest request,
//			HttpServletResponse response) throws IOException {
//		System.out.print("已进入weChatGZH");
//		Map<String, String> resMap=new HashMap<>();
//		//网页授权后获取传递的参数
//		String userId = "test01";//request.getParameter("userId"); 	
//		String orderNo ="test01";// request.getParameter("orderNo"); 	
//		String money ="1";// request.getParameter("money");
//		String code ="test01";// request.getParameter("code");
//		//金额转化为分为单位
//		float sessionmoney = Float.parseFloat(money);
//		String finalmoney = String.format("%.2f", sessionmoney);
//		finalmoney = finalmoney.replace(".", "");
//		
//		System.out.println(code+":::微信进入3");
//
//		
//		
//		//scope 参数视各自需求而定，这里用scope=snsapi_base 不弹出授权页面直接授权目的只获取统一支付接口的openid
//		/*String URL2 = "https://open.weixin.qq.com/connect/oauth2/authorize?" +
//				"appid=" + appid+
//				"&redirect_uri=" +""+
//				"&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
//		String tempValue2="";
//		HttpResponse temp2 = HttpConnect.getInstance().doGetStr(URL2);
//		tempValue2 = temp2.getStringResult();
//		System.out.println("temp2Value::"+tempValue2);*/
//		
//		
//		String openId ="";
//		String URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wxace9f27e7bba7ccc&secret=cf288041d68f3a5cc7eb02d53c8bb7b2&code=test01&grant_type=authorization_code";
//		
//		HttpResponse temp = HttpConnect.getInstance().doGetStr(URL);		
//		String tempValue="";
//		if( temp == null){
//			System.out.println("temp is null");
//			logger.error("temp is null");
//			resMap.put("code", "2");
//			resMap.put("message", "系统繁忙");
//			return resMap;
//		}else
//		{
//			try {
//				tempValue = temp.getStringResult();
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			JSONObject  jsonObj = JSONObject.fromObject(tempValue);
//			if(jsonObj.containsKey("errcode")){
//				System.out.println("tempValue："+tempValue);
//				logger.error("tempValue : "+tempValue);
//				resMap.put("code", "201");
//				resMap.put("message", "微信支付系统繁忙");
//			}
//			openId = jsonObj.getString("openid");
//		}
//		
//		
//		//获取openId后调用统一支付接口https://api.mch.weixin.qq.com/pay/unifiedorder
//		String currTime = TenpayUtil.getCurrTime();
//		//8位日期
//		String strTime = currTime.substring(8, currTime.length());
//		//四位随机数
//		String strRandom = TenpayUtil.buildRandom(4) + "";
//		//10位序列号,可以自行调整。
//		String strReq = strTime + strRandom;
//		
//		
//		//商户号
//		String mch_id = CommonsUtil.PARTNER;
//		//子商户号  非必输
////						String sub_mch_id="";
//		//设备号   非必输
////		String device_info="";
//		//随机数 
//		String nonce_str = strReq;
//		//商品描述
////						String body = describe;
//		
//        //商品描述根据情况修改
//		String body = "美食";
//		//附加数据
//		String attach = userId;
//		//商户订单号
//		String out_trade_no = orderNo;
//		int intMoney = Integer.parseInt(finalmoney);
//		
//		//总金额以分为单位，不带小数点
//		int total_fee = intMoney;
//		//订单生成的机器 IP
//		String spbill_create_ip = request.getRemoteAddr();
//		//订 单 生 成 时 间   非必输
////						String time_start ="";
//		//订单失效时间      非必输
////						String time_expire = "";
//		//商品标记   非必输
////						String goods_tag = "";
//		
// 
//		
//		
//		String trade_type = "JSAPI";
//		String openid = openId;
//		//非必输
////						String product_id = "";
//		SortedMap<String, String> packageParams = new TreeMap<String, String>();
//		packageParams.put("appid", CommonsUtil.APPID);  
//		packageParams.put("mch_id", mch_id);  
//		packageParams.put("nonce_str", nonce_str);  
//		packageParams.put("body", body);  
//		packageParams.put("attach", attach);  
//		packageParams.put("out_trade_no", out_trade_no);  
//		
//		
//		//这里写的金额为1 分到时修改
//		packageParams.put("total_fee",String.valueOf(total_fee));  //商品金额,以分为单位 
//		packageParams.put("spbill_create_ip", spbill_create_ip);  
//		packageParams.put("notify_url", CommonsUtil.NOTIFY_URL);  
//		
//		packageParams.put("trade_type", trade_type);  
//		packageParams.put("openid", openid);  
//
//		RequestHandler reqHandler = new RequestHandler(request, response);
//		reqHandler.init(CommonsUtil.APPID, CommonsUtil.APPSECRET, CommonsUtil.PARTNERKEY);
//		
//		String sign = reqHandler.createSign(packageParams);
//		String xml="<xml>"+
//				"<appid>"+CommonsUtil.APPID+"</appid>"+
//				"<mch_id>"+mch_id+"</mch_id>"+
//				"<nonce_str>"+nonce_str+"</nonce_str>"+
//				"<sign>"+sign+"</sign>"+
//				"<body><![CDATA["+body+"]]></body>"+
//				"<attach>"+attach+"</attach>"+
//				"<out_trade_no>"+out_trade_no+"</out_trade_no>"+
//				"<total_fee>"+total_fee+"</total_fee>"+
//				"<spbill_create_ip>"+spbill_create_ip+"</spbill_create_ip>"+
//				"<notify_url>"+CommonsUtil.NOTIFY_URL+"</notify_url>"+
//				"<trade_type>"+trade_type+"</trade_type>"+
//				"<openid>"+openid+"</openid>"+
//				"</xml>";
//		System.out.println(xml);
////		String allParameters = "";
////		try {
////			allParameters =  reqHandler.genPackage(packageParams);
////		} catch (Exception e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
//		String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";//统一支付接口获取预支付订单
//		String prepay_id="";
//		try {
//			prepay_id = new GetWxOrderno().getPayNo(createOrderURL, xml);
//			if(prepay_id.equals("")){
//				System.out.println("统一支付接口获取预支付订单出错");
//				logger.error("统一支付接口获取预支付订单出错");
//				resMap.put("code", "2");
//				resMap.put("message", "系统繁忙");
//			}
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		}
//		SortedMap<String, String> finalpackage = new TreeMap<String, String>();
//		String appid2 = CommonsUtil.APPID;
//		String timestamp = Sha1Util.getTimeStamp();
//		String nonceStr2 = nonce_str;
//		String prepay_id2 = "prepay_id="+prepay_id;
//		String packages = prepay_id2;
//		finalpackage.put("appId", appid2);  
//		finalpackage.put("timeStamp", timestamp);  
//		finalpackage.put("nonceStr", nonceStr2);  
//		finalpackage.put("package", packages);  
//		finalpackage.put("signType", "MD5");
//		String finalsign = reqHandler.createSign(finalpackage);//通过finalpackage去构造签名
//		System.out.println("微信支付已经成功");
//		System.out.println("finalsign:"+finalsign);
//		resMap.put("code", "0");
//		resMap.put("message", "确认支付");
//		resMap.put("appid", appid2);
//		resMap.put("timeStamp", timestamp);
//		resMap.put("nonceStr",nonceStr2);
//		resMap.put("package", packages);
//		resMap.put("sign", finalsign);
//		
//		return resMap;
//	}
//	@Test
//	public void TestweChatGzh(){
//	
//	}
//	
//
//	
//	/*
//	@RequestMapping("/weChatGzh")
//	public Map<String, String> weChatGzh(HttpServletRequest request,
//			HttpServletResponse response) throws IOException {
//		logger.info("invoke weChatGZH");
//		Map<String, String> resMap=new HashMap<>();
//		//网页授权后获取传递的参数
//		String userId = request.getParameter("userId"); 	
//		String orderNo = request.getParameter("orderNo"); 	
//		String money = request.getParameter("money");
//		String code = request.getParameter("code");
//		//userId=b88001&orderNo=1499900164812&describe=西瓜&money=1.00
//		//金额转化为分为单位
//		float sessionmoney = Float.parseFloat(money);
//		String finalmoney = String.format("%.2f", sessionmoney);
//		finalmoney = finalmoney.replace(".", "");
//        //商户相关资料 
//		String appid = "wxc0d0b2ed3ab63e7e";
//		String appsecret = "57e914e6415df89699a11163e78524be";
//		String partner = "1325535001";
//		String partnerkey = "DSE785DS8924D9DSsw82fs69ds3dgs91";
//		
//		//scope 参数视各自需求而定，这里用scope=snsapi_base 不弹出授权页面直接授权目的只获取统一支付接口的openid
//		String URL2 = "https://open.weixin.qq.com/connect/oauth2/authorize?" +
//				"appid=" + appid+
//				"&redirect_uri=" +""+
//				"&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
//		String tempValue2="";
//		HttpResponse temp2 = HttpConnect.getInstance().doGetStr(URL2);
//		tempValue2 = temp2.getStringResult();
//		System.out.println("temp2Value::"+tempValue2);
//		
//		String openId ="";
//		String URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appid+"&secret="+appsecret+"&code="+code+"&grant_type=authorization_code";
//		HttpResponse temp = HttpConnect.getInstance().doGetStr(URL);		
//		String tempValue="";
//		if( temp == null){
//			logger.error("微信支付出错，temp="+temp);
//			resMap.put("code", "201");
//			resMap.put("message", "系统繁忙，请稍后重试");
//			return resMap;
//		}else
//		{
//			try {
//				tempValue = temp.getStringResult();
//			} catch (Exception e) {
//				logger.error("微信支付出错at line 81 , "+e.getMessage());
//			}
//			JSONObject  jsonObj = JSONObject.fromObject(tempValue);
//			if(jsonObj.containsKey("errcode")){
//				logger.error("微信支付出错，tempValue="+tempValue);
//				resMap.put("code", "201");
//				resMap.put("message", "系统繁忙，请稍后重试");
//			}
//			openId = jsonObj.getString("openid");
//		}
//		
//		
//			//获取openId后调用统一支付接口https://api.mch.weixin.qq.com/pay/unifiedorder
//			String currTime = TenpayUtil.getCurrTime();
//			//8位日期
//			String strTime = currTime.substring(8, currTime.length());
//			//四位随机数
//			String strRandom = TenpayUtil.buildRandom(4) + "";
//			//10位序列号,可以自行调整。
//			String strReq = strTime + strRandom;
//			//商户号
//			String mch_id = partner;
//			//子商户号  非必输
////				String sub_mch_id="";
//			//设备号   非必输
//			String device_info="";
//			//随机数 
//			String nonce_str = strReq;
//			//商品描述
////				String body = describe;
//            //商品描述根据情况修改
//			String body = "衣梦制衣";
//			//附加数据
//			String attach = userId;
//			//商户订单号
//			String out_trade_no = orderNo;
//			int intMoney = Integer.parseInt(finalmoney);
//			
//			//订单生成的机器 IP
//			String spbill_create_ip = request.getRemoteAddr();
//			//订 单 生 成 时 间   非必输
////				String time_start ="";
//			//订单失效时间      非必输
////				String time_expire = "";
//			//商品标记   非必输
////				String goods_tag = "";
//			
//			//这里notify_url是 支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等。
//			String notify_url ="http://192.168.1.111:8082/testPay/aa.htm";
//			
//			
//			String trade_type = "JSAPI";
//			String openid = openId;
//			//非必输
////				String product_id = "";
//			SortedMap<String, String> packageParams = new TreeMap<String, String>();
//			packageParams.put("appid", appid);  
//			packageParams.put("mch_id", mch_id);  
//			packageParams.put("nonce_str", nonce_str);  
//			packageParams.put("body", body);  
//			packageParams.put("attach", attach);  
//			packageParams.put("out_trade_no", out_trade_no);  
//			
//			//这里写的金额为1 分到时修改 
//			packageParams.put("total_fee", String.valueOf(intMoney));  
//			packageParams.put("spbill_create_ip", spbill_create_ip);  
//			packageParams.put("notify_url", notify_url);  
//			
//			packageParams.put("trade_type", trade_type);  
//			packageParams.put("openid", openid);  
//
//			RequestHandler reqHandler = new RequestHandler(request, response);
//			reqHandler.init(appid, appsecret, partnerkey);
//			
//			String sign = reqHandler.createSign(packageParams);
//			String xml="<xml>"+
//					"<appid>"+appid+"</appid>"+
//					"<mch_id>"+mch_id+"</mch_id>"+
//					"<nonce_str>"+nonce_str+"</nonce_str>"+
//					"<sign>"+sign+"</sign>"+
//					"<body><![CDATA["+body+"]]></body>"+
//					"<attach>"+attach+"</attach>"+
//					"<out_trade_no>"+out_trade_no+"</out_trade_no>"+
////金额，这里写的1 分到时修改
//					"<total_fee>"+intMoney+"</total_fee>"+
////						"<total_fee>"+finalmoney+"</total_fee>"+
//					"<spbill_create_ip>"+spbill_create_ip+"</spbill_create_ip>"+
//					"<notify_url>"+notify_url+"</notify_url>"+
//					"<trade_type>"+trade_type+"</trade_type>"+
//					"<openid>"+openid+"</openid>"+
//					"</xml>";
//			logger.info(xml);
//			String allParameters = "";
//			try {
//				allParameters =  reqHandler.genPackage(packageParams);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";//统一支付接口获取预支付订单
//			String prepay_id="";
//			try {
//				prepay_id = new GetWxOrderno().getPayNo(createOrderURL, xml);
//				if(prepay_id.equals("")){
//					logger.error("统一支付接口获取预支付订单出错");
//					resMap.put("code", "2");
//					resMap.put("message", "微信支付出错");
//				}
//			} catch (Exception e1) {
//				e1.printStackTrace();
//			}
//			SortedMap<String, String> finalpackage = new TreeMap<String, String>();
//			String appid2 = appid;
//			String timestamp = Sha1Util.getTimeStamp();
//			String nonceStr2 = nonce_str;
//			String prepay_id2 = "prepay_id="+prepay_id;
//			String packages = prepay_id2;
//			finalpackage.put("appId", appid2);  
//			finalpackage.put("timeStamp", timestamp);  
//			finalpackage.put("nonceStr", nonceStr2);  
//			finalpackage.put("package", packages);  
//			finalpackage.put("signType", "MD5");
//			String finalsign = reqHandler.createSign(finalpackage);//通过finalpackage去构造签名
//			
//			
//			
//			resMap.put("appid", appid2);
//			resMap.put("timeStamp",timestamp);
//			resMap.put("nonceStr", nonceStr2);
//			resMap.put("package", packages);
//			resMap.put("sign",finalsign);
//			return resMap;
//	}*/
//	
//}
