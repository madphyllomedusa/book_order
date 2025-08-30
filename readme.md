# Library (book_order)

Простое веб-приложение для заказа книг в библиотеке.  
Реализовано на **Java 8 + Spring Boot 2.7.18 + PostgreSQL 14 + Maven**.  
Поддерживает веб-интерфейс и REST API для управления книгами, клиентами и выдачей книг на чтение.  

---

## Подготовка к запуску

Необходимо создать базу данных PostgreSQL и прописать данные параметры в файле .env

```
DB_NAME=book_order
DB_URL=jdbc:postgresql://localhost:5432/${DB_NAME}
DB_USER=postgres
DB_PASSWORD=123
```


---

## Сборка

```bash
mvn clean package
```

Итоговый файл:  
```
target/book_order-0.0.1-SNAPSHOT.jar
```

---

##  Запуск

```bash
java -jar target/book_order-0.0.1-SNAPSHOT.jar
```

---

## Документация

После запуска:

- Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)  
- OpenAPI JSON: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)  

Также в директории проекта есть файл, который содержит документацию Postman

- [BookOrder Api.postman_collection.json](BookOrder%20Api.postman_collection.json)
  
