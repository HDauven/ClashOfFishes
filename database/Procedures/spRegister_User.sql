/* Set the custom DELITIMER to $$ */
DELIMITER $$

/* Drop the procedure if it already exists within the database */
DROP PROCEDURE IF EXISTS spRegister_User$$

/* Create the new procedure */
CREATE PROCEDURE spRegister_User(IN p_username VARCHAR(255),
							IN p_password VARCHAR(255),
							IN p_email VARCHAR(255),
							OUT r_userAdded BOOLEAN
							)					
BEGIN
	/* Declare temp. variables */
	DECLARE foundUser_ID INT(255);
	
	/* Set default values to variables */
	SET r_userAdded = false;
	SET foundUser_ID = 0;
	
	/* Look for an existing user by comparing user names and email addresses */
		/* If an existing user exists within the database return its primary key */
	SELECT `User_ID` INTO foundUser_ID
	FROM `User`
	WHERE (Username IN (p_username, p_email) OR Email IN (p_username, p_email));
	
	/* If we found an existing user we set the userAdded BOOLEAN to false */
	IF foundUser_ID > 0 THEN
		SET r_userAdded = false;
	/* Else if the foundUser_ID is default we set the userAdded to TRUE and add the record into the database */
	ELSEIF foundUser_ID = 0 THEN
		INSERT INTO `User` (`Username`, `Password`, `Email`) VALUES (p_username, p_password, p_email);
		INSERT INTO `Highscores` (`User_ID`, `Gamemode_ID`, `Score`) VALUES ((SELECT `User_ID` FROM `User` WHERE `Username` = p_username), (SELECT `Gamemode_ID` FROM `Gamemode` WHERE `Name` = 'LAST_FISH_STANDING'), 0);
		INSERT INTO `Highscores` (`User_ID`, `Gamemode_ID`, `Score`) VALUES ((SELECT `User_ID` FROM `User` WHERE `Username` = p_username), (SELECT `Gamemode_ID` FROM `Gamemode` WHERE `Name` = 'EVOLUTION_OF_TIME'), 0);
		INSERT INTO `Highscores` (`User_ID`, `Gamemode_ID`, `Score`) VALUES ((SELECT `User_ID` FROM `User` WHERE `Username` = p_username), (SELECT `Gamemode_ID` FROM `Gamemode` WHERE `Name` = 'EVOLVED'), 0);
		SET r_userAdded = true;
	END IF;
	
/* End of procedure */
END$$

/* Set the DELIMITER back to its default ";" */
DELIMITER ;
