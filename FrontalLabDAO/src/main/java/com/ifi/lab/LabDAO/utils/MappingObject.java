package com.ifi.lab.LabDAO.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.ifi.lab.LabDAO.model.Lab;
import com.ifi.lab.LabDAO.model.MonitorGateway;

public class MappingObject {
	public static List<Lab> mappingObjectToLab(List<Object[]> rows){
		List<Lab> listLab = new ArrayList<Lab>();
		for (Object[] row : rows) {
			Lab lab = new Lab();
			lab.setId(Integer.parseInt(row[0].toString()));
			lab.setUrl(row[1].toString());
			lab.setUri(row[2].toString());
			lab.setName(row[3].toString());
			lab.setSiteId(Integer.parseInt(row[4].toString()));
			listLab.add(lab);
		}
		return listLab;
	}
	public static List<MonitorGateway> mappingObjectToGateway(List<Object[]> rows) throws ParseException{
		List<MonitorGateway> list = new ArrayList<MonitorGateway>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (Object[] row : rows) {
			MonitorGateway obj = new MonitorGateway();
			obj.setId(Integer.parseInt(row[0].toString()));
			obj.setSerialNo(row[1].toString());
			if (row[2] != null) {
				obj.setDateData(dateFormat.parse(row[2].toString()));
			}
			obj.setLastUpdated(dateFormat.parse(row[3].toString()));
			if (row[4] != null) {
				obj.setType(row[4].toString());
			}
			if (row[5] != null) {
				obj.setActive(Integer.parseInt(row[5].toString()));
			}
			if (row[6] != null) {
				obj.setNumFiles(Integer.parseInt(row[6].toString()));
			}
			list.add(obj);
		}
		return list;
	}
}
