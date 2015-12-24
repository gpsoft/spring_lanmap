package jp.dip.gpsoft.lanmap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.dip.gpsoft.lanmap.form.UserForm;
import jp.dip.gpsoft.lanmap.model.User;
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

	@RequestMapping("/users/create")
	public String create(@ModelAttribute UserForm form) {
		return "users/create";
	}

	@RequestMapping("/users/edit/{id}")
	public String edit(@PathVariable("id") Long id, @ModelAttribute UserForm form) {
		User user = userService.findOneById(id);
		form.setupForEdit(user);
		return "users/edit";
	}

	@RequestMapping(value = "/users/save", method = RequestMethod.POST)
	public String save(@ModelAttribute UserForm form, Model model) {
		logger.info(form.toString());
		if (!form.withId()) { // new entry?
			User user = new User(form);
			userService.save(user, true);
		} else { // update
			User user = userService.findOneById(form.getId());
			user.patch(form);
			userService.save(user, form.withPassword());
		}

		return "redirect:/users";
	}

}
