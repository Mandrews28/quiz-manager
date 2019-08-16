# Quiz Manager (Back End)

To load the WebbiSkools Quiz Manager, you need to set up both the Front-End (FE) and the Back-End (BE).
This is the BE repository, the FE is available at https://github.com/Mandrews28/quiz-manager-ui
You should start the BE application before starting the FE application

## Database Setup 
Currently the application runs locally only. Therefore the database has to be initialised locally

### Prerequisites
- Have a version of MySQL > 8.0 (If you are downloading it, download it directly and not through a package manager like Homebrew)
- Have the MySQL Server running on your pc

### Steps
In the command line, run:
- Login to mysql with an admin user, i.e. ```$ mysql -u root -p``` and enter the password
- Execute ```mysql> CREATE USER 'quiz-manager'@'%' IDENTIFIED BY 'password';```
- Execute ```mysql> CREATE SCHEMA `quiz-manager` CHARACTER SET utf8 COLLATE utf8_general_ci;```
- Execute ```mysql> GRANT ALL ON `quiz-manager`.* TO 'quiz-manager'@'%';```
- Exit mysql and log back in as the quiz-manager user: ```$ mysql -u quiz-manager -p```

## Running the application
In the command line. Run:

```mvn clean install```

to create the jar file (in the target folder). Then to start the BE application (it is configured to use localhost:8080):

```java -jar target/quiz-manager-0.4.0.jar --DB_USERNAME=quiz-manager --DB_PASSWORD=password --DB_HOSTNAME=localhost```

Now go to the FE application and start that to get the whole quiz manager application running locally

## API information
Visit http://localhost:8080/swagger-ui.html to see Swagger generated documentation on the application.