# hibernateXml
- This project is a demo of hibernate mappings using XML (one to one, one to many, many to one, many to many)

				One to One			One to Many
		Address	 -------					--------- Hardware
				|					|
				---------------- Employee ---------------
				|					|
		Training -------					--------- Company
				Many to Many			Many to One

- Oracle XE-11.2.0 database is used, create schema as defined in "src/main/resources/com/rsingal/dbSchema/createSchema.sql".
- Maven is used for build dependencies.
- Run as Java Application: "src/main/java/com/rsingal/app/HibernateApp.java".

