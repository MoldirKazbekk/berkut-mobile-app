#FROM openjdk:17 as build
#WORKDIR /
#COPY build.gradle .
#COPY settings.gradle .
#COPY gradlew .
#COPY gradlew.bat .
#COPY src src
#RUN gradlew.bat build
#
#FROM openjdk:17
#WORKDIR /
#COPY --from=build /build/libs/berkut-app-0.0.1-SNAPSHOT.jar /
#EXPOSE 8080
#ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "/berkut-app-0.0.1-SNAPSHOT.jar"]
##WARNING!: case-sensitive

FROM gradle:8.5-jdk17 as build
WORKDIR /app
COPY . .
RUN ./gradlew build

FROM openjdk:17
WORKDIR /
COPY --from=build /app/build/libs/berkut-app-0.0.1-SNAPSHOT.jar /
EXPOSE 8080
ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "/berkut-app-0.0.1-SNAPSHOT.jar"]
