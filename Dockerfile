FROM gradle:8.6.0-jdk17 as build
ARG ENV
ARG PORT
WORKDIR /app
COPY . .
RUN ./gradlew clean build -x check -x test

FROM openjdk:17
WORKDIR /
COPY --from=build /app/build/libs/berkut-app-0.0.1-SNAPSHOT.jar /
EXPOSE $PORT
ENTRYPOINT ["java", "-Dspring.profiles.active=$ENV", "-jar", "/berkut-app-0.0.1-SNAPSHOT.jar"]