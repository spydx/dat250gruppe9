# FeedApp

## Database

```
> docker pull mysql
> docker run --name feedapp-mysql -p 3306:3306 -eMYSQL_ROOT_PASSWORD=mhnrBKex$F$3^Q2h%fyA -eMYSQL_DATABASE=feedappdb -eMYSQL_USER=feedapp -eMYSQL_PASSWORD=feedapppassword -d mysql:latest

```


MacOS Command to find 8080 port scumbags
```
> lsof -nP -iTCP -sTCP:LISTEN | grep 8080
```