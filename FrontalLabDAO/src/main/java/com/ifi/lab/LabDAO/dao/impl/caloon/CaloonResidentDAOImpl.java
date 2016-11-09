package com.ifi.lab.LabDAO.dao.impl.caloon;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ifi.lab.LabDAO.dao.caloon.CaloonResidentDAO;
import com.ifi.lab.LabDAO.model.Lab009EnergyType;
import com.ifi.lab.LabDAO.model.caloon.CaloonResident;

@Repository("caloonResidentDAO")
@Transactional
public class CaloonResidentDAOImpl implements CaloonResidentDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	public CaloonResident findById(Integer caloonResidentId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM CaloonResident c WHERE c.id = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", caloonResidentId);
		@SuppressWarnings("unchecked")
		List<CaloonResident> listlab = (List<CaloonResident>) query.list();
		if (listlab != null && listlab.size() > 0) {
			return listlab.get(0);
		} else {
			return null;
		}
	}
	
	public CaloonResident findByUser(Integer caloonUserId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM CaloonResident c WHERE c.caloonUserId = :caloonUserId";
		Query query = session.createQuery(hql);
		query.setParameter("caloonUserId", caloonUserId);
		@SuppressWarnings("unchecked")
		List<CaloonResident> listlab = (List<CaloonResident>) query.list();
		if (listlab != null && listlab.size() > 0) {
			return listlab.get(0);
		} else {
			return null;
		}
	}
	
	public List<CaloonResident> findBySyndic(Integer caloonSyndicId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM CaloonResident c WHERE c.caloonSyndicId = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", caloonSyndicId);
		@SuppressWarnings("unchecked")
		List<CaloonResident> listlab = (List<CaloonResident>) query.list();
		return listlab;
	}
	
	public List<CaloonResident> findByFilter(Integer caloonSyndicId, Boolean isSuperficieGreater, Integer superficie,
			Boolean logements){
		Session session = sessionFactory.getCurrentSession();
		StringBuilder sb = new StringBuilder();
		sb.append("FROM CaloonResident c WHERE c.caloonSyndicId = :id ");
		if( isSuperficieGreater != null && superficie != null){
			if(isSuperficieGreater){
				sb.append(" AND c.superficie > :superficie ");
			}else{
				sb.append(" AND c.superficie < :superficie ");
			}
		}
		if(logements != null){
			sb.append(" AND c.logements = :logements ");
		}
		Query query = session.createQuery(sb.toString());
		query.setParameter("id", caloonSyndicId);
		if( isSuperficieGreater != null && superficie != null){
			query.setInteger("superficie", superficie);
		}
		if(logements != null){
			query.setBoolean("logements", logements);
		}
		@SuppressWarnings("unchecked")
		List<CaloonResident> listlab = (List<CaloonResident>) query.list();
		return listlab;
	}

	public List<CaloonResident> getAllSyndicId() {
		Session session = sessionFactory.getCurrentSession();
//		String hql = "FROM CaloonResident c WHERE c.caloonSyndicId is NULL";
		String hql = "FROM CaloonResident c ";
		Query query = session.createQuery(hql);
		@SuppressWarnings("unchecked")
		List<CaloonResident> list = query.list();
		if (list == null || list.size() == 0) {
			return null;
		}
		return list;
	}
	
	public List<CaloonResident> getAllSyndicIdNull() {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM CaloonResident c WHERE c.caloonSyndicId is NULL";
//		String hql = "FROM CaloonResident c";
		Query query = session.createQuery(hql);
		@SuppressWarnings("unchecked")
		List<CaloonResident> list = query.list();
		if (list == null || list.size() == 0) {
			return null;
		}
		return list;
	}

	public Integer createCaloonResident(CaloonResident obj) {
		Session session = sessionFactory.getCurrentSession();
		session.save(obj);
		return obj.getId();
	}

	public Boolean updateSyndicId(Integer caloonSyndicId, Integer id) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "UPDATE CaloonResident c SET c.caloonSyndicId = :caloonSyndicId WHERE c.id = :id";
		Query query = session.createQuery(hql);
		query.setParameter("caloonSyndicId", caloonSyndicId);
		query.setParameter("id", id);
		if(query.executeUpdate()<0){
			return false;
		}
		return true;
		
	}
	
	public Boolean updateSyndicId(Integer caloonSyndicId, List<Integer> residentIdArr) {
		if( residentIdArr == null || residentIdArr.isEmpty()){
			return true;
		}
		Session session = sessionFactory.getCurrentSession();
		StringBuilder hqlSb = new StringBuilder();
		hqlSb.append("UPDATE CaloonResident c SET c.caloonSyndicId = ? WHERE c.id IN (");
		
		int sizeMinus = residentIdArr.size() - 1;
		for(int i = 0 ;i < sizeMinus ;i++){
			hqlSb.append("?,");
		}
		hqlSb.append("?)");
		Query query = session.createQuery(hqlSb.toString());
		int count = 0;
		query.setParameter(count++, caloonSyndicId);
		for (Integer residentId : residentIdArr) {
			query.setParameter(count++, residentId);
		}
		if(query.executeUpdate()<0){
			return false;
		}
		return true;
		
	}


	public boolean saveOrUpdate(CaloonResident obj) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(obj);
		return true;
	}


	public Boolean deleteResident(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "DELETE FROM CaloonResident c WHERE c.id = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		if(query.executeUpdate()<0){
			return false;
		}
		return true;
	}

	public Boolean updateSyndicId(Integer syndicId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "UPDATE CaloonResident c SET c.caloonSyndicId = NULL WHERE c.caloonSyndicId = :caloonSyndicId";
		Query query = session.createQuery(hql);
		query.setParameter("caloonSyndicId", syndicId);
		if(query.executeUpdate()<0){
			return false;
		}
		return true;
	}


}
