package com.ifi.lab.LabDAO.dao.caloon;

import java.util.List;

import com.ifi.lab.LabDAO.model.caloon.CaloonConsommationRange;
import com.ifi.lab.LabDAO.model.caloon.CaloonSyndic;

public interface CaloonSyndicDAO {

	CaloonSyndic findById(Integer caloonSyndicId);

	List<CaloonConsommationRange> getAllConsommationRange();

	Integer createSyndic(CaloonSyndic obj);

	Boolean deleteSyndic(Integer id);

	List<CaloonSyndic> getAll();

	Boolean saveOrUpdate(CaloonSyndic obj);

	CaloonConsommationRange getConsommationRangeById(Integer id);
	
	List<CaloonSyndic> searchByKey(String key);
	
	List<CaloonSyndic> findByUser(Integer caloonUserId);
	
	CaloonSyndic findDefalutByUser(Integer caloonUserId);
}
