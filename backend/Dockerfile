# FROM eclipse-temurin:19.0.2_7-jre-alpine
# WORKDIR /opt/app
# RUN addgroup --system javauser && adduser -S -s /usr/bin/nologin -G javauser javauser
# COPY backend.jar app.jar
# RUN chown -R javauser:javauser .
# USER javauser
# ENTRYPOINT ["java", "-jar", "app.jar"]
FROM maven:3.8.7-openjdk-18-slim AS build-env
COPY ./controller/pom.xml ./pom.xml
ENV POSTGRES_PWD=$POSTGRES_PWD \
    POSTGRES_USR=$POSTGRES_USR
RUN mvn dependency:go-offline -B
COPY ./controller/src ./src
RUN mvn package

FROM eclipse-temurin:19.0.2_7-jre-alpine
COPY --from=build-env /target/controller-0.0.1-SNAPSHOT.jar app.jar
ENV POSTGRES_PWD=$POSTGRES_PWD \
    POSTGRES_USR=$POSTGRES_USR
EXPOSE 8080
ENV POSTGRES_PWD=$POSTGRES_PWD \
    POSTGRES_USR=$POSTGRES_USR
ENTRYPOINT ["java", "-jar", "app.jar"]