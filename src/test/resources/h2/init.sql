CREATE TABLE race
(
    id   SERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(20)        NOT NULL
);

INSERT INTO race (name)
VALUES ('HUMAN');
INSERT INTO race (name)
VALUES ('DWARF');
INSERT INTO race (name)
VALUES ('ELF');
INSERT INTO race (name)
VALUES ('GIANT');
INSERT INTO race (name)
VALUES ('ORC');
INSERT INTO race (name)
VALUES ('TROLL');
INSERT INTO race (name)
VALUES ('HOBBIT');

CREATE TABLE profession
(
    id   SERIAL PRIMARY KEY NOT NULL,
    name VARCHAR(20)        NOT NULL
);

INSERT INTO profession (name)
VALUES ('WARRIOR');
INSERT INTO profession (name)
VALUES ('ROGUE');
INSERT INTO profession (name)
VALUES ('SORCERER');
INSERT INTO profession (name)
VALUES ('CLERIC');
INSERT INTO profession (name)
VALUES ('PALADIN');
INSERT INTO profession (name)
VALUES ('NAZGUL');
INSERT INTO profession (name)
VALUES ('WARLOCK');
INSERT INTO profession (name)
VALUES ('DRUID');

CREATE TABLE player
(
    id             BIGSERIAL PRIMARY KEY          NOT NULL,
    name           VARCHAR(12)                    NOT NULL,
    title          VARCHAR(30)                    NOT NULL,
    race_id        INT REFERENCES race (id)       NOT NULL,
    profession_id  INT REFERENCES profession (id) NOT NULL,
    birthday       DATE                           NOT NULL,
    banned         BOOLEAN                        NOT NULL,
    experience     INT                            NOT NULL,
    level          INT                            NOT NULL,
    untilNextLevel INT                            NOT NULL
);

INSERT INTO player (id, name, title, race_id, profession_id, birthday, banned, experience, level, untilNextLevel)
values (1, 'nameTest1', 'titleTest1', 2, 4, '2020-01-01', true, 0, 0, 100),
       (2, 'nameTest2', 'titleTest2', 2, 4, '2020-01-01', true, 0, 0, 100),
       (3, 'nameTest', 'title', 2, 4, '2021-02-27', true, 4000, 8, 500),
       (4, 'name', 'Test', 3, 2, '2013-11-11', false, 600111, 109, 10389),
       (5, 'nameTest3', 'titleTest3', 2, 4, '2020-01-01', true, 0, 0, 100),
       (6, 'name', 'title', 3, 4, '2010-11-11', true, 600111, 109, 10389),
       (7, 'name', 'Test', 3, 4, '2009-11-11', false, 600111, 109, 10389);





