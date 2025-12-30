FROM openjdk:17.0.2-jdk

WORKDIR /app

COPY target/SmartLogiV2-0.0.1-SNAPSHOT.jar app/SmartLogiV2-0.0.1-SNAPSHOT.jar

EXPOSE 8080

CMD ["java","-jar","app/SmartLogiV2-0.0.1-SNAPSHOT.jar"]
