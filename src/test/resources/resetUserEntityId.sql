SET  @num := 0;
UPDATE user_entity SET id = @num := (@num+1);
ALTER TABLE user_entity AUTO_INCREMENT =1;

INSERT INTO user_entity(id,username,password,email,first_name,last_name,enabled,plan_id) VALUES(33,'matek_1991','M@jkaM@jka1!','mateusz.nowak@pandatronik.com','majka','majka',1,1);
