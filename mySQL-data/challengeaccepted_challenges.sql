-- MySQL dump 10.13  Distrib 5.7.9, for osx10.9 (x86_64)
--
-- Host: localhost    Database: challengeaccepted
-- ------------------------------------------------------
-- Server version	5.7.11

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
-- Table structure for table `challenges`
--

DROP TABLE IF EXISTS `challenges`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `challenges` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `creationDate` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `isChallengeClaimed` bit(1) DEFAULT NULL,
  `isChallengeCompleted` bit(1) DEFAULT NULL,
  `isChallengeDisapproved` bit(1) DEFAULT NULL,
  `isYoutubeUrlProvided` bit(1) DEFAULT NULL,
  `isYoutubeVideoUploaded` bit(1) DEFAULT NULL,
  `points` double DEFAULT NULL,
  `topic` varchar(255) DEFAULT NULL,
  `upvotes` double DEFAULT NULL,
  `youtubeURL` varchar(255) DEFAULT NULL,
  `challenge_claimer_id` bigint(20) DEFAULT NULL,
  `challenge_creator_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_9ocnxn5eus1tie48jn5tptm3` (`challenge_claimer_id`),
  KEY `FK_cqce2b189qgrgueef1k1c52j7` (`challenge_creator_id`),
  CONSTRAINT `FK_9ocnxn5eus1tie48jn5tptm3` FOREIGN KEY (`challenge_claimer_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FK_cqce2b189qgrgueef1k1c52j7` FOREIGN KEY (`challenge_creator_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `challenges`
--

LOCK TABLES `challenges` WRITE;
/*!40000 ALTER TABLE `challenges` DISABLE KEYS */;
INSERT INTO `challenges` VALUES (1,'2016-05-19 11:00:48','lkjadgdkldkdhksöxkbf\n','','','\0','\0','\0',2,'sadlkhjashdnmalsdmkjb',1,'https://www.youtube.com/embed/Ax7tmaYObIQ',2,1),(2,'2016-05-19 11:05:35','PADDE! DET ÄR DAGS ATT SKAFFA RIGMOR NU!!!!!','\0','\0','\0',NULL,'\0',NULL,'Känn dig utmanad...',0,NULL,NULL,2),(3,'2016-05-19 11:05:54','I en isvak och sjung Bä, bä vita lamm.','','','\0','\0','\0',1,'Doppa huvudet',1,'https://www.youtube.com/embed/gsYHQb266qg',3,2),(4,'2016-05-19 11:06:16','I 40 minuter utan att andas och utan att dö','\0','\0','\0',NULL,'\0',NULL,'Vissla en sång',1,NULL,NULL,2),(5,'2016-05-19 11:06:31','Hela vägen till och från skolan','\0','\0','\0',NULL,'\0',NULL,'Hoppa jämfota',1,NULL,NULL,2),(6,'2016-05-19 11:06:46','Resten av livet','','','\0','\0','\0',2,'Drick kallt kaffe',3,'https://www.youtube.com/embed/NkhwuV9wAKE',3,2);
/*!40000 ALTER TABLE `challenges` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-05-19 11:14:03
