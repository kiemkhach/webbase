package com.ifi.lab.LabDAO.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ifi.lab.LabDAO.dao.ConfigLab005Dao;
import com.ifi.lab.LabDAO.model.ConfigLab005;

@Repository("lab005DAO")
@Transactional
public class ConfigLab005DaoImpl implements ConfigLab005Dao {
	@Autowired
	private SessionFactory sessionFactory;
	
	public ConfigLab005 findById(int id) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM ConfigLab005 c WHERE c.id = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		@SuppressWarnings("unchecked")
		List<ConfigLab005> listlab = (List<ConfigLab005>) query.list();
		if (listlab != null && listlab.size() > 0) {
			return listlab.get(0);
		} else {
			return null;
		}
	}

	public boolean delete(int id) {
		Session session = sessionFactory.getCurrentSession();
		ConfigLab005 configLab = findById(id);
		if (configLab == null) {
			return false;
		}
		session.delete(configLab);
		return true;
	}

	public boolean save(ConfigLab005 obj) {
		Session session = null;
		session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(obj);
		return true;
	}

	public ConfigLab005 getConfigBySite(Integer siteId) {
		Session session = null;
		session = sessionFactory.getCurrentSession();
		String hql = "FROM ConfigLab005 c WHERE c.siteId = :siteId";
		Query query = session.createQuery(hql);
		query.setInteger("siteId", siteId);
		@SuppressWarnings("unchecked")
		List<ConfigLab005> listlab = (List<ConfigLab005>) query.list();
		if (listlab != null && listlab.size() > 0) {
			return listlab.get(0);
		} else {
			return null;
		}
	}
	
	public Integer getMaxSiteId() {
		Session session = sessionFactory.getCurrentSession();
		String sql = "SELECT MAX(site_id) FROM config_lab005";
		Query query = session.createSQLQuery(sql);
		Object obj = query.list().get(0);
		Integer maxSiteId = 0;
		if (obj != null) {
			maxSiteId = (Integer) obj;
		}
		return maxSiteId;
	}
	
	public List<ConfigLab005> getAllConfig(){
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM ConfigLab005";
		Query query = session.createQuery(hql);
		@SuppressWarnings("unchecked")
		List<ConfigLab005> listConfig = query.list();
		if (listConfig == null || listConfig.size() == 0) {
			return null;
		}
		return listConfig;
	}
	
	public ConfigLab005 findBySite(Integer siteId) {
		Session session = null;
		session = sessionFactory.getCurrentSession();
		String hql = "FROM ConfigLab005 c WHERE c.siteId = :siteId";
		Query query = session.createQuery(hql);
		query.setInteger("siteId", siteId);
		@SuppressWarnings("unchecked")
		List<ConfigLab005> listlab = (List<ConfigLab005>) query.list();
		if (listlab != null && listlab.size() > 0) {
			return listlab.get(0);
		} else {
			return new ConfigLab005();
		}
	}
}
