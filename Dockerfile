FROM openjdk:17-jdk
EXPOSE 8081
WORKDIR /app
COPY /target/Device_Shop-1.0-SNAPSHOT.jar .
ENTRYPOINT ["java", "-jar", "Device_Shop-1.0-SNAPSHOT.jar"]





