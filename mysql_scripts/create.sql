CREATE DATABASE IF NOT EXISTS db;

USE db;

CREATE TABLE IF NOT EXISTS Users (
	UserId INT NOT NULL AUTO_INCREMENT,
	Username VARCHAR(255) NOT NULL,
	Email VARCHAR(255) NOT NULL,
	Dashboard JSON,
	PRIMARY KEY (UserId)
);

INSERT INTO Users (Username, Email, Dashboard) VALUES ("AlexHorejsi", "alex.horejsi59@gmail.com", NULL);
INSERT INTO Users (Username, Email, Dashboard) VALUES ("MisaelCorvera", "mcmalfaro140@gmail.com", NULL);
INSERT INTO Users (Username, Email, Dashboard) VALUES ("YiWang", "superhotdogzz@gmail.com", NULL);
INSERT INTO Users (Username, Email, Dashboard) VALUES ("ZacYou", "zacyou151@yahoo.com", NULL);
INSERT INTO Users (Username, Email, Dashboard) VALUES ("JatDida", "didajateni@gmail.com", NULL);
