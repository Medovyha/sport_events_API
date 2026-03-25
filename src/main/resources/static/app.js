document.addEventListener('DOMContentLoaded', async () => {
    const app = new SportsEventsDashboard();
    await app.init();
    window.sportsEventsApp = app;
});
