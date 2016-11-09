package com.ifi.lab.LabDAO.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ifi.lab.LabDAO.dao.ConfigLab004Dao;
import com.ifi.lab.LabDAO.model.ConfigLab004;

@Repository("lab004DAO")
@Transactional
public class ConfigLab004DaoImpl implements ConfigLab004Dao {

	@Autowired
	private SessionFactory sessionFactory;
	
	public ConfigLab004 findById(int id) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM ConfigLab004 c WHERE c.id = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		@SuppressWarnings("unchecked")
		List<ConfigLab004> listlab = (List<ConfigLab004>) query.list();
		if (listlab != null && listlab.size() > 0) {
			return listlab.get(0);
		} else {
			return null;
		}
	}

	public boolean delete(int id) {
		Session session = sessionFactory.getCurrentSession();
		ConfigLab004 configLab = findById(id);
		if (configLab == null) {
			return false;
		}
		session.delete(configLab);
		return true;
	}

	public ConfigLab004 getConfigByType(int type) {
		Session session = sessionFactory.getCurrentSession();
		Query q = session
				.createQuery("FROM ConfigLab004 Where siteId = :siteId");
		q.setInteger("siteId", type);
		@SuppressWarnings("unchecked")
		List<Object> lst = q.list();
		if (lst != null && lst.size() > 0) {
			return (ConfigLab004) lst.get(0);
		}
		return null;
	}

	public boolean save(ConfigLab004 obj) {
		Session session = null;
		session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(obj);
		return true;

	}

	public ConfigLab004 getConfigBySite(Integer siteId) {
		Session session = null;
		session = sessionFactory.getCurrentSession();
		String hql = "FROM ConfigLab004 c WHERE c.siteId = :siteId";
		Query query = session.createQuery(hql);
		query.setInteger("siteId", siteId);
		@SuppressWarnings("unchecked")
		List<ConfigLab004> listlab = (List<ConfigLab004>) query.list();
		if (listlab != null && listlab.size() > 0) {
			return listlab.get(0);
		}
		return null;
	}

	public ConfigLab004 findBySite(Integer siteId) {
		Session session = null;
		session = sessionFactory.getCurrentSession();
		String hql = "FROM ConfigLab004 c WHERE c.siteId = :siteId";
		Query query = session.createQuery(hql);
		query.setInteger("siteId", siteId);
		@SuppressWarnings("unchecked")
		List<ConfigLab004> listlab = (List<ConfigLab004>) query.list();
		if (listlab != null && listlab.size() > 0) {
			return listlab.get(0);
		} else {
			return new ConfigLab004();
		}
	}

	public List<ConfigLab004> getAllConfig() {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM ConfigLab004";
		Query query = session.createQuery(hql);
		List<ConfigLab004> listConfig = query.list();
		if (listConfig == null || listConfig.size() == 0) {
			return null;
		}
		return listConfig;

	}
	
	public Integer getMaxSiteId() {
		Session session = sessionFactory.getCurrentSession();
		String sql = "SELECT MAX(site_id) FROM config_lab004";
		Query query = session.createSQLQuery(sql);
		Object obj = query.list().get(0);
		Integer maxSiteId = 0;
		if (obj != null) {
			maxSiteId = (Integer) obj;
		}
		return maxSiteId;
	}

}
