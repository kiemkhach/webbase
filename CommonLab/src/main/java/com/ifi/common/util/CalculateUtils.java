package com.ifi.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalculateUtils {
	public static List<Integer> getIDFromStr(String moduleStr) {
		List<Integer> lst = new ArrayList<Integer>();
		if (moduleStr == null || moduleStr.isEmpty()) {
			return lst;
		}
		String[] plusSplit = moduleStr.split(FrontalKey.PLUS_SPECIAL);
		for (String plus : plusSplit) {
			String[] multiSplit = plus.split(FrontalKey.MULTIPLICATION_SPECIAL);
			String moduleID = multiSplit[multiSplit.length - 1];
			try {
				lst.add(Integer.parseInt(moduleID));
			} catch (NumberFormatException nfe) {
				nfe.printStackTrace();
			}
		}
		return lst;
	}

	public static List<String> getModuleFromFomular(String moduleStr) {
		List<String> lst = new ArrayList<String>();
		if (moduleStr == null || moduleStr.isEmpty()) {
			return lst;
		}
		String[] plusSplit = moduleStr.split(FrontalKey.PLUS_SPECIAL);
		for (String plus : plusSplit) {
			String[] minusArr = plus.split(FrontalKey.MINUS);
			for (String minus : minusArr) {
				String[] multiSplit = minus.split(FrontalKey.MULTIPLICATION_SPECIAL);
				String moduleID = multiSplit[multiSplit.length - 1];
				try {
					lst.add(moduleID);
				} catch (NumberFormatException nfe) {
					nfe.printStackTrace();
				}
			}
		}
		return lst;
	}

	public static Integer getValueFromStr(String moduleStr, Map<Integer, Map<String, Integer>> mapValue) {
		Double rs = 0d;
		String[] plusSplit = moduleStr.split(FrontalKey.PLUS_SPECIAL);
		for (String plus : plusSplit) {
			String[] multiSplit = plus.split(FrontalKey.MULTIPLICATION_SPECIAL);
			int sizeMinus = multiSplit.length - 1;
			Integer moduleID = Integer.parseInt(multiSplit[sizeMinus]);
			double multi = getConsommationFromMap(moduleID, mapValue);
			for (int i = 0; i < sizeMinus; i++) {
				Double d = Double.parseDouble(multiSplit[i]);
				multi *= d;
			}
			rs += multi;
		}
		return rs.intValue();
	}

	private static int getConsommationFromMap(Integer moduleId, Map<Integer, Map<String, Integer>> mapValue) {
		if (moduleId == null || mapValue == null) {
			return 0;
		}
		if (mapValue.containsKey(moduleId)) {
			Map<String, Integer> map = mapValue.get(moduleId);
			if (map != null && map.size() > 0) {
				Integer rs = 0;
				for (Map.Entry<String, Integer> entry : map.entrySet()) {
					if (entry.getValue() != 0) {
						rs += entry.getValue();
					}
				}
				return rs;
			}
		}
		return 0;
	}

	public static Integer getConsommation(String moduleStr, Map<Integer, Integer> mapConsommation) {
		Double rs = 0d;
		String[] plusSplit = moduleStr.split(FrontalKey.PLUS_SPECIAL);
		for (String plus : plusSplit) {
			String[] multiSplit = plus.split(FrontalKey.MULTIPLICATION_SPECIAL);
			int sizeMinus = multiSplit.length - 1;
			Integer moduleID = Integer.parseInt(multiSplit[sizeMinus]);
			Double multi = 0d;
			if (mapConsommation.containsKey(moduleID)) {
				multi = (double) mapConsommation.get(moduleID);
			}
			for (int i = 0; i < sizeMinus; i++) {
				Double d = Double.parseDouble(multiSplit[i]);
				multi *= d;
			}
			rs += multi;
		}

		return rs.intValue();
	}

	public static void addModuleIDToList(String moduleStr, List<Integer> lstModuleID) {
		if (moduleStr == null || moduleStr.isEmpty()) {
			return;
		}
		List<Integer> lst = getIDFromStr(moduleStr);
		for (Integer moduleID : lst) {
			if (!lstModuleID.contains(moduleID)) {
				lstModuleID.add(moduleID);
			}
		}
	}

	public static Boolean isContainCalculate(String moduleStr) {
		if (moduleStr == null || moduleStr.isEmpty()) {
			return null;
		}
		return (moduleStr.contains(FrontalKey.PLUS) || moduleStr.contains(FrontalKey.MULTIPLICATION));
	}

	// public static void main(String[] args) {
	// System.out.println(getIDFromStr("12*1234+54+70*878"));
	// Map<Integer, Integer> map = new HashMap<Integer, Integer>();
	// map.put(123, 1);
	// map.put(456, 2);
	// System.out.println(getValueFromStr("12*123+456+0.1*123", map));
	//
	// }

	public static Float calculateByFormular(String formular, Map<Integer, Float> mapData,
			Map<Integer, String> mapCounter) {
		if (formular == null || formular.isEmpty()) {
			return null;
		}
		String[] plusArr = formular.split(FrontalKey.PLUS_SPECIAL);
		Float finalValue = 0f;
		for (String plus : plusArr) {
			String[] minusArr = plus.split(FrontalKey.MINUS);

			Float rsMinus = 0f;
			int count = 0;
			for (String minus : minusArr) {
				String[] multiArr = minus.split(FrontalKey.MULTIPLICATION_SPECIAL);
				Float rsMulti = 1f;

				for (String multi : multiArr) {
					String[] divisionArr = multi.split(FrontalKey.DIVISION_SPECIAL);
					Float rsDivision = 1f;
					int countDivision = 0;
					for (String division : divisionArr) {
						Float rs = getValue(division, mapData, mapCounter);

						if (rs != null) {
							if (countDivision == 0) {
								rsDivision = rs;
							} else {
								rsDivision = rsDivision / rs;
							}
							countDivision++;
						} else {
							return null;
						}
					}
					rsMulti *= rsDivision;
				}

				if (count == 0) {
					rsMinus = rsMulti;
				} else {
					rsMinus -= rsMulti;
				}
				count++;
			}

			finalValue += rsMinus;
		}
		return finalValue;
	}

	private static Float getValue(String number, Map<Integer, Float> mapData, Map<Integer, String> compteurMap) {
		number = number.trim();
		boolean isCompteur = false;
		Integer counterID = null;
		for (Map.Entry<Integer, String> entry : compteurMap.entrySet()) {
			String compteurName = entry.getValue();
			if (compteurName.trim().equalsIgnoreCase(number)) {
				isCompteur = true;
				counterID = entry.getKey();
				break;
			}
		}

		if (isCompteur) {
			if (mapData.containsKey(counterID)) {
				return mapData.get(counterID);
			} else {
				return null;
			}
		} else {
			try {
				return Float.parseFloat(number.replace(",", "."));
			} catch (NumberFormatException nfe) {
				nfe.printStackTrace();
				return null;
			}
		}
	}

	public static Double calculateFirstByFormular(String formular, Map<String, Map<String, Double>> mapData) {
		if (formular == null || formular.trim().isEmpty()) {
			return null;
		}
		String[] plusArr = formular.split(FrontalKey.PLUS_SPECIAL);
		Double finalValue = 0d;
		for (String plus : plusArr) {
			if (plus.trim().isEmpty()) {
				continue;
			}
			String[] minusArr = plus.trim().split(FrontalKey.MINUS);

			Double rsMinus = 0d;
			int count = 0;
			for (String minus : minusArr) {
				if (minus.trim().isEmpty()) {
					continue;
				}
				String[] multiArr = minus.split(FrontalKey.MULTIPLICATION_SPECIAL);
				Double rsMulti = 1d;
				int multiArrSizeMinus = multiArr.length - 1;
				for (int i = 0; i < multiArrSizeMinus; i++) {
					String multi = multiArr[i];
					if (multi.trim().isEmpty()) {
						continue;
					}
					String[] divisionArr = multi.split(FrontalKey.DIVISION_SPECIAL);
					Double rsDivision = 1d;
					int countDivision = 0;
					for (String division : divisionArr) {
						if (division.trim().isEmpty()) {
							continue;
						}
						Double rs = Double.parseDouble(division.trim().replace(",", "."));

						if (countDivision == 0) {
							rsDivision = rs;
						} else {
							rsDivision = rsDivision / rs;
						}
						countDivision++;
					}
					rsMulti *= rsDivision;
				}
				Double rs = getFirstValue(multiArr[multiArrSizeMinus], mapData);
				if (rs != null) {
					rsMulti *= rs;
				}

				if (count == 0) {
					rsMinus = rsMulti;
				} else {
					rsMinus -= rsMulti;
				}
				count++;
			}

			finalValue += rsMinus;
		}
		return finalValue;
	}

	private static Double getFirstValue(String number, Map<String, Map<String, Double>> mapData) {
		if (number == null || mapData == null) {
			return null;
		}
		number = number.trim();
		if (mapData.containsKey(number)) {
			Map<String, Double> mapTime = mapData.get(number);
			if (!mapTime.isEmpty()) {
				return mapTime.values().iterator().next();
			}
		}
		return null;
	}

	private static Double getValue(String number, Map<String, Double> mapData) {
		if (number == null || mapData == null) {
			return null;
		}
		number = number.trim();
		if (mapData.containsKey(number)) {
			return mapData.get(number);
		}
		return null;
	}

	public static Double calculateByFormular(String formular, Map<String, Double> mapData) {
		if (formular == null || formular.trim().isEmpty()) {
			return null;
		}
		String[] plusArr = formular.split(FrontalKey.PLUS_SPECIAL);
		Double finalValue = 0d;
		for (String plus : plusArr) {
			if (plus.trim().isEmpty()) {
				continue;
			}
			String[] minusArr = plus.trim().split(FrontalKey.MINUS);

			Double rsMinus = 0d;
			int count = 0;
			for (String minus : minusArr) {
				if (minus.trim().isEmpty()) {
					continue;
				}
				String[] multiArr = minus.split(FrontalKey.MULTIPLICATION_SPECIAL);
				Double rsMulti = 1d;
				int multiArrSizeMinus = multiArr.length - 1;
				for (int i = 0; i < multiArrSizeMinus; i++) {
					String multi = multiArr[i];
					if (multi.trim().isEmpty()) {
						continue;
					}
					String[] divisionArr = multi.split(FrontalKey.DIVISION_SPECIAL);
					Double rsDivision = 1d;
					int countDivision = 0;
					for (String division : divisionArr) {
						if (division.trim().isEmpty()) {
							continue;
						}
						Double rs = Double.parseDouble(division.trim().replace(",", "."));

						if (countDivision == 0) {
							rsDivision = rs;
						} else {
							rsDivision = rsDivision / rs;
						}
						countDivision++;
					}
					rsMulti *= rsDivision;
				}
				Double rs = getValue(multiArr[multiArrSizeMinus], mapData);
				if (rs != null) {
					rsMulti *= rs;
				}

				if (count == 0) {
					rsMinus = rsMulti;
				} else {
					rsMinus -= rsMulti;
				}
				count++;
			}

			finalValue += rsMinus;
		}
		return finalValue;
	}

	public static void main(String[] args) {
		Map<Integer, Float> mapData = new HashMap<Integer, Float>();
		mapData.put(1, 1f);
		mapData.put(2, 2f);
		mapData.put(3, 3f);
		mapData.put(4, 4f);

		Map<Integer, String> mapCounter = new HashMap<Integer, String>();
		mapCounter.put(1, "CT_1");
		mapCounter.put(2, "CT_3");

		// System.out.println(calculateByFormular("12*CT_1+20*CT_2", mapData));
		System.out.println(calculateByFormular("12 * CT_1 - 20 * CT_3 - 15/3", mapData, mapCounter));
	}
}
