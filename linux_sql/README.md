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


##Implementation
In order to implement this project first of all a bash script was created. The task of this script is to created a Doocker container than runs PostgresSQL and then stop it. After this step was completed the DDL file was created with 2 dirferent tables inside the host_agent database. The forst table is called host_info that specifies all the hardware spes and host_usage with specifies the usage of the system.To Conclude the host_info.sh and host_usage.sh  files were created so the information can be feteched to the database.

##Architecture

<iframe style="border: 1px solid rgba(0, 0, 0, 0.1);" width="800" height="450" src="https://www.figma.com/embed?embed_host=share&url=https%3A%2F%2Fwww.figma.com%2Ffile%2F2K2vGdPpdeVcKjJer5dW2d%2FUntitled%3Ftype%3Ddesign%26node-id%3D0%253A1%26mode%3Ddesign%26t%3DMxgKKT7rWdr40i7l-1" allowfullscreen></iframe>

##Scripts Description
```
1.psql_docker.sh-This scripy creates the jrvs-psql Postgres Docker container, and  also has the ability to start and stop the container.It can create a pgdata volume for the container to store the database.

2.host_info.sh-This script collects the machine hardware specifications such as  memory. After collecting this information it fetches to the Postgres database.

3.host_usage.sh-This script runs every  minute on the host machine, in order to collect information about tje hardware usage, such as the time the machine has been indle.This script is assited by contrab. It will fetch the collected information to the Postgres database.

4.ddl.sql: This SQL  script  contains all the information collected by the bash scripts.


```
