# Plumbing

REST API приложения для учета сантехников в районе и их
закрепления за конкретными домами.

Установка:
- $ git clone https://github.com/Denusariy/Plumbing.git
- в IntelliJ Idea прописать команду mvn clean package -Dmaven.test.skip
- $ docker build -t plumbing-api:latest .
- $ brew install k6

Запуск:
- $ docker-compose up
- $ K6_PROMETHEUS_RW_TREND_AS_NATIVE_HISTOGRAM=true k6 run -o experimental-prometheus-rw --tag testid=123451 ./k6_demo/first_script.js

Есть поддержка Swagger, в браузере:
http://localhost:7777/swagger-ui/index.html