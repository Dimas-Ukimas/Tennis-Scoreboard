# Tennis Scoreboard
Tennis scoreboard is a web application for keeping track of the points in a tennis match, where you can also browse finished matches and find concrete matches by player's name via page filter.

# Technologies

**Frontend:** JSP, HTML/CSS, JavaScript  
**Backend:** Java, Servlets  
**Database:** Hibernate, H2 (in-memory SQL database)  
**Tests:** JUnit5  
**Building:** Maven  
**Other:** Lombok, MapStruct, Logback  

# How to Run
1. Clone the repository  
   ```git clone https://github.com/Dimas-Ukimas/Tennis-Scoreboard.git```
2. Build war artifact:  
   ```mvnw.cmd clean package```
3. Build docker image:  
```docker build -t tennis-image .```
4. Run docker container:  
```docker run -d -p 8080:8080 --name tennis-container tennis-image```
5. Go to http://localhost:8080/
