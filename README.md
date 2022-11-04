# Auto-Repair-and-Service-Management-System

Milestone 1 - Report, Due October 3rd 9AM 
For the first milestone, you should: 
1. Fill in the form for deciding team members as soon as possible. 
2. A partial ER-Diagram covering the entity and relationship types and constraints that you have identified so far relevant to Employees, Stores and Services. 
3. A translation of your partial E-R model into SQL. 
4. Include a table that has two columns. One column has a phrase in the description document that you have identified as a constraint and the other column says how your model so far has captured the constraint e.g. foreign key. You do not need to include those that you have not captured yet since some constraints will require all of SQL to capture. 
5. Also include up to 5 functional dependencies that you have identified so far. 

# Running the application:
1. Create a database.properties file in the root of the AutoCenter folder

database.properties should contain:
```
username=yourUnityID (or username if not ncsu Oracle db)
password=yourOraclePassword
jdbcUrl=yourjdbcUrl
```
2. Run the Home.java file to start the application, or use start.bat

# Schema Notes:
* Change employees ISA hierarchy to users ISA hierarchy that also includes customer - Simplifies login process
* Vacations are solely related to mechanics
* Invoices may be a view, and might not need to be represented as its own entity, as all of the information it is storing comes from other entities

