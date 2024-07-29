CREATE DATABASE  IF NOT EXISTS `service_app` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `service_app`;
-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: service_app
-- ------------------------------------------------------
-- Server version	8.0.37

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `sr_ticket_history`
--

DROP TABLE IF EXISTS `sr_ticket_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sr_ticket_history` (
  `TICKET_HISTORY_ID` int NOT NULL AUTO_INCREMENT,
  `TICKET_ID` int DEFAULT NULL,
  `ticket_status` int NOT NULL,
  `UPDATED_BY` int DEFAULT NULL,
  `UPDATE_TIMESTAMP` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`TICKET_HISTORY_ID`),
  KEY `TICKET_ID` (`TICKET_ID`),
  KEY `UPDATED_BY` (`UPDATED_BY`),
  KEY `FK2aiobmllmwywyvolx02p0048e` (`ticket_status`),
  CONSTRAINT `FK2aiobmllmwywyvolx02p0048e` FOREIGN KEY (`ticket_status`) REFERENCES `sr_ticket_status` (`TICKET_STATUS_ID`),
  CONSTRAINT `sr_ticket_history_ibfk_1` FOREIGN KEY (`TICKET_ID`) REFERENCES `sr_tickets` (`SR_TICKET_ID`),
  CONSTRAINT `sr_ticket_history_ibfk_2` FOREIGN KEY (`UPDATED_BY`) REFERENCES `employee` (`EMPLOYEE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=133 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sr_ticket_history`
--

LOCK TABLES `sr_ticket_history` WRITE;
/*!40000 ALTER TABLE `sr_ticket_history` DISABLE KEYS */;
INSERT INTO `sr_ticket_history` VALUES (3,6,1,2,'2024-07-16 06:41:39'),(5,8,1,5,'2024-07-16 23:33:04'),(21,14,1,2,'2024-07-18 05:01:59'),(22,15,1,2,'2024-07-18 05:05:05'),(23,15,1,2,'2024-07-18 05:07:46'),(24,16,1,2,'2024-07-18 05:08:19'),(26,18,1,2,'2024-07-18 05:13:27'),(27,18,1,2,'2024-07-18 05:14:38'),(28,18,1,2,'2024-07-18 05:15:50'),(29,19,1,4,'2024-07-18 10:47:38'),(30,19,1,4,'2024-07-18 10:49:23'),(31,18,1,4,'2024-07-18 10:54:17'),(32,18,1,2,'2024-07-18 10:55:08'),(33,20,1,4,'2024-07-18 11:05:46'),(34,20,1,4,'2024-07-18 11:07:40'),(35,21,1,2,'2024-07-18 11:09:42'),(36,21,1,2,'2024-07-18 11:11:07'),(37,22,1,2,'2024-07-18 11:17:26'),(39,24,1,2,'2024-07-18 12:56:47'),(40,1,1,5,'2024-07-19 06:21:32'),(46,8,1,5,'2024-07-19 07:34:08'),(47,21,1,2,'2024-07-19 07:34:43'),(50,28,1,5,'2024-07-19 08:48:07'),(51,29,1,2,'2024-07-19 09:43:47'),(55,21,1,2,'2024-07-23 04:58:25'),(60,33,1,12,'2024-07-23 06:14:54'),(61,22,1,2,'2024-07-23 06:48:53'),(62,22,1,2,'2024-07-23 06:52:56'),(63,34,1,11,'2024-07-23 06:54:53'),(64,35,1,11,'2024-07-23 06:57:11'),(65,35,1,11,'2024-07-23 06:57:33'),(67,37,1,2,'2024-07-23 08:42:39'),(68,38,1,2,'2024-07-23 09:08:49'),(69,38,1,2,'2024-07-23 09:10:29'),(70,38,1,2,'2024-07-23 09:17:33'),(71,39,1,2,'2024-07-23 09:18:09'),(77,41,1,2,'2024-07-23 09:29:24'),(78,42,1,2,'2024-07-23 09:32:24'),(79,43,1,2,'2024-07-23 09:49:22'),(81,45,1,2,'2024-07-23 11:32:16'),(82,45,1,2,'2024-07-23 11:33:42'),(83,45,1,2,'2024-07-23 11:33:56'),(90,50,1,5,'2024-07-24 04:58:49'),(91,51,1,5,'2024-07-24 04:59:34'),(94,53,1,5,'2024-07-24 05:02:32'),(95,54,1,6,'2024-07-24 05:08:09'),(96,55,1,5,'2024-07-24 06:25:29'),(98,57,1,5,'2024-07-24 08:11:27'),(99,58,1,5,'2024-07-24 08:15:59'),(100,55,1,5,'2024-07-24 08:41:47'),(101,55,1,5,'2024-07-24 08:44:34'),(102,55,1,5,'2024-07-24 08:49:54'),(103,55,1,5,'2024-07-24 10:04:21'),(106,61,1,5,'2024-07-24 10:11:38'),(109,58,1,5,'2024-07-24 10:44:36'),(110,64,1,6,'2024-07-24 11:01:06'),(111,65,1,5,'2024-07-24 11:12:08'),(112,66,1,5,'2024-07-24 12:11:36'),(113,65,1,5,'2024-07-24 12:11:54'),(114,45,1,2,'2024-07-24 13:23:32'),(115,45,1,2,'2024-07-24 13:24:15'),(116,67,1,5,'2024-07-25 04:23:56'),(117,67,1,5,'2024-07-25 04:24:13'),(118,67,1,5,'2024-07-25 04:26:18'),(119,66,1,5,'2024-07-25 04:27:00'),(121,69,1,5,'2024-07-25 04:28:48'),(122,66,1,5,'2024-07-25 04:30:25'),(123,67,1,5,'2024-07-25 04:30:33'),(124,70,1,18,'2024-07-25 04:34:58'),(125,70,1,18,'2024-07-25 04:36:03'),(127,72,1,18,'2024-07-25 04:38:20'),(128,73,1,19,'2024-07-25 05:08:38'),(129,73,1,19,'2024-07-25 05:13:26'),(130,74,1,19,'2024-07-25 05:23:23'),(131,75,1,12,'2024-07-25 05:27:23'),(132,76,1,12,'2024-07-25 05:31:09');
/*!40000 ALTER TABLE `sr_ticket_history` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-07-25 12:14:56
