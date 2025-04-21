
FROM openjdk:17-jdk-slim 
WORKDIR /app 
COPY . . 
RUN apt-get update && apt-get install -y maven
RUN mvn clean install

CMD ["mvn", "-X", "spring-boot:run"]
