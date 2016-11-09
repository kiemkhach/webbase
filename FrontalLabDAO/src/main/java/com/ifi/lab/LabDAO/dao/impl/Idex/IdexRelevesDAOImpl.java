package com.ifi.lab.LabDAO.dao.impl.Idex;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ifi.common.bean.Idex.Lab010IdexRelevesBean;
import com.ifi.lab.LabDAO.dao.Idex.IdexRelevesDAO;
import com.ifi.lab.LabDAO.model.Idex.IdexCostDetail;
import com.ifi.lab.LabDAO.model.Idex.IdexReleves;

@Repository("idexRelevesDAO")
@Transactional
public class IdexRelevesDAOImpl implements IdexRelevesDAO{
	@Autowired
	private SessionFactory sessionFactory;
	
	public Map<Integer, List<IdexReleves>> getMapByCompteur(List<Integer> compteurLst) {
		
		Map<Integer, List<IdexReleves>> map = new HashMap<Integer, List<IdexReleves>>();
		if(compteurLst == null || compteurLst.isEmpty() ){
			return map;
		}
		Session session = sessionFactory.getCurrentSession();
		StringBuilder sb = new StringBuilder();
		sb.append("FROM IdexReleves i LEFT JOIN fetch i.idexCounter  WHERE  i.idexCounter.idexCounterId IN (");
		for(int i = 0 ;i< compteurLst.size() -1 ;i++){
			sb.append("?,");
		}
		sb.append("?) ORDER BY month ");
		Query query = session.createQuery(sb.toString());
		int count = 0;
		for(int i = 0 ;i< compteurLst.size() ;i++){
			query.setParameter(count ++, compteurLst.get(i));
		}
		@SuppressWarnings("unchecked")
		List<IdexReleves> list = query.list();
		for(IdexReleves idexReleves: list ){
			List<IdexReleves> dataList = null;
			Integer key = idexReleves.getIdexCounter().getIdexCounterId();
//			if(idexReleves.getValue() != null){
//				if(idexReleves.getIdexCounter().getCoefficientDeConversion() != null){
//					idexReleves.setValue(idexReleves.getValue() * idexReleves.getIdexCounter().getCoefficientDeConversion());
//				}
//				if(idexReleves.getIdexCounter().getCoefficientEnergetique() != null){
//					idexReleves.setValue(idexReleves.getValue() * idexReleves.getIdexCounter().getCoefficientEnergetique());
//				}
//			}
			if(map.containsKey(key)){
				dataList = map.get(key);
			}else{
				dataList = new ArrayList<IdexReleves>();
			}
			dataList.add(idexReleves);
			map.put(key, dataList);
		}
		return map;
	}
	
	public Lab010IdexRelevesBean getSumMapByInstallation(Integer installationId,Date fromDate, Date toDate){
		Session session = sessionFactory.getCurrentSession();
		StringBuilder sb = new StringBuilder();
		sb.append("FROM IdexReleves i LEFT JOIN fetch i.idexCounter WHERE  i.idexCounter.idexInstallation.idexInstallationId = :installationId ORDER BY month");
		Query query = session.createQuery(sb.toString());
		query.setInteger("installationId", installationId);
		@SuppressWarnings("unchecked")
		List<IdexReleves> list = query.list();
		Map<Integer, List<IdexReleves>> map = new HashMap<Integer, List<IdexReleves>>();
		for(IdexReleves idexReleves: list ){
			List<IdexReleves> dataList = null;
			Integer key = idexReleves.getIdexCounter().getIdexCounterId();
			
			if(map.containsKey(key)){
				dataList = map.get(key);
			}else{
				dataList = new ArrayList<IdexReleves>();
			}
			dataList.add(idexReleves);
			map.put(key, dataList);
		}
		
		Map<Integer,Float> mapRs = new HashMap<Integer, Float>();
		for(Map.Entry<Integer, List<IdexReleves>> entry : map.entrySet()){
			Integer key = entry.getKey();
			List<IdexReleves> valueLst = entry.getValue();
			Float sum = 0f;
			Float beforeValue = null;
			
			if(valueLst.size() >0){
				for (IdexReleves idexReleves : valueLst) {
					if(fromDate == null || toDate == null || (idexReleves.getMonth().compareTo(fromDate) >= 0 && idexReleves.getMonth().compareTo(toDate) <= 0)){
						Float consommation = 0f;
						if(beforeValue != null){
							consommation = idexReleves.getValue() - beforeValue;
							if(consommation >= 0){
								if(idexReleves.getIdexCounter().getCoefficientDeConversion() != null){
									consommation = consommation* idexReleves.getIdexCounter().getCoefficientDeConversion();
								}
								if(idexReleves.getIdexCounter().getCoefficientEnergetique() != null){
									consommation = consommation* idexReleves.getIdexCounter().getCoefficientEnergetique();
								}
								sum += consommation;
							}
						}
						if(consommation >= 0){
							beforeValue = idexReleves.getValue();
						}
					}
					
					
				}

			}
			mapRs.put(key, sum);
		}
		
		if (fromDate == null || toDate == null) {
			if (!list.isEmpty()) {
				fromDate = list.get(0).getMonth();
				toDate = list.get(list.size() - 1).getMonth();
			}
		}
		Lab010IdexRelevesBean bean = new Lab010IdexRelevesBean();
		bean.setMapData(mapRs);
		bean.setFromDate(fromDate);
		bean.setToDate(toDate);
		return bean;
	}

	public List<IdexReleves> findByCounter(int idexCounterId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM IdexReleves r WHERE r.idexCounter.idexCounterId = :idexCounterId";
		Query query = session.createQuery(hql);
		query.setParameter("idexCounterId", idexCounterId);
		@SuppressWarnings("unchecked")
		List<IdexReleves> idexRelevesList = query.list();
		if (idexRelevesList == null || idexRelevesList.size() == 0) {
			return null;
		}
		return idexRelevesList;
	}

	public boolean deleteByCounter(int idexCounterId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "DELETE FROM IdexReleves r "
				+ "WHERE r.idexCounter.idexCounterId = :idexCounterId";
		try{
			Query query = session.createQuery(hql);
			query.setParameter("idexCounterId", idexCounterId);
			query.executeUpdate();
			return true;
		}catch(Exception e){
			return false;
		}
	}

	public boolean saveRelevesList(List<IdexReleves> relevesList) {
		Session session = sessionFactory.getCurrentSession();
		try{
			for (int i=0; i < relevesList.size(); i++) {
				session.save(relevesList.get(i));
				if(i % 50 == 0){
					session.flush();
					session.clear();
				}
			}
			return true;
		}catch(Exception e){
			return false;
		}
	}

	public List<IdexReleves> getAll() {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM IdexReleves";
		Query query = session.createQuery(hql);
		@SuppressWarnings("unchecked")
		List<IdexReleves> idexRelevesList = query.list();
		if (idexRelevesList == null || idexRelevesList.size() == 0) {
			return null;
		}
		return idexRelevesList;
	}
}
