package com.energisme.service;

import java.util.List;

import com.ifi.lab.LabDAO.model.caloon.CaloonResident;
import com.ifi.lab.LabDAO.model.caloon.CaloonSyndic;

public interface Lab011ResidentService {
	List<CaloonResident> getAllResident();
	boolean saveResident(CaloonResident obj);
	CaloonResident getLab011ResidentById(Integer residentId);
	boolean updateSyndicId(Integer caloonSyndicId,Integer id);
	boolean updateSyndicId(Integer caloonSyndicId,List<Integer> residentIdArr);
	boolean deleteResident(Integer id);
	boolean updateSyndicId(Integer syndicId);
	Integer createResident(CaloonResident obj);
	List<CaloonResident> getAllSyndicId();
	List<CaloonResident> getAllResidentBySyndicId(Integer caloonSyndicId);
	
	CaloonSyndic getCaloonSyndicByUser(Integer userId);
}
