package pk.home.busterminal.application;


/**
 * Конфигурационный фаил
 * 
 * @author povloid
 *
 */
public final class Config {

	/**
	 * Размер буфера для загрузки/выгрузки ресурсов
	 */
	public static final int BUFFER_SIZE = 512;

	/**
	 * Папка для выгрузки дополнительных ресурсов 
	 */
	public static final String BASE_FILES_DIR = "/opt/vta-data/";
	
	// Work wor windows
	// Необходимо добавить чтобы работали отчеты в виндовс и некорежило кодировки
	// set JAVA_OPTS=-Dfile.encoding=UTF-8
	
	// public static final String BASE_FILES_DIR = "c:/btmp/";

}
