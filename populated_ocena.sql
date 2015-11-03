-- MySQL dump 10.13  Distrib 5.6.19, for linux-glibc2.5 (x86_64)
--
-- Host: localhost    Database: ocena
-- ------------------------------------------------------
-- Server version	5.6.27

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `admission`
--

DROP TABLE IF EXISTS `admission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admission` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `admission` varchar(60) NOT NULL,
  `active` tinyint(1) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `admission_UNIQUE` (`admission`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admission`
--

LOCK TABLES `admission` WRITE;
/*!40000 ALTER TABLE `admission` DISABLE KEYS */;
INSERT INTO `admission` VALUES (1,'Undergraduate',1,NULL),(2,'Graduate',1,NULL);
/*!40000 ALTER TABLE `admission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `assessed_evaluation`
--

DROP TABLE IF EXISTS `assessed_evaluation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `assessed_evaluation` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `question_description` varchar(200) DEFAULT NULL,
  `evaluated_question` int(10) unsigned NOT NULL,
  `question_category` smallint(3) unsigned NOT NULL,
  `rating` varchar(10) DEFAULT NULL,
  `percentage_score` varchar(20) DEFAULT NULL,
  `evaluation_session` int(10) unsigned NOT NULL,
  `course_of_session` int(10) unsigned NOT NULL,
  `version` int(11) DEFAULT NULL,
  `active` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_assessed_evaluation_evaluated_question1_idx` (`evaluated_question`),
  KEY `fk_assessed_evaluation_question_category1_idx` (`question_category`),
  KEY `fk_assessed_evaluation_evaluation_session1_idx` (`evaluation_session`),
  KEY `fk_assessed_evaluation_course_of_session1_idx` (`course_of_session`),
  CONSTRAINT `fk_assessed_evaluation_course_of_session1` FOREIGN KEY (`course_of_session`) REFERENCES `course_of_session` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_assessed_evaluation_evaluated_question1` FOREIGN KEY (`evaluated_question`) REFERENCES `evaluated_question` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_assessed_evaluation_evaluation_session1` FOREIGN KEY (`evaluation_session`) REFERENCES `evaluation_session` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_assessed_evaluation_question_category1` FOREIGN KEY (`question_category`) REFERENCES `question_category` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=181 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `assessed_evaluation`
--

LOCK TABLES `assessed_evaluation` WRITE;
/*!40000 ALTER TABLE `assessed_evaluation` DISABLE KEYS */;
INSERT INTO `assessed_evaluation` VALUES (1,'Clarity of course objectives (Classroom/Clinical)',1,1,'0.0',NULL,1,1,NULL,1),(2,'Achievement of course objectives',2,1,'2.0',NULL,1,1,NULL,1),(3,'Relevance of course to programme objectives',3,1,'3.5',NULL,1,1,NULL,1),(4,'Interpretation of concepts and theories',4,2,'3.5',NULL,1,1,NULL,1),(5,'Coverage of course syllabus',5,2,'5.0',NULL,1,1,NULL,1),(6,'Clarity in presentation',6,2,'3.5',NULL,1,1,NULL,1),(7,'Effectiveness of presentation methods',7,2,'4.0',NULL,1,1,NULL,1),(8,'Sufficiency of handouts',8,3,'4.5',NULL,1,1,NULL,1),(9,'Value of recommended resource materials',9,3,'4.5',NULL,1,1,NULL,1),(10,'Use of audio-visual and other teaching aids',10,3,'4.0',NULL,1,1,NULL,1),(11,'Guidance on the use of web based material/journals',11,3,'4.5',NULL,1,1,NULL,1),(12,'Adequacy of physical facilities',12,3,'4.5',NULL,1,1,NULL,1),(13,'Sufficiency of computer(ICT) facility',13,3,'4.0',NULL,1,1,NULL,1),(14,'Relevance of laboratory experiment(if any)',14,3,'5.0',NULL,1,1,NULL,1),(15,'Relevance and usefulness of assignments/practicals/CATs',15,4,'4.5',NULL,1,1,NULL,1),(16,'Appropriate coursework assessement',16,4,'4.5',NULL,1,1,NULL,1),(17,'Satisfaction with methods of evaluation for classroom theory',17,4,'5.0',NULL,1,1,NULL,1),(18,'Satisfaction with methods of assessment for practicals',18,4,'4.5',NULL,1,1,NULL,1),(19,'Attends class regularly',19,5,'4.5',NULL,1,1,NULL,1),(20,'Keeps to the published timetable',20,5,'5.0',NULL,1,1,NULL,1),(21,'Is available for consultation when necessary(outside class time)',21,5,'4.0',NULL,1,1,NULL,1),(22,'Guidance in practical lessons(e.g. Nursing)',22,5,'4.5',NULL,1,1,NULL,1),(23,'Explains the scope, recommended readings, delivery and evaluation methodology of the course',23,6,'4.5',NULL,1,1,NULL,1),(24,'Uses organized, up-to-date notes and course materials',24,6,'4.5',NULL,1,1,NULL,1),(25,'Manages time well(punctual, uses class time efficiently)',25,6,'4.0',NULL,1,1,NULL,1),(26,'Demonstration of procedures in the practical sessions',26,6,'4.0',NULL,1,1,NULL,1),(27,'Presents course concepts and theories in a clear and interesting way',27,7,'3.5',NULL,1,1,NULL,1),(28,'Facilitates meaningful and active class participation by students',28,7,'4.5',NULL,1,1,NULL,1),(29,'Answers questions clearly and knowledgeably',29,7,'4.5',NULL,1,1,NULL,1),(30,'Uses relevant examples and illustrations in the class/practical',30,7,'3.5',NULL,1,1,NULL,1),(31,'Is open to diverse viewpoints and opinions',31,7,'4.5',NULL,1,1,NULL,1),(32,'Gives relevant and challenging assignments and tests',32,8,'4.5',NULL,1,1,NULL,1),(33,'Marks assignments and tests promptly',33,8,'3.5',NULL,1,1,NULL,1),(34,'Gives helpful feedback on assignments and tests',34,8,'3.5',NULL,1,1,NULL,1),(35,'Use of case studies for analysis',35,8,'4.0',NULL,1,1,NULL,1),(36,'Overall rating of lecturer in the classroom/during lecture',36,9,'4.5',NULL,1,1,NULL,1),(37,'Overall rating of the lecturer during practical/clinical sessions',37,9,'4.0',NULL,1,1,NULL,1),(38,'Would you recommend this course to any other group of students?',38,11,NULL,'100.0%',1,1,NULL,1),(39,'What proportions of classes do you attend?',39,10,NULL,'93.5%',1,1,NULL,1),(40,'In a normal class/practical sesison, what portion of class members are present?',40,10,NULL,'87.0%',1,1,NULL,1),(41,'What are the reasons for the level of attendance you have reported above?',41,10,NULL,NULL,1,1,NULL,1),(42,'What is your overall view of the course?',42,11,NULL,NULL,1,1,NULL,1),(43,'Your lecturer would like to know if there are certain aspects you believe he/she has done especially well in his/her teaching of this course?',43,11,NULL,NULL,1,1,NULL,1),(44,'Your lecturer would also like to know what specific things you believe must be done to improvehis/her teaching in the course?',44,11,NULL,NULL,1,1,NULL,1),(45,'What other materials or resources, if any, should be added to enhance understanding of the course contents?',45,11,NULL,NULL,1,1,NULL,1),(46,'Clarity of course objectives (Classroom/Clinical)',1,1,'2.0',NULL,1,2,NULL,1),(47,'Achievement of course objectives',2,1,'2.5',NULL,1,2,NULL,1),(48,'Relevance of course to programme objectives',3,1,'3.0',NULL,1,2,NULL,1),(49,'Interpretation of concepts and theories',4,2,'4.0',NULL,1,2,NULL,1),(50,'Coverage of course syllabus',5,2,'4.0',NULL,1,2,NULL,1),(51,'Clarity in presentation',6,2,'4.0',NULL,1,2,NULL,1),(52,'Effectiveness of presentation methods',7,2,'4.5',NULL,1,2,NULL,1),(53,'Sufficiency of handouts',8,3,'3.5',NULL,1,2,NULL,1),(54,'Value of recommended resource materials',9,3,'3.5',NULL,1,2,NULL,1),(55,'Use of audio-visual and other teaching aids',10,3,'4.0',NULL,1,2,NULL,1),(56,'Guidance on the use of web based material/journals',11,3,'4.5',NULL,1,2,NULL,1),(57,'Adequacy of physical facilities',12,3,'3.5',NULL,1,2,NULL,1),(58,'Sufficiency of computer(ICT) facility',13,3,'3.5',NULL,1,2,NULL,1),(59,'Relevance of laboratory experiment(if any)',14,3,'3.5',NULL,1,2,NULL,1),(60,'Relevance and usefulness of assignments/practicals/CATs',15,4,'3.0',NULL,1,2,NULL,1),(61,'Appropriate coursework assessement',16,4,'4.0',NULL,1,2,NULL,1),(62,'Satisfaction with methods of evaluation for classroom theory',17,4,'3.5',NULL,1,2,NULL,1),(63,'Satisfaction with methods of assessment for practicals',18,4,'4.0',NULL,1,2,NULL,1),(64,'Attends class regularly',19,5,'3.0',NULL,1,2,NULL,1),(65,'Keeps to the published timetable',20,5,'3.5',NULL,1,2,NULL,1),(66,'Is available for consultation when necessary(outside class time)',21,5,'5.0',NULL,1,2,NULL,1),(67,'Guidance in practical lessons(e.g. Nursing)',22,5,'3.0',NULL,1,2,NULL,1),(68,'Explains the scope, recommended readings, delivery and evaluation methodology of the course',23,6,'3.5',NULL,1,2,NULL,1),(69,'Uses organized, up-to-date notes and course materials',24,6,'4.0',NULL,1,2,NULL,1),(70,'Manages time well(punctual, uses class time efficiently)',25,6,'3.0',NULL,1,2,NULL,1),(71,'Demonstration of procedures in the practical sessions',26,6,'4.5',NULL,1,2,NULL,1),(72,'Presents course concepts and theories in a clear and interesting way',27,7,'5.0',NULL,1,2,NULL,1),(73,'Facilitates meaningful and active class participation by students',28,7,'4.0',NULL,1,2,NULL,1),(74,'Answers questions clearly and knowledgeably',29,7,'4.0',NULL,1,2,NULL,1),(75,'Uses relevant examples and illustrations in the class/practical',30,7,'4.0',NULL,1,2,NULL,1),(76,'Is open to diverse viewpoints and opinions',31,7,'3.0',NULL,1,2,NULL,1),(77,'Gives relevant and challenging assignments and tests',32,8,'4.5',NULL,1,2,NULL,1),(78,'Marks assignments and tests promptly',33,8,'5.0',NULL,1,2,NULL,1),(79,'Gives helpful feedback on assignments and tests',34,8,'4.5',NULL,1,2,NULL,1),(80,'Use of case studies for analysis',35,8,'4.0',NULL,1,2,NULL,1),(81,'Overall rating of lecturer in the classroom/during lecture',36,9,'5.0',NULL,1,2,NULL,1),(82,'Overall rating of the lecturer during practical/clinical sessions',37,9,'4.5',NULL,1,2,NULL,1),(83,'Would you recommend this course to any other group of students?',38,11,NULL,'100.0%',1,2,NULL,1),(84,'What proportions of classes do you attend?',39,10,NULL,'87.0%',1,2,NULL,1),(85,'In a normal class/practical sesison, what portion of class members are present?',40,10,NULL,'87.0%',1,2,NULL,1),(86,'What are the reasons for the level of attendance you have reported above?',41,10,NULL,NULL,1,2,NULL,1),(87,'What is your overall view of the course?',42,11,NULL,NULL,1,2,NULL,1),(88,'Your lecturer would like to know if there are certain aspects you believe he/she has done especially well in his/her teaching of this course?',43,11,NULL,NULL,1,2,NULL,1),(89,'Your lecturer would also like to know what specific things you believe must be done to improvehis/her teaching in the course?',44,11,NULL,NULL,1,2,NULL,1),(90,'What other materials or resources, if any, should be added to enhance understanding of the course contents?',45,11,NULL,NULL,1,2,NULL,1),(91,'Clarity of course objectives (Classroom/Clinical)',1,1,'0.0',NULL,1,3,NULL,1),(92,'Achievement of course objectives',2,1,'2.0',NULL,1,3,NULL,1),(93,'Relevance of course to programme objectives',3,1,'3.0',NULL,1,3,NULL,1),(94,'Interpretation of concepts and theories',4,2,'4.0',NULL,1,3,NULL,1),(95,'Coverage of course syllabus',5,2,'4.5',NULL,1,3,NULL,1),(96,'Clarity in presentation',6,2,'4.5',NULL,1,3,NULL,1),(97,'Effectiveness of presentation methods',7,2,'4.0',NULL,1,3,NULL,1),(98,'Sufficiency of handouts',8,3,'5.0',NULL,1,3,NULL,1),(99,'Value of recommended resource materials',9,3,'4.0',NULL,1,3,NULL,1),(100,'Use of audio-visual and other teaching aids',10,3,'4.5',NULL,1,3,NULL,1),(101,'Guidance on the use of web based material/journals',11,3,'4.0',NULL,1,3,NULL,1),(102,'Adequacy of physical facilities',12,3,'5.0',NULL,1,3,NULL,1),(103,'Sufficiency of computer(ICT) facility',13,3,'4.5',NULL,1,3,NULL,1),(104,'Relevance of laboratory experiment(if any)',14,3,'4.5',NULL,1,3,NULL,1),(105,'Relevance and usefulness of assignments/practicals/CATs',15,4,'4.5',NULL,1,3,NULL,1),(106,'Appropriate coursework assessement',16,4,'4.5',NULL,1,3,NULL,1),(107,'Satisfaction with methods of evaluation for classroom theory',17,4,'4.5',NULL,1,3,NULL,1),(108,'Satisfaction with methods of assessment for practicals',18,4,'5.0',NULL,1,3,NULL,1),(109,'Attends class regularly',19,5,'4.0',NULL,1,3,NULL,1),(110,'Keeps to the published timetable',20,5,'5.0',NULL,1,3,NULL,1),(111,'Is available for consultation when necessary(outside class time)',21,5,'4.5',NULL,1,3,NULL,1),(112,'Guidance in practical lessons(e.g. Nursing)',22,5,'4.0',NULL,1,3,NULL,1),(113,'Explains the scope, recommended readings, delivery and evaluation methodology of the course',23,6,'5.0',NULL,1,3,NULL,1),(114,'Uses organized, up-to-date notes and course materials',24,6,'4.5',NULL,1,3,NULL,1),(115,'Manages time well(punctual, uses class time efficiently)',25,6,'4.5',NULL,1,3,NULL,1),(116,'Demonstration of procedures in the practical sessions',26,6,'5.0',NULL,1,3,NULL,1),(117,'Presents course concepts and theories in a clear and interesting way',27,7,'4.0',NULL,1,3,NULL,1),(118,'Facilitates meaningful and active class participation by students',28,7,'4.0',NULL,1,3,NULL,1),(119,'Answers questions clearly and knowledgeably',29,7,'5.0',NULL,1,3,NULL,1),(120,'Uses relevant examples and illustrations in the class/practical',30,7,'4.5',NULL,1,3,NULL,1),(121,'Is open to diverse viewpoints and opinions',31,7,'4.5',NULL,1,3,NULL,1),(122,'Gives relevant and challenging assignments and tests',32,8,'4.0',NULL,1,3,NULL,1),(123,'Marks assignments and tests promptly',33,8,'4.5',NULL,1,3,NULL,1),(124,'Gives helpful feedback on assignments and tests',34,8,'4.5',NULL,1,3,NULL,1),(125,'Use of case studies for analysis',35,8,'4.5',NULL,1,3,NULL,1),(126,'Overall rating of lecturer in the classroom/during lecture',36,9,'4.0',NULL,1,3,NULL,1),(127,'Overall rating of the lecturer during practical/clinical sessions',37,9,'4.5',NULL,1,3,NULL,1),(128,'Would you recommend this course to any other group of students?',38,11,NULL,'100.0%',1,3,NULL,1),(129,'What proportions of classes do you attend?',39,10,NULL,'62.0%',1,3,NULL,1),(130,'In a normal class/practical sesison, what portion of class members are present?',40,10,NULL,'74.5%',1,3,NULL,1),(131,'What are the reasons for the level of attendance you have reported above?',41,10,NULL,NULL,1,3,NULL,1),(132,'What is your overall view of the course?',42,11,NULL,NULL,1,3,NULL,1),(133,'Your lecturer would like to know if there are certain aspects you believe he/she has done especially well in his/her teaching of this course?',43,11,NULL,NULL,1,3,NULL,1),(134,'Your lecturer would also like to know what specific things you believe must be done to improvehis/her teaching in the course?',44,11,NULL,NULL,1,3,NULL,1),(135,'What other materials or resources, if any, should be added to enhance understanding of the course contents?',45,11,NULL,NULL,1,3,NULL,1),(136,'Clarity of course objectives (Classroom/Clinical)',91,1,'0.0',NULL,3,6,NULL,1),(137,'Achievement of course objectives',92,1,'0.0',NULL,3,6,NULL,1),(138,'Relevance of course to programme objectives',93,1,'0.0',NULL,3,6,NULL,1),(139,'Interpretation of concepts and theories',94,2,'0.0',NULL,3,6,NULL,1),(140,'Coverage of course syllabus',95,2,'0.0',NULL,3,6,NULL,1),(141,'Clarity in presentation',96,2,'0.0',NULL,3,6,NULL,1),(142,'Effectiveness of presentation methods',97,2,'0.0',NULL,3,6,NULL,1),(143,'Sufficiency of handouts',98,3,'0.0',NULL,3,6,NULL,1),(144,'Value of recommended resource materials',99,3,'0.0',NULL,3,6,NULL,1),(145,'Use of audio-visual and other teaching aids',100,3,'0.0',NULL,3,6,NULL,1),(146,'Guidance on the use of web based material/journals',101,3,'0.0',NULL,3,6,NULL,1),(147,'Adequacy of physical facilities',102,3,'0.0',NULL,3,6,NULL,1),(148,'Sufficiency of computer(ICT) facility',103,3,'0.0',NULL,3,6,NULL,1),(149,'Relevance of laboratory experiment(if any)',104,3,'0.0',NULL,3,6,NULL,1),(150,'Relevance and usefulness of assignments/practicals/CATs',105,4,'0.0',NULL,3,6,NULL,1),(151,'Appropriate coursework assessement',106,4,'0.0',NULL,3,6,NULL,1),(152,'Satisfaction with methods of evaluation for classroom theory',107,4,'0.0',NULL,3,6,NULL,1),(153,'Satisfaction with methods of assessment for practicals',108,4,'0.0',NULL,3,6,NULL,1),(154,'Attends class regularly',109,5,'0.0',NULL,3,6,NULL,1),(155,'Keeps to the published timetable',110,5,'0.0',NULL,3,6,NULL,1),(156,'Is available for consultation when necessary(outside class time)',111,5,'0.0',NULL,3,6,NULL,1),(157,'Guidance in practical lessons(e.g. Nursing)',112,5,'0.0',NULL,3,6,NULL,1),(158,'Explains the scope, recommended readings, delivery and evaluation methodology of the course',113,6,'0.0',NULL,3,6,NULL,1),(159,'Uses organized, up-to-date notes and course materials',114,6,'0.0',NULL,3,6,NULL,1),(160,'Manages time well(punctual, uses class time efficiently)',115,6,'0.0',NULL,3,6,NULL,1),(161,'Demonstration of procedures in the practical sessions',116,6,'0.0',NULL,3,6,NULL,1),(162,'Presents course concepts and theories in a clear and interesting way',117,7,'0.0',NULL,3,6,NULL,1),(163,'Facilitates meaningful and active class participation by students',118,7,'0.0',NULL,3,6,NULL,1),(164,'Answers questions clearly and knowledgeably',119,7,'0.0',NULL,3,6,NULL,1),(165,'Uses relevant examples and illustrations in the class/practical',120,7,'0.0',NULL,3,6,NULL,1),(166,'Is open to diverse viewpoints and opinions',121,7,'0.0',NULL,3,6,NULL,1),(167,'Gives relevant and challenging assignments and tests',122,8,'0.0',NULL,3,6,NULL,1),(168,'Marks assignments and tests promptly',123,8,'0.0',NULL,3,6,NULL,1),(169,'Gives helpful feedback on assignments and tests',124,8,'0.0',NULL,3,6,NULL,1),(170,'Use of case studies for analysis',125,8,'0.0',NULL,3,6,NULL,1),(171,'Overall rating of lecturer in the classroom/during lecture',126,9,'0.0',NULL,3,6,NULL,1),(172,'Overall rating of the lecturer during practical/clinical sessions',127,9,'0.0',NULL,3,6,NULL,1),(173,'What proportions of classes do you attend?',128,10,NULL,'0.0%',3,6,NULL,1),(174,'In a normal class/practical sesison, what portion of class members are present?',129,10,NULL,'0.0%',3,6,NULL,1),(175,'What are the reasons for the level of attendance you have reported above?',130,10,NULL,NULL,3,6,NULL,1),(176,'Your lecturer would like to know if there are certain aspects you believe he/she has done especially well in his/her teaching of this course?',131,11,NULL,NULL,3,6,NULL,1),(177,'Your lecturer would also like to know what specific things you believe must be done to improvehis/her teaching in the course?',132,11,NULL,NULL,3,6,NULL,1),(178,'What other materials or resources, if any, should be added to enhance understanding of the course contents?',133,11,NULL,NULL,3,6,NULL,1),(179,'What is your overall view of the course?',134,11,NULL,NULL,3,6,NULL,1),(180,'Would you recommend this course to any other group of students?',135,11,NULL,'0.0%',3,6,NULL,1);
/*!40000 ALTER TABLE `assessed_evaluation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `assessed_evaluation_comment`
--

DROP TABLE IF EXISTS `assessed_evaluation_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `assessed_evaluation_comment` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `comment` varchar(200) DEFAULT NULL,
  `assessed_evaluation` int(10) unsigned NOT NULL,
  `active` tinyint(1) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_assessed_evaluation_comments_assessed_evaluation1_idx` (`assessed_evaluation`),
  CONSTRAINT `fk_assessed_evaluation_comments_assessed_evaluation1` FOREIGN KEY (`assessed_evaluation`) REFERENCES `assessed_evaluation` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `assessed_evaluation_comment`
--

LOCK TABLES `assessed_evaluation_comment` WRITE;
/*!40000 ALTER TABLE `assessed_evaluation_comment` DISABLE KEYS */;
INSERT INTO `assessed_evaluation_comment` VALUES (1,'Well elaborated examples',43,1,NULL),(2,'Give more programming assignments',44,1,NULL),(3,'Wifi in the classroom',45,1,NULL),(4,'Proper illustration ',88,1,NULL),(5,'Give more assignments',89,1,NULL),(6,'Wifi in the classroom',90,1,NULL),(7,'pppp',133,1,NULL),(8,'pp',134,1,NULL),(9,'pp',135,1,NULL);
/*!40000 ALTER TABLE `assessed_evaluation_comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `college`
--

DROP TABLE IF EXISTS `college`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `college` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(60) NOT NULL,
  `abbreviation` varchar(20) NOT NULL,
  `institution` int(10) unsigned NOT NULL,
  `active` tinyint(1) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idcollege_UNIQUE` (`id`),
  UNIQUE KEY `collegecol_UNIQUE` (`name`),
  UNIQUE KEY `abbreviation_UNIQUE` (`abbreviation`),
  KEY `fk_college_institution_idx` (`institution`),
  CONSTRAINT `fk_college_institution` FOREIGN KEY (`institution`) REFERENCES `institution` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `college`
--

LOCK TABLES `college` WRITE;
/*!40000 ALTER TABLE `college` DISABLE KEYS */;
INSERT INTO `college` VALUES (1,'College of Biological and Physical Sciences','CBPS',1,1,NULL),(2,'College of Humanities and Social Sciences','CHSS',1,1,NULL),(3,'College of Architecture and Engineering','CAE',1,1,NULL);
/*!40000 ALTER TABLE `college` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contact`
--

DROP TABLE IF EXISTS `contact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contact` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `active` tinyint(1) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=176 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contact`
--

LOCK TABLES `contact` WRITE;
/*!40000 ALTER TABLE `contact` DISABLE KEYS */;
INSERT INTO `contact` VALUES (1,1,NULL),(2,1,NULL),(3,1,NULL),(4,1,NULL),(5,1,NULL),(6,1,NULL),(7,1,NULL),(8,1,NULL),(9,1,NULL),(10,1,NULL),(11,1,NULL),(12,1,NULL),(13,1,NULL),(14,1,NULL),(19,1,NULL),(20,1,NULL),(21,1,NULL),(22,1,NULL),(23,1,NULL),(24,1,NULL),(25,1,NULL),(26,1,NULL),(27,1,NULL),(28,1,NULL),(29,1,NULL),(30,1,NULL),(31,1,NULL),(32,1,NULL),(33,1,NULL),(34,1,NULL),(35,1,NULL),(36,1,NULL),(37,1,NULL),(38,1,NULL),(39,1,NULL),(40,1,NULL),(41,1,NULL),(42,1,NULL),(43,1,NULL),(44,1,NULL),(45,1,NULL),(46,1,NULL),(47,1,NULL),(48,1,NULL),(49,1,NULL),(50,1,NULL),(51,1,NULL),(52,1,NULL),(53,1,NULL),(54,1,NULL),(55,1,NULL),(56,1,NULL),(57,1,NULL),(58,1,NULL),(59,1,NULL),(60,1,NULL),(61,1,NULL),(62,1,NULL),(63,1,NULL),(64,1,NULL),(65,1,NULL),(66,1,NULL),(67,1,NULL),(68,1,NULL),(69,1,NULL),(70,1,NULL),(71,1,NULL),(72,1,NULL),(73,1,NULL),(74,1,NULL),(75,1,NULL),(76,1,NULL),(77,1,NULL),(78,1,NULL),(79,1,NULL),(80,1,NULL),(81,1,NULL),(82,1,NULL),(83,1,NULL),(84,1,NULL),(85,1,NULL),(86,1,NULL),(87,1,NULL),(88,1,NULL),(89,1,NULL),(90,1,NULL),(91,1,NULL),(92,1,NULL),(93,1,NULL),(94,1,NULL),(95,1,NULL),(96,1,NULL),(97,1,NULL),(98,1,NULL),(99,1,NULL),(100,1,NULL),(101,1,NULL),(102,1,NULL),(103,1,NULL),(104,1,NULL),(105,1,NULL),(106,1,NULL),(107,1,NULL),(108,1,NULL),(109,1,NULL),(110,1,NULL),(111,1,NULL),(112,1,NULL),(113,1,NULL),(114,1,NULL),(115,1,NULL),(116,1,NULL),(117,1,NULL),(118,1,NULL),(119,1,NULL),(120,1,NULL),(121,1,NULL),(122,1,NULL),(123,1,NULL),(124,1,NULL),(125,1,NULL),(126,1,NULL),(127,1,NULL),(128,1,NULL),(129,1,NULL),(130,1,NULL),(131,1,NULL),(132,1,NULL),(133,1,NULL),(134,1,NULL),(135,1,NULL),(136,1,NULL),(137,1,NULL),(138,1,NULL),(139,1,NULL),(140,1,NULL),(141,1,NULL),(142,1,NULL),(143,1,NULL),(144,1,NULL),(145,1,NULL),(146,1,NULL),(147,1,NULL),(148,1,NULL),(149,1,NULL),(150,1,NULL),(151,1,NULL),(152,1,NULL),(153,1,NULL),(154,1,NULL),(155,1,NULL),(156,1,NULL),(157,1,NULL),(158,1,NULL),(159,1,NULL),(160,1,NULL),(161,1,NULL),(162,1,NULL),(163,1,NULL),(164,1,NULL),(165,1,NULL),(166,1,NULL),(167,1,NULL),(168,1,NULL),(169,1,NULL),(170,1,NULL),(171,1,NULL),(172,1,NULL),(173,1,NULL),(174,1,NULL),(175,1,NULL);
/*!40000 ALTER TABLE `contact` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `country`
--

DROP TABLE IF EXISTS `country`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `country` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `iso` char(2) NOT NULL,
  `name` varchar(80) NOT NULL,
  `nice_name` varchar(80) NOT NULL,
  `iso3` char(3) DEFAULT NULL,
  `num_code` smallint(6) DEFAULT NULL,
  `phone_code` int(11) NOT NULL,
  `active` tinyint(1) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idcountry_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=254 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `country`
--

LOCK TABLES `country` WRITE;
/*!40000 ALTER TABLE `country` DISABLE KEYS */;
INSERT INTO `country` VALUES (1,'AF','AFGHANISTAN','Afghanistan','AFG',4,93,NULL,NULL),(2,'AL','ALBANIA','Albania','ALB',8,355,NULL,NULL),(3,'DZ','ALGERIA','Algeria','DZA',12,213,NULL,NULL),(4,'AS','AMERICAN SAMOA','American Samoa','ASM',16,1684,NULL,NULL),(5,'AD','ANDORRA','Andorra','AND',20,376,NULL,NULL),(6,'AO','ANGOLA','Angola','AGO',24,244,NULL,NULL),(7,'AI','ANGUILLA','Anguilla','AIA',660,1264,NULL,NULL),(8,'AQ','ANTARCTICA','Antarctica',NULL,NULL,0,NULL,NULL),(9,'AG','ANTIGUA AND BARBUDA','Antigua and Barbuda','ATG',28,1268,NULL,NULL),(10,'AR','ARGENTINA','Argentina','ARG',32,54,NULL,NULL),(11,'AM','ARMENIA','Armenia','ARM',51,374,NULL,NULL),(12,'AW','ARUBA','Aruba','ABW',533,297,NULL,NULL),(13,'AU','AUSTRALIA','Australia','AUS',36,61,NULL,NULL),(14,'AT','AUSTRIA','Austria','AUT',40,43,NULL,NULL),(15,'AZ','AZERBAIJAN','Azerbaijan','AZE',31,994,NULL,NULL),(16,'BS','BAHAMAS','Bahamas','BHS',44,1242,NULL,NULL),(17,'BH','BAHRAIN','Bahrain','BHR',48,973,NULL,NULL),(18,'BD','BANGLADESH','Bangladesh','BGD',50,880,NULL,NULL),(19,'BB','BARBADOS','Barbados','BRB',52,1246,NULL,NULL),(20,'BY','BELARUS','Belarus','BLR',112,375,NULL,NULL),(21,'BE','BELGIUM','Belgium','BEL',56,32,NULL,NULL),(22,'BZ','BELIZE','Belize','BLZ',84,501,NULL,NULL),(23,'BJ','BENIN','Benin','BEN',204,229,NULL,NULL),(24,'BM','BERMUDA','Bermuda','BMU',60,1441,NULL,NULL),(25,'BT','BHUTAN','Bhutan','BTN',64,975,NULL,NULL),(26,'BO','BOLIVIA','Bolivia','BOL',68,591,NULL,NULL),(27,'BA','BOSNIA AND HERZEGOVINA','Bosnia and Herzegovina','BIH',70,387,NULL,NULL),(28,'BW','BOTSWANA','Botswana','BWA',72,267,NULL,NULL),(29,'BV','BOUVET ISLAND','Bouvet Island',NULL,NULL,0,NULL,NULL),(30,'BR','BRAZIL','Brazil','BRA',76,55,NULL,NULL),(31,'IO','BRITISH INDIAN OCEAN TERRITORY','British Indian Ocean Territory',NULL,NULL,246,NULL,NULL),(32,'BN','BRUNEI DARUSSALAM','Brunei Darussalam','BRN',96,673,NULL,NULL),(33,'BG','BULGARIA','Bulgaria','BGR',100,359,NULL,NULL),(34,'BF','BURKINA FASO','Burkina Faso','BFA',854,226,NULL,NULL),(35,'BI','BURUNDI','Burundi','BDI',108,257,NULL,NULL),(36,'KH','CAMBODIA','Cambodia','KHM',116,855,NULL,NULL),(37,'CM','CAMEROON','Cameroon','CMR',120,237,NULL,NULL),(38,'CA','CANADA','Canada','CAN',124,1,NULL,NULL),(39,'CV','CAPE VERDE','Cape Verde','CPV',132,238,NULL,NULL),(40,'KY','CAYMAN ISLANDS','Cayman Islands','CYM',136,1345,NULL,NULL),(41,'CF','CENTRAL AFRICAN REPUBLIC','Central African Republic','CAF',140,236,NULL,NULL),(42,'TD','CHAD','Chad','TCD',148,235,NULL,NULL),(43,'CL','CHILE','Chile','CHL',152,56,NULL,NULL),(44,'CN','CHINA','China','CHN',156,86,NULL,NULL),(45,'CX','CHRISTMAS ISLAND','Christmas Island',NULL,NULL,61,NULL,NULL),(46,'CC','COCOS (KEELING) ISLANDS','Cocos (Keeling) Islands',NULL,NULL,672,NULL,NULL),(47,'CO','COLOMBIA','Colombia','COL',170,57,NULL,NULL),(48,'KM','COMOROS','Comoros','COM',174,269,NULL,NULL),(49,'CG','CONGO','Congo','COG',178,242,NULL,NULL),(50,'CD','CONGO, THE DEMOCRATIC REPUBLIC OF THE','Congo, the Democratic Republic of the','COD',180,243,NULL,NULL),(51,'CK','COOK ISLANDS','Cook Islands','COK',184,682,NULL,NULL),(52,'CR','COSTA RICA','Costa Rica','CRI',188,506,NULL,NULL),(53,'CI','COTE D\'IVOIRE','Cote D\'Ivoire','CIV',384,225,NULL,NULL),(54,'HR','CROATIA','Croatia','HRV',191,385,NULL,NULL),(55,'CU','CUBA','Cuba','CUB',192,53,NULL,NULL),(56,'CY','CYPRUS','Cyprus','CYP',196,357,NULL,NULL),(57,'CZ','CZECH REPUBLIC','Czech Republic','CZE',203,420,NULL,NULL),(58,'DK','DENMARK','Denmark','DNK',208,45,NULL,NULL),(59,'DJ','DJIBOUTI','Djibouti','DJI',262,253,NULL,NULL),(60,'DM','DOMINICA','Dominica','DMA',212,1767,NULL,NULL),(61,'DO','DOMINICAN REPUBLIC','Dominican Republic','DOM',214,1809,NULL,NULL),(62,'EC','ECUADOR','Ecuador','ECU',218,593,NULL,NULL),(63,'EG','EGYPT','Egypt','EGY',818,20,NULL,NULL),(64,'SV','EL SALVADOR','El Salvador','SLV',222,503,NULL,NULL),(65,'GQ','EQUATORIAL GUINEA','Equatorial Guinea','GNQ',226,240,NULL,NULL),(66,'ER','ERITREA','Eritrea','ERI',232,291,NULL,NULL),(67,'EE','ESTONIA','Estonia','EST',233,372,NULL,NULL),(68,'ET','ETHIOPIA','Ethiopia','ETH',231,251,NULL,NULL),(69,'FK','FALKLAND ISLANDS (MALVINAS)','Falkland Islands (Malvinas)','FLK',238,500,NULL,NULL),(70,'FO','FAROE ISLANDS','Faroe Islands','FRO',234,298,NULL,NULL),(71,'FJ','FIJI','Fiji','FJI',242,679,NULL,NULL),(72,'FI','FINLAND','Finland','FIN',246,358,NULL,NULL),(73,'FR','FRANCE','France','FRA',250,33,NULL,NULL),(74,'GF','FRENCH GUIANA','French Guiana','GUF',254,594,NULL,NULL),(75,'PF','FRENCH POLYNESIA','French Polynesia','PYF',258,689,NULL,NULL),(76,'TF','FRENCH SOUTHERN TERRITORIES','French Southern Territories',NULL,NULL,0,NULL,NULL),(77,'GA','GABON','Gabon','GAB',266,241,NULL,NULL),(78,'GM','GAMBIA','Gambia','GMB',270,220,NULL,NULL),(79,'GE','GEORGIA','Georgia','GEO',268,995,NULL,NULL),(80,'DE','GERMANY','Germany','DEU',276,49,NULL,NULL),(81,'GH','GHANA','Ghana','GHA',288,233,NULL,NULL),(82,'GI','GIBRALTAR','Gibraltar','GIB',292,350,NULL,NULL),(83,'GR','GREECE','Greece','GRC',300,30,NULL,NULL),(84,'GL','GREENLAND','Greenland','GRL',304,299,NULL,NULL),(85,'GD','GRENADA','Grenada','GRD',308,1473,NULL,NULL),(86,'GP','GUADELOUPE','Guadeloupe','GLP',312,590,NULL,NULL),(87,'GU','GUAM','Guam','GUM',316,1671,NULL,NULL),(88,'GT','GUATEMALA','Guatemala','GTM',320,502,NULL,NULL),(89,'GN','GUINEA','Guinea','GIN',324,224,NULL,NULL),(90,'GW','GUINEA-BISSAU','Guinea-Bissau','GNB',624,245,NULL,NULL),(91,'GY','GUYANA','Guyana','GUY',328,592,NULL,NULL),(92,'HT','HAITI','Haiti','HTI',332,509,NULL,NULL),(93,'HM','HEARD ISLAND AND MCDONALD ISLANDS','Heard Island and Mcdonald Islands',NULL,NULL,0,NULL,NULL),(94,'VA','HOLY SEE (VATICAN CITY STATE)','Holy See (Vatican City State)','VAT',336,39,NULL,NULL),(95,'HN','HONDURAS','Honduras','HND',340,504,NULL,NULL),(96,'HK','HONG KONG','Hong Kong','HKG',344,852,NULL,NULL),(97,'HU','HUNGARY','Hungary','HUN',348,36,NULL,NULL),(98,'IS','ICELAND','Iceland','ISL',352,354,NULL,NULL),(99,'IN','INDIA','India','IND',356,91,NULL,NULL),(100,'ID','INDONESIA','Indonesia','IDN',360,62,NULL,NULL),(101,'IR','IRAN, ISLAMIC REPUBLIC OF','Iran, Islamic Republic of','IRN',364,98,NULL,NULL),(102,'IQ','IRAQ','Iraq','IRQ',368,964,NULL,NULL),(103,'IE','IRELAND','Ireland','IRL',372,353,NULL,NULL),(104,'IL','ISRAEL','Israel','ISR',376,972,NULL,NULL),(105,'IT','ITALY','Italy','ITA',380,39,NULL,NULL),(106,'JM','JAMAICA','Jamaica','JAM',388,1876,NULL,NULL),(107,'JP','JAPAN','Japan','JPN',392,81,NULL,NULL),(108,'JO','JORDAN','Jordan','JOR',400,962,NULL,NULL),(109,'KZ','KAZAKHSTAN','Kazakhstan','KAZ',398,7,NULL,NULL),(110,'KE','KENYA','Kenya','KEN',404,254,NULL,NULL),(111,'KI','KIRIBATI','Kiribati','KIR',296,686,NULL,NULL),(112,'KP','KOREA, DEMOCRATIC PEOPLE\'S REPUBLIC OF','Korea, Democratic People\'s Republic of','PRK',408,850,NULL,NULL),(113,'KR','KOREA, REPUBLIC OF','Korea, Republic of','KOR',410,82,NULL,NULL),(114,'KW','KUWAIT','Kuwait','KWT',414,965,NULL,NULL),(115,'KG','KYRGYZSTAN','Kyrgyzstan','KGZ',417,996,NULL,NULL),(116,'LA','LAO PEOPLE\'S DEMOCRATIC REPUBLIC','Lao People\'s Democratic Republic','LAO',418,856,NULL,NULL),(117,'LV','LATVIA','Latvia','LVA',428,371,NULL,NULL),(118,'LB','LEBANON','Lebanon','LBN',422,961,NULL,NULL),(119,'LS','LESOTHO','Lesotho','LSO',426,266,NULL,NULL),(120,'LR','LIBERIA','Liberia','LBR',430,231,NULL,NULL),(121,'LY','LIBYAN ARAB JAMAHIRIYA','Libyan Arab Jamahiriya','LBY',434,218,NULL,NULL),(122,'LI','LIECHTENSTEIN','Liechtenstein','LIE',438,423,NULL,NULL),(123,'LT','LITHUANIA','Lithuania','LTU',440,370,NULL,NULL),(124,'LU','LUXEMBOURG','Luxembourg','LUX',442,352,NULL,NULL),(125,'MO','MACAO','Macao','MAC',446,853,NULL,NULL),(126,'MK','MACEDONIA, THE FORMER YUGOSLAV REPUBLIC OF','Macedonia, the Former Yugoslav Republic of','MKD',807,389,NULL,NULL),(127,'MG','MADAGASCAR','Madagascar','MDG',450,261,NULL,NULL),(128,'MW','MALAWI','Malawi','MWI',454,265,NULL,NULL),(129,'MY','MALAYSIA','Malaysia','MYS',458,60,NULL,NULL),(130,'MV','MALDIVES','Maldives','MDV',462,960,NULL,NULL),(131,'ML','MALI','Mali','MLI',466,223,NULL,NULL),(132,'MT','MALTA','Malta','MLT',470,356,NULL,NULL),(133,'MH','MARSHALL ISLANDS','Marshall Islands','MHL',584,692,NULL,NULL),(134,'MQ','MARTINIQUE','Martinique','MTQ',474,596,NULL,NULL),(135,'MR','MAURITANIA','Mauritania','MRT',478,222,NULL,NULL),(136,'MU','MAURITIUS','Mauritius','MUS',480,230,NULL,NULL),(137,'YT','MAYOTTE','Mayotte',NULL,NULL,269,NULL,NULL),(138,'MX','MEXICO','Mexico','MEX',484,52,NULL,NULL),(139,'FM','MICRONESIA, FEDERATED STATES OF','Micronesia, Federated States of','FSM',583,691,NULL,NULL),(140,'MD','MOLDOVA, REPUBLIC OF','Moldova, Republic of','MDA',498,373,NULL,NULL),(141,'MC','MONACO','Monaco','MCO',492,377,NULL,NULL),(142,'MN','MONGOLIA','Mongolia','MNG',496,976,NULL,NULL),(143,'MS','MONTSERRAT','Montserrat','MSR',500,1664,NULL,NULL),(144,'MA','MOROCCO','Morocco','MAR',504,212,NULL,NULL),(145,'MZ','MOZAMBIQUE','Mozambique','MOZ',508,258,NULL,NULL),(146,'MM','MYANMAR','Myanmar','MMR',104,95,NULL,NULL),(147,'NA','NAMIBIA','Namibia','NAM',516,264,NULL,NULL),(148,'NR','NAURU','Nauru','NRU',520,674,NULL,NULL),(149,'NP','NEPAL','Nepal','NPL',524,977,NULL,NULL),(150,'NL','NETHERLANDS','Netherlands','NLD',528,31,NULL,NULL),(151,'AN','NETHERLANDS ANTILLES','Netherlands Antilles','ANT',530,599,NULL,NULL),(152,'NC','NEW CALEDONIA','New Caledonia','NCL',540,687,NULL,NULL),(153,'NZ','NEW ZEALAND','New Zealand','NZL',554,64,NULL,NULL),(154,'NI','NICARAGUA','Nicaragua','NIC',558,505,NULL,NULL),(155,'NE','NIGER','Niger','NER',562,227,NULL,NULL),(156,'NG','NIGERIA','Nigeria','NGA',566,234,NULL,NULL),(157,'NU','NIUE','Niue','NIU',570,683,NULL,NULL),(158,'NF','NORFOLK ISLAND','Norfolk Island','NFK',574,672,NULL,NULL),(159,'MP','NORTHERN MARIANA ISLANDS','Northern Mariana Islands','MNP',580,1670,NULL,NULL),(160,'NO','NORWAY','Norway','NOR',578,47,NULL,NULL),(161,'OM','OMAN','Oman','OMN',512,968,NULL,NULL),(162,'PK','PAKISTAN','Pakistan','PAK',586,92,NULL,NULL),(163,'PW','PALAU','Palau','PLW',585,680,NULL,NULL),(164,'PS','PALESTINIAN TERRITORY, OCCUPIED','Palestinian Territory, Occupied',NULL,NULL,970,NULL,NULL),(165,'PA','PANAMA','Panama','PAN',591,507,NULL,NULL),(166,'PG','PAPUA NEW GUINEA','Papua New Guinea','PNG',598,675,NULL,NULL),(167,'PY','PARAGUAY','Paraguay','PRY',600,595,NULL,NULL),(168,'PE','PERU','Peru','PER',604,51,NULL,NULL),(169,'PH','PHILIPPINES','Philippines','PHL',608,63,NULL,NULL),(170,'PN','PITCAIRN','Pitcairn','PCN',612,0,NULL,NULL),(171,'PL','POLAND','Poland','POL',616,48,NULL,NULL),(172,'PT','PORTUGAL','Portugal','PRT',620,351,NULL,NULL),(173,'PR','PUERTO RICO','Puerto Rico','PRI',630,1787,NULL,NULL),(174,'QA','QATAR','Qatar','QAT',634,974,NULL,NULL),(175,'RE','REUNION','Reunion','REU',638,262,NULL,NULL),(176,'RO','ROMANIA','Romania','ROM',642,40,NULL,NULL),(177,'RU','RUSSIAN FEDERATION','Russian Federation','RUS',643,7,NULL,NULL),(178,'RW','RWANDA','Rwanda','RWA',646,250,NULL,NULL),(179,'SH','SAINT HELENA','Saint Helena','SHN',654,290,NULL,NULL),(180,'KN','SAINT KITTS AND NEVIS','Saint Kitts and Nevis','KNA',659,1869,NULL,NULL),(181,'LC','SAINT LUCIA','Saint Lucia','LCA',662,1758,NULL,NULL),(182,'PM','SAINT PIERRE AND MIQUELON','Saint Pierre and Miquelon','SPM',666,508,NULL,NULL),(183,'VC','SAINT VINCENT AND THE GRENADINES','Saint Vincent and the Grenadines','VCT',670,1784,NULL,NULL),(184,'WS','SAMOA','Samoa','WSM',882,684,NULL,NULL),(185,'SM','SAN MARINO','San Marino','SMR',674,378,NULL,NULL),(186,'ST','SAO TOME AND PRINCIPE','Sao Tome and Principe','STP',678,239,NULL,NULL),(187,'SA','SAUDI ARABIA','Saudi Arabia','SAU',682,966,NULL,NULL),(188,'SN','SENEGAL','Senegal','SEN',686,221,NULL,NULL),(190,'SC','SEYCHELLES','Seychelles','SYC',690,248,NULL,NULL),(191,'SL','SIERRA LEONE','Sierra Leone','SLE',694,232,NULL,NULL),(192,'SG','SINGAPORE','Singapore','SGP',702,65,NULL,NULL),(193,'SK','SLOVAKIA','Slovakia','SVK',703,421,NULL,NULL),(194,'SI','SLOVENIA','Slovenia','SVN',705,386,NULL,NULL),(195,'SB','SOLOMON ISLANDS','Solomon Islands','SLB',90,677,NULL,NULL),(196,'SO','SOMALIA','Somalia','SOM',706,252,NULL,NULL),(197,'ZA','SOUTH AFRICA','South Africa','ZAF',710,27,NULL,NULL),(198,'GS','SOUTH GEORGIA AND THE SOUTH SANDWICH ISLANDS','South Georgia and the South Sandwich Islands',NULL,NULL,0,NULL,NULL),(199,'ES','SPAIN','Spain','ESP',724,34,NULL,NULL),(200,'LK','SRI LANKA','Sri Lanka','LKA',144,94,NULL,NULL),(201,'SD','SUDAN','Sudan','SDN',736,249,NULL,NULL),(202,'SR','SURINAME','Suriname','SUR',740,597,NULL,NULL),(203,'SJ','SVALBARD AND JAN MAYEN','Svalbard and Jan Mayen','SJM',744,47,NULL,NULL),(204,'SZ','SWAZILAND','Swaziland','SWZ',748,268,NULL,NULL),(205,'SE','SWEDEN','Sweden','SWE',752,46,NULL,NULL),(206,'CH','SWITZERLAND','Switzerland','CHE',756,41,NULL,NULL),(207,'SY','SYRIAN ARAB REPUBLIC','Syrian Arab Republic','SYR',760,963,NULL,NULL),(208,'TW','TAIWAN, PROVINCE OF CHINA','Taiwan, Province of China','TWN',158,886,NULL,NULL),(209,'TJ','TAJIKISTAN','Tajikistan','TJK',762,992,NULL,NULL),(210,'TZ','TANZANIA, UNITED REPUBLIC OF','Tanzania, United Republic of','TZA',834,255,NULL,NULL),(211,'TH','THAILAND','Thailand','THA',764,66,NULL,NULL),(212,'TL','TIMOR-LESTE','Timor-Leste',NULL,NULL,670,NULL,NULL),(213,'TG','TOGO','Togo','TGO',768,228,NULL,NULL),(214,'TK','TOKELAU','Tokelau','TKL',772,690,NULL,NULL),(215,'TO','TONGA','Tonga','TON',776,676,NULL,NULL),(216,'TT','TRINIDAD AND TOBAGO','Trinidad and Tobago','TTO',780,1868,NULL,NULL),(217,'TN','TUNISIA','Tunisia','TUN',788,216,NULL,NULL),(218,'TR','TURKEY','Turkey','TUR',792,90,NULL,NULL),(219,'TM','TURKMENISTAN','Turkmenistan','TKM',795,7370,NULL,NULL),(220,'TC','TURKS AND CAICOS ISLANDS','Turks and Caicos Islands','TCA',796,1649,NULL,NULL),(221,'TV','TUVALU','Tuvalu','TUV',798,688,NULL,NULL),(222,'UG','UGANDA','Uganda','UGA',800,256,NULL,NULL),(223,'UA','UKRAINE','Ukraine','UKR',804,380,NULL,NULL),(224,'AE','UNITED ARAB EMIRATES','United Arab Emirates','ARE',784,971,NULL,NULL),(225,'GB','UNITED KINGDOM','United Kingdom','GBR',826,44,NULL,NULL),(226,'US','UNITED STATES','United States','USA',840,1,NULL,NULL),(227,'UM','UNITED STATES MINOR OUTLYING ISLANDS','United States Minor Outlying Islands',NULL,NULL,1,NULL,NULL),(228,'UY','URUGUAY','Uruguay','URY',858,598,NULL,NULL),(229,'UZ','UZBEKISTAN','Uzbekistan','UZB',860,998,NULL,NULL),(230,'VU','VANUATU','Vanuatu','VUT',548,678,NULL,NULL),(231,'VE','VENEZUELA','Venezuela','VEN',862,58,NULL,NULL),(232,'VN','VIET NAM','Viet Nam','VNM',704,84,NULL,NULL),(233,'VG','VIRGIN ISLANDS, BRITISH','Virgin Islands, British','VGB',92,1284,NULL,NULL),(234,'VI','VIRGIN ISLANDS, U.S.','Virgin Islands, U.s.','VIR',850,1340,NULL,NULL),(235,'WF','WALLIS AND FUTUNA','Wallis and Futuna','WLF',876,681,NULL,NULL),(236,'EH','WESTERN SAHARA','Western Sahara','ESH',732,212,NULL,NULL),(237,'YE','YEMEN','Yemen','YEM',887,967,NULL,NULL),(238,'ZM','ZAMBIA','Zambia','ZMB',894,260,NULL,NULL),(239,'ZW','ZIMBABWE','Zimbabwe','ZWE',716,263,NULL,NULL),(240,'RS','SERBIA','Serbia','SRB',688,381,NULL,NULL),(241,'AP','ASIA PACIFIC REGION','Asia / Pacific Region','0',0,0,NULL,NULL),(242,'ME','MONTENEGRO','Montenegro','MNE',499,382,NULL,NULL),(243,'AX','ALAND ISLANDS','Aland Islands','ALA',248,358,NULL,NULL),(244,'BQ','BONAIRE, SINT EUSTATIUS AND SABA','Bonaire, Sint Eustatius and Saba','BES',535,599,NULL,NULL),(245,'CW','CURACAO','Curacao','CUW',531,599,NULL,NULL),(246,'GG','GUERNSEY','Guernsey','GGY',831,44,NULL,NULL),(247,'IM','ISLE OF MAN','Isle of Man','IMN',833,44,NULL,NULL),(248,'JE','JERSEY','Jersey','JEY',832,44,NULL,NULL),(249,'XK','KOSOVO','Kosovo','---',0,381,NULL,NULL),(250,'BL','SAINT BARTHELEMY','Saint Barthelemy','BLM',652,590,NULL,NULL),(251,'MF','SAINT MARTIN','Saint Martin','MAF',663,590,NULL,NULL),(252,'SX','SINT MAARTEN','Sint Maarten','SXM',534,1,NULL,NULL),(253,'SS','SOUTH SUDAN','South Sudan','SSD',728,211,NULL,NULL);
/*!40000 ALTER TABLE `country` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course`
--

DROP TABLE IF EXISTS `course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `course` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(120) NOT NULL,
  `code` varchar(20) NOT NULL,
  `degree` int(10) unsigned NOT NULL,
  `active` tinyint(1) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `title_UNIQUE` (`title`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_course_degree1_idx` (`degree`),
  CONSTRAINT `fk_course_degree1` FOREIGN KEY (`degree`) REFERENCES `degree` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course`
--

LOCK TABLES `course` WRITE;
/*!40000 ALTER TABLE `course` DISABLE KEYS */;
INSERT INTO `course` VALUES (2,'Object Oriented Programming','CSC 221',1,1,NULL),(3,'Automata Theory','CSC 222',1,1,NULL),(4,'Operating Systems','CSC 223',1,1,NULL),(5,'Software Engineering','CSC 224',1,1,NULL),(6,'Computer Networks','CSC 225',1,1,NULL),(7,'Networking Lab','CSC 226',1,1,NULL),(8,'Programming Project','CSC 227',1,1,NULL),(9,'Introduction to Computer Systems','CSC 111',1,1,NULL),(10,'Introduction to Programming ','CSC 112',1,1,NULL),(11,'Discrete Maths','CSC 113',1,1,NULL),(12,'Differential ','CSC 114',1,1,NULL),(13,'Communication and Learning  Skills','CCS 001',1,1,NULL),(14,'Elements of Economics','CCS 009',1,1,NULL);
/*!40000 ALTER TABLE `course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course_of_instance`
--

DROP TABLE IF EXISTS `course_of_instance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `course_of_instance` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `course_of_session` int(10) unsigned NOT NULL,
  `evaluation_instance` int(10) unsigned NOT NULL,
  `active` tinyint(1) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_course_of_session_has_evaluation_instance_evaluation_ins_idx` (`evaluation_instance`),
  KEY `fk_course_of_session_has_evaluation_instance_course_of_sess_idx` (`course_of_session`),
  CONSTRAINT `fk_course_of_session_has_evaluation_instance_course_of_session1` FOREIGN KEY (`course_of_session`) REFERENCES `course_of_session` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_course_of_session_has_evaluation_instance_evaluation_insta1` FOREIGN KEY (`evaluation_instance`) REFERENCES `evaluation_instance` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course_of_instance`
--

LOCK TABLES `course_of_instance` WRITE;
/*!40000 ALTER TABLE `course_of_instance` DISABLE KEYS */;
INSERT INTO `course_of_instance` VALUES (1,1,1,1,NULL),(2,2,1,1,NULL),(3,1,2,1,NULL),(4,2,2,1,NULL),(5,3,2,1,NULL),(6,3,1,1,NULL),(7,6,1,1,NULL),(8,6,2,1,NULL),(9,4,4,1,NULL),(10,5,4,1,NULL),(11,4,5,1,NULL),(12,5,5,1,NULL);
/*!40000 ALTER TABLE `course_of_instance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course_of_session`
--

DROP TABLE IF EXISTS `course_of_session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `course_of_session` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `course` int(10) unsigned NOT NULL,
  `faculty_member` int(10) unsigned NOT NULL,
  `evaluation_session` int(10) unsigned NOT NULL,
  `active` tinyint(1) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_courses_of_session_course1_idx` (`course`),
  KEY `fk_courses_of_session_faculty_member1_idx` (`faculty_member`),
  KEY `fk_course_of_session_evaluation_session1_idx` (`evaluation_session`),
  CONSTRAINT `fk_course_of_session_evaluation_session1` FOREIGN KEY (`evaluation_session`) REFERENCES `evaluation_session` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_courses_of_session_course1` FOREIGN KEY (`course`) REFERENCES `course` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_courses_of_session_faculty_member1` FOREIGN KEY (`faculty_member`) REFERENCES `faculty_member` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course_of_session`
--

LOCK TABLES `course_of_session` WRITE;
/*!40000 ALTER TABLE `course_of_session` DISABLE KEYS */;
INSERT INTO `course_of_session` VALUES (1,2,6,1,1,NULL),(2,4,7,1,1,NULL),(3,5,8,1,1,NULL),(4,2,6,4,1,NULL),(5,4,7,4,1,NULL),(6,6,10,3,1,NULL),(7,5,8,2,1,NULL),(8,7,9,2,1,NULL);
/*!40000 ALTER TABLE `course_of_session` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `degree`
--

DROP TABLE IF EXISTS `degree`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `degree` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(120) NOT NULL,
  `admission` int(10) unsigned NOT NULL,
  `faculty` int(10) unsigned DEFAULT NULL,
  `department` int(10) unsigned DEFAULT NULL,
  `active` tinyint(1) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  KEY `fk_degree_admission1_idx` (`admission`),
  KEY `fk_degree_faculty1_idx` (`faculty`),
  KEY `fk_degree_department1_idx` (`department`),
  CONSTRAINT `fk_degree_admission1` FOREIGN KEY (`admission`) REFERENCES `admission` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_degree_department1` FOREIGN KEY (`department`) REFERENCES `department` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_degree_faculty1` FOREIGN KEY (`faculty`) REFERENCES `faculty` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `degree`
--

LOCK TABLES `degree` WRITE;
/*!40000 ALTER TABLE `degree` DISABLE KEYS */;
INSERT INTO `degree` VALUES (1,'BSc. Computer Science',1,2,NULL,1,NULL);
/*!40000 ALTER TABLE `degree` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `department`
--

DROP TABLE IF EXISTS `department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `department` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(60) NOT NULL,
  `abbreviation` varchar(20) NOT NULL,
  `faculty` int(10) unsigned NOT NULL,
  `contact` int(10) unsigned NOT NULL,
  `active` tinyint(1) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  UNIQUE KEY `abbreviation_UNIQUE` (`abbreviation`),
  KEY `fk_department_faculty1_idx` (`faculty`),
  KEY `fk_department_contact1_idx` (`contact`),
  CONSTRAINT `fk_department_contact1` FOREIGN KEY (`contact`) REFERENCES `contact` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_department_faculty1` FOREIGN KEY (`faculty`) REFERENCES `faculty` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `department`
--

LOCK TABLES `department` WRITE;
/*!40000 ALTER TABLE `department` DISABLE KEYS */;
/*!40000 ALTER TABLE `department` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `email_contact`
--

DROP TABLE IF EXISTS `email_contact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `email_contact` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `email_address` varchar(65) NOT NULL,
  `contact` int(10) unsigned NOT NULL,
  `active` tinyint(1) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_address_UNIQUE` (`email_address`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_email_contact_contact1_idx` (`contact`),
  CONSTRAINT `fk_email_contact_contact1` FOREIGN KEY (`contact`) REFERENCES `contact` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=176 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `email_contact`
--

LOCK TABLES `email_contact` WRITE;
/*!40000 ALTER TABLE `email_contact` DISABLE KEYS */;
INSERT INTO `email_contact` VALUES (1,'fa.cbps@uonbi.ac.ke',1,1,NULL),(2,'sci.cbps@uonbi.ac.ke',2,1,NULL),(3,'sps.cbps@uonbi.ac.ke',3,1,NULL),(4,'siele.bernard@gmail.com',4,1,NULL),(5,'kimotho@gmail.com',5,1,NULL),(6,'mubari@gmail.com',6,1,NULL),(7,'stahimili@gmail.com',7,1,NULL),(8,'kyalo@gmail.com',8,1,NULL),(9,'oboko@uonbi.ac.ke',9,1,NULL),(10,'pauline@uonbi.ac.ke',10,1,NULL),(11,'wausi@gmail.com',11,1,NULL),(12,'ronge@uonbi.ac.ke',12,1,NULL),(13,'onditi@gmail.com',13,1,NULL),(14,'rongem@uonbi.ac.ke',14,1,NULL),(19,'tevin@gmail.com',19,1,NULL),(20,'abdallamohamed12@gmail.com',20,1,NULL),(21,'abdallanawaalkassim52@gmail.com',21,1,NULL),(22,'abdubaguyomohamed82@gmail.com',22,1,NULL),(23,'adanmohamednajib63@gmail.com',23,1,NULL),(24,'adembaaquinousrabongo04@gmail.com',24,1,NULL),(25,'aggreytevinlitunda44@gmail.com',25,1,NULL),(26,'aginelvis64@gmail.com',26,1,NULL),(27,'akivayakevinesendi84@gmail.com',27,1,NULL),(28,'ambanipaulsternmadegwa15@gmail.com',28,1,NULL),(29,'amokewycliffeochieng35@gmail.com',29,1,NULL),(30,'atienoadhiambofay65@gmail.com',30,1,NULL),(31,'barakachillah06@gmail.com',31,1,NULL),(32,'barminogichowabenedict86@gmail.com',32,1,NULL),(33,'baruaemusugutallan57@gmail.com',33,1,NULL),(34,'bettkiplimotonny28@gmail.com',34,1,NULL),(35,'bhundianavikjayant58@gmail.com',35,1,NULL),(36,'boitmichaelkipkosgei88@gmail.com',36,1,NULL),(37,'borusnorahchelagat09@gmail.com',37,1,NULL),(38,'catherinewaigwe39@gmail.com',38,1,NULL),(39,'chumbatrevorkiprop69@gmail.com',39,1,NULL),(40,'damjikodievans99@gmail.com',40,1,NULL),(41,'gacengahoseaciuti10@gmail.com',41,1,NULL),(42,'gachokamarvin30@gmail.com',42,1,NULL),(43,'gagidenisandrew60@gmail.com',43,1,NULL),(44,'gakengebensonmugo80@gmail.com',44,1,NULL),(45,'gichukievanswahome02@gmail.com',45,1,NULL),(46,'gitahijudyannwanjiku42@gmail.com',46,1,NULL),(47,'gitausamuelnjoroge92@gmail.com',47,1,NULL),(48,'ibrahimaliabdi23@gmail.com',48,1,NULL),(49,'ibrahimanasmohamed73@gmail.com',49,1,NULL),(50,'inindalaurencebugasu93@gmail.com',50,1,NULL),(51,'irungumartinmuriuki14@gmail.com',51,1,NULL),(52,'jacksonericknzuki44@gmail.com',52,1,NULL),(53,'jamawarsamemohamud05@gmail.com',53,1,NULL),(54,'jefwakelvinmukare55@gmail.com',54,1,NULL),(55,'jumaedgaronyango95@gmail.com',55,1,NULL),(56,'kahihukelvingitogo46@gmail.com',56,1,NULL),(57,'kahinkhalifmohamed76@gmail.com',57,1,NULL),(58,'kahuranipeterkibicha07@gmail.com',58,1,NULL),(59,'kajiruabubakarmohamed37@gmail.com',59,1,NULL),(60,'kamadieugenesambula67@gmail.com',60,1,NULL),(61,'kamamimoseswamae08@gmail.com',61,1,NULL),(62,'kamaubrianmukuhi98@gmail.com',62,1,NULL),(63,'karimivincentkarani19@gmail.com',63,1,NULL),(64,'kariukimarkmuigai59@gmail.com',64,1,NULL),(65,'kariukiwairimusalome79@gmail.com',65,1,NULL),(66,'kasyokivictor99@gmail.com',66,1,NULL),(67,'keringjoanjepkogei30@gmail.com',67,1,NULL),(68,'kibetmichaelbrian50@gmail.com',68,1,NULL),(69,'kingoriahmichaelmutuma31@gmail.com',69,1,NULL),(70,'kipkemeikevin81@gmail.com',70,1,NULL),(71,'kipkoriradrianabrahamk32@gmail.com',71,1,NULL),(72,'kirujaiangitonga62@gmail.com',72,1,NULL),(73,'kiutejackmwakai63@gmail.com',73,1,NULL),(74,'kuriamarkkaruku14@gmail.com',74,1,NULL),(75,'kuriamichaelwamathai44@gmail.com',75,1,NULL),(76,'kwambaichepkogeimercy35@gmail.com',76,1,NULL),(77,'kyalokelvinmuindi85@gmail.com',77,1,NULL),(78,'kyandemichaeljohn16@gmail.com',78,1,NULL),(79,'langatcalvinkiptoo46@gmail.com',79,1,NULL),(80,'langatdanielkipyegon66@gmail.com',80,1,NULL),(81,'langatvictorkiprono96@gmail.com',81,1,NULL),(82,'likonoian17@gmail.com',82,1,NULL),(83,'lucynjoki67@gmail.com',83,1,NULL),(84,'lupaowanjalaclive59@gmail.com',84,1,NULL),(85,'luttaelvisemmanuel99@gmail.com',85,1,NULL),(86,'machariasantananjoki30@gmail.com',86,1,NULL),(87,'machokatomfrank60@gmail.com',87,1,NULL),(88,'magetoallanbikundo11@gmail.com',88,1,NULL),(89,'mainakelvinmwangi91@gmail.com',89,1,NULL),(90,'makoribahatiagata72@gmail.com',90,1,NULL),(91,'maleyaclarkstinjumba23@gmail.com',91,1,NULL),(92,'malingaerickmuigei53@gmail.com',92,1,NULL),(93,'marosigregoryjosephokari93@gmail.com',93,1,NULL),(94,'mathusharonwanjiru14@gmail.com',94,1,NULL),(95,'mbakaabneroisebe34@gmail.com',95,1,NULL),(96,'mbarijohnwambugu84@gmail.com',96,1,NULL),(97,'mbuguajustusnjuru65@gmail.com',97,1,NULL),(98,'mbuguaphilipndungu06@gmail.com',98,1,NULL),(99,'mbuiteddykamau76@gmail.com',99,1,NULL),(100,'moseticaritonmaranga87@gmail.com',100,1,NULL),(101,'muchendubonifacemichuki58@gmail.com',101,1,NULL),(102,'muchirulewiskuria09@gmail.com',102,1,NULL),(103,'mugenyaemmanuelwilson29@gmail.com',103,1,NULL),(104,'muigailukekivunaga59@gmail.com',104,1,NULL),(105,'mulwaisaackiptoo79@gmail.com',105,1,NULL),(106,'mungacollinsgichuhi00@gmail.com',106,1,NULL),(107,'mungaiericmburu20@gmail.com',107,1,NULL),(108,'mungaitevinchege50@gmail.com',108,1,NULL),(109,'mungathiainnocentkithinji70@gmail.com',109,1,NULL),(110,'muriithiedwinkabui01@gmail.com',110,1,NULL),(111,'murayasammyndirangu31@gmail.com',111,1,NULL),(112,'murimimartin91@gmail.com',112,1,NULL),(113,'muriukitajirigitonga52@gmail.com',113,1,NULL),(114,'musyokarosianahwanza33@gmail.com',114,1,NULL),(115,'mutendederekprince53@gmail.com',115,1,NULL),(116,'mutindajaphethkioko93@gmail.com',116,1,NULL),(117,'mutua augustinenganga14@gmail.com',117,1,NULL),(118,'mutuasolomon44@gmail.com',118,1,NULL),(119,'mutukumaureenmumbua64@gmail.com',119,1,NULL),(120,'mwangibrianmacharia84@gmail.com',120,1,NULL),(121,'mwangifreudkariuki15@gmail.com',121,1,NULL),(122,'mwanikiedwinmbuthia45@gmail.com',122,1,NULL),(123,'mwanziadaudikasia75@gmail.com',123,1,NULL),(124,'mwanziasamuelnzyuko95@gmail.com',124,1,NULL),(125,'mwelesapauletteemali26@gmail.com',125,1,NULL),(126,'mwemaphenaedith56@gmail.com',126,1,NULL),(127,'mwenjestephen17@gmail.com',127,1,NULL),(128,'mwithalievincentmurithi38@gmail.com',128,1,NULL),(129,'ndambukilabankioko59@gmail.com',129,1,NULL),(130,'ndichustevekevin20@gmail.com',130,1,NULL),(131,'ndungudennisgichu70@gmail.com',131,1,NULL),(132,'ndwigalewismunyi01@gmail.com',132,1,NULL),(133,'ngugimichaelgichora21@gmail.com',133,1,NULL),(134,'ngatiasimonmuraguri51@gmail.com',134,1,NULL),(135,'njerusimonmugo81@gmail.com',135,1,NULL),(136,'njoguwinniewanjiru12@gmail.com',136,1,NULL),(137,'njokipeterkahenya42@gmail.com',137,1,NULL),(138,'nyagapetermwaniki72@gmail.com',138,1,NULL),(139,'nyagesoaabiudorina03@gmail.com',139,1,NULL),(140,'nzumajonathanndambuki33@gmail.com',140,1,NULL),(141,'ochiengfelixomondi04@gmail.com',141,1,NULL),(142,'ochomootienowilliam34@gmail.com',142,1,NULL),(143,'odhiambostephenochieng94@gmail.com',143,1,NULL),(144,'odongobrianrailaamolo65@gmail.com',144,1,NULL),(145,'odundostephenopiyo36@gmail.com',145,1,NULL),(146,'okellojobopiyo76@gmail.com',146,1,NULL),(147,'okeromichaelomondi17@gmail.com',147,1,NULL),(148,'olookevinomondi97@gmail.com',148,1,NULL),(149,'oludhepascaloduor48@gmail.com',149,1,NULL),(150,'oluochbilloduor09@gmail.com',150,1,NULL),(151,'omarmohamedabdi49@gmail.com',151,1,NULL),(152,'ombasomohamednyanamba99@gmail.com',152,1,NULL),(153,'onamujamesrodney40@gmail.com',153,1,NULL),(154,'onyangorobertmark12@gmail.com',154,1,NULL),(155,'onyinsitobiaswest04@gmail.com',155,1,NULL),(156,'otienoanitaajwang65@gmail.com',156,1,NULL),(157,'otienoerniembock06@gmail.com',157,1,NULL),(158,'oyiekeallenongado26@gmail.com',158,1,NULL),(159,'sakaligabrielirungu57@gmail.com',159,1,NULL),(160,'thaganakmark87@gmail.com',160,1,NULL),(161,'wahomedennislinus18@gmail.com',161,1,NULL),(162,'wamaejosephwanyoike48@gmail.com',162,1,NULL),(163,'wamuigakelvinkamau78@gmail.com',163,1,NULL),(164,'wanjohibrianmuturi19@gmail.com',164,1,NULL),(165,'wanjohiryangitonga39@gmail.com',165,1,NULL),(166,'wayualikomora30@gmail.com',166,1,NULL),(167,'yassinahmedfaiz21@gmail.com',167,1,NULL),(168,'yusufmoheenrashid61@gmail.com',168,1,NULL),(169,'zakariahusseinabdi91@gmail.com',169,1,NULL),(170,'drevansmiriti74@gmail.com',170,1,NULL),(171,'profomwengaomwenga06@gmail.com',171,1,NULL),(172,'drcorneliusabungu26@gmail.com',172,1,NULL),(173,'drcorneliusabungu56@gmail.com',173,1,NULL),(174,'drmaryokebe86@gmail.com',174,1,NULL),(175,'drpetermuriu07@gmail.com',175,1,NULL);
/*!40000 ALTER TABLE `email_contact` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `evaluated_question`
--

DROP TABLE IF EXISTS `evaluated_question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `evaluated_question` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `question` varchar(200) NOT NULL,
  `means_of_answering` smallint(3) unsigned NOT NULL,
  `question_category` smallint(3) unsigned NOT NULL,
  `rating_type` smallint(3) unsigned DEFAULT NULL,
  `evaluation_session` int(10) unsigned NOT NULL,
  `active` tinyint(1) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_evaluated_question_means_of_answering1_idx` (`means_of_answering`),
  KEY `fk_evaluated_question_question_category1_idx` (`question_category`),
  KEY `fk_evaluated_question_rating_type1_idx` (`rating_type`),
  KEY `fk_evaluated_question_evaluation_session1_idx` (`evaluation_session`),
  CONSTRAINT `fk_evaluated_question_evaluation_session1` FOREIGN KEY (`evaluation_session`) REFERENCES `evaluation_session` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_evaluated_question_means_of_answering1` FOREIGN KEY (`means_of_answering`) REFERENCES `means_of_answering` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_evaluated_question_question_category1` FOREIGN KEY (`question_category`) REFERENCES `question_category` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_evaluated_question_rating_type1` FOREIGN KEY (`rating_type`) REFERENCES `rating_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=181 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evaluated_question`
--

LOCK TABLES `evaluated_question` WRITE;
/*!40000 ALTER TABLE `evaluated_question` DISABLE KEYS */;
INSERT INTO `evaluated_question` VALUES (1,'Clarity of course objectives (Classroom/Clinical)',1,1,1,1,1,NULL),(2,'Achievement of course objectives',1,1,1,1,1,NULL),(3,'Relevance of course to programme objectives',1,1,1,1,1,NULL),(4,'Interpretation of concepts and theories',1,2,1,1,1,NULL),(5,'Coverage of course syllabus',1,2,1,1,1,NULL),(6,'Clarity in presentation',1,2,1,1,1,NULL),(7,'Effectiveness of presentation methods',1,2,1,1,1,NULL),(8,'Sufficiency of handouts',1,3,1,1,1,NULL),(9,'Value of recommended resource materials',1,3,1,1,1,NULL),(10,'Use of audio-visual and other teaching aids',1,3,1,1,1,NULL),(11,'Guidance on the use of web based material/journals',1,3,1,1,1,NULL),(12,'Adequacy of physical facilities',1,3,1,1,1,NULL),(13,'Sufficiency of computer(ICT) facility',1,3,1,1,1,NULL),(14,'Relevance of laboratory experiment(if any)',1,3,1,1,1,NULL),(15,'Relevance and usefulness of assignments/practicals/CATs',1,4,1,1,1,NULL),(16,'Appropriate coursework assessement',1,4,1,1,1,NULL),(17,'Satisfaction with methods of evaluation for classroom theory',1,4,1,1,1,NULL),(18,'Satisfaction with methods of assessment for practicals',1,4,1,1,1,NULL),(19,'Attends class regularly',1,5,1,1,1,NULL),(20,'Keeps to the published timetable',1,5,1,1,1,NULL),(21,'Is available for consultation when necessary(outside class time)',1,5,1,1,1,NULL),(22,'Guidance in practical lessons(e.g. Nursing)',1,5,1,1,1,NULL),(23,'Explains the scope, recommended readings, delivery and evaluation methodology of the course',1,6,1,1,1,NULL),(24,'Uses organized, up-to-date notes and course materials',1,6,1,1,1,NULL),(25,'Manages time well(punctual, uses class time efficiently)',1,6,1,1,1,NULL),(26,'Demonstration of procedures in the practical sessions',1,6,1,1,1,NULL),(27,'Presents course concepts and theories in a clear and interesting way',1,7,1,1,1,NULL),(28,'Facilitates meaningful and active class participation by students',1,7,1,1,1,NULL),(29,'Answers questions clearly and knowledgeably',1,7,1,1,1,NULL),(30,'Uses relevant examples and illustrations in the class/practical',1,7,1,1,1,NULL),(31,'Is open to diverse viewpoints and opinions',1,7,1,1,1,NULL),(32,'Gives relevant and challenging assignments and tests',1,8,1,1,1,NULL),(33,'Marks assignments and tests promptly',1,8,1,1,1,NULL),(34,'Gives helpful feedback on assignments and tests',1,8,1,1,1,NULL),(35,'Use of case studies for analysis',1,8,1,1,1,NULL),(36,'Overall rating of lecturer in the classroom/during lecture',1,9,1,1,1,NULL),(37,'Overall rating of the lecturer during practical/clinical sessions',1,9,1,1,1,NULL),(38,'Would you recommend this course to any other group of students?',1,11,3,1,1,NULL),(39,'What proportions of classes do you attend?',1,10,4,1,1,NULL),(40,'In a normal class/practical sesison, what portion of class members are present?',1,10,4,1,1,NULL),(41,'What are the reasons for the level of attendance you have reported above?',2,10,NULL,1,1,NULL),(42,'What is your overall view of the course?',2,11,NULL,1,1,NULL),(43,'Your lecturer would like to know if there are certain aspects you believe he/she has done especially well in his/her teaching of this course?',3,11,NULL,1,1,NULL),(44,'Your lecturer would also like to know what specific things you believe must be done to improvehis/her teaching in the course?',3,11,NULL,1,1,NULL),(45,'What other materials or resources, if any, should be added to enhance understanding of the course contents?',3,11,NULL,1,1,NULL),(46,'Clarity of course objectives (Classroom/Clinical)',1,1,1,2,1,NULL),(47,'Achievement of course objectives',1,1,1,2,1,NULL),(48,'Relevance of course to programme objectives',1,1,1,2,1,NULL),(49,'Interpretation of concepts and theories',1,2,1,2,1,NULL),(50,'Coverage of course syllabus',1,2,1,2,1,NULL),(51,'Clarity in presentation',1,2,1,2,1,NULL),(52,'Effectiveness of presentation methods',1,2,1,2,1,NULL),(53,'Sufficiency of handouts',1,3,1,2,1,NULL),(54,'Value of recommended resource materials',1,3,1,2,1,NULL),(55,'Use of audio-visual and other teaching aids',1,3,1,2,1,NULL),(56,'Guidance on the use of web based material/journals',1,3,1,2,1,NULL),(57,'Adequacy of physical facilities',1,3,1,2,1,NULL),(58,'Sufficiency of computer(ICT) facility',1,3,1,2,1,NULL),(59,'Relevance of laboratory experiment(if any)',1,3,1,2,1,NULL),(60,'Relevance and usefulness of assignments/practicals/CATs',1,4,1,2,1,NULL),(61,'Appropriate coursework assessement',1,4,1,2,1,NULL),(62,'Satisfaction with methods of evaluation for classroom theory',1,4,1,2,1,NULL),(63,'Satisfaction with methods of assessment for practicals',1,4,1,2,1,NULL),(64,'Attends class regularly',1,5,1,2,1,NULL),(65,'Keeps to the published timetable',1,5,1,2,1,NULL),(66,'Is available for consultation when necessary(outside class time)',1,5,1,2,1,NULL),(67,'Guidance in practical lessons(e.g. Nursing)',1,5,1,2,1,NULL),(68,'Explains the scope, recommended readings, delivery and evaluation methodology of the course',1,6,1,2,1,NULL),(69,'Uses organized, up-to-date notes and course materials',1,6,1,2,1,NULL),(70,'Manages time well(punctual, uses class time efficiently)',1,6,1,2,1,NULL),(71,'Demonstration of procedures in the practical sessions',1,6,1,2,1,NULL),(72,'Presents course concepts and theories in a clear and interesting way',1,7,1,2,1,NULL),(73,'Facilitates meaningful and active class participation by students',1,7,1,2,1,NULL),(74,'Answers questions clearly and knowledgeably',1,7,1,2,1,NULL),(75,'Uses relevant examples and illustrations in the class/practical',1,7,1,2,1,NULL),(76,'Is open to diverse viewpoints and opinions',1,7,1,2,1,NULL),(77,'Gives relevant and challenging assignments and tests',1,8,1,2,1,NULL),(78,'Marks assignments and tests promptly',1,8,1,2,1,NULL),(79,'Gives helpful feedback on assignments and tests',1,8,1,2,1,NULL),(80,'Use of case studies for analysis',1,8,1,2,1,NULL),(81,'Overall rating of lecturer in the classroom/during lecture',1,9,1,2,1,NULL),(82,'Overall rating of the lecturer during practical/clinical sessions',1,9,1,2,1,NULL),(83,'What proportions of classes do you attend?',1,10,4,2,1,NULL),(84,'In a normal class/practical sesison, what portion of class members are present?',1,10,4,2,1,NULL),(85,'What are the reasons for the level of attendance you have reported above?',2,10,NULL,2,1,NULL),(86,'Your lecturer would like to know if there are certain aspects you believe he/she has done especially well in his/her teaching of this course?',3,11,NULL,2,1,NULL),(87,'Your lecturer would also like to know what specific things you believe must be done to improvehis/her teaching in the course?',3,11,NULL,2,1,NULL),(88,'What other materials or resources, if any, should be added to enhance understanding of the course contents?',3,11,NULL,2,1,NULL),(89,'What is your overall view of the course?',2,11,NULL,2,1,NULL),(90,'Would you recommend this course to any other group of students?',1,11,3,2,1,NULL),(91,'Clarity of course objectives (Classroom/Clinical)',1,1,1,3,1,NULL),(92,'Achievement of course objectives',1,1,1,3,1,NULL),(93,'Relevance of course to programme objectives',1,1,1,3,1,NULL),(94,'Interpretation of concepts and theories',1,2,1,3,1,NULL),(95,'Coverage of course syllabus',1,2,1,3,1,NULL),(96,'Clarity in presentation',1,2,1,3,1,NULL),(97,'Effectiveness of presentation methods',1,2,1,3,1,NULL),(98,'Sufficiency of handouts',1,3,1,3,1,NULL),(99,'Value of recommended resource materials',1,3,1,3,1,NULL),(100,'Use of audio-visual and other teaching aids',1,3,1,3,1,NULL),(101,'Guidance on the use of web based material/journals',1,3,1,3,1,NULL),(102,'Adequacy of physical facilities',1,3,1,3,1,NULL),(103,'Sufficiency of computer(ICT) facility',1,3,1,3,1,NULL),(104,'Relevance of laboratory experiment(if any)',1,3,1,3,1,NULL),(105,'Relevance and usefulness of assignments/practicals/CATs',1,4,1,3,1,NULL),(106,'Appropriate coursework assessement',1,4,1,3,1,NULL),(107,'Satisfaction with methods of evaluation for classroom theory',1,4,1,3,1,NULL),(108,'Satisfaction with methods of assessment for practicals',1,4,1,3,1,NULL),(109,'Attends class regularly',1,5,1,3,1,NULL),(110,'Keeps to the published timetable',1,5,1,3,1,NULL),(111,'Is available for consultation when necessary(outside class time)',1,5,1,3,1,NULL),(112,'Guidance in practical lessons(e.g. Nursing)',1,5,1,3,1,NULL),(113,'Explains the scope, recommended readings, delivery and evaluation methodology of the course',1,6,1,3,1,NULL),(114,'Uses organized, up-to-date notes and course materials',1,6,1,3,1,NULL),(115,'Manages time well(punctual, uses class time efficiently)',1,6,1,3,1,NULL),(116,'Demonstration of procedures in the practical sessions',1,6,1,3,1,NULL),(117,'Presents course concepts and theories in a clear and interesting way',1,7,1,3,1,NULL),(118,'Facilitates meaningful and active class participation by students',1,7,1,3,1,NULL),(119,'Answers questions clearly and knowledgeably',1,7,1,3,1,NULL),(120,'Uses relevant examples and illustrations in the class/practical',1,7,1,3,1,NULL),(121,'Is open to diverse viewpoints and opinions',1,7,1,3,1,NULL),(122,'Gives relevant and challenging assignments and tests',1,8,1,3,1,NULL),(123,'Marks assignments and tests promptly',1,8,1,3,1,NULL),(124,'Gives helpful feedback on assignments and tests',1,8,1,3,1,NULL),(125,'Use of case studies for analysis',1,8,1,3,1,NULL),(126,'Overall rating of lecturer in the classroom/during lecture',1,9,1,3,1,NULL),(127,'Overall rating of the lecturer during practical/clinical sessions',1,9,1,3,1,NULL),(128,'What proportions of classes do you attend?',1,10,4,3,1,NULL),(129,'In a normal class/practical sesison, what portion of class members are present?',1,10,4,3,1,NULL),(130,'What are the reasons for the level of attendance you have reported above?',2,10,NULL,3,1,NULL),(131,'Your lecturer would like to know if there are certain aspects you believe he/she has done especially well in his/her teaching of this course?',3,11,NULL,3,1,NULL),(132,'Your lecturer would also like to know what specific things you believe must be done to improvehis/her teaching in the course?',3,11,NULL,3,1,NULL),(133,'What other materials or resources, if any, should be added to enhance understanding of the course contents?',3,11,NULL,3,1,NULL),(134,'What is your overall view of the course?',2,11,NULL,3,1,NULL),(135,'Would you recommend this course to any other group of students?',1,11,3,3,1,NULL),(136,'Clarity of course objectives (Classroom/Clinical)',1,1,1,4,1,NULL),(137,'Achievement of course objectives',1,1,1,4,1,NULL),(138,'Relevance of course to programme objectives',1,1,1,4,1,NULL),(139,'Interpretation of concepts and theories',1,2,1,4,1,NULL),(140,'Coverage of course syllabus',1,2,1,4,1,NULL),(141,'Clarity in presentation',1,2,1,4,1,NULL),(142,'Effectiveness of presentation methods',1,2,1,4,1,NULL),(143,'Sufficiency of handouts',1,3,1,4,1,NULL),(144,'Value of recommended resource materials',1,3,1,4,1,NULL),(145,'Use of audio-visual and other teaching aids',1,3,1,4,1,NULL),(146,'Guidance on the use of web based material/journals',1,3,1,4,1,NULL),(147,'Adequacy of physical facilities',1,3,1,4,1,NULL),(148,'Sufficiency of computer(ICT) facility',1,3,1,4,1,NULL),(149,'Relevance of laboratory experiment(if any)',1,3,1,4,1,NULL),(150,'Relevance and usefulness of assignments/practicals/CATs',1,4,1,4,1,NULL),(151,'Appropriate coursework assessement',1,4,1,4,1,NULL),(152,'Satisfaction with methods of evaluation for classroom theory',1,4,1,4,1,NULL),(153,'Satisfaction with methods of assessment for practicals',1,4,1,4,1,NULL),(154,'Attends class regularly',1,5,1,4,1,NULL),(155,'Keeps to the published timetable',1,5,1,4,1,NULL),(156,'Is available for consultation when necessary(outside class time)',1,5,1,4,1,NULL),(157,'Guidance in practical lessons(e.g. Nursing)',1,5,1,4,1,NULL),(158,'Explains the scope, recommended readings, delivery and evaluation methodology of the course',1,6,1,4,1,NULL),(159,'Uses organized, up-to-date notes and course materials',1,6,1,4,1,NULL),(160,'Manages time well(punctual, uses class time efficiently)',1,6,1,4,1,NULL),(161,'Demonstration of procedures in the practical sessions',1,6,1,4,1,NULL),(162,'Presents course concepts and theories in a clear and interesting way',1,7,1,4,1,NULL),(163,'Facilitates meaningful and active class participation by students',1,7,1,4,1,NULL),(164,'Answers questions clearly and knowledgeably',1,7,1,4,1,NULL),(165,'Uses relevant examples and illustrations in the class/practical',1,7,1,4,1,NULL),(166,'Is open to diverse viewpoints and opinions',1,7,1,4,1,NULL),(167,'Gives relevant and challenging assignments and tests',1,8,1,4,1,NULL),(168,'Marks assignments and tests promptly',1,8,1,4,1,NULL),(169,'Gives helpful feedback on assignments and tests',1,8,1,4,1,NULL),(170,'Use of case studies for analysis',1,8,1,4,1,NULL),(171,'Overall rating of lecturer in the classroom/during lecture',1,9,1,4,1,NULL),(172,'Overall rating of the lecturer during practical/clinical sessions',1,9,1,4,1,NULL),(173,'What proportions of classes do you attend?',1,10,4,4,1,NULL),(174,'In a normal class/practical sesison, what portion of class members are present?',1,10,4,4,1,NULL),(175,'What are the reasons for the level of attendance you have reported above?',2,10,NULL,4,1,NULL),(176,'Your lecturer would like to know if there are certain aspects you believe he/she has done especially well in his/her teaching of this course?',3,11,NULL,4,1,NULL),(177,'Your lecturer would also like to know what specific things you believe must be done to improvehis/her teaching in the course?',3,11,NULL,4,1,NULL),(178,'What other materials or resources, if any, should be added to enhance understanding of the course contents?',3,11,NULL,4,1,NULL),(179,'What is your overall view of the course?',2,11,NULL,4,1,NULL),(180,'Would you recommend this course to any other group of students?',1,11,3,4,1,NULL);
/*!40000 ALTER TABLE `evaluated_question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `evaluated_question_answer`
--

DROP TABLE IF EXISTS `evaluated_question_answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `evaluated_question_answer` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `rating` varchar(20) DEFAULT NULL,
  `reasoning` varchar(200) DEFAULT NULL,
  `comment1` varchar(200) DEFAULT NULL,
  `comment2` varchar(200) DEFAULT NULL,
  `comment3` varchar(200) DEFAULT NULL,
  `evaluated_question` int(10) unsigned NOT NULL,
  `course_of_instance` int(10) unsigned NOT NULL,
  `evaluation_instance` int(10) unsigned NOT NULL,
  `active` tinyint(1) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_evaluated_question_course_of_instance1_idx` (`course_of_instance`),
  KEY `fk_evaluated_question_evaluation_instance1_idx` (`evaluation_instance`),
  KEY `fk_evaluated_question_answer_evaluated_question1_idx` (`evaluated_question`),
  CONSTRAINT `fk_evaluated_question_answer_evaluated_question1` FOREIGN KEY (`evaluated_question`) REFERENCES `evaluated_question` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_evaluated_question_course_of_instance1` FOREIGN KEY (`course_of_instance`) REFERENCES `course_of_instance` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_evaluated_question_evaluation_instance1` FOREIGN KEY (`evaluation_instance`) REFERENCES `evaluation_instance` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=541 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evaluated_question_answer`
--

LOCK TABLES `evaluated_question_answer` WRITE;
/*!40000 ALTER TABLE `evaluated_question_answer` DISABLE KEYS */;
INSERT INTO `evaluated_question_answer` VALUES (1,'N/A',NULL,NULL,NULL,NULL,1,1,1,1,NULL),(2,'2',NULL,NULL,NULL,NULL,2,1,1,1,NULL),(3,'3',NULL,NULL,NULL,NULL,3,1,1,1,NULL),(4,'4',NULL,NULL,NULL,NULL,4,1,1,1,NULL),(5,'5',NULL,NULL,NULL,NULL,5,1,1,1,NULL),(6,'4',NULL,NULL,NULL,NULL,6,1,1,1,NULL),(7,'4',NULL,NULL,NULL,NULL,7,1,1,1,NULL),(8,'5',NULL,NULL,NULL,NULL,8,1,1,1,NULL),(9,'4',NULL,NULL,NULL,NULL,9,1,1,1,NULL),(10,'4',NULL,NULL,NULL,NULL,10,1,1,1,NULL),(11,'5',NULL,NULL,NULL,NULL,11,1,1,1,NULL),(12,'4',NULL,NULL,NULL,NULL,12,1,1,1,NULL),(13,'4',NULL,NULL,NULL,NULL,13,1,1,1,NULL),(14,'5',NULL,NULL,NULL,NULL,14,1,1,1,NULL),(15,'4',NULL,NULL,NULL,NULL,15,1,1,1,NULL),(16,'5',NULL,NULL,NULL,NULL,16,1,1,1,NULL),(17,'5',NULL,NULL,NULL,NULL,17,1,1,1,NULL),(18,'4',NULL,NULL,NULL,NULL,18,1,1,1,NULL),(19,'5',NULL,NULL,NULL,NULL,19,1,1,1,NULL),(20,'5',NULL,NULL,NULL,NULL,20,1,1,1,NULL),(21,'4',NULL,NULL,NULL,NULL,21,1,1,1,NULL),(22,'4',NULL,NULL,NULL,NULL,22,1,1,1,NULL),(23,'5',NULL,NULL,NULL,NULL,23,1,1,1,NULL),(24,'4',NULL,NULL,NULL,NULL,24,1,1,1,NULL),(25,NULL,NULL,NULL,NULL,NULL,25,1,1,1,NULL),(26,'3',NULL,NULL,NULL,NULL,26,1,1,1,NULL),(27,'3',NULL,NULL,NULL,NULL,27,1,1,1,NULL),(28,'4',NULL,NULL,NULL,NULL,28,1,1,1,NULL),(29,'4',NULL,NULL,NULL,NULL,29,1,1,1,NULL),(30,'3',NULL,NULL,NULL,NULL,30,1,1,1,NULL),(31,'4',NULL,NULL,NULL,NULL,31,1,1,1,NULL),(32,'4',NULL,NULL,NULL,NULL,32,1,1,1,NULL),(33,'3',NULL,NULL,NULL,NULL,33,1,1,1,NULL),(34,'4',NULL,NULL,NULL,NULL,34,1,1,1,NULL),(35,'5',NULL,NULL,NULL,NULL,35,1,1,1,NULL),(36,'5',NULL,NULL,NULL,NULL,36,1,1,1,NULL),(37,'4',NULL,NULL,NULL,NULL,37,1,1,1,NULL),(38,'100%',NULL,NULL,NULL,NULL,39,1,1,1,NULL),(39,'75 - 99%',NULL,NULL,NULL,NULL,40,1,1,1,NULL),(40,NULL,'Interesting classes',NULL,NULL,NULL,41,1,1,1,NULL),(41,'Yes',NULL,NULL,NULL,NULL,38,1,1,1,NULL),(42,NULL,'It is important ',NULL,NULL,NULL,42,1,1,1,NULL),(43,NULL,NULL,'Well elaborated examples','Proper explanation of new concepts','',43,1,1,1,NULL),(44,NULL,NULL,'Give more programming assignments','Use more flow charts','',44,1,1,1,NULL),(45,NULL,NULL,'Wifi in the classroom','','',45,1,1,1,NULL),(46,'N/A',NULL,NULL,NULL,NULL,1,2,1,1,NULL),(47,'2',NULL,NULL,NULL,NULL,2,2,1,1,NULL),(48,'2',NULL,NULL,NULL,NULL,3,2,1,1,NULL),(49,'4',NULL,NULL,NULL,NULL,4,2,1,1,NULL),(50,'5',NULL,NULL,NULL,NULL,5,2,1,1,NULL),(51,'4',NULL,NULL,NULL,NULL,6,2,1,1,NULL),(52,'4',NULL,NULL,NULL,NULL,7,2,1,1,NULL),(53,'3',NULL,NULL,NULL,NULL,8,2,1,1,NULL),(54,'2',NULL,NULL,NULL,NULL,9,2,1,1,NULL),(55,'3',NULL,NULL,NULL,NULL,10,2,1,1,NULL),(56,'5',NULL,NULL,NULL,NULL,11,2,1,1,NULL),(57,'3',NULL,NULL,NULL,NULL,12,2,1,1,NULL),(58,'2',NULL,NULL,NULL,NULL,13,2,1,1,NULL),(59,'2',NULL,NULL,NULL,NULL,14,2,1,1,NULL),(60,'2',NULL,NULL,NULL,NULL,15,2,1,1,NULL),(61,'3',NULL,NULL,NULL,NULL,16,2,1,1,NULL),(62,'3',NULL,NULL,NULL,NULL,17,2,1,1,NULL),(63,'3',NULL,NULL,NULL,NULL,18,2,1,1,NULL),(64,'2',NULL,NULL,NULL,NULL,19,2,1,1,NULL),(65,'2',NULL,NULL,NULL,NULL,20,2,1,1,NULL),(66,'5',NULL,NULL,NULL,NULL,21,2,1,1,NULL),(67,'2',NULL,NULL,NULL,NULL,22,2,1,1,NULL),(68,'2',NULL,NULL,NULL,NULL,23,2,1,1,NULL),(69,'3',NULL,NULL,NULL,NULL,24,2,1,1,NULL),(70,'2',NULL,NULL,NULL,NULL,25,2,1,1,NULL),(71,'5',NULL,NULL,NULL,NULL,26,2,1,1,NULL),(72,'5',NULL,NULL,NULL,NULL,27,2,1,1,NULL),(73,'4',NULL,NULL,NULL,NULL,28,2,1,1,NULL),(74,'3',NULL,NULL,NULL,NULL,29,2,1,1,NULL),(75,'3',NULL,NULL,NULL,NULL,30,2,1,1,NULL),(76,'2',NULL,NULL,NULL,NULL,31,2,1,1,NULL),(77,'5',NULL,NULL,NULL,NULL,32,2,1,1,NULL),(78,'5',NULL,NULL,NULL,NULL,33,2,1,1,NULL),(79,'4',NULL,NULL,NULL,NULL,34,2,1,1,NULL),(80,'4',NULL,NULL,NULL,NULL,35,2,1,1,NULL),(81,'5',NULL,NULL,NULL,NULL,36,2,1,1,NULL),(82,'5',NULL,NULL,NULL,NULL,37,2,1,1,NULL),(83,'75 - 99%',NULL,NULL,NULL,NULL,39,2,1,1,NULL),(84,'75 - 99%',NULL,NULL,NULL,NULL,40,2,1,1,NULL),(85,NULL,'Classes were interesting',NULL,NULL,NULL,41,2,1,1,NULL),(86,'Yes',NULL,NULL,NULL,NULL,38,2,1,1,NULL),(87,NULL,'Very useful course',NULL,NULL,NULL,42,2,1,1,NULL),(88,NULL,NULL,'Proper illustration ','Good mastery of the concepts','|Interesting in class',43,2,1,1,NULL),(89,NULL,NULL,'Give more assignments','Case studies of the operating system','',44,2,1,1,NULL),(90,NULL,NULL,'Wifi in the classroom','','',45,2,1,1,NULL),(91,'N/A',NULL,NULL,NULL,NULL,1,3,2,1,NULL),(92,'2',NULL,NULL,NULL,NULL,2,3,2,1,NULL),(93,'4',NULL,NULL,NULL,NULL,3,3,2,1,NULL),(94,'3',NULL,NULL,NULL,NULL,4,3,2,1,NULL),(95,'5',NULL,NULL,NULL,NULL,5,3,2,1,NULL),(96,'3',NULL,NULL,NULL,NULL,6,3,2,1,NULL),(97,'4',NULL,NULL,NULL,NULL,7,3,2,1,NULL),(98,'4',NULL,NULL,NULL,NULL,8,3,2,1,NULL),(99,'5',NULL,NULL,NULL,NULL,9,3,2,1,NULL),(100,'4',NULL,NULL,NULL,NULL,10,3,2,1,NULL),(101,'4',NULL,NULL,NULL,NULL,11,3,2,1,NULL),(102,'5',NULL,NULL,NULL,NULL,12,3,2,1,NULL),(103,'4',NULL,NULL,NULL,NULL,13,3,2,1,NULL),(104,'5',NULL,NULL,NULL,NULL,14,3,2,1,NULL),(105,'5',NULL,NULL,NULL,NULL,15,3,2,1,NULL),(106,'4',NULL,NULL,NULL,NULL,16,3,2,1,NULL),(107,'5',NULL,NULL,NULL,NULL,17,3,2,1,NULL),(108,'5',NULL,NULL,NULL,NULL,18,3,2,1,NULL),(109,'4',NULL,NULL,NULL,NULL,19,3,2,1,NULL),(110,'5',NULL,NULL,NULL,NULL,20,3,2,1,NULL),(111,'4',NULL,NULL,NULL,NULL,21,3,2,1,NULL),(112,'5',NULL,NULL,NULL,NULL,22,3,2,1,NULL),(113,'4',NULL,NULL,NULL,NULL,23,3,2,1,NULL),(114,'5',NULL,NULL,NULL,NULL,24,3,2,1,NULL),(115,'4',NULL,NULL,NULL,NULL,25,3,2,1,NULL),(116,'5',NULL,NULL,NULL,NULL,26,3,2,1,NULL),(117,'4',NULL,NULL,NULL,NULL,27,3,2,1,NULL),(118,'5',NULL,NULL,NULL,NULL,28,3,2,1,NULL),(119,'5',NULL,NULL,NULL,NULL,29,3,2,1,NULL),(120,'4',NULL,NULL,NULL,NULL,30,3,2,1,NULL),(121,'5',NULL,NULL,NULL,NULL,31,3,2,1,NULL),(122,'5',NULL,NULL,NULL,NULL,32,3,2,1,NULL),(123,'4',NULL,NULL,NULL,NULL,33,3,2,1,NULL),(124,'3',NULL,NULL,NULL,NULL,34,3,2,1,NULL),(125,'3',NULL,NULL,NULL,NULL,35,3,2,1,NULL),(126,'4',NULL,NULL,NULL,NULL,36,3,2,1,NULL),(127,'4',NULL,NULL,NULL,NULL,37,3,2,1,NULL),(128,'75 - 99%',NULL,NULL,NULL,NULL,39,3,2,1,NULL),(129,'75 - 99%',NULL,NULL,NULL,NULL,40,3,2,1,NULL),(130,NULL,'The classes were important',NULL,NULL,NULL,41,3,2,1,NULL),(131,'Yes',NULL,NULL,NULL,NULL,38,3,2,1,NULL),(132,NULL,'The course is vital for the programme',NULL,NULL,NULL,42,3,2,1,NULL),(133,NULL,NULL,'Well elaborated examples','Explained new concepts clearly','The lecturer was interesting',43,3,2,1,NULL),(134,NULL,NULL,'Show more object oriented programs','Give challenging assignments','',44,3,2,1,NULL),(135,NULL,NULL,'Flowcharts be used','','',45,3,2,1,NULL),(136,'2',NULL,NULL,NULL,NULL,1,4,2,1,NULL),(137,'3',NULL,NULL,NULL,NULL,2,4,2,1,NULL),(138,'4',NULL,NULL,NULL,NULL,3,4,2,1,NULL),(139,'4',NULL,NULL,NULL,NULL,4,4,2,1,NULL),(140,'3',NULL,NULL,NULL,NULL,5,4,2,1,NULL),(141,'4',NULL,NULL,NULL,NULL,6,4,2,1,NULL),(142,'5',NULL,NULL,NULL,NULL,7,4,2,1,NULL),(143,'4',NULL,NULL,NULL,NULL,8,4,2,1,NULL),(144,'5',NULL,NULL,NULL,NULL,9,4,2,1,NULL),(145,'5',NULL,NULL,NULL,NULL,10,4,2,1,NULL),(146,'4',NULL,NULL,NULL,NULL,11,4,2,1,NULL),(147,'4',NULL,NULL,NULL,NULL,12,4,2,1,NULL),(148,'5',NULL,NULL,NULL,NULL,13,4,2,1,NULL),(149,'5',NULL,NULL,NULL,NULL,14,4,2,1,NULL),(150,'4',NULL,NULL,NULL,NULL,15,4,2,1,NULL),(151,'5',NULL,NULL,NULL,NULL,16,4,2,1,NULL),(152,'4',NULL,NULL,NULL,NULL,17,4,2,1,NULL),(153,'5',NULL,NULL,NULL,NULL,18,4,2,1,NULL),(154,'4',NULL,NULL,NULL,NULL,19,4,2,1,NULL),(155,'5',NULL,NULL,NULL,NULL,20,4,2,1,NULL),(156,'5',NULL,NULL,NULL,NULL,21,4,2,1,NULL),(157,'4',NULL,NULL,NULL,NULL,22,4,2,1,NULL),(158,'5',NULL,NULL,NULL,NULL,23,4,2,1,NULL),(159,'5',NULL,NULL,NULL,NULL,24,4,2,1,NULL),(160,'4',NULL,NULL,NULL,NULL,25,4,2,1,NULL),(161,'4',NULL,NULL,NULL,NULL,26,4,2,1,NULL),(162,'5',NULL,NULL,NULL,NULL,27,4,2,1,NULL),(163,'4',NULL,NULL,NULL,NULL,28,4,2,1,NULL),(164,'5',NULL,NULL,NULL,NULL,29,4,2,1,NULL),(165,'5',NULL,NULL,NULL,NULL,30,4,2,1,NULL),(166,'4',NULL,NULL,NULL,NULL,31,4,2,1,NULL),(167,'4',NULL,NULL,NULL,NULL,32,4,2,1,NULL),(168,'5',NULL,NULL,NULL,NULL,33,4,2,1,NULL),(169,'5',NULL,NULL,NULL,NULL,34,4,2,1,NULL),(170,'4',NULL,NULL,NULL,NULL,35,4,2,1,NULL),(171,'5',NULL,NULL,NULL,NULL,36,4,2,1,NULL),(172,'4',NULL,NULL,NULL,NULL,37,4,2,1,NULL),(173,'75 - 99%',NULL,NULL,NULL,NULL,39,4,2,1,NULL),(174,'75 - 99%',NULL,NULL,NULL,NULL,40,4,2,1,NULL),(175,NULL,'It is almost impossible to attend all classes, hence students attended as much of the classes as they could',NULL,NULL,NULL,41,4,2,1,NULL),(176,'Yes',NULL,NULL,NULL,NULL,38,4,2,1,NULL),(177,NULL,'This course is magnificent',NULL,NULL,NULL,42,4,2,1,NULL),(178,NULL,NULL,'The lecturer taught well and I understood the course concepts very well','The lecturer answered questions posed by students well','The lecturer kept their classes interactive',43,4,2,1,NULL),(179,NULL,NULL,'The lecturer could give more assignments','The lecturer needs handle','Suggest further reading',44,4,2,1,NULL),(180,NULL,NULL,'Pictorials','Course books','',45,4,2,1,NULL),(181,'N/A',NULL,NULL,NULL,NULL,1,5,2,1,NULL),(182,'2',NULL,NULL,NULL,NULL,2,5,2,1,NULL),(183,'3',NULL,NULL,NULL,NULL,3,5,2,1,NULL),(184,'4',NULL,NULL,NULL,NULL,4,5,2,1,NULL),(185,'5',NULL,NULL,NULL,NULL,5,5,2,1,NULL),(186,'4',NULL,NULL,NULL,NULL,6,5,2,1,NULL),(187,'4',NULL,NULL,NULL,NULL,7,5,2,1,NULL),(188,'5',NULL,NULL,NULL,NULL,8,5,2,1,NULL),(189,'4',NULL,NULL,NULL,NULL,9,5,2,1,NULL),(190,'4',NULL,NULL,NULL,NULL,10,5,2,1,NULL),(191,'4',NULL,NULL,NULL,NULL,11,5,2,1,NULL),(192,'5',NULL,NULL,NULL,NULL,12,5,2,1,NULL),(193,'5',NULL,NULL,NULL,NULL,13,5,2,1,NULL),(194,'4',NULL,NULL,NULL,NULL,14,5,2,1,NULL),(195,'4',NULL,NULL,NULL,NULL,15,5,2,1,NULL),(196,'5',NULL,NULL,NULL,NULL,16,5,2,1,NULL),(197,'4',NULL,NULL,NULL,NULL,17,5,2,1,NULL),(198,'5',NULL,NULL,NULL,NULL,18,5,2,1,NULL),(199,'4',NULL,NULL,NULL,NULL,19,5,2,1,NULL),(200,'5',NULL,NULL,NULL,NULL,20,5,2,1,NULL),(201,'4',NULL,NULL,NULL,NULL,21,5,2,1,NULL),(202,'4',NULL,NULL,NULL,NULL,22,5,2,1,NULL),(203,'5',NULL,NULL,NULL,NULL,23,5,2,1,NULL),(204,'5',NULL,NULL,NULL,NULL,24,5,2,1,NULL),(205,'4',NULL,NULL,NULL,NULL,25,5,2,1,NULL),(206,'5',NULL,NULL,NULL,NULL,26,5,2,1,NULL),(207,'4',NULL,NULL,NULL,NULL,27,5,2,1,NULL),(208,'4',NULL,NULL,NULL,NULL,28,5,2,1,NULL),(209,'5',NULL,NULL,NULL,NULL,29,5,2,1,NULL),(210,'4',NULL,NULL,NULL,NULL,30,5,2,1,NULL),(211,'5',NULL,NULL,NULL,NULL,31,5,2,1,NULL),(212,'4',NULL,NULL,NULL,NULL,32,5,2,1,NULL),(213,'4',NULL,NULL,NULL,NULL,33,5,2,1,NULL),(214,'5',NULL,NULL,NULL,NULL,34,5,2,1,NULL),(215,'4',NULL,NULL,NULL,NULL,35,5,2,1,NULL),(216,'4',NULL,NULL,NULL,NULL,36,5,2,1,NULL),(217,'5',NULL,NULL,NULL,NULL,37,5,2,1,NULL),(218,'50 - 74%',NULL,NULL,NULL,NULL,39,5,2,1,NULL),(219,'50 - 74%',NULL,NULL,NULL,NULL,40,5,2,1,NULL),(220,NULL,'The lectures were quite boring',NULL,NULL,NULL,41,5,2,1,NULL),(221,'Yes',NULL,NULL,NULL,NULL,38,5,2,1,NULL),(222,NULL,'Important course',NULL,NULL,NULL,42,5,2,1,NULL),(223,NULL,NULL,'The lecturer explained the questions asked properly','The lecturer\'s noted were comprehensive','The lecturer always made sure concepts were understood',43,5,2,1,NULL),(224,NULL,NULL,'The lecturer could make her classes more intereactive','The lecturer could give more assignments','',44,5,2,1,NULL),(225,NULL,NULL,'Wifi in the classroom','','',45,5,2,1,NULL),(226,'N/A',NULL,NULL,NULL,NULL,1,6,1,1,NULL),(227,'2',NULL,NULL,NULL,NULL,2,6,1,1,NULL),(228,'3',NULL,NULL,NULL,NULL,3,6,1,1,NULL),(229,'4',NULL,NULL,NULL,NULL,4,6,1,1,NULL),(230,'4',NULL,NULL,NULL,NULL,5,6,1,1,NULL),(231,'5',NULL,NULL,NULL,NULL,6,6,1,1,NULL),(232,'4',NULL,NULL,NULL,NULL,7,6,1,1,NULL),(233,'5',NULL,NULL,NULL,NULL,8,6,1,1,NULL),(234,'4',NULL,NULL,NULL,NULL,9,6,1,1,NULL),(235,'5',NULL,NULL,NULL,NULL,10,6,1,1,NULL),(236,'4',NULL,NULL,NULL,NULL,11,6,1,1,NULL),(237,'5',NULL,NULL,NULL,NULL,12,6,1,1,NULL),(238,'4',NULL,NULL,NULL,NULL,13,6,1,1,NULL),(239,'5',NULL,NULL,NULL,NULL,14,6,1,1,NULL),(240,'5',NULL,NULL,NULL,NULL,15,6,1,1,NULL),(241,'4',NULL,NULL,NULL,NULL,16,6,1,1,NULL),(242,'5',NULL,NULL,NULL,NULL,17,6,1,1,NULL),(243,'5',NULL,NULL,NULL,NULL,18,6,1,1,NULL),(244,'4',NULL,NULL,NULL,NULL,19,6,1,1,NULL),(245,'5',NULL,NULL,NULL,NULL,20,6,1,1,NULL),(246,'5',NULL,NULL,NULL,NULL,21,6,1,1,NULL),(247,'4',NULL,NULL,NULL,NULL,22,6,1,1,NULL),(248,'5',NULL,NULL,NULL,NULL,23,6,1,1,NULL),(249,'4',NULL,NULL,NULL,NULL,24,6,1,1,NULL),(250,'5',NULL,NULL,NULL,NULL,25,6,1,1,NULL),(251,'5',NULL,NULL,NULL,NULL,26,6,1,1,NULL),(252,'4',NULL,NULL,NULL,NULL,27,6,1,1,NULL),(253,'4',NULL,NULL,NULL,NULL,28,6,1,1,NULL),(254,'5',NULL,NULL,NULL,NULL,29,6,1,1,NULL),(255,'5',NULL,NULL,NULL,NULL,30,6,1,1,NULL),(256,'4',NULL,NULL,NULL,NULL,31,6,1,1,NULL),(257,'4',NULL,NULL,NULL,NULL,32,6,1,1,NULL),(258,'5',NULL,NULL,NULL,NULL,33,6,1,1,NULL),(259,'4',NULL,NULL,NULL,NULL,34,6,1,1,NULL),(260,'5',NULL,NULL,NULL,NULL,35,6,1,1,NULL),(261,'4',NULL,NULL,NULL,NULL,36,6,1,1,NULL),(262,'4',NULL,NULL,NULL,NULL,37,6,1,1,NULL),(263,'50 - 74%',NULL,NULL,NULL,NULL,39,6,1,1,NULL),(264,'75 - 99%',NULL,NULL,NULL,NULL,40,6,1,1,NULL),(265,NULL,'pppp',NULL,NULL,NULL,41,6,1,1,NULL),(266,'Yes',NULL,NULL,NULL,NULL,38,6,1,1,NULL),(267,NULL,'ppppp',NULL,NULL,NULL,42,6,1,1,NULL),(268,NULL,NULL,'pppp','ppp','ppp',43,6,1,1,NULL),(269,NULL,NULL,'pp','pp','p',44,6,1,1,NULL),(270,NULL,NULL,'pp','pp','p',45,6,1,1,NULL),(271,'2',NULL,NULL,NULL,NULL,1,7,1,1,NULL),(272,'3',NULL,NULL,NULL,NULL,2,7,1,1,NULL),(273,'3',NULL,NULL,NULL,NULL,3,7,1,1,NULL),(274,'3',NULL,NULL,NULL,NULL,4,7,1,1,NULL),(275,'3',NULL,NULL,NULL,NULL,5,7,1,1,NULL),(276,'3',NULL,NULL,NULL,NULL,6,7,1,1,NULL),(277,'4',NULL,NULL,NULL,NULL,7,7,1,1,NULL),(278,'3',NULL,NULL,NULL,NULL,8,7,1,1,NULL),(279,'4',NULL,NULL,NULL,NULL,9,7,1,1,NULL),(280,'4',NULL,NULL,NULL,NULL,10,7,1,1,NULL),(281,'4',NULL,NULL,NULL,NULL,11,7,1,1,NULL),(282,'4',NULL,NULL,NULL,NULL,12,7,1,1,NULL),(283,'4',NULL,NULL,NULL,NULL,13,7,1,1,NULL),(284,'4',NULL,NULL,NULL,NULL,14,7,1,1,NULL),(285,'3',NULL,NULL,NULL,NULL,15,7,1,1,NULL),(286,'3',NULL,NULL,NULL,NULL,16,7,1,1,NULL),(287,'3',NULL,NULL,NULL,NULL,17,7,1,1,NULL),(288,'3',NULL,NULL,NULL,NULL,18,7,1,1,NULL),(289,'4',NULL,NULL,NULL,NULL,19,7,1,1,NULL),(290,'4',NULL,NULL,NULL,NULL,20,7,1,1,NULL),(291,'4',NULL,NULL,NULL,NULL,21,7,1,1,NULL),(292,'4',NULL,NULL,NULL,NULL,22,7,1,1,NULL),(293,'4',NULL,NULL,NULL,NULL,23,7,1,1,NULL),(294,'4',NULL,NULL,NULL,NULL,24,7,1,1,NULL),(295,'4',NULL,NULL,NULL,NULL,25,7,1,1,NULL),(296,'4',NULL,NULL,NULL,NULL,26,7,1,1,NULL),(297,'3',NULL,NULL,NULL,NULL,27,7,1,1,NULL),(298,'3',NULL,NULL,NULL,NULL,28,7,1,1,NULL),(299,'3',NULL,NULL,NULL,NULL,29,7,1,1,NULL),(300,'3',NULL,NULL,NULL,NULL,30,7,1,1,NULL),(301,'3',NULL,NULL,NULL,NULL,31,7,1,1,NULL),(302,'4',NULL,NULL,NULL,NULL,32,7,1,1,NULL),(303,'4',NULL,NULL,NULL,NULL,33,7,1,1,NULL),(304,'4',NULL,NULL,NULL,NULL,34,7,1,1,NULL),(305,'4',NULL,NULL,NULL,NULL,35,7,1,1,NULL),(306,'3',NULL,NULL,NULL,NULL,36,7,1,1,NULL),(307,'3',NULL,NULL,NULL,NULL,37,7,1,1,NULL),(308,'75 - 99%',NULL,NULL,NULL,NULL,39,7,1,1,NULL),(309,'75 - 99%',NULL,NULL,NULL,NULL,40,7,1,1,NULL),(310,NULL,'All classes were important but a miss of one or two is almost never possible.\r\nMyself, I called in sick for two classes. Otherwise I made sure I made to class in time for the rest',NULL,NULL,NULL,41,7,1,1,NULL),(311,NULL,NULL,'The lecturer was patient to let the students understand what she taught','Gave a couple of assignments','The lecturer gave enough suggestions for further study',43,7,1,1,NULL),(312,NULL,NULL,'The lecturer should be in a more cheerful mood during her classes','Take the students through coding; not basic lecturing','Every student should be given a chance to perform those practicals',44,7,1,1,NULL),(313,NULL,NULL,'Pictorials illustrating the concepts','New computers and accessories',' Wifi should be availed in the classroom',45,7,1,1,NULL),(314,NULL,'The course is utterly and completely necessary for a sitting Computer Science student and anyone working in the field requires the knowledge taught in this class',NULL,NULL,NULL,42,7,1,1,NULL),(315,'Yes',NULL,NULL,NULL,NULL,38,7,1,1,NULL),(316,'3',NULL,NULL,NULL,NULL,1,8,2,1,NULL),(317,'4',NULL,NULL,NULL,NULL,2,8,2,1,NULL),(318,'5',NULL,NULL,NULL,NULL,3,8,2,1,NULL),(319,'4',NULL,NULL,NULL,NULL,4,8,2,1,NULL),(320,'5',NULL,NULL,NULL,NULL,5,8,2,1,NULL),(321,'5',NULL,NULL,NULL,NULL,6,8,2,1,NULL),(322,'5',NULL,NULL,NULL,NULL,7,8,2,1,NULL),(323,'5',NULL,NULL,NULL,NULL,8,8,2,1,NULL),(324,'4',NULL,NULL,NULL,NULL,9,8,2,1,NULL),(325,'5',NULL,NULL,NULL,NULL,10,8,2,1,NULL),(326,'4',NULL,NULL,NULL,NULL,11,8,2,1,NULL),(327,'4',NULL,NULL,NULL,NULL,12,8,2,1,NULL),(328,'5',NULL,NULL,NULL,NULL,13,8,2,1,NULL),(329,'4',NULL,NULL,NULL,NULL,14,8,2,1,NULL),(330,'5',NULL,NULL,NULL,NULL,15,8,2,1,NULL),(331,'4',NULL,NULL,NULL,NULL,16,8,2,1,NULL),(332,'5',NULL,NULL,NULL,NULL,17,8,2,1,NULL),(333,'4',NULL,NULL,NULL,NULL,18,8,2,1,NULL),(334,'5',NULL,NULL,NULL,NULL,19,8,2,1,NULL),(335,'4',NULL,NULL,NULL,NULL,20,8,2,1,NULL),(336,'5',NULL,NULL,NULL,NULL,21,8,2,1,NULL),(337,'4',NULL,NULL,NULL,NULL,22,8,2,1,NULL),(338,'5',NULL,NULL,NULL,NULL,23,8,2,1,NULL),(339,'4',NULL,NULL,NULL,NULL,24,8,2,1,NULL),(340,'5',NULL,NULL,NULL,NULL,25,8,2,1,NULL),(341,'4',NULL,NULL,NULL,NULL,26,8,2,1,NULL),(342,'5',NULL,NULL,NULL,NULL,27,8,2,1,NULL),(343,'4',NULL,NULL,NULL,NULL,28,8,2,1,NULL),(344,'5',NULL,NULL,NULL,NULL,29,8,2,1,NULL),(345,'4',NULL,NULL,NULL,NULL,30,8,2,1,NULL),(346,'4',NULL,NULL,NULL,NULL,31,8,2,1,NULL),(347,'5',NULL,NULL,NULL,NULL,32,8,2,1,NULL),(348,'4',NULL,NULL,NULL,NULL,33,8,2,1,NULL),(349,'5',NULL,NULL,NULL,NULL,34,8,2,1,NULL),(350,'4',NULL,NULL,NULL,NULL,35,8,2,1,NULL),(351,'5',NULL,NULL,NULL,NULL,36,8,2,1,NULL),(352,'4',NULL,NULL,NULL,NULL,37,8,2,1,NULL),(353,'75 - 99%',NULL,NULL,NULL,NULL,39,8,2,1,NULL),(354,'75 - 99%',NULL,NULL,NULL,NULL,40,8,2,1,NULL),(355,NULL,'Most students enjoyed the classes',NULL,NULL,NULL,41,8,2,1,NULL),(356,NULL,NULL,'Explained the concepts well','The lecturer ensured that the students understood the concepts well','The lecturer used easy to understand examples',43,8,2,1,NULL),(357,NULL,NULL,'Give more practical exercises','Be less moody during classes','The lecturer could summarise the notes further',44,8,2,1,NULL),(358,NULL,NULL,'Video tutorials','New computers and accessories','Wifi should be availed in the classroom',45,8,2,1,NULL),(359,NULL,'The course is very useful especially for the students who would like to venture into networking and system administration',NULL,NULL,NULL,42,8,2,1,NULL),(360,'Yes',NULL,NULL,NULL,NULL,38,8,2,1,NULL),(361,'4',NULL,NULL,NULL,NULL,136,9,4,1,NULL),(362,'4',NULL,NULL,NULL,NULL,137,9,4,1,NULL),(363,'4',NULL,NULL,NULL,NULL,138,9,4,1,NULL),(364,'4',NULL,NULL,NULL,NULL,139,9,4,1,NULL),(365,'4',NULL,NULL,NULL,NULL,140,9,4,1,NULL),(366,'4',NULL,NULL,NULL,NULL,141,9,4,1,NULL),(367,'4',NULL,NULL,NULL,NULL,142,9,4,1,NULL),(368,'4',NULL,NULL,NULL,NULL,143,9,4,1,NULL),(369,'4',NULL,NULL,NULL,NULL,144,9,4,1,NULL),(370,'4',NULL,NULL,NULL,NULL,145,9,4,1,NULL),(371,'4',NULL,NULL,NULL,NULL,146,9,4,1,NULL),(372,'4',NULL,NULL,NULL,NULL,147,9,4,1,NULL),(373,'5',NULL,NULL,NULL,NULL,148,9,4,1,NULL),(374,'5',NULL,NULL,NULL,NULL,149,9,4,1,NULL),(375,'4',NULL,NULL,NULL,NULL,150,9,4,1,NULL),(376,'5',NULL,NULL,NULL,NULL,151,9,4,1,NULL),(377,'4',NULL,NULL,NULL,NULL,152,9,4,1,NULL),(378,'5',NULL,NULL,NULL,NULL,153,9,4,1,NULL),(379,'4',NULL,NULL,NULL,NULL,154,9,4,1,NULL),(380,'5',NULL,NULL,NULL,NULL,155,9,4,1,NULL),(381,'4',NULL,NULL,NULL,NULL,156,9,4,1,NULL),(382,'5',NULL,NULL,NULL,NULL,157,9,4,1,NULL),(383,'4',NULL,NULL,NULL,NULL,158,9,4,1,NULL),(384,'5',NULL,NULL,NULL,NULL,159,9,4,1,NULL),(385,'5',NULL,NULL,NULL,NULL,160,9,4,1,NULL),(386,'5',NULL,NULL,NULL,NULL,161,9,4,1,NULL),(387,'4',NULL,NULL,NULL,NULL,162,9,4,1,NULL),(388,'5',NULL,NULL,NULL,NULL,163,9,4,1,NULL),(389,'4',NULL,NULL,NULL,NULL,164,9,4,1,NULL),(390,'5',NULL,NULL,NULL,NULL,165,9,4,1,NULL),(391,'4',NULL,NULL,NULL,NULL,166,9,4,1,NULL),(392,'5',NULL,NULL,NULL,NULL,167,9,4,1,NULL),(393,'4',NULL,NULL,NULL,NULL,168,9,4,1,NULL),(394,'5',NULL,NULL,NULL,NULL,169,9,4,1,NULL),(395,'4',NULL,NULL,NULL,NULL,170,9,4,1,NULL),(396,'5',NULL,NULL,NULL,NULL,171,9,4,1,NULL),(397,'4',NULL,NULL,NULL,NULL,172,9,4,1,NULL),(398,'75 - 99%',NULL,NULL,NULL,NULL,173,9,4,1,NULL),(399,'75 - 99%',NULL,NULL,NULL,NULL,174,9,4,1,NULL),(400,NULL,'These classes are enjoyable and more importantly essential.\r\nNevertheless, due to unavoidable circumstances, one would miss a class or two.',NULL,NULL,NULL,175,9,4,1,NULL),(401,NULL,NULL,'The lecturer was patient to let the students understand what she taught','Gave a couple of assignments','The lecturer gave enough suggestions for further study',176,9,4,1,NULL),(402,NULL,NULL,'The lecturer should be in a more cheerful mood during her classes','Take the students through coding; not basic lecturing','Every student should be given a chance to perform those practicals',177,9,4,1,NULL),(403,NULL,NULL,'Video tutorials','New computers and accessories','',178,9,4,1,NULL),(404,NULL,'This course is one of the must-do courses. For any computer scientist who wants to venture into software development, object oriented programming is an overkill',NULL,NULL,NULL,179,9,4,1,NULL),(405,'Yes',NULL,NULL,NULL,NULL,180,9,4,1,NULL),(406,'3',NULL,NULL,NULL,NULL,136,10,4,1,NULL),(407,'4',NULL,NULL,NULL,NULL,137,10,4,1,NULL),(408,'5',NULL,NULL,NULL,NULL,138,10,4,1,NULL),(409,'4',NULL,NULL,NULL,NULL,139,10,4,1,NULL),(410,'3',NULL,NULL,NULL,NULL,140,10,4,1,NULL),(411,'2',NULL,NULL,NULL,NULL,141,10,4,1,NULL),(412,'3',NULL,NULL,NULL,NULL,142,10,4,1,NULL),(413,'4',NULL,NULL,NULL,NULL,143,10,4,1,NULL),(414,'5',NULL,NULL,NULL,NULL,144,10,4,1,NULL),(415,'4',NULL,NULL,NULL,NULL,145,10,4,1,NULL),(416,'3',NULL,NULL,NULL,NULL,146,10,4,1,NULL),(417,'2',NULL,NULL,NULL,NULL,147,10,4,1,NULL),(418,'1',NULL,NULL,NULL,NULL,148,10,4,1,NULL),(419,'2',NULL,NULL,NULL,NULL,149,10,4,1,NULL),(420,'3',NULL,NULL,NULL,NULL,150,10,4,1,NULL),(421,'4',NULL,NULL,NULL,NULL,151,10,4,1,NULL),(422,'5',NULL,NULL,NULL,NULL,152,10,4,1,NULL),(423,'4',NULL,NULL,NULL,NULL,153,10,4,1,NULL),(424,'3',NULL,NULL,NULL,NULL,154,10,4,1,NULL),(425,'2',NULL,NULL,NULL,NULL,155,10,4,1,NULL),(426,'1',NULL,NULL,NULL,NULL,156,10,4,1,NULL),(427,'N/A',NULL,NULL,NULL,NULL,157,10,4,1,NULL),(428,'2',NULL,NULL,NULL,NULL,158,10,4,1,NULL),(429,'3',NULL,NULL,NULL,NULL,159,10,4,1,NULL),(430,'4',NULL,NULL,NULL,NULL,160,10,4,1,NULL),(431,'5',NULL,NULL,NULL,NULL,161,10,4,1,NULL),(432,'4',NULL,NULL,NULL,NULL,162,10,4,1,NULL),(433,'3',NULL,NULL,NULL,NULL,163,10,4,1,NULL),(434,'2',NULL,NULL,NULL,NULL,164,10,4,1,NULL),(435,'1',NULL,NULL,NULL,NULL,165,10,4,1,NULL),(436,'N/A',NULL,NULL,NULL,NULL,166,10,4,1,NULL),(437,'2',NULL,NULL,NULL,NULL,167,10,4,1,NULL),(438,'3',NULL,NULL,NULL,NULL,168,10,4,1,NULL),(439,'4',NULL,NULL,NULL,NULL,169,10,4,1,NULL),(440,'5',NULL,NULL,NULL,NULL,170,10,4,1,NULL),(441,'4',NULL,NULL,NULL,NULL,171,10,4,1,NULL),(442,'3',NULL,NULL,NULL,NULL,172,10,4,1,NULL),(443,'100%',NULL,NULL,NULL,NULL,173,10,4,1,NULL),(444,'75 - 99%',NULL,NULL,NULL,NULL,174,10,4,1,NULL),(445,NULL,'This is the type of course where you will need to hear it from the lecturer to have a better understanding of its concepts. Most students in that case would do everything possible to ensure they miss ',NULL,NULL,NULL,175,10,4,1,NULL),(446,NULL,NULL,'The lecturer used illustrative examples','The lecturer ensured that the students understood the concepts well','The lecturer used easy to understand examples',176,10,4,1,NULL),(447,NULL,NULL,'Give more practical exercises','Be less moody during classes','The lecturer could summarise the notes further',177,10,4,1,NULL),(448,NULL,NULL,'Pictorials illustrating the concepts','New computers and accessories','Wifi should be availed in the classroom',178,10,4,1,NULL),(449,NULL,'Operating systems is the base software for any computing device hence a little or deep understanding of how they work is fundamental and vital for any computer scientist. Basically, this course is a m',NULL,NULL,NULL,179,10,4,1,NULL),(450,'Yes',NULL,NULL,NULL,NULL,180,10,4,1,NULL),(451,'5',NULL,NULL,NULL,NULL,136,11,5,1,NULL),(452,'5',NULL,NULL,NULL,NULL,137,11,5,1,NULL),(453,'5',NULL,NULL,NULL,NULL,138,11,5,1,NULL),(454,'5',NULL,NULL,NULL,NULL,139,11,5,1,NULL),(455,'4',NULL,NULL,NULL,NULL,140,11,5,1,NULL),(456,'4',NULL,NULL,NULL,NULL,141,11,5,1,NULL),(457,'4',NULL,NULL,NULL,NULL,142,11,5,1,NULL),(458,'5',NULL,NULL,NULL,NULL,143,11,5,1,NULL),(459,'5',NULL,NULL,NULL,NULL,144,11,5,1,NULL),(460,'5',NULL,NULL,NULL,NULL,145,11,5,1,NULL),(461,'3',NULL,NULL,NULL,NULL,146,11,5,1,NULL),(462,'3',NULL,NULL,NULL,NULL,147,11,5,1,NULL),(463,'5',NULL,NULL,NULL,NULL,148,11,5,1,NULL),(464,'N/A',NULL,NULL,NULL,NULL,149,11,5,1,NULL),(465,'5',NULL,NULL,NULL,NULL,150,11,5,1,NULL),(466,'5',NULL,NULL,NULL,NULL,151,11,5,1,NULL),(467,'5',NULL,NULL,NULL,NULL,152,11,5,1,NULL),(468,'5',NULL,NULL,NULL,NULL,153,11,5,1,NULL),(469,'5',NULL,NULL,NULL,NULL,154,11,5,1,NULL),(470,'5',NULL,NULL,NULL,NULL,155,11,5,1,NULL),(471,'5',NULL,NULL,NULL,NULL,156,11,5,1,NULL),(472,'N/A',NULL,NULL,NULL,NULL,157,11,5,1,NULL),(473,'5',NULL,NULL,NULL,NULL,158,11,5,1,NULL),(474,'3',NULL,NULL,NULL,NULL,159,11,5,1,NULL),(475,'3',NULL,NULL,NULL,NULL,160,11,5,1,NULL),(476,'3',NULL,NULL,NULL,NULL,161,11,5,1,NULL),(477,'5',NULL,NULL,NULL,NULL,162,11,5,1,NULL),(478,'5',NULL,NULL,NULL,NULL,163,11,5,1,NULL),(479,'5',NULL,NULL,NULL,NULL,164,11,5,1,NULL),(480,'5',NULL,NULL,NULL,NULL,165,11,5,1,NULL),(481,'5',NULL,NULL,NULL,NULL,166,11,5,1,NULL),(482,'5',NULL,NULL,NULL,NULL,167,11,5,1,NULL),(483,'5',NULL,NULL,NULL,NULL,168,11,5,1,NULL),(484,'3',NULL,NULL,NULL,NULL,169,11,5,1,NULL),(485,'5',NULL,NULL,NULL,NULL,170,11,5,1,NULL),(486,'5',NULL,NULL,NULL,NULL,171,11,5,1,NULL),(487,'N/A',NULL,NULL,NULL,NULL,172,11,5,1,NULL),(488,'75 - 99%',NULL,NULL,NULL,NULL,173,11,5,1,NULL),(489,'75 - 99%',NULL,NULL,NULL,NULL,174,11,5,1,NULL),(490,NULL,'One of the courses for which class attendance results in a lot of understanding of concepts taught for the students.\r\nEvery student including myself thereby made a point of attending all or most of th',NULL,NULL,NULL,175,11,5,1,NULL),(491,NULL,NULL,'The lecturer used illustrative examples','The lecturer explains concepts patiently','The lecturer always kept the class lively',176,11,5,1,NULL),(492,NULL,NULL,'Assignments be given in text form not pictures of text','Take the students through coding; not basic lecturing','The lecturer could organise the notes better',177,11,5,1,NULL),(493,NULL,NULL,'Study charts','Such classes should be conducted in the laboratory where computers are in plenty','Wifi in the classroom to facilitate research and reference ',178,11,5,1,NULL),(494,NULL,'This course goes without saying that it is vital and fundamental for any computer scientist ',NULL,NULL,NULL,179,11,5,1,NULL),(495,'Yes',NULL,NULL,NULL,NULL,180,11,5,1,NULL),(496,'4',NULL,NULL,NULL,NULL,136,12,5,1,NULL),(497,'5',NULL,NULL,NULL,NULL,137,12,5,1,NULL),(498,'4',NULL,NULL,NULL,NULL,138,12,5,1,NULL),(499,'5',NULL,NULL,NULL,NULL,139,12,5,1,NULL),(500,'5',NULL,NULL,NULL,NULL,140,12,5,1,NULL),(501,'4',NULL,NULL,NULL,NULL,141,12,5,1,NULL),(502,'5',NULL,NULL,NULL,NULL,142,12,5,1,NULL),(503,'5',NULL,NULL,NULL,NULL,143,12,5,1,NULL),(504,'5',NULL,NULL,NULL,NULL,144,12,5,1,NULL),(505,'5',NULL,NULL,NULL,NULL,145,12,5,1,NULL),(506,'3',NULL,NULL,NULL,NULL,146,12,5,1,NULL),(507,'5',NULL,NULL,NULL,NULL,147,12,5,1,NULL),(508,'5',NULL,NULL,NULL,NULL,148,12,5,1,NULL),(509,'5',NULL,NULL,NULL,NULL,149,12,5,1,NULL),(510,'5',NULL,NULL,NULL,NULL,150,12,5,1,NULL),(511,'5',NULL,NULL,NULL,NULL,151,12,5,1,NULL),(512,'5',NULL,NULL,NULL,NULL,152,12,5,1,NULL),(513,'5',NULL,NULL,NULL,NULL,153,12,5,1,NULL),(514,'5',NULL,NULL,NULL,NULL,154,12,5,1,NULL),(515,'3',NULL,NULL,NULL,NULL,155,12,5,1,NULL),(516,'5',NULL,NULL,NULL,NULL,156,12,5,1,NULL),(517,'5',NULL,NULL,NULL,NULL,157,12,5,1,NULL),(518,'5',NULL,NULL,NULL,NULL,158,12,5,1,NULL),(519,'5',NULL,NULL,NULL,NULL,159,12,5,1,NULL),(520,'5',NULL,NULL,NULL,NULL,160,12,5,1,NULL),(521,'N/A',NULL,NULL,NULL,NULL,161,12,5,1,NULL),(522,'5',NULL,NULL,NULL,NULL,162,12,5,1,NULL),(523,'4',NULL,NULL,NULL,NULL,163,12,5,1,NULL),(524,'5',NULL,NULL,NULL,NULL,164,12,5,1,NULL),(525,'5',NULL,NULL,NULL,NULL,165,12,5,1,NULL),(526,'4',NULL,NULL,NULL,NULL,166,12,5,1,NULL),(527,'5',NULL,NULL,NULL,NULL,167,12,5,1,NULL),(528,'4',NULL,NULL,NULL,NULL,168,12,5,1,NULL),(529,'5',NULL,NULL,NULL,NULL,169,12,5,1,NULL),(530,'4',NULL,NULL,NULL,NULL,170,12,5,1,NULL),(531,'5',NULL,NULL,NULL,NULL,171,12,5,1,NULL),(532,'N/A',NULL,NULL,NULL,NULL,172,12,5,1,NULL),(533,'75 - 99%',NULL,NULL,NULL,NULL,173,12,5,1,NULL),(534,'75 - 99%',NULL,NULL,NULL,NULL,174,12,5,1,NULL),(535,NULL,'The classes, besides being enjoyable poses a yearn to know more since new concepts are taught progressively. Most students, therefore, including myself found it necessary to attend most of all the cla',NULL,NULL,NULL,175,12,5,1,NULL),(536,NULL,NULL,'The lecturer is cool hence is sweet to listen to','The lecturer explains concepts patiently','The lecturer used easy to understand examples',176,12,5,1,NULL),(537,NULL,NULL,'The lecturer should be in a more cheerful mood during her classes','More of the practicals','',177,12,5,1,NULL),(538,NULL,NULL,'Pictorials illustrating the concepts','The different operating systems','',178,12,5,1,NULL),(539,NULL,'This course is so important and useful since operating systems are used everywhere.',NULL,NULL,NULL,179,12,5,1,NULL),(540,'Yes',NULL,NULL,NULL,NULL,180,12,5,1,NULL);
/*!40000 ALTER TABLE `evaluated_question_answer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `evaluation_instance`
--

DROP TABLE IF EXISTS `evaluation_instance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `evaluation_instance` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `anonymous_identity` varchar(45) NOT NULL,
  `evaluation_session` int(10) unsigned NOT NULL,
  `active` tinyint(1) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_evaluation_instance_evaluation_session1_idx` (`evaluation_session`),
  CONSTRAINT `fk_evaluation_instance_evaluation_session1` FOREIGN KEY (`evaluation_session`) REFERENCES `evaluation_session` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evaluation_instance`
--

LOCK TABLES `evaluation_instance` WRITE;
/*!40000 ALTER TABLE `evaluation_instance` DISABLE KEYS */;
INSERT INTO `evaluation_instance` VALUES (1,'834600e6a41f82244b2a3bccf1299a0cbd14b27c',1,1,NULL),(2,'12e68cbfe7f0e4644f288aea89c1491ba8799f3b',1,1,NULL),(3,'0e03d30851456f2d2c09d2f551c3bda4ea4c0d15',1,1,NULL),(4,'eca357364af66f9958efc27b0b5ad4f11d0c4823',4,1,NULL),(5,'bf308ea4639a1509f675e7dde3041047fd1fc0ed',4,1,NULL);
/*!40000 ALTER TABLE `evaluation_instance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `evaluation_session`
--

DROP TABLE IF EXISTS `evaluation_session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `evaluation_session` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `academic_year` varchar(45) DEFAULT NULL,
  `semester` varchar(45) DEFAULT NULL,
  `admission_year` date NOT NULL,
  `degree` int(10) unsigned NOT NULL,
  `active` tinyint(1) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_evaluation_session_degree2_idx` (`degree`),
  CONSTRAINT `fk_evaluation_session_degree2` FOREIGN KEY (`degree`) REFERENCES `degree` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evaluation_session`
--

LOCK TABLES `evaluation_session` WRITE;
/*!40000 ALTER TABLE `evaluation_session` DISABLE KEYS */;
INSERT INTO `evaluation_session` VALUES (1,'2015-08-14','2015-08-15','2015/2016','2 (May to Aug) 2015','2013-05-01',1,0,NULL),(2,'2015-10-27','2015-10-30','2015/2016','2 (May to Aug 2015)','2014-01-01',1,1,NULL),(3,'2015-10-27','2015-10-30','2015/2016','2 sem (Sep to Dec 2015)','2015-05-01',1,0,NULL),(4,'2015-10-28','2015-10-30','2015/2016','1 (Sep to Dec) 2015','2015-09-01',1,1,NULL);
/*!40000 ALTER TABLE `evaluation_session` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `faculty`
--

DROP TABLE IF EXISTS `faculty`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `faculty` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(60) NOT NULL,
  `abbreviation` varchar(20) NOT NULL,
  `college` int(10) unsigned NOT NULL,
  `contact` int(10) unsigned NOT NULL,
  `active` tinyint(1) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  UNIQUE KEY `abbreviation_UNIQUE` (`abbreviation`),
  KEY `fk_school_college1_idx` (`college`),
  KEY `fk_school_contact1_idx` (`contact`),
  CONSTRAINT `fk_school_college1` FOREIGN KEY (`college`) REFERENCES `college` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_school_contact1` FOREIGN KEY (`contact`) REFERENCES `contact` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `faculty`
--

LOCK TABLES `faculty` WRITE;
/*!40000 ALTER TABLE `faculty` DISABLE KEYS */;
INSERT INTO `faculty` VALUES (1,'Faculty of Medicine','FA',1,1,1,NULL),(2,'School of Computing and Informatics','SCI',1,2,1,NULL),(3,'School of Physical Sciences','SPS',1,3,1,NULL);
/*!40000 ALTER TABLE `faculty` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `faculty_member`
--

DROP TABLE IF EXISTS `faculty_member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `faculty_member` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `person` int(10) unsigned NOT NULL,
  `faculty` int(10) unsigned DEFAULT NULL,
  `department` int(10) unsigned DEFAULT NULL,
  `admission_year` date DEFAULT NULL,
  `faculty_member_role` smallint(3) unsigned NOT NULL,
  `active` tinyint(1) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_person_has_school_person1_idx` (`person`),
  KEY `fk_school_member_role1_idx` (`faculty_member_role`),
  KEY `fk_faculty_member_faculty1_idx` (`faculty`),
  KEY `fk_faculty_member_department1_idx` (`department`),
  CONSTRAINT `fk_faculty_member_department1` FOREIGN KEY (`department`) REFERENCES `department` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_faculty_member_faculty1` FOREIGN KEY (`faculty`) REFERENCES `faculty` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_person_has_school_person1` FOREIGN KEY (`person`) REFERENCES `person` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_school_member_role1` FOREIGN KEY (`faculty_member_role`) REFERENCES `faculty_member_role` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=168 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `faculty_member`
--

LOCK TABLES `faculty_member` WRITE;
/*!40000 ALTER TABLE `faculty_member` DISABLE KEYS */;
INSERT INTO `faculty_member` VALUES (1,1,2,NULL,'2015-05-01',3,1,NULL),(2,2,2,NULL,'2015-05-01',3,1,NULL),(3,3,2,NULL,'2014-01-01',3,1,NULL),(4,4,2,NULL,'2015-01-01',3,1,NULL),(5,5,2,NULL,'2015-09-01',3,1,NULL),(6,6,2,NULL,'0000-00-00',2,1,NULL),(7,7,2,NULL,'0000-00-00',2,1,NULL),(8,8,2,NULL,'0000-00-00',2,1,NULL),(9,9,2,NULL,'0000-00-00',4,1,NULL),(10,10,2,NULL,'0000-00-00',2,1,NULL),(11,13,2,NULL,'2015-09-01',3,1,NULL),(12,14,2,NULL,'2015-09-01',3,1,NULL),(13,15,2,NULL,'2015-09-01',3,1,NULL),(14,16,2,NULL,'2015-09-01',3,1,NULL),(15,17,2,NULL,'2015-09-01',3,1,NULL),(16,18,2,NULL,'2015-09-01',3,1,NULL),(17,19,2,NULL,'2015-09-01',3,1,NULL),(18,20,2,NULL,'2015-09-01',3,1,NULL),(19,21,2,NULL,'2015-09-01',3,1,NULL),(20,22,2,NULL,'2015-09-01',3,1,NULL),(21,23,2,NULL,'2015-09-01',3,1,NULL),(22,24,2,NULL,'2015-09-01',3,1,NULL),(23,25,2,NULL,'2015-09-01',3,1,NULL),(24,26,2,NULL,'2015-09-01',3,1,NULL),(25,27,2,NULL,'2015-09-01',3,1,NULL),(26,28,2,NULL,'2015-09-01',3,1,NULL),(27,29,2,NULL,'2015-09-01',3,1,NULL),(28,30,2,NULL,'2015-09-01',3,1,NULL),(29,31,2,NULL,'2015-09-01',3,1,NULL),(30,32,2,NULL,'2015-09-01',3,1,NULL),(31,33,2,NULL,'2015-09-01',3,1,NULL),(32,34,2,NULL,'2015-09-01',3,1,NULL),(33,35,2,NULL,'2015-09-01',3,1,NULL),(34,36,2,NULL,'2015-09-01',3,1,NULL),(35,37,2,NULL,'2015-09-01',3,1,NULL),(36,38,2,NULL,'2015-09-01',3,1,NULL),(37,39,2,NULL,'2015-09-01',3,1,NULL),(38,40,2,NULL,'2015-09-01',3,1,NULL),(39,41,2,NULL,'2015-09-01',3,1,NULL),(40,42,2,NULL,'2015-09-01',3,1,NULL),(41,43,2,NULL,'2015-09-01',3,1,NULL),(42,44,2,NULL,'2015-09-01',3,1,NULL),(43,45,2,NULL,'2015-09-01',3,1,NULL),(44,46,2,NULL,'2015-09-01',3,1,NULL),(45,47,2,NULL,'2015-09-01',3,1,NULL),(46,48,2,NULL,'2015-09-01',3,1,NULL),(47,49,2,NULL,'2015-09-01',3,1,NULL),(48,50,2,NULL,'2015-09-01',3,1,NULL),(49,51,2,NULL,'2015-09-01',3,1,NULL),(50,52,2,NULL,'2015-09-01',3,1,NULL),(51,53,2,NULL,'2015-09-01',3,1,NULL),(52,54,2,NULL,'2015-09-01',3,1,NULL),(53,55,2,NULL,'2015-09-01',3,1,NULL),(54,56,2,NULL,'2015-09-01',3,1,NULL),(55,57,2,NULL,'2015-09-01',3,1,NULL),(56,58,2,NULL,'2015-09-01',3,1,NULL),(57,59,2,NULL,'2015-09-01',3,1,NULL),(58,60,2,NULL,'2015-09-01',3,1,NULL),(59,61,2,NULL,'2015-09-01',3,1,NULL),(60,62,2,NULL,'2015-09-01',3,1,NULL),(61,63,2,NULL,'2015-09-01',3,1,NULL),(62,64,2,NULL,'2015-09-01',3,1,NULL),(63,65,2,NULL,'2015-09-01',3,1,NULL),(64,66,2,NULL,'2015-09-01',3,1,NULL),(65,67,2,NULL,'2015-09-01',3,1,NULL),(66,68,2,NULL,'2015-09-01',3,1,NULL),(67,69,2,NULL,'2015-09-01',3,1,NULL),(68,70,2,NULL,'2015-09-01',3,1,NULL),(69,71,2,NULL,'2015-09-01',3,1,NULL),(70,72,2,NULL,'2015-09-01',3,1,NULL),(71,73,2,NULL,'2015-09-01',3,1,NULL),(72,74,2,NULL,'2015-09-01',3,1,NULL),(73,75,2,NULL,'2015-09-01',3,1,NULL),(74,76,2,NULL,'2015-09-01',3,1,NULL),(75,77,2,NULL,'2015-09-01',3,1,NULL),(76,78,2,NULL,'2015-09-01',3,1,NULL),(77,79,2,NULL,'2015-09-01',3,1,NULL),(78,80,2,NULL,'2015-09-01',3,1,NULL),(79,81,2,NULL,'2015-09-01',3,1,NULL),(80,82,2,NULL,'2015-09-01',3,1,NULL),(81,83,2,NULL,'2015-09-01',3,1,NULL),(82,84,2,NULL,'2015-09-01',3,1,NULL),(83,85,2,NULL,'2015-09-01',3,1,NULL),(84,86,2,NULL,'2015-09-01',3,1,NULL),(85,87,2,NULL,'2015-09-01',3,1,NULL),(86,88,2,NULL,'2015-09-01',3,1,NULL),(87,89,2,NULL,'2015-09-01',3,1,NULL),(88,90,2,NULL,'2015-09-01',3,1,NULL),(89,91,2,NULL,'2015-09-01',3,1,NULL),(90,92,2,NULL,'2015-09-01',3,1,NULL),(91,93,2,NULL,'2015-09-01',3,1,NULL),(92,94,2,NULL,'2015-09-01',3,1,NULL),(93,95,2,NULL,'2015-09-01',3,1,NULL),(94,96,2,NULL,'2015-09-01',3,1,NULL),(95,97,2,NULL,'2015-09-01',3,1,NULL),(96,98,2,NULL,'2015-09-01',3,1,NULL),(97,99,2,NULL,'2015-09-01',3,1,NULL),(98,100,2,NULL,'2015-09-01',3,1,NULL),(99,101,2,NULL,'2015-09-01',3,1,NULL),(100,102,2,NULL,'2015-09-01',3,1,NULL),(101,103,2,NULL,'2015-09-01',3,1,NULL),(102,104,2,NULL,'2015-09-01',3,1,NULL),(103,105,2,NULL,'2015-09-01',3,1,NULL),(104,106,2,NULL,'2015-09-01',3,1,NULL),(105,107,2,NULL,'2015-09-01',3,1,NULL),(106,108,2,NULL,'2015-09-01',3,1,NULL),(107,109,2,NULL,'2015-09-01',3,1,NULL),(108,110,2,NULL,'2015-09-01',3,1,NULL),(109,111,2,NULL,'2015-09-01',3,1,NULL),(110,112,2,NULL,'2015-09-01',3,1,NULL),(111,113,2,NULL,'2015-09-01',3,1,NULL),(112,114,2,NULL,'2015-09-01',3,1,NULL),(113,115,2,NULL,'2015-09-01',3,1,NULL),(114,116,2,NULL,'2015-09-01',3,1,NULL),(115,117,2,NULL,'2015-09-01',3,1,NULL),(116,118,2,NULL,'2015-09-01',3,1,NULL),(117,119,2,NULL,'2015-09-01',3,1,NULL),(118,120,2,NULL,'2015-09-01',3,1,NULL),(119,121,2,NULL,'2015-09-01',3,1,NULL),(120,122,2,NULL,'2015-09-01',3,1,NULL),(121,123,2,NULL,'2015-09-01',3,1,NULL),(122,124,2,NULL,'2015-09-01',3,1,NULL),(123,125,2,NULL,'2015-09-01',3,1,NULL),(124,126,2,NULL,'2015-09-01',3,1,NULL),(125,127,2,NULL,'2015-09-01',3,1,NULL),(126,128,2,NULL,'2015-09-01',3,1,NULL),(127,129,2,NULL,'2015-09-01',3,1,NULL),(128,130,2,NULL,'2015-09-01',3,1,NULL),(129,131,2,NULL,'2015-09-01',3,1,NULL),(130,132,2,NULL,'2015-09-01',3,1,NULL),(131,133,2,NULL,'2015-09-01',3,1,NULL),(132,134,2,NULL,'2015-09-01',3,1,NULL),(133,135,2,NULL,'2015-09-01',3,1,NULL),(134,136,2,NULL,'2015-09-01',3,1,NULL),(135,137,2,NULL,'2015-09-01',3,1,NULL),(136,138,2,NULL,'2015-09-01',3,1,NULL),(137,139,2,NULL,'2015-09-01',3,1,NULL),(138,140,2,NULL,'2015-09-01',3,1,NULL),(139,141,2,NULL,'2015-09-01',3,1,NULL),(140,142,2,NULL,'2015-09-01',3,1,NULL),(141,143,2,NULL,'2015-09-01',3,1,NULL),(142,144,2,NULL,'2015-09-01',3,1,NULL),(143,145,2,NULL,'2015-09-01',3,1,NULL),(144,146,2,NULL,'2015-09-01',3,1,NULL),(145,147,2,NULL,'2015-09-01',3,1,NULL),(146,148,2,NULL,'2015-09-01',3,1,NULL),(147,149,2,NULL,'2015-09-01',3,1,NULL),(148,150,2,NULL,'2015-09-01',3,1,NULL),(149,151,2,NULL,'2015-09-01',3,1,NULL),(150,152,2,NULL,'2015-09-01',3,1,NULL),(151,153,2,NULL,'2015-09-01',3,1,NULL),(152,154,2,NULL,'2015-09-01',3,1,NULL),(153,155,2,NULL,'2015-09-01',3,1,NULL),(154,156,2,NULL,'2015-09-01',3,1,NULL),(155,157,2,NULL,'2015-09-01',3,1,NULL),(156,158,2,NULL,'2015-09-01',3,1,NULL),(157,159,2,NULL,'2015-09-01',3,1,NULL),(158,160,2,NULL,'2015-09-01',3,1,NULL),(159,161,2,NULL,'2015-09-01',3,1,NULL),(160,162,2,NULL,'2015-09-01',3,1,NULL),(161,163,2,NULL,'2015-09-01',3,1,NULL),(162,164,2,NULL,'2015-09-01',2,1,NULL),(163,165,2,NULL,'2015-09-01',2,1,NULL),(164,166,2,NULL,'2015-09-01',2,1,NULL),(165,167,2,NULL,'2015-09-01',2,1,NULL),(166,168,2,NULL,'2015-09-01',2,1,NULL),(167,169,2,NULL,'2015-09-01',2,1,NULL);
/*!40000 ALTER TABLE `faculty_member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `faculty_member_role`
--

DROP TABLE IF EXISTS `faculty_member_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `faculty_member_role` (
  `id` smallint(3) unsigned NOT NULL AUTO_INCREMENT,
  `faculty_member_role` varchar(20) NOT NULL,
  `active` tinyint(1) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idrole_UNIQUE` (`id`),
  UNIQUE KEY `role_UNIQUE` (`faculty_member_role`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `faculty_member_role`
--

LOCK TABLES `faculty_member_role` WRITE;
/*!40000 ALTER TABLE `faculty_member_role` DISABLE KEYS */;
INSERT INTO `faculty_member_role` VALUES (1,'Management',NULL,NULL),(2,'Lecturer',NULL,NULL),(3,'Student',NULL,NULL),(4,'Other staff',NULL,NULL);
/*!40000 ALTER TABLE `faculty_member_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `institution`
--

DROP TABLE IF EXISTS `institution`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `institution` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(60) NOT NULL,
  `abbreviation` varchar(20) NOT NULL,
  `active` tinyint(1) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `country` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  KEY `fk_institution_country2_idx` (`country`),
  CONSTRAINT `fk_institution_country2` FOREIGN KEY (`country`) REFERENCES `country` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `institution`
--

LOCK TABLES `institution` WRITE;
/*!40000 ALTER TABLE `institution` DISABLE KEYS */;
INSERT INTO `institution` VALUES (1,'University of Nairobi','UON',1,NULL,110);
/*!40000 ALTER TABLE `institution` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `means_of_answering`
--

DROP TABLE IF EXISTS `means_of_answering`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `means_of_answering` (
  `id` smallint(3) unsigned NOT NULL AUTO_INCREMENT,
  `means_of_answering` varchar(60) NOT NULL,
  `active` tinyint(1) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `means_of_answering_UNIQUE` (`means_of_answering`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `means_of_answering`
--

LOCK TABLES `means_of_answering` WRITE;
/*!40000 ALTER TABLE `means_of_answering` DISABLE KEYS */;
INSERT INTO `means_of_answering` VALUES (1,'By rating',NULL,NULL),(2,'By reasoning',NULL,NULL),(3,'By listing comments',NULL,NULL);
/*!40000 ALTER TABLE `means_of_answering` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `overall_admin`
--

DROP TABLE IF EXISTS `overall_admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `overall_admin` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(150) DEFAULT NULL,
  `active` tinyint(1) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `overall_admin`
--

LOCK TABLES `overall_admin` WRITE;
/*!40000 ALTER TABLE `overall_admin` DISABLE KEYS */;
/*!40000 ALTER TABLE `overall_admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `person`
--

DROP TABLE IF EXISTS `person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `person` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `first_name` varchar(20) NOT NULL,
  `last_name` varchar(20) NOT NULL,
  `contact` int(10) unsigned NOT NULL,
  `reference_number` varchar(20) NOT NULL,
  `national_id_or_passport` varchar(20) NOT NULL,
  `active` tinyint(1) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `reference_number_UNIQUE` (`reference_number`),
  UNIQUE KEY `national_id_UNIQUE` (`national_id_or_passport`),
  KEY `fk_person_contact1_idx` (`contact`),
  CONSTRAINT `fk_person_contact1` FOREIGN KEY (`contact`) REFERENCES `contact` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=170 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `person`
--

LOCK TABLES `person` WRITE;
/*!40000 ALTER TABLE `person` DISABLE KEYS */;
INSERT INTO `person` VALUES (1,'Ben','Siech',4,'P15/1678/2014','29820457',1,NULL),(2,'Lucy','Kimotho ',5,'P15/1659/2014','16591659',1,NULL),(3,'Kelvin ','Mubari',6,'P15/1660/2014','16601660',1,NULL),(4,'Stahimili','Ibrahim',7,'P15/1661/2014','16611661',1,NULL),(5,'Kyalo','Nickson',8,'P15/1662/2014','16621662',1,NULL),(6,'Mr','Oboko',9,'T221','221221',1,NULL),(7,'Miss','Pauline',10,'T223','223223',1,NULL),(8,'Agnes','Wausi',11,'T224','224224',1,NULL),(9,'Reagan','Onditi',13,'T226','226226',1,NULL),(10,'Mrs','Ronge',14,'T225','225225',1,NULL),(13,'Tevin','Kiptoo',19,'T15/1678/2014','16781678',1,NULL),(14,'ABDALLA','MOHAMED',20,'P15/36965/2016','P15/36965/2016',1,NULL),(15,'ABDALLA','NAWAAL KASSIM',21,'P15/1703/2016','P15/1703/2016',1,NULL),(16,'ABDUBA','GUYO MOHAMED',22,'P15/1699/2016','P15/1699/2016',1,NULL),(17,'ADAN','MOHAMED NAJIB',23,'P15/34475/2014','P15/34475/2014',1,NULL),(18,'ADEMBA','AQUINOUS RABONGO',24,'P15/2753/2016','P15/2753/2016',1,NULL),(19,'AGGREY','TEVIN  LITUNDA',25,'P15/36478/2016','P15/36478/2016',1,NULL),(20,'AGIN','ELVIS',26,'P15/1726/2016','P15/1726/2016',1,NULL),(21,'AKIVAYA','KEVIN ESENDI',27,'P15/36780/2016','P15/36780/2016',1,NULL),(22,'AMBANI','PAULSTERN MADEGWA',28,'P15/1721/2016','P15/1721/2016',1,NULL),(23,'AMOKE','WYCLIFFE OCHIENG\'',29,'P15/1728/2016','P15/1728/2016',1,NULL),(24,'ATIENO','ADHIAMBO FAY',30,'P15/4630/2015','P15/4630/2015',1,NULL),(25,'BARAK','ACHILLAH',31,'P15/1709/2016','P15/1709/2016',1,NULL),(26,'BARMIN','OGICHOWA BENEDICT',32,'P15/31737/2015','P15/31737/2015',1,NULL),(27,'BARUA','EMUSUGUT ALLAN',33,'P15/1719/2016','P15/1719/2016',1,NULL),(28,'BETT','KIPLIMO TONNY',34,'P15/1716/2016','P15/1716/2016',1,NULL),(29,'BHUNDIA','NAVIK JAYANT',35,'P15/33494/2015','P15/33494/2015',1,NULL),(30,'BOIT','MICHAEL KIPKOSGEI',36,'P15/37529/2016','P15/37529/2016',1,NULL),(31,'BORUS','NORAH CHELAGAT',37,'P15/2559/2015','P15/2559/2015',1,NULL),(32,'CATHERINE','WAIGWE',38,'P15/1689/2016','P15/1689/2016',1,NULL),(33,'CHUMBA','TREVOR  KIPROP',39,'P15/36376/2016','P15/36376/2016',1,NULL),(34,'DAMJI','KODI EVANS',40,'P15/35947/2015','P15/35947/2015',1,NULL),(35,'GACENGA','HOSEA CIUTI',41,'P15/1706/2016','P15/1706/2016',1,NULL),(36,'GACHOKA','MARVIN',42,'P15/32156/2015','P15/32156/2015',1,NULL),(37,'GAGI','DENIS ANDREW',43,'P15/1713/2016','P15/1713/2016',1,NULL),(38,'GAKENGE','BENSON MUGO',44,'P15/36158/2015','P15/36158/2015',1,NULL),(39,'GICHUKI','EVANS WAHOME',45,'P15/36969/2016','P15/36969/2016',1,NULL),(40,'GITAHI','JUDY ANN WANJIKU',46,'P15/1698/2016','P15/1698/2016',1,NULL),(41,'GITAU','SAMUEL NJOROGE',47,'P15/1696/2016','P15/1696/2016',1,NULL),(42,'IBRAHIM','ALI  ABDI',48,'P15/37272/2016','P15/37272/2016',1,NULL),(43,'IBRAHIM','ANAS  MOHAMED',49,'P15/36268/2016','P15/36268/2016',1,NULL),(44,'ININDA','LAURENCE BUGASU',50,'P15/1708/2016','P15/1708/2016',1,NULL),(45,'IRUNGU','MARTIN MURIUKI',51,'P15/36341/2015','P15/36341/2015',1,NULL),(46,'JACKSON','ERICK NZUKI',52,'P15/37046/2016','P15/37046/2016',1,NULL),(47,'JAMA','WARSAME MOHAMUD',53,'P15/37269/2016','P15/37269/2016',1,NULL),(48,'JEFWA','KELVIN MUKARE',54,'P15/37344/2016','P15/37344/2016',1,NULL),(49,'JUMA','EDGAR ONYANGO',55,'P15/36117/2015','P15/36117/2015',1,NULL),(50,'KAHIHU','KELVIN GITOGO',56,'P15/30653/2015','P15/30653/2015',1,NULL),(51,'KAHIN','KHALIF MOHAMED',57,'P15/37254/2016','P15/37254/2016',1,NULL),(52,'KAHURANI','PETER KIBICHA',58,'P15/36102/2015','P15/36102/2015',1,NULL),(53,'KAJIRU','ABUBAKAR MOHAMED',59,'P15/37738/2016','P15/37738/2016',1,NULL),(54,'KAMADI','EUGENE SAMBULA',60,'P15/31916/2015','P15/31916/2015',1,NULL),(55,'KAMAMI','MOSES WAMAE',61,'P15/37641/2016','P15/37641/2016',1,NULL),(56,'KAMAU','BRIAN MUKUHI',62,'P15/36237/2016','P15/36237/2016',1,NULL),(57,'KARIMI','VINCENT KARANI',63,'P15/36507/2016','P15/36507/2016',1,NULL),(58,'KARIUKI','MARK MUIGAI',64,'P15/35753/2015','P15/35753/2015',1,NULL),(59,'KARIUKI','WAIRIMU SALOME',65,'P15/1690/2016','P15/1690/2016',1,NULL),(60,'KASYOKI','VICTOR',66,'P15/37231/2016','P15/37231/2016',1,NULL),(61,'KERING','JOAN JEPKOGEI',67,'P15/36897/2016','P15/36897/2016',1,NULL),(62,'KIBET','MICHAEL BRIAN',68,'P15/36901/2016','P15/36901/2016',1,NULL),(63,'KING\'ORIAH','MICHAEL MUTUMA',69,'P15/35462/2015','P15/35462/2015',1,NULL),(64,'KIPKEMEI','KEVIN',70,'P15/1711/2016','P15/1711/2016',1,NULL),(65,'KIPKORIR','ADRIAN ABRAHAM K',71,'P15/1715/2016','P15/1715/2016',1,NULL),(66,'KIRUJA','IAN GITONGA',72,'P15/36157/2015','P15/36157/2015',1,NULL),(67,'KIUTE','JACK MWAKAI',73,'P15/36569/2016','P15/36569/2016',1,NULL),(68,'KURIA','MARK KARUKU',74,'P15/37364/2016','P15/37364/2016',1,NULL),(69,'KURIA','MICHAEL WAMATHAI',75,'P15/34677/2014','P15/34677/2014',1,NULL),(70,'KWAMBAI','CHEPKOGEI MERCY',76,'P15/1718/2016','P15/1718/2016',1,NULL),(71,'KYALO','KELVIN MUINDI',77,'P15/37561/2016','P15/37561/2016',1,NULL),(72,'KYANDE','MICHAEL JOHN',78,'P15/34906/2014','P15/34906/2014',1,NULL),(73,'LANGAT','CALVIN KIPTOO',79,'P15/36089/2015','P15/36089/2015',1,NULL),(74,'LANGAT','DANIEL KIPYEGON',80,'P15/1714/2016','P15/1714/2016',1,NULL),(75,'LANGAT','VICTOR KIPRONO',81,'P15/1841/2016','P15/1841/2016',1,NULL),(76,'LIKONO','IAN',82,'P15/1717/2016','P15/1717/2016',1,NULL),(77,'LUCY','NJOKI',83,'P15/1694/2016','P15/1694/2016',1,NULL),(78,'LUPAO','WANJALA CLIVE',84,'P15/1701/2016','P15/1701/2016',1,NULL),(79,'LUTTA','ELVIS EMMANUEL',85,'P15/35805/2013','P15/35805/2013',1,NULL),(80,'MACHARIA','SANTANA NJOKI',86,'P15/1693/2016','P15/1693/2016',1,NULL),(81,'MACHOKA','TOM FRANK',87,'P15/31178/2015','P15/31178/2015',1,NULL),(82,'MAGETO','ALLAN BIKUNDO',88,'P15/35160/2014','P15/35160/2014',1,NULL),(83,'MAINA','KELVIN MWANGI',89,'P15/36503/2016','P15/36503/2016',1,NULL),(84,'MAKORI','BAHATI AGATA',90,'P15/1702/2016','P15/1702/2016',1,NULL),(85,'MALEYA','CLARKSTIN JUMBA',91,'P15/36168/2015','P15/36168/2015',1,NULL),(86,'MALING\'A','ERICK MUIGEI',92,'P15/36132/2015','P15/36132/2015',1,NULL),(87,'MAROSI','GREGORY JOSEPH OKARI',93,'P15/35834/2015','P15/35834/2015',1,NULL),(88,'MATHU','SHARON WANJIRU',94,'P15/36812/2016','P15/36812/2016',1,NULL),(89,'MBAKA','ABNER OISEBE',95,'P15/30459/2015','P15/30459/2015',1,NULL),(90,'MBARI','JOHN WAMBUGU',96,'P15/35101/2015','P15/35101/2015',1,NULL),(91,'MBUGUA','JUSTUS NJURU',97,'P15/33977/2014','P15/33977/2014',1,NULL),(92,'MBUGUA','PHILIP NDUNGU',98,'P15/37874/2016','P15/37874/2016',1,NULL),(93,'MBUI','TEDDY KAMAU',99,'P15/36234/2016','P15/36234/2016',1,NULL),(94,'MOSETI','CARITON MARANGA',100,'P15/36821/2016','P15/36821/2016',1,NULL),(95,'MUCHENDU','BONIFACE MICHUKI',101,'P15/35893/2015','P15/35893/2015',1,NULL),(96,'MUCHIRU','LEWIS KURIA',102,'P15/35097/2015','P15/35097/2015',1,NULL),(97,'MUGENYA','EMMANUEL WILSON',103,'P15/37139/2016','P15/37139/2016',1,NULL),(98,'MUIGAI','LUKE KIVUNAGA',104,'P15/1697/2016','P15/1697/2016',1,NULL),(99,'MULWA','ISAAC KIPTOO',105,'P15/37217/2016','P15/37217/2016',1,NULL),(100,'MUNGA','COLLINS GICHUHI',106,'P15/37957/2016','P15/37957/2016',1,NULL),(101,'MUNGAI','ERIC MBURU',107,'P15/1695/2016','P15/1695/2016',1,NULL),(102,'MUNGAI','TEVIN CHEGE',108,'P15/35759/2015','P15/35759/2015',1,NULL),(103,'MUNGATHIA','INNOCENT KITHINJI',109,'P15/36496/2016','P15/36496/2016',1,NULL),(104,'MURIITHI','EDWIN KABUI',110,'P15/36653/2016','P15/36653/2016',1,NULL),(105,'MURAYA','SAMMY NDIRANGU',111,'P15/30324/2015','P15/30324/2015',1,NULL),(106,'MURIMI','MARTIN',112,'P15/1433/2016','P15/1433/2016',1,NULL),(107,'MURIUKI','TAJIRI GITONGA',113,'P15/37017/2016','P15/37017/2016',1,NULL),(108,'MUSYOKA','ROSIANAH WANZA',114,'P15/38293/2016','P15/38293/2016',1,NULL),(109,'MUTENDE','DEREK PRINCE',115,'P15/1724/2016','P15/1724/2016',1,NULL),(110,'MUTINDA','JAPHETH  KIOKO',116,'P15/34665/2014','P15/34665/2014',1,NULL),(111,'MUTUA',' AUGUSTINE NGANGA',117,'P15/30363/2015','P15/30363/2015',1,NULL),(112,'MUTUA','SOLOMON',118,'P15/37821/2016','P15/37821/2016',1,NULL),(113,'MUTUKU','MAUREEN MUMBUA',119,'P15/1710/2016','P15/1710/2016',1,NULL),(114,'MWANGI','BRIAN MACHARIA',120,'P15/37010/2016','P15/37010/2016',1,NULL),(115,'MWANGI','FREUD KARIUKI',121,'P15/1692/2016','P15/1692/2016',1,NULL),(116,'MWANIKI','EDWIN MBUTHIA',122,'P15/1705/2016','P15/1705/2016',1,NULL),(117,'MWANZIA','DAUDI KASIA',123,'P15/38236/2016','P15/38236/2016',1,NULL),(118,'MWANZIA','SAMUEL NZYUKO',124,'P15/36533/2016','P15/36533/2016',1,NULL),(119,'MWELESA','PAULETTE EMALI',125,'P15/36513/2016','P15/36513/2016',1,NULL),(120,'MWEMA','PHENA  EDITH',126,'P15/37125/2016','P15/37125/2016',1,NULL),(121,'MWENJE','STEPHEN',127,'P15/1722/2016','P15/1722/2016',1,NULL),(122,'MWITHALIE','VINCENT MURITHI',128,'P15/1723/2016','P15/1723/2016',1,NULL),(123,'NDAMBUKI','LABAN KIOKO',129,'P15/37019/2016','P15/37019/2016',1,NULL),(124,'NDICHU','STEVE KEVIN',130,'P15/36238/2016','P15/36238/2016',1,NULL),(125,'NDUNGU','DENNIS GICHU',131,'P15/36648/2016','P15/36648/2016',1,NULL),(126,'NDWIGA','LEWIS MUNYI',132,'P15/36076/2015','P15/36076/2015',1,NULL),(127,'NGUGI','MICHAEL GICHORA',133,'P15/36134/2015','P15/36134/2015',1,NULL),(128,'NGATIA','SIMON MURAGURI',134,'P15/1688/2016','P15/1688/2016',1,NULL),(129,'NJERU','SIMON MUGO',135,'P15/31143/2015','P15/31143/2015',1,NULL),(130,'NJOGU','WINNIE WANJIRU',136,'P15/35037/2014','P15/35037/2014',1,NULL),(131,'NJOKI','PETER KAHENYA',137,'P15/1712/2016','P15/1712/2016',1,NULL),(132,'NYAGA','PETER MWANIKI',138,'P15/1700/2016','P15/1700/2016',1,NULL),(133,'NYAGESOA','ABIUD ORINA',139,'P15/1725/2016','P15/1725/2016',1,NULL),(134,'NZUMA','JONATHAN NDAMBUKI',140,'P15/36756/2016','P15/36756/2016',1,NULL),(135,'OCHIENG','FELIX OMONDI',141,'P15/37073/2016','P15/37073/2016',1,NULL),(136,'OCHOMO','OTIENO WILLIAM',142,'P15/1720/2016','P15/1720/2016',1,NULL),(137,'ODHIAMBO','STEPHEN OCHIENG',143,'P15/36874/2016','P15/36874/2016',1,NULL),(138,'ODONGO','BRIAN RAILA AMOLO',144,'P15/1727/2016','P15/1727/2016',1,NULL),(139,'ODUNDO','STEPHEN OPIYO',145,'P15/34017/2015','P15/34017/2015',1,NULL),(140,'OKELLO','JOB OPIYO',146,'P15/37353/2016','P15/37353/2016',1,NULL),(141,'OKERO','MICHAEL OMONDI',147,'P15/30269/2015','P15/30269/2015',1,NULL),(142,'OLOO','KEVIN OMONDI',148,'P15/35324/2015','P15/35324/2015',1,NULL),(143,'OLUDHE','PASCAL ODUOR',149,'P15/35255/2015','P15/35255/2015',1,NULL),(144,'OLUOCH','BILL ODUOR',150,'P15/38119/2016','P15/38119/2016',1,NULL),(145,'OMAR','MOHAMED ABDI',151,'P15/36337/2016','P15/36337/2016',1,NULL),(146,'OMBASO','MOHAMED NYANAMBA',152,'P15/35291/2015','P15/35291/2015',1,NULL),(147,'ONAMU','JAMES RODNEY',153,'P15/35945/2015','P15/35945/2015',1,NULL),(148,'ONYANGO','ROBERT MARK',154,'P15/1729/2016','P15/1729/2016',1,NULL),(149,'ONYINSI','TOBIAS WEST',155,'P15/37451/2016','P15/37451/2016',1,NULL),(150,'OTIENO','ANITA AJWANG',156,'P15/36551/2016','P15/36551/2016',1,NULL),(151,'OTIENO','ERNIE MBOCK',157,'P15/35280/2015','P15/35280/2015',1,NULL),(152,'OYIEKE','ALLEN ONGADO',158,'P15/36567/2016','P15/36567/2016',1,NULL),(153,'SAKALI','GABRIEL IRUNGU',159,'P15/36349/2016','P15/36349/2016',1,NULL),(154,'THAGANA','K MARK',160,'P15/1691/2016','P15/1691/2016',1,NULL),(155,'WAHOME','DENNIS LINUS',161,'P15/38166/2016','P15/38166/2016',1,NULL),(156,'WAMAE','JOSEPH WANYOIKE',162,'P15/37390/2016','P15/37390/2016',1,NULL),(157,'WAMUIGA','KELVIN KAMAU',163,'P15/35060/2015','P15/35060/2015',1,NULL),(158,'WANJOHI','BRIAN MUTURI',164,'P15/37020/2016','P15/37020/2016',1,NULL),(159,'WANJOHI','RYAN GITONGA',165,'P15/1704/2016','P15/1704/2016',1,NULL),(160,'WAYU','ALI  KOMORA',166,'P15/35954/2015','P15/35954/2015',1,NULL),(161,'YASSIN','AHMED FAIZ',167,'P15/1707/2016','P15/1707/2016',1,NULL),(162,'YUSUF','MOHEEN  RASHID',168,'P15/36820/2016','P15/36820/2016',1,NULL),(163,'ZAKARIA','HUSSEIN ABDI',169,'P15/37847/2016','P15/37847/2016',1,NULL),(164,'DR EVANS','MIRITI',170,'CSC111Miriti','CSC111Miriti',1,NULL),(165,'PROF OMWENGA','OMWENGA',171,'CSC112Omwenga','CSC112Omwenga',1,NULL),(166,'DR CORNELIUS','ABUNGU',172,'CSC113Abungu','CSC113Abungu',1,NULL),(167,'DR CORNELIUS','ABUNGU',173,'CSC114Abungu','CSC114Abungu',1,NULL),(168,'DR MARY','OKEBE',174,'CCS001Mary','CCS001Mary',1,NULL),(169,'DR PETER','MURIU',175,'CSC009Muriu','CSC009Muriu',1,NULL);
/*!40000 ALTER TABLE `person` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `phone_contact`
--

DROP TABLE IF EXISTS `phone_contact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `phone_contact` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `mobile_number` varchar(20) DEFAULT NULL,
  `fixed_number` varchar(20) DEFAULT NULL,
  `contact` int(10) unsigned NOT NULL,
  `active` tinyint(1) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `mobile_number_UNIQUE` (`mobile_number`),
  KEY `fk_phone_contact_contact1_idx` (`contact`),
  CONSTRAINT `fk_phone_contact_contact1` FOREIGN KEY (`contact`) REFERENCES `contact` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=173 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phone_contact`
--

LOCK TABLES `phone_contact` WRITE;
/*!40000 ALTER TABLE `phone_contact` DISABLE KEYS */;
INSERT INTO `phone_contact` VALUES (1,'+254-75520289506','',1,1,NULL),(2,'+254-73740435424','',2,1,NULL),(3,'+254-71620888295','',3,1,NULL),(4,'0701404084','404084',4,1,NULL),(5,'27500007004','7004',5,1,NULL),(6,'20400001009','01009',6,1,NULL),(7,'0716611661','01116',7,1,NULL),(8,'27552001014','01014',8,1,NULL),(9,'0701404081','010011',9,1,NULL),(10,'0701404083','404083',10,1,NULL),(11,'0701404085','40405',11,1,NULL),(12,'0701404088','404089',13,1,NULL),(13,'0701404023','23121',14,1,NULL),(16,'07014040838','4356904',19,1,NULL),(17,'202129811',NULL,20,1,NULL),(18,'217052915',NULL,21,1,NULL),(19,'229082987',NULL,22,1,NULL),(20,'23263914',NULL,23,1,NULL),(21,'2459923',NULL,24,1,NULL),(22,'2534924',NULL,25,1,NULL),(23,'2664921',NULL,26,1,NULL),(24,'27658417',NULL,27,1,NULL),(25,'2890517',NULL,28,1,NULL),(26,'29359811',NULL,29,1,NULL),(27,'3065926',NULL,30,1,NULL),(28,'319521',NULL,31,1,NULL),(29,'32666980',NULL,32,1,NULL),(30,'3372791',NULL,33,1,NULL),(31,'343289832',NULL,34,1,NULL),(32,'35555830',NULL,35,1,NULL),(33,'3688933',NULL,36,1,NULL),(34,'37099830',NULL,37,1,NULL),(35,'38929984',NULL,38,1,NULL),(36,'3953519',NULL,39,1,NULL),(37,'40999813',NULL,40,1,NULL),(38,'411005',NULL,41,1,NULL),(39,'428230093',NULL,42,1,NULL),(40,'435002',NULL,43,1,NULL),(41,'448029',NULL,44,1,NULL),(42,'45002021',NULL,45,1,NULL),(43,'465142099',NULL,46,1,NULL),(44,'47339202',NULL,47,1,NULL),(45,'482316',NULL,48,1,NULL),(46,'492563019',NULL,49,1,NULL),(47,'508295',NULL,50,1,NULL),(48,'5126140935',NULL,51,1,NULL),(49,'5231440933',NULL,52,1,NULL),(50,'5397',NULL,53,1,NULL),(51,'54445048',NULL,54,1,NULL),(52,'558510',NULL,55,1,NULL),(53,'562352',NULL,56,1,NULL),(54,'577600',NULL,57,1,NULL),(55,'58079623',NULL,58,1,NULL),(56,'593727',NULL,59,1,NULL),(57,'60670918',NULL,60,1,NULL),(58,'6188870933',NULL,61,1,NULL),(59,'62849',NULL,62,1,NULL),(60,'63190929',NULL,63,1,NULL),(61,'64441',NULL,64,1,NULL),(62,'65179061',NULL,65,1,NULL),(63,'666934',NULL,66,1,NULL),(64,'6791015',NULL,67,1,NULL),(65,'68439',NULL,68,1,NULL),(66,'6927',NULL,69,1,NULL),(67,'7071116',NULL,70,1,NULL),(68,'7114321931',NULL,71,1,NULL),(69,'724362194',NULL,72,1,NULL),(70,'736464',NULL,73,1,NULL),(71,'7491143',NULL,74,1,NULL),(72,'75321',NULL,75,1,NULL),(73,'7660354',NULL,76,1,NULL),(74,'77518565',NULL,77,1,NULL),(75,'78062',NULL,78,1,NULL),(76,'793628',NULL,79,1,NULL),(77,'8090617',NULL,80,1,NULL),(78,'817786128',NULL,81,1,NULL),(79,'821117168',NULL,82,1,NULL),(80,'83571933',NULL,83,1,NULL),(81,'8484939',NULL,84,1,NULL),(82,'8594833',NULL,85,1,NULL),(83,'860302924',NULL,86,1,NULL),(84,'876023',NULL,87,1,NULL),(85,'88150159',NULL,88,1,NULL),(86,'892054',NULL,89,1,NULL),(87,'907576',NULL,90,1,NULL),(88,'91713245',NULL,91,1,NULL),(89,'92335311',NULL,92,1,NULL),(90,'93832974',NULL,93,1,NULL),(91,'9411434',NULL,94,1,NULL),(92,'956342938',NULL,95,1,NULL),(93,'96306413',NULL,96,1,NULL),(94,'979755299',NULL,97,1,NULL),(95,'98062991',NULL,98,1,NULL),(96,'99262914',NULL,99,1,NULL),(97,'100727255',NULL,100,1,NULL),(98,'10158235',NULL,101,1,NULL),(99,'10262010',NULL,102,1,NULL),(100,'103295',NULL,103,1,NULL),(101,'1049481',NULL,104,1,NULL),(102,'1057917',NULL,105,1,NULL),(103,'106979924',NULL,106,1,NULL),(104,'1072086',NULL,107,1,NULL),(105,'108403930',NULL,108,1,NULL),(106,'1093703934',NULL,109,1,NULL),(107,'11001104',NULL,110,1,NULL),(108,'11110349',NULL,111,1,NULL),(109,'11283',NULL,112,1,NULL),(110,'11372323911',NULL,113,1,NULL),(111,'11482339103',NULL,114,1,NULL),(112,'1155533992',NULL,115,1,NULL),(113,'11689733974',NULL,116,1,NULL),(114,'1170414384',NULL,117,1,NULL),(115,'11819343990',NULL,118,1,NULL),(116,'11962637',NULL,119,1,NULL),(117,'1201831',NULL,120,1,NULL),(118,'121150',NULL,121,1,NULL),(119,'12234',NULL,122,1,NULL),(120,'123665121',NULL,123,1,NULL),(121,'12495338',NULL,124,1,NULL),(122,'1251816109',NULL,125,1,NULL),(123,'126946103',NULL,126,1,NULL),(124,'12751126',NULL,127,1,NULL),(125,'12838283971',NULL,128,1,NULL),(126,'12929345',NULL,129,1,NULL),(127,'13049893118',NULL,130,1,NULL),(128,'13157094',NULL,131,1,NULL),(129,'13200149115',NULL,132,1,NULL),(130,'1333621465',NULL,133,1,NULL),(131,'1345104',NULL,134,1,NULL),(132,'13532814982',NULL,135,1,NULL),(133,'136431124',NULL,136,1,NULL),(134,'1379142440',NULL,137,1,NULL),(135,'1388172488',NULL,138,1,NULL),(136,'1399262',NULL,139,1,NULL),(137,'1409723491',NULL,140,1,NULL),(138,'141044919',NULL,141,1,NULL),(139,'142244116',NULL,142,1,NULL),(140,'14313721',NULL,143,1,NULL),(141,'1446454101',NULL,144,1,NULL),(142,'145250',NULL,145,1,NULL),(143,'146346694',NULL,146,1,NULL),(144,'14707136',NULL,147,1,NULL),(145,'148874135',NULL,148,1,NULL),(146,'149638430',NULL,149,1,NULL),(147,'15018984978',NULL,150,1,NULL),(148,'15144936',NULL,151,1,NULL),(149,'1520089114',NULL,152,1,NULL),(150,'15396305114',NULL,153,1,NULL),(151,'1541025996',NULL,154,1,NULL),(152,'1550856',NULL,155,1,NULL),(153,'1566539',NULL,156,1,NULL),(154,'1578930',NULL,157,1,NULL),(155,'158129',NULL,158,1,NULL),(156,'159475146',NULL,159,1,NULL),(157,'1604087554',NULL,160,1,NULL),(158,'161501859109',NULL,161,1,NULL),(159,'162938106',NULL,162,1,NULL),(160,'163768548',NULL,163,1,NULL),(161,'164095143',NULL,164,1,NULL),(162,'1653139',NULL,165,1,NULL),(163,'1668995989',NULL,166,1,NULL),(164,'1679806922',NULL,167,1,NULL),(165,'16851656',NULL,168,1,NULL),(166,'16978132',NULL,169,1,NULL),(167,'17065474',NULL,170,1,NULL),(168,'1718520131',NULL,171,1,NULL),(169,'17226286',NULL,172,1,NULL),(170,'173596',NULL,173,1,NULL),(171,'174708144',NULL,174,1,NULL),(172,'175760123',NULL,175,1,NULL);
/*!40000 ALTER TABLE `phone_contact` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `postal_contact`
--

DROP TABLE IF EXISTS `postal_contact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `postal_contact` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `box_number` varchar(20) DEFAULT NULL,
  `postal_code` varchar(20) DEFAULT NULL,
  `town` varchar(30) DEFAULT NULL,
  `country` int(10) unsigned NOT NULL,
  `contact` int(10) unsigned NOT NULL,
  `active` tinyint(1) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idpostal_contact_UNIQUE` (`id`),
  KEY `fk_postal_contact_contact1_idx` (`contact`),
  KEY `fk_postal_contact_country1_idx` (`country`),
  CONSTRAINT `fk_postal_contact_contact1` FOREIGN KEY (`contact`) REFERENCES `contact` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_postal_contact_country1` FOREIGN KEY (`country`) REFERENCES `country` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=173 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `postal_contact`
--

LOCK TABLES `postal_contact` WRITE;
/*!40000 ALTER TABLE `postal_contact` DISABLE KEYS */;
INSERT INTO `postal_contact` VALUES (1,'','','',110,1,1,NULL),(2,'','','',110,2,1,NULL),(3,'','','',110,3,1,NULL),(4,'2030','20400','Bomet',110,4,1,NULL),(5,'2000','00100','Nairobi',110,5,1,NULL),(6,'234','00100','Nairobi',110,6,1,NULL),(7,'3000','00100','Nairobi',110,7,1,NULL),(8,'5674','00100','Nairobi',110,8,1,NULL),(9,'2341','00100','Nairobi',110,9,1,NULL),(10,'334','00100','Nairobi',110,10,1,NULL),(11,'9832','00100','Nairobi',110,11,1,NULL),(12,'8984','00100','Nairobi',110,13,1,NULL),(13,'20003','00100','Nairobi',110,14,1,NULL),(16,'203','20400','Bomet',110,19,1,NULL),(17,NULL,NULL,NULL,110,20,1,NULL),(18,NULL,NULL,NULL,110,21,1,NULL),(19,NULL,NULL,NULL,110,22,1,NULL),(20,NULL,NULL,NULL,110,23,1,NULL),(21,NULL,NULL,NULL,110,24,1,NULL),(22,NULL,NULL,NULL,110,25,1,NULL),(23,NULL,NULL,NULL,110,26,1,NULL),(24,NULL,NULL,NULL,110,27,1,NULL),(25,NULL,NULL,NULL,110,28,1,NULL),(26,NULL,NULL,NULL,110,29,1,NULL),(27,NULL,NULL,NULL,110,30,1,NULL),(28,NULL,NULL,NULL,110,31,1,NULL),(29,NULL,NULL,NULL,110,32,1,NULL),(30,NULL,NULL,NULL,110,33,1,NULL),(31,NULL,NULL,NULL,110,34,1,NULL),(32,NULL,NULL,NULL,110,35,1,NULL),(33,NULL,NULL,NULL,110,36,1,NULL),(34,NULL,NULL,NULL,110,37,1,NULL),(35,NULL,NULL,NULL,110,38,1,NULL),(36,NULL,NULL,NULL,110,39,1,NULL),(37,NULL,NULL,NULL,110,40,1,NULL),(38,NULL,NULL,NULL,110,41,1,NULL),(39,NULL,NULL,NULL,110,42,1,NULL),(40,NULL,NULL,NULL,110,43,1,NULL),(41,NULL,NULL,NULL,110,44,1,NULL),(42,NULL,NULL,NULL,110,45,1,NULL),(43,NULL,NULL,NULL,110,46,1,NULL),(44,NULL,NULL,NULL,110,47,1,NULL),(45,NULL,NULL,NULL,110,48,1,NULL),(46,NULL,NULL,NULL,110,49,1,NULL),(47,NULL,NULL,NULL,110,50,1,NULL),(48,NULL,NULL,NULL,110,51,1,NULL),(49,NULL,NULL,NULL,110,52,1,NULL),(50,NULL,NULL,NULL,110,53,1,NULL),(51,NULL,NULL,NULL,110,54,1,NULL),(52,NULL,NULL,NULL,110,55,1,NULL),(53,NULL,NULL,NULL,110,56,1,NULL),(54,NULL,NULL,NULL,110,57,1,NULL),(55,NULL,NULL,NULL,110,58,1,NULL),(56,NULL,NULL,NULL,110,59,1,NULL),(57,NULL,NULL,NULL,110,60,1,NULL),(58,NULL,NULL,NULL,110,61,1,NULL),(59,NULL,NULL,NULL,110,62,1,NULL),(60,NULL,NULL,NULL,110,63,1,NULL),(61,NULL,NULL,NULL,110,64,1,NULL),(62,NULL,NULL,NULL,110,65,1,NULL),(63,NULL,NULL,NULL,110,66,1,NULL),(64,NULL,NULL,NULL,110,67,1,NULL),(65,NULL,NULL,NULL,110,68,1,NULL),(66,NULL,NULL,NULL,110,69,1,NULL),(67,NULL,NULL,NULL,110,70,1,NULL),(68,NULL,NULL,NULL,110,71,1,NULL),(69,NULL,NULL,NULL,110,72,1,NULL),(70,NULL,NULL,NULL,110,73,1,NULL),(71,NULL,NULL,NULL,110,74,1,NULL),(72,NULL,NULL,NULL,110,75,1,NULL),(73,NULL,NULL,NULL,110,76,1,NULL),(74,NULL,NULL,NULL,110,77,1,NULL),(75,NULL,NULL,NULL,110,78,1,NULL),(76,NULL,NULL,NULL,110,79,1,NULL),(77,NULL,NULL,NULL,110,80,1,NULL),(78,NULL,NULL,NULL,110,81,1,NULL),(79,NULL,NULL,NULL,110,82,1,NULL),(80,NULL,NULL,NULL,110,83,1,NULL),(81,NULL,NULL,NULL,110,84,1,NULL),(82,NULL,NULL,NULL,110,85,1,NULL),(83,NULL,NULL,NULL,110,86,1,NULL),(84,NULL,NULL,NULL,110,87,1,NULL),(85,NULL,NULL,NULL,110,88,1,NULL),(86,NULL,NULL,NULL,110,89,1,NULL),(87,NULL,NULL,NULL,110,90,1,NULL),(88,NULL,NULL,NULL,110,91,1,NULL),(89,NULL,NULL,NULL,110,92,1,NULL),(90,NULL,NULL,NULL,110,93,1,NULL),(91,NULL,NULL,NULL,110,94,1,NULL),(92,NULL,NULL,NULL,110,95,1,NULL),(93,NULL,NULL,NULL,110,96,1,NULL),(94,NULL,NULL,NULL,110,97,1,NULL),(95,NULL,NULL,NULL,110,98,1,NULL),(96,NULL,NULL,NULL,110,99,1,NULL),(97,NULL,NULL,NULL,110,100,1,NULL),(98,NULL,NULL,NULL,110,101,1,NULL),(99,NULL,NULL,NULL,110,102,1,NULL),(100,NULL,NULL,NULL,110,103,1,NULL),(101,NULL,NULL,NULL,110,104,1,NULL),(102,NULL,NULL,NULL,110,105,1,NULL),(103,NULL,NULL,NULL,110,106,1,NULL),(104,NULL,NULL,NULL,110,107,1,NULL),(105,NULL,NULL,NULL,110,108,1,NULL),(106,NULL,NULL,NULL,110,109,1,NULL),(107,NULL,NULL,NULL,110,110,1,NULL),(108,NULL,NULL,NULL,110,111,1,NULL),(109,NULL,NULL,NULL,110,112,1,NULL),(110,NULL,NULL,NULL,110,113,1,NULL),(111,NULL,NULL,NULL,110,114,1,NULL),(112,NULL,NULL,NULL,110,115,1,NULL),(113,NULL,NULL,NULL,110,116,1,NULL),(114,NULL,NULL,NULL,110,117,1,NULL),(115,NULL,NULL,NULL,110,118,1,NULL),(116,NULL,NULL,NULL,110,119,1,NULL),(117,NULL,NULL,NULL,110,120,1,NULL),(118,NULL,NULL,NULL,110,121,1,NULL),(119,NULL,NULL,NULL,110,122,1,NULL),(120,NULL,NULL,NULL,110,123,1,NULL),(121,NULL,NULL,NULL,110,124,1,NULL),(122,NULL,NULL,NULL,110,125,1,NULL),(123,NULL,NULL,NULL,110,126,1,NULL),(124,NULL,NULL,NULL,110,127,1,NULL),(125,NULL,NULL,NULL,110,128,1,NULL),(126,NULL,NULL,NULL,110,129,1,NULL),(127,NULL,NULL,NULL,110,130,1,NULL),(128,NULL,NULL,NULL,110,131,1,NULL),(129,NULL,NULL,NULL,110,132,1,NULL),(130,NULL,NULL,NULL,110,133,1,NULL),(131,NULL,NULL,NULL,110,134,1,NULL),(132,NULL,NULL,NULL,110,135,1,NULL),(133,NULL,NULL,NULL,110,136,1,NULL),(134,NULL,NULL,NULL,110,137,1,NULL),(135,NULL,NULL,NULL,110,138,1,NULL),(136,NULL,NULL,NULL,110,139,1,NULL),(137,NULL,NULL,NULL,110,140,1,NULL),(138,NULL,NULL,NULL,110,141,1,NULL),(139,NULL,NULL,NULL,110,142,1,NULL),(140,NULL,NULL,NULL,110,143,1,NULL),(141,NULL,NULL,NULL,110,144,1,NULL),(142,NULL,NULL,NULL,110,145,1,NULL),(143,NULL,NULL,NULL,110,146,1,NULL),(144,NULL,NULL,NULL,110,147,1,NULL),(145,NULL,NULL,NULL,110,148,1,NULL),(146,NULL,NULL,NULL,110,149,1,NULL),(147,NULL,NULL,NULL,110,150,1,NULL),(148,NULL,NULL,NULL,110,151,1,NULL),(149,NULL,NULL,NULL,110,152,1,NULL),(150,NULL,NULL,NULL,110,153,1,NULL),(151,NULL,NULL,NULL,110,154,1,NULL),(152,NULL,NULL,NULL,110,155,1,NULL),(153,NULL,NULL,NULL,110,156,1,NULL),(154,NULL,NULL,NULL,110,157,1,NULL),(155,NULL,NULL,NULL,110,158,1,NULL),(156,NULL,NULL,NULL,110,159,1,NULL),(157,NULL,NULL,NULL,110,160,1,NULL),(158,NULL,NULL,NULL,110,161,1,NULL),(159,NULL,NULL,NULL,110,162,1,NULL),(160,NULL,NULL,NULL,110,163,1,NULL),(161,NULL,NULL,NULL,110,164,1,NULL),(162,NULL,NULL,NULL,110,165,1,NULL),(163,NULL,NULL,NULL,110,166,1,NULL),(164,NULL,NULL,NULL,110,167,1,NULL),(165,NULL,NULL,NULL,110,168,1,NULL),(166,NULL,NULL,NULL,110,169,1,NULL),(167,NULL,NULL,NULL,110,170,1,NULL),(168,NULL,NULL,NULL,110,171,1,NULL),(169,NULL,NULL,NULL,110,172,1,NULL),(170,NULL,NULL,NULL,110,173,1,NULL),(171,NULL,NULL,NULL,110,174,1,NULL),(172,NULL,NULL,NULL,110,175,1,NULL);
/*!40000 ALTER TABLE `postal_contact` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `question` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `question` varchar(200) NOT NULL,
  `question_category` smallint(3) unsigned NOT NULL,
  `means_of_answering` smallint(3) unsigned NOT NULL,
  `rating_type` smallint(3) unsigned DEFAULT NULL,
  `faculty` int(10) unsigned DEFAULT NULL,
  `department` int(10) unsigned DEFAULT NULL,
  `active` tinyint(1) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_question_question_category1_idx` (`question_category`),
  KEY `fk_question_means_of_answering1_idx` (`means_of_answering`),
  KEY `fk_question_faculty1_idx` (`faculty`),
  KEY `fk_question_department1_idx` (`department`),
  KEY `fk_question_rating_type1_idx` (`rating_type`),
  CONSTRAINT `fk_question_department1` FOREIGN KEY (`department`) REFERENCES `department` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_question_faculty1` FOREIGN KEY (`faculty`) REFERENCES `faculty` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_question_means_of_answering1` FOREIGN KEY (`means_of_answering`) REFERENCES `means_of_answering` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_question_question_category1` FOREIGN KEY (`question_category`) REFERENCES `question_category` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_question_rating_type1` FOREIGN KEY (`rating_type`) REFERENCES `rating_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question`
--

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
INSERT INTO `question` VALUES (1,'Clarity of course objectives (Classroom/Clinical)',1,1,1,2,NULL,1,NULL),(2,'Achievement of course objectives',1,1,1,2,NULL,1,NULL),(3,'Relevance of course to programme objectives',1,1,1,2,NULL,1,NULL),(4,'Interpretation of concepts and theories',2,1,1,2,NULL,1,NULL),(5,'Coverage of course syllabus',2,1,1,2,NULL,1,NULL),(6,'Clarity in presentation',2,1,1,2,NULL,1,NULL),(7,'Effectiveness of presentation methods',2,1,1,2,NULL,1,NULL),(8,'Sufficiency of handouts',3,1,1,2,NULL,1,NULL),(9,'Value of recommended resource materials',3,1,1,2,NULL,1,NULL),(10,'Use of audio-visual and other teaching aids',3,1,1,2,NULL,1,NULL),(11,'Guidance on the use of web based material/journals',3,1,1,2,NULL,1,NULL),(12,'Adequacy of physical facilities',3,1,1,2,NULL,1,NULL),(13,'Sufficiency of computer(ICT) facility',3,1,1,2,NULL,1,NULL),(14,'Relevance of laboratory experiment(if any)',3,1,1,2,NULL,1,NULL),(15,'Relevance and usefulness of assignments/practicals/CATs',4,1,1,2,NULL,1,NULL),(16,'Appropriate coursework assessement',4,1,1,2,NULL,1,NULL),(17,'Satisfaction with methods of evaluation for classroom theory',4,1,1,2,NULL,1,NULL),(18,'Satisfaction with methods of assessment for practicals',4,1,1,2,NULL,1,NULL),(19,'Attends class regularly',5,1,1,2,NULL,1,NULL),(20,'Keeps to the published timetable',5,1,1,2,NULL,1,NULL),(21,'Is available for consultation when necessary(outside class time)',5,1,1,2,NULL,1,NULL),(22,'Guidance in practical lessons(e.g. Nursing)',5,1,1,2,NULL,1,NULL),(23,'Explains the scope, recommended readings, delivery and evaluation methodology of the course',6,1,1,2,NULL,1,NULL),(24,'Uses organized, up-to-date notes and course materials',6,1,1,2,NULL,1,NULL),(25,'Manages time well(punctual, uses class time efficiently)',6,1,1,2,NULL,1,NULL),(26,'Demonstration of procedures in the practical sessions',6,1,1,2,NULL,1,NULL),(27,'Presents course concepts and theories in a clear and interesting way',7,1,1,2,NULL,1,NULL),(28,'Facilitates meaningful and active class participation by students',7,1,1,2,NULL,1,NULL),(29,'Answers questions clearly and knowledgeably',7,1,1,2,NULL,1,NULL),(30,'Uses relevant examples and illustrations in the class/practical',7,1,1,2,NULL,1,NULL),(31,'Is open to diverse viewpoints and opinions',7,1,1,2,NULL,1,NULL),(32,'Gives relevant and challenging assignments and tests',8,1,1,2,NULL,1,NULL),(33,'Marks assignments and tests promptly',8,1,1,2,NULL,1,NULL),(34,'Gives helpful feedback on assignments and tests',8,1,1,2,NULL,1,NULL),(35,'Use of case studies for analysis',8,1,1,2,NULL,1,NULL),(36,'Overall rating of lecturer in the classroom/during lecture',9,1,1,2,NULL,1,NULL),(37,'Overall rating of the lecturer during practical/clinical sessions',9,1,1,2,NULL,1,NULL),(38,'What proportions of classes do you attend?',10,1,4,2,NULL,1,NULL),(39,'In a normal class/practical sesison, what portion of class members are present?',10,1,4,2,NULL,1,NULL),(40,'What are the reasons for the level of attendance you have reported above?',10,2,NULL,2,NULL,1,NULL),(41,'Your lecturer would like to know if there are certain aspects you believe he/she has done especially well in his/her teaching of this course?',11,3,NULL,2,NULL,1,NULL),(42,'Your lecturer would also like to know what specific things you believe must be done to improvehis/her teaching in the course?',11,3,NULL,2,NULL,1,NULL),(43,'What other materials or resources, if any, should be added to enhance understanding of the course contents?',11,3,NULL,2,NULL,1,NULL),(44,'What is your overall view of the course?',11,2,NULL,2,NULL,1,NULL),(45,'Would you recommend this course to any other group of students?',11,1,3,2,NULL,1,NULL);
/*!40000 ALTER TABLE `question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question_category`
--

DROP TABLE IF EXISTS `question_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `question_category` (
  `id` smallint(3) unsigned NOT NULL AUTO_INCREMENT,
  `category` varchar(60) DEFAULT NULL,
  `active` tinyint(1) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question_category`
--

LOCK TABLES `question_category` WRITE;
/*!40000 ALTER TABLE `question_category` DISABLE KEYS */;
INSERT INTO `question_category` VALUES (1,'Objectives',1,NULL),(2,'Content and Methodology',1,NULL),(3,'Materials and Physical Facilities',1,NULL),(4,'Evaluation',1,NULL),(5,'Availability of lecturer',1,NULL),(6,'Preparation',1,NULL),(7,'Course delivery',1,NULL),(8,'Course evaluation',1,NULL),(9,'Overall rating',1,NULL),(10,'Class attendance',1,NULL),(11,'General comments',1,NULL);
/*!40000 ALTER TABLE `question_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rating`
--

DROP TABLE IF EXISTS `rating`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rating` (
  `id` smallint(3) unsigned NOT NULL AUTO_INCREMENT,
  `rating` varchar(60) DEFAULT NULL,
  `rating_type` smallint(3) unsigned NOT NULL,
  `active` tinyint(1) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_rating_rating_type1_idx` (`rating_type`),
  CONSTRAINT `fk_rating_rating_type1` FOREIGN KEY (`rating_type`) REFERENCES `rating_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rating`
--

LOCK TABLES `rating` WRITE;
/*!40000 ALTER TABLE `rating` DISABLE KEYS */;
INSERT INTO `rating` VALUES (1,'N/A',1,1,NULL),(2,'1',1,1,NULL),(3,'2',1,1,NULL),(4,'3',1,1,NULL),(5,'4',1,1,NULL),(6,'5',1,1,NULL),(7,'100%',4,1,NULL),(8,'75 - 99%',4,1,NULL),(9,'50 - 74%',4,1,NULL),(10,'Below 49%',4,1,NULL),(11,'Yes',3,1,NULL),(12,'No',3,1,NULL),(13,'true',2,1,NULL),(14,'false',2,1,NULL);
/*!40000 ALTER TABLE `rating` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rating_type`
--

DROP TABLE IF EXISTS `rating_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rating_type` (
  `id` smallint(3) unsigned NOT NULL AUTO_INCREMENT,
  `rating_type` varchar(60) NOT NULL,
  `active` tinyint(1) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `rating_type_UNIQUE` (`rating_type`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rating_type`
--

LOCK TABLES `rating_type` WRITE;
/*!40000 ALTER TABLE `rating_type` DISABLE KEYS */;
INSERT INTO `rating_type` VALUES (1,'star',NULL,NULL),(2,'boolean',NULL,NULL),(3,'yes or no',NULL,NULL),(4,'percentage',NULL,NULL);
/*!40000 ALTER TABLE `rating_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student_feedback`
--

DROP TABLE IF EXISTS `student_feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student_feedback` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `feedback` varchar(400) NOT NULL,
  `faculty_member` int(10) unsigned NOT NULL,
  `date_completed` date DEFAULT NULL,
  `evaluation_session` int(10) unsigned NOT NULL,
  `active` tinyint(1) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_student_feedback_faculty_member1_idx` (`faculty_member`),
  KEY `fk_student_feedback_evaluation_session1_idx` (`evaluation_session`),
  CONSTRAINT `fk_student_feedback_evaluation_session1` FOREIGN KEY (`evaluation_session`) REFERENCES `evaluation_session` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_student_feedback_faculty_member1` FOREIGN KEY (`faculty_member`) REFERENCES `faculty_member` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_feedback`
--

LOCK TABLES `student_feedback` WRITE;
/*!40000 ALTER TABLE `student_feedback` DISABLE KEYS */;
INSERT INTO `student_feedback` VALUES (1,'Ben, you have completed the course/lecturer evaluation for this evaluation session. We are happy to inform you that your opinions, comments and reasonings have been received and we are grateful. You completed your evaluation on Oct 28, 2015. Thank you very much!',1,'2015-10-28',3,1,NULL),(2,'Lucy, you have completed the course/lecturer evaluation for this evaluation session. We are happy to inform you that your opinions, comments and reasonings have been received and we are grateful. You completed your evaluation on Oct 28, 2015. Thank you very much!',2,'2015-10-28',3,1,NULL),(3,'Kyalo, you have completed the course/lecturer evaluation for this evaluation session. We are happy to inform you that your opinions, comments and reasonings have been received and we are grateful. You completed your evaluation on Oct 29, 2015. Thank you very much!',5,'2015-10-29',4,1,NULL),(4,'Tevin, you have completed the course/lecturer evaluation for this evaluation session. We are happy to inform you that your opinions, comments and reasonings have been received and we are grateful. You completed your evaluation on Oct 29, 2015. Thank you very much!',11,'2015-10-29',4,1,NULL);
/*!40000 ALTER TABLE `student_feedback` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_account`
--

DROP TABLE IF EXISTS `user_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_account` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `password` varchar(150) NOT NULL,
  `person` int(10) unsigned NOT NULL,
  `user_group` smallint(3) unsigned NOT NULL,
  `active` tinyint(1) DEFAULT NULL,
  `active_from` datetime DEFAULT NULL,
  `deactivated_on` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  KEY `fk__user_user_group1_idx` (`user_group`),
  KEY `fk__user_person1_idx` (`person`),
  CONSTRAINT `fk__user_person1` FOREIGN KEY (`person`) REFERENCES `person` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk__user_user_group1` FOREIGN KEY (`user_group`) REFERENCES `user_group` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=170 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_account`
--

LOCK TABLES `user_account` WRITE;
/*!40000 ALTER TABLE `user_account` DISABLE KEYS */;
INSERT INTO `user_account` VALUES (1,'P15/1678/2014','1aca6dca73cc19c35147c3c7d2707ca78dd2495c7075c4b41912c140c4b39c00',1,3,1,NULL,NULL,NULL),(2,'P15/1659/2014','4f863dd0dfaab6f024031c951f20c29f2efd6268557ec75c66ee22affdca79be',2,3,0,NULL,NULL,NULL),(3,'P15/1660/2014','a46405be0eeb4de49d97838c2405b5ec71195bea2f648ef635561dc36a7ed6f3',3,3,0,NULL,NULL,NULL),(4,'P15/1661/2014','dbbec3d22822fdde2c8a0778e633d282b0450120d20af693adf8c586cc08b22c',4,3,1,NULL,NULL,NULL),(5,'P15/1662/2014','f55791b8ad1ea895b8bb46679455e4821c08c0cf5a03199969f647a241a8fce1',5,3,0,NULL,NULL,NULL),(6,'T221','52bd476f97069d34495850d97461be8540be99581f1db71420feaa815f13ce03',6,2,0,NULL,NULL,NULL),(7,'T223','52a0035bda9762f575a313172f0958354efe732088521f8ca9ff90e268a25ff2',7,2,0,NULL,NULL,NULL),(8,'T224','201cc20700ce3ce412154cbdbd3d5b7aa3ea570516d6426bde0ef05f4af8629c',8,2,0,NULL,NULL,NULL),(9,'T226','b076f0e8ca5c8e6e69bbf75fb3bc7a1d168885b327ca8d39041c4c1037c2b250',9,5,0,NULL,NULL,NULL),(10,'T225','5e83cb0900ef44890efce4abe22ebe18fc034f8afaab886a5ab71b7685965df5',10,2,0,NULL,NULL,NULL),(13,'T15/1678/2014','4ef7ff94dd9d27b6aa431b74672ce28ee1576100e05a195565f7478c85916eb6',13,3,0,NULL,NULL,NULL),(14,'P15/36965/2016','2c5c6de873654ed0f13668b8918304c3b21827c1bc17ba86fde6be73a3c7ef64',14,3,1,NULL,NULL,NULL),(15,'P15/1703/2016','823b0279823733f814894a3f8a24c1eb558d6aba798bf7b84cc6b868bb7d3484',15,3,1,NULL,NULL,NULL),(16,'P15/1699/2016','b4cf1b990280c1e0550b83d50d033d85c10f7802ae9fbd579a018875a77a8fa3',16,3,1,NULL,NULL,NULL),(17,'P15/34475/2014','2dfa91de326845bd54896f0ba5125032798e68741c92bc9c334ecbe692086892',17,3,1,NULL,NULL,NULL),(18,'P15/2753/2016','e54336c4f29f8c9f40bbceffd45293d89f7c99c8d00e6fbe431c3a9b08231c07',18,3,1,NULL,NULL,NULL),(19,'P15/36478/2016','27ef5b68c771ee8206b8a2e28ae2a7cf155b651c9975e000d3d190b9ae9dcac0',19,3,1,NULL,NULL,NULL),(20,'P15/1726/2016','413d0b67e95a8d83d44b65b8321103d7cc2dbf0cb1cc6c1a10eb2ccd71403502',20,3,1,NULL,NULL,NULL),(21,'P15/36780/2016','ea3d16cc24eaa9b21b924a3f1a2078cdf29fc5cb9f60db1df201556f5b4ccf58',21,3,1,NULL,NULL,NULL),(22,'P15/1721/2016','376c89aec254139311abc0d41cfc05157378c73e55fd0923a13a698af122da13',22,3,1,NULL,NULL,NULL),(23,'P15/1728/2016','69b54066d724b41511d8ae57f931f72580439004f42f54240b9d07070d95a9bb',23,3,1,NULL,NULL,NULL),(24,'P15/4630/2015','7fd197dd6801345413e937dacd51d49839e1fae2624f7d2735025f657b3839d6',24,3,1,NULL,NULL,NULL),(25,'P15/1709/2016','59621bc02dbb050e7bf0b51afef80e95b16f1f1f254e26e5e1271515299c2483',25,3,1,NULL,NULL,NULL),(26,'P15/31737/2015','5bcb2fbf729340d8e84b2cd3205a3782f47914b55f0a38224063b15bcf8d094c',26,3,1,NULL,NULL,NULL),(27,'P15/1719/2016','59cfa5a2bc0c08148b5b7b6530e84833f52c9743e46939fb19198efe6f159ebf',27,3,1,NULL,NULL,NULL),(28,'P15/1716/2016','062ef09461db8dd1ce707263be9c47a89e9b6ee927d7c6237a24697ed55711f5',28,3,1,NULL,NULL,NULL),(29,'P15/33494/2015','9aaceaf1ee87d25c29a4c146e384d242a3e2c3a729fe964b033362a8b5c5db1c',29,3,1,NULL,NULL,NULL),(30,'P15/37529/2016','ea198da81fedb799e4a041784b870910b7721c2338233b9f647badef69e4f4d0',30,3,1,NULL,NULL,NULL),(31,'P15/2559/2015','977385cfd8cd0bebbe3475a5c9823100026039ac14e103fab232c6318b7e4673',31,3,1,NULL,NULL,NULL),(32,'P15/1689/2016','5778e27de7deaf153568603fdf6233a07f189a2281e1515d04dd44b23b9bbdd6',32,3,1,NULL,NULL,NULL),(33,'P15/36376/2016','5e9e156945246435c976d40929aa98f497c0bf5c0ef6f2a2e76f277863f5ac7a',33,3,1,NULL,NULL,NULL),(34,'P15/35947/2015','cded24d9394d8e0b7000d109b89bda386ae4ee4f14fb1e5ed3ee79aab166964a',34,3,1,NULL,NULL,NULL),(35,'P15/1706/2016','2c670c622ef86f9f9c4b43945d95f3ac52bc10151f351571dacc30bbd132630f',35,3,1,NULL,NULL,NULL),(36,'P15/32156/2015','a7768721a2262f9f3698f79caed9f8c8ba3ac0ad1db532609c5cc47036a9433f',36,3,1,NULL,NULL,NULL),(37,'P15/1713/2016','24484471a1367970f39788306de0b5a8237c81cdb9d19342032f1721d48dbdf8',37,3,1,NULL,NULL,NULL),(38,'P15/36158/2015','4b72c496c267fd611388ea873b5a0e75aa42ddb3a50d54c6ad3aaf70a78dc2cb',38,3,1,NULL,NULL,NULL),(39,'P15/36969/2016','f5254a8b3044cfdd31c3a53ebbed07fb2b21f60f4d602cf1daefa9037f7b0874',39,3,1,NULL,NULL,NULL),(40,'P15/1698/2016','728789b46274584eb693e191cb0a6559dd9ee57e9df03defc9e3cc66e323aed9',40,3,1,NULL,NULL,NULL),(41,'P15/1696/2016','d5d502afac6368637c81196f273ccd83faae6424bc3d2b13027a0dc0040a7785',41,3,1,NULL,NULL,NULL),(42,'P15/37272/2016','e1c4f7f85f65d2ca8d9e1bf07b76b92c1761b78876563367e1105f5e8287a578',42,3,1,NULL,NULL,NULL),(43,'P15/36268/2016','ec8052f5e705882ac41508c1c291d90279f9671d374e62dcf4b1a98d596d0e86',43,3,1,NULL,NULL,NULL),(44,'P15/1708/2016','ccf62ae81565386d7f222ed0774c26d3df452148e92b1c1f0573eb3fc85ba44f',44,3,1,NULL,NULL,NULL),(45,'P15/36341/2015','aaa107cc9a0f48a99ebabda913ba3808322571ef3b2fb318700e465544981a1d',45,3,1,NULL,NULL,NULL),(46,'P15/37046/2016','2e09af1f27f1e526b038b4beca060bfc106096557e959d0d146938b7bfddb118',46,3,1,NULL,NULL,NULL),(47,'P15/37269/2016','48e77f10cf9075fc8f069a7b958f31f2514f806314c7e519a4377815bbb0e405',47,3,1,NULL,NULL,NULL),(48,'P15/37344/2016','ee97cb6c32c9440e93c0a6257ef188e24f45eb24929fd5044f742d54e187b78f',48,3,1,NULL,NULL,NULL),(49,'P15/36117/2015','75299e204c3be3d569132afe4276404c5f3e148d2008a8cc6e639bfc0cfcace8',49,3,1,NULL,NULL,NULL),(50,'P15/30653/2015','e79d10b717160bd0733a2ac29889f092d8eec4b204213acb8604f22543a76967',50,3,1,NULL,NULL,NULL),(51,'P15/37254/2016','0ce55b20bd4d5639ed5fc8c89c174bbc571cc1031b8b963204ed3e8ecd8f4a02',51,3,1,NULL,NULL,NULL),(52,'P15/36102/2015','5699fd7501b4075d65d48ca5164662c2257c6514c3b1b533025905e570f88d5c',52,3,1,NULL,NULL,NULL),(53,'P15/37738/2016','11a26e5cc5bd8376e01bb4307fcc7eab64ed89a08bcab7c4e357851aa59eae21',53,3,1,NULL,NULL,NULL),(54,'P15/31916/2015','b0a71831f31725624be8199f31c5f4b6194551523f2c3a725bb1e4f791f37348',54,3,1,NULL,NULL,NULL),(55,'P15/37641/2016','da489e57311b82600e9986bd02b5e0851e208152cc0d588efebe8529808b4986',55,3,1,NULL,NULL,NULL),(56,'P15/36237/2016','ee7a88a095281ea2034181df2195ce69caeef35870d329eb237845129d10ce02',56,3,1,NULL,NULL,NULL),(57,'P15/36507/2016','9e9b7727196bbfcbd0927851bf723771e159d6ab3ac2a03a4434a810e8214c71',57,3,1,NULL,NULL,NULL),(58,'P15/35753/2015','10cc0bba84020e9ab5dc7516ee771d729b57daedc775e919290a5e4e8f659ca3',58,3,1,NULL,NULL,NULL),(59,'P15/1690/2016','c347a4763b6c037fdbc149ed3ecf43866104c9169414840d818e05c93e581254',59,3,1,NULL,NULL,NULL),(60,'P15/37231/2016','ec7fb54b770eb1979afbae20a97e6c3b800e7448deab2a9db0f3a481716ca814',60,3,1,NULL,NULL,NULL),(61,'P15/36897/2016','07ddf43ca316c8d5ddcc14d3cbd5735fa6ab168b13a95d8de363a029840724f9',61,3,1,NULL,NULL,NULL),(62,'P15/36901/2016','54ea77ceb260749e5c8a29715bb2b649d693f9e8c9e83e0ce33b8b7ac9255646',62,3,1,NULL,NULL,NULL),(63,'P15/35462/2015','bc2bde2e1a32b2dff68471351ae620d0db25dd11a3233b851dc14938f7a75def',63,3,1,NULL,NULL,NULL),(64,'P15/1711/2016','84a0423838c2ccca9eb9482ba112e0362caef91f0adbcb1b1edf5cfb494ce859',64,3,1,NULL,NULL,NULL),(65,'P15/1715/2016','6a9ce93d7cd1637801d1c78d01f868277953b116cae35178d8e92f3e1baf27af',65,3,1,NULL,NULL,NULL),(66,'P15/36157/2015','815856fc5b3369ae655a14ac171f2ba5fc024f2e8a92970745fc7a0ff7579a37',66,3,1,NULL,NULL,NULL),(67,'P15/36569/2016','e8bc67c2fbd99a68da77db2e89848fd1b0c84544b1d763048d062d342d3747ed',67,3,1,NULL,NULL,NULL),(68,'P15/37364/2016','6f3a5b6834f94021cd93bf80df06dcf8b9149f16d89efb2b4f59455469be6e2a',68,3,1,NULL,NULL,NULL),(69,'P15/34677/2014','e08218b6e31f23c9e962f536f748b4619a8ca834f32069e351a3d539d12d8e57',69,3,1,NULL,NULL,NULL),(70,'P15/1718/2016','c9d641ad7b973561b22504aa61244522b5a0c286fe27e15ee1fa6548a7c774ca',70,3,1,NULL,NULL,NULL),(71,'P15/37561/2016','187440ef0d06d5d3457ca869480b5c1bce31cc9115c89eb291d9401ece68f1cc',71,3,1,NULL,NULL,NULL),(72,'P15/34906/2014','8e3886a81f31a3036b1e286280e3f375b70d65071fcc767894ddb883c260de64',72,3,1,NULL,NULL,NULL),(73,'P15/36089/2015','9bf356d98d79cfb85862ff74b764827d61d1b793397a50792c0665bede98d178',73,3,1,NULL,NULL,NULL),(74,'P15/1714/2016','b4738a7a503b3e1f7dc09c230932127db3dde449dc2cfd31df8ca8bc69adf7e6',74,3,1,NULL,NULL,NULL),(75,'P15/1841/2016','1e0f03447980c94e0d4756355127d9563dde1d07130d8121537748f4bd32944f',75,3,1,NULL,NULL,NULL),(76,'P15/1717/2016','74b8a59b0da60724441335559eb01876b8a31ce90f8f482220df395a45254876',76,3,1,NULL,NULL,NULL),(77,'P15/1694/2016','01d40043d2a11ea4f390e08296f38dee2a25e68ff9e74d1d764bfce763dd86db',77,3,1,NULL,NULL,NULL),(78,'P15/1701/2016','b915b783849d507c00cf09cc21272dc3152da1eccad9f520197877188ac1c703',78,3,1,NULL,NULL,NULL),(79,'P15/35805/2013','0a639b22f1a635e5fab3e5642352d27daa678bfa37807a85fd0e4cc0b258719e',79,3,1,NULL,NULL,NULL),(80,'P15/1693/2016','6fd37ffe87c928b48b775b8540cb3fd5961c2dff360cbd15c2f9d0bcb95d3f46',80,3,1,NULL,NULL,NULL),(81,'P15/31178/2015','1827a35df3011b54de77d369500fcbfd70fe3063f102ab548594bb9542c7fb55',81,3,1,NULL,NULL,NULL),(82,'P15/35160/2014','596e008d90c18e351cdf7cca52dfeb81ec9c6e7b7206573e91eea860fce0fc5c',82,3,1,NULL,NULL,NULL),(83,'P15/36503/2016','48e21537b7c0c35095baf004e8074bacbc08c8a45efb26e9f3f2287e82b0e2f3',83,3,1,NULL,NULL,NULL),(84,'P15/1702/2016','f2348b46790e12c9e4e6318ddfdfdd07ed0ca06e7514eb4cf58a2b271bf38cd6',84,3,1,NULL,NULL,NULL),(85,'P15/36168/2015','9b9d43faaebbce9c38e47faf6b5ff8280358ac16472a9dae1fee5d41d411d996',85,3,1,NULL,NULL,NULL),(86,'P15/36132/2015','45b7114439e76c9e78a8a5bcec048e83330d55174525cd3f8f2ca0dd295cfac1',86,3,1,NULL,NULL,NULL),(87,'P15/35834/2015','ceed5c30d5116e7fa8bb8661b32c515ef64fc585649d97aace021ed9cbbe264b',87,3,1,NULL,NULL,NULL),(88,'P15/36812/2016','69d1a40516e8fddd097bac753dd274029bf2b2e16618e414d5ac59c4be963449',88,3,1,NULL,NULL,NULL),(89,'P15/30459/2015','a1e0f336c787bf6fe7d739157882d1ba67921fd14e1d01d7b35a3964a6751872',89,3,1,NULL,NULL,NULL),(90,'P15/35101/2015','64b4f413adb50bfeab98b7ea28ed7eee2fdd6772b79f39bb757f03b28e182ac5',90,3,1,NULL,NULL,NULL),(91,'P15/33977/2014','e34d027df84c2c08c3f0281abb99bc3e11166cb72fa5f9b19cdd9c831a95bec2',91,3,1,NULL,NULL,NULL),(92,'P15/37874/2016','d2b0f9288c212072b67567dd586b755350941fc42c7013a5f943adb1587d277c',92,3,1,NULL,NULL,NULL),(93,'P15/36234/2016','ed11e1a82469186e85eda9f2bedbb6f7bb4845f2ba8d3fae2a73669b1371a875',93,3,1,NULL,NULL,NULL),(94,'P15/36821/2016','43f04cf54a629b42691de043b38eca08be207538436b1eaed51cb8a78f40b6d0',94,3,1,NULL,NULL,NULL),(95,'P15/35893/2015','952591be27b23634ddcaf1bff0e93001360024ec3721bde3672c9c2f29e0b99f',95,3,1,NULL,NULL,NULL),(96,'P15/35097/2015','55ed83adaaa25937abd2a69c34c1dcda1e51a037b7b33f33472069724a3e6702',96,3,1,NULL,NULL,NULL),(97,'P15/37139/2016','dd7587a61696ca4cf81315a516f94e816e1fcdac5f9fc9bd41f5bd2bd4b49057',97,3,1,NULL,NULL,NULL),(98,'P15/1697/2016','5cfd116be4b0dcd0a2d421a4510c4e94a9d1e331c7d0c214aeaf9a9f3c2e99b9',98,3,1,NULL,NULL,NULL),(99,'P15/37217/2016','1f4e313feff928c1517a1bb6b60141680cadea7108c2d2c7cbc27fcdbdbab00f',99,3,1,NULL,NULL,NULL),(100,'P15/37957/2016','5f8a54dfacb4e4291d417e992d65415537b7fccc010a7194746327866827dbc5',100,3,1,NULL,NULL,NULL),(101,'P15/1695/2016','8fb695b3fcfb808c15227e0bd51fbc0ad40d9e676eb4cff5409395c85ba6651e',101,3,1,NULL,NULL,NULL),(102,'P15/35759/2015','cdb307d3162038b7f7927289173090e9d44d2f239e02200a1e5d48206a2df5e1',102,3,1,NULL,NULL,NULL),(103,'P15/36496/2016','108fcdf790449669fa9873955b412a1d152c55084c144d00891df6ce6a43f8f7',103,3,1,NULL,NULL,NULL),(104,'P15/36653/2016','70bc0942ce6d02f8cd4d04e42dff4da97db05779a13c832480151684e1ed7135',104,3,1,NULL,NULL,NULL),(105,'P15/30324/2015','2167811da286ed8f497700aab5e97ee2d2e69836dd0b3fa084b193bd884ec4b1',105,3,1,NULL,NULL,NULL),(106,'P15/1433/2016','2b69a826c7f908b4b613988a555c52e2bfabb07f2663df11563c52bf946d9e35',106,3,1,NULL,NULL,NULL),(107,'P15/37017/2016','6bbdeb0bdc6de598ed63947a4af1343e7f599eaee05c55c6cbdc80014c138f2b',107,3,1,NULL,NULL,NULL),(108,'P15/38293/2016','63a545043018de88398c6d1b921799da1b243343c44fa75bbcdfc18367861b4a',108,3,1,NULL,NULL,NULL),(109,'P15/1724/2016','7e77ce4fc08010b270aaf4de5a0e806b8b890ad264eac20692a18b40f8562236',109,3,1,NULL,NULL,NULL),(110,'P15/34665/2014','b59a806f545e53a883b2d53428d40f29ff73eda145e51ad49e47fab17581eb3c',110,3,1,NULL,NULL,NULL),(111,'P15/30363/2015','99b5c7a70bd742362e207a809f4be30e254b8a615d4a82954809dc24b7c7a8c8',111,3,1,NULL,NULL,NULL),(112,'P15/37821/2016','de0f2a371fadba410898b3b3161962c52971d82bbd3f6473220efd05f0b90360',112,3,1,NULL,NULL,NULL),(113,'P15/1710/2016','759675d79183353789a2f1306a1e86c6190c0ccd8ea6f548e74f634fec0ac9b4',113,3,1,NULL,NULL,NULL),(114,'P15/37010/2016','d88e7852d0cd4bd69e71404c1e67cccfe71ea16651e5879717cdbc9a16ec604b',114,3,1,NULL,NULL,NULL),(115,'P15/1692/2016','10fc6de3065302deee3267710f9c8517746e7c28f5ab2b4d24880f54d790b84b',115,3,1,NULL,NULL,NULL),(116,'P15/1705/2016','528723ce030b58e203093c49ec95175a77921626c6c9b81726f3c1c65fb16657',116,3,1,NULL,NULL,NULL),(117,'P15/38236/2016','1f902cf5ef6ecadb2b6d03e6942b7cfba9b2152a4c2b009159d237b5619d811d',117,3,1,NULL,NULL,NULL),(118,'P15/36533/2016','a75d98b355b1fc30f1ad83e66060aff67dbf374c397d19f1423c3c91d25a4fca',118,3,1,NULL,NULL,NULL),(119,'P15/36513/2016','69f485fa5dae156c6ccf74e4f0d061b049a3432214c336e4a2045f15a36325b3',119,3,1,NULL,NULL,NULL),(120,'P15/37125/2016','a8c6808a63fd822f4625154182892cd52729c66ae148fe287d6a9d7ac7796e3b',120,3,1,NULL,NULL,NULL),(121,'P15/1722/2016','0985e86c31046068ebc2fbcfe93ea098846d4100039edddff0329ccee009d4f9',121,3,1,NULL,NULL,NULL),(122,'P15/1723/2016','a0d96bc1ecaca6f849fc2f70818d82533d98bafe3a7026c0bec02a42ca90ad8d',122,3,1,NULL,NULL,NULL),(123,'P15/37019/2016','1969022c36fbcd14e885159fa29cb3e4d17d036ecad9c9d32510e29fc661c086',123,3,1,NULL,NULL,NULL),(124,'P15/36238/2016','3a701ee6649adcf8cf02481456982741f658d5342cd28ea35da64a622246eac7',124,3,1,NULL,NULL,NULL),(125,'P15/36648/2016','6c6ccb24e4845a74a676bbc2e3d2f1fb8b63d396fdb87d918e8b84180be33505',125,3,1,NULL,NULL,NULL),(126,'P15/36076/2015','98e037de7b13012928b578ac463a8f2b6d728c8871b334037bb9d4b1972aff7d',126,3,1,NULL,NULL,NULL),(127,'P15/36134/2015','aa2d1136dceb9eab2232b198ebb5f96d68eef76b16bcbecee6656ad78ff3108f',127,3,1,NULL,NULL,NULL),(128,'P15/1688/2016','fdefefb4c72e99a9ca6a6284b57c8045fd3a08a39f9bec0b019a239d0e38983c',128,3,1,NULL,NULL,NULL),(129,'P15/31143/2015','bf1a59be27bf1ae9a155924defd4e36e1405ff594161423319d75aaae2e94a2d',129,3,1,NULL,NULL,NULL),(130,'P15/35037/2014','1b45cd1d2e2e3476a56e3bc2e51f4aadb14ac311ee778111f9228f368f8c6315',130,3,1,NULL,NULL,NULL),(131,'P15/1712/2016','b8324e3e7687667e561fa5b962d3b999b933185dd70b7fc578258fa4fc1beb98',131,3,1,NULL,NULL,NULL),(132,'P15/1700/2016','7bc9e0e3e47bc8e6ae0d717851d44004a8e32efee0e684824c47a0f81c4e476d',132,3,1,NULL,NULL,NULL),(133,'P15/1725/2016','00db8981a932c4a2fe7c7f7532ce630c5d34b3a73835d9cdf5992c81f34d4a03',133,3,1,NULL,NULL,NULL),(134,'P15/36756/2016','9c268c50bf7d3608cdfd396581b3f6f82044f3ae261648df1567d7a0648ddb5f',134,3,1,NULL,NULL,NULL),(135,'P15/37073/2016','54eb033b30bbfa85178df1d9775f9ede4640426989d36f8a877a4abe13894c7f',135,3,1,NULL,NULL,NULL),(136,'P15/1720/2016','ab5465ef4488db63bd2656a61af960cfd82839779b66bb435078027560caa8fb',136,3,1,NULL,NULL,NULL),(137,'P15/36874/2016','1de896e5ec56edd77cb98c172b6cb579ab1952b00d766a86e16d118c77e718cd',137,3,1,NULL,NULL,NULL),(138,'P15/1727/2016','6a00aad0ebf0eb7fd6cb1523f1dee3b48fc505fd69228f37a0080fd11e83f138',138,3,1,NULL,NULL,NULL),(139,'P15/34017/2015','e1f8a6f299cbcc76622d4ad91b075f275e4617c11f176c865fec344a71c45f3b',139,3,1,NULL,NULL,NULL),(140,'P15/37353/2016','eecdaa9a81ec262281e849c2414d19ce5e7a99a21990b9bfbe5c16646e756408',140,3,1,NULL,NULL,NULL),(141,'P15/30269/2015','3f1f37d900eb9a0e8bb98bb0d8b516ea12eaf3228f7672005487f6bc661effe5',141,3,1,NULL,NULL,NULL),(142,'P15/35324/2015','8dd42800cdf0c3170b07b6eeb3967a3a98fc26aa42fae88c0d6bbab857aa082d',142,3,1,NULL,NULL,NULL),(143,'P15/35255/2015','47c1fe4c50a887b9b7de66cf86c3658faa2e46a06680d030d83c6feec211ceaa',143,3,1,NULL,NULL,NULL),(144,'P15/38119/2016','9d1aafd49d8197d0cc2ff9383b3b72438946a7fe208490a71616a9496a53599b',144,3,1,NULL,NULL,NULL),(145,'P15/36337/2016','c1cef4fc6678a16752d4478b64b24049531620b20227b7db3e9e2cff5d08f9e5',145,3,1,NULL,NULL,NULL),(146,'P15/35291/2015','23b7a58a9156c0e6ff4212e1419f55a9130ef17dd20fa3247eb61cc6c785be72',146,3,1,NULL,NULL,NULL),(147,'P15/35945/2015','573c5c25543d7718a1bc0a9cf130d3b1055f933e56403f6da01b4fd5886f243c',147,3,1,NULL,NULL,NULL),(148,'P15/1729/2016','12c020f9783fd6560999df6065ccf6577f6f74d77e4891cc65f5cb3e581c5623',148,3,1,NULL,NULL,NULL),(149,'P15/37451/2016','9c7cc077649b9e62644f83384e84c7c7a0c579bb4cf4cb19bd447323f41bc90e',149,3,1,NULL,NULL,NULL),(150,'P15/36551/2016','69429a7ee742370535824a25bcff46a3554f6e2e250546ad9e63bda54617c279',150,3,1,NULL,NULL,NULL),(151,'P15/35280/2015','3058d0020103af1d15ca0160979031ed2f6423f5252662cf5c6b2c970118c81c',151,3,1,NULL,NULL,NULL),(152,'P15/36567/2016','6e28e20665214b8a6c3200281c4b21d22993351a587de9d2b8c0542827bd66db',152,3,1,NULL,NULL,NULL),(153,'P15/36349/2016','0e0935678082e17b172b1a4b55672ece23b46c353222e5af42dac037202da6cf',153,3,1,NULL,NULL,NULL),(154,'P15/1691/2016','3693c3404e69aa269065bb0f5d183a4bdc51166984646a25e05918e628b95a7a',154,3,1,NULL,NULL,NULL),(155,'P15/38166/2016','438349f8e9c92230ce1c68f042bcc92850e3ee54452376293afe49f0dd643d63',155,3,1,NULL,NULL,NULL),(156,'P15/37390/2016','ec30d5d0c94c8b37fa3dd966e64652f00064373d3f8758bc5e35ca715a97b0c1',156,3,1,NULL,NULL,NULL),(157,'P15/35060/2015','358e70ccf59c6735e582f5da645f4bb26e21664f99ddef8d16f2f50d62f43fa6',157,3,1,NULL,NULL,NULL),(158,'P15/37020/2016','66863a945abd27c38ccb262fe956b291d5e848555d14463ec1189eac2571bbb0',158,3,1,NULL,NULL,NULL),(159,'P15/1704/2016','b4f8b8d69f92c9978e8ac96205e9a645517d14777c707c4d1e5c3ec46ac76d08',159,3,1,NULL,NULL,NULL),(160,'P15/35954/2015','043d42116badeab5def31a78158175a8b5598a23fd2ab55d5c556df1b3cc82d7',160,3,1,NULL,NULL,NULL),(161,'P15/1707/2016','bdcf9bfbd9cc79926bc630bc67a33fa4647d5cf48ab7b35b4bd58fa27d54fda6',161,3,1,NULL,NULL,NULL),(162,'P15/36820/2016','f58b442a7e86905fd056ed8b93f7cb11cfe0f54512cb1d0a7b284e40aedfa02a',162,3,1,NULL,NULL,NULL),(163,'P15/37847/2016','02032898fda59e12faab931c681331ec530e050e1236a60cf1d900208a11006a',163,3,1,NULL,NULL,NULL),(164,'CSC111Miriti','e2b4aa5e0ba3a6db1e9a571c14e45bdf64c9afd72472c560f7c9930f073adffc',164,2,1,NULL,NULL,NULL),(165,'CSC112Omwenga','46e11654d07e5dafafe1bbe965a549cc0d2e0de670e04d9c16ac79cf84476c46',165,2,1,NULL,NULL,NULL),(166,'CSC113Abungu','6aafe6ed78ced396ca499c455b605e0d4e1ddb9d7f6499697c161335b075ccbd',166,2,1,NULL,NULL,NULL),(167,'CSC114Abungu','8e602119509f2fd70f1281ab8a65b4bbba021e9071766a6e66dac2874dae0b40',167,2,1,NULL,NULL,NULL),(168,'CCS001Mary','b7cf4cc9d326608c466907733f13f1abb08082c9928465784210f623980eae64',168,2,1,NULL,NULL,NULL),(169,'CSC009Muriu','0ba07c13ead3f1d1f7566c2583f086b1cf603c8d2cc9ce292da712a2dd4778d5',169,2,1,NULL,NULL,NULL);
/*!40000 ALTER TABLE `user_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_group`
--

DROP TABLE IF EXISTS `user_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_group` (
  `id` smallint(3) unsigned NOT NULL AUTO_INCREMENT,
  `user_group` varchar(45) NOT NULL,
  `active` tinyint(1) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `user_group_UNIQUE` (`user_group`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_group`
--

LOCK TABLES `user_group` WRITE;
/*!40000 ALTER TABLE `user_group` DISABLE KEYS */;
INSERT INTO `user_group` VALUES (1,'Management',NULL,NULL),(2,'Lecturer',NULL,NULL),(3,'Student',NULL,NULL),(4,'Other staff',NULL,NULL),(5,'Admin',NULL,NULL);
/*!40000 ALTER TABLE `user_group` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-11-02 23:48:02
