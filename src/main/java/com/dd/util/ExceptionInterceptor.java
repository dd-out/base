package com.dd.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class ExceptionInterceptor extends HandlerInterceptorAdapter {
	private static Log logger = LogFactory.getLog(ExceptionInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		boolean result = false;
		try {
			result = super.preHandle(request, response, handler);
		} catch (BIException bi) {
			logger.error(bi);
		} catch (Exception e) {
			logger.error(e);
		}
		return result;
	}
}
