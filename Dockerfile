FROM openjdk:17 as builder
WORKDIR /
COPY . .
RUN ./gradlew build

FROM openjdk:17
WORKDIR /
ADD build/libs/berkut-app-0.0.1-SNAPSHOT.jar /
EXPOSE 8080
ENTRYPOINT ["java","-Dspring.profiles.active=dev", "-jar", "/berkut-app-0.0.1-SNAPSHOT.jar"]
#WARNING!: case-sensitive