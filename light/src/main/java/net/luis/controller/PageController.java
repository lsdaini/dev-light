package net.luis.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import net.luis.entity.Admin;
import net.luis.entity.Menu;
import net.luis.service.MenuService;
import net.luis.utils.ContextUtils;
import net.luis.utils.Login;


@Controller
public class PageController {

	@Resource
	private MenuService menuService;

	@Login(needLogin = true)
	@RequestMapping("/index")
	public ModelAndView showIndex() {
		ModelAndView modelAndView = new ModelAndView("index");
		Admin admin = (Admin) ContextUtils.getSession().getAttribute("admin");
		List<Menu> list = menuService.selectTopByAdminId(admin.getId());
		for (Menu menu : list) {
			List<Menu> childList = menuService.selectByParentIdAndAdminId(menu.getId(), admin.getId());
			menu.setChilds(childList);
		}
		modelAndView.addObject("admin", admin);
		modelAndView.addObject("list", list);
		return modelAndView;
	}

	@RequestMapping("/welcome")
	public String welcome() {
		return "welcome";
	}
}
