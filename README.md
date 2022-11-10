This Project implements the accounts and transactions REST API. 
The below options provides the ability to run this:

Option 1:
- Download/Clone the project from git.
- Change to the downloaded directory.
- Make sure Docker is running in your machine (Possibly Docker Desktop).
- Run the command:  docker build -t prismocodeassessment:latest .
- Then run the command: docker run -p 8080:8080 prismocodeassessment:latest
- Once the applications starts access the API on http://localhost:8080/swagger-ui/

Option 2 (If there is no docker available on your machine):
- Download/Clone the project from git.
- Change to the downloaded directory.
- In Linux based machine run './mvnw clean install' followed by './mvnw spring-boot:run'
- In Windows based machine run './mvnw.cmd clean install' followed by './mvnw.cmd spring-boot:run'
- Once the applications starts access the API on http://localhost:8080/swagger-ui/