CREATE DATABASE pandatronik_dev_docker;

ALTER TABLE calendar_entity MODIFY id bigint NOT NULL AUTO_INCREMENT;

DROP PROCEDURE IF EXISTS filldates;
DELIMITER |
CREATE PROCEDURE filldates(dateStart DATE, dateEnd DATE)
BEGIN
  WHILE dateStart <= dateEnd DO
    INSERT INTO calendar_entity (calendar_date) VALUES (dateStart);
    SET dateStart = date_add(dateStart, INTERVAL 1 DAY);
  END WHILE;
END;
|
DELIMITER ;
CALL filldates('1980-01-01','2040-12-31');