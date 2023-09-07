# pandatronik setup

## Setup database

- Fill calendar entity table

| id | calendar_date |
|---|---|
| 1 | 1980-01-01 |
| ... | ... |
| 22281 | 2040-12-32 |

```sql
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
```

- Create plan and role tables.

```sql
INSERT INTO plan VALUES ('1', 'Basic');
INSERT INTO plan VALUES ('2', 'Pro');

INSERT INTO role VALUES (1, 'ROLE_BASIC');
INSERT INTO role VALUES (2, 'ROLE_PRO');
INSERT INTO role VALUES (3, 'ROLE_ADMIN');

COMMIT;
```

- MySQL account. To not use root user. Only described for DEV

```sql
flush privileges;

DROP user 'pandatronik-dev-user'@'localhost';

SHOW VARIABLES LIKE 'validate_password%';

SET GLOBAL validate_password.length = 10;
SET GLOBAL validate_password.number_count = 3;
SET GLOBAL validate_password.policy = 2;
SET GLOBAL validate_password.special_char_count = 0;

CREATE USER 'ptrdevuser'@'localhost' IDENTIFIED BY '1yvS8lEnD5';

GRANT SELECT ON pandatronik_dev.* to 'ptrdevuser'@'localhost';
GRANT INSERT ON pandatronik_dev.* to 'ptrdevuser'@'localhost';
GRANT DELETE ON pandatronik_dev.* to 'ptrdevuser'@'localhost';
GRANT UPDATE ON pandatronik_dev.* to 'ptrdevuser'@'localhost';
```

- Activate dummy user

```sql
UPDATE `pandatronik_dev`.`user_entity` SET `enabled` = 1 WHERE (`id` = 1);
```

***

## UI part

***

## Backend part

- Manage user

- Manage application

| Application | Http Method | Endpoint | Action |
|---|---|---|---|
| Days | GET | ${api.version}/users/{username}/days/{id} | find day by id |
| Days | GET | ${api.version}/users/{username}/days/{year}/{month}/{day} | find day by full date yyyy/MM/dd |
| Days | POST | ${api.version}/users/{username}/days/ | create day |
| Days | PUT | ${api.version}/users/{username}/days/{id} | update day |
| Days | DELETE | ${api.version}/users/{username}/days/{id} | delete day by id |
| ImportantIndex | GET | ${api.version}/users/{username}/important/{year}/{month} | important index view |
| Important1 | GET | ${api.version}/users/{username}/important/1/{id} | find important1 by id |
| Important1 | GET | ${api.version}/users/{username}/important/1/{year}/{month}/{day} | find important1 by full date yyyy/MM/dd |
| Important1 | POST | ${api.version}/users/{username}/important/1 | create important1 |
| Important1 | PUT | ${api.version}/users/{username}/important/1/{id} | update important1 |
| Important1 | DELETE | ${api.version}/users/{username}/important/1/{id} | delete important1 |
| Important2 | GET | ${api.version}/users/{username}/important/2/{id} | find important2 by id |
| Important2 | GET | ${api.version}/users/{username}/important/2/{year}/{month}/{day} | find important2 by full date yyyy/MM/dd |
| Important2 | POST | ${api.version}/users/{username}/important/2 | create important2 |
| Important2 | PUT | ${api.version}/users/{username}/important/2/{id} | update important2 |
| Important2 | DELETE | ${api.version}/users/{username}/important/2/{id} | delete important2 |
| Important3 | GET | ${api.version}/users/{username}/important/3/{id} | find important3 by id |
| Important3 | GET | ${api.version}/users/{username}/important/3/{year}/{month}/{day} | find important3 by full date yyyy/MM/dd |
| Important3 | POST | ${api.version}/users/{username}/important/3 | create important3 |
| Important3 | PUT | ${api.version}/users/{username}/important/3/{id} | update important3 |
| Important3 | DELETE | ${api.version}/users/{username}/important/3/{id} | delete important3 |
| LessImportantIndex | GET | ${api.version}/users/{username}/lessimportant/{year}/{month} | less important index view |

- ImportantIndex - retrieves all data from Days, Important1, Important2, Important3, Remarkable based on
selected month and year

- Statistics



## Angular

```console
cd /home/matikomp/pandatronik/ptr-ui/src/app
ng g c statistics/statistics-start-end --module app
```

### Angular Material

```console
ng add @angular/material
```

**update package.json**

- Modify package.json.

```console
ng update

ng g m Shared
ng g c shared/Input

ng g m Days --routing
ng g c days/DaysCreate
ng g c days/DaysHome

ng g m Auth --routing
ng g c auth/Signin
ng g c auth/Signup
ng g s auth/Auth

ng g s shared/ErrorHandle
ng g s auth/HttpInterceptorAuth
ng g s auth/RouteGuard

ng g m Index --routing
ng g c index/Important
ng g c index/LessImportant
ng g c index/ImportantCreate
```
