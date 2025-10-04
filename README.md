# shop21API

## Описание
REST API сервис для управления магазином Shop21. Основной бэкенд-сервис на Spring Boot.

## Технологии
- Java / Spring Boot
- PostgreSQL
- gRPC (клиент для взаимодействия с Auth сервисом)
- Maven
- Protocol Buffers

## Структура
- `src/main/java/backend/` - исходный код Java приложения
- `src/main/proto/` - Protocol Buffers схемы (cfg.proto)
- `src/main/resources/` - конфигурационные файлы (application.properties)
- `src/sql/` - SQL скрипты для инициализации БД
  - `01_table.sql` - создание таблиц
  - `02_data.sql` - начальные данные
  - `03_create_users.sql` - создание пользователей БД
- `Dockerfile` - файл для сборки Docker образа
- `pom.xml` - Maven конфигурация

## Конфигурация

### Переменные окружения
- `SPRING_DATASOURCE_URL` - URL подключения к PostgreSQL
- `SPRING_DATASOURCE_USERNAME` - имя пользователя БД
- `SPRING_DATASOURCE_PASSWORD` - пароль БД
- `GRPC_CLIENT_AUTH_SERVICE_ADDRESS` - адрес gRPC сервиса аутентификации

### Порты
- **8080** - HTTP порт приложения (внутри контейнера)
- **10000** - основной экземпляр
- **10001** - реплика для чтения #1
- **10002** - реплика для чтения #2

### Запуск
Осуществляется через shop21INIT

## API Endpoints
API предоставляет endpoints для управления:
- Продуктами
- Клиентами
- Поставщиками
- Изображениями

## База данных
Использует PostgreSQL БД `shopAPI` на порту 10009.
SQL скрипты автоматически выполняются при первом запуске контейнера.
