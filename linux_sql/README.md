Linux Cluster Monitoring Agent

Introduction

In this project, a monitoring system agent is implemented. The system monitors the host hardware specification and its usage, and after monitoring them, it stores this information in a database. The potential users of this MVP are companies that need to manage a cluster of machines. The information stored in the database about each node can be very useful for the company in order to execute better resource planning for future projects. In this project, the tools used were Linux command lines, Bash, Docker, crontab, PostgreSQL, IntelliJ IDE, and Git. The operating system of the virtual machine used was CentOS 7.

Quick Start

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
Implementation

In order to implement this project, first, a bash script was created. The task of this script is to create a Docker container that runs PostgreSQL and then stop it. After this step was completed, the DDL file was created with 2 different tables inside the host_agent database. The first table is called host_info, which specifies all the hardware specs, and host_usage, which specifies the usage of the system. To conclude, the host_info.sh and host_usage.sh files were created so the information can be fetched to the database.

Architecture

[Architectire image] (https://www.figma.com/file/2K2vGdPpdeVcKjJer5dW2d/Untitled?type=design&node-id=0%3A1&mode=dev)

Scripts

1.psql_docker.sh - This script creates the jrvs-psql Postgres Docker container and also has the ability to start and stop the container. It can create a pgdata volume for the container to store the database.

2.host_info.sh - This script collects the machine hardware specifications, such as memory. After collecting this information, it is fetched to the Postgres database.

3.host_usage.sh - This script runs every minute on the host machine to collect information about the hardware usage, such as the time the machine has been idle. This script is assisted by cron. It will fetch the collected information to the Postgres database.

.ddl.sql: This SQL  script  contains all the information collected by the bash scripts.
# psql_docker.sh usage:
./psql_docker.sh [create/start/stop] [username] [password]

# host_info.sh usage:
./host_info.sh [host] [port] [database] [username] [password]

# host_usage.sh usage:
./host_usage.sh [host] [port] [database] [username] [password]


Database Modeling

All values must be no null

host_info

Property	Description
id	Unique id that identifies each machine
hostname	Unique hostname of machine
cpu_number	Number of cpu's in machine
cpu_architecture	The architecture of the machine
cpu_model	The model of the machine
cpu_mhz	The clock of the machine in mhz
L2_cache	Size of L2 cache in KB
total_mem	Amount of total memory in KB
timestamp	Time in UTC when data is collected
host_usage

Property	Description
timestamp	Timestamp in UTC when data was collected
host_id	Unique if of host machine
memory_free	Amount of free memory in KB
cpu_idle	The percentage of the CPU that is idle
cpu_kernel	The model of the CPU that is used by the kernel
disk_io	Number of disk I/O the machine has
disk_available	T he amount of disk available in MB
Test

This program was tested using bash scripts on a virtual machine running CentOS7. The outcome of the test was that every script ran as expected and collected and fetched the required information.

Deployment

The program was deployed to Git from the Linux terminal using various Git commands.

Improvemnts

1.One improvement that could have been done is checking first if Docker is available and providing an outcome scenario if Docker is not available. 2.Another improvement can be to provide better error handling for various situations and scenarios.
