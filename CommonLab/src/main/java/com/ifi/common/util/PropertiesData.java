package com.ifi.common.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Read data from properties
 * 
 * @author ndlong
 * 
 */
public class PropertiesData {

	private static String pathFile;
	private static String dataFile1;
	private static String dataFile2;
	private static String rangeFile;
	public static int STATUS_FAIL = -1;
	public static int STATUS_EXIST = 0;
	public static int STATUS_SUCCESS = 1;

	/**
	 * Check user exist in config file
	 * 
	 * @param userName
	 * @return
	 */
	public boolean isExist(String userName) {
		userName = userName.toUpperCase();
		String key = ConfigEnum.USER_NAME_ACCESS.toString();
		Map<String, String> mapData = loadConfig(pathFile);
		String str = mapData.get(key);
		if (str.toUpperCase().contains(userName)) {
			return true;
		}
		return false;
	}


	/**
	 * add user in user key
	 * 
	 * @param key
	 * @param userName
	 * @return -1: fail ; 0: is exists user; 1 success
	 */
	public int addUser(String userName) {
		String key = ConfigEnum.USER_NAME_ACCESS.toString();
		Map<String, String> mapData = loadConfig(pathFile);
		String value = "";
		if (mapData != null) {
			if (mapData.containsKey(key)) {
				value = mapData.get(key);
			}
		} else {
			return STATUS_FAIL;
		}
		String[] uNameArr = value.split(",");
		for (String uName : uNameArr) {
			if(uName.trim().equalsIgnoreCase(userName)){
				return STATUS_EXIST;
			}
		}
		value += ",";
		value += userName;
		mapData.put(key, value);
		if (saveConfig(mapData, pathFile)) {
			return STATUS_SUCCESS;
		}
		return STATUS_FAIL;
	}

	/**
	 * xoa user da duoc dang ky
	 * 
	 * @param key
	 * @param userName
	 * @return -1: fail, 0: not exists user, 1: success
	 */
	public int removeUser(String userName) {
		userName = userName.toUpperCase();
		String key = ConfigEnum.USER_NAME_ACCESS.toString();
		Map<String, String> mapData = loadConfig(pathFile);
		String value = "";
		if (mapData != null) {
			if (mapData.containsKey(key)) {
				value = mapData.get(key);
			} else {
				return STATUS_EXIST;
			}
		} else {
			return STATUS_FAIL;
		}
		if (!value.toUpperCase().contains(userName)) {
			return STATUS_EXIST;
		}
		String[] uList = value.split(",");
		String newValue = "";
		for (String u : uList) {
			if (!u.isEmpty() && !newValue.equalsIgnoreCase(userName)) {
				newValue += u;
				newValue += ",";
			}
		}
		mapData.put(key, newValue);
		if (saveConfig(mapData, pathFile)) {
			return STATUS_SUCCESS;
		}
		return STATUS_FAIL;

	}

	/**
	 * Load map from properties file
	 * 
	 * @return
	 */
	private Map<String, String> loadConfig(String fileName) {
		Map<String, String> mapData = new HashMap<String, String>();
		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream(fileName);
			prop.load(input);
			Enumeration<Object> em = prop.keys();
			while (em.hasMoreElements()) {
				String k = (String) em.nextElement();
				String v = (String) prop.get(k);
				mapData.put(k, v);
			}
		} catch (IOException io) {
			io.printStackTrace();
			return null;
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return mapData;
	}

	/**
	 * Load map from properties file
	 * 
	 * @return
	 */
	private Map<String, Integer> loadConfigInt(String fileName) {
		Map<String, Integer> mapData = new HashMap<String, Integer>();
		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream(fileName);
			prop.load(input);
			Enumeration<Object> em = prop.keys();
			while (em.hasMoreElements()) {
				String k = (String) em.nextElement();
				String v = (String) prop.get(k);
				mapData.put(k, Integer.parseInt(v.trim()));
			}
		} catch (IOException io) {
			io.printStackTrace();
			return null;
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return mapData;
	}

	/**
	 * save map to properties file
	 * 
	 * @param mapData
	 * @return
	 */
	private boolean saveConfig(Map<String, String> mapData, String fileName) {
		Properties prop = new Properties();
		OutputStream output = null;
		try {
			output = new FileOutputStream(fileName);
			for (Map.Entry<String, String> entry : mapData.entrySet()) {
				prop.setProperty(entry.getKey(), entry.getValue());
			}
			prop.store(output, null);
		} catch (IOException io) {
			io.printStackTrace();
			return false;
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		return true;
	}

	public String getPathFile() {
		return pathFile;
	}

	public void setPathFile(String pathFile) {
		PropertiesData.pathFile = pathFile;
	}

	public static String getDataFile1() {
		return dataFile1;
	}

	public static void setDataFile1(String dataFile1) {
		PropertiesData.dataFile1 = dataFile1;
	}

	public static String getDataFile2() {
		return dataFile2;
	}

	public static void setDataFile2(String dataFile2) {
		PropertiesData.dataFile2 = dataFile2;
	}

	public static String getRangeFile() {
		return rangeFile;
	}

	public static void setRangeFile(String rangeFile) {
		PropertiesData.rangeFile = rangeFile;
	}

	public Map<String, String> loadRangeConfig() {
		return loadConfig(rangeFile);
	}

	public boolean saveRangeConfig(Map<String, String> mapData) {
		return saveConfig(mapData, rangeFile);
	}

	public Map<String, Integer> loadRangeConfigInt() {
		return loadConfigInt(rangeFile);
	}
}
