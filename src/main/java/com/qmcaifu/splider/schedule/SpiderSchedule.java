package com.qmcaifu.splider.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.qmcaifu.splider.pojo.enums.Parasitifer;

/**
 * 蜘蛛爬寻定时任务，后期可以交给调度中心来完成
 * 
 * @author zhuxinyu.carter
 * @version $Id: SpiderSchedule.java, v 0.1 2016年1月4日 下午5:15:46 zhuxinyu.carter Exp $
 */
@Component
public class SpiderSchedule {

	@Autowired
	private SpiderWoker spiderWoker;

	private static final Logger logger = LoggerFactory.getLogger(SpiderSchedule.class);

	@Scheduled(cron = "0 10 09 * * ?")
	public void process() {
		logger.info("现在开始执行抓取任务调度");
		Parasitifer[] parasitifers = Parasitifer.values();
		for (final Parasitifer p : parasitifers) {
			spiderWoker.process(p);
		}
		logger.info("抓取任务调度已经分发完毕");
	}
}
