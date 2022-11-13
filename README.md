# **ESXI API**
This is the api written so that users can create virtual machines on the website.
* Technology used: 
    * Framework: Spring Boot
    * DBMS: MySQL
    * Programming language: Java, PowerShell Script
## **Install & Set up**
**First step, clone repo** 
* you need to clone this project from github. Select a folder on your computer, go to the terminal and type the following command :
    ```
    git clone https://github.com/dttrung257/esxi_api.git
    ```
**Second step, install Java**
* You can check the java version on your device by going to the terminal and typing the following command : 
    ```
    java -version
    ```
Your machine must install java version **at least 17**. You can install it at this link : 
[Download Java](https://www.oracle.com/java/technologies/downloads/archive/) 
and and set up java in environment variable. 
* If you use linux, you can refer to the installation instructions here : [Download Java On Linux](https://youtu.be/Mlmm7XvB_2k).

**Third step, install maven** 
* You can check the maven version on your device by going to the terminal and typing the following command : 
    ```
    mvn -version
    ```
* If you don't have one on your computer, you can download it here https://dlcdn.apache.org/maven/maven-3/3.8.6/binaries/apache-maven-3.8.6-bin.zip
## **Use**
**First step, create database** 
* Connect to your mysql server and create database name : esxi_api_db (Example).
    ```
    CREATE DATABASE IF NOT EXISTS esxi_api_db;
    ```
    You can name the database as you like.
* Go to Go to the file **application.properties** (src/main/resources/application.properties) edit the information :
    ``` 
    db.name=?
    db.username=?
    db.password=?
    ```
**Second step, install VMware ESXI**
* Turn on VMware ESXI on your computer.
Third step, configure to connect to ESXI
* Go to the file **application.properties** (src/main/resources/application.properties) edit the information of ESXI :
    ``` 
    esxi_server.ip=?
    esxi_server.username=?
    esxi_server.password=?
    ```
* Add 2 files **UbuntuServer_disk0.vmdk** and **WindowServer_disk0.vmdk** to 2 folders ubuntu and window respectively in src/main/resources 
.You can download here : [Files](https://drive.google.com/drive/folders/1jtoAtghj12tLvRxMEjK-A2amm5gJ5-F8)

**Fourth step, run api**
* Use command
    ```
    mvn spring-boot:run
    ```
**API Urls**
* Link
    ```
    localhost:{port}/swagger/swagger-ui/index.html#
    ```
    Port 8080 already set, you can change it in application.properties file.







