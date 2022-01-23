FROM adoptopenjdk/openjdk11
ARG JAR_FILE=target/*.jar app.jar
WORKDIR /usr/local/runme
COPY ${JAR_FILE} app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","app.jar"]





