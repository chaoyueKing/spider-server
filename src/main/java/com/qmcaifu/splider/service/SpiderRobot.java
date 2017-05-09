package com.qmcaifu.splider.service;

import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.qmcaifu.splider.pojo.GoodsInfo;

public interface SpiderRobot {

	void spiderIt();

	void parseCurrentCategoryCount(FetchCategory fc, List<GoodsInfo> goodsList);
	
	void parseListPageDocument(FetchCategory fc, Document document, List<GoodsInfo> goodsList);

	GoodsInfo parseDetailPageDocument(FetchCategory fc, Element product);

	class FetchCategory {
		private String category;
		private String categoryDesc;
		private String orginCategoryDesc;
		private String url;

		public FetchCategory(String category, String categoryDesc, String orginCategoryDesc, String url) {
			this.category = category;
			this.categoryDesc = categoryDesc;
			this.orginCategoryDesc = orginCategoryDesc;
			this.url = url;
		}

		public FetchCategory(String category, String categoryDesc, String url) {
			this(category, categoryDesc, null, url);
		}

		public String getCategory() {
			return category;
		}

		public String getCategoryDesc() {
			return categoryDesc;
		}

		public String getOrginCategoryDesc() {
			return orginCategoryDesc;
		}

		public String getUrl() {
			return url;
		}

		@Override
		public int hashCode() {
			return HashCodeBuilder.reflectionHashCode(this);
		}

		@Override
		public boolean equals(Object obj) {
			return EqualsBuilder.reflectionEquals(this, obj);
		}
	}
}
