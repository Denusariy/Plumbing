# Plumbing

REST API приложения для учета сантехников в районе и их
закрепления за конкретными домами.

Установка:
- $ git clone https://github.com/Denusariy/Plumbing.git
- в IntelliJ Idea прописать команду mvn clean package -Dmaven.test.skip
- $ docker build . -t plumbing-api

Запуск:
- $ docker-compose up

Есть поддержка Swagger, в браузере:
http://localhost:8585/swagger-ui/index.html