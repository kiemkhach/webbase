package com.ifi.lab.LabDAO.dao;

import java.util.Date;
import java.util.List;

import com.ifi.lab.LabDAO.model.Lab008SaveTemplate;

public interface Lab008SaveTemplateDAO {
	boolean delete(Integer id);

	List<Lab008SaveTemplate> findBySiteId(Integer siteId);

	boolean saveOrUpdate(Lab008SaveTemplate obj);
	
	Lab008SaveTemplate findById(Integer id);

	boolean findByDate(Date fromDate, Date toDate, Integer siteId, Integer id);
	
}
