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

import com.ifi.lab.LabDAO.dao.MonitorModuleDAO;
import com.ifi.lab.LabDAO.model.MonitorModule;

@Repository("monitorModuleDAO")
@Transactional
public class MonitorModuleDAOImpl implements MonitorModuleDAO {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(MonitorModuleDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	public boolean save(MonitorModule obj) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(obj);
		LOGGER.info("Insert success");
		return true;
	}

	public boolean saveAll(List<MonitorModule> listObj) {
		Session session = sessionFactory.getCurrentSession();
		for (MonitorModule obj : listObj) {
			session.saveOrUpdate(obj);
		}
		LOGGER.info("Insert success");
		return true;
	}

	public MonitorModule findById(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM MonitorModule m WHERE m.id = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		@SuppressWarnings("unchecked")
		List<MonitorModule> listMonitor = (List<MonitorModule>) query.list();
		if (listMonitor != null && listMonitor.size() > 0) {
			return listMonitor.get(0);
		} else {
			return new MonitorModule();
		}
	}

	public MonitorModule findByModuleNo(String moduleNo) {
		Session session = null;
		session = sessionFactory.getCurrentSession();
		String hql = "FROM MonitorModule m WHERE m.moduleNo = :moduleNo";
		Query query = session.createQuery(hql);
		query.setParameter("moduleNo", moduleNo);
		@SuppressWarnings("unchecked")
		List<MonitorModule> listMonitor = (List<MonitorModule>) query.list();
		if (listMonitor != null && listMonitor.size() > 0) {
			return listMonitor.get(0);
		} else {
			return new MonitorModule();
		}
	}
	
	public MonitorModule findByModuleAndDate(String moduleNo, Date dateData) {
		Session session = null;
		session = sessionFactory.getCurrentSession();
		String hql = "FROM MonitorModule m WHERE m.moduleNo = :moduleNo AND m.dateData = :dateData";
		Query query = session.createQuery(hql);
		query.setParameter("moduleNo", moduleNo);
		query.setParameter("dateData", dateData);
		@SuppressWarnings("unchecked")
		List<MonitorModule> listMonitor = (List<MonitorModule>) query.list();
		if (listMonitor != null && listMonitor.size() > 0) {
			return listMonitor.get(0);
		} else {
			return new MonitorModule();
		}
	}

	public List<MonitorModule> getModuleByDay(Integer gatewayId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM MonitorModule m WHERE m.active = 1 AND m.gatewayId = :gatewayId ORDER BY m.dateData DESC";
		Query query = session.createQuery(hql);
		query.setInteger("gatewayId", gatewayId);
		@SuppressWarnings("unchecked")
		List<MonitorModule> list = query.list();
		return list;
	}
	
	public boolean delete(MonitorModule obj) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(obj);
		return true;
	}
	
	public boolean deleteAll(List<MonitorModule> listObj) {		
		Session session = sessionFactory.getCurrentSession();
		for(MonitorModule obj  : listObj) {
			session.delete(obj);
		}		
		return true;
	}
}
