
CREATE SCHEMA IF NOT EXISTS `Cryptobank` DEFAULT CHARACTER SET utf8 ;
USE `Cryptobank` ;

-- -----------------------------------------------------
-- Table `Cryptobank`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Cryptobank`.`User` (
  `userId` INT NOT NULL AUTO_INCREMENT,
  `firstName` VARCHAR(45) NOT NULL,
  `prefix` VARCHAR(45) NULL,
  `surname` VARCHAR(45) NOT NULL,
  `dateOfBirth` DATE NOT NULL,
  `fiscalNumber` INT NOT NULL,
  `streetName` VARCHAR(45) NOT NULL,
  `houseNumber` VARCHAR(45) NOT NULL,
  `zipCode` VARCHAR(45) NOT NULL,
  `residence` VARCHAR(45) NOT NULL,
  `country` VARCHAR(45) NOT NULL,
  `role` VARCHAR(45) NOT NULL,
  `staffId` INT NULL,
  PRIMARY KEY (`userId`),
  UNIQUE INDEX `staffId_UNIQUE` (`staffId` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Cryptobank`.`Bank Account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Cryptobank`.`Bank Account` (
  `IBAN` VARCHAR(45) NOT NULL,
  `balanceInEuro` DOUBLE NOT NULL,
  `userId` INT NOT NULL,
  PRIMARY KEY (`userId`),
  CONSTRAINT `verzinzelf2`
    FOREIGN KEY (`userId`)
    REFERENCES `Cryptobank`.`User` (`userId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Cryptobank`.`Asset`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Cryptobank`.`Asset` (
  `assetCode` VARCHAR(3) NOT NULL,
  `assetName` VARCHAR(45) NOT NULL,
  `rateInEuro` DOUBLE NOT NULL,
  PRIMARY KEY (`assetCode`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Cryptobank`.`AssetOfCustomer`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Cryptobank`.`AssetOfCustomer` (
  `assetCode` VARCHAR(3) NOT NULL,
  `userId` INT NOT NULL,
  `quantityOfAsset` DOUBLE NOT NULL,
  PRIMARY KEY (`assetCode`, `userId`),
  INDEX `verzinzelf5_idx` (`assetCode` ASC) VISIBLE,
  INDEX `verzinzelf4_idx` (`userId` ASC) VISIBLE,
  CONSTRAINT `verzinzelf5`
    FOREIGN KEY (`assetCode`)
    REFERENCES `Cryptobank`.`Asset` (`assetCode`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `verzinzelf4`
    FOREIGN KEY (`userId`)
    REFERENCES `Cryptobank`.`User` (`userId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Cryptobank`.`TransactionCosts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Cryptobank`.`TransactionCosts` (
  `transactionCosts` DOUBLE NOT NULL,
  PRIMARY KEY (`transactionCosts`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Cryptobank`.`AssetRateHistory`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Cryptobank`.`AssetRateHistory` (
  `dateTime` DATETIME NOT NULL,
  `assetCode` VARCHAR(3) NOT NULL,
  `rate` DOUBLE NOT NULL,
  PRIMARY KEY (`dateTime`, `assetCode`),
  INDEX `verzinzelf6_idx` (`assetCode` ASC) VISIBLE,
  CONSTRAINT `verzinzelf6`
    FOREIGN KEY (`assetCode`)
    REFERENCES `Cryptobank`.`Asset` (`assetCode`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Cryptobank`.`AssetOfCustomerHistory`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Cryptobank`.`AssetOfCustomerHistory` (
  `dateTime` DATETIME NOT NULL,
  `assetCode` VARCHAR(3) NOT NULL,
  `userId` INT NOT NULL,
  `quantityOfAsset` DOUBLE NOT NULL,
  PRIMARY KEY (`dateTime`, `assetCode`, `userId`),
  INDEX `verzinzelf7_idx` (`assetCode` ASC, `userId` ASC) VISIBLE,
  CONSTRAINT `verzinzelf7`
    FOREIGN KEY (`assetCode` , `userId`)
    REFERENCES `Cryptobank`.`AssetOfCustomer` (`assetCode` , `userId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Cryptobank`.`TriggerTransaction`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Cryptobank`.`TriggerTransaction` (
  `assetCode` VARCHAR(3) NOT NULL,
  `userId` INT NOT NULL,
  `sellYesNo` TINYINT NOT NULL,
  `triggerRate` DOUBLE NOT NULL,
  `quantityToBuyOrSell` DOUBLE NOT NULL,
  PRIMARY KEY (`assetCode`, `userId`, `sellYesNo`),
  INDEX `verzinzelf9_idx` (`assetCode` ASC) VISIBLE,
  INDEX `verzinzelf3_idx` (`userId` ASC) VISIBLE,
  CONSTRAINT `verzinzelf9`
    FOREIGN KEY (`assetCode`)
    REFERENCES `Cryptobank`.`Asset` (`assetCode`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `verzinzelf3`
    FOREIGN KEY (`userId`)
    REFERENCES `Cryptobank`.`User` (`userId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Cryptobank`.`TransactionHistory`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Cryptobank`.`TransactionHistory` (
  `transactionId` INT NOT NULL AUTO_INCREMENT,
  `quantity` DOUBLE NOT NULL,
  `rateInEuro` DOUBLE NOT NULL,
  `dateTime` DATETIME NOT NULL,
  `transactionCosts` DOUBLE NOT NULL,
  `buyerId` INT NOT NULL,
  `sellerId` INT NOT NULL,
  `assetCode` VARCHAR(3) NOT NULL,
  PRIMARY KEY (`transactionId`),
  INDEX `verzinzelf10_idx` (`sellerId` ASC) VISIBLE,
  INDEX `verzinzelf11_idx` (`assetCode` ASC) VISIBLE,
  CONSTRAINT `verzinzelf8`
    FOREIGN KEY (`buyerId`)
    REFERENCES `Cryptobank`.`User` (`userId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `verzinzelf10`
    FOREIGN KEY (`sellerId`)
    REFERENCES `Cryptobank`.`User` (`userId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `verzinzelf11`
    FOREIGN KEY (`assetCode`)
    REFERENCES `Cryptobank`.`Asset` (`assetCode`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Cryptobank`.`Profile`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Cryptobank`.`Profile` (
  `userName` VARCHAR(80) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `userId` INT NOT NULL,
  UNIQUE INDEX `userName_UNIQUE` (`userName` ASC) VISIBLE,
  PRIMARY KEY (`userId`),
  CONSTRAINT `verzinzelf1`
    FOREIGN KEY (`userId`)
    REFERENCES `Cryptobank`.`User` (`userId`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
