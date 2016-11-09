package com.ifi.lab.LabDAO.dao;

import java.util.List;

import com.ifi.lab.LabDAO.model.Lab009Provider;

public interface Lab009ProviderDAO {
	List<Lab009Provider> getAllData();
	Lab009Provider findById(Integer id);
	Integer createPro(Lab009Provider obj);
	boolean delete(int id);
	boolean updateProvider(Lab009Provider lab009Provider);
}
