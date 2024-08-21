# 使用官方的 OpenJDK 作为基础镜像
FROM openjdk:17-jdk-slim
# Define a build argument for the version
ARG VERSION=0.0.1-SNAPSHOT
# 设置工作目录
WORKDIR /app

# Use the build argument to copy the JAR file
COPY target/shunwei-oms-${VERSION}.jar /app/shunwei-oms.jar

# Continue with the rest of your Dockerfile
ENTRYPOINT ["java", "-jar", "/app/shunwei-oms.jar"]
