package com.ifi.lab.LabDAO.dao.Idex;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ifi.common.bean.Idex.Lab010IdexRelevesBean;
import com.ifi.lab.LabDAO.model.Idex.IdexReleves;

public interface IdexRelevesDAO {
	Map<Integer, List<IdexReleves>> getMapByCompteur(List<Integer> compteurLst);
	Lab010IdexRelevesBean getSumMapByInstallation(Integer installationId,Date fromDate, Date toDate);
	List<IdexReleves> findByCounter(int idexCounterId);
	boolean deleteByCounter(int idexCounterId);
	boolean saveRelevesList(List<IdexReleves> relevesList);
	List<IdexReleves> getAll();	
}
