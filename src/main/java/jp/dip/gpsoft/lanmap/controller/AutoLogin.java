package jp.dip.gpsoft.lanmap.controller;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AutoLogin {
	@RequestMapping(value = "/autologin", method = RequestMethod.GET)
	public String login() {
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken("piyo", "piyo"));
		return "redirect:/nodes";
	}
}