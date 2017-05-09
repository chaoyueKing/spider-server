package com.qmcaifu.splider.utils.page;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSON;
import com.qmcaifu.splider.pojo.GoodsInfo;

public class PageParser {

	public static GoodsInfo parse(String pageUrl) throws Exception {
		GoodsInfo goodsInfo = new GoodsInfo();
		Document document = Jsoup.connect(pageUrl).get();
		String baseUri = document.baseUri();
		goodsInfo.setDetailPage(baseUri);

		if (StringUtils.contains(baseUri, "item.taobao.com")) {
			getTaobaoGoodsInfo(goodsInfo, document);
		} else if (StringUtils.contains(baseUri, "tmall.com")) {
			getTmallGoodsInfo(goodsInfo, document);
		} else if (StringUtils.contains(baseUri, "ai.taobao.com")) {
			throw new RuntimeException("爱淘宝" + pageUrl + "，改页面不做处理");
		} else if (StringUtils.contains(baseUri, "shop.zhe800.com")) {
			getZhe800GoodsInfo(goodsInfo, document);
		} else {
			throw new RuntimeException("未曾找到" + pageUrl);
		}

		goodsInfo.setGoodsPageTitle(document.title());
		goodsInfo.setCreateTime(new Date());
		goodsInfo.setUpdateTime(new Date());
		return goodsInfo;
	}

	private static GoodsInfo getTaobaoGoodsInfo(GoodsInfo goodsInfo, Document document) throws IOException {
		goodsInfo.setGoodSource("TAOBAO");
		goodsInfo.setShopName(document.select("div.tb-shop-name").select("a").text());

		Elements thumImgs = document.select("#J_UlThumb img");
		if (thumImgs.size() > 0) {
			StringBuilder thums = new StringBuilder();
			StringBuilder shows = new StringBuilder();
			for (int i = 0; i < thumImgs.size(); i++) {
				String thumSrc = thumImgs.get(i).attr("data-src");
				String showSrc = StringUtils.replace(thumSrc, "50x50", "400x400");
				thums.append(thumSrc).append(",");
				shows.append(showSrc).append(",");
				if (i == 0 && StringUtils.isBlank(goodsInfo.getGoodsImg())) {
					goodsInfo.setGoodsImg(showSrc);
				}
			}
			goodsInfo.setGoodsThumPics(StringUtils.substringBeforeLast(thums.toString(), ","));
			goodsInfo.setGoodsShowPics(StringUtils.substringBeforeLast(shows.toString(), ","));
		}

		goodsInfo.setGoodsAttribute("<ul class=\"am-list\">" + Jsoup.clean(document.select("#attributes > ul.attributes-list").html(), Whitelist.basic()) + "</ul>");
		String html = document.html();
		String description = StringUtils.trim(StringUtils.substringBefore(StringUtils.substringAfter(html, "descUrl          : location.protocol==='http:' ? '"), "' :"));
		Document descriptionHtml = Jsoup.connect("http:" + description).get();
		String descriptionPic = StringUtils.substringBeforeLast(StringUtils.substringAfter(descriptionHtml.html(), "var desc='"), "';");
		goodsInfo.setGoodsDesc(descriptionPic);

		return goodsInfo;
	}

	private static GoodsInfo getTmallGoodsInfo(GoodsInfo goodsInfo, Document document) throws IOException {
		goodsInfo.setGoodSource("TMALL");
		goodsInfo.setShopName(document.select("a.slogo-shopname").text());
		goodsInfo.setGoodsAttribute("<ul class=\"am-list\">" + Jsoup.clean(document.select("#J_AttrUL").html(), Whitelist.basic()) + "</ul>");// 信息描述

		String html = document.html();
		String description = StringUtils.trim(StringUtils.substringBefore(StringUtils.substringAfter(html, "\"descUrl\":\""), "\",\"fetchDcUrl\""));
		Document descriptionHtml = Jsoup.connect("http:" + description).get();
		String descriptionPic = StringUtils.substringBeforeLast(StringUtils.substringAfter(descriptionHtml.html(), "var desc='"), "';");
		goodsInfo.setGoodsDesc(Jsoup.clean(descriptionPic, Whitelist.basicWithImages()));// 商品详情

		Elements thumImgs = document.select("#J_UlThumb img");
		if (thumImgs.size() > 0) {
			StringBuilder thums = new StringBuilder();
			StringBuilder shows = new StringBuilder();
			for (int i = 0; i < thumImgs.size(); i++) {
				String thumSrc = thumImgs.get(i).attr("src");
				String showSrc = StringUtils.replace(thumSrc, "60x60", "430x430");
				thums.append(thumSrc).append(",");
				shows.append(showSrc).append(",");
				if (i == 0 && StringUtils.isBlank(goodsInfo.getGoodsImg())) {
					goodsInfo.setGoodsImg(showSrc);
				}
			}
			goodsInfo.setGoodsThumPics(StringUtils.substringBeforeLast(thums.toString(), ","));
			goodsInfo.setGoodsShowPics(StringUtils.substringBeforeLast(shows.toString(), ","));
		}

		return goodsInfo;
	}

	private static GoodsInfo getZhe800GoodsInfo(GoodsInfo goodsInfo, Document document) throws IOException {
		goodsInfo.setGoodSource("ZHE800");

		goodsInfo.setShopName(document.select("div.detailmeta b > a.js_utm_params").text());
		Elements attributeHtml = document.select("div.productdetail > ul.list12");
		goodsInfo.setGoodsAttribute("<ul class=\"am-list\">" + Jsoup.clean(attributeHtml.size() != 0 ? attributeHtml.first().html() : StringUtils.EMPTY, Whitelist.basic())
				+ "</ul>");

		String id = StringUtils.substringBeforeLast(StringUtils.substringAfterLast(goodsInfo.getDetailPage(), "products/"), "?");

		InputStream is = new URL("http://shop.zhe800.com/nnc/products/" + id + "/detail.json").openStream();
		String data = JSON.parseObject(IOUtils.toString(is, "UTF-8")).getString("data");
		IOUtils.closeQuietly(is);

		goodsInfo.setGoodsDesc(data);

		Elements thumImgs = document.select("#detail a > img");
		if (thumImgs.size() > 0) {
			StringBuilder thums = new StringBuilder();
			StringBuilder shows = new StringBuilder();
			for (int i = 0; i < thumImgs.size(); i++) {
				String thumSrc = thumImgs.get(i).attr("src");
				String showSrc = thumImgs.get(i).attr("bigimage-data");
				thums.append(thumSrc).append(",");
				shows.append(showSrc).append(",");

				if (i == 0 && StringUtils.isBlank(goodsInfo.getGoodsImg())) {
					goodsInfo.setGoodsImg(showSrc);
				}
			}
			goodsInfo.setGoodsThumPics(StringUtils.substringBeforeLast(thums.toString(), ","));
			goodsInfo.setGoodsShowPics(StringUtils.substringBeforeLast(shows.toString(), ","));
		}
		return goodsInfo;
	}

}
