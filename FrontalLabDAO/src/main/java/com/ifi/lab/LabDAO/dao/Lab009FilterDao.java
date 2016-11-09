package com.ifi.lab.LabDAO.dao;

import java.util.List;

import com.ifi.lab.LabDAO.model.Lab009Filter;

public interface Lab009FilterDao {
	List<Lab009Filter> getAll();

	List<Lab009Filter> findByIDs(List<Integer> idLst);
	
	Integer create(Lab009Filter filter);
}
