package com.ifi.lab.LabDAO.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ifi.lab.LabDAO.dao.ConfigLab006Dao;
import com.ifi.lab.LabDAO.model.ConfigLab006;

@Repository("lab006DAO")
@Transactional
public class ConfigLab006DaoImpl implements ConfigLab006Dao {
	@Autowired
	private SessionFactory sessionFactory;

	public ConfigLab006 findById(int id) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM ConfigLab006 c WHERE c.id = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		@SuppressWarnings("unchecked")
		List<ConfigLab006> listlab = (List<ConfigLab006>) query.list();
		if (listlab != null && listlab.size() > 0) {
			return listlab.get(0);
		} else {
			return null;
		}
	}

	public boolean delete(int id) {
		Session session = sessionFactory.getCurrentSession();
		ConfigLab006 configLab = findById(id);
		if (configLab == null) {
			return false;
		}
		session.delete(configLab);
		return true;
	}


	public boolean update(ConfigLab006 obj) {
		Session session = sessionFactory.getCurrentSession();
		session.update(obj);
		return true;
	}

	public boolean save(ConfigLab006 obj) {
		Session session = null;
		session = sessionFactory.getCurrentSession();
		session.save(obj);
		return true;
	}

	public Integer getMaxSiteId() {
		Session session = sessionFactory.getCurrentSession();
		String sql = "SELECT MAX(site_id) FROM config_lab006";
		Query query = session.createSQLQuery(sql);
		Object obj = query.list().get(0);
		Integer maxSiteId = 0;
		if (obj != null) {
			maxSiteId = (Integer) obj;
		}
		return maxSiteId;
	}

	public List<ConfigLab006> getAllConfig() {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM ConfigLab006";
		Query query = session.createQuery(hql);
		@SuppressWarnings("unchecked")
		List<ConfigLab006> listConfig = query.list();
		if (listConfig == null || listConfig.size() == 0) {
			return null;
		}
		return listConfig;
	}
	public ConfigLab006 findBySite(Integer siteId) {
		Session session = null;
		session = sessionFactory.getCurrentSession();
		String hql = "FROM ConfigLab006 c WHERE c.siteId = :siteId";
		Query query = session.createQuery(hql);
		query.setInteger("siteId", siteId);
		@SuppressWarnings("unchecked")
		List<ConfigLab006> listlab = (List<ConfigLab006>) query.list();
		if (listlab != null && listlab.size() > 0) {
			return listlab.get(0);
		} else {
			return new ConfigLab006();
		}
	}
}
