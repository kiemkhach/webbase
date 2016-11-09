package com.ifi.lab.LabDAO.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ifi.lab.LabDAO.dao.ConfigLab008DAO;
import com.ifi.lab.LabDAO.model.ConfigLab008;

/**
 * Created by hmtri on 2/29/2016.
 */
@Repository("lab008DAO")
@Transactional
public class ConfigLab008DAOImpl implements ConfigLab008DAO {
	@Autowired
	private SessionFactory sessionFactory;

//	public List<ConfigLab008> getAllConfigs() {
//		Session session = sessionFactory.getCurrentSession();
//		String hql = "FROM ConfigLab008";
//		Query query = session.createQuery(hql);
//		@SuppressWarnings("unchecked")
//		List<ConfigLab008> list = query.list();
//		if (list == null || list.size() == 0) {
//			return null;
//		}
//		return list;
//	}

//	public boolean update(ConfigLab008 obj) {
//		Session session = sessionFactory.getCurrentSession();
//		session.update(obj);
//		return true;
//	}

//	public boolean delete(Integer id) {
//		Session session = sessionFactory.getCurrentSession();
//		String hql = "DELETE FROM ConfigLab008 WHERE ID = ?";
//		Query query = session.createQuery(hql);
//		query.setParameter(0, id);
//		query.executeUpdate();
//		return true;
//	}
//
//	public Integer create(ConfigLab008 obj) {
//		Session session = sessionFactory.getCurrentSession();
//		session.save(obj);
//		return obj.getId();
//	}
//
//	public ConfigLab008 findById(Integer id) {
//		Session session = sessionFactory.getCurrentSession();
//		String hql = "FROM ConfigLab008 c WHERE c.id = :id";
//		Query query = session.createQuery(hql);
//		query.setInteger("id", id);
//		@SuppressWarnings("unchecked")
//		List<Object> objLst = query.list();
//		if (objLst.size() > 0) {
//			return (ConfigLab008) objLst.get(0);
//		} else {
//			return null;
//		}
//	}

	public ConfigLab008 findBySiteId(Integer siteId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM ConfigLab008 c WHERE c.siteId = :siteId";
		Query query = session.createQuery(hql);
		query.setInteger("siteId", siteId);
		@SuppressWarnings("unchecked")
		List<Object> objLst = query.list();
		if (objLst.size() > 0) {
			return (ConfigLab008) query.list().get(0);
		} else {
			return null;
		}
	}

	public List<ConfigLab008> getAllConfig() {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM ConfigLab008";
		Query query = session.createQuery(hql);
		@SuppressWarnings("unchecked")
		List<ConfigLab008> list = query.list();
		if (list == null || list.size() == 0) {
			return null;
		}
		return list;
	}

//	public boolean saveOrUpdate(ConfigLab008 obj) {
//		Session session = sessionFactory.getCurrentSession();
//		session.saveOrUpdate(obj);
//		return true;
//	}
//
//	public Integer getMaxSiteId() {
//		Session session = sessionFactory.getCurrentSession();
//		String sql = "SELECT MAX(site_id) FROM config_lab008";
//		Query query = session.createSQLQuery(sql);
//		Object obj = query.list().get(0);
//		Integer maxSiteId = 0;
//		if (obj != null) {
//			maxSiteId = (Integer) obj;
//		}
//		return maxSiteId;
//	}

}
