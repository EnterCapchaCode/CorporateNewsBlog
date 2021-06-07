# CorporateNewsBlog
Corporate News Blog is a simple CRUD web-application that helps to publish and discuss important corporate news. Application has role system and OAuth 2.0

### ROLES:
- Reader - Can read news posts and write comments
- Creator - Extends ***reader*** role. Can write new posts
- Admin - Extends ***creator*** role. Can moderate comments, posts, users. Has access to user list and and can enhance the role in the system

## Built With

### BE:
- [Java 15](https://www.oracle.com/java/technologies/javase/15-relnote-issues.html)
- [Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/) 
- [Hibernate](https://hibernate.org/)
- [PostgreSQL](https://www.postgresql.org/)
- [Flayway](https://flywaydb.org/)
- [OAuth 2.0](https://oauth.net/2/)
- [MapStruct](https://mapstruct.org/)
- [Lombok](https://projectlombok.org/)

### FE:
- [JavaScript](https://developer.mozilla.org/en-US/docs/Web/JavaScript)
- [Vue.js](https://vuejs.org/)
- [Bootstrap](https://getbootstrap.com/)
- [SCSS](https://sass-lang.com/)

## Run application 

1. Clone the repo https://github.com/EnterCapchaCode/CorporateNewsBlog.git
2. Install NPM packages from project dir
```bash
npm install
```
3. Run Postgres from Docker container
```bash
docker-compose -f extras/docker/docker-compose.yaml up
```
4. Run FE
```bash
npm start
```
5. Build application required Java 14. Run `gradlew clean build` to clean and rebuild project. Run `gradlew bootRun` to start application. Application listen on port 8080

## Contact

 Denis Turbal - dturball@gmail.com
 Project Link: https://github.com/EnterCapchaCode/CorporateNewsBlog


