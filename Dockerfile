FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /workspace

COPY pom.xml ./pom.xml
COPY ms-clientes/pom.xml ./ms-clientes/pom.xml
RUN mvn -q -DskipTests dependency:go-offline

COPY . .
RUN mvn -DskipTests -pl ms-clientes -am clean package


FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /workspace/ms-clientes/target/*.jar app.jar
EXPOSE 8030
ENTRYPOINT ["java","-jar","/app/app.jar"]
