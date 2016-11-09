package com.ifi.lab.LabDAO.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ifi.lab.LabDAO.dao.ConfigLab008ECSDAO;
import com.ifi.lab.LabDAO.model.ConfigLab006Client;
import com.ifi.lab.LabDAO.model.Lab008ECS;

@Repository("lab008ECSDAO")
@Transactional
public class ConfigLab008ECSDAOImpl implements ConfigLab008ECSDAO {
	@Autowired
	private SessionFactory sessionFactory;

	public boolean saveOrUpdate(ConfigLab006Client obj) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(obj);
		return true;
	}

	public boolean delete(int id) {
		Session session = sessionFactory.getCurrentSession();
		Lab008ECS configLab = this.getECSById(id);
		if (configLab == null) {
			return false;
		}
		session.delete(configLab);
		return true;
	}

	public Lab008ECS getECSById(int id) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM Lab008ECS c WHERE c.id = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		@SuppressWarnings("unchecked")
		List<Lab008ECS> listlab = (List<Lab008ECS>) query.list();
		if (listlab != null && listlab.size() > 0) {
			return listlab.get(0);
		} else {
			return null;
		}
	}

	public boolean saveOrUpdate(Lab008ECS obj) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(obj);
		return true;
	}

	public Lab008ECS getNewestECS() {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM Lab008ECS c ORDER BY c.id DESC LIMIT 1";
		Query query = session.createQuery(hql);
		Lab008ECS ecs = (Lab008ECS) query.list().get(0);
		return ecs;
	}

	public List<Lab008ECS> getBySubscription(int id) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM Lab008ECS c WHERE c.configLab008V2ID = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		@SuppressWarnings("unchecked")
		List<Lab008ECS> listlab = (List<Lab008ECS>) query.list();
		return listlab;
	}

	public List<Lab008ECS> getECSByType(int configlab8Id, int type) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM Lab008ECS c WHERE c.configLab008V2ID = :configlab8Id AND c.type = :type";
		Query query = session.createQuery(hql);
		query.setParameter("configlab8Id", configlab8Id);
		query.setParameter("type", type);
		@SuppressWarnings("unchecked")
		List<Lab008ECS> listlab = (List<Lab008ECS>) query.list();
		return listlab;
	}

	
}
