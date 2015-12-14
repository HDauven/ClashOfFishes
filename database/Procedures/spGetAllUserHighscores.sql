/* Set the custom DELITIMER to $$ */
DELIMITER $$

/* Drop the procedure if it already exists within the database */
DROP PROCEDURE IF EXISTS spGetAllUserHighscores$$

/* Create the new procedure */
CREATE PROCEDURE spGetAllUserHighscores(IN p_Gamemode VARCHAR(255))					
BEGIN
	/* Declare temp. variables */ 
	SELECT u.`Username`, g.`Name`, h.`Score` AS Score
	FROM `User` u, `Highscores` h, `Gamemode` g
	WHERE u.User_ID = h.User_ID AND h.Gamemode_ID = g.Gamemode_ID
	AND p_Gamemode = g.`Name`
	ORDER BY Score DESC
	LIMIT 11;	
/* End of procedure */
END$$

/* Set the DELIMITER back to its default ";" */
DELIMITER ;
