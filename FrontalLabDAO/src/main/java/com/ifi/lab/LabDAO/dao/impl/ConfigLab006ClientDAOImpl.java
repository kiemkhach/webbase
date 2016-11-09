package com.ifi.lab.LabDAO.dao.impl;

import com.ifi.lab.LabDAO.dao.ConfigLab006ClientDAO;
import com.ifi.lab.LabDAO.model.ConfigLab006Client;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository("lab006ClientDAO")
@Transactional
public class ConfigLab006ClientDAOImpl implements ConfigLab006ClientDAO {
	@Autowired
	private SessionFactory sessionFactory;
	public ConfigLab006Client getClientById(int id) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM ConfigLab006Client c WHERE c.id = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		@SuppressWarnings("unchecked")
		List<ConfigLab006Client> listlab = (List<ConfigLab006Client>) query.list();
		if (listlab != null && listlab.size() > 0) {
			return listlab.get(0);
		} else {
			return null;
		}
	}

	public boolean saveOrUpdate(ConfigLab006Client obj) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(obj);
		return true;
	}

	public boolean delete(int id) {
		Session session = sessionFactory.getCurrentSession();
		ConfigLab006Client configLab = this.getClientById(id);
		if (configLab == null) {
			return false;
		}
		session.delete(configLab);
		return true;
	}

	public List<ConfigLab006Client> getBySubscription(int id) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM ConfigLab006Client c WHERE c.subscriptionId = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		@SuppressWarnings("unchecked")
		List<ConfigLab006Client> listlab = (List<ConfigLab006Client>) query.list();
		return listlab;
	}

	public ConfigLab006Client getNewestClient() {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM ConfigLab006Client c ORDER BY c.id DESC LIMIT 1";
		Query query = session.createQuery(hql);
		ConfigLab006Client client = (ConfigLab006Client) query.list().get(0);
		return client;
	}
}
