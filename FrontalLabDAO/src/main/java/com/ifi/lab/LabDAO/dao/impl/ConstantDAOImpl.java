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

import com.ifi.lab.LabDAO.dao.ConstantDAO;
import com.ifi.lab.LabDAO.model.Constant;

@Repository("constantDAO")
@Transactional
public class ConstantDAOImpl implements ConstantDAO {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ConstantDAOImpl.class);
	@Autowired
	private SessionFactory sessionFactory;

	public boolean save(Constant obj) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(obj);
		return true;
	}
	
	public boolean saveAll(List<Constant> listObj) {		
		Session session = sessionFactory.getCurrentSession();
		for(Constant obj : listObj) {
			session.saveOrUpdate(obj);	
		}		
		return true;
	}

	public boolean delete(int labId, String key) {
		Constant constant = getByLabIdnKey(labId, key);
		if (constant != null) {
			Session session = sessionFactory.getCurrentSession();
			session.delete(constant);
			return true;
		}
		return false;
	}

	public List<Constant> getByLabId(int labId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM Constant c WHERE c.labId = :labId";
		Query query = session.createQuery(hql);
		query.setParameter("labId", labId);
		List<Constant> list = query.list();
		if (list == null) {
			return null;
		} else if (list.size() == 0)
			return null;
		return list;
	}

	public Constant getByLabIdnKey(int labId, String key) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM Constant c WHERE c.labId = :labId AND c.key = :key";
		Query query = session.createQuery(hql);
		query.setParameter("labId", labId);
		query.setParameter("key", key);
		List<Constant> list = query.list();
		if (list == null) {
			return null;
		} else if (list.size() == 0)
			return null;
		return list.get(0);
	}
	
	public boolean getByKeyValue(String key, String value) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM Constant c WHERE UPPER(c.key) = :key AND UPPER(c.value) = :value";
		Query query = session.createQuery(hql);
		query.setParameter("key", key.toUpperCase());
		query.setParameter("value", value.toUpperCase());
		List<Constant> list = query.list();
		if (list != null && !list.isEmpty()) {
			return true;
		} else{ 
			return false;
		}
	}

	public boolean updateValue(int labId, String key, String value) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "UPDATE Constant c SET c.value = :value WHERE c.labId = :labId AND c.key = :key";
		Query query = session.createQuery(hql);
		query.setParameter("labId", labId);
		query.setParameter("key", key);
		query.setParameter("value", value);
		if(query.executeUpdate()<0){
			return false;
		}
		return true;
	}
}
