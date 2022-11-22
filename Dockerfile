FROM openjdk:11
EXPOSE 8080
ADD target/devops-0.1.jar devops-0.1.jar
ENTRYPOINT ["java", "-jar", "/devops-0.1.jar"]
