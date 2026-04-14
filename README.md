JaCoCo code coverage report hosted on [web disk](https://users.metropolia.fi/~sampokl/OhjelmistoTuotantoProjekti/jacoco/).  
Docker image hosted on [Docker Hub](https://hub.docker.com/repository/docker/gasdy/gradebook).  

# Gradebook and Report Card System

This program allows teachers to:
- Manage courses
- Add homework, exams, etc. for students in their courses
- Record and calculate student grades 
- Generate report cards based on their grades

# Technology Stack
- Java
- Maven
- Hibernate
- Jakarta
- SQL
- JaCoCo
- Jenkins
- Docker

## Architecture

### Use Case Diagram
![Use Case](doc/UseCase.png)

Shows how teachers and students interact with the program

### ER Diagram
![ER Diagram](doc/Figures/ER%20diagrams/ER-diagramV1.3.png)


### Database schema
![Database schema Diagram](doc/Figures/ER%20diagrams/database-schemaV1.3.png)
Shows the database structure with entities like Enrollment, Student, Grade, Teacher, Course and Assignment

### Sequence Diagram
![Sequence Diagram](doc/UMLSeq.png)

### Sprint Reports

#### https://github.com/zakke-username/gradebook/tree/main/doc/Sprint%20reports

## How to Demo
To demo the application you are required to populate the application's database (running on the Docker container) manually. To populate database use newest version of sample-data.sql from [/database-SQL](/database-SQL)


## Database localization
The approach taken for database localization is to create new fields into existing tables, wherever translations are needed for each language. This approach was a straightforward choice, the amount of localized strings is fairly small, and the translated database fields can be easily accessed without modifying the source code too much.
For each field to be translated, new column was created to accommodate each language, where a translation is required. The model class getters and setters were modified to access the correct translated string based on current locale.
A modified ER diagram shows the new columns in the database. The database uses UTF-8 encoding.
