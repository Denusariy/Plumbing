# Plumbing

REST API приложения для учета сантехников в районе и их
закрепления за конкретными домами.

Установка:
- $ git clone https://github.com/Denusariy/Plumbing.git
- $ docker build . -t plumbing-api
- $ docker-compose build

Запуск:
- $ docker-compose up

В браузере по запросу http://localhost:8585/api/v1/house/1
должен быть ответ:
{
"status": "NOT_FOUND",
"message": "Дом с id 1 не найден"
}
