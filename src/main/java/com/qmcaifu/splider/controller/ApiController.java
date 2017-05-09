package com.qmcaifu.splider.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.qmcaifu.splider.constance.GlobalConstance;
import com.qmcaifu.splider.pojo.GoodsInfo;
import com.qmcaifu.splider.pojo.enums.Parasitifer;
import com.qmcaifu.splider.schedule.SpiderWoker;
import com.qmcaifu.splider.utils.cache.RedisManager;
import com.qmcaifu.splider.utils.page.PageParser;

/**
 * 对外提供服务
 * 
 * @author zhuxinyu.carter
 * @version $Id: ApiController.java, v 0.1 2016年1月5日 上午11:30:21 zhuxinyu.carter Exp $
 */
@Controller
@RequestMapping("api")
public class ApiController {

	@Autowired
	@Qualifier("redisManager")
	private RedisManager redisManager;
	
	@Autowired
	private SpiderWoker spiderWoker;

	private static final Logger logger = LoggerFactory.getLogger(ApiController.class);

	@RequestMapping("goodsList/{pcode}")
	public Map<String, Object> queryProduct(ModelMap modelMap, @PathVariable("pcode") String pcode) {
		return queryProduct(modelMap, pcode, null);
	}

	@ResponseBody
	@RequestMapping("goodsList/{pcode}/{pcategory}")
	public Map<String, Object> queryProduct(ModelMap modelMap, @PathVariable("pcode") String parasitiferCode, @PathVariable("pcategory") String pcategory) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("infoCode", 200);

		Parasitifer parasitifer = Parasitifer.getParasitifer(parasitiferCode);

		if (parasitifer != null) {
			List<GoodsInfo> list = null;

			if (StringUtils.isNotBlank(pcategory)) {
				String goodstrList = redisManager.hget(pcategory, parasitifer.getCode());
				list = JSON.parseArray(goodstrList, GoodsInfo.class);
			} else {
				Map<String, String> product = redisManager.hgetAll(parasitifer.getCode());
				list = new ArrayList<GoodsInfo>(product.size());

				for (Entry<String, String> entry : product.entrySet()) {
					list.add(JSON.parseObject(entry.getValue(), GoodsInfo.class));
				}
			}

			map.put("data", list);
		} else {
			map.put("infoCode", 500);
			map.put("infoMsg", "找不到您需要的宿主");
		}

		return map;
	}

	/**
	 * 查询指定宿主的网站下的单个产品信息
	 */
	@ResponseBody
	@RequestMapping("goodsDetail/{pcode}/{sid}")
	public Map<String, Object> showProduct(@PathVariable("pcode") String parasitiferCode, @PathVariable("sid") String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("infoCode", 200);

		Parasitifer parasitifer = Parasitifer.getParasitifer(parasitiferCode);
		if (parasitifer != null) {
			String goodsInfoStr = redisManager.hget(parasitifer.getCode(), id);
			GoodsInfo goodsInfo = JSON.parseObject(goodsInfoStr, GoodsInfo.class);

			if (goodsInfo == null) {
				map.put("infoCode", 404);
				map.put("infoMsg", "找不到您需要的产品详情");
			} else {
				map.put("data", goodsInfo);
			}
		} else {
			map.put("infoCode", 500);
			map.put("infoMsg", "找不到您需要的宿主");
		}

		return map;
	}

	@ResponseBody
	@RequestMapping("/fetchDuxuan")
	public Map<String, Object> fetchDuxuan(HttpServletRequest httpRequest,String fetchUrl, String userName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("infoCode", 200);
		try {
			if (ArrayUtils.contains(GlobalConstance.AUTH_USERNames, userName) || StringUtils.startsWith(fetchUrl, "http")) {
				GoodsInfo goodsInfo = PageParser.parse(fetchUrl);
				goodsInfo.setId("qmcaifu_" + httpRequest.getParameter("id"));//taobao, tmall均有id
				
				goodsInfo.setWebCode("QMCAIFU");
				goodsInfo.setWebSite("http://www.qmcaifu.com");
				goodsInfo.setSiteName("全民财富");
				goodsInfo.setPageUrl(fetchUrl);
				
				map.put("data", goodsInfo);
			} else {
				map.put("infoCode", 500);
				map.put("infoMsg", "请您输入正确的指令");
			}
		} catch (Exception e) {
			map.put("infoCode", 500);
			map.put("infoMsg", "抓取信息出现错误");
			logger.error("fetchDuxuan - 出现错误", e);
		}
		return map;
	}

	@ResponseBody
	@RequestMapping("schedule/begOneSchedule")
	public Map<String, Object> begOneSchedule( String parasitiferCode, String userName) {
		Map<String, Object> map = new HashMap<String, Object>();

		if (ArrayUtils.contains(GlobalConstance.AUTH_USERNames, userName)) {
			final Parasitifer p = Parasitifer.getParasitifer(parasitiferCode);

			if (p != null) {
				String isDoing = redisManager.get("Schedule_" + parasitiferCode);

				if (StringUtils.isBlank(isDoing)) {
					spiderWoker.process(p);

					map.put("infoCode", 200);
					map.put("infoMsg", "hi, 我们已经开始执行");
					redisManager.setex("Schedule_" + parasitiferCode, 60 * 3, "OK");
				} else {
					map.put("infoCode", 300);
					map.put("infoMsg", "不要急，上一次的任务还在执行中呢，等5分钟知道哇");
				}
			}
		} else {
			map.put("infoCode", 500);
			map.put("infoMsg", "请您输入正确的指令");
		}

		return map;
	}

}
