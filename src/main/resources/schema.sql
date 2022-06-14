
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
