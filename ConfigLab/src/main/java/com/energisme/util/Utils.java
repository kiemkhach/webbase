package com.energisme.util;

public class Utils {

	public static boolean isNumber(String number) {
		try {
			Integer.parseInt(number.trim());
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}

	public static Integer getNumber(String number) {
		try {
			return Integer.parseInt(number.trim());
		} catch (NumberFormatException nfe) {
			return null;
		}
	}

	public static Double getNumberDouble(String number) {
		try {
			return Double.parseDouble(number.trim());
		} catch (NumberFormatException nfe) {
			return null;
		}
	}
	
	public static String getModuleIndexRatio(String moduleArr) {

		String[] arr = moduleArr.split("\\*");
		if (arr.length > 1) {
			if (arr[0] != null) {
				return arr[0].trim() + '*';
			}
		}
		return "";
	}

	public static String getModuleNumberFromArray(String array) {
		String[] arr = array.split("\\*");
		if (arr.length > 1) {
			if (arr[1] != null) {
				return arr[1].trim();
			}
		} else if (arr.length > 0) {
			return arr[0].trim();
		}
		return "";
	}
}
