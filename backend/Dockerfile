FROM eclipse-temurin:17-jdk-jammy as build
WORKDIR /opt/app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline
COPY src ./src
RUN ./mvnw clean package -DskipTests=true

FROM eclipse-temurin:17-jre-jammy as run
WORKDIR /opt/build
EXPOSE 8080
COPY --from=build /opt/app/target/*.jar /opt/app/*.jar
ENTRYPOINT ["java", "-jar", "/opt/app/*.jar"]