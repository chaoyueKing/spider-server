package com.qmcaifu.splider.service.impl;

import java.net.SocketTimeoutException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSON;
import com.qmcaifu.splider.pojo.GoodsInfo;
import com.qmcaifu.splider.pojo.enums.Parasitifer;
import com.qmcaifu.splider.service.AbstractSpiderRobot;
import com.qmcaifu.splider.utils.page.PageParser;

/**
 * 折800的数据非常特殊，每个品类的数据，多次请求其返回的页面总数是不定的，处理方式是，每次获取总数分别处理其详情，然后更新到本地
 * 
 * @author zhuxinyu.carter
 * @version $Id: Zhe800SpiderRobot.java, v 0.1 2015年12月21日 下午1:40:38 zhuxinyu.carter Exp $
 */
public class Zhe800SpiderRobot extends AbstractSpiderRobot {

	public Zhe800SpiderRobot(Parasitifer spider) {
		this.spider = spider;

		fcs.add(new FetchCategory("jujia", "居家", "http://www.zhe800.com/ju_type/baoyou/taojujia"));
		fcs.add(new FetchCategory("shuma", "数码", "http://www.zhe800.com/ju_type/baoyou/taoshuma"));
		fcs.add(new FetchCategory("meizhuang", "美妆", "http://www.zhe800.com/ju_type/baoyou/taomeizhuang"));
		fcs.add(new FetchCategory("peishi", "配饰", "http://www.zhe800.com/ju_type/baoyou/taopeishi"));
		fcs.add(new FetchCategory("shipin", "食品", "http://www.zhe800.com/ju_type/baoyou/taomeishi"));

		goodsListDivSelector = "#dealWarpperwide_e div.deal";
	}

	public void parseCurrentCategoryCount(FetchCategory fc, List<GoodsInfo> goodsList) {
		try {
			Document document = Jsoup.connect(fc.getUrl()).get();
			Elements listPage = document.select("div.list_page");
			if (listPage.size() > 0) {
				String pageStr = listPage.first().select("span.page").last().text().trim();
				logger.info("当前{}产品总共有： {} 页", fc.getCategoryDesc(), pageStr);

				if (StringUtils.isNotBlank(pageStr)) {
					int pageCount = Integer.parseInt(pageStr);
					for (int i = 0; i < pageCount; i++) {
						String jujia_page = fc.getUrl();
						if (i != 0) {
							jujia_page = fc.getUrl() + "/page/" + (i + 1);
							document = Jsoup.connect(jujia_page).get();
						}

						parseListPageDocument(fc, document, goodsList);
					}
				}
			} else {
				logger.info("该品类{} 没有分页，直接获取", fc.getCategoryDesc());
				parseListPageDocument(fc, document, goodsList);
			}
		} catch (Exception e) {
			logger.error("spiderIt 抓取页面error", e);
		}
	}

	public GoodsInfo parseDetailPageDocument(FetchCategory fc, Element product) {
		GoodsInfo goodsInfo = null;
		try {
			Element productA = product.select("a.goods_img").get(0);
			String pageUrl = productA.attr("href");

			if (StringUtils.startsWith(pageUrl, "http")) {
				String id = "zhe800_" + StringUtils.substringAfterLast(pageUrl, "/");
				String jsonStr = redisManager.hget(spider.getCode(), id);
				if (StringUtils.isBlank(jsonStr)) {
					goodsInfo = PageParser.parse(pageUrl);
					if (goodsInfo != null) {
						goodsInfo.setId(id);
						goodsInfo.setGoodsName(product.select("h3 > a").text());
						goodsInfo.setCategory(fc.getCategory());
						goodsInfo.setWebCode(spider.getCode());
						goodsInfo.setWebSite(spider.getWebSite());
						goodsInfo.setSiteName(spider.getSiteName());
						goodsInfo.setPageUrl(pageUrl);
						goodsInfo.setGoodsImg(productA.select("img").get(0).attr("data-original"));
						goodsInfo.setNowPrice(StringUtils.replace(product.select("h4 > em").text(), "¥", StringUtils.EMPTY).trim());
						goodsInfo.setGoodOrgPrice(StringUtils.replace(product.select("h4 i").text(), "¥", StringUtils.EMPTY).trim());
					}

					jsonStr = JSON.toJSONString(goodsInfo);
					redisManager.hsetex(spider.getCode(), goodsInfo.getId(), jsonStr, 60 * 60 * 24 * 7);
				} else {
					goodsInfo = JSON.parseObject(jsonStr, GoodsInfo.class);
				}
			}
		} catch (SocketTimeoutException ste) {
			goodsInfo = null;
		} catch (Exception e) {
			goodsInfo = null;
			logger.error("parsePageDocument 解析页面error", e);
		}

		return goodsInfo;
	}
}
