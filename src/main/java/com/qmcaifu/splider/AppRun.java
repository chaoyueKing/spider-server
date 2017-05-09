package com.qmcaifu.splider;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.qmcaifu.splider.pojo.enums.Parasitifer;
import com.qmcaifu.splider.service.SpiderRobot;

/**
 * 通过main函数本地启动抓取数据，主要用于调试
 * 
 * @author zhuxinyu.carter
 * @version $Id: AppRun.java, v 0.1 2016年1月4日 下午2:52:44 zhuxinyu.carter Exp $
 */
public class AppRun {

	private static ClassPathXmlApplicationContext context;

	public static void main(String[] args) {
		long begin = System.currentTimeMillis();
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		System.out.println((context.getStartupDate() - begin) + "ms,已经初始化完成");
		
		Parasitifer[] parasitifers = Parasitifer.values();
		for (final Parasitifer p : parasitifers) {
			new Thread(new Runnable() {
				public void run() {
					SpiderRobot spiderRobot = GoodsSpiderFactory.getGoodsSpider(p);
					spiderRobot.spiderIt();
				}
			}).start();
		}
	}
}
