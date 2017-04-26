package net.luis;

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
import net.luis.service.SystemService;


@Controller
@RequestMapping("/")
public class SystemController {
	private static Logger LOGGER = LoggerFactory.getLogger(SystemController.class);

	@Resource
	private SystemService systemService;

	@RequestMapping("/systemIndex")
	public String Index() {
		return "common/index";
	}

	@RequestMapping("/systemList")
	@ResponseBody
	public DatatablesViewPage<Menu> list(HttpServletRequest request) {
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
		List<Menu> list = systemService.selectByParam(map);

		int total = systemService.selectCountByParam(map);

		DatatablesViewPage<Menu> view = new DatatablesViewPage<Menu>();
		// 查询记录数量
		view.setiTotalDisplayRecords(total);
		// view.setiTotalRecords(100);
		view.setAaData(list);
		return view;
	}

	@RequestMapping("/systemAddInput")
	public ModelAndView addInpute() {
		ModelAndView modelAndView = new ModelAndView("common/add");
		List<Menu> list = systemService.selectTopMenu();
		modelAndView.addObject("list", list);
		return modelAndView;
	}

	@RequestMapping("/systemAdd")
	@ResponseBody
	public Map<String, Object> add(Menu menu) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			systemService.insertValidation(map, menu);

			if ((boolean) map.get("success")) {
				systemService.insert(map, menu);
			}
		} catch (Exception e) {
			LOGGER.error("", e);
		}
		return map;
	}

	@RequestMapping("/systemUpdateInput")
	public ModelAndView updateInput(Long id) {
		ModelAndView modelAndView = new ModelAndView("common/update");
		Menu menu = systemService.selectByPrimaryKey(id);
		modelAndView.addObject("menu", menu);

		List<Menu> list = systemService.selectTopMenu();
		modelAndView.addObject("list", list);
		return modelAndView;
	}

	@RequestMapping("/systemUpdate")
	@ResponseBody
	public Map<String, Object> update(Menu menu) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			systemService.updateValidation(map, menu);

			if ((boolean) map.get("success")) {
				systemService.update(map, menu);
			}
		} catch (Exception e) {
			LOGGER.error("", e);
		}
		return map;
	}

	@RequestMapping("/systemDelete")
	@ResponseBody
	public Map<String, Object> delete(Long id) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			systemService.deleteValidation(map, id);
			if ((boolean) map.get("success")) {
				systemService.delete(map, id);
			}
		} catch (Exception e) {
			LOGGER.error("", e);
		}
		return map;
	}
}
