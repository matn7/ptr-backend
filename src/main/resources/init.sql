USE `pandatronik_dev`;
SET foreign_key_checks = 0;

INSERT INTO plan VALUES ('1', 'Basic');
INSERT INTO plan VALUES ('2', 'Pro');

INSERT INTO pandatronik_dev.role VALUES (1, 'ROLE_BASIC');
INSERT INTO pandatronik_dev.role VALUES (2, 'ROLE_PRO');
INSERT INTO pandatronik_dev.role VALUES (3, 'ROLE_ADMIN');

-- very important commit!
COMMIT;

-- matek_1991       matel_1991_2
-- MatekSzmatek1!
SET foreign_key_checks = 1;

UPDATE user_entity SET enabled = 1 WHERE (id = '1');
UPDATE user_entity SET is_not_locked = 1 WHERE (id = '1');