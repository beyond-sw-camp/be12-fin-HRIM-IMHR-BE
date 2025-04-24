FROM openjdk:17-ea-slim-buster
COPY ./build/libs/breadBook-0.0.1-SNAPSHOT.jar /app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]