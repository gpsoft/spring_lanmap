package jp.dip.gpsoft.lanmap.form;

import org.springframework.web.multipart.MultipartFile;

public class UploadFileForm {
	private MultipartFile[] doc;
	private String name;

	public MultipartFile[] getDoc() {
		return doc;
	}

	public void setDoc(MultipartFile[] doc) {
		this.doc = doc;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
