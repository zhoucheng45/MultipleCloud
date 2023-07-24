# 第一阶段:编译Maven项目
FROM maven:3.6.0-jdk-11-slim AS build
WORKDIR /app
COPY . .
RUN mvn clean package -Dmaven.test.skip=true


# 第二阶段:构建镜像
FROM openjdk:11-jdk-slim
WORKDIR /app
COPY --from=build /app/target/multiple-cloud.jar /app/multiple-cloud.jar
ENTRYPOINT ["java", "-jar", "/app/multiple-cloud.jar"]