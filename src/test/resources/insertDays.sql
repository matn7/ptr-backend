--insert plan
insert into plan(id, name) values(1,'Basic');
--insert user
insert into user_entity(id,username,password,email,first_name,last_name,enabled,plan_id) values(1,'user_123'
,'P@ssw0rd!','username@pandatronik.com','majka','majka',1,1);
--insert days
insert into days(id,body,rate_day,posted_on,start_date) values(1,'Days Entry',0,'2024-03-24 18:27:41.000000','2024-03-01');