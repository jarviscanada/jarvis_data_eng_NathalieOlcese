# SpringBoot app

## Introduction
A trading app was designed. This app is scalable . The system has the ability to let users fetch stock infotmation for diffetent companies and perform operations quotes. User can create an account on order to use the tradingapp. The technologues user whete java SpringBoot,Maven and Docker.

## Quick Start
In order to run this Docker has to be installed . An IEX Cloud API key is also imperative  so the most updayed stock iten can be feteched to the app then it in otder to create a docker imahe the psql directory enter the command

```
docker build -t trading-psql .
docker image ls -f reference=trading-psql
```
Then to build the trading app in the root directory enter the command

```
docker build -t trading-app .
docker image ls -f reference=trading-psql
```

Then to construct a cocker a docker network use the command 

```
docker network create --driver bridge trading-net

```
and to tun the docker containets enter the commands

```

docker run -d --name trading-psql \
-e POSTGRES_USER=$PSQL_USER \
-e POSTGRES_PASSWORD=$PSQL_PASSWORD \
-v pgdata:$PGDATA_PATH \
--network trading-net -p 5432:5432 trading-psql
docker run -d --name trading-app-dev \
-e "PSQL_HOST=trading-psql" \
-e "PSQL_PORT=5432" \
-e "PSQL_DB=jrvstrading" \
-e "PSQL_USER=$PSQL_USER" \
-e "PSQL_PASSWORD=$PSQL_PASSWORD" \
-e "IEX_PUB_TOKEN=$IEX_PUB_TOKEN" \
--network trading-net \
-p 8080:8080 -t trading-app

```

After an app like Postman can be used to send a request to the tradinn app. The API is accesed using swagger.

## Implementation

### Architecture

In the app first a rquest us received by the server that has the WebServlet container and environment so thejava code executes



