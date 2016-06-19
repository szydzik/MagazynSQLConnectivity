# MagazynSQLConnectivity

--------------------------------------------MySQL Script------------------------------------------
/*
Created: 19.06.2016
Modified: 19.06.2016
Model: MySQL 5.6
Database: MySQL 5.6
*/


-- Create tables section -------------------------------------------------

-- Table odpad

CREATE TABLE `odpad`
(
  `ID` Bigint NOT NULL,
  `GRUPA` Int,
  `PODGRUPA` Int,
  `RODZAJ` Int,
  `TYP` Varchar(500),
  `OPIS` Varchar(500)
)
;

ALTER TABLE `odpad` ADD  PRIMARY KEY (`ID`)
;

-- Table magazynp

CREATE TABLE `magazynp`
(
  `ID` Bigint NOT NULL,
  `NR_KARTY` Varchar(20),
  `NR_KLIENTA` Int,
  `FIRMA` Int,
  `JEDN` Varchar(20),
  `MASA` Double,
  `DATAD` Date,
  `ODPAD_ID` Bigint
)
;

CREATE INDEX `IX_Relationship5` ON `magazynp` (`ODPAD_ID`)
;

ALTER TABLE `magazynp` ADD  PRIMARY KEY (`ID`)
;

-- Create relationships section ------------------------------------------------- 

ALTER TABLE `magazynp` ADD CONSTRAINT `Relationship5` FOREIGN KEY (`ODPAD_ID`) REFERENCES `odpad` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
;



-- PROCEDURE ----------------------------Callable Statement-----
DELIMITER $$
DROP PROCEDURE IF EXISTS `magazynpsqlconnectivity`.`getNR_KARTY` $$
CREATE PROCEDURE `magazynpsqlconnectivity`.`getNR_KARTY`
(IN M_ID BIGINT, OUT NR VARCHAR(20))
BEGIN
SELECT NR_KARTY INTO NR
FROM magazynpsqlconnectivity.magazynp
WHERE ID = M_ID;
END $$
DELIMITER ;
