package jp.dip.gpsoft.lanmap.controller;

import java.time.LocalDate;
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
		n.setId(5L);
		n.setNodeTypeId(1L);
		n.setProductName("Thinkpad X348");
		n.setIpaddr("192.168.0.0");
		n.setAcquired(LocalDate.of(2000, 1, 1));
		list.add(n);
		n = new Node();
		n.setName("fuga");
		n.setId(6L);
		list.add(n);
		model.addAttribute("nodes", list);
		return "nodes/index";
	}

}
