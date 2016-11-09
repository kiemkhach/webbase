package com.ifi.lab.LabDAO.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ifi.lab.LabDAO.dao.ConfigLab009DAO;
import com.ifi.lab.LabDAO.model.ConfigLab009;

@Repository("configLab009DAO")
@Transactional
public class ConfigLab009DAOImpl implements ConfigLab009DAO {
	@Autowired
	private SessionFactory sessionFactory;

	public List<ConfigLab009> getAll() {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<ConfigLab009> listLab009 = session.createQuery("FROM ConfigLab009").list();
		return listLab009;
	}

	public ConfigLab009 findByClient(Integer clientId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM ConfigLab009 c WHERE c.clientId = :clientId";
		Query query = session.createQuery(hql);
		query.setParameter("clientId", clientId);
		List<ConfigLab009> lst = (List<ConfigLab009>) query.list();
		if (lst.size() > 0) {
			return lst.get(0);
		}
		return new ConfigLab009();
	}

	public boolean saveOrUpdate(ConfigLab009 configLab009) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(configLab009);
		//LOGGER.info("Insert ConfigPerial success");
		return true;
	}
}
