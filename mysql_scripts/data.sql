INSERT INTO Users (Username, Password, Dashboard) VALUES ('AlexHorejsi', 'test1', '{}');
INSERT INTO Users (Username, Password, Dashboard) VALUES ('MisaelCorvera', 'test2', '{}');
INSERT INTO Users (Username, Password, Dashboard) VALUES ('YiWang', 'test3', '{}');
INSERT INTO Users (Username, Password, Dashboard) VALUES ('ZacYou', 'test4', '{}');
INSERT INTO Users (Username, Password, Dashboard) VALUES ('JayDida', 'test5', '{}');

INSERT INTO SNSTopics (TopicName, TopicArn) VALUES ('Alex_SNS', 'arn:aws:sns:us-west-1:155103565385:Alex_SNS');
INSERT INTO SNSTopics (TopicName, TopicArn) VALUES ('Misael_SNS', 'arn:aws:sns:us-west-1:155103565385:Misael_SNS');
INSERT INTO SNSTopics (TopicName, TopicArn) VALUES ('Zac_SNS', 'arn:aws:sns:us-west-1:155103565385:Zac_SNS');

INSERT INTO LogGroups (Name) VALUES ('test');
INSERT INTO LogGroups (Name) VALUES ('test2');
INSERT INTO LogGroups (Name) VALUES ('test3');
INSERT INTO LogGroups (Name) VALUES ('test4');

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

INSERT INTO XRefLogAlarmKeyword (LogAlarmId, KeywordId) VALUES (1, 1);
INSERT INTO XRefLogAlarmKeyword (LogAlarmId, KeywordId) VALUES (2, 2);
INSERT INTO XRefLogAlarmKeyword (LogAlarmId, KeywordId) VALUES (3, 3);
INSERT INTO XRefLogAlarmKeyword (LogAlarmId, KeywordId) VALUES (1, 3);
INSERT INTO XRefLogAlarmKeyword (LogAlarmId, KeywordId) VALUES (3, 2);

INSERT INTO XRefLogAlarmSNSTopic (LogAlarmId, SNSTopicId, UserId) VALUES (1, 1, 1);
INSERT INTO XRefLogAlarmSNSTopic (LogAlarmId, SNSTopicId, UserId) VALUES (2, 2, 2);
INSERT INTO XRefLogAlarmSNSTopic (LogAlarmId, SNSTopicId, UserId) VALUES (3, 3, 3);
INSERT INTO XRefLogAlarmSNSTopic (LogAlarmId, SNSTopicId, UserId) VALUES (4, 2, 4);
INSERT INTO XRefLogAlarmSNSTopic (LogAlarmId, SNSTopicId, UserId) VALUES (4, 3, 1);
INSERT INTO XRefLogAlarmSNSTopic (LogAlarmId, SNSTopicId, UserId) VALUES (3, 2, 2);
INSERT INTO XRefLogAlarmSNSTopic (LogAlarmId, SNSTopicId, UserId) VALUES (1, 2, 3);
INSERT INTO XRefLogAlarmSNSTopic (LogAlarmId, SNSTopicId, UserId) VALUES (1, 1, 2);


--misael
Use db;
INSERT INTO Users (Username, Password, Dashboard) VALUES ('AlexHorejsi', 'test1', '{}');
INSERT INTO Users (Username, Password, Dashboard) VALUES ('MisaelCorvera', 'test2', '{}');
INSERT INTO Users (Username, Password, Dashboard) VALUES ('YiWang', 'test3', '{}');
INSERT INTO Users (Username, Password, Dashboard) VALUES ('ZacYou', 'test4', '{}');
INSERT INTO Users (Username, Password, Dashboard) VALUES ('JayDida', 'test5', '{}');

INSERT INTO LogGroups (Name) VALUES ('test');
INSERT INTO SNSTopics (TopicName, TopicArn) VALUES ('EmailTopic', 'arn:aws:sns:us-east-1:112911356528:EmailTopic');
INSERT INTO Keywords (Word) VALUES (NULL);
INSERT INTO LogAlarms (LogLevel, Comparison, AlarmName, KeywordRelationship) VALUES ('ERROR', '==', 'alarm4', NULL);

INSERT INTO XRefLogAlarmLogGroup (LogAlarmId, LogGroupId) VALUES (1, 1);
INSERT INTO XRefLogAlarmSNSTopic (LogAlarmId, SNSTopicId) VALUES (1, 1);
INSERT INTO XRefUserLogAlarm (UserId, LogAlarmId) VALUES (1, 1);
INSERT INTO XRefLogAlarmKeyword (LogAlarmId, KeywordId) VALUES (1, 1);
