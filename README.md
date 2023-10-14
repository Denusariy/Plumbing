# Plumbing

REST API приложения для учета сантехников в районе и их
закрепления за конкретными домами.

Установка:
- $ git clone https://github.com/Denusariy/Plumbing.git
- $ docker build -t plumbing-api:latest .

Запуск:
- $ docker-compose up
В другой вкладке терминала:
- $ docker-compose run k6 run -o experimental-prometheus-rw --tag testid=123451 /etc/k6_demo/first_script.js

Есть поддержка Swagger, в браузере:
http://localhost:7777/swagger-ui/index.html
