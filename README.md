# Прокси-сервер с ролевой моделью доступа и кэшированием

Этот проект представляет собой прокси-сервер, который обрабатывает запросы к https://jsonplaceholder.typicode.com/ и имеет ролевую модель доступа с базовой авторизацией. Также в проекте реализовано кэширование данных для снижения числа запросов к jsonplaceholder.

## Описание функциональности

1. **Обработчики запросов:** Проксируют запросы к различным ресурсам на jsonplaceholder:

   - `GET /api/posts`: Получить информацию о постах.
   - `GET /api/posts/{post_id}`: Получить информацию о постe.
   - `POST /api/posts`: Создать новый пост.
   - `PUT /api/posts/{post_id}`: Обновить существующий пост.
   - `DELETE /api/posts/{post_id}`: Удалить пост.

   - `GET /api/users`: Получить информацию о пользователях.
   - `GET /api/users/{user_id}`: Получить информацию о пользователе.
   - `POST /api/users`: Создать нового пользователя.
   - `PUT /api/users/{user_id}`: Обновить существующего пользователя.
   - `DELETE /api/users/{user_id}`: Удалить пользователя.

   - `GET /api/albums}`: Получить информацию о альбомах.
   - `GET /api/albums/{album_id}`: Получить информацию о альбоме.
   - `POST /api/albums`: Создать новый альбом.
   - `PUT /api/albums/{album_id}`: Обновить существующий альбом.
   - `DELETE /api/albums/{album_id}`: Удалить альбом.

2. **Базовая авторизация и ролевая модель доступа:** Реализована ролевая модель доступа с следующими ролями:

   - `ROLE_ADMIN` - полный доступ ко всем обработчикам.
   - `ROLE_POSTS_VIEWER` - доступ для просмотра постов.
   - `ROLE_POSTS_EDITOR` - доступ для редактирования постов.
   - `ROLE_USERS_VIEWER` - доступ для просмотра пользователей.
   - `ROLE_USERS_EDITOR` - доступ для редактирования пользователей.
   - `ROLE_ALBUMS_VIEWER` - доступ для просмотра альбомов.
   - `ROLE_ALBUMS_EDITOR` - доступ для редактирования альбомов.

3. **Кэширование данных:** Для снижения числа запросов к jsonplaceholder реализован Inmemory кэш, который сначала обновляется, а затем отправляет запросы к jsonplaceholder.

4. **База данных H2 и генерация тестовых данных:** База данных создается автоматически и заполняется тестовыми данными при запуске приложения.

5. **Тестирование:** Для обеспечения корректной работы кода реализованы интеграционные тесты.

6. **Реализация WebSocket:** Добавлена конечная точка для WebSocket с базовой авторизацией и ролевой моделью.

7. **Ведение журнала действий (audit):** Реализована система ведения журнала действий с использованием ElasticSearch и Kibana для анализа и мониторинга действий пользователей. Для доступа к Kibana используйте следующие ссылки:

   - [Dashboard в Kibana](http://localhost:5601/app/kibana#/dashboard)
   - [Панель управления в Kibana](http://localhost:5601/app/kibana#/management)

## Запуск проекта

### Предварительные требования

Прежде чем начать установку, убедитесь, что у вас установлены следующие компоненты:

- Java Development Kit (JDK) версии 8 или выше
- Gradle
- Elasticsearch
- Kibana

## Установка Kibana

1. Скачайте Kibana с [официального сайта](https://www.elastic.co/downloads/kibana).

2. Распакуйте архив с Kibana в желаемую директорию на вашем компьютере.

3. Перейдите в директорию, где распакован Kibana.

4. Откройте файл конфигурации kibana.yml, который находится в директории config.

5. Убедитесь, что в файле конфигурации указан правильный адрес для Elasticsearch:
   ```yaml
   elasticsearch.hosts: ["http://localhost:9200"]
   ```
   
6. Запустите Kibana
   
8. Kibana будет доступна по адресу http://localhost:5601/. Откройте этот адрес в браузере, чтобы начать использовать Kibana для анализа данных из Elasticsearch.


## Установка Elasticsearch

1. Скачайте Elasticsearch с [официального сайта](https://www.elastic.co/downloads/elasticsearch).

2. Распакуйте архив с Elasticsearch в желаемую директорию на вашем компьютере.

3. Перейдите в директорию, где распакован Elasticsearch.

4. Запустите Elasticsearch

   
## Запуск проекта

1. Установите [Gradle](https://gradle.org/) на вашем компьютере, если он не установлен.

2. Клонируйте репозиторий:

   ```bash
   git clone https://github.com/russianZAK/vktestproject
   ```
3. Перейдите в директорию проекта:
   ```bash
   cd proxy-server
   ```
4. Соберите проект:
   ```bash
   cd proxy-server
   ```
5. Запустите Kibana и ElasticSearch
6. Запустите приложение:
  ```bash
  gradle bootRun
   ```

Приложение будет доступно по адресу http://localhost:8080/
