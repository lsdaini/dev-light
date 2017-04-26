package net.luis.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.luis.entity.Menu;
import net.luis.mapper.MenuMapper;
import net.luis.mapper.RoleMenuMapper;
import net.luis.service.MenuService;
import net.luis.utils.StringUtils;

@Service
public class MenuServiceImpl implements MenuService {

	@Resource
	private MenuMapper menuMapper;
	
	@Resource
	private RoleMenuMapper roleMenuMapper;

	@Override
	public List<Menu> selectTopMenu() {
		return menuMapper.selectTopMenu();
	}

	@Override
	public void insertValidation(Map<String, Object> map, Menu menu) throws Exception {
		map.put("success", false);
		if (menu.getParentid() < 0) {
			map.put("msg", "父级菜单不能为空");
			return;
		}
		if (StringUtils.isEmpty(menu.getName())) {
			map.put("msg", "菜单名称不能为空");
			return;
		}
		Menu menuName = menuMapper.selectByName(menu.getName(),menu.getId());
		if (menuName != null) {
			map.put("msg", "菜单名称已存在");
			return;
		}
		if (menu.getParentid() > 0 && StringUtils.isEmpty(menu.getLink())) {
			map.put("msg", "链接地址不能为空");
			return;
		}
		if (menu.getEnable() < 0) {
			map.put("msg", "状态不能为空");
			return;
		}
		if (menu.getDisplayorder() < 0) {
			map.put("msg", "显示顺序不能为空");
			return;
		}
		map.put("success", true);
	}

	@Override
	public void insert(Map<String, Object> map, Menu menu) throws Exception {
		map.put("success", false);
		int result = menuMapper.insert(menu);
		if (result <= 0) {
			map.put("msg", "添加失败");
			throw new Exception();
		}
		map.put("msg", "添加成功");
		map.put("success", true);
	}

	@Override
	public List<Menu> selectAll() {
		return menuMapper.selectAll();
	}

	@Override
	public Menu selectByPrimaryKey(Long id) {
		return menuMapper.selectByPrimaryKey(id);
	}

	@Override
	public void updateValidation(Map<String, Object> map, Menu menu) throws Exception {
		map.put("success", false);
		if (menu.getId() < 0) {
			map.put("msg", "菜单不存在");
			return;
		}
		if (menu.getParentid() < 0) {
			map.put("msg", "父级菜单不能为空");
			return;
		}
		if (StringUtils.isEmpty(menu.getName())) {
			map.put("msg", "菜单名称不能为空");
			return;
		}
		Menu menuName = menuMapper.selectByName(menu.getName(),menu.getId());
		if (menuName != null) {
			map.put("msg", "菜单名称已存在");
			return;
		}
		if (menu.getParentid() > 0 && StringUtils.isEmpty(menu.getLink())) {
			map.put("msg", "链接地址不能为空");
			return;
		}
		if (menu.getEnable() < 0) {
			map.put("msg", "状态不能为空");
			return;
		}
		if (menu.getDisplayorder() < 0) {
			map.put("msg", "显示顺序不能为空");
			return;
		}
		map.put("success", true);
	}

	@Override
	public void update(Map<String, Object> map, Menu menu) throws Exception {
		map.put("success", false);
		int result = menuMapper.updateByPrimaryKey(menu);
		if (result <= 0) {
			map.put("msg", "编辑失败");
			throw new Exception();
		}
		map.put("msg", "编辑成功");
		map.put("success", true);
	}

	@Override
	public int selectCountByParam(Map<String, Object> map) {
		return menuMapper.selectCountByParam(map);
	}

	@Override
	public List<Menu> selectByParam(Map<String, Object> map) {
		return menuMapper.selectByParam(map);
	}

	@Override
	public void deleteValidation(Map<String, Object> map, Long id) throws Exception {
		map.put("success", false);
		if (id <= 0) {
			map.put("msg", "菜单id有误");
			return;
		}
		int childCount = menuMapper.selectChildCount(id);
		if (childCount > 0) {
			map.put("msg", "该菜单下有子菜单");
			return;
		}
		int roleMenuCount = roleMenuMapper.selectRoleMenuCountByMenuId(id);
		if (roleMenuCount > 0) {
			map.put("msg", "存在拥有该菜单的角色");
			return;
		}
		map.put("success", true);
	}

	@Override
	public void delete(Map<String, Object> map, Long id) throws Exception {
		map.put("success", false);
		int result = menuMapper.deleteByPrimaryKey(id);
		if (result <= 0) {
			map.put("msg", "删除失败");
			throw new Exception();
		}
		map.put("msg", "删除成功");
		map.put("success", true);
	}

	@Override
	public List<Menu> selectByParentId(Long parentId) {
		return menuMapper.selectByParentId(parentId);
	}

	@Override
	public List<Menu> selectTopByAdminId(Long adminId) {
		return menuMapper.selectTopByAdminId(adminId);
	}

	@Override
	public List<Menu> selectByParentIdAndAdminId(Long parentId, Long adminId) {
		return menuMapper.selectByParentIdAndAdminId(parentId, adminId);
	}

}
