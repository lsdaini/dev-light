package net.luis.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import net.luis.entity.User;
import net.luis.service.UserService;


@Controller
@RequestMapping("/user")
public class UserController {

	@Resource
	private UserService userService;

	@RequestMapping("/show/{id}")
	public ModelAndView show(@PathVariable long id) {
		ModelAndView modelAndView = new ModelAndView("/user/show");
		User user = userService.selectByPrimaryKey(id);
		modelAndView.addObject("user", user);
		return modelAndView;
	}

}
