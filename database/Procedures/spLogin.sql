/* Set the custom DELITIMER to $$ */
DELIMITER $$

/* This is a complete statement, not part of the procedure, so use the custom delimiter $$ */
DROP PROCEDURE IF EXISTS spLogin$$

/* Now start the procedure code */
CREATE PROCEDURE spLogin(	IN p_userIdentifier VARCHAR(255),
							IN p_password VARCHAR(255),
							OUT r_User_ID INT(255),
							OUT r_userIdentifier VARCHAR(255),
							OUT r_Email VARCHAR(255)
							)					
BEGIN


	/* Inside the procedure, individual statements terminate with ; */
    SET r_User_ID = 0;
	SET r_userIdentifier = '';
	SET r_Email = '';
	SELECT `User_ID` INTO r_User_ID
	FROM `User`
	WHERE (UPPER(Username) = UPPER(p_userIdentifier) OR UPPER(Email) = UPPER(p_userIdentifier))
	AND BINARY Password = BINARY p_password;
	
	IF r_User_ID > 0 THEN
		SELECT `Username`, `Email`
		INTO r_userIdentifier, r_Email
		FROM `User`
		WHERE `User_ID` = r_User_ID;
		
		SELECT g.`Name`, h.`Score`
		FROM `User` u, `Highscores` h, `Gamemode` g
		WHERE u.User_ID = h.User_ID 
		AND h.Gamemode_ID = g.Gamemode_ID
		AND r_User_ID = u.`User_ID`;	
	END IF;
		
	
	
	
/* whole procedure ends with the custom delimiter */
END$$

/* Finally, reset the delimiter to the default ; */
DELIMITER ;
