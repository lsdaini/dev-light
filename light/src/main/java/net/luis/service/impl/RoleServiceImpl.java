package net.luis.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import net.luis.entity.Role;
import net.luis.entity.RoleMenu;
import net.luis.mapper.AdminMapper;
import net.luis.mapper.RoleMapper;
import net.luis.mapper.RoleMenuMapper;
import net.luis.service.RoleService;
import net.luis.utils.StringUtils;

@Service
public class RoleServiceImpl implements RoleService {

	@Resource
	private RoleMapper roleMapper;

	@Resource
	private RoleMenuMapper roleMenuMapper;

	@Resource
	private AdminMapper adminMapper;

	@Override
	public int selectCountByParam(Map<String, Object> map) {
		return roleMapper.selectCountByParam(map);
	}

	@Override
	public List<Role> selectByParam(Map<String, Object> map) {
		return roleMapper.selectByParam(map);
	}

	@Override
	public void insertValidation(Map<String, Object> map, Role role) throws Exception {
		map.put("success", false);
		if (StringUtils.isEmpty(role.getName())) {
			map.put("msg", "角色名称不能为空");
			return;
		}
		Role roleName = roleMapper.selectByName(role.getName(), role.getId());
		if (roleName != null) {
			map.put("msg", "角色名称已存在");
			return;
		}
		if (StringUtils.isEmpty(role.getDescription())) {
			map.put("msg", "描述不能为空");
			return;
		}
		map.put("success", true);
	}

	@Override
	public void insert(Map<String, Object> map, Role role) throws Exception {
		map.put("success", false);
		int result = roleMapper.insert(role);
		if (result <= 0) {
			map.put("msg", "添加失败");
			throw new Exception();
		}
		Long roleId = role.getId();
		String menus = role.getMenus();
		List<Long> list = JSON.parseArray(menus, Long.class);
		for (Long menuId : list) {
			RoleMenu roleMenu = new RoleMenu();
			roleMenu.setRoleid(roleId);
			roleMenu.setMenuid((Long) menuId);
			int result2 = roleMenuMapper.insert(roleMenu);
			if (result2 <= 0) {
				map.put("msg", "添加菜单权限失败");
				throw new Exception();
			}
		}
		map.put("msg", "添加成功");
		map.put("success", true);
	}

	@Override
	public Role selectByPrimaryKey(Long id) {
		return roleMapper.selectByPrimaryKey(id);
	}

	@Override
	public void updateValidation(Map<String, Object> map, Role role) throws Exception {
		map.put("success", false);
		if (role.getId() < 0) {
			map.put("msg", "角色不存在");
			return;
		}
		if (StringUtils.isEmpty(role.getName())) {
			map.put("msg", "角色名称不能为空");
			return;
		}
		Role roleName = roleMapper.selectByName(role.getName(), role.getId());
		if (roleName != null) {
			map.put("msg", "角色名称已存在");
			return;
		}
		if (StringUtils.isEmpty(role.getDescription())) {
			map.put("msg", "描述不能为空");
			return;
		}
		map.put("success", true);
	}

	@Override
	public void update(Map<String, Object> map, Role role) throws Exception {
		map.put("success", false);
		int result = roleMapper.updateByPrimaryKey(role);
		if (result <= 0) {
			map.put("msg", "编辑失败");
			throw new Exception();
		}

		Long roleId = role.getId();
		roleMenuMapper.deleteByRoleId(roleId);

		String menus = role.getMenus();
		List<Long> list = JSON.parseArray(menus, Long.class);
		for (Long menuId : list) {
			RoleMenu roleMenu = new RoleMenu();
			roleMenu.setRoleid(roleId);
			roleMenu.setMenuid((Long) menuId);
			int result3 = roleMenuMapper.insert(roleMenu);
			if (result3 <= 0) {
				map.put("msg", "添加菜单权限失败");
				throw new Exception();
			}
		}
		map.put("msg", "编辑成功");
		map.put("success", true);
	}

	@Override
	public void deleteValidation(Map<String, Object> map, Long id) throws Exception {
		map.put("success", false);
		if (id <= 0) {
			map.put("msg", "角色id有误");
			return;
		}
		int result = adminMapper.selectCountByRoleId(id);
		if (result > 0) {
			map.put("msg", "存在拥有该角色的管理员");
			return;
		}
		map.put("success", true);
	}

	@Override
	public void delete(Map<String, Object> map, Long id) throws Exception {
		map.put("success", false);
		int result = roleMapper.deleteByPrimaryKey(id);
		if (result <= 0) {
			map.put("msg", "删除失败");
			throw new Exception();
		}
		roleMenuMapper.deleteByRoleId(id);
		map.put("msg", "删除成功");
		map.put("success", true);
	}

	@Override
	public List<Role> selectAll() {
		return roleMapper.selectAll();
	}

}
