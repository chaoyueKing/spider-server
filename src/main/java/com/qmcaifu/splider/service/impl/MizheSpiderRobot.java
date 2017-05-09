package com.qmcaifu.splider.service.impl;

import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.qmcaifu.splider.pojo.GoodsInfo;
import com.qmcaifu.splider.pojo.enums.Parasitifer;
import com.qmcaifu.splider.service.AbstractSpiderRobot;

public class MizheSpiderRobot extends AbstractSpiderRobot {

	public MizheSpiderRobot(Parasitifer spider) {
		this.spider = spider;
	}

	@Override
	public void parseCurrentCategoryCount(FetchCategory fc, List<GoodsInfo> goodsList) {
	}

	public void parseListPageDocument(FetchCategory fc, Document document, List<GoodsInfo> goodsList) {

	}

	public GoodsInfo parseDetailPageDocument(FetchCategory fc, Element product) {
		return null;
	}


}
