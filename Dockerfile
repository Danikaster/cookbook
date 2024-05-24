FROM openjdk:21

WORKDIR /app

LABEL authors="daniqued"

COPY /target/cookbook-0.0.1-SNAPSHOT.jar /app/cookbook.jar

ENTRYPOINT ["java", "-jar", "cookbook.jar"]