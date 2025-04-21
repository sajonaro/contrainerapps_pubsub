
FROM adoptopenjdk/openjdk11 AS build 
WORKDIR /app 
COPY . . 
RUN apt-get update && apt-get install -y maven
RUN mvn clean package

CMD ["mvn", "-X", "exec:java", "-Dexec.mainClass=PublisherService"]