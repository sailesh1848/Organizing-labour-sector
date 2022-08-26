FROM maven as build
WORKDIR /app
COPY . /app
RUN mvn -T 16C package -Dmaven.test.skip=true

FROM openjdk:15.0.2-jdk-buster
WORKDIR /app
COPY --from=build /app/target/*.jar /app/app.jar
ENTRYPOINT ["java","-jar","app.jar"]