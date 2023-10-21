FROM openjdk:17-jdk

WORKDIR /app

COPY target/labb2-1.0-SNAPSHOT.jar labb2.jar

EXPOSE 8080

CMD ["java", "-jar", "labb2.jar"]
