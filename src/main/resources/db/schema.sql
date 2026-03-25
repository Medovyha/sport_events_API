
CREATE TABLE public.event_players (
    event_players_id integer NOT NULL,
    event_team_id integer NOT NULL,
    player_id integer NOT NULL
);


ALTER TABLE public.event_players OWNER TO "user";

--
-- Name: event_players_event_players_id_seq; Type: SEQUENCE; Schema: public; Owner: user
--

CREATE SEQUENCE public.event_players_event_players_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.event_players_event_players_id_seq OWNER TO "user";

--
-- Name: event_players_event_players_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: user
--

ALTER SEQUENCE public.event_players_event_players_id_seq OWNED BY public.event_players.event_players_id;


--
-- Name: event_teams; Type: TABLE; Schema: public; Owner: user
--

CREATE TABLE public.event_teams (
    event_teams_id integer NOT NULL,
    event_id integer NOT NULL,
    team_id integer NOT NULL
);


ALTER TABLE public.event_teams OWNER TO "user";

--
-- Name: event_teams_event_teams_id_seq; Type: SEQUENCE; Schema: public; Owner: user
--

CREATE SEQUENCE public.event_teams_event_teams_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.event_teams_event_teams_id_seq OWNER TO "user";

--
-- Name: event_teams_event_teams_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: user
--

ALTER SEQUENCE public.event_teams_event_teams_id_seq OWNED BY public.event_teams.event_teams_id;


--
-- Name: event_translations; Type: TABLE; Schema: public; Owner: user
--

CREATE TABLE public.event_translations (
    event_translation_id integer NOT NULL,
    event_id integer NOT NULL,
    language_id integer NOT NULL,
    name character varying(255) NOT NULL,
    description text NOT NULL
);


ALTER TABLE public.event_translations OWNER TO "user";

--
-- Name: event_translations_event_translation_id_seq; Type: SEQUENCE; Schema: public; Owner: user
--

CREATE SEQUENCE public.event_translations_event_translation_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.event_translations_event_translation_id_seq OWNER TO "user";

--
-- Name: event_translations_event_translation_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: user
--

ALTER SEQUENCE public.event_translations_event_translation_id_seq OWNED BY public.event_translations.event_translation_id;


--
-- Name: events; Type: TABLE; Schema: public; Owner: user
--

CREATE TABLE public.events (
    event_id bigint NOT NULL,
    starts_at timestamp with time zone NOT NULL,
    venue_id integer NOT NULL
);


ALTER TABLE public.events OWNER TO "user";

--
-- Name: events_event_id_seq; Type: SEQUENCE; Schema: public; Owner: user
--

CREATE SEQUENCE public.events_event_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.events_event_id_seq OWNER TO "user";

--
-- Name: events_event_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: user
--

ALTER SEQUENCE public.events_event_id_seq OWNED BY public.events.event_id;


--
-- Name: languages; Type: TABLE; Schema: public; Owner: user
--

CREATE TABLE public.languages (
    language_id integer NOT NULL,
    code character varying(5) NOT NULL,
    name character varying(50) NOT NULL
);


ALTER TABLE public.languages OWNER TO "user";

--
-- Name: languages_language_id_seq; Type: SEQUENCE; Schema: public; Owner: user
--

CREATE SEQUENCE public.languages_language_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.languages_language_id_seq OWNER TO "user";

--
-- Name: languages_language_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: user
--

ALTER SEQUENCE public.languages_language_id_seq OWNED BY public.languages.language_id;


--
-- Name: players; Type: TABLE; Schema: public; Owner: user
--

CREATE TABLE public.players (
    player_id integer NOT NULL,
    first_name character varying(100) NOT NULL,
    last_name character varying(100) NOT NULL,
    date_of_birth date NOT NULL
);


ALTER TABLE public.players OWNER TO "user";

--
-- Name: players_player_id_seq; Type: SEQUENCE; Schema: public; Owner: user
--

CREATE SEQUENCE public.players_player_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.players_player_id_seq OWNER TO "user";

--
-- Name: players_player_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: user
--

ALTER SEQUENCE public.players_player_id_seq OWNED BY public.players.player_id;


--
-- Name: sport_translations; Type: TABLE; Schema: public; Owner: user
--

CREATE TABLE public.sport_translations (
    sport_translation_id integer NOT NULL,
    sport_id integer NOT NULL,
    language_id integer NOT NULL,
    name character varying(255) NOT NULL
);


ALTER TABLE public.sport_translations OWNER TO "user";

--
-- Name: sport_translations_sport_translation_id_seq; Type: SEQUENCE; Schema: public; Owner: user
--

CREATE SEQUENCE public.sport_translations_sport_translation_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.sport_translations_sport_translation_id_seq OWNER TO "user";

--
-- Name: sport_translations_sport_translation_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: user
--

ALTER SEQUENCE public.sport_translations_sport_translation_id_seq OWNED BY public.sport_translations.sport_translation_id;


--
-- Name: sports; Type: TABLE; Schema: public; Owner: user
--

CREATE TABLE public.sports (
    sport_id integer NOT NULL
);


ALTER TABLE public.sports OWNER TO "user";

--
-- Name: sports_sport_id_seq; Type: SEQUENCE; Schema: public; Owner: user
--

CREATE SEQUENCE public.sports_sport_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.sports_sport_id_seq OWNER TO "user";

--
-- Name: sports_sport_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: user
--

ALTER SEQUENCE public.sports_sport_id_seq OWNED BY public.sports.sport_id;


--
-- Name: team_players; Type: TABLE; Schema: public; Owner: user
--

CREATE TABLE public.team_players (
    team_player_id integer NOT NULL,
    team_id integer NOT NULL,
    player_id integer NOT NULL
);


ALTER TABLE public.team_players OWNER TO "user";

--
-- Name: team_players_team_player_id_seq; Type: SEQUENCE; Schema: public; Owner: user
--

CREATE SEQUENCE public.team_players_team_player_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.team_players_team_player_id_seq OWNER TO "user";

--
-- Name: team_players_team_player_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: user
--

ALTER SEQUENCE public.team_players_team_player_id_seq OWNED BY public.team_players.team_player_id;


--
-- Name: teams; Type: TABLE; Schema: public; Owner: user
--

CREATE TABLE public.teams (
    team_id integer NOT NULL,
    name character varying(255) NOT NULL,
    sport_id integer NOT NULL
);


ALTER TABLE public.teams OWNER TO "user";

--
-- Name: teams_team_id_seq; Type: SEQUENCE; Schema: public; Owner: user
--

CREATE SEQUENCE public.teams_team_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.teams_team_id_seq OWNER TO "user";

--
-- Name: teams_team_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: user
--

ALTER SEQUENCE public.teams_team_id_seq OWNED BY public.teams.team_id;


--
-- Name: venues; Type: TABLE; Schema: public; Owner: user
--

CREATE TABLE public.venues (
    venue_id integer NOT NULL,
    name character varying(255) NOT NULL,
    address character varying(255) NOT NULL
);


ALTER TABLE public.venues OWNER TO "user";

--
-- Name: venues_venue_id_seq; Type: SEQUENCE; Schema: public; Owner: user
--

CREATE SEQUENCE public.venues_venue_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.venues_venue_id_seq OWNER TO "user";

--
-- Name: venues_venue_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: user
--

ALTER SEQUENCE public.venues_venue_id_seq OWNED BY public.venues.venue_id;


--
-- Name: event_players event_players_id; Type: DEFAULT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.event_players ALTER COLUMN event_players_id SET DEFAULT nextval('public.event_players_event_players_id_seq'::regclass);


--
-- Name: event_teams event_teams_id; Type: DEFAULT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.event_teams ALTER COLUMN event_teams_id SET DEFAULT nextval('public.event_teams_event_teams_id_seq'::regclass);


--
-- Name: event_translations event_translation_id; Type: DEFAULT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.event_translations ALTER COLUMN event_translation_id SET DEFAULT nextval('public.event_translations_event_translation_id_seq'::regclass);


--
-- Name: events event_id; Type: DEFAULT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.events ALTER COLUMN event_id SET DEFAULT nextval('public.events_event_id_seq'::regclass);


--
-- Name: languages language_id; Type: DEFAULT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.languages ALTER COLUMN language_id SET DEFAULT nextval('public.languages_language_id_seq'::regclass);


--
-- Name: players player_id; Type: DEFAULT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.players ALTER COLUMN player_id SET DEFAULT nextval('public.players_player_id_seq'::regclass);


--
-- Name: sport_translations sport_translation_id; Type: DEFAULT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.sport_translations ALTER COLUMN sport_translation_id SET DEFAULT nextval('public.sport_translations_sport_translation_id_seq'::regclass);


--
-- Name: sports sport_id; Type: DEFAULT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.sports ALTER COLUMN sport_id SET DEFAULT nextval('public.sports_sport_id_seq'::regclass);


--
-- Name: team_players team_player_id; Type: DEFAULT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.team_players ALTER COLUMN team_player_id SET DEFAULT nextval('public.team_players_team_player_id_seq'::regclass);


--
-- Name: teams team_id; Type: DEFAULT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.teams ALTER COLUMN team_id SET DEFAULT nextval('public.teams_team_id_seq'::regclass);


--
-- Name: venues venue_id; Type: DEFAULT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.venues ALTER COLUMN venue_id SET DEFAULT nextval('public.venues_venue_id_seq'::regclass);


--
-- Name: event_players event_players_pk; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.event_players
    ADD CONSTRAINT event_players_pk PRIMARY KEY (event_players_id);


--
-- Name: event_teams event_teams_pk; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.event_teams
    ADD CONSTRAINT event_teams_pk PRIMARY KEY (event_teams_id);


--
-- Name: event_translations event_translations_pk; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.event_translations
    ADD CONSTRAINT event_translations_pk PRIMARY KEY (event_translation_id);


--
-- Name: events events_pk; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.events
    ADD CONSTRAINT events_pk PRIMARY KEY (event_id);


--
-- Name: languages languages_pk; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.languages
    ADD CONSTRAINT languages_pk PRIMARY KEY (language_id);


--
-- Name: players players_pk; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.players
    ADD CONSTRAINT players_pk PRIMARY KEY (player_id);


--
-- Name: sport_translations sport_translations_pk; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.sport_translations
    ADD CONSTRAINT sport_translations_pk PRIMARY KEY (sport_translation_id);


--
-- Name: sports sports_pk; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.sports
    ADD CONSTRAINT sports_pk PRIMARY KEY (sport_id);


--
-- Name: team_players team_players_pk; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.team_players
    ADD CONSTRAINT team_players_pk PRIMARY KEY (team_player_id);


--
-- Name: teams teams_pk; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.teams
    ADD CONSTRAINT teams_pk PRIMARY KEY (team_id);


--
-- Name: venues venues_pk; Type: CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.venues
    ADD CONSTRAINT venues_pk PRIMARY KEY (venue_id);


--
-- Name: event_players_event_team_id_player_id_index; Type: INDEX; Schema: public; Owner: user
--

CREATE INDEX event_players_event_team_id_player_id_index ON public.event_players USING btree (event_team_id, player_id);


--
-- Name: event_translations_event_id_language_id_uindex; Type: INDEX; Schema: public; Owner: user
--

CREATE UNIQUE INDEX event_translations_event_id_language_id_uindex ON public.event_translations USING btree (event_id, language_id);


--
-- Name: languages_code_uindex; Type: INDEX; Schema: public; Owner: user
--

CREATE UNIQUE INDEX languages_code_uindex ON public.languages USING btree (code);


--
-- Name: sport_translations_sport_id_language_id_uindex; Type: INDEX; Schema: public; Owner: user
--

CREATE UNIQUE INDEX sport_translations_sport_id_language_id_uindex ON public.sport_translations USING btree (sport_id, language_id);


--
-- Name: team_players_team_id_player_id_uindex; Type: INDEX; Schema: public; Owner: user
--

CREATE UNIQUE INDEX team_players_team_id_player_id_uindex ON public.team_players USING btree (team_id, player_id);


--
-- Name: event_players _event_players_events_event_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.event_players
    ADD CONSTRAINT _event_players_events_event_id_fk FOREIGN KEY (event_team_id) REFERENCES public.event_teams(event_teams_id);


--
-- Name: event_players _event_players_players_player_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.event_players
    ADD CONSTRAINT _event_players_players_player_id_fk FOREIGN KEY (player_id) REFERENCES public.players(player_id);


--
-- Name: event_teams _event_teams_events_event_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.event_teams
    ADD CONSTRAINT _event_teams_events_event_id_fk FOREIGN KEY (event_id) REFERENCES public.events(event_id);


--
-- Name: event_teams _event_teams_teams_team_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.event_teams
    ADD CONSTRAINT _event_teams_teams_team_id_fk FOREIGN KEY (team_id) REFERENCES public.teams(team_id);


--
-- Name: event_translations _events_fk; Type: FK CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.event_translations
    ADD CONSTRAINT _events_fk FOREIGN KEY (event_id) REFERENCES public.events(event_id);


--
-- Name: events _events_venues_venue_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.events
    ADD CONSTRAINT _events_venues_venue_id_fk FOREIGN KEY (venue_id) REFERENCES public.venues(venue_id);


--
-- Name: event_translations _languages_fk; Type: FK CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.event_translations
    ADD CONSTRAINT _languages_fk FOREIGN KEY (language_id) REFERENCES public.languages(language_id);


--
-- Name: sport_translations _languages_fk; Type: FK CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.sport_translations
    ADD CONSTRAINT _languages_fk FOREIGN KEY (language_id) REFERENCES public.languages(language_id);


--
-- Name: sport_translations _sports_fk; Type: FK CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.sport_translations
    ADD CONSTRAINT _sports_fk FOREIGN KEY (sport_id) REFERENCES public.sports(sport_id);


--
-- Name: team_players _team_players_players_player_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.team_players
    ADD CONSTRAINT _team_players_players_player_id_fk FOREIGN KEY (player_id) REFERENCES public.players(player_id);


--
-- Name: team_players _team_players_teams_team_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.team_players
    ADD CONSTRAINT _team_players_teams_team_id_fk FOREIGN KEY (team_id) REFERENCES public.teams(team_id);


--
-- Name: teams _teams_sports_sport_id_fk; Type: FK CONSTRAINT; Schema: public; Owner: user
--

ALTER TABLE ONLY public.teams
    ADD CONSTRAINT _teams_sports_sport_id_fk FOREIGN KEY (sport_id) REFERENCES public.sports(sport_id);

