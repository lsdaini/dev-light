package net.luis.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class ContextUtils {

	/**
	 * 从上下文中取得:HttpServletRequest
	 * 
	 * @return
	 */
	public static HttpServletRequest getRequest() {
		HttpServletRequest request = null;
		if (null != RequestContextHolder.getRequestAttributes()
				&& null != ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()) {
			request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		}
		return request;
	}

	/**
	 * 从上下文中取得:HttpSession
	 * 
	 * @return HttpSession
	 */
	public static HttpSession getSession() {
		HttpSession session = null;
		HttpServletRequest request = getRequest();
		if (null != request) {
			session = request.getSession();
		}
		return session;
	}
}
