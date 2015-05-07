package com.ctbri.spider.cache;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * The constants to be used in the project , the properties will contain the value,
 * so the key should not be kept the same with the Strings like REDIS_PORT.
 * @author hequn
 * 2014-8-19 13:59:30
 */
public class SystemConstants {

	//the properties file loaded by the system
	public static final String resource = "SystemConstants.properties";
	
	//the properties
	public static Properties properties = new Properties();
	
	//the constant String in the properties file
	public static final String REDIS_IP = "REDIS_IP";
	public static final String REDIS_PORT = "REDIS_PORT";
	public static final String SAVE_LOCATION = "SAVE_LOCATION";
	public static final String PJ_COMMAND = "PJ_COMMAND";
	public static final String TH_COUNT = "TH_COUNT";
	public static final String D_SEED = "D_SEED";
	public static final String SEED_CLIENT = "SEED_CLIENT";
	public static final String SEED_LOCATION="SEED_LOCATION";
	public static final String NEVER_STOP="NEVER_STOP";
	
	//the constants of the system
	public static final String SPLITTER = "/";
	
	//load the key value into properties
	static {
		try {
			InputStream is = ClassLoader.getSystemResourceAsStream(resource);
			properties.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}