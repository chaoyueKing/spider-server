package com.qmcaifu.splider.pojo;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;

public class GoodsInfo implements Serializable {

	private static final long serialVersionUID = 8055310899044049629L;

	private String id;

	/**
	 * 抓取宿主网站编码
	 */
	private String webCode;

	/**
	 * 抓取宿主网站名称
	 */
	private String siteName;

	/**
	 * 抓取宿主网站网址
	 */
	private String webSite;

	/**
	 * 商品品类
	 */
	private String category;

	/**
	 * 产品来源平台
	 */
	private String goodSource;

	/**
	 * 店铺名称
	 */
	private String shopName;

	/**
	 * 商品名称
	 */
	private String goodsName;

	/**
	 * 页面标题
	 */
	private String goodsPageTitle;

	/**
	 * 商品图片url（宝贝主图区域的全部图片）
	 */
	private String goodsImg;

	/**
	 * 商品略缩图
	 */
	private String goodsThumPics;

	/**
	 * 商品略缩图对应的显示图
	 */
	private String goodsShowPics;

	/**
	 * 商品属性
	 */
	private String goodsAttribute;

	/**
	 * 产品描述
	 */
	private String goodsDesc;

	/**
	 * 商品原价
	 */
	private String goodOrgPrice;

	/**
	 * 商品现在价钱
	 */
	private String nowPrice;

	/**
	 * 商品页面链接(抓取页面)
	 */
	private String pageUrl;

	/**
	 * 真实的详情页面
	 */
	private String detailPage;

	private Date createTime;

	private Date updateTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWebCode() {
		return webCode;
	}

	public void setWebCode(String webCode) {
		this.webCode = webCode;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getGoodSource() {
		return goodSource;
	}

	public void setGoodSource(String goodSource) {
		this.goodSource = goodSource;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsPageTitle() {
		return goodsPageTitle;
	}

	public void setGoodsPageTitle(String goodsPageTitle) {
		this.goodsPageTitle = goodsPageTitle;
		if (StringUtils.isBlank(goodsName)) {
			goodsName = goodsPageTitle;
		}
	}

	public String getGoodsImg() {
		return goodsImg;
	}

	public void setGoodsImg(String goodsImg) {
		this.goodsImg = goodsImg;
	}

	public String getGoodsThumPics() {
		return goodsThumPics;
	}

	public void setGoodsThumPics(String goodsThumPics) {
		this.goodsThumPics = goodsThumPics;
	}

	public String getGoodsShowPics() {
		return goodsShowPics;
	}

	public void setGoodsShowPics(String goodsShowPics) {
		this.goodsShowPics = goodsShowPics;
	}

	public String getGoodsAttribute() {
		return goodsAttribute;
	}

	public void setGoodsAttribute(String goodsAttribute) {
		this.goodsAttribute = goodsAttribute;
	}

	public String getGoodsDesc() {
		return goodsDesc;
	}

	public void setGoodsDesc(String goodsDesc) {
		this.goodsDesc = goodsDesc;
	}

	public String getGoodOrgPrice() {
		return goodOrgPrice;
	}

	public void setGoodOrgPrice(String goodOrgPrice) {
		this.goodOrgPrice = goodOrgPrice;
	}

	public String getNowPrice() {
		return nowPrice;
	}

	public void setNowPrice(String nowPrice) {
		this.nowPrice = nowPrice;
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	public String getDetailPage() {
		return detailPage;
	}

	public void setDetailPage(String detailPage) {
		this.detailPage = detailPage;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this, true);
	}
}
