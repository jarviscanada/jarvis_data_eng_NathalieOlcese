# JDBC

## Introduction
This project follows a LinkedIn Learning course, and its purpose is to get introduced to the fundamentals of JDBC. The app implements a basic database that manages orders and customers with different important fields, such as the city of the customer or the salesperson that helps the customer place their order. This database also contains a CustomerDAO and an OrderDAO. Both DAOs offer all CRUD operations.

## ER diagram
https://www.figma.com/file/2re02ccXdGrGFVcgkC2orS/ER?type=design&node-id=0%3A1&mode=design&t=lcwww2q25EmUWEer-1

## Desing Patterns 
In this project, different SQL scripts were executed to create the database tables. Maven was used as the build and dependency management tool. The DAO (Data Access Object) design pattern is also an essential part of this project. The DAO allows the joins to be performed by the object itself. These DAOs also offer all CRUD (Create, Read, Update, Delete) operations for customers and orders.
