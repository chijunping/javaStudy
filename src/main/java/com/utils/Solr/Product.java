package com.utils.Solr;

public class Product {

	private String productionId;// 商品id
	private String productionName;// 名称
	private String normalPrice;// 价格
	private String picUrl;// 图片路径
	private String enteringTime;// 上架时间

	public String getEnteringTime() {
		return enteringTime;
	}

	public void setEnteringTime(String enteringTime) {
		this.enteringTime = enteringTime;
	}

	public String getProductionId() {
		return productionId;
	}

	public void setProductionId(String productionId) {
		this.productionId = productionId;
	}

	public String getProductionName() {
		return productionName;
	}

	public void setProductionName(String productionName) {
		this.productionName = productionName;
	}

	public String getNormalPrice() {
		return normalPrice;
	}

	public void setNormalPrice(String normalPrice) {
		this.normalPrice = normalPrice;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

}