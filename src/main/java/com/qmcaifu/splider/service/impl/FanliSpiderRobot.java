package com.qmcaifu.splider.service.impl;

import java.net.SocketTimeoutException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.alibaba.fastjson.JSON;
import com.qmcaifu.splider.pojo.GoodsInfo;
import com.qmcaifu.splider.pojo.enums.Parasitifer;
import com.qmcaifu.splider.service.AbstractSpiderRobot;
import com.qmcaifu.splider.utils.page.PageParser;

public class FanliSpiderRobot extends AbstractSpiderRobot {

	public FanliSpiderRobot(Parasitifer spider) {
		this.spider = spider;
		fcs.add(new FetchCategory("jujia", "居家", "居家百货", "http://9.fanli.com/?cid=107"));
		fcs.add(new FetchCategory("jujia", "居家", "厨房卫浴", "http://9.fanli.com/?cid=101"));
		fcs.add(new FetchCategory("jujia", "居家", "家具建材", "http://9.fanli.com/?cid=161"));
		fcs.add(new FetchCategory("jujia", "居家", "运动器材", "http://9.fanli.com/?cid=191"));
		fcs.add(new FetchCategory("jujia", "居家", "图片音像", "http://9.fanli.com/?cid=227"));
		fcs.add(new FetchCategory("jujia", "居家", "汽车配饰", "http://9.fanli.com/?cid=225"));
		fcs.add(new FetchCategory("jujia", "居家", "户外用品", "http://9.fanli.com/?cid=223"));
		fcs.add(new FetchCategory("jujia", "居家", "家装软饰", "http://9.fanli.com/?cid=97"));
		fcs.add(new FetchCategory("shipin", "食品", "休闲食品", "http://9.fanli.com/?cid=159"));
		fcs.add(new FetchCategory("shipin", "食品", "坚果蜜饯", "http://9.fanli.com/?cid=189"));
		fcs.add(new FetchCategory("shipin", "食品", "水果生鲜", "http://9.fanli.com/?cid=153"));
		fcs.add(new FetchCategory("shipin", "食品", "茶酒冲饮", "http://9.fanli.com/?cid=157"));
		fcs.add(new FetchCategory("shipin", "食品", "粮油干货", "http://9.fanli.com/?cid=155"));
		fcs.add(new FetchCategory("shuma", "数码", "手机配件", "http://9.fanli.com/?cid=131"));
		fcs.add(new FetchCategory("shuma", "数码", "汽车用品", "http://9.fanli.com/?cid=211"));
		fcs.add(new FetchCategory("peishi", "配饰", "配饰", "http://9.fanli.com/?cid=79"));
		fcs.add(new FetchCategory("meizhuang", "美妆", "美妆", "http://9.fanli.com/?cid=35"));

		goodsListDivSelector = "#J-item-list > div.nine-item";
	}

	public void parseCurrentCategoryCount(FetchCategory fc, List<GoodsInfo> goodsList) {
		try {
			Document document = Jsoup.connect(fc.getUrl()).get();
			String fcDesc = document.select("div.home-tuan-subcat2 a.current").text();
			String productCount = StringUtils.substringBeforeLast(StringUtils.substringAfterLast(fcDesc, "("), ")");
			if (StringUtils.isNumeric(productCount)) {
				int pageCount = 1;
				if (Integer.parseInt(productCount) % 60 == 0) {
					pageCount = Integer.parseInt(productCount) / 60;
				} else {
					pageCount = Integer.parseInt(productCount) / 60 + 1;
				}

				logger.info("当前{}产品总共有： {}页", fc.getCategoryDesc(), pageCount);

				for (int i = 0; i < pageCount; i++) {
					String page = fc.getUrl();
					if (i != 0) {
						page = fc.getUrl() + "&p=" + (i + 1);
						document = Jsoup.connect(page).get();
					}

					parseListPageDocument(fc, document, goodsList);
				}
			} else {
				parseListPageDocument(fc, document, goodsList);
			}
		} catch (Exception e) {
			logger.error("parseCurrentCategoryCount - error", e);
		}
	}

	public GoodsInfo parseDetailPageDocument(FetchCategory fc, Element product) {
		GoodsInfo goodsInfo = null;
		try {
			String id = spider.getCode() + product.attr("data-id");
			String jsonStr = redisManager.hget(spider.getCode(), id);
			if (StringUtils.isBlank(jsonStr)) {
				Element productA = product.select("a.link").get(0);

				// 通过中转跳转到淘宝的页面,获取并进行URL解码，再截取获得淘宝的页面
				String dataHref = productA.attr("data-href");
				String haxUrl = URLDecoder.decode(dataHref, Charset.defaultCharset().toString());
				String thirdPage = StringUtils.substringBeforeLast(StringUtils.substringAfterLast(haxUrl, "&go="), "&pid");

				goodsInfo = PageParser.parse(thirdPage);
				if (goodsInfo != null) {
					goodsInfo.setId(id);
					goodsInfo.setGoodsName(product.select("div.title").text());
					goodsInfo.setCategory(fc.getCategory());
					goodsInfo.setWebCode(spider.getCode());
					goodsInfo.setWebSite(spider.getWebSite());
					goodsInfo.setSiteName(spider.getSiteName());
					goodsInfo.setPageUrl(productA.attr("href"));// 返利网真实的详情页面
					Element imgElement = product.select("img").get(0);
					goodsInfo.setGoodsImg(imgElement.hasAttr("data-original") ? imgElement.attr("data-original") : imgElement.attr("src"));
					goodsInfo.setNowPrice(StringUtils.replace(product.select("div.price").text(), "¥", StringUtils.EMPTY).trim());
					goodsInfo.setGoodOrgPrice(StringUtils.replace(product.select("div.left-box del").text(), "¥", StringUtils.EMPTY).trim());
				}

				jsonStr = JSON.toJSONString(goodsInfo);
				redisManager.hsetex(spider.getCode(), goodsInfo.getId(), jsonStr, 60 * 60 * 24 * 7);
			} else {
				goodsInfo = JSON.parseObject(jsonStr, GoodsInfo.class);
			}
		} catch (SocketTimeoutException ste) {
			goodsInfo = null;
		} catch (Exception e) {
			goodsInfo = null;
			e.printStackTrace();
		}

		return goodsInfo;
	}

}
