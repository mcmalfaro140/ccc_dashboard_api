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

CREATE TABLE LogAlarms (
	LogAlarmId INT NOT NULL AUTO_INCREMENT,
	LogLevel VARCHAR(5) NOT NULL CHECK (LogLevel IN ('TRACE', 'DEBUG', 'INFO', 'WARN', 'ERROR')),
	Comparison VARCHAR(2) CHECK (Comparison IN ('==', '<', '<=', '>', '>=')),
	AlarmName VARCHAR(255) NOT NULL,
	KeywordRelationship CHAR(3) CHECK (KeywordRelationship IN ('ANY', 'ALL', NULL)),
	PRIMARY KEY (LogAlarmId),
	UNIQUE (AlarmName)
);

<<<<<<< HEAD
CREATE TABLE MetricAlarms (
	MetricAlarmId INT NOT NULL AUTO_INCREMENT,
	AlarmArn VARCHAR(100) NOT NULL,
	PRIMARY KEY (MetricAlarmId),
	UNIQUE (AlarmArn)
);

=======
-- not created
>>>>>>> MetricAlarmController
CREATE TABLE XRefUserLogAlarm (
	UserLogAlarmId INT NOT NULL AUTO_INCREMENT,
	UserId INT NOT NULL,
	LogAlarmId INT NOT NULL,
	PRIMARY KEY (UserLogAlarmId),
	FOREIGN KEY (UserId) REFERENCES Users(UserId) ON DELETE CASCADE,
	FOREIGN KEY (LogAlarmId) REFERENCES LogAlarms(LogAlarmId) ON DELETE CASCADE,
	UNIQUE (UserId, LogAlarmId)
);

<<<<<<< HEAD
CREATE TABLE XRefUserMetricAlarm (
	UserMetricAlarmId INT NOT NULL AUTO_INCREMENT,
	UserId INT NOT NULL,
	MetricAlarmId INT NOT NULL,
	PRIMARY KEY (UserMetricAlarmId),
	FOREIGN KEY (UserId) REFERENCES Users(UserId) ON DELETE CASCADE,
	FOREIGN KEY (MetricAlarmId) REFERENCES MetricAlarms(MetricAlarmId) ON DELETE CASCADE,
	UNIQUE (UserId, MetricAlarmId)
);

=======
--not created
>>>>>>> MetricAlarmController
CREATE TABLE XRefLogAlarmLogGroup (
	LogAlarmLogGroupId INT NOT NULL AUTO_INCREMENT,
	LogAlarmId INT NOT NULL,
	LogGroupId INT NOT NULL,
	PRIMARY KEY (LogAlarmLogGroupId),
	FOREIGN KEY (LogAlarmId) REFERENCES LogAlarms(LogAlarmId) ON DELETE CASCADE,
	FOREIGN KEY (LogGroupId) REFERENCES LogGroups(LogGroupId) ON DELETE CASCADE,
	UNIQUE (LogAlarmId, LogGroupId)
);

CREATE TABLE XRefLogAlarmKeyword (
	LogAlarmKeywordId INT NOT NULL AUTO_INCREMENT,
	LogAlarmId INT NOT NULL,
	KeywordId INT NOT NULL,
	PRIMARY KEY (LogAlarmKeywordId),
	FOREIGN KEY (LogAlarmId) REFERENCES LogAlarms(LogAlarmId) ON DELETE CASCADE,
	FOREIGN KEY (KeywordId) REFERENCES Keywords(KeywordId) ON DELETE CASCADE,
	UNIQUE (LogAlarmId, KeywordId)
);

CREATE TABLE XRefLogAlarmSNSTopic (
	LogAlarmSNSTopicId INT NOT NULL AUTO_INCREMENT,
	LogAlarmId INT NOT NULL,
	SNSTopicId INT NOT NULL,
	PRIMARY KEY (LogAlarmSNSTopicId),
	FOREIGN KEY (LogAlarmId) REFERENCES LogAlarms(LogAlarmId) ON DELETE CASCADE,
	FOREIGN KEY (SNSTopicId) REFERENCES SNSTopics(SNSTopicId) ON DELETE CASCADE,
	UNIQUE (LogAlarmId, SNSTopicId)
<<<<<<< HEAD
);
=======
	);
>>>>>>> MetricAlarmController
