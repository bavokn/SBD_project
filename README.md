# SBD project

Uses Maven

## Build 
 * mvn clean compile package
 * A WAR file is generated inside the target folder with the name jsp-example-1.0.war 

## Deploy 
The WAR file can be deployed in an application server or in a web server like Apache Tomcat. 
```sh
cp target/jsp-example-1.0.war /opt/apache-tomcat-7.0.53/webapps/
cd /opt/apache-tomcat-7.0.53
bin/startup.sh
tail -f logs/localhost.2018-06-12.log
# access http://localhost:8081/
# access http://localhost:8081/jsp-example-1.0/?user=aaa
```

## Project structure
```
.
|-- README.md
|-- pom.xml
|-- src
|   `-- main
|       |-- java
|       |   `-- org
|       |       `-- allowed
|       |           `-- bitarus
|       |               |-- Handler.java
|       |               |-- TestBean.java
|       `-- webapp
|           |-- WEB-INF
|           |   |-- web.xml
|           |-- index.jsp
```

