package com.qmcaifu.splider.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.qmcaifu.splider.pojo.GoodsInfo;
import com.qmcaifu.splider.pojo.enums.Parasitifer;
import com.qmcaifu.splider.utils.HttpUtils;
import com.qmcaifu.splider.utils.cache.RedisManager;
import com.qmcaifu.splider.utils.spring.SpringContextHolder;

/**
 * http://stackoverflow.com/questions/7744075/how-to-connect-via-https-using-jsoup
 * 
 * @author zhuxinyu.carter
 * @version $Id: AbstractSpiderRobot.java, v 0.1 2016年1月7日 下午2:36:07 zhuxinyu.carter Exp $
 */
public abstract class AbstractSpiderRobot implements SpiderRobot {

	protected Parasitifer spider = null;

	protected static RedisManager redisManager = null;

	protected List<FetchCategory> fcs = new ArrayList<FetchCategory>();

	protected String goodsListDivSelector = "div.deal";

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String[] QMCAIFU_CATEGORY = new String[] {"jujia", "shuma", "meizhuang", "peishi", "shipin"};

	static {
		redisManager = SpringContextHolder.getBean("redisManager");
		HttpUtils.trustEveryone();
	}

	public void spiderIt() {
		logger.info("spiderIt  -  开始抓取 {}", spider.getSiteName());

		// 删除当前分类
		for (String c : QMCAIFU_CATEGORY) {
			redisManager.hdel(c, spider.getCode());
		}

		for (FetchCategory fc : fcs) {
			List<GoodsInfo> goodsList = new ArrayList<GoodsInfo>();
			
			parseCurrentCategoryCount(fc, goodsList);
			
			String preGoodsList = redisManager.hget(fc.getCategory(), spider.getCode());
			if (StringUtils.isNotBlank(preGoodsList)) {
				List<GoodsInfo> preGoodsInfo = JSON.parseArray(preGoodsList, GoodsInfo.class);
				goodsList.addAll(preGoodsInfo);
				redisManager.hdel(fc.getCategory(), spider.getCode());
			}
			String jsonStr = JSON.toJSONString(goodsList);
			redisManager.hsetex(fc.getCategory(), spider.getCode(), jsonStr, 60 * 60 * 24 * 7);
			logger.info("该品类 {} 共有多少产品：  {}", fc.getCategoryDesc(), goodsList.size());
		}
		logger.info("spiderIt  -  结束抓取 {}", spider.getSiteName());
	}

	public abstract void parseCurrentCategoryCount(FetchCategory fc, List<GoodsInfo> goodsList);

	public void parseListPageDocument(FetchCategory fc, Document document, List<GoodsInfo> goodsList) {
		Elements goodsInfos = document.select(goodsListDivSelector);

		logger.info("当前页面有" + goodsInfos.size() + "条产品");

		for (int i = 0; i < goodsInfos.size(); i++) {
			Element goodsDiv = goodsInfos.get(i);
			GoodsInfo goodsInfo = parseDetailPageDocument(fc, goodsDiv);
			if (goodsInfo != null) {
				goodsList.add(goodsInfo);
			}
		}
	}

	public GoodsInfo parseDetailPageDocument(FetchCategory fc, Element product) {
		return null;
	}
}
