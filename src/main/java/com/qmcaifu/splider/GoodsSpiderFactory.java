package com.qmcaifu.splider;

import java.util.HashMap;
import java.util.Map;

import com.qmcaifu.splider.pojo.enums.Parasitifer;
import com.qmcaifu.splider.service.SpiderRobot;
import com.qmcaifu.splider.service.impl.FanliSpiderRobot;
import com.qmcaifu.splider.service.impl.JuanPiSpiderRobot;
import com.qmcaifu.splider.service.impl.MizheSpiderRobot;
import com.qmcaifu.splider.service.impl.Zhe800SpiderRobot;

/**
 * <pre>
 * 折800	http://www.zhe800.com/
 * 返利网	www.fanli.com
 * 卷皮网	http://www.juanpi.com/act/yr1212/
 * 米折	http://www.mizhe.com/
 * 返还网	http://www.fanhuan.com/
 * </pre>
 * 
 * @author zhuxinyu.carter
 * @version $Id: ProductWebFactory.java, v 0.1 2015年12月12日 下午11:27:56 zhuxinyu.carter Exp $
 */
public class GoodsSpiderFactory {

	private static final Map<String, SpiderRobot> spiderContainer = new HashMap<String, SpiderRobot>();

	public static SpiderRobot getGoodsSpider(Parasitifer parasitifer) {
		SpiderRobot spiderRobot = spiderContainer.get(parasitifer.getCode());

		if (spiderRobot == null) {
			switch (parasitifer) {
			case ZHE_800:
				spiderRobot = new Zhe800SpiderRobot(parasitifer);
				break;
			case FAN_LI:
				spiderRobot = new FanliSpiderRobot(parasitifer);
				break;
			case JUAN_PI:
				spiderRobot = new JuanPiSpiderRobot(parasitifer);
				break;
			case MI_ZHE:
				spiderRobot = new MizheSpiderRobot(parasitifer);
				break;
			case FAN_HUAN:
				spiderRobot = new FanliSpiderRobot(parasitifer);
				break;
			default:
				throw new RuntimeException("未曾找到合适的蜘蛛来抓取 " + parasitifer.getSiteName() + " 宿主网站信息");
			}

			spiderContainer.put(parasitifer.getCode(), spiderRobot);
		}

		return spiderRobot;
	}
}
