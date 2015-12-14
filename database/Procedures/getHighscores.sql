DELIMITER $$
DROP PROCEDURE IF EXISTS getScores$$

CREATE PROCEDURE getScores(IN 	p_userID	INT(10))
BEGIN

SELECT User_ID, Gamemode_ID, Score
FROM `Highscores`
WHERE User_ID = p_userID;

END$$

DELIMITER ;