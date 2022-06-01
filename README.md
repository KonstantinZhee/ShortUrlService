# ShortUrlService
Java Service - give you short url by original url from body in http request, redirect...more info in README.md
****
You need SQL DataBase (my is Postgres). (no Redis, no Collections)
to create table: create table Urls (id int, hash varchar, originalUrl varchar, createdAt timestamp)

1. Service takes original url from from body in http POST-request
If url is in SQL data base - service make query to SQL about short url (hash) and give to Client short url. If url isn`t in SQL data base service make from original url hash-url, create new row in SQL DB and give to Client short url.

2. Service takes short (hash) url  from http GET-request. 
If short (hash) url is in SQL data base - service make query to SQL about original url (hash) and redirect Client original url. If short-url isn`t in SQL data base service do nothing.
