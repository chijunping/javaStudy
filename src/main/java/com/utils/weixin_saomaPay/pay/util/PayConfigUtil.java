package com.utils.weixin_saomaPay.pay.util;

public class PayConfigUtil {
	//初始化
	public final static String APP_ID = "wxace9f27e7bba7ccc"; //公众账号appid（改为自己实际的）
	public final static String APP_SECRET = "cf288041d68f3a5cc7eb02d53c8bb7b2";
	public final static String MCH_ID = "1364717702"; //商户号（改为自己实际的）
	public final static String API_KEY = "Mhn4teR6bl6IDGNLEVo0AXGPUNebWcaG"; //（改为自己实际的）key设置路径：微信商户平台(pay.weixin.qq.com)-->账户设置-->API安全-->密钥设置
	
	//有关url
	public final static String UFDODER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	public final static String NOTIFY_URL = "http://xxxxxxx"; //微信支付回调接口（改为自己实际的，且要公网地地址才能正常回调）
	//企业向个人账号付款的URL
	public final static String SEND_EED_PACK_URL = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";
	
	public final static String CREATE_IP = "116.24.66.157";//发起支付ip（改为自己实际的）
	
	    /*
	     * 参考yimengTest环境的参数
	     * 
		NOTIFY_URL = http://omptest.3dyim.com/yimengomp/restapi/wechat/paysuccess2.dos
		APPID = wxace9f27e7bba7ccc
		APPSECRET = cf288041d68f3a5cc7eb02d53c8bb7b2
		PARTNER = 1364717702
		PARTNERKEY = Mhn4teR6bl6IDGNLEVo0AXGPUNebWcaG
		weChartRefund = https://api.mch.weixin.qq.com/secapi/pay/refund
		PICPATH=http\://imagestest.3dyim.com\:60080/imageFiles/
		SCM_URL=http\://scmtest01.3dyim.com/scm
*/
	
}
