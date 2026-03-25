class SportsEventsDashboard {
    constructor() {
        this.api = new SportsEventsApiClient('');
        this.state = {
            sports: [],
            venues: [],
            players: [],
            teams: [],
            events: [],
        };

        this.bindStaticListeners();
    }

    async init() {
        await this.refreshAllData();
        await this.loadEventsTab('all-events');
    }

    bindStaticListeners() {
        document.querySelectorAll('.nav-tab').forEach((tab) => {
            tab.addEventListener('click', (event) => this.onTabClick(event));
        });

        document.getElementById('language-select').addEventListener('change', async (event) => {
            this.api.setLanguage(event.target.value);
            await this.refreshAllData();
            await this.loadActiveTab();
            this.showSuccess(`Language switched to ${event.target.value}`);
        });

        document.getElementById('refresh-all').addEventListener('click', async () => {
            await this.refreshAllData();
            await this.loadActiveTab();
            this.showSuccess('Data refreshed');
        });

        document.querySelector('.modal-close').addEventListener('click', () => this.closeModal());
        document.getElementById('event-modal').addEventListener('click', (event) => {
            if (event.target.id === 'event-modal') {
                this.closeModal();
            }
        });

        this.bindFormListeners();
        this.bindActionButtons();

        document.getElementById('sport-translation-sport-select').addEventListener('change', async () => {
            await this.prefillSportTranslation();
        });

        document.getElementById('sport-translation-lang-select').addEventListener('change', async () => {
            await this.prefillSportTranslation();
        });

        document.getElementById('event-translation-event-select').addEventListener('change', async () => {
            await this.prefillEventTranslation();
        });

        document.getElementById('event-translation-lang-select').addEventListener('change', async () => {
            await this.prefillEventTranslation();
        });
    }

    bindFormListeners() {
        document.getElementById('create-sport-form').addEventListener('submit', async (event) => {
            event.preventDefault();
            const name = document.getElementById('sport-name').value.trim();
            await this.runAction(async () => {
                await this.api.createSport({ name, translations: [] });
                event.target.reset();
                await this.refreshAllData();
                this.showSuccess('Sport created');
            });
        });

        document.getElementById('sport-translation-form').addEventListener('submit', async (event) => {
            event.preventDefault();
            const sportId = Number(document.getElementById('sport-translation-sport-select').value);
            const languageCode = document.getElementById('sport-translation-lang-select').value;
            const name = document.getElementById('sport-translation-name').value.trim();

            await this.runAction(async () => {
                await this.api.upsertSportTranslation(sportId, languageCode, name);
                event.target.reset();
                await this.refreshAllData();
                this.showSuccess(`Sport translation saved (${languageCode})`);
            });
        });

        document.getElementById('create-venue-form').addEventListener('submit', async (event) => {
            event.preventDefault();
            const payload = {
                name: document.getElementById('venue-name').value.trim(),
                address: document.getElementById('venue-address').value.trim(),
            };
            await this.runAction(async () => {
                await this.api.createVenue(payload);
                event.target.reset();
                await this.refreshAllData();
                this.showSuccess('Venue created');
            });
        });

        document.getElementById('create-player-form').addEventListener('submit', async (event) => {
            event.preventDefault();
            const payload = {
                firstName: document.getElementById('player-first-name').value.trim(),
                lastName: document.getElementById('player-last-name').value.trim(),
                dateOfBirth: document.getElementById('player-dob').value,
            };
            await this.runAction(async () => {
                await this.api.createPlayer(payload);
                event.target.reset();
                await this.refreshAllData();
                this.showSuccess('Player created');
            });
        });

        document.getElementById('create-team-form').addEventListener('submit', async (event) => {
            event.preventDefault();
            const payload = {
                name: document.getElementById('team-name').value.trim(),
                sportId: Number(document.getElementById('team-sport-select').value),
            };
            await this.runAction(async () => {
                await this.api.createTeam(payload);
                event.target.reset();
                await this.refreshAllData();
                this.showSuccess('Team created');
            });
        });

        document.getElementById('create-event-form').addEventListener('submit', async (event) => {
            event.preventDefault();
            const startsAt = this.formatOffsetDateTime(document.getElementById('event-start').value);
            const payload = {
                startsAt,
                venueId: Number(document.getElementById('event-venue-select').value),
                name: document.getElementById('event-name').value.trim(),
                description: document.getElementById('event-description').value.trim(),
                translations: [],
            };
            await this.runAction(async () => {
                await this.api.createEvent(payload);
                event.target.reset();
                await this.refreshAllData();
                await this.loadActiveTab();
                this.showSuccess('Event created');
            });
        });

        document.getElementById('event-translation-form').addEventListener('submit', async (event) => {
            event.preventDefault();
            const eventId = Number(document.getElementById('event-translation-event-select').value);
            const languageCode = document.getElementById('event-translation-lang-select').value;
            const translatedName = document.getElementById('event-translation-name').value.trim();
            const translatedDescription = document.getElementById('event-translation-description').value.trim();

            await this.runAction(async () => {
                const baseEvent = await this.api.getEventByLanguage(eventId, 'en');
                const payload = {
                    startsAt: baseEvent.startsAt,
                    venueId: baseEvent.venue?.venueId,
                    name: baseEvent.name,
                    description: baseEvent.description,
                    translations: [
                        {
                            languageCode,
                            name: translatedName,
                            description: translatedDescription,
                        },
                    ],
                };

                await this.api.updateEvent(eventId, payload);
                event.target.reset();
                await this.refreshAllData();
                await this.loadActiveTab();
                this.showSuccess(`Event translation saved (${languageCode})`);
            });
        });
    }

    bindActionButtons() {
        document.getElementById('assign-player-btn').addEventListener('click', async () => {
            const teamId = Number(document.getElementById('team-assign-select').value);
            const playerId = Number(document.getElementById('player-assign-select').value);
            await this.runAction(async () => {
                await this.api.assignPlayerToTeam(teamId, playerId);
                this.showSuccess('Player assigned to team');
            });
        });

        document.getElementById('remove-player-team-btn').addEventListener('click', async () => {
            const teamId = Number(document.getElementById('team-assign-select').value);
            const playerId = Number(document.getElementById('player-assign-select').value);
            await this.runAction(async () => {
                await this.api.removePlayerFromTeam(teamId, playerId);
                this.showSuccess('Player removed from team');
            });
        });

        document.getElementById('add-team-to-event-btn').addEventListener('click', async () => {
            const eventId = Number(document.getElementById('event-select').value);
            const teamId = Number(document.getElementById('event-team-select').value);
            await this.runAction(async () => {
                await this.api.addTeamToEvent(eventId, teamId);
                await this.refreshAllData();
                await this.loadActiveTab();
                this.showSuccess('Team added to event');
            });
        });

        document.getElementById('add-players-to-event-btn').addEventListener('click', async () => {
            const eventId = Number(document.getElementById('event-select').value);
            const teamId = Number(document.getElementById('event-team-select').value);
            const playerIds = this.getMultiSelectValues('event-player-select');
            await this.runAction(async () => {
                await this.api.addPlayersToEventTeam(eventId, teamId, playerIds);
                await this.loadActiveTab();
                this.showSuccess('Players added to event team');
            });
        });

        document.getElementById('replace-event-players-btn').addEventListener('click', async () => {
            const eventId = Number(document.getElementById('event-select').value);
            const teamId = Number(document.getElementById('event-team-select').value);
            const playerIds = this.getMultiSelectValues('event-player-select');
            await this.runAction(async () => {
                await this.api.updateEventTeamPlayers(eventId, teamId, playerIds);
                await this.loadActiveTab();
                this.showSuccess('Event team players replaced');
            });
        });

        document.getElementById('remove-player-event-btn').addEventListener('click', async () => {
            const eventId = Number(document.getElementById('event-select').value);
            const teamId = Number(document.getElementById('event-team-select').value);
            const playerId = Number(document.getElementById('event-player-remove-select').value);
            await this.runAction(async () => {
                await this.api.removePlayerFromEventTeam(eventId, teamId, playerId);
                await this.loadActiveTab();
                this.showSuccess('Player removed from event team');
            });
        });

        document.getElementById('remove-team-event-btn').addEventListener('click', async () => {
            const eventId = Number(document.getElementById('event-select').value);
            const teamId = Number(document.getElementById('event-team-select').value);
            await this.runAction(async () => {
                await this.api.removeTeamFromEvent(eventId, teamId);
                await this.refreshAllData();
                await this.loadActiveTab();
                this.showSuccess('Team removed from event');
            });
        });

        document.getElementById('delete-event-btn').addEventListener('click', async () => {
            const eventId = Number(document.getElementById('delete-event-select').value);
            if (!window.confirm('Delete selected event?')) {
                return;
            }
            await this.runAction(async () => {
                await this.api.deleteEvent(eventId);
                await this.refreshAllData();
                await this.loadActiveTab();
                this.showSuccess('Event deleted');
            });
        });

        document.getElementById('delete-team-btn').addEventListener('click', async () => {
            const teamId = Number(document.getElementById('delete-team-select').value);
            if (!window.confirm('Delete selected team?')) {
                return;
            }
            await this.runAction(async () => {
                await this.api.deleteTeam(teamId);
                await this.refreshAllData();
                await this.loadActiveTab();
                this.showSuccess('Team deleted');
            });
        });

        document.getElementById('delete-player-btn').addEventListener('click', async () => {
            const playerId = Number(document.getElementById('delete-player-select').value);
            if (!window.confirm('Delete selected player?')) {
                return;
            }
            await this.runAction(async () => {
                await this.api.deletePlayer(playerId);
                await this.refreshAllData();
                await this.loadActiveTab();
                this.showSuccess('Player deleted');
            });
        });

        document.getElementById('delete-venue-btn').addEventListener('click', async () => {
            const venueId = Number(document.getElementById('delete-venue-select').value);
            if (!window.confirm('Delete selected venue?')) {
                return;
            }
            await this.runAction(async () => {
                await this.api.deleteVenue(venueId);
                await this.refreshAllData();
                await this.loadActiveTab();
                this.showSuccess('Venue deleted');
            });
        });

        document.getElementById('event-team-select').addEventListener('change', () => {
            this.populateTeamPlayerDropdowns();
        });
    }

    async runAction(action) {
        this.showLoading();
        try {
            await action();
            this.updateApiStatus(true);
        } catch (error) {
            this.updateApiStatus(false);
            this.showError(error.message || 'Action failed');
        } finally {
            this.hideLoading();
        }
    }

    async refreshAllData() {
        this.showLoading();
        try {
            const [sports, venues, players, teams, events] = await Promise.all([
                this.api.getSports(),
                this.api.getVenues(),
                this.api.getPlayers(),
                this.api.getTeams(),
                this.api.getEvents(),
            ]);

            this.state.sports = sports || [];
            this.state.venues = venues || [];
            this.state.players = players || [];
            this.state.teams = teams || [];
            this.state.events = events || [];

            this.updateDropdowns();
            this.updateApiStatus(true);
        } catch (error) {
            this.updateApiStatus(false);
            this.showError(`Refresh failed: ${error.message}`);
        } finally {
            this.hideLoading();
        }
    }

    updateDropdowns() {
        this.fillSelect('team-sport-select', this.state.sports, {
            placeholder: 'Select sport',
            valueKey: 'sportId',
            labelBuilder: (item) => item.name,
        });

        this.fillSelect('sport-translation-sport-select', this.state.sports, {
            placeholder: 'Select sport',
            valueKey: 'sportId',
            labelBuilder: (item) => item.name,
        });

        this.fillSelect('event-venue-select', this.state.venues, {
            placeholder: 'Select venue',
            valueKey: 'venueId',
            labelBuilder: (item) => `${item.name} • ${item.address}`,
        });

        this.fillSelect('team-assign-select', this.state.teams, {
            placeholder: 'Select team',
            valueKey: 'teamId',
            labelBuilder: (item) => item.name,
        });

        this.fillSelect('player-assign-select', this.state.players, {
            placeholder: 'Select player',
            valueKey: 'playerId',
            labelBuilder: (item) => `${item.firstName} ${item.lastName}`,
        });

        const eventLabel = (item) => `${item.name} • ${this.toDateLabel(item.startsAt)}`;
        this.fillSelect('event-select', this.state.events, {
            placeholder: 'Select event',
            valueKey: 'eventId',
            labelBuilder: eventLabel,
        });

        this.fillSelect('event-translation-event-select', this.state.events, {
            placeholder: 'Select event',
            valueKey: 'eventId',
            labelBuilder: eventLabel,
        });

        this.fillSelect('delete-event-select', this.state.events, {
            placeholder: 'Select event',
            valueKey: 'eventId',
            labelBuilder: eventLabel,
        });

        this.fillSelect('event-team-select', this.state.teams, {
            placeholder: 'Select team',
            valueKey: 'teamId',
            labelBuilder: (item) => item.name,
        });

        this.fillSelect('delete-team-select', this.state.teams, {
            placeholder: 'Select team',
            valueKey: 'teamId',
            labelBuilder: (item) => item.name,
        });

        this.fillSelect('delete-player-select', this.state.players, {
            placeholder: 'Select player',
            valueKey: 'playerId',
            labelBuilder: (item) => `${item.firstName} ${item.lastName}`,
        });

        this.fillSelect('delete-venue-select', this.state.venues, {
            placeholder: 'Select venue',
            valueKey: 'venueId',
            labelBuilder: (item) => item.name,
        });

        this.populateTeamPlayerDropdowns();
        this.prefillSportTranslation();
        this.prefillEventTranslation();
    }

    async prefillSportTranslation() {
        const sportId = Number(document.getElementById('sport-translation-sport-select').value);
        const languageCode = document.getElementById('sport-translation-lang-select').value;
        const target = document.getElementById('sport-translation-name');

        if (!sportId || !languageCode || Number.isNaN(sportId)) {
            target.value = '';
            return;
        }

        try {
            const sport = await this.api.getSportByLanguage(sportId, languageCode);
            target.value = sport?.name || '';
        } catch (_) {
            target.value = '';
        }
    }

    async prefillEventTranslation() {
        const eventId = Number(document.getElementById('event-translation-event-select').value);
        const languageCode = document.getElementById('event-translation-lang-select').value;
        const nameTarget = document.getElementById('event-translation-name');
        const descriptionTarget = document.getElementById('event-translation-description');

        if (!eventId || !languageCode || Number.isNaN(eventId)) {
            nameTarget.value = '';
            descriptionTarget.value = '';
            return;
        }

        try {
            const event = await this.api.getEventByLanguage(eventId, languageCode);
            nameTarget.value = event?.name || '';
            descriptionTarget.value = event?.description || '';
        } catch (_) {
            nameTarget.value = '';
            descriptionTarget.value = '';
        }
    }

    populateTeamPlayerDropdowns() {
        const teamId = Number(document.getElementById('event-team-select').value);
        const relatedPlayers = Number.isNaN(teamId)
            ? []
            : this.state.players;

        this.fillSelect('event-player-remove-select', relatedPlayers, {
            placeholder: 'Select player',
            valueKey: 'playerId',
            labelBuilder: (item) => `${item.firstName} ${item.lastName}`,
        });

        this.fillSelect('event-player-select', relatedPlayers, {
            valueKey: 'playerId',
            labelBuilder: (item) => `${item.firstName} ${item.lastName}`,
            allowPlaceholder: false,
        });
    }

    fillSelect(selectId, items, config) {
        const select = document.getElementById(selectId);
        if (!select) {
            return;
        }

        const {
            placeholder = 'Select option',
            valueKey,
            labelBuilder,
            allowPlaceholder = true,
        } = config;

        const currentValue = select.multiple ? null : select.value;
        select.innerHTML = '';

        if (allowPlaceholder) {
            const placeholderOption = document.createElement('option');
            placeholderOption.value = '';
            placeholderOption.textContent = placeholder;
            select.appendChild(placeholderOption);
        }

        items.forEach((item) => {
            const option = document.createElement('option');
            option.value = String(item[valueKey]);
            option.textContent = labelBuilder(item);
            select.appendChild(option);
        });

        if (!select.multiple && currentValue && Array.from(select.options).some((option) => option.value === currentValue)) {
            select.value = currentValue;
        } else if (!select.multiple && allowPlaceholder) {
            select.value = '';
        }
    }

    async onTabClick(event) {
        const tabName = event.currentTarget.dataset.tab;
        document.querySelectorAll('.nav-tab').forEach((tab) => tab.classList.remove('active'));
        event.currentTarget.classList.add('active');

        document.querySelectorAll('.tab-content').forEach((content) => content.classList.remove('active'));
        document.getElementById(tabName).classList.add('active');

        await this.loadEventsTab(tabName);
    }

    async loadActiveTab() {
        const activeTab = document.querySelector('.nav-tab.active')?.dataset.tab || 'all-events';
        await this.loadEventsTab(activeTab);
    }

    async loadEventsTab(tabName) {
        const map = {
            'all-events': { endpoint: () => this.api.getEvents(), container: 'all-events-list' },
            'future-events': { endpoint: () => this.api.getFutureEvents(), container: 'future-events-list' },
            'past-events': { endpoint: () => this.api.getPastEvents(), container: 'past-events-list' },
        };

        const config = map[tabName];
        if (!config) {
            return;
        }

        const container = document.getElementById(config.container);
        this.showLoading();

        try {
            const events = await config.endpoint();
            this.renderEvents(container, events || []);
            this.updateApiStatus(true);
        } catch (error) {
            this.updateApiStatus(false);
            container.innerHTML = '<p class="error">Could not load events.</p>';
            this.showError(error.message || 'Could not load events');
        } finally {
            this.hideLoading();
        }
    }

    renderEvents(container, events) {
        if (!events.length) {
            container.innerHTML = '<p class="no-events">No events found.</p>';
            return;
        }

        container.innerHTML = events.map((event) => this.createEventCard(event)).join('');

        container.querySelectorAll('.event-card').forEach((card) => {
            card.addEventListener('click', async () => {
                const eventId = Number(card.dataset.eventId);
                await this.openEventDetails(eventId);
            });
        });
    }

    createEventCard(event) {
        const startsAt = this.toDateLabel(event.startsAt);
        const venue = event.venue?.name || 'TBA';
        const desc = event.description ? `<div class="event-description">${this.escapeHtml(event.description)}</div>` : '';

        return `
            <div class="event-card" data-event-id="${event.eventId}">
                <div class="event-header">
                    <div class="event-date">${startsAt}</div>
                    <div class="event-name">${this.escapeHtml(event.name || 'Unnamed Event')}</div>
                </div>
                <div class="event-body">
                    <div class="event-venue">
                        <div class="venue-label">📍 Venue</div>
                        <div class="venue-name">${this.escapeHtml(venue)}</div>
                    </div>
                    ${desc}
                    <div class="event-footer">
                        <button class="btn-view-details" type="button">View Details →</button>
                    </div>
                </div>
            </div>
        `;
    }

    async openEventDetails(eventId) {
        this.showLoading();
        try {
            const event = await this.api.getEvent(eventId);
            const teams = event.teams || [];
            const teamHtml = teams.length
                ? teams.map((team) => this.teamDetailsHtml(team)).join('')
                : '<div class="no-teams">No teams added yet.</div>';

            document.getElementById('modal-body').innerHTML = `
                <div class="modal-header">
                    <div class="modal-title">${this.escapeHtml(event.name || 'Event')}</div>
                    <div class="modal-date">${this.toDateLabel(event.startsAt)}</div>
                </div>
                <div class="modal-section">
                    <div class="modal-section-title">📝 Description</div>
                    <p>${this.escapeHtml(event.description || 'No description')}</p>
                </div>
                <div class="modal-section">
                    <div class="modal-section-title">📍 Venue</div>
                    <div class="venue-details">
                        <div class="detail-item"><span class="detail-label">Name:</span><span class="detail-value">${this.escapeHtml(event.venue?.name || 'TBA')}</span></div>
                        <div class="detail-item"><span class="detail-label">Address:</span><span class="detail-value">${this.escapeHtml(event.venue?.address || 'N/A')}</span></div>
                    </div>
                </div>
                <div class="modal-section">
                    <div class="modal-section-title">🏆 Teams & Players</div>
                    <div class="teams-details">${teamHtml}</div>
                </div>
            `;

            this.openModal();
            this.updateApiStatus(true);
        } catch (error) {
            this.updateApiStatus(false);
            this.showError(error.message || 'Could not load event details');
        } finally {
            this.hideLoading();
        }
    }

    teamDetailsHtml(team) {
        const players = (team.players || []).map((player) => `
            <span class="player-badge">${this.escapeHtml(player.firstName)} ${this.escapeHtml(player.lastName)}</span>
        `).join('');

        return `
            <div class="team-details">
                <div class="team-name">${this.escapeHtml(team.teamName || 'Team')}</div>
                <div class="team-info"><strong>Sport:</strong> ${this.escapeHtml(team.sportName || 'N/A')}</div>
                <div class="players-list">${players || '<span class="team-info">No players</span>'}</div>
            </div>
        `;
    }

    getMultiSelectValues(selectId) {
        const select = document.getElementById(selectId);
        return Array.from(select.selectedOptions)
            .map((option) => Number(option.value))
            .filter((value) => !Number.isNaN(value));
    }

    formatOffsetDateTime(value) {
        const parsed = new Date(value);
        if (Number.isNaN(parsed.getTime())) {
            throw new Error('Invalid date/time');
        }
        return parsed.toISOString();
    }

    toDateLabel(value) {
        if (!value) {
            return 'TBA';
        }
        const date = new Date(value);
        if (Number.isNaN(date.getTime())) {
            return value;
        }
        return date.toLocaleString();
    }

    openModal() {
        document.getElementById('event-modal').classList.remove('hidden');
    }

    closeModal() {
        document.getElementById('event-modal').classList.add('hidden');
    }

    showLoading() {
        document.getElementById('loading').classList.remove('hidden');
    }

    hideLoading() {
        document.getElementById('loading').classList.add('hidden');
    }

    showError(message) {
        const error = document.getElementById('error-message');
        error.textContent = message;
        error.classList.remove('hidden');
        setTimeout(() => error.classList.add('hidden'), 5000);
    }

    showSuccess(message) {
        const success = document.getElementById('success-message');
        success.textContent = message;
        success.classList.remove('hidden');
        setTimeout(() => success.classList.add('hidden'), 3000);
    }

    updateApiStatus(isConnected) {
        const status = document.getElementById('api-status');
        status.textContent = isConnected ? 'Connected' : 'Disconnected';
        status.classList.toggle('status-ok', isConnected);
        status.classList.toggle('status-error', !isConnected);
    }

    escapeHtml(value) {
        return String(value)
            .replaceAll('&', '&amp;')
            .replaceAll('<', '&lt;')
            .replaceAll('>', '&gt;')
            .replaceAll('"', '&quot;')
            .replaceAll("'", '&#039;');
    }
}
