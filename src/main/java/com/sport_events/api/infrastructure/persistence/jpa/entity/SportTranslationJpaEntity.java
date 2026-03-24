package com.sport_events.api.infrastructure.persistence.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "sport_translations")
public class SportTranslationJpaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sport_translations_sport_translation_id_seq")
	@SequenceGenerator(name = "sport_translations_sport_translation_id_seq", sequenceName = "sport_translations_sport_translation_id_seq", allocationSize = 1)
	@Column(name = "sport_translation_id", nullable = false)
	private Integer sportTranslationId;

	@Column(name = "sport_id", nullable = false)
	private Integer sportId;

	@Column(name = "language_id", nullable = false)
	private Integer languageId;

	@Column(name = "name", nullable = false, length = 255)
	private String name;

	public SportTranslationJpaEntity() {
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
