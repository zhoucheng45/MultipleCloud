# 第一阶段:编译Maven项目
FROM maven:3.6.0-jdk-11-slim AS build
WORKDIR /app

ARG PROJECT_NAME

COPY . .
WORKDIR /app/before
RUN mvn clean package -DskipTests=true


# 第二阶段:构建镜像
FROM openjdk:11-jdk-slim
WORKDIR /app
COPY --from=build /app/before/target/before.jar /app/before.jar
ENTRYPOINT ["java", "-jar", "/app/before.jar"]


