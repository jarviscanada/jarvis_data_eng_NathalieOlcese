# Linux Cluster Monitoring Agent
## Introduction
In this project a monitoring sytem agent is impleted.The system monitors the host hardware specifaction and its usage and after monitoring them it stores this information in a database. The potential users of this MVP is a company that needs manage a cluster of machines. The information stored in the databas about each node can be veryuseful for the company in order to execute a better resource panning for futre projects .In these project the tools used were Linux command lines,Bash,Docker,crontab,PostsgresSQL, intlliJ IDE and git. The operating system of the virual machine use was centOS 7.

## Quick Start
``` #create and run a psql intance using psql_docker.sh
export PGPASSWORD=[password]
psql -h localhost -U [username] -c "CREATE DATABASE host_agent;"
psql -h localhost -U [username] -d host_agent -f ./sql/ddl.sql

#create the required tables
export PGPASSWORD=[password]
psql -h localhost -U [username] -c "CREATE DATABASE host_agent;"
psql -h localhost -U [username] -d host_agent -f ./sql/ddl.sql

#insert the hardware specs in the database
./scripts/host_info.sh localhost 5432 host_agent [username] [password]

#insert the hardware usage information in the database
./scripts/host_usage.sh localhost 5432 host_agent [username] [password]

#setup Contrab
crontab -e
#this has to be added to run host_usage.sh every minute and dave the data
* * * * * bash /global/path/to/host_usage.sh localhost 5432 host_agent [username] [password] >> /tmp/host_usage.log 2>&1```

