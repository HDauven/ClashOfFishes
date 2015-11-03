-- -----------------------------------
-- Drop tables if they exist already--
-- -----------------------------------

DROP TABLE IF EXISTS `Highscores`;
DROP TABLE IF EXISTS `User`;
DROP TABLE IF EXISTS `Gamemode`;

-- -----------------------------------
-- -----------------------------------


-- --------------------------------------------------------
-- Create tables based on the Entity Relationship Diagram--
-- --------------------------------------------------------

CREATE TABLE IF NOT EXISTS `User` (
--	Field Identifiers:		Datatypes:		In-line constraints:

	`User_ID` 				INT(255)		UNSIGNED
											AUTO_INCREMENT
											PRIMARY KEY,
											
	
	`Username`				VARCHAR(255)	UNIQUE,
	`Password`				VARCHAR(255),
	`Email`					VARCHAR(255)	UNIQUE
	
);

CREATE TABLE IF NOT EXISTS `Gamemode` (
--	Field Identifiers:		Datatypes:		In-line constraints:

	`Gamemode_ID`			INT(255)		UNSIGNED
											AUTO_INCREMENT
											PRIMARY KEY,
											

	`Name`					VARCHAR(255)	UNIQUE,
	`Description`			VARCHAR(255)
	
);

CREATE TABLE IF NOT EXISTS `Highscores` (
--	Field Identifiers:		Datatypes:		In-line constraints:

	`User_ID` 				INT(255)		UNSIGNED,
	`Gamemode_ID`			INT(255)		UNSIGNED,
	
	`Score`					INT(255)		UNSIGNED,
	
--	Out-line contraints:

	CONSTRAINT Highscore_Identifier 			PRIMARY KEY (`User_ID`, `Gamemode_ID`), -- Unique combination of User_ID and Gamemode_ID acts as Primary Key
	
	CONSTRAINT Highscore_User_ID_Reference 		FOREIGN KEY (`User_ID`)
												REFERENCES `User` (`User_ID`),
											
	CONSTRAINT Highscore_Gamemode_ID_Reference 	FOREIGN KEY (`Gamemode_ID`)
												REFERENCES `Gamemode` (`Gamemode_ID`)
);

-- --------------------------------------------------------
-- --------------------------------------------------------