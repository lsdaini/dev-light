package net.luis.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSONObject;

import net.luis.entity.Admin;
import net.luis.utils.Login;
import net.luis.utils.StringUtils;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	private static Log log = LogFactory.getLog(LoginInterceptor.class);

	/**
	 * 登录拦截
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (handler instanceof HandlerMethod) {

			String requestType = request.getHeader("X-Requested-With");
			String path = request.getContextPath();

			// 日志打印
			Log log = LogFactory.getLog(LoginInterceptor.class);
			Admin admin = (Admin) request.getSession().getAttribute("admin");

			String message = admin == null ? "" : "(用户名:" + admin.getUsername() + ")";
			log.info("正在执行action请求命令" + message + ":" + request.getRequestURL());

			// 安全拦截
			InjectionInterceptor interceptor = new InjectionInterceptor();

			String result = interceptor.intercept(request, response);
			if (result != null) {
				response.setContentType("text/html; charset=utf-8");
				response.getWriter().print(result);
				return false;
			}

			// 登录拦截
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();
			Login annotation = method.getAnnotation(Login.class);
			if (annotation != null) {
				boolean needlind = annotation.needLogin();
				if (needlind) {
					if (request.getSession().getAttribute("admin") != null) {
						return true;
					} else {
						if (!StringUtils.isEmpty(requestType) && requestType.equals("XMLHttpRequest")) {
							// 异步请求
							JSONObject obj = new JSONObject();
							obj.put("msg", "noLogin");
							response.getWriter().print(obj);
						} else {
//							response.sendRedirect(path + "/login.do?beforeURL=" + request.getRequestURI() + "?"
//									+ request.getQueryString());
							response.sendRedirect(path + "/loginInput");
						}
						return false;
					}
				}
			}
			return true;
		} else {
			return super.preHandle(request, response, handler);
		}

	}

}
