-- -----------------------------------
-- Drop tables if they exist already--
-- -----------------------------------

DROP TABLE IF EXISTS `Highscores`;
DROP TABLE IF EXISTS `User`;
DROP TABLE IF EXISTS `Gamemode`;
DROP TABLE IF EXISTS `Achievement`;
DROP TABLE IF EXISTS `Unlocked_Achievement`;

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

CREATE TABLE IF NOT EXISTS `Achievement` (
--	Field Identifiers:		Datatypes:		In-line constraints:

	`Achievement_ID`		INT(255)		UNSIGNED
											AUTO_INCREMENT
											PRIMARY KEY,
											

	`Name`					VARCHAR(255)	UNIQUE,
	`Description`			VARCHAR(255),
	`Progress_Length`		INT(255)		UNSIGNED
	
);

CREATE TABLE IF NOT EXISTS `Unlocked_Achievement` (
--	Field Identifiers:		Datatypes:		In-line constraints:

	`User_ID` 				INT(255)		UNSIGNED,
	`Achievement_ID`		INT(255)		UNSIGNED,
	
	`Unlocked`				INT(1),
	`Progress`				INT(255)		UNSIGNED,
	
--	Out-line contraints:

	CONSTRAINT Unlocked_Achievement_Identifier  PRIMARY KEY (`User_ID`, `Achievement_ID`), -- Unique combination of User_ID and Achievement_ID acts as Primary Key
	
	CONSTRAINT Achievement_User_ID_Reference 	FOREIGN KEY (`User_ID`)
												REFERENCES `User` (`User_ID`),
											
	CONSTRAINT Achievement_Achievement_ID_Reference 	FOREIGN KEY (`Achievement_ID`)
														REFERENCES `Achievement` (`Achievement_ID`)
);

-- --------------------------------------------------------
-- --------------------------------------------------------