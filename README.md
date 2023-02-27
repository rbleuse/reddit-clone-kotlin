Implement a reddit clone app using Kotlin, following https://dev.to/maxicb/full-stack-reddit-clone-spring-boot-react-electron-app-part-1-1c22


Start a postgres instance locally with this command :

```docker run --name pgs13.5 -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -e POSTGRES_DB=reddit -d -p 5432:5432 postgres:13.5-alpine```