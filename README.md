
## Description 

This project explores [dapr](https://docs.dapr.io/), more specifically, `dapr's` [pub sub](https://docs.dapr.io/getting-started/quickstarts/pubsub-quickstart/) capability, applied to 2 microservices ran in docker containers, both written in Java:
- `sender` publishes `order` messages to message bus via  `dapr`, `order processor` (aka subscriber) is subscribed to messages   


## How to demo the PoC

```bash
# stand up the docker compose stack
$ docker compose up -d --build
```


## Explanation

 - docker-compose.yml contains definitions of `sender` and `receiver` services (sender  invokes receiver's 'ping' method), to enable dapr based communication between `sender` and `receiver` for each service we define `service_dapr` container with daprd (a.k.a. sidecar) sharing service container's network via network_mode: {namespace}:{server_name} configuration)    


## issues and howto's

- install azure cli
```bash
$ curl -sL https://aka.ms/InstallAzureCLIDeb | sudo bash
```

- issue with unavailable docker.sock on linux
```bash
 # chown docker.sock 
 $ sudo chown $(whoami):$(whoami) /var/run/docker.sock
```

- to push image to ACR
```bash
$ az login
$ az acr login --name cr123
# generate image
$ docker pull mcr.microsoft.com/mcr/hello-world
# tag the image
$ docker tag mcr.microsoft.com/mcr/hello-world cr123.azurecr.io/samples/hello-world
# push it to ACR
$ docker push cr123.azurecr.io/samples/hello-world
```

- to run a java program (via maven with mojo plugin)
```bash
# e.g if in mojo configuration PublisherService is set as main class
$ mvn compile exec:java -Dexec.mainClass="PublisherService"
```
