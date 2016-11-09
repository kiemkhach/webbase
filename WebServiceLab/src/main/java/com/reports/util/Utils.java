package com.reports.util;

public class Utils {
	public static Double convertToKWh(String moduleArr, Integer valueModule) {
		if (valueModule == null){
			return 0d;
		}
		String[] arr = moduleArr.split("\\*");
		Double ratio = 1d;
		if (arr.length > 1) {
			ratio =  Double.valueOf(arr[0]);
		}
		return (valueModule*ratio);
	}
	
	public static String getModuleNumberFromArray(String array){
		String[] arr = array.split("\\*");
		if (arr.length > 1) {
			return arr[1];
		}
		return arr[0];
	}

	public static String getModuleIndexRatio(String moduleArr) {
		String[] arr = moduleArr.split("\\*");
		if (arr.length > 1) {
			return arr[0]+'*';
		}
		return "";
	}
}
