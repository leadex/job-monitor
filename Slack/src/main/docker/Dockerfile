FROM jboss/base-jdk:8

USER jboss
WORKDIR /
ADD jobmonitor-slack-1.0.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]