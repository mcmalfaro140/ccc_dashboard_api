DROP DATABASE IF EXISTS db;
CREATE DATABASE db;
USE db;

CREATE TABLE Users (
	UserId INT NOT NULL AUTO_INCREMENT,
	Username VARCHAR(50) NOT NULL,
    Password TEXT NOT NULL,
	Email VARCHAR(255) NOT NULL,
	Dashboard LONGTEXT NOT NULL,
	PRIMARY KEY (UserId),
	UNIQUE (Username),
	UNIQUE (Email)
);

CREATE TABLE SNSTopics (
	SNSTopicId INT NOT NULL AUTO_INCREMENT,
	TopicName VARCHAR(50) NOT NULL,
	TopicArn VARCHAR(255) NOT NULL,
	PRIMARY KEY (SNSTopicId),
	UNIQUE (TopicName),
	UNIQUE (TopicArn)
);

CREATE TABLE LogGroups (
	LogGroupId INT NOT NULL AUTO_INCREMENT,
	Name VARCHAR(255) NOT NULL,
	PRIMARY KEY (LogGroupId),
	UNIQUE (Name)
);

CREATE TABLE Keywords (
	KeywordId INT NOT NULL AUTO_INCREMENT,
	Word VARCHAR(70),
	PRIMARY KEY (KeywordId),
	UNIQUE (Word)
);

CREATE TABLE LogLevelCriteria (
	LogLevelCriteriaId INT NOT NULL AUTO_INCREMENT,
	LogLevel VARCHAR(5) NOT NULL CHECK (LogLevel IN ('TRACE', 'DEBUG', 'INFO', 'WARN', 'ERROR')),
	Comparison VARCHAR(2) CHECK (Comparison IN ('==', '>', '>=')),
	PRIMARY KEY (LogLevelCriteriaId),
	UNIQUE (LogLevel, Comparison)
);

CREATE TABLE LogAlarms (
	LogAlarmId INT NOT NULL AUTO_INCREMENT,
	LogLevelCriteriaId INT NOT NULL,
	AlarmName VARCHAR(255) NOT NULL,
	KeywordRelationship VARCHAR(3) CHECK (KeywordRelationship IN ('AND', 'OR', NULL)),
	PRIMARY KEY (LogAlarmId),
	FOREIGN KEY (LogLevelCriteriaId) REFERENCES LogLevelCriteria(LogLevelCriteriaId),
	UNIQUE (AlarmName)
);

CREATE TABLE XRefUserLogAlarm (
	UserLogAlarmId INT NOT NULL AUTO_INCREMENT,
	UserId INT NOT NULL,
	LogAlarmId INT NOT NULL,
	PRIMARY KEY (UserLogAlarmId),
	FOREIGN KEY (UserId) REFERENCES Users(UserId),
	FOREIGN KEY (LogAlarmId) REFERENCES LogAlarms(LogAlarmId),
	UNIQUE (UserId, LogAlarmId)
);

CREATE TABLE XRefLogAlarmLogGroup (
	LogAlarmLogGroupId INT NOT NULL AUTO_INCREMENT,
	LogAlarmId INT NOT NULL,
	LogGroupId INT NOT NULL,
	PRIMARY KEY (LogAlarmLogGroupId),
	FOREIGN KEY (LogAlarmId) REFERENCES LogAlarms(LogAlarmId),
	FOREIGN KEY (LogGroupId) REFERENCES LogGroups(LogGroupId),
	UNIQUE (LogAlarmId, LogGroupId)
);

CREATE TABLE XRefLogAlarmKeyword (
	LogAlarmKeywordId INT NOT NULL AUTO_INCREMENT,
	LogAlarmId INT NOT NULL,
	KeywordId INT NOT NULL, 
	PRIMARY KEY (LogAlarmKeywordId),
	FOREIGN KEY (LogAlarmId) REFERENCES LogAlarms(LogAlarmId),
	FOREIGN KEY (KeywordId) REFERENCES Keywords(KeywordId),
	UNIQUE (LogAlarmId, KeywordId)
);

CREATE TABLE XRefLogAlarmSNSTopic (
	LogAlarmSNSTopicId INT NOT NULL AUTO_INCREMENT,
	LogAlarmId INT NOT NULL,
	SNSTopicId INT NOT NULL,
	PRIMARY KEY (LogAlarmSNSTopicId),
	FOREIGN KEY (LogAlarmId) REFERENCES LogAlarms(LogAlarmId),
	FOREIGN KEY (SNSTopicId) REFERENCES SNSTopics(SNSTopicId),
	UNIQUE (LogAlarmId, SNSTopicId)
);
