package jp.dip.gpsoft.lanmap.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.dip.gpsoft.lanmap.model.Node;

@Controller
public class NodesController extends BaseController {

	@RequestMapping("/nodes")
	public String index(Model model) {

		List<Node> list = new ArrayList<Node>();
		Node n = new Node();
		n.setName("hoge");
		list.add(n);
		n = new Node();
		n.setName("fuga");
		list.add(n);
		model.addAttribute("nodes", list);
		return "nodes/index";
	}

}
