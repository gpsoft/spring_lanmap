package jp.dip.gpsoft.lanmap.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController extends BaseController {

	@ExceptionHandler(Exception.class)
	public String handleException(Model model, Exception ex) {
		logger.error("Default exception handler:", ex);
		model.addAttribute("ex", ex);
		return "error";
	}
}
