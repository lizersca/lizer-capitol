FROM openjdk:17-jdk-slim

COPY target/boot-0.0.1-SNAPSHOT.jar /lizer-capitol.jar

ENTRYPOINT [ "java", "-jar", "-XX:+AllowRedefinitionToAddDeleteMethods", "/lizer-capitol.jar" ]

EXPOSE 8090
