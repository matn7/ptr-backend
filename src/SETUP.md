# Pandatronik setup

- Instruction for first time application run.
- Have to configure dedicated user pandatronik-user or something. Do not user root user.

**Create database**

```sql
CREATE DATABASE pandatronik_user_experiments_db_model

CREATE DATABASE pandatronik_user_experiments_db_model
```

**Prepare schema**

- Modify application.properties.

```properties
spring.jpa.generate-ddl=true
# spring.jpa.hibernate.ddl-auto=validate # <--- comment this line
spring.jpa.properties.hibernate.jdbc.time_zone=UTC
spring.jpa.hibernate.ddl-auto=create # <--- uncomment this line
```

- Run application.
- After start immediately stop application, and revert changes in application.properties.

```properties
spring.jpa.generate-ddl=true
# spring.jpa.hibernate.ddl-auto=validate # <--- comment this line
spring.jpa.properties.hibernate.jdbc.time_zone=UTC
spring.jpa.hibernate.ddl-auto=create # <--- uncomment this line
```

**Populate tables with data**

- Populate role and plan tables. Execute sql script.

```sql
INSERT INTO plan VALUES ('1', 'Basic');
INSERT INTO plan VALUES ('2', 'Pro');

INSERT INTO role VALUES (1, 'ROLE_BASIC');
INSERT INTO role VALUES (2, 'ROLE_PRO');
INSERT INTO role VALUES (3, 'ROLE_ADMIN');

COMMIT;
```

# UI

**Pull code from github**

**Navigate to ptr-ui folder**

```console
npm install
```

## Way to update mysql primary key

- Use case after migrating to other db, PK changed for instance last entry n old DB was 2005.
Last entry in new DB was 9, next addition will result with exception as 10 is already in DB.
- Fix change GenerationType.AUTO to GenerationType.IDENTITY.

```java
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
```