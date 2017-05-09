package com.qmcaifu.splider.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class WebExceptionHandler implements HandlerExceptionResolver {

	private static final Logger logger = LoggerFactory.getLogger("commons-error");

	public ModelAndView resolveException(HttpServletRequest httpRequest, HttpServletResponse httpResponse, Object handler, Exception ex) {
		logger.error("resolveException - " + handler, ex);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("error");
		return modelAndView;
	}

}
