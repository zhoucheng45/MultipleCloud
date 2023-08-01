# 第一阶段:编译Maven项目
FROM maven:3.6.0-jdk-11-slim AS build
WORKDIR /app

ARG PROJECT_NAME

COPY . .
WORKDIR /app/${PROJECT_NAME}
RUN mvn clean package -DskipTests=true


# 第二阶段:构建镜像
FROM openjdk:11-jdk-slim
WORKDIR /app
ARG PROJECT_NAME
RUN echo ${PROJECT_NAME}
COPY --from=build /app/${PROJECT_NAME}/target/before.jar /app/${PROJECT_NAME}.jar
ENTRYPOINT ["java", "-jar", "/app/${PROJECT_NAME}.jar"]
