package com.ifi.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.simple.JSONObject;

import com.ifi.common.bean.MonitorGatewayBean;
import com.ifi.common.bean.MonitorModuleBean;
import com.ifi.common.bean.MonitorViewBean;

public class ConvertJSONToBean {
	public static MonitorGatewayBean toMonitorGatewayBean(JSONObject input)
			throws ParseException {
		if (input != null) {
			MonitorGatewayBean bean = new MonitorGatewayBean();
			bean.setId(Integer.parseInt(input.get("id").toString()));
			bean.setSerialNo(input.get("serialNo").toString());
			if (input.get("dateData") != null) {
				Date dateDate = new Date(Long.parseLong(input.get("dateData")
						.toString()));
				SimpleDateFormat dateFormat = new SimpleDateFormat(
						"dd/MM/yyyy HH:mm:ss");
				bean.setDateData(dateFormat.format(dateDate));
			}
			bean.setLastUpdated(input.get("lastUpdated").toString());
			if (input.get("type") != null) {
				String type = input.get("type").toString();
				if (FrontalKey.MONITOR_TYPE_PRIOS.equals(type)) {
					type = "DEVICE";
				} else {
					type = "LCPC";
				}
				bean.setType(type);
			}
			bean.setNumFiles(Integer.parseInt(input.get("numFiles").toString()));
			return bean;
		}
		return null;
	}

	public static MonitorModuleBean toMonitorModuleBean(JSONObject input)
			throws ParseException {
		if (input != null) {
			MonitorModuleBean bean = new MonitorModuleBean();
			bean.setId(Integer.parseInt(input.get("id").toString()));
			bean.setGatewayId(Integer.parseInt(input.get("gatewayId")
					.toString()));
			if (input.get("dateData") != null) {
				long dateData = Long
						.parseLong(input.get("dateData").toString());
				Date date = new Date(dateData);
				SimpleDateFormat dateFormat = new SimpleDateFormat(
						"dd/MM/yyyy HH:mm:ss");
				bean.setDateData(dateFormat.format(date));
			}
			if (input.get("type") != null) {
				bean.setType(input.get("type").toString());
			}
			if (input.get("index") != null) {
				String index = input.get("index").toString();
				if ("IZAR".equals(input.get("type").toString())) {
					String[] arr = index.split("\\.");
					index = arr[0];
				}
				bean.setIndex(index);
			}
			bean.setModuleNo(input.get("moduleNo").toString());
			return bean;
		}
		return null;
	}

	public static MonitorViewBean toMonitorViewBean(JSONObject input)
			throws ParseException {
		if (input != null) {
			MonitorViewBean bean = new MonitorViewBean();
			bean.setId(Integer.parseInt(input.get("id").toString()));
			bean.setName(input.get("name").toString());
			return bean;
		}
		return null;
	}
}
