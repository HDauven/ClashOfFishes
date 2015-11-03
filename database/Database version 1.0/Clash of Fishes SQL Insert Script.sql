-- ---------------------------------------------------------------------------------------------------------------
-- Document information
-- ---------------------------------------------------------------------------------------------------------------

-- This insert script contains dummy data for the Clash of Fishes project
	-- Use this after creating the tables using the "Clash of Fishes SQL Create Script.sql"
	-- This file contains the definition of 3 tables:
		
		-- Table: User 
		-- Contains: User_ID (Auto incremented Primary Key)
		-- 			 Username
		--			 Password 
		--			 Email
		
		-- Table: Gamemode 
		-- Contains: Gamemode_ID (Auto incremented Primary Key)
		-- 			 Name
		--			 Description 
		
		-- Table: Highscores 
		-- Contains: User_ID (Primary in combination with Gamemode_ID) & (References table User.User_ID)
		-- 			 Gamemode_ID (Primary in combination with User_ID) & (References table Gamemode.Gamemode_ID)
		--			 Password 
		--			 Email
	
-- ---------------------------------------------------------------------------------------------------------------
-- ---------------------------------------------------------------------------------------------------------------

-- ---------------------------------------------------------------------------------------------------------------
-- Insert statements 
-- ---------------------------------------------------------------------------------------------------------------

INSERT INTO `User` (`Username`, `Password`, `Email`) VALUES ('MuK', 'test', 'ChristianAdkin@hotmail.com');
INSERT INTO `User` (`Username`, `Password`, `Email`) VALUES ('HDauven', 'test', 'HeinDauven@Gmail.com');
INSERT INTO `User` (`Username`, `Password`, `Email`) VALUES ('Stef', 'test', 'StefPhilipsen@gmail.com');
INSERT INTO `User` (`Username`, `Password`, `Email`) VALUES ('Admin', 'admin', 'Admin@ClashOfFishes.com');
	
INSERT INTO `Gamemode` (`Name`, `Description`) VALUES ('LAST_FISH_STANDING', '<beschrijving>');
INSERT INTO `Gamemode` (`Name`, `Description`) VALUES ('EVOLUTION_OF_TIME', '<beschrijving>');
INSERT INTO `Gamemode` (`Name`, `Description`) VALUES ('EVOLVED', '<beschrijving>');

INSERT INTO `Highscores` (`User_ID`, `Gamemode_ID`, `Score`) VALUES ((SELECT `User_ID` FROM `User` WHERE `Username` = 'MuK'), (SELECT `Gamemode_ID` FROM `Gamemode` WHERE `Name` = 'LAST_FISH_STANDING'), 100);
INSERT INTO `Highscores` (`User_ID`, `Gamemode_ID`, `Score`) VALUES ((SELECT `User_ID` FROM `User` WHERE `Username` = 'MuK'), (SELECT `Gamemode_ID` FROM `Gamemode` WHERE `Name` = 'EVOLUTION_OF_TIME'), 100);
INSERT INTO `Highscores` (`User_ID`, `Gamemode_ID`, `Score`) VALUES ((SELECT `User_ID` FROM `User` WHERE `Username` = 'MuK'), (SELECT `Gamemode_ID` FROM `Gamemode` WHERE `Name` = 'EVOLVED'), 100);

INSERT INTO `Highscores` (`User_ID`, `Gamemode_ID`, `Score`) VALUES ((SELECT `User_ID` FROM `User` WHERE `Username` = 'HDauven'), (SELECT `Gamemode_ID` FROM `Gamemode` WHERE `Name` = 'LAST_FISH_STANDING'), 80);
INSERT INTO `Highscores` (`User_ID`, `Gamemode_ID`, `Score`) VALUES ((SELECT `User_ID` FROM `User` WHERE `Username` = 'HDauven'), (SELECT `Gamemode_ID` FROM `Gamemode` WHERE `Name` = 'EVOLUTION_OF_TIME'), 80);
INSERT INTO `Highscores` (`User_ID`, `Gamemode_ID`, `Score`) VALUES ((SELECT `User_ID` FROM `User` WHERE `Username` = 'HDauven'), (SELECT `Gamemode_ID` FROM `Gamemode` WHERE `Name` = 'EVOLVED'), 80);

INSERT INTO `Highscores` (`User_ID`, `Gamemode_ID`, `Score`) VALUES ((SELECT `User_ID` FROM `User` WHERE `Username` = 'Stef'), (SELECT `Gamemode_ID` FROM `Gamemode` WHERE `Name` = 'LAST_FISH_STANDING'), 60);
INSERT INTO `Highscores` (`User_ID`, `Gamemode_ID`, `Score`) VALUES ((SELECT `User_ID` FROM `User` WHERE `Username` = 'Stef'), (SELECT `Gamemode_ID` FROM `Gamemode` WHERE `Name` = 'EVOLUTION_OF_TIME'), 60);
INSERT INTO `Highscores` (`User_ID`, `Gamemode_ID`, `Score`) VALUES ((SELECT `User_ID` FROM `User` WHERE `Username` = 'Stef'), (SELECT `Gamemode_ID` FROM `Gamemode` WHERE `Name` = 'EVOLVED'), 60);

INSERT INTO `Highscores` (`User_ID`, `Gamemode_ID`, `Score`) VALUES ((SELECT `User_ID` FROM `User` WHERE `Username` = 'Admin'), (SELECT `Gamemode_ID` FROM `Gamemode` WHERE `Name` = 'LAST_FISH_STANDING'), 0);
INSERT INTO `Highscores` (`User_ID`, `Gamemode_ID`, `Score`) VALUES ((SELECT `User_ID` FROM `User` WHERE `Username` = 'Admin'), (SELECT `Gamemode_ID` FROM `Gamemode` WHERE `Name` = 'EVOLUTION_OF_TIME'), 0);
INSERT INTO `Highscores` (`User_ID`, `Gamemode_ID`, `Score`) VALUES ((SELECT `User_ID` FROM `User` WHERE `Username` = 'Admin'), (SELECT `Gamemode_ID` FROM `Gamemode` WHERE `Name` = 'EVOLVED'), 0);

-- ---------------------------------------------------------------------------------------------------------------
-- ---------------------------------------------------------------------------------------------------------------

-- AUTO JOIN VOOR SCORE PER GAMEMODE PER PERSOON

SELECT g.`Name`, h.`Score`
FROM `User` u, `Highscores` h, `Gamemode` g
WHERE u.User_ID = h.User_ID AND h.Gamemode_ID = g.Gamemode_ID;

SELECT u.`Username`, g.`Name`, h.`Score` AS Score
FROM `User` u, `Highscores` h, `Gamemode` g
WHERE u.User_ID = h.User_ID AND h.Gamemode_ID = g.Gamemode_ID
ORDER BY Score DESC;