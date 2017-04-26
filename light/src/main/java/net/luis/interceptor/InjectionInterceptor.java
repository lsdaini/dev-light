package net.luis.interceptor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.luis.utils.File;

public class InjectionInterceptor {

	private static final long serialVersionUID = 1L;

	public static Boolean __SYS_SHOVE_FLAG_IsUsed_InjectionInterceptor = false;

	private static List<String> validImgExtName = null;

	private static final String rule0 = "<[^>]+?style=[\\w]+?:expression\\(|\\b(alert|confirm|prompt)\\b|^\\+/v(8|9)|<[^>]*?=[^>]*?&#[^>]*?>|\\b(and|or)\\b.{1,6}?(=|>|<|\\bin\\b|\\blike\\b)|/\\*.+?\\*/|<\\s*script\\b|<\\s*img\\b|\\bEXEC\\b|UNION.+?SELECT|UPDATE.+?SET|INSERT\\s+INTO.+?VALUES|(SELECT|DELETE).+?FROM|(CREATE|ALTER|DROP|TRUNCATE)\\s+(TABLE|DATABASE)|[\']+?.*?(OR|AND|[-]{2,}|UPDATE|CREATE|ALTER|DROP|TRUNCATE|SELECT|DELETE|EXEC|INSERT)\\b|\\b(OR|AND|[-]{2,}|UPDATE|CREATE|ALTER|DROP|TRUNCATE|SELECT|DELETE|EXEC|INSERT)\\b.*?[\']+?";
	private static final String rule1 = "<[^>]+?style=[\\w]+?:expression\\(|\\b(alert|confirm|prompt)\\b|^\\+/v(8|9)|<[^>]*?=[^>]*?&#[^>]*?>|\\b(and|or)\\b.{1,6}?(=|>|<|\\bin\\b|\\blike\\b)|/\\*.+?\\*/|<\\s*script\\b|\\bEXEC\\b|UNION.+?SELECT|UPDATE.+?SET|INSERT\\s+INTO.+?VALUES|(SELECT|DELETE).+?FROM|(CREATE|ALTER|DROP|TRUNCATE)\\s+(TABLE|DATABASE)|[\']+?.*?(OR|AND|[-]{2,}|UPDATE|CREATE|ALTER|DROP|TRUNCATE|SELECT|DELETE|EXEC|INSERT)\\b|\\b(OR|AND|[-]{2,}|UPDATE|CREATE|ALTER|DROP|TRUNCATE|SELECT|DELETE|EXEC|INSERT)\\b.*?[\']+?";
	private static Pattern pattern0 = Pattern.compile(rule0, Pattern.CASE_INSENSITIVE);
	private static Pattern pattern1 = Pattern.compile(rule1, Pattern.CASE_INSENSITIVE);

	private static final String imgRule = "<img\\b[^<>]*?\\bsrc[\\s\t\r\n]*=[\\s\t\r\n]*[\"']?[\\s\t\r\n]*([^\\s\t\r\n\"'<>]*)[^<>]*?/?[\\s\t\r\n]*[/]*>";
	private static Pattern patternImg = Pattern.compile(imgRule, Pattern.CASE_INSENSITIVE);

	private int exceptionLevel = 0;

	public int getExceptionLevel() {
		return exceptionLevel;
	}

	public void setExceptionLevel(int exceptionLevel) {
		this.exceptionLevel = exceptionLevel;
	}

	private Map<String, String[]> parameterMap = null;
	private Cookie[] cookies = null;
	private String referer = null;

	public String intercept(HttpServletRequest request, HttpServletResponse response) throws Exception {

		parameterMap = request.getParameterMap();
		cookies = request.getCookies();
		referer = request.getHeader("Referer");

		if (parameterMap.isEmpty() && (cookies == null) && (referer == null)) {

			return null;
		}

		if (!__SYS_SHOVE_FLAG_IsUsed_InjectionInterceptor) {
			initialize();
		}

		String result = null;

		if (exceptionLevel == 0) {
			result = intercept(pattern0, false, request, response);
		} else if (exceptionLevel == 1) {
			result = intercept(pattern1, true, request, response);
		} else {
			result = null;
		}

		return (result == null) ? result : (isAjaxRequest(request, response) ? null : result);
	}

	private synchronized void initialize() {

		if (__SYS_SHOVE_FLAG_IsUsed_InjectionInterceptor) {
			return;
		}

		__SYS_SHOVE_FLAG_IsUsed_InjectionInterceptor = true;

		validImgExtName = new ArrayList<String>();
		validImgExtName.add(".jpg");
		validImgExtName.add(".jpeg");
		validImgExtName.add(".png");
		validImgExtName.add(".bmp");
		validImgExtName.add(".gif");
		validImgExtName.add(".tif");
		validImgExtName.add(".tiff");
	}

	private boolean isAjaxRequest(HttpServletRequest request, HttpServletResponse response) {

		String header = request.getHeader("X-Requested-With");

		return ((header != null) && ("XMLHttpRequest".equals(header)));
	}

	private String intercept(Pattern pattern, Boolean isCheckImg, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		if (checkCookies(pattern, isCheckImg)) {
			return buildReturnValue("Cookie", request, response);
		}

		if (checkReferer(pattern, isCheckImg)) {
			return buildReturnValue("Referer", request, response);
		}

		if (checkRequest(pattern, isCheckImg)) {
			return buildReturnValue("POST、GET", request, response);
		}

		return null;
	}

	private Boolean checkCookies(Pattern pattern, Boolean isCheckImg) {

		if ((null == cookies) || (cookies.length == 0)) {

			return false;
		}

		for (Cookie c : cookies) {
			if (checkData(pattern, c.getValue(), isCheckImg)) {

				return true;
			}
		}

		return false;
	}

	private Boolean checkReferer(Pattern pattern, Boolean isCheckImg) {

		if ((null == referer) || (referer.isEmpty())) {

			return false;
		}

		if (checkData(pattern, referer, isCheckImg)) {

			return true;
		}

		return false;
	}

	private Boolean checkRequest(Pattern pattern, Boolean isCheckImg) {

		if (parameterMap.isEmpty()) {

			return false;
		}

		Set<String> keySet = parameterMap.keySet();
		Iterator<String> iterator = keySet.iterator();

		while (iterator.hasNext()) {

			String[] values = parameterMap.get(iterator.next());

			for (String value : values) {

				if (checkData(pattern, value, isCheckImg)) {

					return true;
				}
			}
		}

		return false;
	}

	private Boolean checkData(Pattern pattern, String value, Boolean isCheckImg) {

		if ((value == null) || (value.isEmpty())) {
			return false;
		}

		if (pattern.matcher(value).find()) {
			return true;
		}

		if (!isCheckImg) {
			return false;
		}

		// 校验 IMG 文件扩展名是否合法
		Matcher m = patternImg.matcher(value);

		while (m.find()) {
			String fileName = m.group(1);

			if (!validImgExtName.contains(File.getExtensionName(fileName).toLowerCase())) {
				return true;
			}
		}

		return false;
	}

	private String buildReturnValue(String checkType, HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		return "InjectionInterceptorError: 系统检测到您提交的数据中存在恶意的注入型攻击数据(或 img 标签的 src 文件类型不合法)，请检查 " + checkType
				+ " 数据，如果是系统误报，请联系我们处理，谢谢。给您带来了不便，十分抱歉！";

	}
}
