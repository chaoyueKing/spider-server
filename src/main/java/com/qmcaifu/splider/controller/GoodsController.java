package com.qmcaifu.splider.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.qmcaifu.splider.pojo.GoodsInfo;
import com.qmcaifu.splider.pojo.enums.Parasitifer;
import com.qmcaifu.splider.utils.cache.RedisManager;

@Controller
public class GoodsController {

	@Autowired
	@Qualifier("redisManager")
	private RedisManager redisManager;

	/**
	 * 查询指定宿主的网站产品信息
	 */
	@RequestMapping("goodsList/{pcode}")
	public String queryProduct(ModelMap modelMap, @PathVariable("pcode") String pcode) {
		return queryProduct(modelMap, pcode, null);
	}
	
	/**
	 * 查询指定宿主的网站下的分类的产品信息
	 */
	@RequestMapping("goodsList/{pcode}/{pcategory}")
	public String queryProduct(ModelMap modelMap, @PathVariable("pcode") String parasitiferCode, @PathVariable("pcategory") String pcategory) {
		Parasitifer parasitifer = Parasitifer.getParasitifer(parasitiferCode);

		List<GoodsInfo> list = null;
		if (parasitifer != null) {
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
			
			modelMap.put("siteName", parasitifer.getSiteName());
		}
		
		modelMap.put("goodsListCount", list == null ? 0 : list.size());
		modelMap.put("pcode", parasitiferCode);
		modelMap.put("pcategory", pcategory);
		modelMap.put("goodsList", list);

		return "goodsList";
	}

	/**
	 * 查询指定宿主的网站下的单个产品信息
	 */
	@RequestMapping("goodsDetail/{pcode}/{sid}")
	public String showProduct(ModelMap modelMap, @PathVariable("pcode") String parasitiferCode, @PathVariable("sid") String id) {
		Parasitifer parasitifer = Parasitifer.getParasitifer(parasitiferCode);
		if (parasitifer != null) {
			String goodsInfoStr = redisManager.hget(parasitifer.getCode(), id);

			GoodsInfo goodsInfo = JSON.parseObject(goodsInfoStr, GoodsInfo.class);

			modelMap.put("goodsInfo", goodsInfo);
		}

		modelMap.put("pcode", parasitiferCode);
		return "goodsDetail";
	}
}
