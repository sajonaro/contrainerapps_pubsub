FROM eclipse-temurin:23

WORKDIR /app 
COPY ./target/dapr-pubsub-publisher-*.jar /app/app.jar

CMD exec java $([ "$DEBUG_ON" = "yes" ] && echo -agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=0.0.0.0:5005) -jar /app/app.jar