export MAVEN_OPTS=-agentlib:jdwp=transport=dt_socket,address=8086,server=y,suspend=n

mvn clean package tomcat7:run

