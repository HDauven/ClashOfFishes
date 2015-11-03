DELIMITER $$
DROP PROCEDURE IF EXISTS getUser$$

CREATE PROCEDURE getUser(IN 	p_username	VARCHAR(100),
						OUT p_id INT(10),
						OUT p_email VARCHAR(100))
BEGIN

SELECT User_ID, Email
INTO p_id, p_email
FROM `User`
WHERE Username = p_username;

END
