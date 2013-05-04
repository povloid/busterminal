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
	
	
	

	<!-- Надо бобавить в context.xml -->

	<!--<Parameter name="javax.faces.PROJECT_STAGE" value="Development" override="false"/> 
		<Parameter name="javax.faces.FACELETS_REFRESH_PERIOD" value="1" override="false"/> 
		<Parameter name="spring.profiles.default" value="Prod" override="false"/> -->


Готовый шаблон добавить в context.xml:

	<!-- ************************************************************** -->
	 
	 <Parameter name="javax.faces.PROJECT_STAGE" value="Development"
         override="false"/>
     <Parameter name="javax.faces.FACELETS_REFRESH_PERIOD" value="1"
         override="false"/>
     <Parameter name="spring.profiles.default" value="Dev"
         override="false"/>

	<!-- ************************************************************** -->

		
Опции запуска сервера:

	Память_______________________________| Лог________________________________|
	-Xms40m -Xmx768m -XX:MaxPermSize=256m  -Dlog4j.configuration=log4j-dev.xml

Добавить в server.xml

	<Connector SSLEnabled="true" URIEncoding="UTF-8" clientAuth="false" 
	keystoreFile="${user.home}/.keystore" keystorePass="paradox#65535" 
	maxThreads="200" port="8443" scheme="https" secure="true" sslProtocol="TLS"/>

	