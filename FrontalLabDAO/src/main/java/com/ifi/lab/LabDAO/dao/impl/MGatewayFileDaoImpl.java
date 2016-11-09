package com.ifi.lab.LabDAO.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ifi.lab.LabDAO.dao.MGatewayFileDao;
import com.ifi.lab.LabDAO.model.MGatewayFile;

@Repository("mGatewayFileDao")
@Transactional
public class MGatewayFileDaoImpl implements MGatewayFileDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(MGatewayFileDaoImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	public boolean save(MGatewayFile file) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(file);
		LOGGER.info("Insert success");
		return true;
	}

	public MGatewayFile findByID(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM MGatewayFile m WHERE m.id = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		@SuppressWarnings("unchecked")
		List<MGatewayFile> lst = (List<MGatewayFile>) query.list();
		if (lst != null && lst.size() > 0) {
			return lst.get(0);
		} else {
			return null;
		}
	}

	public List<MGatewayFile> getByGateway(Integer gatewayId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM MGatewayFile m WHERE m.gatewayId = :gatewayId";
		Query query = session.createQuery(hql);
		query.setParameter("gatewayId", gatewayId);
		@SuppressWarnings("unchecked")
		List<MGatewayFile> lst = (List<MGatewayFile>) query.list();
		return lst;
	}
	
	public int deleteBeforeDay(Date beforeDate){
		Session session = sessionFactory.getCurrentSession();
		String hql = "DELETE FROM MGatewayFile m WHERE m.dateData < :dateData";
		Query query = session.createQuery(hql);
		query.setParameter("dateData", beforeDate);
		return query.executeUpdate();
	}

}
