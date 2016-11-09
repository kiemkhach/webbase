package com.ifi.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class LabUtils {

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmss");
	private static final SimpleDateFormat sdfShort = new SimpleDateFormat("yyyyMMdd");
	private static final SimpleDateFormat sdf_MMdd = new SimpleDateFormat("MMdd");

	// private static Map<Integer, String> mapMonthFr = new HashMap<Integer,
	// String>();
	//
	// static {
	// mapMonthFr.put(0, "Jan");
	// mapMonthFr.put(1, "Fév");
	// mapMonthFr.put(2, "Mar");
	// mapMonthFr.put(3, "Avr");
	// mapMonthFr.put(4, "Mai");
	// mapMonthFr.put(5, "Juin");
	// mapMonthFr.put(6, "Juil");
	// mapMonthFr.put(7, "Aoû");
	// mapMonthFr.put(8, "Sep");
	// mapMonthFr.put(9, "Oct");
	// mapMonthFr.put(10, "Nov");
	// mapMonthFr.put(11, "Déc.");
	// }

	public static Long getTimeInUTC(Date d){
		SimpleDateFormat sdf = new SimpleDateFormat(FrontalKey.DATE_FORMAT_DAY_1);
		String tmpD = sdf.format(d);
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		try {
			return sdf.parse(tmpD).getTime();
		} catch (Exception e) {
			return null;
		}
		
	}
	
	public static Date convertDateByFormat(String date, String format) {
		if (date == null || date.isEmpty() || format == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static Date convertDateByFormatLocal(String date, String format) {
		if (date == null || date.isEmpty() || format == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String convertDateByFormat(Date date, String format) {
		if (date == null || format == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		return sdf.format(date);
	}
	
	public static String convertDateByFormatNonUTC(Date date, String format) {
		if (date == null || format == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	public static Float convertToFloat(String s) {
		try {
			return Float.parseFloat(s);
		} catch (NumberFormatException nfe) {
			nfe.printStackTrace();
			return null;
		}
	}

	public static String getCurrentDate() {
		Calendar cal = Calendar.getInstance();
		return sdf.format(cal.getTime());
	}

	public static Date getCurrentDay() {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * MMDD --> DD - 1
	 * 
	 * @param date
	 * @return
	 */
	public static String getBeforeOneDay(String dateStr) {
		try {
			Calendar cal = Calendar.getInstance();
			Date date = sdfShort.parse(cal.get(Calendar.YEAR) + dateStr);
			cal.setTime(date);
			cal.add(Calendar.DAY_OF_YEAR, -1);
			return sdf_MMdd.format(cal.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}

	}

	public static String convertNumber(double d, int numberOfDigit) {
		String str = String.valueOf(d);
		return str.substring(0, str.indexOf(".") + numberOfDigit + 1);
	}

	// public static Date getDateBy(int year, int month, int day) {
	// String d = String.valueOf(year);
	// if (month < 10) {
	// d += 0;
	// }
	// d += month;
	// if (day < 10) {
	// d += 0;
	// }
	// d += day;
	// try {
	// return sdfShort.parse(d);
	// } catch (ParseException e) {
	// e.printStackTrace();
	// return null;
	// }
	// }

	public static DateTime getFrenchDate() {
		DateTime dt = new DateTime();
		DateTime dtParis = dt.withZone(DateTimeZone.forID("Europe/Paris"));
		return dtParis;
	}

	/**
	 * Get number day between two date.
	 * 
	 * @param start
	 * @param end
	 * @param dateFormat
	 * @return
	 */
	public static int different2Date(String start, String end, String dateFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		try {
			Long startDate = sdf.parse(start).getTime();
			Long endDate = sdf.parse(end).getTime();
			return (int) Math.abs((startDate - endDate) / (1000 * 60 * 60 * 24)) + 1;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static int different2Date(Date startDate, Date endDate) {
		Long start = startDate.getTime();
		Long end = endDate.getTime();
		return (int) Math.abs((start - end) / (1000 * 60 * 60 * 24)) + 1;
	}

	// public static int different2Date(String start, String end,String format)
	// {
	//
	// Long startDate = start.getTime();
	// Long endDate = end.getTime();
	// return (int) Math.abs((startDate - endDate) / (1000 * 60 * 60 * 24)) + 1;
	// }

	public static Date addMonth(Date d, int month) {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		cal.setTime(d);
		cal.add(Calendar.MONTH, month);
		return cal.getTime();
	}

	public static int getYear(Date d) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		return cal.get(Calendar.YEAR);
	}

	public static int getTypeTimeByDate(Date fromDate, Date toDate) {
		Calendar fromCal = Calendar.getInstance();
		Calendar toCal = Calendar.getInstance();
		fromCal.setTime(fromDate);
		toCal.setTime(toDate);
		// toCal.add(Calendar.DAY_OF_MONTH, 1);
		fromCal.add(Calendar.DAY_OF_MONTH, 7);
		if (fromCal.compareTo(toCal) > 0) {
			return FrontalKey.TYPE_TIME_HOUR;
		} else {
			fromCal.setTime(fromDate);
			toCal.setTime(toDate);
			fromCal.add(Calendar.MONTH, 3);
			if (fromCal.compareTo(toCal) > 0) {
				return FrontalKey.TYPE_TIME_DAY;
			} else {
				return FrontalKey.TYPE_TIME_MONTH;
			}
		}
	}

	/**
	 * Convert date from month to last day of before month 201605 --> 20160430
	 * 
	 * @param date
	 * @return
	 */
	public static String getLastDayOfMonth(String date) {
		Date d = convertDateByFormat(date + "01", FrontalKey.DATE_FORMAT_DAY_1);
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.MONTH, 1);
		cal.add(Calendar.DAY_OF_YEAR, -1);
		return convertDateByFormat(cal.getTime(), FrontalKey.DATE_FORMAT_DAY_1);
	}
	
	public static Date getLastDateOfMonth(String date) {
		Date d = convertDateByFormat(date + "01", FrontalKey.DATE_FORMAT_DAY_1);
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.MONTH, 1);
		cal.add(Calendar.DAY_OF_YEAR, -1);
		return cal.getTime();
	}

	public static Map<String, Integer> putMapSum(Map<String, Integer> map, String key, Integer value) {
		if (key != null && value != null) {
			Integer sum = 0;
			if (map.containsKey(key)) {
				sum = value + map.get(key);
			} else {
				sum = value;
			}
			map.put(key, sum);
		}
		return map;
	}

	public static <K, V> Map<K, V> putMapToMap(Map<K, V> mapDestination, Map<K, V> mapSource) {
		for (Map.Entry<K, V> entry : mapSource.entrySet()) {
			if (mapDestination == null) {
				mapDestination = new TreeMap<K, V>();
			}
			K key = entry.getKey();
			V value = entry.getValue();
			mapDestination = LabUtils.putValueToMap(mapDestination, key, value);
		}

		return mapDestination;
	}

	public static <K, V> Map<K, V> putValueToMap(Map<K, V> map, K key, V value) {
		V valueTmp = null;
		if (map == null) {
			map = new TreeMap<K, V>();
		}
		if (map.containsKey(key)) {
			valueTmp = sumValue(value, map.get(key));
		} else {
			valueTmp = value;
		}
		map.put(key, valueTmp);
		return map;
	}

	public static <K1, K2, V> Map<K1, Map<K2, V>> putValueToMapMap(Map<K1, Map<K2, V>> map, K1 k1, K2 k2, V value) {
		Map<K2, V> map2 = null;
		if (map.containsKey(k1)) {
			map2 = map.get(k1);
		} else {
			map2 = new TreeMap<K2, V>();
		}
		if (map2.containsKey(k2)) {
			map2.put(k2, sumValue(value, map2.get(k2)));
		} else {
			map2.put(k2, value);
		}
		map.put(k1, map2);

		return map;
	}

	public static <K, V> Map<K, Integer> convertMapToPercent(Map<K, V> map, V total) {
		if (map == null || map.isEmpty()) {
			return null;
		}
		Iterator<K> iterator = map.keySet().iterator();
		K first = iterator.next();
		boolean isFirts = true;
		Map<K, Integer> percentMap = new HashMap<K, Integer>();
		int sumPercent = 0;
		isFirts = true;
		for (Map.Entry<K, V> entry : map.entrySet()) {
			if (isFirts) {
				isFirts = false;
				continue;
			}
			int percent = getPercent(entry.getValue(), total);
			sumPercent += percent;
			percentMap.put(entry.getKey(), percent);
		}
		percentMap.put(first, 100 - sumPercent);
		return percentMap;
	}

	public static <K, V> Map<K, Integer[]> convertMapToPercentByAll(Map<K, V> map, V total, Integer totalPercent,
			V totalChild) {
		if (map == null || map.isEmpty()) {
			return null;
		}
		Iterator<K> iterator = map.keySet().iterator();
		K first = iterator.next();
		boolean isFirts = true;
		Map<K, Integer[]> percentMap = new HashMap<K, Integer[]>();
		int sumPercent = 0;
		int sumPercentChild = 0;
		isFirts = true;
		for (Map.Entry<K, V> entry : map.entrySet()) {
			if (isFirts) {
				isFirts = false;
				continue;
			}
			int percent = getPercent(entry.getValue(), total);
			int percentChild = getPercent(entry.getValue(), totalChild);
			sumPercent += percent;
			sumPercentChild += percentChild;
			percentMap.put(entry.getKey(), new Integer[] { percent, percentChild });
		}
		percentMap.put(first, new Integer[] { totalPercent - sumPercent, 100 - sumPercentChild });
		return percentMap;
	}

	public static <K1, K2, V> Map<K1, Map<K2, Integer>> convertMapMapToPercent(Map<K1, Map<K2, V>> map, V total,
			Map<K1, V> mapTotal) {
		Map<K1, Map<K2, Integer>> percentMap = new HashMap<K1, Map<K2, Integer>>();
		for (Map.Entry<K1, Map<K2, V>> entry : map.entrySet()) {
			percentMap.put(entry.getKey(), convertMapToPercent(entry.getValue(), mapTotal.get(entry.getKey())));
		}
		return percentMap;
	}

	public static <K1, K2, V> Map<K1, Map<K2, Integer[]>> convertMapMapToPercentByAll(Map<K1, Map<K2, V>> map, V total,
			Map<K1, Integer> mapTotal) {
		Map<K1, Map<K2, Integer[]>> percentMap = new HashMap<K1, Map<K2, Integer[]>>();
		for (Map.Entry<K1, Map<K2, V>> entry : map.entrySet()) {
			int count = 0;
			V sumChild = null;
			for (Map.Entry<K2, V> entry2 : entry.getValue().entrySet()) {
				if (count == 0) {
					sumChild = entry2.getValue();
				} else {
					sumChild = sumValue(sumChild, entry2.getValue());
				}
				count++;
			}
			percentMap.put(entry.getKey(),
					convertMapToPercentByAll(entry.getValue(), total, mapTotal.get(entry.getKey()), sumChild));
		}
		return percentMap;
	}

	private static <T> Integer getPercent(T value, T total) {
		T percent = devideValue(multiValue(value, 100), total);
		if (percent instanceof Double) {
			return ((Double) percent).intValue();
		} else if (percent instanceof Integer) {
			return ((Integer) percent);
		}
		return 0;
	}

	@SuppressWarnings("unchecked")
	public static <T> T sumValue(T value1, T value2) {
		if (value1 instanceof Double && value2 instanceof Double) {
			Double sum = ((Double) value1 + (Double) value2);
			return (T) sum;
		} else if (value1 instanceof Integer && value2 instanceof Integer) {
			Integer sum = ((Integer) value1 + (Integer) value2);
			return (T) sum;
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	private static <T> T devideValue(T value1, T value2) {
		if (value1 instanceof Double) {

			if ((Double) value2 != 0) {
				Double div = ((Double) value1 / (Double) value2);
				return (T) div;
			}

		} else if (value1 instanceof Integer) {
			if ((Integer) value2 != 0) {
				Integer div = ((Integer) value1 / (Integer) value2);
				return (T) div;
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private static <T> T multiValue(T value1, Integer value2) {
		if (value1 instanceof Double) {
			Double multi = ((Double) value1 * value2);
			return (T) multi;
		} else if (value1 instanceof Integer) {
			Integer multi = ((Integer) value1 * value2);
			return (T) multi;
		} else {
			return null;
		}
	}

	/**
	 * 08/02/2005 -> 02/04/2013 convert to : 01/01/2006,01/01/2007
	 * ...,01/01/2013
	 * 
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	public static List<Long> getListStartYear(Date fromDate, Date toDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(fromDate);
		cal.set(Calendar.DAY_OF_YEAR, 1);
		cal.set(Calendar.MONTH, 0);
		cal.add(Calendar.YEAR, 1);
		List<Long> lst = new ArrayList<Long>();
		while (cal.getTime().compareTo(toDate) < 0) {
			System.out.println(cal.getTime());
			lst.add(cal.getTimeInMillis());
			cal.add(Calendar.YEAR, 1);
		}
		return lst;
	}

	public static List<Long> getListDisplayYear(Date fromDate, Date toDate) {
		List<Long> lst = new ArrayList<Long>();
		Calendar calTmp = Calendar.getInstance();
		Calendar cal = Calendar.getInstance();
		cal.setTime(fromDate);
		calTmp.setTime(fromDate);
		int numberMonth = (11 - cal.get(Calendar.MONTH)) / 2 + cal.get(Calendar.MONTH);
		calTmp.set(Calendar.MONTH, numberMonth);
		System.out.println(calTmp.getTime());
		lst.add(calTmp.getTimeInMillis());

		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.MONTH, 0);
		cal.add(Calendar.YEAR, 1);
		while (cal.getTime().compareTo(toDate) < 0) {
			calTmp.set(Calendar.MONTH, 5);
			calTmp.set(Calendar.YEAR, cal.get(Calendar.YEAR));
			if (calTmp.getTime().compareTo(toDate) < 0) {
				System.out.println(calTmp.getTime());
				lst.add(calTmp.getTimeInMillis());
			}
			cal.add(Calendar.YEAR, 1);
		}
		return lst;
	}
	
	public static List<Integer[]> getListDisplayYearByIndex(Date fromDate, Date toDate, int typeTime) {
		List<Integer[]> lst = new ArrayList<Integer[]>();
		Calendar calTmp = Calendar.getInstance();
		calTmp.setTime(toDate);
		calTmp.set(Calendar.HOUR_OF_DAY, 23);
		toDate = calTmp.getTime();
		calTmp.setTime(fromDate);
		int lastIndex = -1;
		while (calTmp.getTime().compareTo(toDate) <= 0) {
			Date fromDateTmp = calTmp.getTime();
			calTmp.add(Calendar.YEAR, 1);
			calTmp.set(Calendar.DAY_OF_YEAR, 1);
			calTmp.set(Calendar.MONTH, 0);
			calTmp.add(Calendar.HOUR_OF_DAY, -1);
			Date toDateTmp = null;
			if(calTmp.getTime().compareTo(toDate) >= 0){
				toDateTmp = toDate;
			}else{
				toDateTmp = calTmp.getTime();
			}
			
			// get index of time
			int index = ++lastIndex ;
			if(typeTime == FrontalKey.TYPE_TIME_HOUR){
				long numberHour = (toDateTmp.getTime() - fromDateTmp.getTime())/ 3600000;
				lastIndex += (int)numberHour;
			}else if(typeTime == FrontalKey.TYPE_TIME_DAY){
				long numberDay = (toDateTmp.getTime() - fromDateTmp.getTime())/ (3600000 * 24);
				lastIndex += (int)numberDay;
			}else{
				long numberMonth = (toDateTmp.getTime() - fromDateTmp.getTime())/ ((long)3600000 * 24  * 30);
				lastIndex += (int)numberMonth;
			}
			lst.add(new Integer[]{index ,fromDateTmp.getYear()+ 1900});
			
			calTmp.add(Calendar.HOUR_OF_DAY, 1);
		}
		return lst;
	}

	public static List<Integer[]> getListIndexYear(Date fromDate, Date toDate, int typeTime) {
		List<Integer[]> lst = new ArrayList<Integer[]>();
		Calendar calTmp = Calendar.getInstance();
		calTmp.setTime(toDate);
		calTmp.set(Calendar.HOUR_OF_DAY, 23);
		toDate = calTmp.getTime();
		calTmp.setTime(fromDate);
		int lastIndex = 0;
		while (calTmp.getTime().compareTo(toDate) <= 0) {
			Date fromDateTmp = calTmp.getTime();
			calTmp.add(Calendar.YEAR, 1);
			calTmp.set(Calendar.DAY_OF_YEAR, 1);
			calTmp.set(Calendar.MONTH, 0);
			calTmp.add(Calendar.HOUR_OF_DAY, -1);
			Date toDateTmp = null;
			if(calTmp.getTime().compareTo(toDate) >= 0){
				toDateTmp = toDate;
			}else{
				toDateTmp = calTmp.getTime();
			}
			
			// get index of time
			int index = lastIndex;
			if(typeTime == FrontalKey.TYPE_TIME_HOUR){
				long numberHour = (toDateTmp.getTime() - fromDateTmp.getTime())/ 3600000;
				index += (int)numberHour/2;
				lastIndex += (int)numberHour;
			}else if(typeTime == FrontalKey.TYPE_TIME_DAY){
				long numberDay = (toDateTmp.getTime() - fromDateTmp.getTime())/ (3600000 * 24);
				index += (int)numberDay/2;
				lastIndex += (int)numberDay;
			}else{
				long numberMonth = (toDateTmp.getTime() - fromDateTmp.getTime())/ ((long)3600000 * 24  * 30);
				index += (int)numberMonth/2;
				lastIndex += (int)numberMonth;
			}
			lst.add(new Integer[]{index ,fromDateTmp.getYear()+ 1900});
			
			calTmp.add(Calendar.HOUR_OF_DAY, 1);
		}
		return lst;
	}

	/**
	 * Return next month: if d is the last day of month return last day of next
	 * month
	 * 
	 * @param d
	 * @return
	 */
	public static Date getNextMonth(Date d) {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		cal.setTime(d);
		boolean isLastDayOfMonth = false;
		if (cal.getActualMaximum(Calendar.DAY_OF_MONTH) == cal.get(Calendar.DAY_OF_MONTH)) {
			isLastDayOfMonth = true;
		}
		cal.add(Calendar.MONTH, 1);
		if (isLastDayOfMonth) {
			cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		}
		return cal.getTime();
	}

	/**
	 * Return previous month: if d is the last day of month return last day bo
	 * previous month
	 * 
	 * @param d
	 * @return
	 */
	public static Date getPreviousMonth(Date d) {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		cal.setTime(d);
		boolean isLastDayOfMonth = false;
		if (cal.getActualMaximum(Calendar.DAY_OF_MONTH) == cal.get(Calendar.DAY_OF_MONTH)) {
			isLastDayOfMonth = true;
		}
		cal.add(Calendar.MONTH, -1);
		if (isLastDayOfMonth) {
			cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		}
		return cal.getTime();
	}

	/**
	 * convert lit to m3
	 * 
	 * @param value
	 * @param unit
	 * @return
	 */
	public static Integer convertToCubicMeter(Integer value, String unitModule, String unitConfig) {
		if (unitModule == null || value == null || unitConfig == null) {
			return value;
		}

		if (unitConfig.equalsIgnoreCase(FrontalKey.VOLUME_UNIT_M3)
				&& (FrontalKey.VOLUME_UNIT_L.equalsIgnoreCase(unitModule)
						|| FrontalKey.VOLUME_UNIT_LITERS.equalsIgnoreCase(unitModule)
						|| FrontalKey.VOLUME_UNIT_LITRE.equalsIgnoreCase(unitModule)
						|| FrontalKey.VOLUME_UNIT_LITRES.equalsIgnoreCase(unitModule))) {
			return (int) (value * 0.001);
		}
		return value;
	}

	/**
	 * conver MGH -> KWH
	 * 
	 * @param value
	 * @param unit
	 * @return
	 */
	public static Integer convertToKWH(Integer value, String unit) {
		if (unit == null || value == null) {
			return value;
		}
		if (FrontalKey.CONSOMMATION_UNIT_MWH.equalsIgnoreCase(unit)) {
			return (int) (value * 1000);
		}
		return value;
	}

	public static Date getfirstDayOfCurrentMonth() {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public static List<Integer> getLstByString(String numbers) {
		String[] arr = numbers.split(FrontalKey.COMMA);
		List<Integer> lst = new ArrayList<Integer>();
		for (String tmp : arr) {
			if (!tmp.isEmpty()) {
				lst.add(Integer.parseInt(tmp));
			}
		}
		return lst;
	}

	public static Integer sumAllOfList(List<Integer> lst) {
		Integer sum = 0;
		for (Integer i : lst) {
			sum += i;
		}
		return sum;
	}

	/**
	 * Get before day with firt day of month
	 * 
	 * @return
	 */
	public static String getPreviousMonth(String date, String dateFormat) {
		Date dateD = convertDateByFormat(date, dateFormat);
		Date dateConvert = getPreviousMonth(dateD);
		return convertDateByFormat(dateConvert, dateFormat);
	}

	public static Double convertStringToDouble(String number) {
		return Double.parseDouble(number.replace(",", "."));
	}

	public static List<Long> getLstCategoryByType(Date fromDate, Date toDate, int typeTime) {

		List<Long> lstCategory = new ArrayList<Long>();
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		if (typeTime == FrontalKey.TYPE_TIME_HOUR) {
			cal.setTime(toDate);
			cal.set(Calendar.HOUR_OF_DAY, 23);
			toDate = cal.getTime();
		}
		cal.setTime(fromDate);

		while (cal.getTime().compareTo(toDate) <= 0) {
			Long time = null;
			time = cal.getTime().getTime();
			if (typeTime == FrontalKey.TYPE_TIME_HOUR) {
				cal.add(Calendar.HOUR_OF_DAY, 1);
			} else if (typeTime == FrontalKey.TYPE_TIME_DAY) {
				cal.add(Calendar.DAY_OF_YEAR, 1);
			} else if (typeTime == FrontalKey.TYPE_TIME_MONTH) {
				cal.add(Calendar.MONTH, 1);
			}

			lstCategory.add(time);
		}

		return lstCategory;
	}

	public static void main(String[] args) {
		
		Date fromDate = convertDateByFormat("20150831", "yyyyMMdd");
		Date toDate = convertDateByFormat("20160927", "yyyyMMdd");
		int typeTime = 3;
		System.out.println(getLstCategoryByType(fromDate, toDate, typeTime));
		
		List<Integer[]> lst = getListDisplayYearByIndex(fromDate,toDate, typeTime);
		for (Integer[] arr : lst) {
			System.out.print(arr[0]);
			System.out.print("--");
			System.out.print(arr[1]);
			System.out.println("");
		}
		
	}
}
