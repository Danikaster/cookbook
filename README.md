# Кулинарная книга

Данный проект представляет собой кулинарную книгу, в которую можно добавить рецепты и ингредиенты, установить связь между ними, удалить или обновить.

**Использованные технологии:** OpenJDK 21, Spring Boot 3.2.3, Maven


**Пример использования**:
Откройте браузер или используйте инструмент для тестирования API (например, Postman).
Отправьте POST-запрос 

    POST http://localhost:8080/api/recipes/add
    Content-Type: application/json
    
    {
      "name": "salad",
      "ingredients": ["tomato"]
    }
    
Отправьте GET-запрос:

    GET http://localhost:8080/api/ingredients
Пример ответа

    HTTP/1.1 200 
    Content-Type: application/json
    Transfer-Encoding: chunked
    Date: Thu, 21 Mar 2024 10:10:52 GMT
    Keep-Alive: timeout=60
    Connection: keep-alive

    [
      {
        "id": 25,
        "name": "tomato",
        "recipes": []
      },
      {
        "id": 26,
        "name": "potato",
        "recipes": []
      }
    ]
    
