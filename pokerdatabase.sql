CREATE TABLE hands
(
	hand_id 		SERIAL PRIMARY KEY
	, cards 		CHARACTER(2) NOT NULL
	, is_suited 		BOOLEAN NOT NULL
	, is_winning_hand 	BOOLEAN NOT NULL
);

CREATE TABLE players
(
	player_id 	SERIAL PRIMARY KEY
	, name 		VARCHAR(100) NOT NULL
	, balance 	INTEGER NOT NULL
);

CREATE TABLE player_hands
(
	hand_id INTEGER NOT NULL
	, player_id INTEGER NOT NULL
	
	, CONSTRAINT pk_player_hands PRIMARY KEY(hand_id,player_id)
	,CONSTRAINT fk_players FOREIGN KEY (player_id) REFERENCES players(player_id)
	,CONSTRAINT fk_hands FOREIGN KEY (hand_id) REFERENCES hands(hand_id)
);
