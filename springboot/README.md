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
[https://www.figma.com/file/O4VV6XFgDJLDl5Xa6e7QqS/Spring_Boot_Architecture?type=design&node-id=0%3A1&mode=design&t=PnF04GrDF7frZSix-1]
In the app first a rquest us received by the server that has the WebServlet container and environment so thejava code executes. The request is passed by oen of the controllers that is mapped to receipe the type of reciveing request in the controller layer. The controller then hanldes the rquest by calling the needed services to accomplish thr rquest and send the reponnse to the user.

the service are invoked from the service layer. It provides abastraction to the class that performs operations with the model. The services call their DAO componet and manahe that model.

The DAO comunicates with the dtavase API . JDBCCrudDao is an abstract object that has abstracted the common logic out of all the other DAOs. The MarketDataDao comunivates with  IEX Cloud to fetch the most uodated ypdated stock information.For this DAO, it uses an HTTP Client object to assist in making the request to IEX Cloud API using an API key.

### REST API Usage

### Swagger
Swagger is an inteface that let users acces an application in the browser 

### Quote controller
users can get the most upfate information from the quotes by using the quote controller . Users can add tickets. . The market data itself is coming from the IEX Cloud APIs, and the data is cached in a Postgres database.

Endpoints:
* GET /iex/ticker/{ticker}: Get the latest stock information for the given ticker
* PUT /iexMarketData: Update the stock information for every quote in the daily list
* PUT /: Add the given quote to the daily list
* POST /tickerId/{tickerId}: Get the quote for the given ticker from IEX Cloud and save the information in the daily list
* GET /dailyList: Get the daily list

### Trader Controller
The trader contoller allows users to manage traders and account information to mange funds in an account from a trader.

Endpoints:
* POST /firstname/{firstname}/lastname/{lastname}/dob/{dob}/country/{country}/email/{email}: Add a new trader to the database with the given information.
* POST /: Add a new trader to the database with the given information from the request's body in the JSON format.
* DELETE /traderId/{traderId}: Delete the trader with the given ID, as long as the account is empty, and it has no open positions.
* PUT /deposit/traderId/{traderId}/amount/{amount}: Deposit funds to a trader's account using the supplied trader's ID and amount fields.
* PUT /withdraw/traderId/{traderId}/amount/{amount}: Withdraw funds from a trader's account using the supplied trader's ID and amount fields.

## Test
The application is tested using JUnit. Integrestiosn test wehre perfomerd for every level . The code coverage is 80%

### Deployment 
Docker contaneris the Postgres database an the application

The Postgres database container was made using the postgres:9.6-alpine image, and two SQL scripts were copied to its docker-entrypoint-initdb.d directory so that Postgres would load the files if it finds that its database is empty.

The trading app container was made by moving our source code into a maven:3.6-jdk-8-slim image, and get Maven to package our application. Then we the produced Jar file was moved to an openjdk:8-alpine image and set its entry point to execute Jar file in a JVM, which will run the webserver.

## Improvements

1. implement the order and the dashboard component
2. Implement the app with JPA




