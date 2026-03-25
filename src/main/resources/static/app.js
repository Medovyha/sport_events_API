/* ============================================================================
   Sports Events API Client - app.js
   ============================================================================ */

class EventsAPIClient {
    constructor(baseUrl = 'http://localhost:8080') {
        this.baseUrl = baseUrl;
        this.apiVersion = 'v1';
        this.language = 'en';
    }

    /**
     * Make an API request
     */
    async request(endpoint, options = {}) {
        const url = `${this.baseUrl}${endpoint}`;
        const headers = {
            'Content-Type': 'application/json',
            'Accept-Language': this.language,
            ...options.headers,
        };

        try {
            const response = await fetch(url, {
                method: options.method || 'GET',
                headers,
                ...options,
            });

            if (!response.ok) {
                throw new Error(`API Error: ${response.status} ${response.statusText}`);
            }

            return await response.json();
        } catch (error) {
            console.error('API Request Error:', error);
            throw error;
        }
    }

    /**
     * Get all events
     */
    async getAllEvents() {
        return this.request('/events');
    }

    /**
     * Get future events
     */
    async getFutureEvents() {
        return this.request('/events/future');
    }

    /**
     * Get past events
     */
    async getPastEvents() {
        return this.request('/events/previous');
    }

    /**
     * Get event details
     */
    async getEventDetails(eventId) {
        return this.request(`/events/${eventId}`);
    }
}

/* ============================================================================
   Event Display Manager
   ============================================================================ */

class EventsDisplayManager {
    constructor() {
        this.apiClient = new EventsAPIClient();
        this.currentEventData = {};
        this.initializeEventListeners();
    }

    /**
     * Initialize all event listeners
     */
    initializeEventListeners() {
        // Tab navigation
        document.querySelectorAll('.nav-tab').forEach(tab => {
            tab.addEventListener('click', (e) => this.handleTabClick(e));
        });

        // Modal close button
        document.querySelector('.modal-close').addEventListener('click', () => {
            this.closeModal();
        });

        // Modal background click
        document.getElementById('event-modal').addEventListener('click', (e) => {
            if (e.target.id === 'event-modal') {
                this.closeModal();
            }
        });

        // Load events on page load
        window.addEventListener('DOMContentLoaded', () => {
            this.loadAllEvents();
        });
    }

    /**
     * Handle tab navigation
     */
    handleTabClick(event) {
        const tabName = event.target.dataset.tab;
        
        // Update active tab button
        document.querySelectorAll('.nav-tab').forEach(tab => {
            tab.classList.remove('active');
        });
        event.target.classList.add('active');

        // Update active content section
        document.querySelectorAll('.tab-content').forEach(content => {
            content.classList.remove('active');
        });
        document.getElementById(tabName).classList.add('active');

        // Load appropriate events
        switch (tabName) {
            case 'all-events':
                this.loadAllEvents();
                break;
            case 'future-events':
                this.loadFutureEvents();
                break;
            case 'past-events':
                this.loadPastEvents();
                break;
        }
    }

    /**
     * Load and display all events
     */
    async loadAllEvents() {
        await this.loadEvents('/events', 'all-events-list');
    }

    /**
     * Load and display future events
     */
    async loadFutureEvents() {
        await this.loadEvents('/events/future', 'future-events-list');
    }

    /**
     * Load and display past events
     */
    async loadPastEvents() {
        await this.loadEvents('/events/previous', 'past-events-list');
    }

    /**
     * Generic event loading function
     */
    async loadEvents(endpoint, containerId) {
        const container = document.getElementById(containerId);
        this.showLoading();

        try {
            const events = await this.apiClient.request(endpoint);
            this.hideLoading();
            this.updateAPIStatus(true);

            if (!Array.isArray(events) || events.length === 0) {
                container.innerHTML = '<p class="no-events">No events found.</p>';
                return;
            }

            container.innerHTML = events.map(event => this.createEventCard(event)).join('');

            // Add click listeners to event cards
            container.querySelectorAll('.event-card').forEach(card => {
                card.addEventListener('click', () => {
                    const eventId = card.dataset.eventId;
                    this.showEventDetails(eventId);
                });
            });
        } catch (error) {
            this.hideLoading();
            this.updateAPIStatus(false);
            this.showError(`Failed to load events: ${error.message}`);
            container.innerHTML = '<p class="error">Failed to load events. Please try again later.</p>';
        }
    }

    /**
     * Create event card HTML
     */
    createEventCard(event) {
        const eventDate = new Date(event.startsAt);
        const formattedDate = eventDate.toLocaleDateString('en-US', {
            weekday: 'short',
            month: 'short',
            day: 'numeric',
            year: 'numeric',
        });
        const formattedTime = eventDate.toLocaleTimeString('en-US', {
            hour: '2-digit',
            minute: '2-digit',
        });

        const teams = Array.isArray(event.teams) ? event.teams : [];
        const teamNames = teams.map(team => team.teamName).filter(Boolean);

        const venueName = event.venue?.name || 'TBA';
        const venueAddress = event.venue?.address || '';

        return `
            <div class="event-card" data-event-id="${event.eventId}">
                <div class="event-header">
                    <div class="event-date">${formattedDate}</div>
                    <div class="event-name">${event.name || 'Unnamed Event'}</div>
                    <div class="event-time">${formattedTime}</div>
                </div>
                <div class="event-body">
                    <div class="event-venue">
                        <div class="venue-label">📍 Venue</div>
                        <div class="venue-name">${venueName}</div>
                        ${venueAddress ? `<div class="venue-address">${venueAddress}</div>` : ''}
                    </div>
                    
                    ${teamNames.length > 0 ? `
                        <div class="event-teams">
                            <div class="teams-label">🏆 Teams</div>
                            <div class="teams-list">
                                ${teamNames.map(team => `<span class="team-badge">${team}</span>`).join('')}
                            </div>
                        </div>
                    ` : ''}
                    
                    ${event.description ? `
                        <div class="event-description">${event.description}</div>
                    ` : ''}
                    
                    <div class="event-footer">
                        <button class="btn-view-details" data-event-id="${event.eventId}">
                            View Details →
                        </button>
                    </div>
                </div>
            </div>
        `;
    }

    /**
     * Show event details in modal
     */
    async showEventDetails(eventId) {
        this.showLoading();

        try {
            const event = await this.apiClient.getEventDetails(eventId);
            this.hideLoading();

            const eventDate = new Date(event.startsAt);
            const formattedDate = eventDate.toLocaleDateString('en-US', {
                weekday: 'long',
                month: 'long',
                day: 'numeric',
                year: 'numeric',
            });
            const formattedTime = eventDate.toLocaleTimeString('en-US', {
                hour: '2-digit',
                minute: '2-digit',
            });

            const teams = Array.isArray(event.teams) ? event.teams : [];
            const venueName = event.venue?.name || 'TBA';
            const venueAddress = event.venue?.address || '';

            const modalBody = document.getElementById('modal-body');
            modalBody.innerHTML = `
                <div class="modal-header">
                    <div class="modal-title">${event.name || 'Event Details'}</div>
                    <div class="modal-date">${formattedDate} at ${formattedTime}</div>
                </div>

                <div class="modal-section">
                    <div class="modal-section-title">📍 Venue Information</div>
                    <div class="venue-details">
                        <div class="detail-item">
                            <span class="detail-label">Name:</span>
                            <span class="detail-value">${venueName}</span>
                        </div>
                        ${venueAddress ? `
                            <div class="detail-item">
                                <span class="detail-label">Address:</span>
                                <span class="detail-value">${venueAddress}</span>
                            </div>
                        ` : ''}
                    </div>
                </div>

                ${event.description ? `
                    <div class="modal-section">
                        <div class="modal-section-title">📝 Description</div>
                        <p>${event.description}</p>
                    </div>
                ` : ''}

                ${teams.length > 0 ? `
                    <div class="modal-section">
                        <div class="modal-section-title">🏆 Teams & Players</div>
                        <div class="teams-details">
                            ${teams.map(team => this.createTeamDetailsHTML(team)).join('')}
                        </div>
                    </div>
                ` : `
                    <div class="modal-section">
                        <div class="no-teams">No teams added to this event yet.</div>
                    </div>
                `}
            `;

            this.openModal();
        } catch (error) {
            this.hideLoading();
            this.showError(`Failed to load event details: ${error.message}`);
        }
    }

    /**
     * Create team details HTML
     */
    createTeamDetailsHTML(team) {
        const players = Array.isArray(team.players) ? team.players : [];
        const playerCount = players.length;

        return `
            <div class="team-details">
                <div class="team-name">${team.teamName}</div>
                <div class="team-info">
                    <strong>Sport:</strong> ${team.sportName || 'N/A'} | 
                    <strong>Players:</strong> ${playerCount}
                </div>
                ${players.length > 0 ? `
                    <div class="players-list">
                        ${players.map(player => `
                            <span class="player-badge">
                                ${player.firstName} ${player.lastName}
                            </span>
                        `).join('')}
                    </div>
                ` : '<p class="team-info">No players assigned</p>'}
            </div>
        `;
    }

    /**
     * Show loading indicator
     */
    showLoading() {
        document.getElementById('loading').classList.remove('hidden');
    }

    /**
     * Hide loading indicator
     */
    hideLoading() {
        document.getElementById('loading').classList.add('hidden');
    }

    /**
     * Show error message
     */
    showError(message) {
        const errorElement = document.getElementById('error-message');
        errorElement.textContent = message;
        errorElement.classList.remove('hidden');
        setTimeout(() => {
            errorElement.classList.add('hidden');
        }, 5000);
    }

    /**
     * Open event details modal
     */
    openModal() {
        document.getElementById('event-modal').classList.remove('hidden');
    }

    /**
     * Close event details modal
     */
    closeModal() {
        document.getElementById('event-modal').classList.add('hidden');
    }

    /**
     * Update API status indicator
     */
    updateAPIStatus(isConnected) {
        const statusElement = document.getElementById('api-status');
        if (isConnected) {
            statusElement.textContent = 'Connected';
            statusElement.classList.remove('status-error');
            statusElement.classList.add('status-ok');
        } else {
            statusElement.textContent = 'Disconnected';
            statusElement.classList.remove('status-ok');
            statusElement.classList.add('status-error');
        }
    }
}

/* ============================================================================
   Application Initialization
   ============================================================================ */

let app;

document.addEventListener('DOMContentLoaded', () => {
    app = new EventsDisplayManager();
    console.log('Sports Events Application Initialized');
});
