package com.qmcaifu.splider.pojo.enums;

/**
 * 宿主
 * 
 * @author zhuxinyu.carter
 * @version $Id: Parasitifer.java, v 0.1 2016年1月6日 上午10:33:39 zhuxinyu.carter Exp $
 */
public enum Parasitifer {

	ZHE_800("ZHE_800", "http://www.zhe800.com/", "折800"),

	FAN_LI("FAN_LI", "http://www.fanli.com", "返利网"),

	JUAN_PI("JUAN_PI", "http://www.juanpi.com/act/yr1212/", "卷皮网"),

	MI_ZHE("MI_ZHE", "http://www.mizhe.com/", "米折"),

	FAN_HUAN("FAN_HUAN", "http://www.fanhuan.com/", "返还");

	private String code;

	private String webSite;

	private String siteName;

	private Parasitifer(String code, String webSite, String siteName) {
		this.code = code;
		this.webSite = webSite;
		this.siteName = siteName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public static Parasitifer getParasitifer(String code) {
		for (Parasitifer tt : Parasitifer.values()) {
			if (tt.code.equals(code)) {
				return tt;
			}
		}
		return null;
	}
}
