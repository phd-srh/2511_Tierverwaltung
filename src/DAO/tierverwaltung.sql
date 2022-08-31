-- Script für die Tierverwaltung
drop database if exists 2511_tierverwaltung;
create database 2511_tierverwaltung;
use 2511_tierverwaltung;

DROP USER if exists 2511_tieradm;
CREATE USER 2511_tieradm IDENTIFIED BY 'geheim123';
GRANT ALL ON 2511_tierverwaltung.* TO 2511_tieradm WITH GRANT OPTION;

CREATE TABLE tiere (
	chipnummer INT PRIMARY KEY,
    name varchar(255),
    `alter` INT,
    geschlecht CHAR(1),
    tierart INT NOT NULL,
    persönlichkeit INT NOT NULL
	);

CREATE TABLE tierart (
	tierart INT PRIMARY KEY,
    bezeichnung VARCHAR(255)
    );

CREATE TABLE persönlichkeit (
	persönlichkeit INT PRIMARY KEY,
    bezeichnung VARCHAR(255)
    );

-- fixe Wert für die Persönlichkeit
INSERT INTO persönlichkeit VALUES
	(0, 'nett'),
    (1, 'böse'),
    (2, 'scheu'),
    (3, 'verspielt'),
    (4, 'schelmisch'),
    (5, 'verwöhnt'),
    (6, 'aufgeweckt'),
    (7, 'zutraulich'),
    (8, 'neugierig'),
    (9, 'frech'),
    (10, 'treu');

-- Testdaten
INSERT INTO tierart (tierart, bezeichnung) VALUES
	(1, 'Hund'),
    (2, 'Katze'),
    (3, 'Vogel'),
    (4, 'Fisch');

INSERT INTO tiere (chipnummer, name, `alter`, geschlecht, tierart, persönlichkeit)
	VALUES
    (1, 'Lessie', 2, 'w', 1, 0),
    (2, 'Hachiko', 5, 'm', 1, 10),
    (3, 'Beethoven', 3, 'm', 1, 7),
    (4, 'Susi', 1, 'w', 1, 4),
    (5, 'Strolch', 1, 'm', 1, 3),

    (6, 'Tom', 3, 'm', 2, 4),
    (7, 'Garfield', 7, 'm', 2, 1),
    (8, 'Nala', 1, 'w', 2, 6),
    (9, 'Simba', 2, 'm', 2, 5),
    (10, 'Kiara', 1, 'w', 2, 9),

    (11, 'Zazu', 12, 'm', 3, 2),
    (12, 'Hedwig', 6, 'd', 3, 8),
    (13, 'Pepper', 5, 'w', 3, 6),
    (14, 'Popcorn', 2, 'd', 3, 9),

    (15, 'Nemo','1','d', 4, 8),
    (16, 'Dori','2','w', 4, 6),
    (17, 'Marlin','2','m', 4, 0),
    (18, 'Flipper','7','m', 4, 3);
