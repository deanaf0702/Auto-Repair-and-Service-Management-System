# Auto-Repair-and-Service-Management-System

## Milestone 1 - Report, Due October 3rd at 9 AM

For the first milestone, you should:

1. Fill in the form for deciding on team members as soon as possible.
2. A partial ER-Diagram covering the entity and relationship types and constraints that you have identified so far relevant to Employees, Stores and Services.
3. A translation of your partial E-R model into SQL.
4. Include a table that has two columns. One column has a phrase in the description document that you have identified as a constraint and the other column says how your model so far has captured the constraint e.g. foreign key. You do not need to include those that you have not captured yet since some constraints will require all of SQL to capture.
5. Also, include up to 5 functional dependencies that you have identified so far.

## Milestone 2 - Report, Due November 9th at 9 AM

1. Final ER model (ER.pdf)
2. Two SQL files:
a) Database creation (such as tables, constraints, procedures, etc.) (set_up.sql)
b) Queries for populating the tables with the sample data (populating.sql)
3. Discussion (discussion.pdf)
a) A 1-2 page discussion of your application design, with particular emphasis on how
constraints are captured/implemented and whether your design is 3NF, BCNF. You
should highlight which constraints you were not able to capture in the ER model and how
you eventually captured them in your SQL design using SQL features like CHECK
constraints or whichever features.
b) You can use a table format to show a mapping between constraint and implementation features. For example, you can say, constraint1, constraint2, ... FD1, ...
were implemented as CHECK constraints; constraint4, constraint5 FD2 were
implemented as a foreign key, ... etc (this is just a suggested way to present the
information).
c) Your discussion should also note which advanced SQL features like Procedures,
Triggers etc., you used (if any). Also, note if there were constraints that needed to be
implemented outside the DBMS i.e. in the application code (excluding user interface
functionality).
4. Executable file with source Java Code (execution.JAR)
5. README.txt file that contains the names, unity IDs (NOT STUDENT ID NUMBER) of
the team members and explains any additional instructions on how to compile and
execute your code.
6. Everything should be in a single zip file called project1.zip so that when we unzip it, we
can read the README file, follow the directions, and run your project.

## Running the application

1. Create a database.properties file in the root of the AutoCenter folder

    database.properties should contain:

    ```txt
    username=yourUnityID (or username if not ncsu Oracle db)
    password=yourOraclePassword
    jdbcUrl=yourjdbcUrl
    ```

2. Run the Home.java file to start the application or use start.bat

## Schema Notes

* Change employees ISA hierarchy to users ISA hierarchy that also includes customer - Simplifies login process
* Vacations are solely related to mechanics
* Invoices may be a view, and might not need to be represented as its own entity, as all of the information it is storing comes from other entities

## Project members

* Josh Kersey - jkersey
* Deana Franks - dfranks
* Tyler Enck - tenck
