Address Book APP built with Spring Boot 3 with Angular 15
-----

The application is built with java 17, spring boot 3, angular 15, docker/test containers, node 18 and maven 3

install Java using [Jabba](https://github.com/Jabba-Team/jabba) or [SDKMan] (https://github.com/sdkman/sdkman-cli) or manual

install [NodeJs](https://nodejs.org/en) or use [NVM](https://github.com/nvm-sh/nvm)

Install [Maven](https://maven.apache.org/) or use IDE maven builtin 

Install [Docker](https://www.docker.com/)


To run tests (this steps runs test containers to provide mysql):
````
mvn test
````


start the database:

````
docker compose up
````

To run the app:
````
mvn spring-boot:run
````

Frontend
-----

Frontend resources resides in folder:
````
src/main/webapp
````

navigate there and install libraries:
````
npm i
````

to start the app run in the same folder:

````
ng serve
````

Open API file are store at the root folder, filename api.json.

