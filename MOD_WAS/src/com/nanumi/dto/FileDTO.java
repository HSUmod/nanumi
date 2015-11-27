package com.nanumi.dto;

public class FileDTO {
	private String idx, articleNum, original_file_name, stored_file_name, file_size, crea_dtm;

	public FileDTO() {

	}

	public FileDTO(String idx, String articleNum, String original_file_name, String stored_file_name, String file_size, String crea_dtm) {
		this.idx = idx;
		this.articleNum = articleNum;
		this.original_file_name = original_file_name;
		this.stored_file_name = stored_file_name;
		this.file_size = file_size;
		this.crea_dtm = crea_dtm;
	}

	public String getIdx() {
		return idx;
	}

	public void setIdx(String idx) {
		this.idx = idx;
	}

	public String getArticleNum() {
		return articleNum;
	}

	public void setArticleNum(String articleNum) {
		this.articleNum = articleNum;
	}

	public String getOriginal_file_name() {
		return original_file_name;
	}

	public void setOriginal_file_name(String original_file_name) {
		this.original_file_name = original_file_name;
	}

	public String getStored_file_name() {
		return stored_file_name;
	}

	public void setStored_file_name(String stored_file_name) {
		this.stored_file_name = stored_file_name;
	}

	public String getFile_size() {
		return file_size;
	}

	public void setFile_size(String file_size) {
		this.file_size = file_size;
	}

	public String getCrea_dtm() {
		return crea_dtm;
	}

	public void setCrea_dtm(String crea_dtm) {
		this.crea_dtm = crea_dtm;
	}

}
