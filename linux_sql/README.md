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
* * * * * bash /global/path/to/host_usage.sh localhost 5432 host_agent [username] [password] >> /tmp/host_usage.log 2>&1
```


## Implementation
In order to implement this project first of all a bash script was created. The task of this script is to created a Doocker container than runs PostgresSQL and then stop it. After this step was completed the DDL file was created with 2 dirferent tables inside the host_agent database. The forst table is called host_info that specifies all the hardware spes and host_usage with specifies the usage of the system.To Conclude the host_info.sh and host_usage.sh  files were created so the information can be feteched to the database.

## Architecture
[Architectire image]
(https://www.figma.com/file/2K2vGdPpdeVcKjJer5dW2d/Untitled?type=design&node-id=0%3A1&mode=dev)

## Scripts 
```
1.psql_docker.sh-This scripy creates the jrvs-psql Postgres Docker container, and  also has the ability to start and stop the container.It can create a pgdata volume for the container to store the database.

2.host_info.sh-This script collects the machine hardware specifications such as  memory. After collecting this information it fetches to the Postgres database.

3.host_usage.sh-This script runs every  minute on the host machine, in order to collect information about tje hardware usage, such as the time the machine has been indle.This script is assited by contrab. It will fetch the collected information to the Postgres database.

4.ddl.sql: This SQL  script  contains all the information collected by the bash scripts.
# psql_docker.sh usage:
./psql_docker.sh [create/start/stop] [username] [password]

# host_info.sh usage:
./host_info.sh [host] [port] [database] [username] [password]

# host_usage.sh usage:
./host_usage.sh [host] [port] [database] [username] [password]


```

# Databasa Modeling

All values must be no null

host_info
| Property | Description |
| --- | --- |
| id | Unique id identifyng each machine |
| hostname| Unique hostname of machine |
| cpu_number | Number of cpu's in machine  |
| cpu_architecture| The architecture of the machine |
| cpu_model | The model of the machine |
| cpu_mhz| The clock of the machine in mhz |
| L2_cache | Size of L2 cache in KB |
| total_mem| Amoount of total memory in KB |
| timestamp| Time in UTC when data is collected|

host_usage
| Property | Description |
| --- | --- |
| timestamp| Timestamp in UTC when data was collected |
| host_id| Unique if of host machine |
| memory_free | Amount of free memory in KB  |
| cpu_idle| The percentage of the CPU that is idle|
| cpu_kernel | The model of the CPU that is used by the kernel |
| disk_io| Number of disk I/O the machine has|
| disk_available| T he amount of disk available in MB|

#Test 
This program was test using bash scripts using a birtual machine that runs CentOS7. the outcome of the test was that every script run as expected and colectect and fetched the required information.

#Deployment
The program was deployed in git from the linuc teminal with different git commands.

#Improvemnts

1.One improvement that could have been done is check forst id docker is available and an outcome scenario if docker is not available.
2.Another improvement can be providing a better errror handling






