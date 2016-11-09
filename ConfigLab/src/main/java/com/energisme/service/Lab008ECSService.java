package com.energisme.service;

import java.util.List;

import com.energisme.bean.NumConfigLab008ECS;
import com.ifi.lab.LabDAO.model.Lab008ECS;

public interface Lab008ECSService {
	List<Lab008ECS> getListECSBySiteId(Integer siteId);
	String saveECSList(NumConfigLab008ECS obj);
	boolean deleteECS(Integer id);
	Integer getNewstId();
	Lab008ECS getECSById(Integer id);
	List<Lab008ECS> getECSByType(int configlab8Id, int type);
}
