package com.ifi.lab.LabDAO.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ifi.lab.LabDAO.dao.ConfigLab003DAO;
import com.ifi.lab.LabDAO.model.ConfigLab003;

@Repository("configLab003DAO")
@Transactional
public class ConfigLab003DAOImpl implements ConfigLab003DAO {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ConfigLab003DAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;
	
	public ConfigLab003 findById(int id) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM ConfigLab003 c WHERE c.id = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		@SuppressWarnings("unchecked")
		List<ConfigLab003> listlab = (List<ConfigLab003>) query.list();
		if (listlab != null && listlab.size() > 0) {
			return listlab.get(0);
		} else {
			return null;
		}
	}

	public boolean delete(int id) {
		Session session = sessionFactory.getCurrentSession();
		ConfigLab003 configLab = findById(id);
		if (configLab == null) {
			return false;
		}
		session.delete(configLab);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ifi.lab.LabDAO.dao.AgregatDAO#save(com.ifi.lab.LabDAO.model.Agregat)
	 */
	public boolean save(ConfigLab003 lab) {
		Session session = null;
		// Transaction tx = null;
		// try {
		session = sessionFactory.getCurrentSession();
		// tx = session.beginTransaction();
		session.saveOrUpdate(lab);
		// tx.commit();
		LOGGER.info("Insert Lab success");
		return true;
		// } catch (Exception e) {
		// tx.rollback();
		// LOGGER.debug("Insert error transaction rollback");
		// return false;
		// } finally {
		// session.close();
		// }
	}

	/**
	 * get Config by site or userName
	 * 
	 * @param siteId
	 * @param userName
	 * @return
	 */
	public ConfigLab003 getConfigBySite(Integer siteId) {
		Session session = null;
		// try {
		session = sessionFactory.getCurrentSession();
		String hql = "FROM ConfigLab003 c WHERE c.siteId = :siteId";
		Query query = session.createQuery(hql);
		query.setInteger("siteId", siteId);
		@SuppressWarnings("unchecked")
		List<ConfigLab003> listlab = (List<ConfigLab003>) query.list();
		if (listlab != null && listlab.size() > 0) {
			return listlab.get(0);
		}
		// } catch (Exception e) {
		// e.printStackTrace();
		// } finally {
		// if (session != null) {
		// session.close();
		// }
		// }
		return null;
	}

	/**
	 * get Config by site or userName
	 * 
	 * @param siteId
	 * @param userName
	 * @return
	 */
	public ConfigLab003 findBySite(Integer siteId) {
		Session session = null;
		// try {
		session = sessionFactory.getCurrentSession();
		String hql = "FROM ConfigLab003 c WHERE c.siteId = :siteId";
		Query query = session.createQuery(hql);
		query.setInteger("siteId", siteId);
		@SuppressWarnings("unchecked")
		List<ConfigLab003> listlab = (List<ConfigLab003>) query.list();
		if (listlab != null && listlab.size() > 0) {
			return listlab.get(0);
		} else {
			return new ConfigLab003();
		}
		// } catch (Exception e) {
		// e.printStackTrace();
		// } finally {
		// if (session != null) {
		// session.close();
		// }
		// }
		// return null;
	}

	public List<ConfigLab003> getAllConfig() {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM ConfigLab003";
		Query query = session.createQuery(hql);
		List<ConfigLab003> listConfig = query.list();
		if (listConfig == null || listConfig.size() == 0) {
			return null;
		}
		return listConfig;
	}
	
	public Integer getMaxSiteId() {
		Session session = sessionFactory.getCurrentSession();
		String sql = "SELECT MAX(site_id) FROM config_lab003";
		Query query = session.createSQLQuery(sql);
		Object obj = query.list().get(0);
		Integer maxSiteId = 0;
		if (obj != null) {
			maxSiteId = (Integer) obj;
		}
		return maxSiteId;
	}

}
