MERGE INTO players (id,name) VALUES (1,'Billie Herrington');
MERGE INTO players (id,name) VALUES (2,'Van Darkholme');
MERGE INTO players (id,name) VALUES (3,'Boy next door');
MERGE INTO players (id,name) VALUES (4,'Steve Rambo');
MERGE INTO players (id,name) VALUES (5,'Brad McGuire');
MERGE INTO players (id,name) VALUES (6,'Rey Harley');

ALTER TABLE players ALTER COLUMN id RESTART WITH 7;

INSERT INTO matches (id, player1_id, player2_id, winner_id) VALUES (1,1, 2, 1);
INSERT INTO matches (id, player1_id, player2_id, winner_id) VALUES (2,1, 3, 1);
INSERT INTO matches (id, player1_id, player2_id, winner_id) VALUES (3,1, 4, 1);
INSERT INTO matches (id, player1_id, player2_id, winner_id) VALUES (4,2, 3, 2);
INSERT INTO matches (id, player1_id, player2_id, winner_id) VALUES (5,2, 4, 2);
INSERT INTO matches (id, player1_id, player2_id, winner_id) VALUES (6,2, 5, 2);
INSERT INTO matches (id, player1_id, player2_id, winner_id) VALUES (7,1, 4, 1);
INSERT INTO matches (id, player1_id, player2_id, winner_id) VALUES (8,1, 5, 1);
INSERT INTO matches (id, player1_id, player2_id, winner_id) VALUES (9,1, 6, 1);

ALTER TABLE matches ALTER COLUMN id RESTART WITH 10;