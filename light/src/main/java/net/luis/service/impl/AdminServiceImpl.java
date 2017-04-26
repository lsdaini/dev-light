package net.luis.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.luis.entity.Admin;
import net.luis.mapper.AdminMapper;
import net.luis.service.AdminService;
import net.luis.utils.ContextUtils;
import net.luis.utils.MD5;
import net.luis.utils.StringUtils;

@Service
public class AdminServiceImpl implements AdminService {

	@Resource
	private AdminMapper adminMapper;

	@Override
	public int selectCountByParam(Map<String, Object> map) {
		return adminMapper.selectCountByParam(map);
	}

	@Override
	public List<Admin> selectByParam(Map<String, Object> map) {
		return adminMapper.selectByParam(map);
	}

	@Override
	public void insertValidation(Map<String, Object> map, Admin admin) throws Exception {
		map.put("success", false);
		if (StringUtils.isEmpty(admin.getUsername())) {
			map.put("msg", "用户名不能为空");
			return;
		}
		Admin adminName = adminMapper.selectByName(admin.getUsername(), admin.getId());
		if (adminName != null) {
			map.put("msg", "用户名已存在");
			return;
		}
		if (StringUtils.isEmpty(admin.getPassword())) {
			map.put("msg", "密码不能为空");
			return;
		}
		if (StringUtils.isEmpty(admin.getPassword2())) {
			map.put("msg", "确认密码不能为空");
			return;
		}
		if (!admin.getPassword2().equals(admin.getPassword())) {
			map.put("msg", "两次密码输入不一致");
			return;
		}
		if (admin.getEnable() < 0) {
			map.put("msg", "状态不能为空");
			return;
		}
		if (admin.getRoleid() < 0) {
			map.put("msg", "角色不能为空");
			return;
		}
		map.put("success", true);
	}

	@Override
	public void insert(Map<String, Object> map, Admin admin) throws Exception {
		map.put("success", false);
		String pwd = MD5.ecodeByMD5(admin.getPassword());
		admin.setPassword(pwd);
		int result = adminMapper.insert(admin);
		if (result <= 0) {
			map.put("msg", "添加失败");
			throw new Exception();
		}
		map.put("msg", "添加成功");
		map.put("success", true);
	}

	@Override
	public Admin selectByPrimaryKey(Long id) {
		return adminMapper.selectByPrimaryKey(id);
	}

	@Override
	public void updateValidation(Map<String, Object> map, Admin admin) throws Exception {
		map.put("success", false);
		if (admin.getId() < 0) {
			map.put("msg", "管理员不存在");
			return;
		}
		if (admin.getEnable() < 0) {
			map.put("msg", "状态不能为空");
			return;
		}
		if (admin.getRoleid() < 0) {
			map.put("msg", "角色不能为空");
			return;
		}
		map.put("success", true);
	}

	@Override
	public void update(Map<String, Object> map, Admin admin) throws Exception {
		map.put("success", false);
		int result = adminMapper.updateByPrimaryKey(admin);
		if (result <= 0) {
			map.put("msg", "编辑失败");
			throw new Exception();
		}
		map.put("msg", "编辑成功");
		map.put("success", true);
	}

	@Override
	public void deleteValidation(Map<String, Object> map, Long id) throws Exception {
		map.put("success", false);
		if (id <= 0) {
			map.put("msg", "用户id有误");
			return;
		}
		map.put("success", true);
	}

	@Override
	public void delete(Map<String, Object> map, Long id) throws Exception {
		map.put("success", false);
		int result = adminMapper.deleteByPrimaryKey(id);
		if (result <= 0) {
			map.put("msg", "删除失败");
			throw new Exception();
		}
		map.put("msg", "删除成功");
		map.put("success", true);
	}

	@Override
	public void loginValidation(Map<String, Object> map, Admin admin) throws Exception {
		map.put("success", false);
		if (StringUtils.isEmpty(admin.getUsername())) {
			map.put("msg", "用户不能为空");
			return;
		}
		if (StringUtils.isEmpty(admin.getPassword())) {
			map.put("msg", "密码不能为空");
			return;
		}
		String imageCode = (String) ContextUtils.getSession().getAttribute("imageCode");
		if (StringUtils.isEmpty(admin.getImageCode())) {
			map.put("msg", "验证码不能为空");
			return;
		}
		if (!admin.getImageCode().equals(imageCode)) {
			map.put("msg", "验证码不正确");
			return;
		}
		Admin enable = adminMapper.selectEnable(admin);
		if (enable == null) {
			map.put("msg", "您的账户不存在或已被禁用请联系网站管理员");
			return;
		}
		map.put("success", true);
	}

	@Override
	public void login(Map<String, Object> map, Admin admin) throws Exception {
		map.put("success", false);
		String pwd = MD5.ecodeByMD5(admin.getPassword());
		admin.setPassword(pwd);
		Admin resultAdmin = adminMapper.login(admin);
		if (resultAdmin == null) {
			map.put("msg", "账户或密码错误");
			throw new Exception();
		}
		ContextUtils.getSession().setAttribute("admin", resultAdmin);
		map.put("msg", "登录成功");
		map.put("success", true);
	}

}
