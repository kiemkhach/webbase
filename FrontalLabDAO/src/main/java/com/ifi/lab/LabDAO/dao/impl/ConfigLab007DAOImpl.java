package com.ifi.lab.LabDAO.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ifi.lab.LabDAO.dao.ConfigLab007DAO;
import com.ifi.lab.LabDAO.model.ConfigLab007;
@Repository("lab007DAO")
@Transactional
public class ConfigLab007DAOImpl implements ConfigLab007DAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	public boolean save(ConfigLab007 obj) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(obj);
		return true;
	}

	public boolean update(ConfigLab007 obj) {
		Session session = sessionFactory.getCurrentSession();
		session.update(obj);
		return true;
	}

	public boolean delete(int id) {
		Session session = sessionFactory.getCurrentSession();
		ConfigLab007 obj = this.findById(id);
		if (obj != null) {
			session.delete(obj);
			return true;
		}
		return false;
	}

	public List<ConfigLab007> getAllConfig() {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM ConfigLab007";
		Query query = session.createQuery(hql);
		@SuppressWarnings("unchecked")
		List<ConfigLab007> list = query.list();
		if (list == null || list.size() == 0) {
			return null;
		}
		return list;
	}

	public ConfigLab007 findById(int id) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM ConfigLab007 b WHERE b.id = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		ConfigLab007 obj = (ConfigLab007) query.list().get(0);
		return obj;
	}

	public ConfigLab007 findBySiteId(int siteId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM ConfigLab007 b WHERE b.siteId = :siteId";
		Query query = session.createQuery(hql);
		query.setParameter("siteId", siteId);
		ConfigLab007 obj = (ConfigLab007) query.list().get(0);
		return obj;
	}

	public boolean saveOrUpdate(ConfigLab007 obj) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(obj);
		return true;
	}

	public Integer getMaxSiteId() {
		Session session = sessionFactory.getCurrentSession();
		String sql = "SELECT MAX(site_id) FROM config_lab007";
		Query query = session.createSQLQuery(sql);
		Object obj = query.list().get(0);
		Integer maxSiteId = 0;
		if (obj != null) {
			maxSiteId = (Integer) obj;
		}
		return maxSiteId;
	}

}
