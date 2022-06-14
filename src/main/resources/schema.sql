CREATE SCHEMA IF NOT EXISTS `cryptobank` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `cryptobank`;

DROP TABLE IF EXISTS `asset`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `asset` (
                         `assetCode` varchar(6) NOT NULL,
                         `assetName` varchar(45) NOT NULL,
                         `rateInEuro` double NOT NULL,
                         PRIMARY KEY (`assetCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `assetratehistory` (
                                    `dateTime` datetime NOT NULL,
                                    `assetCode` varchar(15) NOT NULL,
                                    `rate` double NOT NULL,
                                    PRIMARY KEY (`dateTime`,`assetCode`),
                                    KEY `verzinzelf6_idx` (`assetCode`),
                                    CONSTRAINT `verzinzelf6` FOREIGN KEY (`assetCode`) REFERENCES `asset` (`assetCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*!40101 SET character_set_client = @saved_cs_client */;
