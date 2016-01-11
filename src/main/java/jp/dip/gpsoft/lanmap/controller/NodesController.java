package jp.dip.gpsoft.lanmap.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import jp.dip.gpsoft.lanmap.form.UploadFileForm;
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

	@RequestMapping(value="/nodes/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public Resource download(HttpServletRequest request, HttpServletResponse response) {
		response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=download.xml");
		return new FileSystemResource("pom.xml");
	}

	@RequestMapping(value="/nodes/download/hoge", produces = "application/vnd.ms-excel")
	@ResponseBody
	public Resource hoge(HttpServletResponse response) {
		response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=hoge.xls");
		return new FileSystemResource("hoge.xls");
	}

	@RequestMapping(value = "/nodes/upload", method = RequestMethod.POST)
	public String upload(UploadFileForm form) {
		MultipartFile doc = form.getDoc();
		if (doc.getOriginalFilename().length() > 0) {
			logger.info(doc.getOriginalFilename() + ", " + doc.getSize());
			try {
				String xmlStr = new String(doc.getBytes(),
						StandardCharsets.UTF_8);
				logger.info(xmlStr);
			} catch (IOException e) {
				logger.error("Nooooo!", e);
			}
		}
		return "redirect:/nodes";
	}
}
