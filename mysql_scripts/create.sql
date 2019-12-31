CREATE DATABASE IF NOT EXISTS db;

USE db;

CREATE TABLE IF NOT EXISTS Users (
	UserId INT NOT NULL AUTO_INCREMENT,
	Username text NOT NULL,
    Password text NOT NULL,
	Email text NOT NULL,
	Dashboard longtext NOT NULL,
	PRIMARY KEY (UserId)
);


INSERT INTO Users (Username, Password, Email, Dashboard) VALUES ("AlexHorejsi","test1", "alex.horejsi59@gmail.com", "{}");
INSERT INTO Users (Username, Password, Email, Dashboard) VALUES ("MisaelCorvera","test2", "mcmalfaro140@gmail.com", "{}");
INSERT INTO Users (Username, Password, Email, Dashboard) VALUES ("YiWang","test3", "superhotdogzz@gmail.com", "{}");
INSERT INTO Users (Username, Password, Email, Dashboard) VALUES ("ZacYou","test4", "zacyou151@yahoo.com", "{}");
INSERT INTO Users (Username, Password, Email, Dashboard) VALUES ("JatDida","test5", "didajateni@gmail.com", "{}");

