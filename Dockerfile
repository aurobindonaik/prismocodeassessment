#Stage 1 - Build the jar and extract the layers
FROM amazoncorretto:11-alpine-jdk as builder
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} application.jar
RUN java -Djarmode=layertools -jar application.jar extract
#ENTRYPOINT ["java","-jar","/app.jar"]
#EXPOSE 8080

#Stage 2 - Build Image from the extracted layers
FROM amazoncorretto:11-alpine-jdk
COPY --from=builder dependencies/ ./
COPY --from=builder snapshot-dependencies/ ./
COPY --from=builder spring-boot-loader/ ./
COPY --from=builder application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]