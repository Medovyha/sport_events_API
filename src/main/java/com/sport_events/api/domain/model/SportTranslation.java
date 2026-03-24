package com.sport_events.api.domain.model;

public class SportTranslation {

	private Integer sportTranslationId;
	private Integer sportId;
	private Integer languageId;
	private String name;

	public SportTranslation() {
	}

	public SportTranslation(Integer sportTranslationId, Integer sportId, Integer languageId, String name) {
		this.sportTranslationId = sportTranslationId;
		this.sportId = sportId;
		this.languageId = languageId;
		this.name = name;
	}

	public Integer getSportTranslationId() {
		return sportTranslationId;
	}

	public void setSportTranslationId(Integer sportTranslationId) {
		this.sportTranslationId = sportTranslationId;
	}

	public Integer getSportId() {
		return sportId;
	}

	public void setSportId(Integer sportId) {
		this.sportId = sportId;
	}

	public Integer getLanguageId() {
		return languageId;
	}

	public void setLanguageId(Integer languageId) {
		this.languageId = languageId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
