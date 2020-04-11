INSERT INTO Users (Username, Password, Email, Dashboard) VALUES ('AlexHorejsi', 'test1', 'alex.horejsi59@gmail.com', '{}');
INSERT INTO Users (Username, Password, Email, Dashboard) VALUES ('MisaelCorvera', 'test2', 'mcmalfaro140@gmail.com', '{}');
INSERT INTO Users (Username, Password, Email, Dashboard) VALUES ('YiWang', 'test3', 'superhotdogzz@gmail.com', '{}');
INSERT INTO Users (Username, Password, Email, Dashboard) VALUES ('ZacYou', 'test4', 'zacyou151@yahoo.com', '{}');
INSERT INTO Users (Username, Password, Email, Dashboard) VALUES ('JayDida', 'test5', 'didajateni@gmail.com', '{}');

INSERT INTO SNSTopics (TopicName, TopicArn) VALUES ('EmailTopic', 'arn:aws:sns:us-east-1:112911356528:EmailTopic');
INSERT INTO SNSTopics (TopicName, TopicArn) VALUES ('PhoneTopic', 'arn:aws:sns:us-east-1:112911356528:PhoneTopic');
INSERT INTO SNSTopics (TopicName, TopicArn) VALUES ('DualTopic', 'arn:aws:sns:us-east-1:112911356528:DualTopic');

INSERT INTO LogGroups (Name) VALUES ('test');
INSERT INTO LogGroups (Name) VALUES ('test2');
INSERT INTO LogGroups (Name) VALUES ('test3');
INSERT INTO LogGroups (Name) VALUES ('test4');

INSERT INTO Keywords (Word) VALUES (NULL);
INSERT INTO Keywords (Word) VALUES ('message1');
INSERT INTO Keywords (Word) VALUES ('message2');
INSERT INTO Keywords (Word) VALUES ('message3');

INSERT INTO LogAlarms (LogLevel, Comparison, AlarmName, KeywordRelationship) VALUES ('WARN', '==', 'alarm', 'ANY');
INSERT INTO LogAlarms (LogLevel, Comparison, AlarmName, KeywordRelationship) VALUES ('WARN', '>=', 'alarm2', 'ANY');
INSERT INTO LogAlarms (LogLevel, Comparison, AlarmName, KeywordRelationship) VALUES ('INFO', '>=', 'alarm3', 'ALL');
INSERT INTO LogAlarms (LogLevel, Comparison, AlarmName, KeywordRelationship) VALUES ('ERROR', '==', 'alarm4', NULL);

INSERT INTO XRefLogAlarmLogGroup (LogAlarmId, LogGroupId) VALUES (1, 1);
INSERT INTO XRefLogAlarmLogGroup (LogAlarmId, LogGroupId) VALUES (2, 2);
INSERT INTO XRefLogAlarmLogGroup (LogAlarmId, LogGroupId) VALUES (3, 3);
INSERT INTO XRefLogAlarmLogGroup (LogAlarmId, LogGroupId) VALUES (4, 1);

INSERT INTO XRefLogAlarmKeyword (LogAlarmId, KeywordId) VALUES (1, 2);
INSERT INTO XRefLogAlarmKeyword (LogAlarmId, KeywordId) VALUES (2, 3);
INSERT INTO XRefLogAlarmKeyword (LogAlarmId, KeywordId) VALUES (3, 4);
INSERT INTO XRefLogAlarmKeyword (LogAlarmId, KeywordId) VALUES (1, 4);
INSERT INTO XRefLogAlarmKeyword (LogAlarmId, KeywordId) VALUES (4, 1);
INSERT INTO XRefLogAlarmKeyword (LogAlarmId, KeywordId) VALUES (3, 3);

INSERT INTO XRefUserLogAlarm (UserId, LogAlarmId) VALUES (1, 1);
INSERT INTO XRefUserLogAlarm (UserId, LogAlarmId) VALUES (2, 2);
INSERT INTO XRefUserLogAlarm (UserId, LogAlarmId) VALUES (3, 3);
INSERT INTO XRefUserLogAlarm (UserId, LogAlarmId) VALUES (4, 4);
INSERT INTO XRefUserLogAlarm (UserId, LogAlarmId) VALUES (1, 4);
INSERT INTO XRefUserLogAlarm (UserId, LogAlarmId) VALUES (2, 3);
INSERT INTO XRefUserLogAlarm (UserId, LogAlarmId) VALUES (3, 1);

INSERT INTO XRefLogAlarmSNSTopic (LogAlarmId, SNSTopicId) VALUES (1, 1);
INSERT INTO XRefLogAlarmSNSTopic (LogAlarmId, SNSTopicId) VALUES (2, 2);
INSERT INTO XRefLogAlarmSNSTopic (LogAlarmId, SNSTopicId) VALUES (3, 3);
INSERT INTO XRefLogAlarmSNSTopic (LogAlarmId, SNSTopicId) VALUES (4, 2);
INSERT INTO XRefLogAlarmSNSTopic (LogAlarmId, SNSTopicId) VALUES (4, 3);
INSERT INTO XRefLogAlarmSNSTopic (LogAlarmId, SNSTopicId) VALUES (1, 2);
INSERT INTO XRefLogAlarmSNSTopic (LogAlarmId, SNSTopicId) VALUES (3, 2);

INSERT INTO Assigners (UserId, LogAlarmSNSTopicId) VALUES (1, 1);
INSERT INTO Assigners (UserId, LogAlarmSNSTopicId) VALUES (2, 2);
INSERT INTO Assigners (UserId, LogAlarmSNSTopicId) VALUES (3, 3);
INSERT INTO Assigners (UserId, LogAlarmSNSTopicId) VALUES (4, 4);
INSERT INTO Assigners (UserId, LogAlarmSNSTopicId) VALUES (1, 5);
INSERT INTO Assigners (UserId, LogAlarmSNSTopicId) VALUES (1, 6);
INSERT INTO Assigners (UserId, LogAlarmSNSTopicId) VALUES (2, 7);


--misael
Use db;
INSERT INTO Users (Username, Password, Email, Dashboard) VALUES ('AlexHorejsi', 'test1', 'alex9@gmail.com', '{}');
INSERT INTO Users (Username, Password, Email, Dashboard) VALUES ('MisaelCorvera', 'test2', 'mcmal@gmail.com', '{}');
INSERT INTO Users (Username, Password, Email, Dashboard) VALUES ('YiWang', 'test3', 'super@gmail.com', '{}');
INSERT INTO Users (Username, Password, Email, Dashboard) VALUES ('ZacYou', 'test4', 'zac@yahoo.com', '{}');
INSERT INTO Users (Username, Password, Email, Dashboard) VALUES ('JayDida', 'test5', 'dida@gmail.com', '{}');

INSERT INTO LogGroups (Name) VALUES ('test');
INSERT INTO SNSTopics (TopicName, TopicArn) VALUES ('EmailTopic', 'arn:aws:sns:us-east-1:112911356528:EmailTopic');
INSERT INTO Keywords (Word) VALUES (NULL);
INSERT INTO LogAlarms (LogLevel, Comparison, AlarmName, KeywordRelationship) VALUES ('ERROR', '==', 'alarm4', NULL);

INSERT INTO XRefLogAlarmLogGroup (LogAlarmId, LogGroupId) VALUES (1, 1);
INSERT INTO XRefLogAlarmSNSTopic (LogAlarmId, SNSTopicId) VALUES (1, 1);
INSERT INTO XRefUserLogAlarm (UserId, LogAlarmId) VALUES (1, 1);
INSERT INTO XRefLogAlarmKeyword (LogAlarmId, KeywordId) VALUES (1, 1);
