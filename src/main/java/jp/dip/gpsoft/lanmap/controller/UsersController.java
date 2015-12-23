package jp.dip.gpsoft.lanmap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.dip.gpsoft.lanmap.service.UserService;

@Controller
public class UsersController extends BaseController {
	@Autowired
	private UserService userService;

	@RequestMapping("/users")
	public String index(Model model) {

		model.addAttribute("users", userService.findAll());
		return "users/index";
	}

}
