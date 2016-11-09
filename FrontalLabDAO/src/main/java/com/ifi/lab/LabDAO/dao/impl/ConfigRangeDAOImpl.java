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

import com.ifi.lab.LabDAO.dao.ConfigRangeDAO;
import com.ifi.lab.LabDAO.model.ConfigRange;

@Repository("configRangeDAO")
@Transactional
public class ConfigRangeDAOImpl implements ConfigRangeDAO {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ConfigRangeDAOImpl.class);
	@Autowired
	private SessionFactory sessionFactory;

	public List<ConfigRange> getRangeByType(String type) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM ConfigRange c WHERE c.type = :type";
		Query query = session.createQuery(hql);
		query.setParameter("type", type);
		@SuppressWarnings("unchecked")
		List<ConfigRange> list = query.list();
		if (list == null || list.size() == 0) {
			return null;
		}
		return list;
	}

	public ConfigRange getRangeById(int id) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM ConfigRange c WHERE c.id = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		try {
			ConfigRange obj = (ConfigRange) query.list().get(0);
			return obj;
		} catch (NullPointerException e) {
			return null;
		}
	}

	public boolean save(ConfigRange obj) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(obj);
		return true;
	}

	public boolean delete(int id) {
		ConfigRange obj = getRangeById(id);
		if (obj != null) {
			Session session = sessionFactory.getCurrentSession();
			session.delete(obj);
			LOGGER.debug("Delete ConfigRange Success");
			return true;
		}
		return false;
	}

	public List<ConfigRange> getRangeByLabIdnPageIdnType(int labId, int pageId,
			String type) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM ConfigRange c WHERE c.type = :type AND c.labId = :labId AND c.pageId = :pageId";
		Query query = session.createQuery(hql);
		query.setParameter("type", type);
		query.setParameter("labId", labId);
		query.setParameter("pageId", pageId);
		@SuppressWarnings("unchecked")
		List<ConfigRange> list = query.list();
		if (list == null) {
			return null;
		} else if (list.size() == 0)
			return null;
		return list;
	}

}
