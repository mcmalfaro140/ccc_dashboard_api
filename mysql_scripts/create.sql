CREATE DATABASE IF NOT EXISTS db;

USE db;

CREATE TABLE IF NOT EXISTS Users (
	Id INT NOT NULL AUTO_INCREMENT,
	Username VARCHAR(255) NOT NULL,
	Email VARCHAR(255) NOT NULL,
	Dashboard JSON,
	PRIMARY KEY (Id)
);

INSERT INTO user (user_id, Username, Email, Dashboard) VALUES (1,"AlexHorejsi", "alex.horejsi59@gmail.com", NULL);
INSERT INTO user (user_id,Username, Email, Dashboard) VALUES (2,"MisaelCorvera", "mcmalfaro140@gmail.com", NULL);
INSERT INTO user (user_id,Username, Email, Dashboard) VALUES (3,"YiWang", "superhotdogzz@gmail.com", NULL);
INSERT INTO user (user_id,Username, Email, Dashboard) VALUES (4,"ZacYou", "zacyou151@yahoo.com", NULL);
INSERT INTO user (user_id,Username, Email, Dashboard) VALUES (5,"JatDida", "didajateni@gmail.com", NULL);
