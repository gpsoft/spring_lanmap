package jp.dip.gpsoft.lanmap.form;

import org.springframework.web.multipart.MultipartFile;

public class UploadFileForm {
	private MultipartFile doc;

	public MultipartFile getDoc() {
		return doc;
	}

	public void setDoc(MultipartFile doc) {
		this.doc = doc;
	}
}
