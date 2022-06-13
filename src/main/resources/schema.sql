CREATE DATABASE  IF NOT EXISTS `cryptobank` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `cryptobank`;
-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: miw-team-1.nl    Database: cryptobank
-- ------------------------------------------------------
-- Server version	8.0.26

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
-- Table structure for table `article_link`
--

DROP TABLE IF EXISTS `article_link`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `article_link` (
  `id` int NOT NULL AUTO_INCREMENT,
  `article_link` varchar(800) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `asset`
--

DROP TABLE IF EXISTS `asset`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `asset` (
  `assetCode` varchar(6) NOT NULL,
  `assetName` varchar(45) NOT NULL,
  `rateInEuro` double NOT NULL,
  PRIMARY KEY (`assetCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `assetofcustomer`
--

DROP TABLE IF EXISTS `assetofcustomer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `assetofcustomer` (
  `assetCode` varchar(8) NOT NULL,
  `userId` int NOT NULL,
  `quantityOfAsset` double NOT NULL,
  PRIMARY KEY (`assetCode`,`userId`),
  KEY `verzinzelf5_idx` (`assetCode`),
  KEY `verzinzelf4_idx` (`userId`),
  CONSTRAINT `verzinzelf4` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`),
  CONSTRAINT `verzinzelf5` FOREIGN KEY (`assetCode`) REFERENCES `asset` (`assetCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `assetofcustomerhistory`
--

DROP TABLE IF EXISTS `assetofcustomerhistory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `assetofcustomerhistory` (
  `dateTime` datetime NOT NULL,
  `assetCode` varchar(3) NOT NULL,
  `userId` int NOT NULL,
  `quantityOfAsset` double NOT NULL,
  PRIMARY KEY (`dateTime`,`assetCode`,`userId`),
  KEY `verzinzelf7_idx` (`assetCode`,`userId`),
  CONSTRAINT `verzinzelf7` FOREIGN KEY (`assetCode`, `userId`) REFERENCES `assetofcustomer` (`assetCode`, `userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `assetratehistory`
--

DROP TABLE IF EXISTS `assetratehistory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `assetratehistory` (
  `dateTime` datetime NOT NULL,
  `assetCode` varchar(15) NOT NULL,
  `rate` double NOT NULL,
  PRIMARY KEY (`dateTime`,`assetCode`),
  KEY `verzinzelf6_idx` (`assetCode`),
  CONSTRAINT `verzinzelf6` FOREIGN KEY (`assetCode`) REFERENCES `asset` (`assetCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `bank account`
--

DROP TABLE IF EXISTS `bank account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bank account` (
  `IBAN` varchar(45) NOT NULL,
  `balanceInEuro` double NOT NULL,
  `userId` int NOT NULL,
  PRIMARY KEY (`userId`),
  CONSTRAINT `verzinzelf2` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `profile`
--

DROP TABLE IF EXISTS `profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `profile` (
                           `userName` varchar(80) NOT NULL,
                           `hash` varchar(128) NOT NULL,
                           `salt` varchar(128) NOT NULL,
                           `userId` int NOT NULL,
                           PRIMARY KEY (`userId`),
                           UNIQUE KEY `userName_UNIQUE` (`userName`),
                           CONSTRAINT `verzinzelf1` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `token`
--

DROP TABLE IF EXISTS `token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `token` (
  `idToken` varchar(64) NOT NULL,
  `valid until` datetime NOT NULL,
  `userId` int NOT NULL,
  PRIMARY KEY (`idToken`),
  KEY `verzinzelf12_idx` (`userId`),
  CONSTRAINT `verzinzelf12` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `transactioncosts`
--

DROP TABLE IF EXISTS `transactioncosts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transactioncosts` (
  `transactionCosts` double NOT NULL,
  PRIMARY KEY (`transactionCosts`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `transactionhistory`
--

DROP TABLE IF EXISTS `transactionhistory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transactionhistory` (
  `transactionId` int NOT NULL AUTO_INCREMENT,
  `quantity` double NOT NULL,
  `rateInEuro` double NOT NULL,
  `dateTime` datetime NOT NULL,
  `transactionCosts` double NOT NULL,
  `buyerId` int NOT NULL,
  `sellerId` int NOT NULL,
  `assetCode` varchar(3) NOT NULL,
  PRIMARY KEY (`transactionId`),
  KEY `verzinzelf10_idx` (`sellerId`),
  KEY `verzinzelf11_idx` (`assetCode`),
  KEY `verzinzelf8` (`buyerId`),
  CONSTRAINT `verzinzelf10` FOREIGN KEY (`sellerId`) REFERENCES `user` (`userId`),
  CONSTRAINT `verzinzelf11` FOREIGN KEY (`assetCode`) REFERENCES `asset` (`assetCode`),
  CONSTRAINT `verzinzelf8` FOREIGN KEY (`buyerId`) REFERENCES `user` (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `triggertransaction`
--

DROP TABLE IF EXISTS `triggertransaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `triggertransaction` (
  `assetCode` varchar(3) NOT NULL,
  `userId` int NOT NULL,
  `sellYesNo` tinyint NOT NULL,
  `triggerRate` double NOT NULL,
  `quantityToBuyOrSell` double NOT NULL,
  PRIMARY KEY (`assetCode`,`userId`,`sellYesNo`),
  KEY `verzinzelf9_idx` (`assetCode`),
  KEY `verzinzelf3_idx` (`userId`),
  CONSTRAINT `verzinzelf3` FOREIGN KEY (`userId`) REFERENCES `user` (`userId`),
  CONSTRAINT `verzinzelf9` FOREIGN KEY (`assetCode`) REFERENCES `asset` (`assetCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `userId` int NOT NULL AUTO_INCREMENT,
  `firstName` varchar(45) NOT NULL,
  `prefix` varchar(45) DEFAULT NULL,
  `surname` varchar(45) NOT NULL,
  `dateOfBirth` date NOT NULL,
  `fiscalNumber` int NOT NULL,
  `streetName` varchar(45) NOT NULL,
  `houseNumber` varchar(45) NOT NULL,
  `zipCode` varchar(45) NOT NULL,
  `residence` varchar(45) NOT NULL,
  `country` varchar(45) NOT NULL,
  `role` varchar(45) NOT NULL,
  `staffId` int DEFAULT NULL,
  PRIMARY KEY (`userId`),
  UNIQUE KEY `fiscalNumber` (`fiscalNumber`)
) ENGINE=InnoDB AUTO_INCREMENT=3131 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-06-08 14:39:09
