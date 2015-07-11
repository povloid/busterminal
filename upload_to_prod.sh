#!/bin/sh

mvn clean package -Dmaven.test.skip=true

ssh -p 7877 politrend@vta.kz 'export JAVA_HOME=/opt/jdk;/home/politrend/opt/apache-tomcat-8.0.18/bin/shutdown.sh'
ssh -p 7877 politrend@vta.kz 'rm -vfr /home/politrend/opt/apache-tomcat-8.0.18/webapps/busterminal*'
scp -P 7877 target/busterminal.war politrend@vta.kz:/home/politrend/opt/apache-tomcat-8.0.18/webapps/busterminal.war
ssh -p 7877 politrend@vta.kz 'export JAVA_HOME=/opt/jdk;/home/politrend/opt/apache-tomcat-8.0.18/bin/startup.sh'
