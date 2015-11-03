DELIMITER $$
DROP PROCEDURE IF EXISTS removeUser$$

CREATE PROCEDURE removeUser(IN 	p_userID	INT(100))
BEGIN

DELETE FROM `Highscores`
WHERE User_ID = p_userID;

DELETE FROM `User`
WHERE User_ID = p_userID;

END

call removeUser('Stef');
