FROM openjdk:14-alpine
COPY target/challenge-micronaut-*.jar challenge-micronaut.jar
EXPOSE 8080
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "challenge-micronaut.jar"]