FROM eclipse-temurin:21-jre-alpine

ARG JAR_FILE=build/libs/*.jar

ENV PROFILES_ACTIVE dev

COPY ${JAR_FILE} app.jar

EXPOSE 8880

ENTRYPOINT ["java", "-Xms512m", "-Xmx512m", "-jar", "/app.jar", "--spring.profiles.active=${PROFILES_ACTIVE}"]
