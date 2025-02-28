FROM tomcat:10.1.31-jdk21

COPY target/tennis-scoreboard-1.0-SNAPSHOT.war webapps/ROOT.war

CMD ["catalina.sh", "run"]