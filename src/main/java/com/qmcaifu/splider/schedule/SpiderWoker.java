package com.qmcaifu.splider.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.qmcaifu.splider.GoodsSpiderFactory;
import com.qmcaifu.splider.pojo.enums.Parasitifer;
import com.qmcaifu.splider.service.SpiderRobot;

/**
 * 蜘蛛爬寻定时任务，后期可以交给调度中心来完成
 * 
 * @author zhuxinyu.carter
 * @version $Id: SpiderSchedule.java, v 0.1 2016年1月4日 下午5:15:46 zhuxinyu.carter Exp $
 */
@Component
public class SpiderWoker {

	private static final Logger logger = LoggerFactory.getLogger(SpiderWoker.class);

	@Async
	public void process(Parasitifer parasitifer) {
		logger.info("现在开始执行抓取的平台编号是{}", parasitifer.getCode());

		SpiderRobot spiderRobot = GoodsSpiderFactory.getGoodsSpider(parasitifer);
		spiderRobot.spiderIt();

		logger.info("现在抓取结束的平台编号是{}", parasitifer.getCode());
	}
}
