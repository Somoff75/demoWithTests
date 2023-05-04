FROM openjdk:11
ADD target/demoWithTest-0.0.1-SNAPSHOT.jar demo.jar
ENTRYPOINT ["java", "-jar", "demo.jar"]