package com.sport_events.api.infrastructure.persistence.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "languages")
public class LanguageJpaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "languages_language_id_seq")
	@SequenceGenerator(name = "languages_language_id_seq", sequenceName = "languages_language_id_seq", allocationSize = 1)
	@Column(name = "language_id", nullable = false)
	private Integer languageId;

	@Column(name = "code", nullable = false, length = 5)
	private String code;

	@Column(name = "name", nullable = false, length = 50)
	private String name;

	public LanguageJpaEntity() {
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
