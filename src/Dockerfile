# Comentário de documentação no início do Dockerfile; não altera o comportamento de build/run
FROM eclipse-temurin:17-jdk AS build
WORKDIR /workspace
COPY . .
RUN sed -i 's/\r$//' mvnw && chmod +x mvnw && ./mvnw -DskipTests clean package

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /workspace/target/arena-api-0.0.1-SNAPSHOT.jar app.jar
ENV JAVA_OPTS="-Xms256m -Xmx512m -XX:+UseG1GC -XX:MaxMetaspaceSize=128m"
EXPOSE 8080
CMD ["bash", "-lc", "java $JAVA_OPTS -jar app.jar"]

