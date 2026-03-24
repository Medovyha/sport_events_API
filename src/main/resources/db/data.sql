-- ============================================================
-- Seed data for sport_events database
-- Run manually: psql -U user -d sportradar -f src/main/resources/db/data.sql
-- All inserts are idempotent (ON CONFLICT DO NOTHING)
-- ============================================================

-- Languages
INSERT INTO public.languages (language_id, code, name) VALUES
    (1, 'en', 'English'),
    (2, 'uk', 'Ukrainian'),
    (3, 'pl', 'Polish')
ON CONFLICT DO NOTHING;

-- Sports  (sport_id only — names live in sport_translations)
INSERT INTO public.sports (sport_id) VALUES (1), (2)
ON CONFLICT DO NOTHING;

-- Sport translations
INSERT INTO public.sport_translations (sport_translation_id, sport_id, language_id, name) VALUES
    (1, 1, 1, 'Football'),
    (2, 1, 2, 'Футбол'),
    (3, 2, 1, 'Basketball'),
    (4, 2, 2, 'Баскетбол'),
    (5, 1, 3, 'Piłka nożna'),
    (6, 2, 3, 'Koszykówka')
ON CONFLICT DO NOTHING;

-- Venues
INSERT INTO public.venues (venue_id, name, address) VALUES
    (1, 'Santiago Bernabéu',  'Av. de Concha Espina, 1, Madrid, Spain'),
    (2, 'Camp Nou',           'Carrer d''Aristides Maillol, Barcelona, Spain'),
    (3, 'Crypto.com Arena',   '1111 S Figueroa St, Los Angeles, CA, USA')
ON CONFLICT DO NOTHING;

-- Players
INSERT INTO public.players (player_id, first_name, last_name, date_of_birth) VALUES
    -- Real Madrid
    (1,  'Carlos',   'Silva',      '1990-03-15'),
    (2,  'Luis',     'Fernandez',  '1993-07-22'),
    (3,  'Marco',    'Garcia',     '1995-01-08'),
    (4,  'Antonio',  'Lopez',      '1991-11-30'),
    (5,  'Jorge',    'Martinez',   '1998-05-14'),
    -- FC Barcelona
    (6,  'David',    'Torres',     '1992-09-03'),
    (7,  'Pablo',    'Sanchez',    '1994-02-17'),
    (8,  'Miguel',   'Rodriguez',  '1996-06-25'),
    (9,  'Roberto',  'Diaz',       '1993-04-11'),
    (10, 'Fernando', 'Perez',      '1999-08-20'),
    -- LA Lakers
    (11, 'James',    'Williams',   '1994-12-01'),
    (12, 'Marcus',   'Johnson',    '1997-03-18'),
    (13, 'Tyler',    'Davis',      '1996-07-04'),
    (14, 'Kevin',    'Brown',      '1993-10-22'),
    (15, 'Andre',    'Wilson',     '1995-05-09'),
    -- Chicago Bulls
    (16, 'Derek',    'Thomas',     '1991-08-14'),
    (17, 'Brian',    'Harris',     '1998-01-25'),
    (18, 'Samuel',   'Clark',      '1995-11-30'),
    (19, 'Jordan',   'White',      '1997-06-15'),
    (20, 'Chris',    'Moore',      '1994-03-07')
ON CONFLICT DO NOTHING;

-- Teams
INSERT INTO public.teams (team_id, name, sport_id) VALUES
    (1, 'Real Madrid',   1),
    (2, 'FC Barcelona',  1),
    (3, 'LA Lakers',     2),
    (4, 'Chicago Bulls', 2)
ON CONFLICT DO NOTHING;

-- Team players (squad rosters)
INSERT INTO public.team_players (team_player_id, team_id, player_id) VALUES
    (1,  1, 1),  (2,  1, 2),  (3,  1, 3),  (4,  1, 4),  (5,  1, 5),
    (6,  2, 6),  (7,  2, 7),  (8,  2, 8),  (9,  2, 9),  (10, 2, 10),
    (11, 3, 11), (12, 3, 12), (13, 3, 13), (14, 3, 14), (15, 3, 15),
    (16, 4, 16), (17, 4, 17), (18, 4, 18), (19, 4, 19), (20, 4, 20)
ON CONFLICT DO NOTHING;

-- Events
INSERT INTO public.events (event_id, starts_at, venue_id) VALUES
    (1, '2026-04-05 20:00:00+00', 1),
    (2, '2026-04-10 19:30:00+00', 3)
ON CONFLICT DO NOTHING;

-- Event translations
INSERT INTO public.event_translations (event_translation_id, event_id, language_id, name, description) VALUES
    (1, 1, 1, 'El Clásico',
        'The most anticipated football clash of the season between Real Madrid and FC Barcelona.'),
    (2, 1, 2, 'Ель Класіко',
        'Найочікуваніший футбольний матч сезону між Реал Мадридом та ФК Барселона.'),
    (3, 2, 1, 'Lakers vs Bulls',
        'An electrifying NBA showdown between the Los Angeles Lakers and the Chicago Bulls.'),
    (4, 2, 2, 'Лейкерс — Буллз',
        'Захоплюючий матч НБА між Лос-Анджелес Лейкерс та Чикаго Буллз.'),
    (5, 1, 3, 'El Clásico',
        'Najbardziej wyczekiwane starcie piłkarskie sezonu pomiędzy Realem Madryt a FC Barceloną.'),
    (6, 2, 3, 'Lakers vs Bulls',
        'Elektryzujące starcie NBA pomiędzy Los Angeles Lakers a Chicago Bulls.')
ON CONFLICT DO NOTHING;

-- Event teams (which teams play in each event)
INSERT INTO public.event_teams (event_teams_id, event_id, team_id) VALUES
    (1, 1, 1),   -- El Clásico: Real Madrid
    (2, 1, 2),   -- El Clásico: FC Barcelona
    (3, 2, 3),   -- Lakers vs Bulls: LA Lakers
    (4, 2, 4)    -- Lakers vs Bulls: Chicago Bulls
ON CONFLICT DO NOTHING;

-- Event players (which players are fielded in each event team)
INSERT INTO public.event_players (event_players_id, event_team_id, player_id) VALUES
    -- El Clásico — Real Madrid (event_team_id = 1)
    (1,  1, 1),  (2,  1, 2),  (3,  1, 3),  (4,  1, 4),  (5,  1, 5),
    -- El Clásico — FC Barcelona (event_team_id = 2)
    (6,  2, 6),  (7,  2, 7),  (8,  2, 8),  (9,  2, 9),  (10, 2, 10),
    -- Lakers vs Bulls — LA Lakers (event_team_id = 3)
    (11, 3, 11), (12, 3, 12), (13, 3, 13), (14, 3, 14), (15, 3, 15),
    -- Lakers vs Bulls — Chicago Bulls (event_team_id = 4)
    (16, 4, 16), (17, 4, 17), (18, 4, 18), (19, 4, 19), (20, 4, 20)
ON CONFLICT DO NOTHING;

-- Advance sequences past the manually inserted IDs
SELECT setval('public.languages_language_id_seq',               3);
SELECT setval('public.sports_sport_id_seq',                     2);
SELECT setval('public.sport_translations_sport_translation_id_seq', 6);
SELECT setval('public.venues_venue_id_seq',                     3);
SELECT setval('public.players_player_id_seq',                   20);
SELECT setval('public.teams_team_id_seq',                       4);
SELECT setval('public.team_players_team_player_id_seq',         20);
SELECT setval('public.events_event_id_seq',                     2);
SELECT setval('public.event_translations_event_translation_id_seq', 6);
SELECT setval('public.event_teams_event_teams_id_seq',          4);
SELECT setval('public.event_players_event_players_id_seq',      20);
