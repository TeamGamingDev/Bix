FROM openjdk:8-jdk-slim

LABEL maintainer = "biosphere.dev@gmx.de"

COPY target/Bix-1.0-SNAPSHOT-shaded.jar  Bix.jar

ENTRYPOINT ["java", "-jar", "Bix.jar"]
