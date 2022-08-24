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
    (9, 'frech');
