package jp.dip.gpsoft.lanmap.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TopController {
	@RequestMapping("/")
	public String top() {
		return "index";
	}
}
