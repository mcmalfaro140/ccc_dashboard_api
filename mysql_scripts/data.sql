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

INSERT INTO LogLevelCriteria (LogLevel, Comparison) VALUES ('WARN', '==');
INSERT INTO LogLevelCriteria (LogLevel, Comparison) VALUES ('WARN', '>=');
INSERT INTO LogLevelCriteria (LogLevel, Comparison) VALUES ('INFO', '>=');
INSERT INTO LogLevelCriteria (LogLevel, Comparison) VALUES ('ERROR', '==');

INSERT INTO LogAlarms (LogLevelCriteriaId, AlarmName, KeywordRelationship) VALUES (1, 'alarm', 'OR');
INSERT INTO LogAlarms (LogLevelCriteriaId, AlarmName, KeywordRelationship) VALUES (2, 'alarm2', 'OR');
INSERT INTO LogAlarms (LogLevelCriteriaId, AlarmName, KeywordRelationship) VALUES (3, 'alarm3', 'AND');
INSERT INTO LogAlarms (LogLevelCriteriaId, AlarmName, KeywordRelationship) VALUES (4, 'alarm4', NULL);

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

INSERT INTO XRefLogAlarmSNSTopic (LogAlarmId, SNSTopicId) VALUES (1, 1);
INSERT INTO XRefLogAlarmSNSTopic (LogAlarmId, SNSTopicId) VALUES (2, 2);
INSERT INTO XRefLogAlarmSNSTopic (LogAlarmId, SNSTopicId) VALUES (3, 3);
INSERT INTO XRefLogAlarmSNSTopic (LogAlarmId, SNSTopicId) VALUES (1, 2);
INSERT INTO XRefLogAlarmSNSTopic (LogAlarmId, SNSTopicId) VALUES (2, 3);
INSERT INTO XRefLogAlarmSNSTopic (LogAlarmId, SNSTopicId) VALUES (3, 1);

INSERT INTO XRefUserLogAlarm (UserId, LogAlarmId) VALUES (1, 1);
INSERT INTO XRefUserLogAlarm (UserId, LogAlarmId) VALUES (2, 2);
INSERT INTO XRefUserLogAlarm (UserId, LogAlarmId) VALUES (3, 3);
INSERT INTO XRefUserLogAlarm (UserId, LogAlarmId) VALUES (4, 4);
