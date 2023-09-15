# SpringBoot app

## Introduction
A trading app has been designed. This app is scalable. The system has the ability to allow users to fetch stock information for different companies and perform operations on quotes. Users can create an account in order to use the trading app. The technologies used are Java, Spring Boot, Maven, and Docker.

## Quick Start
n order to run this app, Docker has to be installed. An IEX Cloud API key is also imperative so that the most updated stock information can be fetched for the app. To create a Docker image, navigate to the psql directory and enter the command.

```
docker build -t trading-psql .
docker image ls -f reference=trading-psql
```
Then to build the trading app in the root directory enter the command

```
docker build -t trading-app .
docker image ls -f reference=trading-psql
```

Then to construct a Docker network use the command 

```
docker network create --driver bridge trading-net

```
and to run the docker containers enter the commands

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

After an app like Postman can be used to send a request to the trading app. The API is accesed using swagger.

## Implementation

### Architecture
[https://www.figma.com/file/O4VV6XFgDJLDl5Xa6e7QqS/Spring_Boot_Architecture?type=design&node-id=0%3A1&mode=design&t=PnF04GrDF7frZSix-1]
In the app, a request is first received by the server that hosts the WebServlet container and environment, allowing the Java code to execute. The request is then passed to one of the controllers, which are mapped to receive different types of requests in the controller layer. The controller handles the request by calling the necessary services to fulfill the request and sends the response back to the user.

The services are invoked from the service layer, which provides an abstraction for the classes that perform operations with the model. These services call their corresponding DAO components and manage the models.

The DAO communicates with the database API. The JDBCCrudDao is an abstract object that encapsulates common logic shared by all other DAOs. The MarketDataDao communicates with IEX Cloud to fetch the most updated stock information. For this DAO, it uses an HTTP Client object to assist in making requests to the IEX Cloud API, utilizing an API key for authentication.

### REST API Usage

### Swagger
Swagger is an interface that allows users to access an application using a web browser.

### Quote controller

Users can obtain the most up-to-date information from the quotes by utilizing the Quote Controller. Users also have the ability to add tickers. The market data originates from the IEX Cloud APIs, and this data is cached within a Postgres database.

Endpoints:
* GET /iex/ticker/{ticker}: Get the latest stock information for the given ticker
* PUT /iexMarketData: Update the stock information for every quote in the daily list
* PUT /: Add the given quote to the daily list
* POST /tickerId/{tickerId}: Get the quote for the given ticker from IEX Cloud and save the information in the daily list
* GET /dailyList: Get the daily list

### Trader Controller
The Trader Controller enables users to manage trader and account information, allowing them to handle funds within a trader's account.

Endpoints:
* POST /firstname/{firstname}/lastname/{lastname}/dob/{dob}/country/{country}/email/{email}: Add a new trader to the database with the given information.
* POST /: Add a new trader to the database with the given information from the request's body in the JSON format.
* DELETE /traderId/{traderId}: Delete the trader with the given ID, as long as the account is empty, and it has no open positions.
* PUT /deposit/traderId/{traderId}/amount/{amount}: Deposit funds to a trader's account using the supplied trader's ID and amount fields.
* PUT /withdraw/traderId/{traderId}/amount/{amount}: Withdraw funds from a trader's account using the supplied trader's ID and amount fields.

## Test
The application is thoroughly tested using JUnit. Integration tests were performed at every level to ensure comprehensive testing. The code coverage has reached 80%,

### Deployment 

Docker containers include the Postgres database and the application.

The Postgres database container was created using the postgres:9.6-alpine image. Two SQL scripts were copied into its docker-entrypoint-initdb.d directory to be loaded by Postgres if the database is empty.

The trading app container was built by moving the source code into a maven:3.6-jdk-8-slim image and using Maven to package the application. The resulting JAR file was then moved to an openjdk:8-alpine image, and the entry point was set to execute the JAR file in a JVM, which runs the web server.

## Improvements

Implement the order and dashboard components.
Create a graphical user interface (GUI) to present the information.
Implement the application using Java Persistence API (JPA).



