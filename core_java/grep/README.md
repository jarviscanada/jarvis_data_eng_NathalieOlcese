# Grep app

## Introduction 
A command line-utily app was implemen. This utility is called grep and it retuns the lines that march a regex string entered by the user fom the files that are in a specific directory. The project was implement using java. the fetaure and librarues used were regex, lanbdda and stream. The tools used to implemnet this grep app are intelliJ IDE, and docker ,

## Quick Start 

In order to run the app for the source it self this command has to be enetered
```
mvn clean package
java -jar ./target/grep-1.0-SNAPSHOT.jar <regex expression> <path to folder> <path to output file>

```
To rung the app in Docker this command has to be entered 

```
docker pull jbrar/grep
docker run --rm \
-v <host path to its data folder>:<container path to its data folder> \
-v <host path to its log folder>:<container path to its log folder> \
jbrar/grep <regex string> <host path to its data folder> <host path to its log file>

```
## Implementaion
The implementation goes though all directories and get the files recursively. So the matching lines with the regex string will be otuputed. The implementation was then modififed using lambda wich emahcnec the grep app perfomace.

## Pseudocode

```
matchedLines = []
for file in all files obtained recursively from the given directory:
    for line in files:
        if line matches the given regex:
            matchedLines.append(line)
write matchedLines to the given output file
```

## Performance issue
The perfomance issue in this grep app is that the amoount of memory used might exceed the mmery allocated by the jvn of the file is larget than it so the program may crash in this case.

in order to resolve this streams can be used so not all lines of code have to be sotred in the memory and ``` BufferReader.lines() ``` 
can be used to rech for the matching lines with the regex string  and output the ressulted lines.

## Test
The app was tested using  sample data and suign mutliple test cases. In all the test cases perfomaed with the sample data the grep app worked as expected.

## Deployment

Maven Shade plutign was added first to pack the grep app as Fat Jar. then mvm clean package was escuted to pacjage the greo app as Fat Jar. A DockerFile was then created and specified the base image used that contains the Java 8 JRE amd to cpy the Fat Jar file . then the new Dpcker container was executed and pulled the image usinf Docker Hub.

## Improvements 

1. fixin the memory issue usinf ``` BufferReader.lines() ```
2. Using multithreadin to imrpove the performance of the grep app.
