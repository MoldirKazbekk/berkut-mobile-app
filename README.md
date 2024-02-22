# berkut-mobile-app
**to launch Postgres container:**
docker  container run --detach 
--name test-postgres-container 
-p 5433:5432 
-e POSTGRES_PASSWORD=password 
-e POSTGRES_USER=Moldir 
-e  POSTGRES_DB=berkut 
-d postgres:latest

**build the image for berkut app:** 
docker build -t berkut-app .

**build the berkut app container:**
docker container run 
--name=berkut-app-container 
-p 8090:8080 
--link test-postgres-container:postgres 
-d --rm 6dd8537ded27

FROM gradle:8.6.0-jdk17 as build
WORKDIR /app
COPY . .
RUN ./gradlew build

FROM openjdk:17
WORKDIR /
COPY --from=build /app/build/libs/berkut-app-0.0.1-SNAPSHOT.jar /
EXPOSE 8080
ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "/berkut-app-0.0.1-SNAPSHOT.jar"]