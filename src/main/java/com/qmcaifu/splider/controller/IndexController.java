package com.qmcaifu.splider.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

	/**
	 * 访问首页，可以查看内存使用情况
	 */
	@RequestMapping("index")
	public String indexPage(ModelMap modelMap) {
		Runtime runtime = Runtime.getRuntime();
		long freeMemoery = runtime.freeMemory();
		long totalMemory = runtime.totalMemory();
		long usedMemory = totalMemory - freeMemoery;
		long maxMemory = runtime.maxMemory();
		long useableMemory = maxMemory - totalMemory + freeMemoery;

		modelMap.addAttribute("freeMemoery", freeMemoery);
		modelMap.addAttribute("totalMemory", totalMemory);
		modelMap.addAttribute("usedMemory", usedMemory);
		modelMap.addAttribute("maxMemory", maxMemory);
		modelMap.addAttribute("useableMemory", useableMemory);

		return "index";
	}
}
