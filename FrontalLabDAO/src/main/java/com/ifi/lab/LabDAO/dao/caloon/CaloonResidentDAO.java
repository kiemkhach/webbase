package com.ifi.lab.LabDAO.dao.caloon;

import java.util.List;

import com.ifi.lab.LabDAO.model.caloon.CaloonResident;

public interface CaloonResidentDAO {

	CaloonResident findById(Integer caloonResidentId);

	List<CaloonResident> findBySyndic(Integer caloonSyndicId);

	List<CaloonResident> getAllSyndicId();

	List<CaloonResident> getAllSyndicIdNull();

	Integer createCaloonResident(CaloonResident obj);

	Boolean updateSyndicId(Integer caloonSyndicId, Integer id);
	
	Boolean updateSyndicId(Integer caloonSyndicId, List<Integer> residentIdArr);

	Boolean updateSyndicId(Integer syndicId);

	Boolean deleteResident(Integer id);

	boolean saveOrUpdate(CaloonResident obj);

	List<CaloonResident> findByFilter(Integer caloonSyndicId, Boolean isSuperficieGreater, Integer superficie,
			Boolean logements);
	
	CaloonResident findByUser(Integer caloonUserId);
	
}
