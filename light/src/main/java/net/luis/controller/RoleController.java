package net.luis.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import net.luis.entity.DatatablesViewPage;
import net.luis.entity.Menu;
import net.luis.entity.Role;
import net.luis.service.MenuService;
import net.luis.service.RoleMenuService;
import net.luis.service.RoleService;


@Controller
@RequestMapping("/")
public class RoleController {
	private static Logger LOGGER = LoggerFactory.getLogger(RoleController.class);

	@Resource
	private RoleService roleService;

	@Resource
	private MenuService menuService;

	@Resource
	private RoleMenuService roleMenuService;

	@RequestMapping("/roleIndex")
	public String Index() {
		return "role/index";
	}

	@RequestMapping("/roleList")
	@ResponseBody
	public DatatablesViewPage<Role> list(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 获取分页控件的信息
		int start = Integer.parseInt(request.getParameter("start"));
		int length = Integer.parseInt(request.getParameter("length"));
		// 获取前台额外传递过来的查询条件
		String name = request.getParameter("name");
		map.put("start", start);
		map.put("length", length);
		map.put("name", name);
		// 随便组织的查询结果
		List<Role> list = roleService.selectByParam(map);

		int total = roleService.selectCountByParam(map);

		DatatablesViewPage<Role> view = new DatatablesViewPage<Role>();
		// 查询记录数量
		view.setiTotalDisplayRecords(total);
		// view.setiTotalRecords(100);
		view.setAaData(list);
		return view;
	}

	@RequestMapping("/roleAddInput")
	public ModelAndView addInpute() {
		ModelAndView modelAndView = new ModelAndView("role/add");
		List<Menu> parentList = menuService.selectTopMenu();
		for (Menu menu : parentList) {
			List<Menu> childList = menuService.selectByParentId(menu.getId());
			menu.setChilds(childList);
		}
		modelAndView.addObject("parentList", parentList);
		return modelAndView;
	}

	@RequestMapping("/roleAdd")
	@ResponseBody
	public Map<String, Object> add(Role role) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			roleService.insertValidation(map, role);

			if ((boolean) map.get("success")) {
				roleService.insert(map, role);
			}
		} catch (Exception e) {
			LOGGER.error("", e);
		}
		return map;
	}

	@RequestMapping("/roleUpdateInput")
	public ModelAndView updateInput(Long id) {
		ModelAndView modelAndView = new ModelAndView("role/update");
		Role role = roleService.selectByPrimaryKey(id);
		modelAndView.addObject("role", role);

		List<Long> menus = roleMenuService.selectMenusByroleId(id);
		modelAndView.addObject("menus", menus);

		List<Menu> parentList = menuService.selectTopMenu();
		for (Menu menu : parentList) {
			List<Menu> childList = menuService.selectByParentId(menu.getId());
			menu.setChilds(childList);
		}
		modelAndView.addObject("parentList", parentList);
		return modelAndView;
	}

	@RequestMapping("/roleUpdate")
	@ResponseBody
	public Map<String, Object> update(Role role) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			roleService.updateValidation(map, role);

			if ((boolean) map.get("success")) {
				roleService.update(map, role);
			}
		} catch (Exception e) {
			LOGGER.error("", e);
		}
		return map;
	}

	@RequestMapping("/roleDelete")
	@ResponseBody
	public Map<String, Object> delete(Long id) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			roleService.deleteValidation(map, id);
			if ((boolean) map.get("success")) {
				roleService.delete(map, id);
			}
		} catch (Exception e) {
			LOGGER.error("", e);
		}
		return map;
	}
}
