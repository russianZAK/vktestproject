# Используем официальный образ OpenJDK для Java 11
FROM openjdk:11

# Устанавливаем переменную окружения для версии Elasticsearch
ENV ELASTICSEARCH_VERSION=7.16.2

# Устанавливаем Elasticsearch
RUN wget -qO - https://artifacts.elastic.co/GPG-KEY-elasticsearch | apt-key add - \
    && echo "deb https://artifacts.elastic.co/packages/7.x/apt stable main" | tee -a /etc/apt/sources.list.d/elastic-7.x.list \
    && apt-get update \
    && apt-get install -y elasticsearch=$ELASTICSEARCH_VERSION

# Копируем конфигурационные файлы Elasticsearch (если нужно)
# COPY elasticsearch.yml /etc/elasticsearch/

# Expose порт для Elasticsearch
EXPOSE 9200

# Запускаем Elasticsearch при старте контейнера
CMD ["elasticsearch"]

# Копируем файлы проекта в контейнер
COPY . /usr/src/app

# Устанавливаем рабочую директорию
WORKDIR /usr/src/app

# Собираем проект Gradle
RUN ./gradlew build
