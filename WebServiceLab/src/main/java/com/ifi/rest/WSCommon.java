package com.ifi.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.ifi.common.bean.MonitorViewBean;
import com.ifi.lab.LabDAO.model.MView;

/**
 * Class common of WebServiceLab
 * 
 * @author ndlong
 * 
 */
public class WSCommon {
	private static final String DATE_FORMAT = "yyyyMMdd";

	/**
	 * Get number day between 2 date if occurs error return 0
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int different2Date(String start, String end) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		try {
			Long startDate = sdf.parse(start).getTime();
			Long endDate = sdf.parse(end).getTime();
			return (int) Math
					.abs((startDate - endDate) / (1000 * 60 * 60 * 24)) + 1;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

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

	public static Double getDNumber(String number) {
		try {

			return Double.parseDouble(number.trim());
		} catch (NumberFormatException nfe) {
			return null;
		}
	}

	public List<MonitorViewBean> convertToMonitorBean(List<MView> listObj) {
		List<MonitorViewBean> listView = new ArrayList<MonitorViewBean>();
		for (MView obj : listObj) {
			MonitorViewBean mBean = new MonitorViewBean();
			mBean.setId(obj.getId());
			mBean.setName(obj.getName());
			listView.add(mBean);
		}
		return listView;
	}
}
