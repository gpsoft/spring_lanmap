package jp.dip.gpsoft.lanmap.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.dip.gpsoft.lanmap.service.NodeService;

@Controller
public class NodesController extends BaseController {
	@Autowired
	private NodeService nodeService;

	@RequestMapping("/nodes")
	public String index(Model model) {

		model.addAttribute("nodes", nodeService.findAllAlive());
		return "nodes/index";
	}
}
