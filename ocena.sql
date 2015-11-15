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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `assessed_evaluation`
--

LOCK TABLES `assessed_evaluation` WRITE;
/*!40000 ALTER TABLE `assessed_evaluation` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `assessed_evaluation_comment`
--

LOCK TABLES `assessed_evaluation_comment` WRITE;
/*!40000 ALTER TABLE `assessed_evaluation_comment` DISABLE KEYS */;
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
  `name` varchar(300) NOT NULL,
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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `college`
--

LOCK TABLES `college` WRITE;
/*!40000 ALTER TABLE `college` DISABLE KEYS */;
INSERT INTO `college` VALUES (1,'College Agriculture and Veterinary Sciences','CAVS',1,1,NULL),(2,'College of Biological and Physical Sciences 	','CBPS',1,1,NULL),(3,'College of Architecture and Engineering','CAE',1,1,NULL),(4,'College of Education And External Studies 	','CEES',1,1,NULL),(5,'College of Health Sciences 	','CHS',1,1,NULL),(6,'College of Humanities and Social Sciences','CHSS',1,1,NULL),(7,'Alumni Association','UONAA',1,1,NULL);
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
) ENGINE=InnoDB AUTO_INCREMENT=445 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contact`
--

LOCK TABLES `contact` WRITE;
/*!40000 ALTER TABLE `contact` DISABLE KEYS */;
INSERT INTO `contact` VALUES (1,1,NULL),(2,1,NULL),(3,1,NULL),(4,1,NULL),(5,1,NULL),(6,1,NULL),(7,1,NULL),(8,1,NULL),(9,1,NULL),(10,1,NULL),(11,1,NULL),(12,1,NULL),(13,1,NULL),(14,1,NULL),(19,1,NULL),(20,1,NULL),(21,1,NULL),(22,1,NULL),(23,1,NULL),(24,1,NULL),(25,1,NULL),(26,1,NULL),(27,1,NULL),(28,1,NULL),(29,1,NULL),(30,1,NULL),(31,1,NULL),(32,1,NULL),(33,1,NULL),(34,1,NULL),(35,1,NULL),(36,1,NULL),(37,1,NULL),(38,1,NULL),(39,1,NULL),(40,1,NULL),(41,1,NULL),(42,1,NULL),(43,1,NULL),(44,1,NULL),(45,1,NULL),(46,1,NULL),(47,1,NULL),(48,1,NULL),(49,1,NULL),(50,1,NULL),(51,1,NULL),(52,1,NULL),(53,1,NULL),(54,1,NULL),(55,1,NULL),(56,1,NULL),(57,1,NULL),(58,1,NULL),(59,1,NULL),(60,1,NULL),(61,1,NULL),(62,1,NULL),(63,1,NULL),(64,1,NULL),(65,1,NULL),(66,1,NULL),(67,1,NULL),(68,1,NULL),(69,1,NULL),(70,1,NULL),(71,1,NULL),(72,1,NULL),(73,1,NULL),(74,1,NULL),(75,1,NULL),(76,1,NULL),(77,1,NULL),(78,1,NULL),(79,1,NULL),(80,1,NULL),(81,1,NULL),(82,1,NULL),(83,1,NULL),(84,1,NULL),(85,1,NULL),(86,1,NULL),(87,1,NULL),(88,1,NULL),(89,1,NULL),(90,1,NULL),(91,1,NULL),(92,1,NULL),(93,1,NULL),(94,1,NULL),(95,1,NULL),(96,1,NULL),(97,1,NULL),(98,1,NULL),(99,1,NULL),(100,1,NULL),(101,1,NULL),(102,1,NULL),(103,1,NULL),(104,1,NULL),(105,1,NULL),(106,1,NULL),(107,1,NULL),(108,1,NULL),(109,1,NULL),(110,1,NULL),(111,1,NULL),(112,1,NULL),(113,1,NULL),(114,1,NULL),(115,1,NULL),(116,1,NULL),(117,1,NULL),(118,1,NULL),(119,1,NULL),(120,1,NULL),(121,1,NULL),(122,1,NULL),(123,1,NULL),(124,1,NULL),(125,1,NULL),(126,1,NULL),(127,1,NULL),(128,1,NULL),(129,1,NULL),(130,1,NULL),(131,1,NULL),(132,1,NULL),(133,1,NULL),(134,1,NULL),(135,1,NULL),(136,1,NULL),(137,1,NULL),(138,1,NULL),(139,1,NULL),(140,1,NULL),(141,1,NULL),(142,1,NULL),(143,1,NULL),(144,1,NULL),(145,1,NULL),(146,1,NULL),(147,1,NULL),(148,1,NULL),(149,1,NULL),(150,1,NULL),(151,1,NULL),(152,1,NULL),(153,1,NULL),(154,1,NULL),(155,1,NULL),(156,1,NULL),(157,1,NULL),(158,1,NULL),(159,1,NULL),(160,1,NULL),(161,1,NULL),(162,1,NULL),(163,1,NULL),(164,1,NULL),(165,1,NULL),(166,1,NULL),(167,1,NULL),(168,1,NULL),(169,1,NULL),(170,1,NULL),(171,1,NULL),(172,1,NULL),(173,1,NULL),(174,1,NULL),(175,1,NULL),(176,1,NULL),(177,1,NULL),(178,1,NULL),(179,1,NULL),(180,1,NULL),(181,1,NULL),(182,1,NULL),(183,1,NULL),(184,1,NULL),(185,1,NULL),(186,1,NULL),(187,1,NULL),(188,1,NULL),(189,1,NULL),(190,1,NULL),(191,1,NULL),(192,1,NULL),(193,1,NULL),(194,1,NULL),(195,1,NULL),(196,1,NULL),(197,1,NULL),(198,1,NULL),(199,1,NULL),(200,1,NULL),(201,1,NULL),(202,1,NULL),(203,1,NULL),(204,1,NULL),(205,1,NULL),(206,1,NULL),(207,1,NULL),(208,1,NULL),(209,1,NULL),(210,1,NULL),(211,1,NULL),(212,1,NULL),(213,1,NULL),(214,1,NULL),(215,1,NULL),(216,1,NULL),(217,1,NULL),(218,1,NULL),(219,1,NULL),(220,1,NULL),(221,1,NULL),(222,1,NULL),(223,1,NULL),(224,1,NULL),(225,1,NULL),(226,1,NULL),(227,1,NULL),(228,1,NULL),(229,1,NULL),(230,1,NULL),(231,1,NULL),(232,1,NULL),(233,1,NULL),(234,1,NULL),(235,1,NULL),(236,1,NULL),(237,1,NULL),(238,1,NULL),(239,1,NULL),(240,1,NULL),(241,1,NULL),(242,1,NULL),(243,1,NULL),(244,1,NULL),(245,1,NULL),(246,1,NULL),(247,1,NULL),(248,1,NULL),(249,1,NULL),(250,1,NULL),(251,1,NULL),(252,1,NULL),(253,1,NULL),(254,1,NULL),(255,1,NULL),(256,1,NULL),(257,1,NULL),(258,1,NULL),(260,1,NULL),(261,1,NULL),(262,1,NULL),(263,1,NULL),(264,1,NULL),(265,1,NULL),(266,1,NULL),(267,1,NULL),(268,1,NULL),(269,1,NULL),(270,1,NULL),(271,1,NULL),(272,1,NULL),(273,1,NULL),(274,1,NULL),(275,1,NULL),(276,1,NULL),(277,1,NULL),(278,1,NULL),(279,1,NULL),(280,1,NULL),(281,1,NULL),(282,1,NULL),(283,1,NULL),(284,1,NULL),(285,1,NULL),(286,1,NULL),(287,1,NULL),(288,1,NULL),(289,1,NULL),(290,1,NULL),(291,1,NULL),(292,1,NULL),(293,1,NULL),(294,1,NULL),(295,1,NULL),(296,1,NULL),(297,1,NULL),(298,1,NULL),(299,1,NULL),(300,1,NULL),(301,1,NULL),(302,1,NULL),(303,1,NULL),(304,1,NULL),(305,1,NULL),(306,1,NULL),(307,1,NULL),(308,1,NULL),(309,1,NULL),(310,1,NULL),(311,1,NULL),(312,1,NULL),(313,1,NULL),(314,1,NULL),(315,1,NULL),(316,1,NULL),(317,1,NULL),(318,1,NULL),(319,1,NULL),(320,1,NULL),(321,1,NULL),(322,1,NULL),(323,1,NULL),(324,1,NULL),(325,1,NULL),(326,1,NULL),(327,1,NULL),(328,1,NULL),(329,1,NULL),(330,1,NULL),(331,1,NULL),(332,1,NULL),(333,1,NULL),(334,1,NULL),(335,1,NULL),(336,1,NULL),(337,1,NULL),(338,1,NULL),(339,1,NULL),(340,1,NULL),(341,1,NULL),(342,1,NULL),(343,1,NULL),(344,1,NULL),(345,1,NULL),(346,1,NULL),(347,1,NULL),(348,1,NULL),(349,1,NULL),(350,1,NULL),(351,1,NULL),(352,1,NULL),(353,1,NULL),(354,1,NULL),(355,1,NULL),(356,1,NULL),(357,1,NULL),(358,1,NULL),(359,1,NULL),(360,1,NULL),(361,1,NULL),(362,1,NULL),(363,1,NULL),(364,1,NULL),(365,1,NULL),(366,1,NULL),(367,1,NULL),(368,1,NULL),(369,1,NULL),(370,1,NULL),(371,1,NULL),(372,1,NULL),(373,1,NULL),(374,1,NULL),(375,1,NULL),(376,1,NULL),(377,1,NULL),(378,1,NULL),(379,1,NULL),(380,1,NULL),(381,1,NULL),(382,1,NULL),(383,1,NULL),(384,1,NULL),(385,1,NULL),(386,1,NULL),(387,1,NULL),(388,1,NULL),(389,1,NULL),(390,1,NULL),(391,1,NULL),(392,1,NULL),(393,1,NULL),(394,1,NULL),(395,1,NULL),(396,1,NULL),(397,1,NULL),(398,1,NULL),(399,1,NULL),(400,1,NULL),(401,1,NULL),(402,1,NULL),(403,1,NULL),(404,1,NULL),(405,1,NULL),(406,1,NULL),(407,1,NULL),(408,1,NULL),(409,1,NULL),(410,1,NULL),(411,1,NULL),(412,1,NULL),(413,1,NULL),(414,1,NULL),(415,1,NULL),(416,1,NULL),(417,1,NULL),(418,1,NULL),(419,1,NULL),(420,1,NULL),(421,1,NULL),(422,1,NULL),(423,1,NULL),(424,1,NULL),(425,1,NULL),(426,1,NULL),(427,1,NULL),(428,1,NULL),(429,1,NULL),(430,1,NULL),(431,1,NULL),(432,1,NULL),(433,1,NULL),(434,1,NULL),(435,1,NULL),(436,1,NULL),(437,1,NULL),(438,1,NULL),(439,1,NULL),(440,1,NULL),(441,1,NULL),(442,1,NULL),(443,1,NULL),(444,1,NULL);
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
) ENGINE=InnoDB AUTO_INCREMENT=254 DEFAULT CHARSET=latin1;
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
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course`
--

LOCK TABLES `course` WRITE;
/*!40000 ALTER TABLE `course` DISABLE KEYS */;
INSERT INTO `course` VALUES (2,'Object Oriented Programming','CSC 221',1,1,NULL),(3,'Automata Theory','CSC 222',1,1,NULL),(4,'Operating Systems','CSC 223',1,1,NULL),(5,'Software Engineering','CSC 224',1,1,NULL),(6,'Computer Networks','CSC 225',1,1,NULL),(7,'Networking Lab','CSC 226',1,1,NULL),(8,'Programming Project','CSC 227',1,1,NULL),(9,'Introduction to Computer Systems','CSC 111',1,1,NULL),(10,'Introduction to Programming ','CSC 112',1,1,NULL),(11,'Discrete Maths','CSC 113',1,1,NULL),(12,'Differential and Integral Calculus','CSC 114',1,1,NULL),(13,'Communication and Learning  Skills','CCS 001',1,1,NULL),(14,'Elements of Economics','CCS 009',1,1,NULL);
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course_of_instance`
--

LOCK TABLES `course_of_instance` WRITE;
/*!40000 ALTER TABLE `course_of_instance` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course_of_session`
--

LOCK TABLES `course_of_session` WRITE;
/*!40000 ALTER TABLE `course_of_session` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `degree`
--

LOCK TABLES `degree` WRITE;
/*!40000 ALTER TABLE `degree` DISABLE KEYS */;
INSERT INTO `degree` VALUES (1,'BSc. Computer Science',1,8,NULL,1,NULL);
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
  `name` varchar(300) NOT NULL,
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
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `department`
--

LOCK TABLES `department` WRITE;
/*!40000 ALTER TABLE `department` DISABLE KEYS */;
INSERT INTO `department` VALUES (1,'Agricultural Economics','AE',1,179,1,NULL),(2,'Field Station','FS',1,180,1,NULL),(3,'Food Science, Nutrition and Technology','FSNT',1,181,1,NULL),(4,'Land Resource Management ','LRAMT',1,182,1,NULL),(5,'Plant Science and Crop Protection','PSCP',1,183,1,NULL),(6,'The Center for Sustainable Dryland Ecosystems and Societies','CSDSS',1,184,1,NULL),(7,'Kibwezi Field Station','KFS',1,185,1,NULL),(8,'Animal Production','AP',3,186,1,NULL),(9,'Clinical Studies','CS',3,187,1,NULL),(10,'Public Health Pharmacology ','PHPT',3,188,1,NULL),(11,'Vet Farm','VF',3,189,1,NULL),(12,'Vet Anatomy, Physiology and Tox','VAP',3,190,1,NULL),(13,'Veterinary Pathology, Microbiology  and Parasitology','VPMP',3,191,1,NULL),(14,'Chemistry Department','CD',6,198,1,NULL),(15,'Geology Department','GD',6,199,1,NULL),(16,'Meteorology','M',6,200,1,NULL),(17,'Physics ','P',6,201,1,NULL),(18,'Civil and Construction Engineering','CCE',11,206,1,NULL),(19,'Electrical and Information Engineering','EIE',11,207,1,NULL),(20,'Geospatial and Space Technology','GST',11,208,1,NULL),(21,'Mechanical and Manufacturing Engineering','MME',11,209,1,NULL),(22,'Environmental and Biosystems Engineering','EBE',11,210,1,NULL),(23,'Architecture and Building Science','ABS',13,211,1,NULL),(24,'Real Estate and Construction Management','RECM',13,212,1,NULL),(25,'Urban And Regional Planning','URP',13,213,1,NULL),(26,'Extra Mural Studies','EMS',16,218,1,NULL),(27,'Distance Studies','DS',16,219,1,NULL),(28,'Education Studies','ES',16,220,1,NULL),(29,'Education Administration And Planning','EAP',17,221,1,NULL),(30,'Education Communication and Technology','ECT',17,222,1,NULL),(31,'Educational Foundations','EF',17,223,1,NULL),(32,'Physical Education and Sports','PES',17,224,1,NULL),(33,'Conservative and Prosthetic Dentistry','CPD',19,233,1,NULL),(34,'Oral/Maxillofacial Surgery, Oral Medicine/Pathology, Oral/Maxillofacial Radiology','OMS/OMP/OMR',19,234,1,NULL),(35,'Paediatric/Dentistry and Orthodontics','PDO',19,235,1,NULL),(36,'Periodontology/Community and Preventive Dentistry','PCPD',19,236,1,NULL),(37,'Biochemistry','B',21,237,1,NULL),(38,'Clinical Medicine and Therapeutics','CMT',21,238,1,NULL),(39,'Diagnostic Imaging ','DIRM',21,239,1,NULL),(40,'Human Anatomy','HA',21,240,1,NULL),(41,'Human Pathology','HP',21,241,1,NULL),(42,'Medical Microbiology','MM',21,242,1,NULL),(43,'Medical Physiology','MP',21,243,1,NULL),(44,'Obstetrics ','OG',21,244,1,NULL),(45,'Opthalmology','O',21,245,1,NULL),(46,'Orthopaedic Surgery','OS',21,246,1,NULL),(47,'Paediatrics ','PCH',21,247,1,NULL),(48,'Surgery','S',21,248,1,NULL),(49,'Anaesthesia Department ','AD',21,249,1,NULL),(50,'Pharmaceutical Chemistry','PC',23,250,1,NULL),(51,'Pharmaceutics and Pharmacy Practice','PPP',23,251,1,NULL),(52,'Pharmacology and Pharmacognosy','PP',23,252,1,NULL),(53,'Commercial Law','CL',36,267,1,NULL),(54,'Private Law','PrL',36,268,1,NULL),(55,'Public Law','PL',36,269,1,NULL),(56,'Business Administration','BA',29,270,1,NULL),(57,'Finance and Accounting','FA',29,271,1,NULL),(58,'Management Science','MS',29,272,1,NULL),(59,'Geography and Environmental Studies','GES',28,273,1,NULL),(60,'Library and Information Science','LIS',28,274,1,NULL),(61,'History and Archaeological Studies','HAS',28,275,1,NULL),(62,'Literature','L',28,276,1,NULL),(63,'Linguistics ','LL',28,277,1,NULL),(64,'Arabic','A',28,278,1,NULL),(65,'Korean Centre','KC',28,279,1,NULL),(66,'Kiswahili','K',28,280,1,NULL),(67,'German Studies','GS',28,281,1,NULL),(68,'Confucius','C',28,282,1,NULL),(69,'Psychology','Psy',28,283,1,NULL),(70,'Philosophy and Religious Studies','PRS',28,284,1,NULL),(71,'Centre for Human Rights and Peace','CHRP',28,285,1,NULL),(72,'Sociology and Social Work','SSW',28,286,1,NULL),(73,'Political Science and Public Administration','PSPA',28,287,1,NULL),(74,'Communication Skills and Studies','CSS',28,288,1,NULL),(75,'French','F',28,289,1,NULL);
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
) ENGINE=InnoDB AUTO_INCREMENT=270 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `email_contact`
--

LOCK TABLES `email_contact` WRITE;
/*!40000 ALTER TABLE `email_contact` DISABLE KEYS */;
INSERT INTO `email_contact` VALUES (1,'fa.cavs@uonbi.ac.ke',176,1,NULL),(2,'wmipes.cavs@uonbi.ac.ke',177,1,NULL),(3,'vm.cavs@uonbi.ac.ke',178,1,NULL),(4,'ae.fa@uonbi.ac.ke',179,1,NULL),(5,'fs.fa@uonbi.ac.ke',180,1,NULL),(6,'fsnt.fa@uonbi.ac.ke',181,1,NULL),(7,'lramt.fa@uonbi.ac.ke',182,1,NULL),(8,'pscp.fa@uonbi.ac.ke',183,1,NULL),(9,'csdss.fa@uonbi.ac.ke',184,1,NULL),(10,'kfs.fa@uonbi.ac.ke',185,1,NULL),(11,'ap.vm@uonbi.ac.ke',186,1,NULL),(12,'cs.vm@uonbi.ac.ke',187,1,NULL),(13,'phpt.vm@uonbi.ac.ke',188,1,NULL),(14,'vf.vm@uonbi.ac.ke',189,1,NULL),(15,'vap.vm@uonbi.ac.ke',190,1,NULL),(16,'vpmp.vm@uonbi.ac.ke',191,1,NULL),(17,'cbb.cbps@uonbi.ac.ke',192,1,NULL),(18,'iccauon.cbps@uonbi.ac.ke',193,1,NULL),(19,'ps.cbps@uonbi.ac.ke',194,1,NULL),(20,'sbs.cbps@uonbi.ac.ke',195,1,NULL),(21,'sci.cbps@uonbi.ac.ke',196,1,NULL),(22,'sm.cbps@uonbi.ac.ke',197,1,NULL),(23,'cd.ps@uonbi.ac.ke',198,1,NULL),(24,'gd.ps@uonbi.ac.ke',199,1,NULL),(25,'m.ps@uonbi.ac.ke',200,1,NULL),(26,'p.ps@uonbi.ac.ke',201,1,NULL),(27,'ad.cae@uonbi.ac.ke',202,1,NULL),(28,'e.cae@uonbi.ac.ke',203,1,NULL),(29,'nst.cae@uonbi.ac.ke',204,1,NULL),(30,'be.cae@uonbi.ac.ke',205,1,NULL),(31,'cce.e@uonbi.ac.ke',206,1,NULL),(32,'eie.e@uonbi.ac.ke',207,1,NULL),(33,'gst.e@uonbi.ac.ke',208,1,NULL),(34,'mme.e@uonbi.ac.ke',209,1,NULL),(35,'ebe.e@uonbi.ac.ke',210,1,NULL),(36,'abs.be@uonbi.ac.ke',211,1,NULL),(37,'recm.be@uonbi.ac.ke',212,1,NULL),(38,'urp.be@uonbi.ac.ke',213,1,NULL),(39,'codl.cees@uonbi.ac.ke',214,1,NULL),(40,'ksc.cees@uonbi.ac.ke',215,1,NULL),(41,'scde.cees@uonbi.ac.ke',216,1,NULL),(42,'se.cees@uonbi.ac.ke',217,1,NULL),(43,'ems.scde@uonbi.ac.ke',218,1,NULL),(44,'ds.scde@uonbi.ac.ke',219,1,NULL),(45,'es.scde@uonbi.ac.ke',220,1,NULL),(46,'eap.se@uonbi.ac.ke',221,1,NULL),(47,'ect.se@uonbi.ac.ke',222,1,NULL),(48,'ef.se@uonbi.ac.ke',223,1,NULL),(49,'pes.se@uonbi.ac.ke',224,1,NULL),(50,'chpr.chs@uonbi.ac.ke',225,1,NULL),(51,'ds.chs@uonbi.ac.ke',226,1,NULL),(52,'itid.chs@uonbi.ac.ke',227,1,NULL),(53,'m.chs@uonbi.ac.ke',228,1,NULL),(54,'n.chs@uonbi.ac.ke',229,1,NULL),(55,'p.chs@uonbi.ac.ke',230,1,NULL),(56,'ph.chs@uonbi.ac.ke',231,1,NULL),(57,'chsl.chs@uonbi.ac.ke',232,1,NULL),(58,'cpd.ds@uonbi.ac.ke',233,1,NULL),(59,'oms/omp/omr.ds@uonbi.ac.ke',234,1,NULL),(60,'pdo.ds@uonbi.ac.ke',235,1,NULL),(61,'pcpd.ds@uonbi.ac.ke',236,1,NULL),(62,'b.m@uonbi.ac.ke',237,1,NULL),(63,'cmt.m@uonbi.ac.ke',238,1,NULL),(64,'dirm.m@uonbi.ac.ke',239,1,NULL),(65,'ha.m@uonbi.ac.ke',240,1,NULL),(66,'hp.m@uonbi.ac.ke',241,1,NULL),(67,'mm.m@uonbi.ac.ke',242,1,NULL),(68,'mp.m@uonbi.ac.ke',243,1,NULL),(69,'og.m@uonbi.ac.ke',244,1,NULL),(70,'o.m@uonbi.ac.ke',245,1,NULL),(71,'os.m@uonbi.ac.ke',246,1,NULL),(72,'pch.m@uonbi.ac.ke',247,1,NULL),(73,'s.m@uonbi.ac.ke',248,1,NULL),(74,'ad.m@uonbi.ac.ke',249,1,NULL),(75,'pc.p@uonbi.ac.ke',250,1,NULL),(76,'ppp.p@uonbi.ac.ke',251,1,NULL),(77,'pp.p@uonbi.ac.ke',252,1,NULL),(78,'awsc.chss@uonbi.ac.ke',253,1,NULL),(79,'as.chss@uonbi.ac.ke',254,1,NULL),(80,'a.chss@uonbi.ac.ke',255,1,NULL),(81,'b.chss@uonbi.ac.ke',256,1,NULL),(82,'caselap.chss@uonbi.ac.ke',257,1,NULL),(83,'dis.chss@uonbi.ac.ke',258,1,NULL),(85,'ids.chss@uonbi.ac.ke',260,1,NULL),(86,'j.chss@uonbi.ac.ke',261,1,NULL),(87,'kc.chss@uonbi.ac.ke',262,1,NULL),(88,'l.chss@uonbi.ac.ke',263,1,NULL),(89,'mc.chss@uonbi.ac.ke',264,1,NULL),(90,'psri.chss@uonbi.ac.ke',265,1,NULL),(91,'cti.chss@uonbi.ac.ke',266,1,NULL),(92,'cl.l@uonbi.ac.ke',267,1,NULL),(93,'prl.l@uonbi.ac.ke',268,1,NULL),(94,'pl.l@uonbi.ac.ke',269,1,NULL),(95,'ba.b@uonbi.ac.ke',270,1,NULL),(96,'fa.b@uonbi.ac.ke',271,1,NULL),(97,'ms.b@uonbi.ac.ke',272,1,NULL),(98,'ges.a@uonbi.ac.ke',273,1,NULL),(99,'lis.a@uonbi.ac.ke',274,1,NULL),(100,'has.a@uonbi.ac.ke',275,1,NULL),(101,'l.a@uonbi.ac.ke',276,1,NULL),(102,'ll.a@uonbi.ac.ke',277,1,NULL),(103,'a.a@uonbi.ac.ke',278,1,NULL),(104,'kc.a@uonbi.ac.ke',279,1,NULL),(105,'k.a@uonbi.ac.ke',280,1,NULL),(106,'gs.a@uonbi.ac.ke',281,1,NULL),(107,'c.a@uonbi.ac.ke',282,1,NULL),(108,'psy.a@uonbi.ac.ke',283,1,NULL),(109,'prs.a@uonbi.ac.ke',284,1,NULL),(110,'chrp.a@uonbi.ac.ke',285,1,NULL),(111,'ssw.a@uonbi.ac.ke',286,1,NULL),(112,'pspa.a@uonbi.ac.ke',287,1,NULL),(113,'css.a@uonbi.ac.ke',288,1,NULL),(114,'f.a@uonbi.ac.ke',289,1,NULL),(115,'abdallamohamed23@gmail.com',290,1,NULL),(116,'abdallanawaalkassim53@gmail.com',291,1,NULL),(117,'abdubaguyomohamed34@gmail.com',292,1,NULL),(118,'adanmohamednajib74@gmail.com',293,1,NULL),(119,'adembaaquinousrabongo05@gmail.com',294,1,NULL),(120,'aggreytevinlitunda35@gmail.com',295,1,NULL),(121,'aginelvis65@gmail.com',296,1,NULL),(122,'akivayakevinesendi06@gmail.com',297,1,NULL),(123,'ambanipaulsternmadegwa56@gmail.com',298,1,NULL),(124,'amokewycliffeochieng68@gmail.com',299,1,NULL),(125,'atienoadhiambofay98@gmail.com',300,1,NULL),(126,'barakachillah10@gmail.com',301,1,NULL),(127,'barminogichowabenedict51@gmail.com',302,1,NULL),(128,'baruaemusugutallan02@gmail.com',303,1,NULL),(129,'bettkiplimotonny32@gmail.com',304,1,NULL),(130,'bhundianavikjayant82@gmail.com',305,1,NULL),(131,'boitmichaelkipkosgei23@gmail.com',306,1,NULL),(132,'borusnorahchelagat83@gmail.com',307,1,NULL),(133,'catherinewaigwe14@gmail.com',308,1,NULL),(134,'chumbatrevorkiprop65@gmail.com',309,1,NULL),(135,'damjikodievans36@gmail.com',310,1,NULL),(136,'gacengahoseaciuti96@gmail.com',311,1,NULL),(137,'gachokamarvin70@gmail.com',312,1,NULL),(138,'gagidenisandrew48@gmail.com',313,1,NULL),(139,'gakengebensonmugo10@gmail.com',314,1,NULL),(140,'gichukievanswahome90@gmail.com',315,1,NULL),(141,'gitahijudyannwanjiku31@gmail.com',316,1,NULL),(142,'gitausamuelnjoroge91@gmail.com',317,1,NULL),(143,'ibrahimaliabdi32@gmail.com',318,1,NULL),(144,'ibrahimanasmohamed72@gmail.com',319,1,NULL),(145,'inindalaurencebugasu03@gmail.com',320,1,NULL),(146,'irungumartinmuriuki53@gmail.com',321,1,NULL),(147,'jacksonericknzuki04@gmail.com',322,1,NULL),(148,'jamawarsamemohamud71@gmail.com',323,1,NULL),(149,'jefwakelvinmukare82@gmail.com',324,1,NULL),(150,'jumaedgaronyango33@gmail.com',325,1,NULL),(151,'kahihukelvingitogo34@gmail.com',326,1,NULL),(152,'kahinkhalifmohamed74@gmail.com',327,1,NULL),(153,'kahuranipeterkibicha85@gmail.com',328,1,NULL),(154,'kajiruabubakarmohamed26@gmail.com',329,1,NULL),(155,'kamadieugenesambula76@gmail.com',330,1,NULL),(156,'kamamimoseswamae17@gmail.com',331,1,NULL),(157,'kamaubrianmukuhi62@gmail.com',332,1,NULL),(158,'karimivincentkarani50@gmail.com',333,1,NULL),(159,'kariukimarkmuigai02@gmail.com',334,1,NULL),(160,'kariukiwairimusalome62@gmail.com',335,1,NULL),(161,'kasyokivictor13@gmail.com',336,1,NULL),(162,'keringjoanjepkogei63@gmail.com',337,1,NULL),(163,'kibetmichaelbrian14@gmail.com',338,1,NULL),(164,'kingoriahmichaelmutuma54@gmail.com',339,1,NULL),(165,'kipkemeikevin74@gmail.com',340,1,NULL),(166,'kipkoriradrianabrahamk95@gmail.com',341,1,NULL),(167,'kirujaiangitonga26@gmail.com',342,1,NULL),(168,'kiutejackmwakai56@gmail.com',343,1,NULL),(169,'kuriamarkkaruku76@gmail.com',344,1,NULL),(170,'kuriamichaelwamathai07@gmail.com',345,1,NULL),(171,'kwambaichepkogeimercy27@gmail.com',346,1,NULL),(172,'kyalokelvinmuindi47@gmail.com',347,1,NULL),(173,'kyandemichaeljohn67@gmail.com',348,1,NULL),(174,'langatcalvinkiptoo87@gmail.com',349,1,NULL),(175,'langatdanielkipyegon18@gmail.com',350,1,NULL),(176,'langatvictorkiprono48@gmail.com',351,1,NULL),(177,'likonoian68@gmail.com',352,1,NULL),(178,'lucynjoki98@gmail.com',353,1,NULL),(179,'lupaowanjalaclive19@gmail.com',354,1,NULL),(180,'luttaelvisemmanuel39@gmail.com',355,1,NULL),(181,'machariasantananjoki69@gmail.com',356,1,NULL),(182,'machokatomfrank89@gmail.com',357,1,NULL),(183,'magetoallanbikundo20@gmail.com',358,1,NULL),(184,'mainakelvinmwangi11@gmail.com',359,1,NULL),(185,'makoribahatiagata41@gmail.com',360,1,NULL),(186,'maleyaclarkstinjumba12@gmail.com',361,1,NULL),(187,'malingaerickmuigei32@gmail.com',362,1,NULL),(188,'marosigregoryjosephokari62@gmail.com',363,1,NULL),(189,'mathusharonwanjiru82@gmail.com',364,1,NULL),(190,'mbakaabneroisebe13@gmail.com',365,1,NULL),(191,'mbarijohnwambugu43@gmail.com',366,1,NULL),(192,'mbuguajustusnjuru63@gmail.com',367,1,NULL),(193,'mbuguaphilipndungu93@gmail.com',368,1,NULL),(194,'mbuiteddykamau14@gmail.com',369,1,NULL),(195,'moseticaritonmaranga54@gmail.com',370,1,NULL),(196,'muchendubonifacemichuki74@gmail.com',371,1,NULL),(197,'muchirulewiskuria94@gmail.com',372,1,NULL),(198,'mugenyaemmanuelwilson75@gmail.com',373,1,NULL),(199,'muigailukekivunaga06@gmail.com',374,1,NULL),(200,'mulwaisaackiptoo36@gmail.com',375,1,NULL),(201,'mungacollinsgichuhi56@gmail.com',376,1,NULL),(202,'mungaiericmburu86@gmail.com',377,1,NULL),(203,'mungaitevinchege07@gmail.com',378,1,NULL),(204,'mungathiainnocentkithinji37@gmail.com',379,1,NULL),(205,'muriithiedwinkabui77@gmail.com',380,1,NULL),(206,'murayasammyndirangu08@gmail.com',381,1,NULL),(207,'murimimartin28@gmail.com',382,1,NULL),(208,'muriukitajirigitonga68@gmail.com',383,1,NULL),(209,'musyokarosianahwanza98@gmail.com',384,1,NULL),(210,'mutendederekprince19@gmail.com',385,1,NULL),(211,'mutindajaphethkioko49@gmail.com',386,1,NULL),(212,'mutua augustinenganga79@gmail.com',387,1,NULL),(213,'mutuasolomon99@gmail.com',388,1,NULL),(214,'mutukumaureenmumbua50@gmail.com',389,1,NULL),(215,'mwangibrianmacharia21@gmail.com',390,1,NULL),(216,'mwangifreudkariuki41@gmail.com',391,1,NULL),(217,'mwanikiedwinmbuthia71@gmail.com',392,1,NULL),(218,'mwanziadaudikasia91@gmail.com',393,1,NULL),(219,'mwanziasamuelnzyuko12@gmail.com',394,1,NULL),(220,'mwelesapauletteemali62@gmail.com',395,1,NULL),(221,'mwemaphenaedith23@gmail.com',396,1,NULL),(222,'mwenjestephen43@gmail.com',397,1,NULL),(223,'mwithalievincentmurithi63@gmail.com',398,1,NULL),(224,'ndambukilabankioko93@gmail.com',399,1,NULL),(225,'ndichustevekevin14@gmail.com',400,1,NULL),(226,'ndungudennisgichu44@gmail.com',401,1,NULL),(227,'ndwigalewismunyi54@gmail.com',402,1,NULL),(228,'ngugimichaelgichora74@gmail.com',403,1,NULL),(229,'ngatiasimonmuraguri15@gmail.com',404,1,NULL),(230,'njerusimonmugo45@gmail.com',405,1,NULL),(231,'njoguwinniewanjiru75@gmail.com',406,1,NULL),(232,'njokipeterkahenya95@gmail.com',407,1,NULL),(233,'nyagapetermwaniki16@gmail.com',408,1,NULL),(234,'nyagesoaabiudorina46@gmail.com',409,1,NULL),(235,'nzumajonathanndambuki86@gmail.com',410,1,NULL),(236,'ochiengfelixomondi37@gmail.com',411,1,NULL),(237,'ochomootienowilliam57@gmail.com',412,1,NULL),(238,'odhiambostephenochieng08@gmail.com',413,1,NULL),(239,'odongobrianrailaamolo58@gmail.com',414,1,NULL),(240,'odundostephenopiyo78@gmail.com',415,1,NULL),(241,'okellojobopiyo09@gmail.com',416,1,NULL),(242,'okeromichaelomondi29@gmail.com',417,1,NULL),(243,'olookevinomondi79@gmail.com',418,1,NULL),(244,'oludhepascaloduor10@gmail.com',419,1,NULL),(245,'oluochbilloduor80@gmail.com',420,1,NULL),(246,'omarmohamedabdi21@gmail.com',421,1,NULL),(247,'ombasomohamednyanamba51@gmail.com',422,1,NULL),(248,'onamujamesrodney71@gmail.com',423,1,NULL),(249,'onyangorobertmark02@gmail.com',424,1,NULL),(250,'onyinsitobiaswest22@gmail.com',425,1,NULL),(251,'otienoanitaajwang42@gmail.com',426,1,NULL),(252,'otienoerniembock82@gmail.com',427,1,NULL),(253,'oyiekeallenongado33@gmail.com',428,1,NULL),(254,'sakaligabrielirungu73@gmail.com',429,1,NULL),(255,'thaganakmark93@gmail.com',430,1,NULL),(256,'wahomedennislinus24@gmail.com',431,1,NULL),(257,'wamaejosephwanyoike44@gmail.com',432,1,NULL),(258,'wamuigakelvinkamau74@gmail.com',433,1,NULL),(259,'wanjohibrianmuturi94@gmail.com',434,1,NULL),(260,'wanjohiryangitonga15@gmail.com',435,1,NULL),(261,'wayualikomora45@gmail.com',436,1,NULL),(262,'yassinahmedfaiz65@gmail.com',437,1,NULL),(263,'yusufmoheenrashid95@gmail.com',438,1,NULL),(264,'zakariahusseinabdi16@gmail.com',439,1,NULL),(265,'drevansmiriti46@gmail.com',440,1,NULL),(266,'profomwengaomwenga76@gmail.com',441,1,NULL),(267,'drcorneliusabungu96@gmail.com',442,1,NULL),(268,'drmaryokebe37@gmail.com',443,1,NULL),(269,'drpetermuriu57@gmail.com',444,1,NULL);
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evaluated_question`
--

LOCK TABLES `evaluated_question` WRITE;
/*!40000 ALTER TABLE `evaluated_question` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evaluated_question_answer`
--

LOCK TABLES `evaluated_question_answer` WRITE;
/*!40000 ALTER TABLE `evaluated_question_answer` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evaluation_instance`
--

LOCK TABLES `evaluation_instance` WRITE;
/*!40000 ALTER TABLE `evaluation_instance` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `evaluation_session`
--

LOCK TABLES `evaluation_session` WRITE;
/*!40000 ALTER TABLE `evaluation_session` DISABLE KEYS */;
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
  `name` varchar(300) NOT NULL,
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
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `faculty`
--

LOCK TABLES `faculty` WRITE;
/*!40000 ALTER TABLE `faculty` DISABLE KEYS */;
INSERT INTO `faculty` VALUES (1,'Faculty of Agriculture ','FA',1,176,1,NULL),(2,'The Wangari Maathai Institute for Peace and Environmental Studies','WMIPES',1,177,1,NULL),(3,'Veterinary Medicine','VM',1,178,1,NULL),(4,'Centre For Biotechnology and Bioinformatics','CBB',2,192,1,NULL),(5,'Institute for Climate Change and Adaptation at the University of Nairobi','ICCAUON',2,193,1,NULL),(6,'Physical Sciences','PS',2,194,1,NULL),(7,'School of Biological Sciences','SBS',2,195,1,NULL),(8,'School of Computing and Informatics','SCI',2,196,1,NULL),(9,'School of Mathematics','SM',2,197,1,NULL),(10,'Arts and Design ','AD',3,202,1,NULL),(11,'Engineering','E',3,203,1,NULL),(12,'Nuclear Science and Technology','NST',3,204,1,NULL),(13,'The Built Environment','BE',3,205,1,NULL),(14,'Center of Open and Distance Learning','CODL',4,214,1,NULL),(15,'Kenya Science Campus','KSC',4,215,1,NULL),(16,'School of Continuing and Distance Education ','SCDE',4,216,1,NULL),(17,'School of Education','SE',4,217,1,NULL),(18,'Centre for Hiv Prevention And Research','CHPR',5,225,1,NULL),(19,'Dental Sciences','DS',5,226,1,NULL),(20,'Institute of Tropical and Infectious Diseases','ITID',5,227,1,NULL),(21,'Medicine','M',5,228,1,NULL),(22,'Nursing','N',5,229,1,NULL),(23,'Pharmacy','P',5,230,1,NULL),(24,'Public Health','PH',5,231,1,NULL),(25,'College of Health Sciences Library','CHSL',5,232,1,NULL),(26,'African Women\'s Studies Centre','AWSC',6,253,1,NULL),(27,'African Studies','AS',6,254,1,NULL),(28,'Arts','A',6,255,1,NULL),(29,'Business','B',6,256,1,NULL),(30,'Centre for Advanced Studies in Environmental Law and Policy ','CASELAP',6,257,1,NULL),(31,'Diplomacy and International Studies','DIS',6,258,1,NULL),(33,'Institute For Development Studies','IDS',6,260,1,NULL),(34,'Journalism','J',6,261,1,NULL),(35,'Kisumu Campus','KC',6,262,1,NULL),(36,'Law','L',6,263,1,NULL),(37,'Mombasa Campus','MC',6,264,1,NULL),(38,'Population Studies and Research Institute','PSRI',6,265,1,NULL),(39,'Centre for Translation and Interpretation','CTI',6,266,1,NULL);
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
) ENGINE=InnoDB AUTO_INCREMENT=156 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `faculty_member`
--

LOCK TABLES `faculty_member` WRITE;
/*!40000 ALTER TABLE `faculty_member` DISABLE KEYS */;
INSERT INTO `faculty_member` VALUES (1,1,8,NULL,'2015-09-01',3,1,NULL),(2,2,8,NULL,'2015-09-01',3,1,NULL),(3,3,8,NULL,'2015-09-01',3,1,NULL),(4,4,8,NULL,'2015-09-01',3,1,NULL),(5,5,8,NULL,'2015-09-01',3,1,NULL),(6,6,8,NULL,'2015-09-01',3,1,NULL),(7,7,8,NULL,'2015-09-01',3,1,NULL),(8,8,8,NULL,'2015-09-01',3,1,NULL),(9,9,8,NULL,'2015-09-01',3,1,NULL),(10,10,8,NULL,'2015-09-01',3,1,NULL),(11,11,8,NULL,'2015-09-01',3,1,NULL),(12,12,8,NULL,'2015-09-01',3,1,NULL),(13,13,8,NULL,'2015-09-01',3,1,NULL),(14,14,8,NULL,'2015-09-01',3,1,NULL),(15,15,8,NULL,'2015-09-01',3,1,NULL),(16,16,8,NULL,'2015-09-01',3,1,NULL),(17,17,8,NULL,'2015-09-01',3,1,NULL),(18,18,8,NULL,'2015-09-01',3,1,NULL),(19,19,8,NULL,'2015-09-01',3,1,NULL),(20,20,8,NULL,'2015-09-01',3,1,NULL),(21,21,8,NULL,'2015-09-01',3,1,NULL),(22,22,8,NULL,'2015-09-01',3,1,NULL),(23,23,8,NULL,'2015-09-01',3,1,NULL),(24,24,8,NULL,'2015-09-01',3,1,NULL),(25,25,8,NULL,'2015-09-01',3,1,NULL),(26,26,8,NULL,'2015-09-01',3,1,NULL),(27,27,8,NULL,'2015-09-01',3,1,NULL),(28,28,8,NULL,'2015-09-01',3,1,NULL),(29,29,8,NULL,'2015-09-01',3,1,NULL),(30,30,8,NULL,'2015-09-01',3,1,NULL),(31,31,8,NULL,'2015-09-01',3,1,NULL),(32,32,8,NULL,'2015-09-01',3,1,NULL),(33,33,8,NULL,'2015-09-01',3,1,NULL),(34,34,8,NULL,'2015-09-01',3,1,NULL),(35,35,8,NULL,'2015-09-01',3,1,NULL),(36,36,8,NULL,'2015-09-01',3,1,NULL),(37,37,8,NULL,'2015-09-01',3,1,NULL),(38,38,8,NULL,'2015-09-01',3,1,NULL),(39,39,8,NULL,'2015-09-01',3,1,NULL),(40,40,8,NULL,'2015-09-01',3,1,NULL),(41,41,8,NULL,'2015-09-01',3,1,NULL),(42,42,8,NULL,'2015-09-01',3,1,NULL),(43,43,8,NULL,'2015-09-01',3,1,NULL),(44,44,8,NULL,'2015-09-01',3,1,NULL),(45,45,8,NULL,'2015-09-01',3,1,NULL),(46,46,8,NULL,'2015-09-01',3,1,NULL),(47,47,8,NULL,'2015-09-01',3,1,NULL),(48,48,8,NULL,'2015-09-01',3,1,NULL),(49,49,8,NULL,'2015-09-01',3,1,NULL),(50,50,8,NULL,'2015-09-01',3,1,NULL),(51,51,8,NULL,'2015-09-01',3,1,NULL),(52,52,8,NULL,'2015-09-01',3,1,NULL),(53,53,8,NULL,'2015-09-01',3,1,NULL),(54,54,8,NULL,'2015-09-01',3,1,NULL),(55,55,8,NULL,'2015-09-01',3,1,NULL),(56,56,8,NULL,'2015-09-01',3,1,NULL),(57,57,8,NULL,'2015-09-01',3,1,NULL),(58,58,8,NULL,'2015-09-01',3,1,NULL),(59,59,8,NULL,'2015-09-01',3,1,NULL),(60,60,8,NULL,'2015-09-01',3,1,NULL),(61,61,8,NULL,'2015-09-01',3,1,NULL),(62,62,8,NULL,'2015-09-01',3,1,NULL),(63,63,8,NULL,'2015-09-01',3,1,NULL),(64,64,8,NULL,'2015-09-01',3,1,NULL),(65,65,8,NULL,'2015-09-01',3,1,NULL),(66,66,8,NULL,'2015-09-01',3,1,NULL),(67,67,8,NULL,'2015-09-01',3,1,NULL),(68,68,8,NULL,'2015-09-01',3,1,NULL),(69,69,8,NULL,'2015-09-01',3,1,NULL),(70,70,8,NULL,'2015-09-01',3,1,NULL),(71,71,8,NULL,'2015-09-01',3,1,NULL),(72,72,8,NULL,'2015-09-01',3,1,NULL),(73,73,8,NULL,'2015-09-01',3,1,NULL),(74,74,8,NULL,'2015-09-01',3,1,NULL),(75,75,8,NULL,'2015-09-01',3,1,NULL),(76,76,8,NULL,'2015-09-01',3,1,NULL),(77,77,8,NULL,'2015-09-01',3,1,NULL),(78,78,8,NULL,'2015-09-01',3,1,NULL),(79,79,8,NULL,'2015-09-01',3,1,NULL),(80,80,8,NULL,'2015-09-01',3,1,NULL),(81,81,8,NULL,'2015-09-01',3,1,NULL),(82,82,8,NULL,'2015-09-01',3,1,NULL),(83,83,8,NULL,'2015-09-01',3,1,NULL),(84,84,8,NULL,'2015-09-01',3,1,NULL),(85,85,8,NULL,'2015-09-01',3,1,NULL),(86,86,8,NULL,'2015-09-01',3,1,NULL),(87,87,8,NULL,'2015-09-01',3,1,NULL),(88,88,8,NULL,'2015-09-01',3,1,NULL),(89,89,8,NULL,'2015-09-01',3,1,NULL),(90,90,8,NULL,'2015-09-01',3,1,NULL),(91,91,8,NULL,'2015-09-01',3,1,NULL),(92,92,8,NULL,'2015-09-01',3,1,NULL),(93,93,8,NULL,'2015-09-01',3,1,NULL),(94,94,8,NULL,'2015-09-01',3,1,NULL),(95,95,8,NULL,'2015-09-01',3,1,NULL),(96,96,8,NULL,'2015-09-01',3,1,NULL),(97,97,8,NULL,'2015-09-01',3,1,NULL),(98,98,8,NULL,'2015-09-01',3,1,NULL),(99,99,8,NULL,'2015-09-01',3,1,NULL),(100,100,8,NULL,'2015-09-01',3,1,NULL),(101,101,8,NULL,'2015-09-01',3,1,NULL),(102,102,8,NULL,'2015-09-01',3,1,NULL),(103,103,8,NULL,'2015-09-01',3,1,NULL),(104,104,8,NULL,'2015-09-01',3,1,NULL),(105,105,8,NULL,'2015-09-01',3,1,NULL),(106,106,8,NULL,'2015-09-01',3,1,NULL),(107,107,8,NULL,'2015-09-01',3,1,NULL),(108,108,8,NULL,'2015-09-01',3,1,NULL),(109,109,8,NULL,'2015-09-01',3,1,NULL),(110,110,8,NULL,'2015-09-01',3,1,NULL),(111,111,8,NULL,'2015-09-01',3,1,NULL),(112,112,8,NULL,'2015-09-01',3,1,NULL),(113,113,8,NULL,'2015-09-01',3,1,NULL),(114,114,8,NULL,'2015-09-01',3,1,NULL),(115,115,8,NULL,'2015-09-01',3,1,NULL),(116,116,8,NULL,'2015-09-01',3,1,NULL),(117,117,8,NULL,'2015-09-01',3,1,NULL),(118,118,8,NULL,'2015-09-01',3,1,NULL),(119,119,8,NULL,'2015-09-01',3,1,NULL),(120,120,8,NULL,'2015-09-01',3,1,NULL),(121,121,8,NULL,'2015-09-01',3,1,NULL),(122,122,8,NULL,'2015-09-01',3,1,NULL),(123,123,8,NULL,'2015-09-01',3,1,NULL),(124,124,8,NULL,'2015-09-01',3,1,NULL),(125,125,8,NULL,'2015-09-01',3,1,NULL),(126,126,8,NULL,'2015-09-01',3,1,NULL),(127,127,8,NULL,'2015-09-01',3,1,NULL),(128,128,8,NULL,'2015-09-01',3,1,NULL),(129,129,8,NULL,'2015-09-01',3,1,NULL),(130,130,8,NULL,'2015-09-01',3,1,NULL),(131,131,8,NULL,'2015-09-01',3,1,NULL),(132,132,8,NULL,'2015-09-01',3,1,NULL),(133,133,8,NULL,'2015-09-01',3,1,NULL),(134,134,8,NULL,'2015-09-01',3,1,NULL),(135,135,8,NULL,'2015-09-01',3,1,NULL),(136,136,8,NULL,'2015-09-01',3,1,NULL),(137,137,8,NULL,'2015-09-01',3,1,NULL),(138,138,8,NULL,'2015-09-01',3,1,NULL),(139,139,8,NULL,'2015-09-01',3,1,NULL),(140,140,8,NULL,'2015-09-01',3,1,NULL),(141,141,8,NULL,'2015-09-01',3,1,NULL),(142,142,8,NULL,'2015-09-01',3,1,NULL),(143,143,8,NULL,'2015-09-01',3,1,NULL),(144,144,8,NULL,'2015-09-01',3,1,NULL),(145,145,8,NULL,'2015-09-01',3,1,NULL),(146,146,8,NULL,'2015-09-01',3,1,NULL),(147,147,8,NULL,'2015-09-01',3,1,NULL),(148,148,8,NULL,'2015-09-01',3,1,NULL),(149,149,8,NULL,'2015-09-01',3,1,NULL),(150,150,8,NULL,'2015-09-01',3,1,NULL),(151,151,8,NULL,'2015-09-01',3,1,NULL),(152,152,8,NULL,'2015-09-01',3,1,NULL),(153,153,8,NULL,'2015-09-01',3,1,NULL),(154,154,8,NULL,'2015-09-01',3,1,NULL),(155,155,8,NULL,'2015-09-01',3,1,NULL);
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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `overall_admin`
--

LOCK TABLES `overall_admin` WRITE;
/*!40000 ALTER TABLE `overall_admin` DISABLE KEYS */;
INSERT INTO `overall_admin` VALUES (1,'admin','8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918',1,NULL);
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
) ENGINE=InnoDB AUTO_INCREMENT=156 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `person`
--

LOCK TABLES `person` WRITE;
/*!40000 ALTER TABLE `person` DISABLE KEYS */;
INSERT INTO `person` VALUES (1,'ABDALLA','MOHAMED',290,'P15/36965/2016','P15/36965/2016',1,NULL),(2,'ABDALLA','NAWAAL KASSIM',291,'P15/1703/2016','P15/1703/2016',1,NULL),(3,'ABDUBA','GUYO MOHAMED',292,'P15/1699/2016','P15/1699/2016',1,NULL),(4,'ADAN','MOHAMED NAJIB',293,'P15/34475/2014','P15/34475/2014',1,NULL),(5,'ADEMBA','AQUINOUS RABONGO',294,'P15/2753/2016','P15/2753/2016',1,NULL),(6,'AGGREY','TEVIN  LITUNDA',295,'P15/36478/2016','P15/36478/2016',1,NULL),(7,'AGIN','ELVIS',296,'P15/1726/2016','P15/1726/2016',1,NULL),(8,'AKIVAYA','KEVIN ESENDI',297,'P15/36780/2016','P15/36780/2016',1,NULL),(9,'AMBANI','PAULSTERN MADEGWA',298,'P15/1721/2016','P15/1721/2016',1,NULL),(10,'AMOKE','WYCLIFFE OCHIENG\'',299,'P15/1728/2016','P15/1728/2016',1,NULL),(11,'ATIENO','ADHIAMBO FAY',300,'P15/4630/2015','P15/4630/2015',1,NULL),(12,'BARAK','ACHILLAH',301,'P15/1709/2016','P15/1709/2016',1,NULL),(13,'BARMIN','OGICHOWA BENEDICT',302,'P15/31737/2015','P15/31737/2015',1,NULL),(14,'BARUA','EMUSUGUT ALLAN',303,'P15/1719/2016','P15/1719/2016',1,NULL),(15,'BETT','KIPLIMO TONNY',304,'P15/1716/2016','P15/1716/2016',1,NULL),(16,'BHUNDIA','NAVIK JAYANT',305,'P15/33494/2015','P15/33494/2015',1,NULL),(17,'BOIT','MICHAEL KIPKOSGEI',306,'P15/37529/2016','P15/37529/2016',1,NULL),(18,'BORUS','NORAH CHELAGAT',307,'P15/2559/2015','P15/2559/2015',1,NULL),(19,'CATHERINE','WAIGWE',308,'P15/1689/2016','P15/1689/2016',1,NULL),(20,'CHUMBA','TREVOR  KIPROP',309,'P15/36376/2016','P15/36376/2016',1,NULL),(21,'DAMJI','KODI EVANS',310,'P15/35947/2015','P15/35947/2015',1,NULL),(22,'GACENGA','HOSEA CIUTI',311,'P15/1706/2016','P15/1706/2016',1,NULL),(23,'GACHOKA','MARVIN',312,'P15/32156/2015','P15/32156/2015',1,NULL),(24,'GAGI','DENIS ANDREW',313,'P15/1713/2016','P15/1713/2016',1,NULL),(25,'GAKENGE','BENSON MUGO',314,'P15/36158/2015','P15/36158/2015',1,NULL),(26,'GICHUKI','EVANS WAHOME',315,'P15/36969/2016','P15/36969/2016',1,NULL),(27,'GITAHI','JUDY ANN WANJIKU',316,'P15/1698/2016','P15/1698/2016',1,NULL),(28,'GITAU','SAMUEL NJOROGE',317,'P15/1696/2016','P15/1696/2016',1,NULL),(29,'IBRAHIM','ALI  ABDI',318,'P15/37272/2016','P15/37272/2016',1,NULL),(30,'IBRAHIM','ANAS  MOHAMED',319,'P15/36268/2016','P15/36268/2016',1,NULL),(31,'ININDA','LAURENCE BUGASU',320,'P15/1708/2016','P15/1708/2016',1,NULL),(32,'IRUNGU','MARTIN MURIUKI',321,'P15/36341/2015','P15/36341/2015',1,NULL),(33,'JACKSON','ERICK NZUKI',322,'P15/37046/2016','P15/37046/2016',1,NULL),(34,'JAMA','WARSAME MOHAMUD',323,'P15/37269/2016','P15/37269/2016',1,NULL),(35,'JEFWA','KELVIN MUKARE',324,'P15/37344/2016','P15/37344/2016',1,NULL),(36,'JUMA','EDGAR ONYANGO',325,'P15/36117/2015','P15/36117/2015',1,NULL),(37,'KAHIHU','KELVIN GITOGO',326,'P15/30653/2015','P15/30653/2015',1,NULL),(38,'KAHIN','KHALIF MOHAMED',327,'P15/37254/2016','P15/37254/2016',1,NULL),(39,'KAHURANI','PETER KIBICHA',328,'P15/36102/2015','P15/36102/2015',1,NULL),(40,'KAJIRU','ABUBAKAR MOHAMED',329,'P15/37738/2016','P15/37738/2016',1,NULL),(41,'KAMADI','EUGENE SAMBULA',330,'P15/31916/2015','P15/31916/2015',1,NULL),(42,'KAMAMI','MOSES WAMAE',331,'P15/37641/2016','P15/37641/2016',1,NULL),(43,'KAMAU','BRIAN MUKUHI',332,'P15/36237/2016','P15/36237/2016',1,NULL),(44,'KARIMI','VINCENT KARANI',333,'P15/36507/2016','P15/36507/2016',1,NULL),(45,'KARIUKI','MARK MUIGAI',334,'P15/35753/2015','P15/35753/2015',1,NULL),(46,'KARIUKI','WAIRIMU SALOME',335,'P15/1690/2016','P15/1690/2016',1,NULL),(47,'KASYOKI','VICTOR',336,'P15/37231/2016','P15/37231/2016',1,NULL),(48,'KERING','JOAN JEPKOGEI',337,'P15/36897/2016','P15/36897/2016',1,NULL),(49,'KIBET','MICHAEL BRIAN',338,'P15/36901/2016','P15/36901/2016',1,NULL),(50,'KING\'ORIAH','MICHAEL MUTUMA',339,'P15/35462/2015','P15/35462/2015',1,NULL),(51,'KIPKEMEI','KEVIN',340,'P15/1711/2016','P15/1711/2016',1,NULL),(52,'KIPKORIR','ADRIAN ABRAHAM K',341,'P15/1715/2016','P15/1715/2016',1,NULL),(53,'KIRUJA','IAN GITONGA',342,'P15/36157/2015','P15/36157/2015',1,NULL),(54,'KIUTE','JACK MWAKAI',343,'P15/36569/2016','P15/36569/2016',1,NULL),(55,'KURIA','MARK KARUKU',344,'P15/37364/2016','P15/37364/2016',1,NULL),(56,'KURIA','MICHAEL WAMATHAI',345,'P15/34677/2014','P15/34677/2014',1,NULL),(57,'KWAMBAI','CHEPKOGEI MERCY',346,'P15/1718/2016','P15/1718/2016',1,NULL),(58,'KYALO','KELVIN MUINDI',347,'P15/37561/2016','P15/37561/2016',1,NULL),(59,'KYANDE','MICHAEL JOHN',348,'P15/34906/2014','P15/34906/2014',1,NULL),(60,'LANGAT','CALVIN KIPTOO',349,'P15/36089/2015','P15/36089/2015',1,NULL),(61,'LANGAT','DANIEL KIPYEGON',350,'P15/1714/2016','P15/1714/2016',1,NULL),(62,'LANGAT','VICTOR KIPRONO',351,'P15/1841/2016','P15/1841/2016',1,NULL),(63,'LIKONO','IAN',352,'P15/1717/2016','P15/1717/2016',1,NULL),(64,'LUCY','NJOKI',353,'P15/1694/2016','P15/1694/2016',1,NULL),(65,'LUPAO','WANJALA CLIVE',354,'P15/1701/2016','P15/1701/2016',1,NULL),(66,'LUTTA','ELVIS EMMANUEL',355,'P15/35805/2013','P15/35805/2013',1,NULL),(67,'MACHARIA','SANTANA NJOKI',356,'P15/1693/2016','P15/1693/2016',1,NULL),(68,'MACHOKA','TOM FRANK',357,'P15/31178/2015','P15/31178/2015',1,NULL),(69,'MAGETO','ALLAN BIKUNDO',358,'P15/35160/2014','P15/35160/2014',1,NULL),(70,'MAINA','KELVIN MWANGI',359,'P15/36503/2016','P15/36503/2016',1,NULL),(71,'MAKORI','BAHATI AGATA',360,'P15/1702/2016','P15/1702/2016',1,NULL),(72,'MALEYA','CLARKSTIN JUMBA',361,'P15/36168/2015','P15/36168/2015',1,NULL),(73,'MALING\'A','ERICK MUIGEI',362,'P15/36132/2015','P15/36132/2015',1,NULL),(74,'MAROSI','GREGORY JOSEPH OKARI',363,'P15/35834/2015','P15/35834/2015',1,NULL),(75,'MATHU','SHARON WANJIRU',364,'P15/36812/2016','P15/36812/2016',1,NULL),(76,'MBAKA','ABNER OISEBE',365,'P15/30459/2015','P15/30459/2015',1,NULL),(77,'MBARI','JOHN WAMBUGU',366,'P15/35101/2015','P15/35101/2015',1,NULL),(78,'MBUGUA','JUSTUS NJURU',367,'P15/33977/2014','P15/33977/2014',1,NULL),(79,'MBUGUA','PHILIP NDUNGU',368,'P15/37874/2016','P15/37874/2016',1,NULL),(80,'MBUI','TEDDY KAMAU',369,'P15/36234/2016','P15/36234/2016',1,NULL),(81,'MOSETI','CARITON MARANGA',370,'P15/36821/2016','P15/36821/2016',1,NULL),(82,'MUCHENDU','BONIFACE MICHUKI',371,'P15/35893/2015','P15/35893/2015',1,NULL),(83,'MUCHIRU','LEWIS KURIA',372,'P15/35097/2015','P15/35097/2015',1,NULL),(84,'MUGENYA','EMMANUEL WILSON',373,'P15/37139/2016','P15/37139/2016',1,NULL),(85,'MUIGAI','LUKE KIVUNAGA',374,'P15/1697/2016','P15/1697/2016',1,NULL),(86,'MULWA','ISAAC KIPTOO',375,'P15/37217/2016','P15/37217/2016',1,NULL),(87,'MUNGA','COLLINS GICHUHI',376,'P15/37957/2016','P15/37957/2016',1,NULL),(88,'MUNGAI','ERIC MBURU',377,'P15/1695/2016','P15/1695/2016',1,NULL),(89,'MUNGAI','TEVIN CHEGE',378,'P15/35759/2015','P15/35759/2015',1,NULL),(90,'MUNGATHIA','INNOCENT KITHINJI',379,'P15/36496/2016','P15/36496/2016',1,NULL),(91,'MURIITHI','EDWIN KABUI',380,'P15/36653/2016','P15/36653/2016',1,NULL),(92,'MURAYA','SAMMY NDIRANGU',381,'P15/30324/2015','P15/30324/2015',1,NULL),(93,'MURIMI','MARTIN',382,'P15/1433/2016','P15/1433/2016',1,NULL),(94,'MURIUKI','TAJIRI GITONGA',383,'P15/37017/2016','P15/37017/2016',1,NULL),(95,'MUSYOKA','ROSIANAH WANZA',384,'P15/38293/2016','P15/38293/2016',1,NULL),(96,'MUTENDE','DEREK PRINCE',385,'P15/1724/2016','P15/1724/2016',1,NULL),(97,'MUTINDA','JAPHETH  KIOKO',386,'P15/34665/2014','P15/34665/2014',1,NULL),(98,'MUTUA',' AUGUSTINE NGANGA',387,'P15/30363/2015','P15/30363/2015',1,NULL),(99,'MUTUA','SOLOMON',388,'P15/37821/2016','P15/37821/2016',1,NULL),(100,'MUTUKU','MAUREEN MUMBUA',389,'P15/1710/2016','P15/1710/2016',1,NULL),(101,'MWANGI','BRIAN MACHARIA',390,'P15/37010/2016','P15/37010/2016',1,NULL),(102,'MWANGI','FREUD KARIUKI',391,'P15/1692/2016','P15/1692/2016',1,NULL),(103,'MWANIKI','EDWIN MBUTHIA',392,'P15/1705/2016','P15/1705/2016',1,NULL),(104,'MWANZIA','DAUDI KASIA',393,'P15/38236/2016','P15/38236/2016',1,NULL),(105,'MWANZIA','SAMUEL NZYUKO',394,'P15/36533/2016','P15/36533/2016',1,NULL),(106,'MWELESA','PAULETTE EMALI',395,'P15/36513/2016','P15/36513/2016',1,NULL),(107,'MWEMA','PHENA  EDITH',396,'P15/37125/2016','P15/37125/2016',1,NULL),(108,'MWENJE','STEPHEN',397,'P15/1722/2016','P15/1722/2016',1,NULL),(109,'MWITHALIE','VINCENT MURITHI',398,'P15/1723/2016','P15/1723/2016',1,NULL),(110,'NDAMBUKI','LABAN KIOKO',399,'P15/37019/2016','P15/37019/2016',1,NULL),(111,'NDICHU','STEVE KEVIN',400,'P15/36238/2016','P15/36238/2016',1,NULL),(112,'NDUNGU','DENNIS GICHU',401,'P15/36648/2016','P15/36648/2016',1,NULL),(113,'NDWIGA','LEWIS MUNYI',402,'P15/36076/2015','P15/36076/2015',1,NULL),(114,'NGUGI','MICHAEL GICHORA',403,'P15/36134/2015','P15/36134/2015',1,NULL),(115,'NGATIA','SIMON MURAGURI',404,'P15/1688/2016','P15/1688/2016',1,NULL),(116,'NJERU','SIMON MUGO',405,'P15/31143/2015','P15/31143/2015',1,NULL),(117,'NJOGU','WINNIE WANJIRU',406,'P15/35037/2014','P15/35037/2014',1,NULL),(118,'NJOKI','PETER KAHENYA',407,'P15/1712/2016','P15/1712/2016',1,NULL),(119,'NYAGA','PETER MWANIKI',408,'P15/1700/2016','P15/1700/2016',1,NULL),(120,'NYAGESOA','ABIUD ORINA',409,'P15/1725/2016','P15/1725/2016',1,NULL),(121,'NZUMA','JONATHAN NDAMBUKI',410,'P15/36756/2016','P15/36756/2016',1,NULL),(122,'OCHIENG','FELIX OMONDI',411,'P15/37073/2016','P15/37073/2016',1,NULL),(123,'OCHOMO','OTIENO WILLIAM',412,'P15/1720/2016','P15/1720/2016',1,NULL),(124,'ODHIAMBO','STEPHEN OCHIENG',413,'P15/36874/2016','P15/36874/2016',1,NULL),(125,'ODONGO','BRIAN RAILA AMOLO',414,'P15/1727/2016','P15/1727/2016',1,NULL),(126,'ODUNDO','STEPHEN OPIYO',415,'P15/34017/2015','P15/34017/2015',1,NULL),(127,'OKELLO','JOB OPIYO',416,'P15/37353/2016','P15/37353/2016',1,NULL),(128,'OKERO','MICHAEL OMONDI',417,'P15/30269/2015','P15/30269/2015',1,NULL),(129,'OLOO','KEVIN OMONDI',418,'P15/35324/2015','P15/35324/2015',1,NULL),(130,'OLUDHE','PASCAL ODUOR',419,'P15/35255/2015','P15/35255/2015',1,NULL),(131,'OLUOCH','BILL ODUOR',420,'P15/38119/2016','P15/38119/2016',1,NULL),(132,'OMAR','MOHAMED ABDI',421,'P15/36337/2016','P15/36337/2016',1,NULL),(133,'OMBASO','MOHAMED NYANAMBA',422,'P15/35291/2015','P15/35291/2015',1,NULL),(134,'ONAMU','JAMES RODNEY',423,'P15/35945/2015','P15/35945/2015',1,NULL),(135,'ONYANGO','ROBERT MARK',424,'P15/1729/2016','P15/1729/2016',1,NULL),(136,'ONYINSI','TOBIAS WEST',425,'P15/37451/2016','P15/37451/2016',1,NULL),(137,'OTIENO','ANITA AJWANG',426,'P15/36551/2016','P15/36551/2016',1,NULL),(138,'OTIENO','ERNIE MBOCK',427,'P15/35280/2015','P15/35280/2015',1,NULL),(139,'OYIEKE','ALLEN ONGADO',428,'P15/36567/2016','P15/36567/2016',1,NULL),(140,'SAKALI','GABRIEL IRUNGU',429,'P15/36349/2016','P15/36349/2016',1,NULL),(141,'THAGANA','K MARK',430,'P15/1691/2016','P15/1691/2016',1,NULL),(142,'WAHOME','DENNIS LINUS',431,'P15/38166/2016','P15/38166/2016',1,NULL),(143,'WAMAE','JOSEPH WANYOIKE',432,'P15/37390/2016','P15/37390/2016',1,NULL),(144,'WAMUIGA','KELVIN KAMAU',433,'P15/35060/2015','P15/35060/2015',1,NULL),(145,'WANJOHI','BRIAN MUTURI',434,'P15/37020/2016','P15/37020/2016',1,NULL),(146,'WANJOHI','RYAN GITONGA',435,'P15/1704/2016','P15/1704/2016',1,NULL),(147,'WAYU','ALI  KOMORA',436,'P15/35954/2015','P15/35954/2015',1,NULL),(148,'YASSIN','AHMED FAIZ',437,'P15/1707/2016','P15/1707/2016',1,NULL),(149,'YUSUF','MOHEEN  RASHID',438,'P15/36820/2016','P15/36820/2016',1,NULL),(150,'ZAKARIA','HUSSEIN ABDI',439,'P15/37847/2016','P15/37847/2016',1,NULL),(151,'DR EVANS','MIRITI',440,'CSC111Miriti','CSC111Miriti',1,NULL),(152,'PROF OMWENGA','OMWENGA',441,'CSC112Omwenga','CSC112Omwenga',1,NULL),(153,'DR CORNELIUS','ABUNGU',442,'CSC113Abungu','CSC113Abungu',1,NULL),(154,'DR MARY','OKEBE',443,'CCS001Mary','CCS001Mary',1,NULL),(155,'DR PETER','MURIU',444,'CSC009Muriu','CSC009Muriu',1,NULL);
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
) ENGINE=InnoDB AUTO_INCREMENT=270 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phone_contact`
--

LOCK TABLES `phone_contact` WRITE;
/*!40000 ALTER TABLE `phone_contact` DISABLE KEYS */;
INSERT INTO `phone_contact` VALUES (1,'+254-73820668602','',176,1,NULL),(2,'+254-7760590250','',177,1,NULL),(3,'+254-7620323917','',178,1,NULL),(4,'+254-75270876684','',179,1,NULL),(5,'+254-77620595156','',180,1,NULL),(6,'+254-79670687892','',181,1,NULL),(7,'+254-74720378999','',182,1,NULL),(8,'+254-79110381124','',183,1,NULL),(9,'+254-7218067951','',184,1,NULL),(10,'+254-75820853998','',185,1,NULL),(11,'+254-7400909784','',186,1,NULL),(12,'+254-761019693','',187,1,NULL),(13,'+254-7680735243','',188,1,NULL),(14,'+254-78360120662','',189,1,NULL),(15,'+254-7402080151','',190,1,NULL),(16,'+254-76640917293','',191,1,NULL),(17,'+254-78480359524','',192,1,NULL),(18,'+254-72770790421','',193,1,NULL),(19,'+254-7782096783','',194,1,NULL),(20,'+254-76490827121','',195,1,NULL),(21,'+254-79390688674','',196,1,NULL),(22,'+254-76240379360','',197,1,NULL),(23,'+254-77270609185','',198,1,NULL),(24,'+254-74750588981','',199,1,NULL),(25,'+254-72370801981','',200,1,NULL),(26,'+254-72670830874','',201,1,NULL),(27,'+254-71810214512','',202,1,NULL),(28,'+254-78410371929','',203,1,NULL),(29,'+254-762089175','',204,1,NULL),(30,'+254-76520852539','',205,1,NULL),(31,'+254-7472059229','',206,1,NULL),(32,'+254-76090284271','',207,1,NULL),(33,'+254-77730106189','',208,1,NULL),(34,'+254-71790790401','',209,1,NULL),(35,'+254-75640710831','',210,1,NULL),(36,'+254-75840958461','',211,1,NULL),(37,'+254-75490167817','',212,1,NULL),(38,'+254-7277019475','',213,1,NULL),(39,'+254-76780683149','',214,1,NULL),(40,'+254-74210165249','',215,1,NULL),(41,'+254-7880276886','',216,1,NULL),(42,'+254-74840124183','',217,1,NULL),(43,'+254-72000676649','',218,1,NULL),(44,'+254-77030123708','',219,1,NULL),(45,'+254-79510682496','',220,1,NULL),(46,'+254-73380266482','',221,1,NULL),(47,'+254-7229050750','',222,1,NULL),(48,'+254-7160268734','',223,1,NULL),(49,'+254-7114074430','',224,1,NULL),(50,'+254-74980136264','',225,1,NULL),(51,'+254-79650317936','',226,1,NULL),(52,'+254-74330691669','',227,1,NULL),(53,'+254-79090174781','',228,1,NULL),(54,'+254-72550269596','',229,1,NULL),(55,'+254-71080700714','',230,1,NULL),(56,'+254-7502021721','',231,1,NULL),(57,'+254-78580725558','',232,1,NULL),(58,'+254-72590904612','',233,1,NULL),(59,'+254-75780789467','',234,1,NULL),(60,'+254-78870161935','',235,1,NULL),(61,'+254-76350829781','',236,1,NULL),(62,'+254-71120420384','',237,1,NULL),(63,'+254-76600420449','',238,1,NULL),(64,'+254-76360580996','',239,1,NULL),(65,'+254-7346026232','',240,1,NULL),(66,'+254-715071488','',241,1,NULL),(67,'+254-74940187810','',242,1,NULL),(68,'+254-73980849662','',243,1,NULL),(69,'+254-7419045640','',244,1,NULL),(70,'+254-79280634514','',245,1,NULL),(71,'+254-7129017247','',246,1,NULL),(72,'+254-7424021261','',247,1,NULL),(73,'+254-79360400387','',248,1,NULL),(74,'+254-7130397835','',249,1,NULL),(75,'+254-72400425454','',250,1,NULL),(76,'+254-73360930601','',251,1,NULL),(77,'+254-73930400430','',252,1,NULL),(78,'+254-74570837128','',253,1,NULL),(79,'+254-78620553558','',254,1,NULL),(80,'+254-74710838556','',255,1,NULL),(81,'+254-72060803134','',256,1,NULL),(82,'+254-7920596885','',257,1,NULL),(83,'+254-7100143228','',258,1,NULL),(85,'+254-77360468395','',260,1,NULL),(86,'+254-7410363136','',261,1,NULL),(87,'+254-7316025246','',262,1,NULL),(88,'+254-73000866699','',263,1,NULL),(89,'+254-77950328306','',264,1,NULL),(90,'+254-79610707895','',265,1,NULL),(91,'+254-73390227485','',266,1,NULL),(92,'+254-7665061612','',267,1,NULL),(93,'+254-77190754304','',268,1,NULL),(94,'+254-796405466','',269,1,NULL),(95,'+254-73360983782','',270,1,NULL),(96,'+254-7910335980','',271,1,NULL),(97,'+254-7112086982','',272,1,NULL),(98,'+254-731501191','',273,1,NULL),(99,'+254-77530528176','',274,1,NULL),(100,'+254-74670222567','',275,1,NULL),(101,'+254-71160604153','',276,1,NULL),(102,'+254-77750532935','',277,1,NULL),(103,'+254-77970434282','',278,1,NULL),(104,'+254-7420864452','',279,1,NULL),(105,'+254-76350801341','',280,1,NULL),(106,'+254-7919093525','',281,1,NULL),(107,'+254-7451027762','',282,1,NULL),(108,'+254-769604646','',283,1,NULL),(109,'+254-75590178943','',284,1,NULL),(110,'+254-78380859572','',285,1,NULL),(111,'+254-7730370740','',286,1,NULL),(112,'+254-73950267539','',287,1,NULL),(113,'+254-74590592900','',288,1,NULL),(114,'+254-75180338337','',289,1,NULL),(115,'2904131254',NULL,290,1,NULL),(116,'291545263',NULL,291,1,NULL),(117,'29242419127',NULL,292,1,NULL),(118,'2939641184',NULL,293,1,NULL),(119,'2940220',NULL,294,1,NULL),(120,'2953519278',NULL,295,1,NULL),(121,'296651929',NULL,296,1,NULL),(122,'29706217',NULL,297,1,NULL),(123,'2984619156',NULL,298,1,NULL),(124,'299328282',NULL,299,1,NULL),(125,'3001934',NULL,300,1,NULL),(126,'301363999',NULL,301,1,NULL),(127,'3029312145',NULL,302,1,NULL),(128,'3039129129',NULL,303,1,NULL),(129,'304723299',NULL,304,1,NULL),(130,'30507746',NULL,305,1,NULL),(131,'306423165',NULL,306,1,NULL),(132,'30788616',NULL,307,1,NULL),(133,'3083142172',NULL,308,1,NULL),(134,'30905552933',NULL,309,1,NULL),(135,'3106263',NULL,310,1,NULL),(136,'31143962980',NULL,311,1,NULL),(137,'3124392228',NULL,312,1,NULL),(138,'31374298',NULL,313,1,NULL),(139,'31499396',NULL,314,1,NULL),(140,'315748181',NULL,315,1,NULL),(141,'316231145',NULL,316,1,NULL),(142,'3178814264',NULL,317,1,NULL),(143,'31832249112',NULL,318,1,NULL),(144,'319072493',NULL,319,1,NULL),(145,'3205034102',NULL,320,1,NULL),(146,'3219643281',NULL,321,1,NULL),(147,'32229349117',NULL,322,1,NULL),(148,'32394159300',NULL,323,1,NULL),(149,'324217122',NULL,324,1,NULL),(150,'325813220',NULL,325,1,NULL),(151,'3263524193',NULL,326,1,NULL),(152,'32774536',NULL,327,1,NULL),(153,'328047559119',NULL,328,1,NULL),(154,'32916249',NULL,329,1,NULL),(155,'330246309',NULL,330,1,NULL),(156,'331621759290',NULL,331,1,NULL),(157,'332628128',NULL,332,1,NULL),(158,'333063088',NULL,333,1,NULL),(159,'3343917246',NULL,334,1,NULL),(160,'33526279282',NULL,335,1,NULL),(161,'336711379181',NULL,336,1,NULL),(162,'337637989',NULL,337,1,NULL),(163,'33821479286',NULL,338,1,NULL),(164,'339025327',NULL,339,1,NULL),(165,'340457271',NULL,340,1,NULL),(166,'341595324',NULL,341,1,NULL),(167,'34232214',NULL,342,1,NULL),(168,'343436',NULL,343,1,NULL),(169,'344947679292',NULL,344,1,NULL),(170,'345150779',NULL,345,1,NULL),(171,'34615277199',NULL,346,1,NULL),(172,'3473477269',NULL,347,1,NULL),(173,'348406779313',NULL,348,1,NULL),(174,'349738779306',NULL,349,1,NULL),(175,'350170226',NULL,350,1,NULL),(176,'35127387941',NULL,351,1,NULL),(177,'35206879283',NULL,352,1,NULL),(178,'353619258',NULL,353,1,NULL),(179,'35451979155',NULL,354,1,NULL),(180,'3556397137',NULL,355,1,NULL),(181,'356685107',NULL,356,1,NULL),(182,'357838',NULL,357,1,NULL),(183,'35845108937',NULL,358,1,NULL),(184,'3594790307',NULL,359,1,NULL),(185,'3608318161',NULL,360,1,NULL),(186,'3610138',NULL,361,1,NULL),(187,'36272289343',NULL,362,1,NULL),(188,'3638852894',NULL,363,1,NULL),(189,'364228184',NULL,364,1,NULL),(190,'3658038222',NULL,365,1,NULL),(191,'3663246',NULL,366,1,NULL),(192,'36766389271',NULL,367,1,NULL),(193,'3689389150',NULL,368,1,NULL),(194,'36914205',NULL,369,1,NULL),(195,'370448999',NULL,370,1,NULL),(196,'37196311',NULL,371,1,NULL),(197,'37264948218',NULL,372,1,NULL),(198,'3739658267',NULL,373,1,NULL),(199,'374600317',NULL,374,1,NULL),(200,'3757268922',NULL,375,1,NULL),(201,'37605689253',NULL,376,1,NULL),(202,'377676211',NULL,377,1,NULL),(203,'378100789299',NULL,378,1,NULL),(204,'379137898',NULL,379,1,NULL),(205,'3806794',NULL,380,1,NULL),(206,'3817978196',NULL,381,1,NULL),(207,'3822889234',NULL,382,1,NULL),(208,'38384588138',NULL,383,1,NULL),(209,'3849889321',NULL,384,1,NULL),(210,'385051989158',NULL,385,1,NULL),(211,'38604989350',NULL,386,1,NULL),(212,'387625',NULL,387,1,NULL),(213,'3880299281',NULL,388,1,NULL),(214,'38994289',NULL,389,1,NULL),(215,'3905119913',NULL,390,1,NULL),(216,'39115419987',NULL,391,1,NULL),(217,'39271114',NULL,392,1,NULL),(218,'39391378',NULL,393,1,NULL),(219,'39412192',NULL,394,1,NULL),(220,'395529329',NULL,395,1,NULL),(221,'39603326',NULL,396,1,NULL),(222,'3977143174',NULL,397,1,NULL),(223,'398563341',NULL,398,1,NULL),(224,'3994883144',NULL,399,1,NULL),(225,'40011362',NULL,400,1,NULL),(226,'4013499187',NULL,401,1,NULL),(227,'40265176',NULL,402,1,NULL),(228,'403374203',NULL,403,1,NULL),(229,'40480599227',NULL,404,1,NULL),(230,'405224511',NULL,405,1,NULL),(231,'4066560',NULL,406,1,NULL),(232,'4071988',NULL,407,1,NULL),(233,'40851309',NULL,408,1,NULL),(234,'40983680',NULL,409,1,NULL),(235,'4106769296',NULL,410,1,NULL),(236,'411379313',NULL,411,1,NULL),(237,'4121579105',NULL,412,1,NULL),(238,'41387153',NULL,413,1,NULL),(239,'4144899207',NULL,414,1,NULL),(240,'415657899349',NULL,415,1,NULL),(241,'41609989391',NULL,416,1,NULL),(242,'4172959',NULL,417,1,NULL),(243,'418769280',NULL,418,1,NULL),(244,'419100099',NULL,419,1,NULL),(245,'420863',NULL,420,1,NULL),(246,'42121267',NULL,421,1,NULL),(247,'4224100117',NULL,422,1,NULL),(248,'4232471010',NULL,423,1,NULL),(249,'4249791260',NULL,424,1,NULL),(250,'425122193',NULL,425,1,NULL),(251,'4264542375',NULL,426,1,NULL),(252,'427172386',NULL,427,1,NULL),(253,'428330389',NULL,428,1,NULL),(254,'429763382',NULL,429,1,NULL),(255,'43056930078',NULL,430,1,NULL),(256,'43199119',NULL,431,1,NULL),(257,'43243440329',NULL,432,1,NULL),(258,'4338664069',NULL,433,1,NULL),(259,'434910',NULL,434,1,NULL),(260,'4351570',NULL,435,1,NULL),(261,'43679350345',NULL,436,1,NULL),(262,'437265184',NULL,437,1,NULL),(263,'4385850131',NULL,438,1,NULL),(264,'439751152',NULL,439,1,NULL),(265,'440293600241',NULL,440,1,NULL),(266,'441717600267',NULL,441,1,NULL),(267,'442596276',NULL,442,1,NULL),(268,'44327263',NULL,443,1,NULL),(269,'4443548',NULL,444,1,NULL);
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
) ENGINE=InnoDB AUTO_INCREMENT=270 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `postal_contact`
--

LOCK TABLES `postal_contact` WRITE;
/*!40000 ALTER TABLE `postal_contact` DISABLE KEYS */;
INSERT INTO `postal_contact` VALUES (1,'','','',110,176,1,NULL),(2,'','','',110,177,1,NULL),(3,'','','',110,178,1,NULL),(4,'','','',110,179,1,NULL),(5,'','','',110,180,1,NULL),(6,'','','',110,181,1,NULL),(7,'','','',110,182,1,NULL),(8,'','','',110,183,1,NULL),(9,'','','',110,184,1,NULL),(10,'','','',110,185,1,NULL),(11,'','','',110,186,1,NULL),(12,'','','',110,187,1,NULL),(13,'','','',110,188,1,NULL),(14,'','','',110,189,1,NULL),(15,'','','',1,190,1,NULL),(16,'','','',1,191,1,NULL),(17,'','','',110,192,1,NULL),(18,'','','',110,193,1,NULL),(19,'','','',110,194,1,NULL),(20,'','','',110,195,1,NULL),(21,'','','',110,196,1,NULL),(22,'','','',110,197,1,NULL),(23,'','','',110,198,1,NULL),(24,'','','',110,199,1,NULL),(25,'','','',110,200,1,NULL),(26,'','','',110,201,1,NULL),(27,'','','',110,202,1,NULL),(28,'','','',110,203,1,NULL),(29,'','','',110,204,1,NULL),(30,'','','',110,205,1,NULL),(31,'','','',110,206,1,NULL),(32,'','','',110,207,1,NULL),(33,'','','',110,208,1,NULL),(34,'','','',110,209,1,NULL),(35,'','','',110,210,1,NULL),(36,'','','',110,211,1,NULL),(37,'','','',110,212,1,NULL),(38,'','','',110,213,1,NULL),(39,'','','',110,214,1,NULL),(40,'','','',110,215,1,NULL),(41,'','','',110,216,1,NULL),(42,'','','',110,217,1,NULL),(43,'','','',110,218,1,NULL),(44,'','','',110,219,1,NULL),(45,'','','',110,220,1,NULL),(46,'','','',110,221,1,NULL),(47,'','','',110,222,1,NULL),(48,'','','',110,223,1,NULL),(49,'','','',110,224,1,NULL),(50,'','','',110,225,1,NULL),(51,'','','',110,226,1,NULL),(52,'','','',110,227,1,NULL),(53,'','','',110,228,1,NULL),(54,'','','',110,229,1,NULL),(55,'','','',110,230,1,NULL),(56,'','','',1,231,1,NULL),(57,'','','',110,232,1,NULL),(58,'','','',110,233,1,NULL),(59,'','','',110,234,1,NULL),(60,'','','',110,235,1,NULL),(61,'','','',110,236,1,NULL),(62,'','','',110,237,1,NULL),(63,'','','',110,238,1,NULL),(64,'','','',110,239,1,NULL),(65,'','','',110,240,1,NULL),(66,'','','',110,241,1,NULL),(67,'','','',110,242,1,NULL),(68,'','','',110,243,1,NULL),(69,'','','',110,244,1,NULL),(70,'','','',110,245,1,NULL),(71,'','','',110,246,1,NULL),(72,'','','',110,247,1,NULL),(73,'','','',110,248,1,NULL),(74,'','','',110,249,1,NULL),(75,'','','',110,250,1,NULL),(76,'','','',110,251,1,NULL),(77,'','','',110,252,1,NULL),(78,'','','',110,253,1,NULL),(79,'','','',110,254,1,NULL),(80,'','','',110,255,1,NULL),(81,'','','',110,256,1,NULL),(82,'','','',110,257,1,NULL),(83,'','','',110,258,1,NULL),(85,'','','',110,260,1,NULL),(86,'','','',110,261,1,NULL),(87,'','','',110,262,1,NULL),(88,'','','',110,263,1,NULL),(89,'','','',110,264,1,NULL),(90,'','','',110,265,1,NULL),(91,'','','',110,266,1,NULL),(92,'','','',110,267,1,NULL),(93,'','','',110,268,1,NULL),(94,'','','',110,269,1,NULL),(95,'','','',110,270,1,NULL),(96,'','','',110,271,1,NULL),(97,'','','',110,272,1,NULL),(98,'','','',110,273,1,NULL),(99,'','','',110,274,1,NULL),(100,'','','',110,275,1,NULL),(101,'','','',110,276,1,NULL),(102,'','','',110,277,1,NULL),(103,'','','',110,278,1,NULL),(104,'','','',1,279,1,NULL),(105,'','','',110,280,1,NULL),(106,'','','',110,281,1,NULL),(107,'','','',110,282,1,NULL),(108,'','','',110,283,1,NULL),(109,'','','',110,284,1,NULL),(110,'','','',110,285,1,NULL),(111,'','','',110,286,1,NULL),(112,'','','',110,287,1,NULL),(113,'','','',110,288,1,NULL),(114,'','','',110,289,1,NULL),(115,NULL,NULL,NULL,110,290,1,NULL),(116,NULL,NULL,NULL,110,291,1,NULL),(117,NULL,NULL,NULL,110,292,1,NULL),(118,NULL,NULL,NULL,110,293,1,NULL),(119,NULL,NULL,NULL,110,294,1,NULL),(120,NULL,NULL,NULL,110,295,1,NULL),(121,NULL,NULL,NULL,110,296,1,NULL),(122,NULL,NULL,NULL,110,297,1,NULL),(123,NULL,NULL,NULL,110,298,1,NULL),(124,NULL,NULL,NULL,110,299,1,NULL),(125,NULL,NULL,NULL,110,300,1,NULL),(126,NULL,NULL,NULL,110,301,1,NULL),(127,NULL,NULL,NULL,110,302,1,NULL),(128,NULL,NULL,NULL,110,303,1,NULL),(129,NULL,NULL,NULL,110,304,1,NULL),(130,NULL,NULL,NULL,110,305,1,NULL),(131,NULL,NULL,NULL,110,306,1,NULL),(132,NULL,NULL,NULL,110,307,1,NULL),(133,NULL,NULL,NULL,110,308,1,NULL),(134,NULL,NULL,NULL,110,309,1,NULL),(135,NULL,NULL,NULL,110,310,1,NULL),(136,NULL,NULL,NULL,110,311,1,NULL),(137,NULL,NULL,NULL,110,312,1,NULL),(138,NULL,NULL,NULL,110,313,1,NULL),(139,NULL,NULL,NULL,110,314,1,NULL),(140,NULL,NULL,NULL,110,315,1,NULL),(141,NULL,NULL,NULL,110,316,1,NULL),(142,NULL,NULL,NULL,110,317,1,NULL),(143,NULL,NULL,NULL,110,318,1,NULL),(144,NULL,NULL,NULL,110,319,1,NULL),(145,NULL,NULL,NULL,110,320,1,NULL),(146,NULL,NULL,NULL,110,321,1,NULL),(147,NULL,NULL,NULL,110,322,1,NULL),(148,NULL,NULL,NULL,110,323,1,NULL),(149,NULL,NULL,NULL,110,324,1,NULL),(150,NULL,NULL,NULL,110,325,1,NULL),(151,NULL,NULL,NULL,110,326,1,NULL),(152,NULL,NULL,NULL,110,327,1,NULL),(153,NULL,NULL,NULL,110,328,1,NULL),(154,NULL,NULL,NULL,110,329,1,NULL),(155,NULL,NULL,NULL,110,330,1,NULL),(156,NULL,NULL,NULL,110,331,1,NULL),(157,NULL,NULL,NULL,110,332,1,NULL),(158,NULL,NULL,NULL,110,333,1,NULL),(159,NULL,NULL,NULL,110,334,1,NULL),(160,NULL,NULL,NULL,110,335,1,NULL),(161,NULL,NULL,NULL,110,336,1,NULL),(162,NULL,NULL,NULL,110,337,1,NULL),(163,NULL,NULL,NULL,110,338,1,NULL),(164,NULL,NULL,NULL,110,339,1,NULL),(165,NULL,NULL,NULL,110,340,1,NULL),(166,NULL,NULL,NULL,110,341,1,NULL),(167,NULL,NULL,NULL,110,342,1,NULL),(168,NULL,NULL,NULL,110,343,1,NULL),(169,NULL,NULL,NULL,110,344,1,NULL),(170,NULL,NULL,NULL,110,345,1,NULL),(171,NULL,NULL,NULL,110,346,1,NULL),(172,NULL,NULL,NULL,110,347,1,NULL),(173,NULL,NULL,NULL,110,348,1,NULL),(174,NULL,NULL,NULL,110,349,1,NULL),(175,NULL,NULL,NULL,110,350,1,NULL),(176,NULL,NULL,NULL,110,351,1,NULL),(177,NULL,NULL,NULL,110,352,1,NULL),(178,NULL,NULL,NULL,110,353,1,NULL),(179,NULL,NULL,NULL,110,354,1,NULL),(180,NULL,NULL,NULL,110,355,1,NULL),(181,NULL,NULL,NULL,110,356,1,NULL),(182,NULL,NULL,NULL,110,357,1,NULL),(183,NULL,NULL,NULL,110,358,1,NULL),(184,NULL,NULL,NULL,110,359,1,NULL),(185,NULL,NULL,NULL,110,360,1,NULL),(186,NULL,NULL,NULL,110,361,1,NULL),(187,NULL,NULL,NULL,110,362,1,NULL),(188,NULL,NULL,NULL,110,363,1,NULL),(189,NULL,NULL,NULL,110,364,1,NULL),(190,NULL,NULL,NULL,110,365,1,NULL),(191,NULL,NULL,NULL,110,366,1,NULL),(192,NULL,NULL,NULL,110,367,1,NULL),(193,NULL,NULL,NULL,110,368,1,NULL),(194,NULL,NULL,NULL,110,369,1,NULL),(195,NULL,NULL,NULL,110,370,1,NULL),(196,NULL,NULL,NULL,110,371,1,NULL),(197,NULL,NULL,NULL,110,372,1,NULL),(198,NULL,NULL,NULL,110,373,1,NULL),(199,NULL,NULL,NULL,110,374,1,NULL),(200,NULL,NULL,NULL,110,375,1,NULL),(201,NULL,NULL,NULL,110,376,1,NULL),(202,NULL,NULL,NULL,110,377,1,NULL),(203,NULL,NULL,NULL,110,378,1,NULL),(204,NULL,NULL,NULL,110,379,1,NULL),(205,NULL,NULL,NULL,110,380,1,NULL),(206,NULL,NULL,NULL,110,381,1,NULL),(207,NULL,NULL,NULL,110,382,1,NULL),(208,NULL,NULL,NULL,110,383,1,NULL),(209,NULL,NULL,NULL,110,384,1,NULL),(210,NULL,NULL,NULL,110,385,1,NULL),(211,NULL,NULL,NULL,110,386,1,NULL),(212,NULL,NULL,NULL,110,387,1,NULL),(213,NULL,NULL,NULL,110,388,1,NULL),(214,NULL,NULL,NULL,110,389,1,NULL),(215,NULL,NULL,NULL,110,390,1,NULL),(216,NULL,NULL,NULL,110,391,1,NULL),(217,NULL,NULL,NULL,110,392,1,NULL),(218,NULL,NULL,NULL,110,393,1,NULL),(219,NULL,NULL,NULL,110,394,1,NULL),(220,NULL,NULL,NULL,110,395,1,NULL),(221,NULL,NULL,NULL,110,396,1,NULL),(222,NULL,NULL,NULL,110,397,1,NULL),(223,NULL,NULL,NULL,110,398,1,NULL),(224,NULL,NULL,NULL,110,399,1,NULL),(225,NULL,NULL,NULL,110,400,1,NULL),(226,NULL,NULL,NULL,110,401,1,NULL),(227,NULL,NULL,NULL,110,402,1,NULL),(228,NULL,NULL,NULL,110,403,1,NULL),(229,NULL,NULL,NULL,110,404,1,NULL),(230,NULL,NULL,NULL,110,405,1,NULL),(231,NULL,NULL,NULL,110,406,1,NULL),(232,NULL,NULL,NULL,110,407,1,NULL),(233,NULL,NULL,NULL,110,408,1,NULL),(234,NULL,NULL,NULL,110,409,1,NULL),(235,NULL,NULL,NULL,110,410,1,NULL),(236,NULL,NULL,NULL,110,411,1,NULL),(237,NULL,NULL,NULL,110,412,1,NULL),(238,NULL,NULL,NULL,110,413,1,NULL),(239,NULL,NULL,NULL,110,414,1,NULL),(240,NULL,NULL,NULL,110,415,1,NULL),(241,NULL,NULL,NULL,110,416,1,NULL),(242,NULL,NULL,NULL,110,417,1,NULL),(243,NULL,NULL,NULL,110,418,1,NULL),(244,NULL,NULL,NULL,110,419,1,NULL),(245,NULL,NULL,NULL,110,420,1,NULL),(246,NULL,NULL,NULL,110,421,1,NULL),(247,NULL,NULL,NULL,110,422,1,NULL),(248,NULL,NULL,NULL,110,423,1,NULL),(249,NULL,NULL,NULL,110,424,1,NULL),(250,NULL,NULL,NULL,110,425,1,NULL),(251,NULL,NULL,NULL,110,426,1,NULL),(252,NULL,NULL,NULL,110,427,1,NULL),(253,NULL,NULL,NULL,110,428,1,NULL),(254,NULL,NULL,NULL,110,429,1,NULL),(255,NULL,NULL,NULL,110,430,1,NULL),(256,NULL,NULL,NULL,110,431,1,NULL),(257,NULL,NULL,NULL,110,432,1,NULL),(258,NULL,NULL,NULL,110,433,1,NULL),(259,NULL,NULL,NULL,110,434,1,NULL),(260,NULL,NULL,NULL,110,435,1,NULL),(261,NULL,NULL,NULL,110,436,1,NULL),(262,NULL,NULL,NULL,110,437,1,NULL),(263,NULL,NULL,NULL,110,438,1,NULL),(264,NULL,NULL,NULL,110,439,1,NULL),(265,NULL,NULL,NULL,110,440,1,NULL),(266,NULL,NULL,NULL,110,441,1,NULL),(267,NULL,NULL,NULL,110,442,1,NULL),(268,NULL,NULL,NULL,110,443,1,NULL),(269,NULL,NULL,NULL,110,444,1,NULL);
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
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question`
--

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
INSERT INTO `question` VALUES (1,'Clarity of course objectives (Classroom/Clinical)',1,1,1,8,NULL,1,NULL),(2,'Achievement of course objectives',1,1,1,8,NULL,1,NULL),(3,'Relevance of course to programme objectives',1,1,1,8,NULL,1,NULL),(4,'Interpretation of concepts and theories',2,1,1,8,NULL,1,NULL),(5,'Coverage of course syllabus',2,1,1,8,NULL,1,NULL),(6,'Clarity in presentation',2,1,1,8,NULL,1,NULL),(7,'Effectiveness of presentation methods',2,1,1,8,NULL,1,NULL),(8,'Sufficiency of handouts',3,1,1,8,NULL,1,NULL),(9,'Value of recommended resource materials',3,1,1,8,NULL,1,NULL),(10,'Use of audio-visual and other teaching aids',3,1,1,8,NULL,1,NULL),(11,'Guidance on the use of web based material/journals',3,1,1,8,NULL,1,NULL),(12,'Adequacy of physical facilities',3,1,1,8,NULL,1,NULL),(13,'Sufficiency of computer(ICT) facility',3,1,1,8,NULL,1,NULL),(14,'Relevance of laboratory experiment(if any)',3,1,1,8,NULL,1,NULL),(15,'Relevance and usefulness of assignments/practicals/CATs',4,1,1,8,NULL,1,NULL),(16,'Appropriate coursework assessement',4,1,1,8,NULL,1,NULL),(17,'Satisfaction with methods of evaluation for classroom theory',4,1,1,8,NULL,1,NULL),(18,'Satisfaction with methods of assessment for practicals',4,1,1,8,NULL,1,NULL),(19,'Attends class regularly',5,1,1,8,NULL,1,NULL),(20,'Keeps to the published timetable',5,1,1,8,NULL,1,NULL),(21,'Is available for consultation when necessary(outside class time)',5,1,1,8,NULL,1,NULL),(22,'Guidance in practical lessons(e.g. Nursing)',5,1,1,8,NULL,1,NULL),(23,'Explains the scope, recommended readings, delivery and evaluation methodology of the course',6,1,1,8,NULL,1,NULL),(24,'Uses organized, up-to-date notes and course materials',6,1,1,8,NULL,1,NULL),(25,'Manages time well(punctual, uses class time efficiently)',6,1,1,8,NULL,1,NULL),(26,'Demonstration of procedures in the practical sessions',6,1,1,8,NULL,1,NULL),(27,'Presents course concepts and theories in a clear and interesting way',7,1,1,8,NULL,1,NULL),(28,'Facilitates meaningful and active class participation by students',7,1,1,8,NULL,1,NULL),(29,'Answers questions clearly and knowledgeably',7,1,1,8,NULL,1,NULL),(30,'Uses relevant examples and illustrations in the class/practical',7,1,1,8,NULL,1,NULL),(31,'Is open to diverse viewpoints and opinions',7,1,1,8,NULL,1,NULL),(32,'Gives relevant and challenging assignments and tests',8,1,1,8,NULL,1,NULL),(33,'Marks assignments and tests promptly',8,1,1,8,NULL,1,NULL),(34,'Gives helpful feedback on assignments and tests',8,1,1,8,NULL,1,NULL),(35,'Use of case studies for analysis',8,1,1,8,NULL,1,NULL),(36,'Overall rating of lecturer in the classroom/during lecture',9,1,1,8,NULL,1,NULL),(37,'Overall rating of the lecturer during practical/clinical sessions',9,1,1,8,NULL,1,NULL),(38,'What proportions of classes do you attend?',10,1,4,8,NULL,1,NULL),(39,'In a normal class/practical sesison, what portion of class members are present?',10,1,4,8,NULL,1,NULL),(40,'What are the reasons for the level of attendance you have reported above?',10,2,NULL,8,NULL,1,NULL),(41,'Your lecturer would like to know if there are certain aspects you believe he/she has done especially well in his/her teaching of this course?',11,3,NULL,8,NULL,1,NULL),(42,'Your lecturer would also like to know what specific things you believe must be done to improve his/her teaching in the course?',11,3,NULL,8,NULL,1,NULL),(43,'What other materials or resources, if any, should be added to enhance understanding of the course contents?',11,3,NULL,8,NULL,1,NULL),(44,'What is your overall view of the course?',11,2,NULL,8,NULL,1,NULL),(45,'Would you recommend this course to any other group of students?',11,1,3,8,NULL,1,NULL);
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
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;
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
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `rating` varchar(60) DEFAULT NULL,
  `rating_type` smallint(3) unsigned NOT NULL,
  `active` tinyint(1) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_rating_rating_type1_idx` (`rating_type`),
  CONSTRAINT `fk_rating_rating_type1` FOREIGN KEY (`rating_type`) REFERENCES `rating_type` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;
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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
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
  `date_completed` datetime DEFAULT NULL,
  `evaluation_session` int(10) unsigned NOT NULL,
  `active` tinyint(1) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `fk_student_feedback_faculty_member1_idx` (`faculty_member`),
  KEY `fk_student_feedback_evaluation_session1_idx` (`evaluation_session`),
  CONSTRAINT `fk_student_feedback_evaluation_session1` FOREIGN KEY (`evaluation_session`) REFERENCES `evaluation_session` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_student_feedback_faculty_member1` FOREIGN KEY (`faculty_member`) REFERENCES `faculty_member` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_feedback`
--

LOCK TABLES `student_feedback` WRITE;
/*!40000 ALTER TABLE `student_feedback` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=156 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_account`
--

LOCK TABLES `user_account` WRITE;
/*!40000 ALTER TABLE `user_account` DISABLE KEYS */;
INSERT INTO `user_account` VALUES (1,'P15/36965/2016','2c5c6de873654ed0f13668b8918304c3b21827c1bc17ba86fde6be73a3c7ef64',1,3,1,NULL,NULL,NULL),(2,'P15/1703/2016','823b0279823733f814894a3f8a24c1eb558d6aba798bf7b84cc6b868bb7d3484',2,3,1,NULL,NULL,NULL),(3,'P15/1699/2016','b4cf1b990280c1e0550b83d50d033d85c10f7802ae9fbd579a018875a77a8fa3',3,3,1,NULL,NULL,NULL),(4,'P15/34475/2014','2dfa91de326845bd54896f0ba5125032798e68741c92bc9c334ecbe692086892',4,3,1,NULL,NULL,NULL),(5,'P15/2753/2016','e54336c4f29f8c9f40bbceffd45293d89f7c99c8d00e6fbe431c3a9b08231c07',5,3,1,NULL,NULL,NULL),(6,'P15/36478/2016','27ef5b68c771ee8206b8a2e28ae2a7cf155b651c9975e000d3d190b9ae9dcac0',6,3,1,NULL,NULL,NULL),(7,'P15/1726/2016','413d0b67e95a8d83d44b65b8321103d7cc2dbf0cb1cc6c1a10eb2ccd71403502',7,3,1,NULL,NULL,NULL),(8,'P15/36780/2016','ea3d16cc24eaa9b21b924a3f1a2078cdf29fc5cb9f60db1df201556f5b4ccf58',8,3,1,NULL,NULL,NULL),(9,'P15/1721/2016','376c89aec254139311abc0d41cfc05157378c73e55fd0923a13a698af122da13',9,3,1,NULL,NULL,NULL),(10,'P15/1728/2016','69b54066d724b41511d8ae57f931f72580439004f42f54240b9d07070d95a9bb',10,3,1,NULL,NULL,NULL),(11,'P15/4630/2015','7fd197dd6801345413e937dacd51d49839e1fae2624f7d2735025f657b3839d6',11,3,1,NULL,NULL,NULL),(12,'P15/1709/2016','59621bc02dbb050e7bf0b51afef80e95b16f1f1f254e26e5e1271515299c2483',12,3,1,NULL,NULL,NULL),(13,'P15/31737/2015','5bcb2fbf729340d8e84b2cd3205a3782f47914b55f0a38224063b15bcf8d094c',13,3,1,NULL,NULL,NULL),(14,'P15/1719/2016','59cfa5a2bc0c08148b5b7b6530e84833f52c9743e46939fb19198efe6f159ebf',14,3,1,NULL,NULL,NULL),(15,'P15/1716/2016','062ef09461db8dd1ce707263be9c47a89e9b6ee927d7c6237a24697ed55711f5',15,3,1,NULL,NULL,NULL),(16,'P15/33494/2015','9aaceaf1ee87d25c29a4c146e384d242a3e2c3a729fe964b033362a8b5c5db1c',16,3,1,NULL,NULL,NULL),(17,'P15/37529/2016','ea198da81fedb799e4a041784b870910b7721c2338233b9f647badef69e4f4d0',17,3,1,NULL,NULL,NULL),(18,'P15/2559/2015','977385cfd8cd0bebbe3475a5c9823100026039ac14e103fab232c6318b7e4673',18,3,1,NULL,NULL,NULL),(19,'P15/1689/2016','5778e27de7deaf153568603fdf6233a07f189a2281e1515d04dd44b23b9bbdd6',19,3,1,NULL,NULL,NULL),(20,'P15/36376/2016','5e9e156945246435c976d40929aa98f497c0bf5c0ef6f2a2e76f277863f5ac7a',20,3,1,NULL,NULL,NULL),(21,'P15/35947/2015','cded24d9394d8e0b7000d109b89bda386ae4ee4f14fb1e5ed3ee79aab166964a',21,3,1,NULL,NULL,NULL),(22,'P15/1706/2016','2c670c622ef86f9f9c4b43945d95f3ac52bc10151f351571dacc30bbd132630f',22,3,1,NULL,NULL,NULL),(23,'P15/32156/2015','a7768721a2262f9f3698f79caed9f8c8ba3ac0ad1db532609c5cc47036a9433f',23,3,1,NULL,NULL,NULL),(24,'P15/1713/2016','24484471a1367970f39788306de0b5a8237c81cdb9d19342032f1721d48dbdf8',24,3,1,NULL,NULL,NULL),(25,'P15/36158/2015','4b72c496c267fd611388ea873b5a0e75aa42ddb3a50d54c6ad3aaf70a78dc2cb',25,3,1,NULL,NULL,NULL),(26,'P15/36969/2016','f5254a8b3044cfdd31c3a53ebbed07fb2b21f60f4d602cf1daefa9037f7b0874',26,3,1,NULL,NULL,NULL),(27,'P15/1698/2016','728789b46274584eb693e191cb0a6559dd9ee57e9df03defc9e3cc66e323aed9',27,3,1,NULL,NULL,NULL),(28,'P15/1696/2016','d5d502afac6368637c81196f273ccd83faae6424bc3d2b13027a0dc0040a7785',28,3,1,NULL,NULL,NULL),(29,'P15/37272/2016','e1c4f7f85f65d2ca8d9e1bf07b76b92c1761b78876563367e1105f5e8287a578',29,3,1,NULL,NULL,NULL),(30,'P15/36268/2016','ec8052f5e705882ac41508c1c291d90279f9671d374e62dcf4b1a98d596d0e86',30,3,1,NULL,NULL,NULL),(31,'P15/1708/2016','ccf62ae81565386d7f222ed0774c26d3df452148e92b1c1f0573eb3fc85ba44f',31,3,1,NULL,NULL,NULL),(32,'P15/36341/2015','aaa107cc9a0f48a99ebabda913ba3808322571ef3b2fb318700e465544981a1d',32,3,1,NULL,NULL,NULL),(33,'P15/37046/2016','2e09af1f27f1e526b038b4beca060bfc106096557e959d0d146938b7bfddb118',33,3,1,NULL,NULL,NULL),(34,'P15/37269/2016','48e77f10cf9075fc8f069a7b958f31f2514f806314c7e519a4377815bbb0e405',34,3,1,NULL,NULL,NULL),(35,'P15/37344/2016','ee97cb6c32c9440e93c0a6257ef188e24f45eb24929fd5044f742d54e187b78f',35,3,1,NULL,NULL,NULL),(36,'P15/36117/2015','75299e204c3be3d569132afe4276404c5f3e148d2008a8cc6e639bfc0cfcace8',36,3,1,NULL,NULL,NULL),(37,'P15/30653/2015','e79d10b717160bd0733a2ac29889f092d8eec4b204213acb8604f22543a76967',37,3,1,NULL,NULL,NULL),(38,'P15/37254/2016','0ce55b20bd4d5639ed5fc8c89c174bbc571cc1031b8b963204ed3e8ecd8f4a02',38,3,1,NULL,NULL,NULL),(39,'P15/36102/2015','5699fd7501b4075d65d48ca5164662c2257c6514c3b1b533025905e570f88d5c',39,3,1,NULL,NULL,NULL),(40,'P15/37738/2016','11a26e5cc5bd8376e01bb4307fcc7eab64ed89a08bcab7c4e357851aa59eae21',40,3,1,NULL,NULL,NULL),(41,'P15/31916/2015','b0a71831f31725624be8199f31c5f4b6194551523f2c3a725bb1e4f791f37348',41,3,1,NULL,NULL,NULL),(42,'P15/37641/2016','da489e57311b82600e9986bd02b5e0851e208152cc0d588efebe8529808b4986',42,3,1,NULL,NULL,NULL),(43,'P15/36237/2016','ee7a88a095281ea2034181df2195ce69caeef35870d329eb237845129d10ce02',43,3,1,NULL,NULL,NULL),(44,'P15/36507/2016','9e9b7727196bbfcbd0927851bf723771e159d6ab3ac2a03a4434a810e8214c71',44,3,1,NULL,NULL,NULL),(45,'P15/35753/2015','10cc0bba84020e9ab5dc7516ee771d729b57daedc775e919290a5e4e8f659ca3',45,3,1,NULL,NULL,NULL),(46,'P15/1690/2016','c347a4763b6c037fdbc149ed3ecf43866104c9169414840d818e05c93e581254',46,3,1,NULL,NULL,NULL),(47,'P15/37231/2016','ec7fb54b770eb1979afbae20a97e6c3b800e7448deab2a9db0f3a481716ca814',47,3,1,NULL,NULL,NULL),(48,'P15/36897/2016','07ddf43ca316c8d5ddcc14d3cbd5735fa6ab168b13a95d8de363a029840724f9',48,3,1,NULL,NULL,NULL),(49,'P15/36901/2016','54ea77ceb260749e5c8a29715bb2b649d693f9e8c9e83e0ce33b8b7ac9255646',49,3,1,NULL,NULL,NULL),(50,'P15/35462/2015','bc2bde2e1a32b2dff68471351ae620d0db25dd11a3233b851dc14938f7a75def',50,3,1,NULL,NULL,NULL),(51,'P15/1711/2016','84a0423838c2ccca9eb9482ba112e0362caef91f0adbcb1b1edf5cfb494ce859',51,3,1,NULL,NULL,NULL),(52,'P15/1715/2016','6a9ce93d7cd1637801d1c78d01f868277953b116cae35178d8e92f3e1baf27af',52,3,1,NULL,NULL,NULL),(53,'P15/36157/2015','815856fc5b3369ae655a14ac171f2ba5fc024f2e8a92970745fc7a0ff7579a37',53,3,1,NULL,NULL,NULL),(54,'P15/36569/2016','e8bc67c2fbd99a68da77db2e89848fd1b0c84544b1d763048d062d342d3747ed',54,3,1,NULL,NULL,NULL),(55,'P15/37364/2016','6f3a5b6834f94021cd93bf80df06dcf8b9149f16d89efb2b4f59455469be6e2a',55,3,1,NULL,NULL,NULL),(56,'P15/34677/2014','e08218b6e31f23c9e962f536f748b4619a8ca834f32069e351a3d539d12d8e57',56,3,1,NULL,NULL,NULL),(57,'P15/1718/2016','c9d641ad7b973561b22504aa61244522b5a0c286fe27e15ee1fa6548a7c774ca',57,3,1,NULL,NULL,NULL),(58,'P15/37561/2016','187440ef0d06d5d3457ca869480b5c1bce31cc9115c89eb291d9401ece68f1cc',58,3,1,NULL,NULL,NULL),(59,'P15/34906/2014','8e3886a81f31a3036b1e286280e3f375b70d65071fcc767894ddb883c260de64',59,3,1,NULL,NULL,NULL),(60,'P15/36089/2015','9bf356d98d79cfb85862ff74b764827d61d1b793397a50792c0665bede98d178',60,3,1,NULL,NULL,NULL),(61,'P15/1714/2016','b4738a7a503b3e1f7dc09c230932127db3dde449dc2cfd31df8ca8bc69adf7e6',61,3,1,NULL,NULL,NULL),(62,'P15/1841/2016','1e0f03447980c94e0d4756355127d9563dde1d07130d8121537748f4bd32944f',62,3,1,NULL,NULL,NULL),(63,'P15/1717/2016','74b8a59b0da60724441335559eb01876b8a31ce90f8f482220df395a45254876',63,3,1,NULL,NULL,NULL),(64,'P15/1694/2016','01d40043d2a11ea4f390e08296f38dee2a25e68ff9e74d1d764bfce763dd86db',64,3,1,NULL,NULL,NULL),(65,'P15/1701/2016','b915b783849d507c00cf09cc21272dc3152da1eccad9f520197877188ac1c703',65,3,1,NULL,NULL,NULL),(66,'P15/35805/2013','0a639b22f1a635e5fab3e5642352d27daa678bfa37807a85fd0e4cc0b258719e',66,3,1,NULL,NULL,NULL),(67,'P15/1693/2016','6fd37ffe87c928b48b775b8540cb3fd5961c2dff360cbd15c2f9d0bcb95d3f46',67,3,1,NULL,NULL,NULL),(68,'P15/31178/2015','1827a35df3011b54de77d369500fcbfd70fe3063f102ab548594bb9542c7fb55',68,3,1,NULL,NULL,NULL),(69,'P15/35160/2014','596e008d90c18e351cdf7cca52dfeb81ec9c6e7b7206573e91eea860fce0fc5c',69,3,1,NULL,NULL,NULL),(70,'P15/36503/2016','48e21537b7c0c35095baf004e8074bacbc08c8a45efb26e9f3f2287e82b0e2f3',70,3,1,NULL,NULL,NULL),(71,'P15/1702/2016','f2348b46790e12c9e4e6318ddfdfdd07ed0ca06e7514eb4cf58a2b271bf38cd6',71,3,1,NULL,NULL,NULL),(72,'P15/36168/2015','9b9d43faaebbce9c38e47faf6b5ff8280358ac16472a9dae1fee5d41d411d996',72,3,1,NULL,NULL,NULL),(73,'P15/36132/2015','45b7114439e76c9e78a8a5bcec048e83330d55174525cd3f8f2ca0dd295cfac1',73,3,1,NULL,NULL,NULL),(74,'P15/35834/2015','ceed5c30d5116e7fa8bb8661b32c515ef64fc585649d97aace021ed9cbbe264b',74,3,1,NULL,NULL,NULL),(75,'P15/36812/2016','69d1a40516e8fddd097bac753dd274029bf2b2e16618e414d5ac59c4be963449',75,3,1,NULL,NULL,NULL),(76,'P15/30459/2015','a1e0f336c787bf6fe7d739157882d1ba67921fd14e1d01d7b35a3964a6751872',76,3,1,NULL,NULL,NULL),(77,'P15/35101/2015','64b4f413adb50bfeab98b7ea28ed7eee2fdd6772b79f39bb757f03b28e182ac5',77,3,1,NULL,NULL,NULL),(78,'P15/33977/2014','e34d027df84c2c08c3f0281abb99bc3e11166cb72fa5f9b19cdd9c831a95bec2',78,3,1,NULL,NULL,NULL),(79,'P15/37874/2016','d2b0f9288c212072b67567dd586b755350941fc42c7013a5f943adb1587d277c',79,3,1,NULL,NULL,NULL),(80,'P15/36234/2016','ed11e1a82469186e85eda9f2bedbb6f7bb4845f2ba8d3fae2a73669b1371a875',80,3,1,NULL,NULL,NULL),(81,'P15/36821/2016','43f04cf54a629b42691de043b38eca08be207538436b1eaed51cb8a78f40b6d0',81,3,1,NULL,NULL,NULL),(82,'P15/35893/2015','952591be27b23634ddcaf1bff0e93001360024ec3721bde3672c9c2f29e0b99f',82,3,1,NULL,NULL,NULL),(83,'P15/35097/2015','55ed83adaaa25937abd2a69c34c1dcda1e51a037b7b33f33472069724a3e6702',83,3,1,NULL,NULL,NULL),(84,'P15/37139/2016','dd7587a61696ca4cf81315a516f94e816e1fcdac5f9fc9bd41f5bd2bd4b49057',84,3,1,NULL,NULL,NULL),(85,'P15/1697/2016','5cfd116be4b0dcd0a2d421a4510c4e94a9d1e331c7d0c214aeaf9a9f3c2e99b9',85,3,1,NULL,NULL,NULL),(86,'P15/37217/2016','1f4e313feff928c1517a1bb6b60141680cadea7108c2d2c7cbc27fcdbdbab00f',86,3,1,NULL,NULL,NULL),(87,'P15/37957/2016','5f8a54dfacb4e4291d417e992d65415537b7fccc010a7194746327866827dbc5',87,3,1,NULL,NULL,NULL),(88,'P15/1695/2016','8fb695b3fcfb808c15227e0bd51fbc0ad40d9e676eb4cff5409395c85ba6651e',88,3,1,NULL,NULL,NULL),(89,'P15/35759/2015','cdb307d3162038b7f7927289173090e9d44d2f239e02200a1e5d48206a2df5e1',89,3,1,NULL,NULL,NULL),(90,'P15/36496/2016','108fcdf790449669fa9873955b412a1d152c55084c144d00891df6ce6a43f8f7',90,3,1,NULL,NULL,NULL),(91,'P15/36653/2016','70bc0942ce6d02f8cd4d04e42dff4da97db05779a13c832480151684e1ed7135',91,3,1,NULL,NULL,NULL),(92,'P15/30324/2015','2167811da286ed8f497700aab5e97ee2d2e69836dd0b3fa084b193bd884ec4b1',92,3,1,NULL,NULL,NULL),(93,'P15/1433/2016','2b69a826c7f908b4b613988a555c52e2bfabb07f2663df11563c52bf946d9e35',93,3,1,NULL,NULL,NULL),(94,'P15/37017/2016','6bbdeb0bdc6de598ed63947a4af1343e7f599eaee05c55c6cbdc80014c138f2b',94,3,1,NULL,NULL,NULL),(95,'P15/38293/2016','63a545043018de88398c6d1b921799da1b243343c44fa75bbcdfc18367861b4a',95,3,1,NULL,NULL,NULL),(96,'P15/1724/2016','7e77ce4fc08010b270aaf4de5a0e806b8b890ad264eac20692a18b40f8562236',96,3,1,NULL,NULL,NULL),(97,'P15/34665/2014','b59a806f545e53a883b2d53428d40f29ff73eda145e51ad49e47fab17581eb3c',97,3,1,NULL,NULL,NULL),(98,'P15/30363/2015','99b5c7a70bd742362e207a809f4be30e254b8a615d4a82954809dc24b7c7a8c8',98,3,1,NULL,NULL,NULL),(99,'P15/37821/2016','de0f2a371fadba410898b3b3161962c52971d82bbd3f6473220efd05f0b90360',99,3,1,NULL,NULL,NULL),(100,'P15/1710/2016','759675d79183353789a2f1306a1e86c6190c0ccd8ea6f548e74f634fec0ac9b4',100,3,1,NULL,NULL,NULL),(101,'P15/37010/2016','d88e7852d0cd4bd69e71404c1e67cccfe71ea16651e5879717cdbc9a16ec604b',101,3,1,NULL,NULL,NULL),(102,'P15/1692/2016','10fc6de3065302deee3267710f9c8517746e7c28f5ab2b4d24880f54d790b84b',102,3,1,NULL,NULL,NULL),(103,'P15/1705/2016','528723ce030b58e203093c49ec95175a77921626c6c9b81726f3c1c65fb16657',103,3,1,NULL,NULL,NULL),(104,'P15/38236/2016','1f902cf5ef6ecadb2b6d03e6942b7cfba9b2152a4c2b009159d237b5619d811d',104,3,1,NULL,NULL,NULL),(105,'P15/36533/2016','a75d98b355b1fc30f1ad83e66060aff67dbf374c397d19f1423c3c91d25a4fca',105,3,1,NULL,NULL,NULL),(106,'P15/36513/2016','69f485fa5dae156c6ccf74e4f0d061b049a3432214c336e4a2045f15a36325b3',106,3,1,NULL,NULL,NULL),(107,'P15/37125/2016','a8c6808a63fd822f4625154182892cd52729c66ae148fe287d6a9d7ac7796e3b',107,3,1,NULL,NULL,NULL),(108,'P15/1722/2016','0985e86c31046068ebc2fbcfe93ea098846d4100039edddff0329ccee009d4f9',108,3,1,NULL,NULL,NULL),(109,'P15/1723/2016','a0d96bc1ecaca6f849fc2f70818d82533d98bafe3a7026c0bec02a42ca90ad8d',109,3,1,NULL,NULL,NULL),(110,'P15/37019/2016','1969022c36fbcd14e885159fa29cb3e4d17d036ecad9c9d32510e29fc661c086',110,3,1,NULL,NULL,NULL),(111,'P15/36238/2016','3a701ee6649adcf8cf02481456982741f658d5342cd28ea35da64a622246eac7',111,3,1,NULL,NULL,NULL),(112,'P15/36648/2016','6c6ccb24e4845a74a676bbc2e3d2f1fb8b63d396fdb87d918e8b84180be33505',112,3,1,NULL,NULL,NULL),(113,'P15/36076/2015','98e037de7b13012928b578ac463a8f2b6d728c8871b334037bb9d4b1972aff7d',113,3,1,NULL,NULL,NULL),(114,'P15/36134/2015','aa2d1136dceb9eab2232b198ebb5f96d68eef76b16bcbecee6656ad78ff3108f',114,3,1,NULL,NULL,NULL),(115,'P15/1688/2016','fdefefb4c72e99a9ca6a6284b57c8045fd3a08a39f9bec0b019a239d0e38983c',115,3,1,NULL,NULL,NULL),(116,'P15/31143/2015','bf1a59be27bf1ae9a155924defd4e36e1405ff594161423319d75aaae2e94a2d',116,3,1,NULL,NULL,NULL),(117,'P15/35037/2014','1b45cd1d2e2e3476a56e3bc2e51f4aadb14ac311ee778111f9228f368f8c6315',117,3,1,NULL,NULL,NULL),(118,'P15/1712/2016','b8324e3e7687667e561fa5b962d3b999b933185dd70b7fc578258fa4fc1beb98',118,3,1,NULL,NULL,NULL),(119,'P15/1700/2016','7bc9e0e3e47bc8e6ae0d717851d44004a8e32efee0e684824c47a0f81c4e476d',119,3,1,NULL,NULL,NULL),(120,'P15/1725/2016','00db8981a932c4a2fe7c7f7532ce630c5d34b3a73835d9cdf5992c81f34d4a03',120,3,1,NULL,NULL,NULL),(121,'P15/36756/2016','9c268c50bf7d3608cdfd396581b3f6f82044f3ae261648df1567d7a0648ddb5f',121,3,1,NULL,NULL,NULL),(122,'P15/37073/2016','54eb033b30bbfa85178df1d9775f9ede4640426989d36f8a877a4abe13894c7f',122,3,1,NULL,NULL,NULL),(123,'P15/1720/2016','ab5465ef4488db63bd2656a61af960cfd82839779b66bb435078027560caa8fb',123,3,1,NULL,NULL,NULL),(124,'P15/36874/2016','1de896e5ec56edd77cb98c172b6cb579ab1952b00d766a86e16d118c77e718cd',124,3,1,NULL,NULL,NULL),(125,'P15/1727/2016','6a00aad0ebf0eb7fd6cb1523f1dee3b48fc505fd69228f37a0080fd11e83f138',125,3,1,NULL,NULL,NULL),(126,'P15/34017/2015','e1f8a6f299cbcc76622d4ad91b075f275e4617c11f176c865fec344a71c45f3b',126,3,1,NULL,NULL,NULL),(127,'P15/37353/2016','eecdaa9a81ec262281e849c2414d19ce5e7a99a21990b9bfbe5c16646e756408',127,3,1,NULL,NULL,NULL),(128,'P15/30269/2015','3f1f37d900eb9a0e8bb98bb0d8b516ea12eaf3228f7672005487f6bc661effe5',128,3,1,NULL,NULL,NULL),(129,'P15/35324/2015','8dd42800cdf0c3170b07b6eeb3967a3a98fc26aa42fae88c0d6bbab857aa082d',129,3,1,NULL,NULL,NULL),(130,'P15/35255/2015','47c1fe4c50a887b9b7de66cf86c3658faa2e46a06680d030d83c6feec211ceaa',130,3,1,NULL,NULL,NULL),(131,'P15/38119/2016','9d1aafd49d8197d0cc2ff9383b3b72438946a7fe208490a71616a9496a53599b',131,3,1,NULL,NULL,NULL),(132,'P15/36337/2016','c1cef4fc6678a16752d4478b64b24049531620b20227b7db3e9e2cff5d08f9e5',132,3,1,NULL,NULL,NULL),(133,'P15/35291/2015','23b7a58a9156c0e6ff4212e1419f55a9130ef17dd20fa3247eb61cc6c785be72',133,3,1,NULL,NULL,NULL),(134,'P15/35945/2015','573c5c25543d7718a1bc0a9cf130d3b1055f933e56403f6da01b4fd5886f243c',134,3,1,NULL,NULL,NULL),(135,'P15/1729/2016','12c020f9783fd6560999df6065ccf6577f6f74d77e4891cc65f5cb3e581c5623',135,3,1,NULL,NULL,NULL),(136,'P15/37451/2016','9c7cc077649b9e62644f83384e84c7c7a0c579bb4cf4cb19bd447323f41bc90e',136,3,1,NULL,NULL,NULL),(137,'P15/36551/2016','69429a7ee742370535824a25bcff46a3554f6e2e250546ad9e63bda54617c279',137,3,1,NULL,NULL,NULL),(138,'P15/35280/2015','3058d0020103af1d15ca0160979031ed2f6423f5252662cf5c6b2c970118c81c',138,3,1,NULL,NULL,NULL),(139,'P15/36567/2016','6e28e20665214b8a6c3200281c4b21d22993351a587de9d2b8c0542827bd66db',139,3,1,NULL,NULL,NULL),(140,'P15/36349/2016','0e0935678082e17b172b1a4b55672ece23b46c353222e5af42dac037202da6cf',140,3,1,NULL,NULL,NULL),(141,'P15/1691/2016','3693c3404e69aa269065bb0f5d183a4bdc51166984646a25e05918e628b95a7a',141,3,1,NULL,NULL,NULL),(142,'P15/38166/2016','438349f8e9c92230ce1c68f042bcc92850e3ee54452376293afe49f0dd643d63',142,3,1,NULL,NULL,NULL),(143,'P15/37390/2016','ec30d5d0c94c8b37fa3dd966e64652f00064373d3f8758bc5e35ca715a97b0c1',143,3,1,NULL,NULL,NULL),(144,'P15/35060/2015','358e70ccf59c6735e582f5da645f4bb26e21664f99ddef8d16f2f50d62f43fa6',144,3,1,NULL,NULL,NULL),(145,'P15/37020/2016','66863a945abd27c38ccb262fe956b291d5e848555d14463ec1189eac2571bbb0',145,3,1,NULL,NULL,NULL),(146,'P15/1704/2016','b4f8b8d69f92c9978e8ac96205e9a645517d14777c707c4d1e5c3ec46ac76d08',146,3,1,NULL,NULL,NULL),(147,'P15/35954/2015','043d42116badeab5def31a78158175a8b5598a23fd2ab55d5c556df1b3cc82d7',147,3,1,NULL,NULL,NULL),(148,'P15/1707/2016','bdcf9bfbd9cc79926bc630bc67a33fa4647d5cf48ab7b35b4bd58fa27d54fda6',148,3,1,NULL,NULL,NULL),(149,'P15/36820/2016','f58b442a7e86905fd056ed8b93f7cb11cfe0f54512cb1d0a7b284e40aedfa02a',149,3,1,NULL,NULL,NULL),(150,'P15/37847/2016','02032898fda59e12faab931c681331ec530e050e1236a60cf1d900208a11006a',150,3,1,NULL,NULL,NULL),(151,'CSC111Miriti','e2b4aa5e0ba3a6db1e9a571c14e45bdf64c9afd72472c560f7c9930f073adffc',151,3,1,NULL,NULL,NULL),(152,'CSC112Omwenga','46e11654d07e5dafafe1bbe965a549cc0d2e0de670e04d9c16ac79cf84476c46',152,3,1,NULL,NULL,NULL),(153,'CSC113Abungu','6aafe6ed78ced396ca499c455b605e0d4e1ddb9d7f6499697c161335b075ccbd',153,3,1,NULL,NULL,NULL),(154,'CCS001Mary','b7cf4cc9d326608c466907733f13f1abb08082c9928465784210f623980eae64',154,3,1,NULL,NULL,NULL),(155,'CSC009Muriu','0ba07c13ead3f1d1f7566c2583f086b1cf603c8d2cc9ce292da712a2dd4778d5',155,3,1,NULL,NULL,NULL);
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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_group`
--

LOCK TABLES `user_group` WRITE;
/*!40000 ALTER TABLE `user_group` DISABLE KEYS */;
INSERT INTO `user_group` VALUES (1,'Management',NULL,NULL),(2,'Lecturer',NULL,NULL),(3,'Student',NULL,NULL),(4,'Other staff',NULL,NULL),(5,'Admin',NULL,NULL);
/*!40000 ALTER TABLE `user_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'ocena'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-11-15 22:21:39
