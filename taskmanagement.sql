CREATE DATABASE  IF NOT EXISTS `taskmanagment` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `taskmanagment`;
-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: localhost    Database: taskmanagment
-- ------------------------------------------------------
-- Server version	8.0.42

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
-- Table structure for table `assignmentrequests`
--

DROP TABLE IF EXISTS `assignmentrequests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `assignmentrequests` (
  `request_id` int NOT NULL,
  `user_id` int NOT NULL,
  `username` varchar(100) NOT NULL,
  `task_id` int NOT NULL,
  `status` varchar(20) DEFAULT 'Pending',
  PRIMARY KEY (`request_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `assignmentrequests`
--

LOCK TABLES `assignmentrequests` WRITE;
/*!40000 ALTER TABLE `assignmentrequests` DISABLE KEYS */;
INSERT INTO `assignmentrequests` VALUES (6,12,'meet',9,'Pending');
/*!40000 ALTER TABLE `assignmentrequests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tasklogs`
--

DROP TABLE IF EXISTS `tasklogs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tasklogs` (
  `log_id` int NOT NULL AUTO_INCREMENT,
  `task_id` int DEFAULT NULL,
  `action` varchar(50) DEFAULT NULL,
  `action_by_user_id` int DEFAULT NULL,
  `action_timestamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `details` text,
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tasklogs`
--

LOCK TABLES `tasklogs` WRITE;
/*!40000 ALTER TABLE `tasklogs` DISABLE KEYS */;
INSERT INTO `tasklogs` VALUES (1,7,'ADD_TASK',13,'2025-08-16 10:42:56','Task \"task 1\" added.'),(2,7,'REMOVE_TASK',13,'2025-08-16 11:09:01','Task \"task 1\" removed.'),(3,8,'ADD_TASK',13,'2025-08-18 08:52:53','Task \"TestTaskOne\" added.'),(4,8,'REMOVE_TASK',13,'2025-08-18 10:49:32','Task \"TestTaskOne\" removed.'),(5,9,'ADD_TASK',6,'2025-08-22 13:26:55','Task \"test\" added.');
/*!40000 ALTER TABLE `tasklogs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tasks`
--

DROP TABLE IF EXISTS `tasks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tasks` (
  `task_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `description` text,
  `priority` enum('Low','Medium','High') DEFAULT NULL,
  `status` enum('Pending','In Progress','Completed') DEFAULT NULL,
  `due_date` date DEFAULT NULL,
  `assigned_user_id` int DEFAULT NULL,
  PRIMARY KEY (`task_id`),
  KEY `assigned_user_id` (`assigned_user_id`),
  CONSTRAINT `tasks_ibfk_1` FOREIGN KEY (`assigned_user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tasks`
--

LOCK TABLES `tasks` WRITE;
/*!40000 ALTER TABLE `tasks` DISABLE KEYS */;
INSERT INTO `tasks` VALUES (2,'just do it','task that help you to concentrate','High','Pending','2025-03-13',5),(3,'let\'s go','keep your self active ','Medium','In Progress','2012-03-14',5),(5,'task 1','it is just a test task ','Medium','Completed','2025-12-03',8),(6,'my task 1','this is a try task ','Medium','Pending','2002-02-03',11),(9,'test','test task','Low','Pending','2025-12-03',12);
/*!40000 ALTER TABLE `tasks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=112 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (5,'wet','wet@123gmail.com','456789vds'),(6,'meet','meet@gmail.com','pvdqsx'),(7,'neel','neel@gmail','pvdqsx012'),(8,'teel','teel@gmail.com','qwerty123'),(9,'neel','qwert@gamil',''),(10,'neel','qwe','pvdqsx'),(11,'meet patel','meet','ldds456'),(12,'me','me@you.com','ldxnt'),(13,'qwfsgdhmjh','verb dsg','q56'),(14,'3','3','6'),(15,'qwfsgdhmjh','qwfsgdhmjh','q56'),(16,'asdfdghgfhm,','dgfhjghkhjl','frcgeifj'),(17,'vv','vv@gmail.com','4567vdc'),(18,'meet23','meet@@@@@@@@.in','pvdqsx45645464546#45@56'),(111,'admin','admin@gmail.com','pvdqsx');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-08-27 18:07:30
