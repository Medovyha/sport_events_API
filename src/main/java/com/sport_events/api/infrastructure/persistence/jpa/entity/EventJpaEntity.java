package com.sport_events.api.infrastructure.persistence.jpa.entity;

import java.time.OffsetDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "events")
@Getter
@Setter
@NoArgsConstructor
public class EventJpaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "events_event_id_seq")
	@SequenceGenerator(name = "events_event_id_seq", sequenceName = "events_event_id_seq", allocationSize = 1)
	@Column(name = "event_id", nullable = false)
	private Long eventId;

	@Column(name = "starts_at", nullable = false)
	private OffsetDateTime startsAt;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "venue_id", nullable = false)
	private VenueJpaEntity venue;

	@OneToMany(mappedBy = "event")
	private Set<EventTeamJpaEntity> eventTeams = new LinkedHashSet<>();

	@OneToMany(mappedBy = "event")
	private Set<EventTranslationJpaEntity> translations = new LinkedHashSet<>();
}
