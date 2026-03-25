class SportsEventsApiClient {
    constructor(baseUrl = '') {
        this.baseUrl = baseUrl;
        this.language = 'en';
    }

    setLanguage(languageCode) {
        this.language = languageCode || 'en';
    }

    async request(endpoint, options = {}) {
        const headers = {
            'Content-Type': 'application/json',
            'Accept-Language': this.language,
            ...(options.headers || {}),
        };

        const response = await fetch(`${this.baseUrl}${endpoint}`, {
            method: options.method || 'GET',
            headers,
            body: options.body,
        });

        if (!response.ok) {
            let message = `HTTP ${response.status}`;
            try {
                const data = await response.json();
                if (data?.message) {
                    message = data.message;
                }
            } catch (_) {
                message = `${message} ${response.statusText}`.trim();
            }
            throw new Error(message);
        }

        if (response.status === 204) {
            return null;
        }

        return response.json();
    }

    getSports() {
        return this.request('/sports');
    }

    getSportByLanguage(sportId, languageCode) {
        return this.request(`/sports/${sportId}`, {
            headers: {
                'Accept-Language': languageCode,
            },
        });
    }

    createSport(payload) {
        return this.request('/sports', {
            method: 'POST',
            body: JSON.stringify(payload),
        });
    }

    upsertSportTranslation(sportId, languageCode, name) {
        return this.request(`/sports/${sportId}/translations/${languageCode}`, {
            method: 'PUT',
            body: JSON.stringify({ name }),
        });
    }

    getVenues() {
        return this.request('/venues');
    }

    createVenue(payload) {
        return this.request('/venues', {
            method: 'POST',
            body: JSON.stringify(payload),
        });
    }

    deleteVenue(venueId) {
        return this.request(`/venues/${venueId}`, { method: 'DELETE' });
    }

    getPlayers() {
        return this.request('/players');
    }

    createPlayer(payload) {
        return this.request('/players', {
            method: 'POST',
            body: JSON.stringify(payload),
        });
    }

    deletePlayer(playerId) {
        return this.request(`/players/${playerId}`, { method: 'DELETE' });
    }

    getTeams() {
        return this.request('/teams');
    }

    getTeamPlayers(teamId) {
        return this.request(`/teams/${teamId}/players`);
    }

    createTeam(payload) {
        return this.request('/teams', {
            method: 'POST',
            body: JSON.stringify(payload),
        });
    }

    deleteTeam(teamId) {
        return this.request(`/teams/${teamId}`, { method: 'DELETE' });
    }

    assignPlayerToTeam(teamId, playerId) {
        return this.request(`/teams/${teamId}/players`, {
            method: 'POST',
            body: JSON.stringify({ playerId }),
        });
    }

    removePlayerFromTeam(teamId, playerId) {
        return this.request(`/teams/${teamId}/players/${playerId}`, {
            method: 'DELETE',
        });
    }

    getEvents() {
        return this.request('/events');
    }

    getFutureEvents() {
        return this.request('/events/future');
    }

    getPastEvents() {
        return this.request('/events/previous');
    }

    getEvent(eventId) {
        return this.request(`/events/${eventId}`);
    }

    getEventByLanguage(eventId, languageCode) {
        return this.request(`/events/${eventId}`, {
            headers: {
                'Accept-Language': languageCode,
            },
        });
    }

    createEvent(payload) {
        return this.request('/events', {
            method: 'POST',
            body: JSON.stringify(payload),
        });
    }

    updateEvent(eventId, payload) {
        return this.request(`/events/${eventId}`, {
            method: 'PUT',
            body: JSON.stringify(payload),
        });
    }

    deleteEvent(eventId) {
        return this.request(`/events/${eventId}`, { method: 'DELETE' });
    }

    addTeamToEvent(eventId, teamId) {
        return this.request(`/events/${eventId}/teams`, {
            method: 'POST',
            body: JSON.stringify({ teamId }),
        });
    }

    removeTeamFromEvent(eventId, teamId) {
        return this.request(`/events/${eventId}/teams/${teamId}`, {
            method: 'DELETE',
        });
    }

    addPlayersToEventTeam(eventId, teamId, playerIds) {
        return this.request(`/events/${eventId}/teams/${teamId}/players`, {
            method: 'POST',
            body: JSON.stringify({ playerIds }),
        });
    }

    updateEventTeamPlayers(eventId, teamId, playerIds) {
        return this.request(`/events/${eventId}/teams/${teamId}/players`, {
            method: 'PUT',
            body: JSON.stringify({ playerIds }),
        });
    }

    removePlayerFromEventTeam(eventId, teamId, playerId) {
        return this.request(`/events/${eventId}/teams/${teamId}/players/${playerId}`, {
            method: 'DELETE',
        });
    }
}
