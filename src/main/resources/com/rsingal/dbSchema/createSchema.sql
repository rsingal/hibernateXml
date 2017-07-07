-- **********************************************************************
-- From User: SYSTEM 
-- **********************************************************************
BEGIN
	BEGIN
		EXECUTE IMMEDIATE 'DROP USER RSINGAL CASCADE';
	EXCEPTION
		WHEN OTHERS THEN
		IF SQLCODE != -1918 THEN
			RAISE;
		END IF;
	END;
	EXECUTE IMMEDIATE 'CREATE USER rsingal IDENTIFIED BY "rsingal" DEFAULT TABLESPACE "USERS" TEMPORARY TABLESPACE "TEMP"';
	EXECUTE IMMEDIATE 'GRANT all privileges to rsingal identified by rsingal';
	EXECUTE IMMEDIATE 'GRANT CONNECT, DBA to rsingal identified by rsingal';
	EXECUTE IMMEDIATE 'ALTER USER rsingal QUOTA UNLIMITED ON USERS';
END;

-- **********************************************************************
-- From User: RSINGAL, Delete data from all tables, Make sure to Commit
-- **********************************************************************
DELETE ADDRESS CASCADE;
DELETE COMPANY CASCADE;
DELETE EMPLOYEE CASCADE;
DELETE ACCOUNT CASCADE;
DELETE TRAINING CASCADE;
DELETE EMP_TRNG_MAPPING CASCADE;
DELETE EMP_TRNG_MAPPING_EXTENDED CASCADE;
COMMIT;

-- **********************************************************************
-- From User: RSINGAL, Create all tables
-- **********************************************************************
-- Table ADDRESS
-- **********************************************************************
CREATE TABLE "ADDRESS" 
	(	"ID_ADDRESS" NUMBER(10,0) NOT NULL ENABLE, 
		"ADDRESS" VARCHAR2(256 BYTE), 
		CONSTRAINT "ADDRESS_PK" PRIMARY KEY ("ID_ADDRESS")
	);
-- **********************************************************************
-- Table COMPANY
-- **********************************************************************
CREATE TABLE "COMPANY" 
	(	"ID_COMPANY" NUMBER(10,0) NOT NULL ENABLE, 
		"COMPANY_NAME" VARCHAR2(32 BYTE) NOT NULL ENABLE,
		CONSTRAINT "COMPANY_PK" PRIMARY KEY ("ID_COMPANY"),
		CONSTRAINT "COMPANY_UK1_COMP_NAME" UNIQUE ("COMPANY_NAME")
	);
-- **********************************************************************
-- Table EMPLOYEE
-- **********************************************************************
CREATE TABLE "EMPLOYEE" 
	(	"ID_EMPLOYEE" NUMBER(10,0) NOT NULL ENABLE, 
		"EMPLOYEE_NAME" VARCHAR2(32 BYTE) NOT NULL ENABLE, 
		"ADDRESS_ID_ADDRESS" NUMBER(10,0) NOT NULL ENABLE, 
		"COMPANY_ID_COMPANY" NUMBER(10,0) NOT NULL ENABLE, 
		CONSTRAINT "EMPLOYEE_PK" PRIMARY KEY ("ID_EMPLOYEE"),
		CONSTRAINT "EMPLOYEE_FK1_ADDRESS" FOREIGN KEY ("ADDRESS_ID_ADDRESS") REFERENCES "ADDRESS" ("ID_ADDRESS") ON DELETE CASCADE ENABLE, 
		CONSTRAINT "EMPLOYEE_FK1_COMPANY" FOREIGN KEY ("COMPANY_ID_COMPANY") REFERENCES "COMPANY" ("ID_COMPANY") ON DELETE CASCADE ENABLE
	);
-- **********************************************************************
-- Table ACCOUNT
-- **********************************************************************
CREATE TABLE "ACCOUNT"
	(	"ID_ACCOUNT" NUMBER(10,0) NOT NULL ENABLE, 
		"BANK_NAME" VARCHAR2(32 BYTE) NOT NULL ENABLE, 
		"ACCOUNT_NUMBER" NUMBER(16,0) NOT NULL ENABLE, 
		"EMPLOYEE_ID_EMPLOYEE" NUMBER(10,0), 
		CONSTRAINT "ACCOUNT_PK" PRIMARY KEY ("ID_ACCOUNT"),
		CONSTRAINT "ACCOUNT_UK1_BANK_NAME_ACCT_NO" UNIQUE ("BANK_NAME", "ACCOUNT_NUMBER"),
		CONSTRAINT "ACCOUNT_FK1_EMPLOYEE" FOREIGN KEY ("EMPLOYEE_ID_EMPLOYEE") REFERENCES "EMPLOYEE" ("ID_EMPLOYEE") ON DELETE CASCADE ENABLE
	);
-- **********************************************************************
-- Table TRAINING
-- **********************************************************************
CREATE TABLE "TRAINING" 
	(	"ID_TRAINING" NUMBER(10,0) NOT NULL ENABLE,
		"TRAINING_NAME" VARCHAR2(32 BYTE) NOT NULL ENABLE,
		CONSTRAINT "TRAINING_PK" PRIMARY KEY ("ID_TRAINING")
	);
-- **********************************************************************
-- Table EMP_TRNG_MAPPING
-- **********************************************************************
CREATE TABLE "EMP_TRNG_MAPPING" 
	(	"ID_EMPLOYEE" NUMBER(10,0) NOT NULL ENABLE, 
		"ID_TRAINING" NUMBER(10,0) NOT NULL ENABLE, 
		CONSTRAINT "EMP_TRNG_MAPPING_PK" PRIMARY KEY ("ID_EMPLOYEE", "ID_TRAINING"),
		CONSTRAINT "EMP_TRNG_MAPPING_FK1_TRAINING" FOREIGN KEY ("ID_TRAINING") REFERENCES "TRAINING" ("ID_TRAINING") ON DELETE CASCADE ENABLE, 
		CONSTRAINT "EMP_TRNG_MAPPING_FK1_EMPLOYEE" FOREIGN KEY ("ID_EMPLOYEE") REFERENCES "EMPLOYEE" ("ID_EMPLOYEE") ON DELETE CASCADE ENABLE
	);
-- **********************************************************************
-- Table EMP_TRNG_MAPPING_EXTENDED
-- **********************************************************************
CREATE TABLE "EMP_TRNG_MAPPING_EXTENDED" 
	(	"ID_EMPLOYEE" NUMBER(10,0) NOT NULL ENABLE, 
		"ID_TRAINING" NUMBER(10,0) NOT NULL ENABLE, 
		"STATUS" NUMBER(1,0) DEFAULT 0 NOT NULL ENABLE, 
		CONSTRAINT "EMP_TRNG_MAPPING_EXTENDED_PK" PRIMARY KEY ("ID_EMPLOYEE", "ID_TRAINING"),
		CONSTRAINT "EMP_TRNG_MAPPING_EXTENDED_FK1" FOREIGN KEY ("ID_EMPLOYEE") REFERENCES "EMPLOYEE" ("ID_EMPLOYEE") ON DELETE CASCADE ENABLE, 
		CONSTRAINT "EMP_TRNG_MAPPING_EXTENDED_FK2" FOREIGN KEY ("ID_TRAINING") REFERENCES "TRAINING" ("ID_TRAINING") ON DELETE CASCADE ENABLE
	);
