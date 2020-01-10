#!/bin/sh
rm /opt/tomcat/latest/webapps/jsp-example-1.0*
mvn clean compile package
cp target/jsp-example-1.0.war /opt/tomcat/latest/webapps/