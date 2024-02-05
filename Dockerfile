FROM openjdk:20-slim
WORKDIR /app
RUN mkdir /app/config
COPY /target/*.jar /app/app.jar
EXPOSE 8080/tcp
ENTRYPOINT ["java", "-jar", "/app/app.jar"]