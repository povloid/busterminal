# Система для автоматизации продажи пассажирских билетов на автобусы (автоматизация транспортных пассажирских перевозок).

## Как пользоваться (видеоинструкция)
[![Видеоинструкция](https://img.youtube.com/vi/E-VcN4te_Tc/0.jpg)](https://youtu.be/E-VcN4te_Tc?feature=shared)

## Как устанавливать

Устанавливается как обычное приложение для java веб контейнера (например Apache Tomcat).

Опции web.xml:

	<!-- 
	Enables special Facelets debug output during development
	Разрешить во время разработки специальный отладочный вывод facelets
	
	Может принимать значения Development и Production
	-->
	<!-- <context-param>
		<param-name>javax.faces.PROJECT_STAGE</param-name>
		<param-value>Development</param-value>
	</context-param> -->
	<!-- 
	Causes Facelets to refresh templates during development
	Заставлять facelets обновлять шаблон во время разработки
	1 - заставит проверять шаблон каждую секунду
	-1 - необходимо задать в Production  
	-->
	<!-- <context-param>
		<param-name>javax.faces.FACELETS_REFRESH_PERIOD</param-name>
		<param-value>1</param-value>
	</context-param> -->
	
	
	<!-- Надо добавить в context.xml -->

	<!--<Parameter name="javax.faces.PROJECT_STAGE" value="Development" override="false"/> 
	<Parameter name="javax.faces.FACELETS_REFRESH_PERIOD" value="1" override="false"/> 
	<Parameter name="spring.profiles.default" value="Prod" override="false"/> -->


Готовый шаблон добавить в context.xml:

	<!-- ************************************************************** -->
	 
      	<Parameter name="javax.faces.PROJECT_STAGE" value="Development" override="false"/>
     	<Parameter name="javax.faces.FACELETS_REFRESH_PERIOD" value="1" override="false"/>
     	<Parameter name="spring.profiles.default" value="Dev" override="false"/>

	<!-- ************************************************************** -->
		
Опции запуска сервера:

	Память_______________________________| Лог________________________________|
	-Xms40m -Xmx768m -XX:MaxPermSize=256m  -Dlog4j.configuration=log4j-dev.xml

Добавить в server.xml

	<Connector SSLEnabled="true" URIEncoding="UTF-8" clientAuth="false" 
	keystoreFile="${user.home}/.keystore" keystorePass="password" 
	maxThreads="200" port="8443" scheme="https" secure="true" sslProtocol="TLS"/>


(JasperReport)

// Work wor windows
// Необходимо добавить чтобы работали отчеты в виндовс и некорежило кодировки
// set JAVA_OPTS=-Dfile.encoding=UTF-8
	
// public static final String BASE_FILES_DIR = "c:/btmp/";





	
