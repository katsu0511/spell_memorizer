package config;

import java.io.IOException;
import java.util.Properties;

public class AppConfig {
	private static Properties props = new Properties();
	
	static {
		try {
			props.load(AppConfig.class.getClassLoader().getResourceAsStream("application.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String get(String key) {
		return props.getProperty(key);
	}
}
