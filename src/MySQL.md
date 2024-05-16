# MySQL

**show databases**

show databases;

**create database**

CREATE DATABASE pet_shop;

create database SlimStore;

**delete database**

DROP DATABASE SlimStore;

drop database pet_shop;

**use database**

USE chicken_coop;

SELECT database();

**Create Tables**

```
CREATE TABLE cats 
    (
        name VARCHAR(100),
        age INT
    );
```

SHOW TABLES;

SHOW COLUMNS FROM cats;

DESC cats;

**Deleting Tables**

DROP TABLE dogs;

**INSERT**

```
INSERT INTO cats (name, age) VALUES ('Jetson', 7);
```

SELECT * FROM cats;

**MULTIPLE INSERTS**

```
INSERT INTO cats(name, age)
VALUES ("Meatball",5),
       ("Turkey", 1),
       ("Potato Face", 15);
```

**NOT NULL**

```
CREATE TABLE cats2
    (
        name VARCHAR(100) NOT NULL,
        age INT NOT NULL
    );
```

**DEFAULT VALUES**

```
CREATE TABLE cats4
    (
        name VARCHAR(100) NOT NULL DEFAULT 'mystery',
        age INT DEFAULT 99
    );
```

**Primary Keys**

```
CREATE TABLE unique_cats
    (
        cat_id INT NOT NULL PRIMARY KEY,
        name VARCHAR(100),
        age INT
    );
    
CREATE TABLE unique_cats2
    (
        cat_id INT,
        name VARCHAR(100),
        age INT,
        PRIMARY KEY(cat_id)
    );    
    
CREATE TABLE unique_cats3
    (
        cat_id INT AUTO_INCREMENT,
        name VARCHAR(100),
        age INT,
        PRIMARY KEY(cat_id)
    );       
```

```
CREATE TABLE employees
    (
        id INT AUTO_INCREMENT PRIMARY KEY,
        last_name VARCHAR(100) NOT NULL,
        first_name VARCHAR(100) NOT NULL,
        middle_name VARCHAR(100),
        age INT NOT NULL,
        current_status VARCHAR(100) NOT NULL DEFAULT 'employed'
    );
```

















