package net.luis.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.luis.entity.Admin;
import net.luis.service.AdminService;


@Controller
public class LoginController {
	private static Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

	@Resource
	private AdminService adminService;

	@RequestMapping("/loginInput")
	public ModelAndView loginInput(String beforeURL) {
		ModelAndView modelAndView = new ModelAndView("login");
		// modelAndView.addObject("beforeURL", beforeURL);
		return modelAndView;
	}

	@RequestMapping("/login")
	@ResponseBody
	public Map<String, Object> login(Admin admin) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			adminService.loginValidation(map, admin);
			if ((boolean) map.get("success")) {
				adminService.login(map, admin);
			}
		} catch (Exception e) {
			LOGGER.error("", e);
		}
		return map;
	}

	@RequestMapping("/loginOut")
	public ModelAndView loginOut(HttpServletRequest request) {
		request.getSession().removeAttribute("admin");
		ModelAndView modelAndView = new ModelAndView("login");
		return modelAndView;
	}
}
