FROM maven:3.9-eclipse-temurin-23 as builder

WORKDIR /app

COPY pom.xml ./

RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn clean package -DskipTests=false

FROM eclipse-temurin:23.0.1_11-jdk

ENV SPRING_DATASOURCE_URL=""
ENV SPRING_DATASOURCE_USERNAME=""
ENV SPRING_DATASOURCE_PASSWORD=""

WORKDIR /app

COPY --from=builder /app/target/agenda-0.0.1-SNAPSHOT.jar /app/

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/agenda-0.0.1-SNAPSHOT.jar"]