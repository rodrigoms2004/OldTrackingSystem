# Database VMSYS

# Creating database
#create database db_organization;
#connect db_organization;

# creating user database
#grant all privileges on db_organziation.* to 'vmsys'@'localhost' identified by 'vmsyspass';
#flush privileges;

# remote access
# edit file vim /etc/my.conf and add line "bind-address = *"
#grant all privileges on db_organization.* to 'vmsys'@'%' identified by 'vmsyspass';
#flush privileges;
#exit;

# creates table for organization
USE db_organization;
CREATE TABLE ORGANIZATION(
            id INTEGER auto_increment NOT NULL,
            name CHAR(20),
            CNPJ CHAR(30),
            PRIMARY KEY(id)
            );

# creates tables for user's organization
CREATE TABLE USER_ORG_DATA(
            id INTEGER auto_increment NOT NULL,
            name CHAR(20),
            CPF CHAR(20),
            username CHAR(20),
            password CHAR(20),
            id_org INTEGER,
            PRIMARY KEY(id),
            FOREIGN KEY(id_org) REFERENCES ORGANIZATION(id)
            );

# creates customer data table
CREATE TABLE CUSTOMER_DATA(
            id INTEGER auto_increment NOT NULL,
            name CHAR(20),
            CNPJ CHAR(20),
            db_name CHAR(20),
            db_user CHAR(20),
            db_pass CHAR(20),
            db_host CHAR(20),
            id_org INTEGER,
            PRIMARY KEY(id),
            FOREIGN KEY(id_org) REFERENCES ORGANIZATION(id)
            );

# creates customer's users table
CREATE TABLE USER_CUSTOMER_DATA(
            id INTEGER auto_increment NOT NULL,
            name CHAR(20),
            CPF CHAR(20),
            username CHAR(20),
            password CHAR(20),
            id_customer INTEGER,
            PRIMARY KEY(id),
            FOREIGN KEY(id_customer) REFERENCES CUSTOMER_DATA(id)
            );

# DELETE EVERYTHING
DROP TABLE USER_ORG_DATA;
DROP TABLE USER_CUSTOMER_DATA;
DROP TABLE CUSTOMER_DATA;
DROP TABLE ORGANIZATION;



# INSERT some organizations, CNPJ generates from http://www.geradorcnpj.com/
INSERT INTO ORGANIZATION (name, CNPJ) VALUES ('VMSYS', '07.081.137/0001-67');
INSERT INTO ORGANIZATION (name, CNPJ) VALUES ('Integrador', '57.895.770/0001-67');
INSERT INTO ORGANIZATION (name, CNPJ) VALUES ('ClienteXPTO  ', '86.157.600/0001-83');
SELECT * FROM ORGANIZATION;
#DELETE FROM ORGANIZATION WHERE id = 1;

# INSERT some organization users, CPF from http://www.geradorcpf.com/
INSERT INTO USER_ORG_DATA(name, CPF, username, password, id_org) VALUES ('Rodrigo', '306.580.928-12', 'rms', 'rms2015',1);
INSERT INTO USER_ORG_DATA(name, CPF, username, password, id_org) VALUES ('Daniel', '618.562.131-29', 'daniel', 'daniel2015',1);
INSERT INTO USER_ORG_DATA(name, CPF, username, password, id_org) VALUES ('Boleslau', '618.417.209-33', 'boleslau', 'zzz',2);
INSERT INTO USER_ORG_DATA(name, CPF, username, password, id_org) VALUES ('Trofozildo', '361.639.429-57', 'trofozildo', 'yyy',2);
INSERT INTO USER_ORG_DATA(name, CPF, username, password, id_org) VALUES ('Blastonio', '930.545.555-77', 'blastonio', 'vvv',3);
INSERT INTO USER_ORG_DATA(name, CPF, username, password, id_org) VALUES ('Ermerantes', '930.545.555-77', 'ermerantes', 'www',3);
SELECT * FROM USER_ORG_DATA;

# INSERT some CUSTOMERS
INSERT INTO CUSTOMER_DATA(name, CNPJ, db_name, db_user, db_pass, db_host, id_org) VALUES ('Cliente1', '87.401.481/0001-25', 'db_cliente1','cliente1', 'pass1', 'localhost', 1); 
INSERT INTO CUSTOMER_DATA(name, CNPJ, db_name, db_user, db_pass, db_host, id_org) VALUES ('Cliente2', '43.639.835/0001-07', 'db_cliente2','cliente2', 'pass2', 'localhost', 1); 
INSERT INTO CUSTOMER_DATA(name, CNPJ, db_name, db_user, db_pass, db_host, id_org) VALUES ('Cliente3', '31.535.569/0001-85', 'db_cliente3','cliente3', 'pass3', 'localhost', 2); 
INSERT INTO CUSTOMER_DATA(name, CNPJ, db_name, db_user, db_pass, db_host, id_org) VALUES ('Cliente4', '42.338.621/0001-20', 'db_cliente4','cliente4', 'pass4', 'localhost', 2); 
INSERT INTO CUSTOMER_DATA(name, CNPJ, db_name, db_user, db_pass, db_host, id_org) VALUES ('Cliente5', '92.056.227/0001-77', 'db_cliente5','cliente5', 'pass5', 'localhost', 3); 
INSERT INTO CUSTOMER_DATA(name, CNPJ, db_name, db_user, db_pass, db_host, id_org) VALUES ('Cliente6', '89.614.696/0001-50', 'db_cliente6','cliente6', 'pass6', 'localhost', 3); 
SELECT * FROM CUSTOMER_DATA;

# INSERT some customer users, CPF from http://www.geradorcpf.com/
INSERT INTO USER_CUSTOMER_DATA(name, CPF, username, password, id_customer) VALUES ('Rodrigo', '306.580.928-12', 'rms', 'rms2015',1);
INSERT INTO USER_CUSTOMER_DATA(name, CPF, username, password, id_customer) VALUES ('Daniel', '618.562.131-29', 'daniel', 'daniel2015',2);
INSERT INTO USER_CUSTOMER_DATA(name, CPF, username, password, id_customer) VALUES ('Boleslau', '618.417.209-33', 'boleslau', 'zzz',3);
INSERT INTO USER_CUSTOMER_DATA(name, CPF, username, password, id_customer) VALUES ('Trofozildo', '361.639.429-57', 'trofozildo', 'yyy',4);
INSERT INTO USER_CUSTOMER_DATA(name, CPF, username, password, id_customer) VALUES ('Blastonio', '930.545.555-77', 'blastonio', 'vvv',5);
INSERT INTO USER_CUSTOMER_DATA(name, CPF, username, password, id_customer) VALUES ('Ermerantes', '930.545.555-77', 'ermerantes', 'www',6);
SELECT * FROM USER_CUSTOMER_DATA;

SELECT * FROM CUSTOMER_DATA;

