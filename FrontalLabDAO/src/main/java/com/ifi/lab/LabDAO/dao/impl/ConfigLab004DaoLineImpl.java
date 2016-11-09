package com.ifi.lab.LabDAO.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ifi.lab.LabDAO.dao.ConfigLab004DaoLine;
import com.ifi.lab.LabDAO.model.ConfigLab004Line;

@Repository("lab004LineDAO")
@Transactional
public class ConfigLab004DaoLineImpl implements ConfigLab004DaoLine {
	@Autowired
	private SessionFactory sessionFactory;

	public boolean save(ConfigLab004Line obj) {
		Session session = null;
		session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(obj);
		return true;
	}

	public boolean saveAll(List<ConfigLab004Line> listObj) {
		Session session = null;
		session = sessionFactory.getCurrentSession();
		for (ConfigLab004Line obj : listObj) {
			session.saveOrUpdate(obj);
		}
		return true;
	}

	public ConfigLab004Line findByid(Integer id) {
		Session session = null;
		session = sessionFactory.getCurrentSession();
		String hql = "FROM ConfigLab004Line c WHERE c.id = :id";
		Query query = session.createQuery(hql);
		query.setInteger("id", id);
		@SuppressWarnings("unchecked")
		List<ConfigLab004Line> listlab = (List<ConfigLab004Line>) query.list();
		if (listlab != null && listlab.size() > 0) {
			return listlab.get(0);
		} else {
			return new ConfigLab004Line();
		}
	}

	public List<ConfigLab004Line> getAllLineByConfigId(Integer configLab004Id) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM ConfigLab004Line c WHERE c.configLab004Id = :configLab004Id";
		Query query = session.createQuery(hql);
		query.setInteger("configLab004Id", configLab004Id);
		List<ConfigLab004Line> listConfig = query.list();
		if (listConfig == null || listConfig.size() == 0) {
			return null;
		}
		return listConfig;
	}

	public ConfigLab004Line getConfigLineByType(Integer type,
			Integer configLab004Id) {
		Session session = null;
		session = sessionFactory.getCurrentSession();
		String hql = "FROM ConfigLab004Line c WHERE c.type = :type and c.configLab004Id = :configLab004Id";
		Query query = session.createQuery(hql);
		query.setInteger("type", type);
		query.setInteger("configLab004Id", configLab004Id);
		@SuppressWarnings("unchecked")
		List<ConfigLab004Line> listlab = (List<ConfigLab004Line>) query.list();
		if (listlab != null && listlab.size() > 0) {
			return listlab.get(0);
		} else {
			return new ConfigLab004Line();
		}
	}
}
