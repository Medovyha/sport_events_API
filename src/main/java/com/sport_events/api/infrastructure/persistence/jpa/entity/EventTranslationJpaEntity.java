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
	name = "event_translations",
	uniqueConstraints = @UniqueConstraint(
		name = "event_translations_event_id_language_id_uindex",
		columnNames = {"event_id", "language_id"}
	)
)
@Getter
@Setter
@NoArgsConstructor
public class EventTranslationJpaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_translations_event_translation_id_seq")
	@SequenceGenerator(name = "event_translations_event_translation_id_seq", sequenceName = "event_translations_event_translation_id_seq", allocationSize = 1)
	@Column(name = "event_translation_id", nullable = false)
	private Integer eventTranslationId;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "event_id", nullable = false)
	private EventJpaEntity event;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "language_id", nullable = false)
	private LanguageJpaEntity language;

	@Column(name = "name", nullable = false, length = 255)
	private String name;

	@Column(name = "description", nullable = false, columnDefinition = "text")
	private String description;
}
