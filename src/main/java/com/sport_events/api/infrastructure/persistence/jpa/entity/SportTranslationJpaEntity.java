package com.sport_events.api.infrastructure.persistence.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "sport_translations")
@Getter
@Setter
@NoArgsConstructor
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
}
