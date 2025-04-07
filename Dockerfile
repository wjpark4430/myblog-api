# 1단계: JAR 빌드
FROM maven:3.9.6 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# 2단계: 실행 환경 구성
FROM openjdk:17-jdk
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
ENTRYPOINT ["java","-Xms128m", "-Xmx256m", "-XX:+UseSerialGC", "-jar", "app.jar"]
