package pk.home.busterminal.domain.security;

/**
 * Список ролей
 * 
 * @author povloid
 * 
 */
public enum UserAuthoritys {
	ROLE_SUPERUSER, // Суперпользователь
	ROLE_ADMIN, // Администратор

	ROLE_USER,

	ROLE_DISPATCHER, // Диспетчер

	ROLE_RB,
	ROLE_RB_TRANSPORT, // Работа со справочником транспорта
	ROLE_RB_PATH, // Работа со справочниками пути
	ROLE_RB_ORGANIZATION, // Работа со справочниками организации
	ROLE_RB_CUSTOMER, // Работа со справочником клиентов

	ROLE_OP,
	ROLE_OP_CASSIR_SALE, // Операции кассир продажа билета
	ROLE_OP_CASSIR_SALE_DISCOUNT, // Операции кассир продажа билета
	ROLE_OP_CASSIR_RETURN, // Операции кассир возврат билета

	ROLE_USER_META_EDITOR, // Редактор метатегов

	ROLE_VIEWER, // Просмотр
	
	ROLE_REPORTS,
	ROLE_REPORTS_DIVISION,
	ROLE_REPORTS_DIVISION_BALANCE_F1,
	
	ROLE_REPORTS_DRIVER,
	ROLE_REPORTS_DRIVER_FORM1,
	ROLE_REPORTS_DRIVER_FORM2,
	
	
	ROLE_BLOCKER,		// Дает право блокировать место и разблокировать место
	ROLE_BLOCKER_SUPER	// Дает право блокировать место и разблокировать все места
	
}
