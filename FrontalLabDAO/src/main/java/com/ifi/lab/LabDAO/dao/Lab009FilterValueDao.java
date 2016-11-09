package com.ifi.lab.LabDAO.dao;

import java.util.List;

import com.ifi.lab.LabDAO.model.Lab009FilterValue;

public interface Lab009FilterValueDao {
	List<Lab009FilterValue> findByFilterID(Integer filterId);
}
