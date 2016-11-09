package com.ifi.lab.LabDAO.dao.impl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ifi.lab.LabDAO.dao.MonitorGatewayDAO;
import com.ifi.lab.LabDAO.model.MonitorGateway;
import com.ifi.lab.LabDAO.utils.MappingObject;

@Repository("monitorGateWayDAO")
@Transactional
public class MonitorGatewayDAOImpl implements MonitorGatewayDAO {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MonitorGatewayDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	public boolean save(MonitorGateway obj) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(obj);
		LOGGER.info("Insert success");
		return true;
	}

	public boolean saveAll(List<MonitorGateway> listObj) {
		Session session = sessionFactory.getCurrentSession();
		for (MonitorGateway obj : listObj) {
			session.saveOrUpdate(obj);
		}
		LOGGER.info("Insert success");
		return true;
	}

	public MonitorGateway findById(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM MonitorGateway m WHERE m.id = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		@SuppressWarnings("unchecked")
		List<MonitorGateway> listMonitor = (List<MonitorGateway>) query.list();
		if (listMonitor != null && listMonitor.size() > 0) {
			return listMonitor.get(0);
		} else {
			return new MonitorGateway();
		}
	}

	public MonitorGateway findBySerialNo(String serialNo) {
		Session session = null;
		session = sessionFactory.getCurrentSession();
		String hql = "FROM MonitorGateway m WHERE m.serialNo = :serialNo";
		Query query = session.createQuery(hql);
		query.setParameter("serialNo", serialNo);
		@SuppressWarnings("unchecked")
		List<MonitorGateway> listMonitor = (List<MonitorGateway>) query.list();
		if (listMonitor != null && listMonitor.size() > 0) {
			return listMonitor.get(0);
		} else {
			return new MonitorGateway();
		}
	}
	
	public MonitorGateway findBySerialAndDate(String serialNo, Date dateData) {
		Session session = null;
		session = sessionFactory.getCurrentSession();
		String hql = "FROM MonitorGateway m WHERE m.serialNo = :serialNo AND m.dateData = :dateData";
		Query query = session.createQuery(hql);
		query.setParameter("serialNo", serialNo);
		query.setParameter("dateData", dateData);
		@SuppressWarnings("unchecked")
		List<MonitorGateway> listMonitor = (List<MonitorGateway>) query.list();
		if (listMonitor != null && listMonitor.size() > 0) {
			return listMonitor.get(0);
		} else {
			return new MonitorGateway();
		}
	}

	@SuppressWarnings("unchecked")
	public List<MonitorGateway> getAllGateways(Timestamp dateData) {
		Session session = sessionFactory.getCurrentSession();
		String sql = "CALL sp_getAllGateway(:dataDate)";
		Query query = session.createSQLQuery(sql);
		query.setTimestamp("dataDate", dateData);
		List<MonitorGateway> list = null;
		try {
			list = MappingObject.mappingObjectToGateway(query.list());
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return list;
	}

	public boolean delete(MonitorGateway obj) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(obj);
		return true;
	}
	
	public boolean deleteAll(List<MonitorGateway> listObj) {		
		Session session = sessionFactory.getCurrentSession();
		for(MonitorGateway obj  : listObj) {
			session.delete(obj);
		}		
		return true;
	}

	public List<MonitorGateway> getGatewaysByDay(Timestamp dateData,int viewId) {
		Session session = sessionFactory.getCurrentSession();
		String sql = "CALL sp_getGateway(:dataDate,:viewId)";
		Query query = session.createSQLQuery(sql);
		query.setTimestamp("dataDate", dateData);
		query.setInteger("viewId", viewId);
		List<MonitorGateway> list = null;
		try {
			list = MappingObject.mappingObjectToGateway(query.list());
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return list;
	}
}
