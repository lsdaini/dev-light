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

import net.luis.entity.Admin;
import net.luis.entity.DatatablesViewPage;
import net.luis.entity.Role;
import net.luis.service.AdminService;
import net.luis.service.RoleService;


@Controller
@RequestMapping("/")
public class AdminController {
	private static Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

	@Resource
	private AdminService adminService;

	@Resource
	private RoleService roleService;

	@RequestMapping("/adminIndex")
	public String Index() {
		return "admin/index";
	}

	@RequestMapping("/adminList")
	@ResponseBody
	public DatatablesViewPage<Admin> list(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 获取分页控件的信息
		int start = Integer.parseInt(request.getParameter("start"));
		int length = Integer.parseInt(request.getParameter("length"));
		// 获取前台额外传递过来的查询条件
		String username = request.getParameter("username");
		map.put("start", start);
		map.put("length", length);
		map.put("username", username);
		// 随便组织的查询结果
		List<Admin> list = adminService.selectByParam(map);

		int total = adminService.selectCountByParam(map);

		DatatablesViewPage<Admin> view = new DatatablesViewPage<Admin>();
		// 查询记录数量
		view.setiTotalDisplayRecords(total);
		// view.setiTotalRecords(100);
		view.setAaData(list);
		return view;
	}

	@RequestMapping("/adminAddInput")
	public ModelAndView addInpute() {
		ModelAndView modelAndView = new ModelAndView("admin/add");
		List<Role> list = roleService.selectAll();
		modelAndView.addObject("list", list);
		return modelAndView;
	}

	@RequestMapping("/adminAdd")
	@ResponseBody
	public Map<String, Object> add(Admin admin) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			adminService.insertValidation(map, admin);

			if ((boolean) map.get("success")) {
				adminService.insert(map, admin);
			}
		} catch (Exception e) {
			LOGGER.error("", e);
		}
		return map;
	}

	@RequestMapping("/adminUpdateInput")
	public ModelAndView updateInput(Long id) {
		ModelAndView modelAndView = new ModelAndView("admin/update");
		Admin admin = adminService.selectByPrimaryKey(id);
		modelAndView.addObject("admin", admin);

		List<Role> list = roleService.selectAll();
		modelAndView.addObject("list", list);
		return modelAndView;
	}

	@RequestMapping("/adminUpdate")
	@ResponseBody
	public Map<String, Object> update(Admin admin) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			adminService.updateValidation(map, admin);

			if ((boolean) map.get("success")) {
				adminService.update(map, admin);
			}
		} catch (Exception e) {
			LOGGER.error("", e);
		}
		return map;
	}

	@RequestMapping("/adminDelete")
	@ResponseBody
	public Map<String, Object> delete(Long id) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			adminService.deleteValidation(map, id);
			if ((boolean) map.get("success")) {
				adminService.delete(map, id);
			}
		} catch (Exception e) {
			LOGGER.error("", e);
		}
		return map;
	}
}
