package com.ifi.lab.LabDAO.dao;

import java.util.List;

import com.ifi.lab.LabDAO.model.Lab009LotConsommation;

public interface Lab009LotConsommationDAO {
	List<Lab009LotConsommation> getAllData();
	Lab009LotConsommation findById(Integer id);
	boolean delete(int id);
	Integer createLot(Lab009LotConsommation obj);
	List<Lab009LotConsommation> getAllLotActive() ;
	boolean updateLotConsommation(Lab009LotConsommation lab009LotConsommation);
}
