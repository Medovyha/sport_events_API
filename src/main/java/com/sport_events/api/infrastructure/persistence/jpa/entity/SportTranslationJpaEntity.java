package com.sport_events.api.infrastructure.persistence.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
	name = "sport_translations",
	uniqueConstraints = @UniqueConstraint(
		name = "sport_translations_sport_id_language_id_uindex",
		columnNames = {"sport_id", "language_id"}
	)
)
@Getter
@Setter
@NoArgsConstructor
public class SportTranslationJpaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sport_translations_sport_translation_id_seq")
	@SequenceGenerator(name = "sport_translations_sport_translation_id_seq", sequenceName = "sport_translations_sport_translation_id_seq", allocationSize = 1)
	@Column(name = "sport_translation_id", nullable = false)
	private Integer sportTranslationId;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "sport_id", nullable = false)
	private SportJpaEntity sport;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "language_id", nullable = false)
	private LanguageJpaEntity language;

	@Column(name = "name", nullable = false, length = 255)
	private String name;
}
