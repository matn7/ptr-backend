USE `pandatronik_user_experiments_db_model`;
SET foreign_key_checks = 0;

INSERT INTO plan VALUES ('1', 'Basic');
INSERT INTO plan VALUES ('2', 'Pro');

INSERT INTO pandatronik_user_experiments_db_model.role VALUES (1, 'ROLE_BASIC');
INSERT INTO pandatronik_user_experiments_db_model.role VALUES (2, 'ROLE_PRO');
INSERT INTO pandatronik_user_experiments_db_model.role VALUES (3, 'ROLE_ADMIN');

COMMIT;

-- matek_1991       matel_1991_2
-- MatekSzmatek1!
SET foreign_key_checks = 1;