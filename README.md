# simple_ORM_Java
простая реализация Hibernate ORM

## Проект "Простой Hibernate ORM"
представляет собой реализацию простой системы объектно-реляционного отображения (ORM) с использованием собственных аннотаций для создания таблиц в базе данных и управления данными. Каждая модель, определенная в вашем проекте, будет автоматически создавать соответствующую таблицу в базе данных.

## Особенности
Создание таблиц в базе данных на основе классов моделей с использованием кастомных аннотаций.
Использование класса OrmProcessor, унаследованного от AbstractProcessor, для генерации SQL-скрипта создания таблиц.
Подключение к базе данных с использованием HikariDataSource.
OrmManager и его реализация OrmManagerImpl для управления сохранением, обновлением и поиском записей.

## Требования
Для успешной работы проекта необходимо иметь следующее:

Java Development Kit (JDK) версии 8 или выше.
Библиотеку HikariCP для управления подключениями к базе данных.

## Заключение
Проект "Простой Hibernate ORM" предоставляет минимальную реализацию системы ORM для создания таблиц в базе данных на основе классов моделей и управления данными. Вы можете доработать и расширить этот проект в соответствии с вашими потребностями.




