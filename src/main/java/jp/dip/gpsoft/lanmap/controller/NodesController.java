package jp.dip.gpsoft.lanmap.controller;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.stream.IntStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
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
import jp.dip.gpsoft.lanmap.utils.Utils;

@Controller
public class NodesController extends BaseController {
	@Autowired
	private NodeService nodeService;

	@RequestMapping("/nodes")
	public String index(Model model) {

		model.addAttribute("nodes", nodeService.findAllAlive());
		return "nodes/index";
	}

	@RequestMapping(value = "/nodes/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public Resource download(HttpServletRequest request,
			HttpServletResponse response) {
		response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=download.xml");
		return new FileSystemResource("pom.xml");
	}

	@RequestMapping(value = "/nodes/download/excel", produces = "application/vnd.ms-excel")
	@ResponseBody
	public Resource export(HttpServletResponse response) {

		ByteArrayOutputStream baos = exportHoge();
		if (baos == null) {
			throw new RuntimeException();
		}

		response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=nodes.xls");
		return new ByteArrayResource(baos.toByteArray());
	}

	private ByteArrayOutputStream exportHoge() {
		try (POIFSFileSystem fs = new POIFSFileSystem(
				new FileInputStream("hoge.xls"));
				HSSFWorkbook wb = new HSSFWorkbook(fs)) {
			HSSFSheet sh = wb.getSheetAt(0);
			IntStream.range(0, 3).forEach(roff -> {
				String val = sh.getRow(roff + 1).getCell(1)
						.getStringCellValue();
				logger.info(val);
				sh.getRow(roff + 1).createCell(2)
						.setCellValue(val + "(copied)");
			});
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			wb.write(baos);
			return baos;
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
		return null;
	}

	@RequestMapping(value = "/nodes/upload", method = RequestMethod.POST)
	public String upload(UploadFileForm form) {
		MultipartFile[] docs = form.getDoc();
		if (docs != null && !Utils.blank(docs[0].getOriginalFilename())) {
			logger.info(
					docs[0].getOriginalFilename() + ", " + docs[0].getSize());
			try {
				String xmlStr = new String(docs[0].getBytes(),
						StandardCharsets.UTF_8);
				// logger.info(xmlStr);
			} catch (IOException e) {
				logger.error("Nooooo!", e);
			}
		}
		return "redirect:/nodes";
	}
}
