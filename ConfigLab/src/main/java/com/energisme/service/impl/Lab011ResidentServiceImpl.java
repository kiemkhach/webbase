/**
 * 
 */
package com.energisme.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.energisme.service.Lab011ResidentService;
import com.ifi.lab.LabDAO.dao.caloon.CaloonResidentDAO;
import com.ifi.lab.LabDAO.dao.caloon.CaloonSyndicDAO;
import com.ifi.lab.LabDAO.model.caloon.CaloonResident;
import com.ifi.lab.LabDAO.model.caloon.CaloonSyndic;

public class Lab011ResidentServiceImpl implements Lab011ResidentService {

	private static final Logger logger = LoggerFactory.getLogger(Lab006ClientServiceImpl.class);

	@Autowired
	private CaloonResidentDAO caloonResidentDAO;
	
	@Autowired
	private CaloonSyndicDAO caloonSyndicDAO;

	@Override
	public List<CaloonResident> getAllResident() {
		List<CaloonResident> listResidents = caloonResidentDAO.getAllSyndicId();

		return listResidents;
	}

	@Override
	public CaloonResident getLab011ResidentById(Integer residentId) {
		CaloonResident cal = caloonResidentDAO.findById(residentId);
		return cal;
	}

	@Override
	public boolean saveResident(CaloonResident obj) {
		CaloonResident resident = null;
		if(obj.getId() != null){
			resident = caloonResidentDAO.findById(obj.getId());
		}else {
			resident = new CaloonResident();
			Integer setCaloonSyndicId = obj.getCaloonSyndicId().intValue() != 0 ? obj.getCaloonSyndicId().intValue() : 0;
			resident.setCaloonSyndicId(setCaloonSyndicId);
		}
		String appartementNumber = obj.getAppartementNumber() != null ? obj.getAppartementNumber().trim() : "";
		String chauffage = obj.getChauffage() != null ? obj.getChauffage().trim() : "";
		String eauChaude = obj.getEauChaude() != null ? obj.getEauChaude().trim() : "";
		String eauFroide = obj.getEauFroide() != null ? obj.getEauFroide().trim() : "";
		String clientName = obj.getClientName() != null ? obj.getClientName().trim() : "";
		Integer superficie = obj.getSuperficie();
		Boolean logements = obj.getLogements();
		resident.setAppartementNumber(appartementNumber);
		resident.setChauffage(chauffage);
		resident.setEauChaude(eauChaude);
		resident.setEauFroide(eauFroide);
		resident.setClientName(clientName);
		resident.setLogements(logements);
		resident.setSuperficie(superficie);
		
		return caloonResidentDAO.saveOrUpdate(resident);
	}

	@Override
	public boolean updateSyndicId(Integer caloonSyndicId, Integer id) {
		return caloonResidentDAO.updateSyndicId(caloonSyndicId, id);
	}

	@Override
	public boolean updateSyndicId(Integer caloonSyndicId,List<Integer> residentIdArr){
		return caloonResidentDAO.updateSyndicId(caloonSyndicId, residentIdArr);
	}
	
	@Override
	public boolean deleteResident(Integer id) {
		return caloonResidentDAO.deleteResident(id);
	}

	@Override
	public boolean updateSyndicId(Integer syndicId) {
		return caloonResidentDAO.updateSyndicId(syndicId);
	}
	@Override
	public Integer createResident(CaloonResident obj) {
		return caloonResidentDAO.createCaloonResident(obj);
	}

	@Override
	public List<CaloonResident> getAllSyndicId() {
		List<CaloonResident> caloonResidentsLst = caloonResidentDAO.getAllSyndicIdNull();
		return caloonResidentsLst;
	}

	@Override
	public List<CaloonResident> getAllResidentBySyndicId(Integer caloonSyndicId) {
		// TODO Auto-generated method stub
		List<CaloonResident> lst = caloonResidentDAO.findBySyndic(caloonSyndicId);
		return lst;
	}
	
	@Override
	public CaloonSyndic getCaloonSyndicByUser(Integer userId){
		return caloonSyndicDAO.findDefalutByUser(userId);
	}
	
}
