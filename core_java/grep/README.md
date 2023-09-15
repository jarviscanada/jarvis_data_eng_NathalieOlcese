# Grep app

## Introduction 
A command-line utility app was implemented. This utility is called 'grep,' and it returns lines that match a regex string entered by the user from files that are in a specific directory. The project was implemented using Java. The features and libraries used were regex, lambda expressions, and streams. The tools used to implement this grep app were the IntelliJ IDE and Docker.

## Quick Start 

In order to run the app from the source itself this command has to be enetered
```
mvn clean package
java -jar ./target/grep-1.0-SNAPSHOT.jar <regex expression> <path to folder> <path to output file>

```
To run the app in Docker this command has to be entered 

```
docker pull jbrar/grep
docker run --rm \
-v <host path to its data folder>:<container path to its data folder> \
-v <host path to its log folder>:<container path to its log folder> \
jbrar/grep <regex string> <host path to its data folder> <host path to its log file>

```
## Implementaion
The implementation goes through all directories and gets the files recursively. The lines matching the regex string are then outputted. The implementation was then modified using lambdas, which enhanced the performance of the grep app.

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
The perfomance issue in this grep app is that the amoount of memory used might exceed the memory allocated by the jvm if the file is larger than it so the program may crash in this case.

in order to resolve this streams can be used so not all lines of code have to be sotred in the memory and ``` BufferReader.lines() ``` 
can be used to check for the matching lines with the regex string  and output the ressulted lines.

## Test
The app was tested using sample data and multiple test cases. In all the test cases performed with the sample data, the grep app worked as expected.

## Deployment

The Maven Shade plugin was added initially to package the grep app as a Fat Jar. Then, 'mvn clean package' was executed to package the grep app as a Fat Jar. Subsequently, a Dockerfile was created, specifying the base image that includes the Java 8 JRE, and copying the Fat Jar file. Finally, a new Docker container was executed, and the image was pulled from Docker Hub.

## Improvements 

1. fixing the memory issue using ``` BufferReader.lines() ```
2. Using multithreading to improve the performance of the grep app.
