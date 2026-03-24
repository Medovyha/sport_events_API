package com.sport_events.api.domain.model;

public class Language {

	private Integer languageId;
	private String code;
	private String name;

	public Language() {
	}

	public Language(Integer languageId, String code, String name) {
		this.languageId = languageId;
		this.code = code;
		this.name = name;
	}

	public Integer getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Integer languageId) {
		this.languageId = languageId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
