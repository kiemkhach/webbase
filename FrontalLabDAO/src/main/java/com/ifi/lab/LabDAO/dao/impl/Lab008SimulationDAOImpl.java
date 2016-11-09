package com.ifi.lab.LabDAO.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ifi.lab.LabDAO.dao.Lab008SimulationDAO;
import com.ifi.lab.LabDAO.model.ConfigLab008V2;
import com.ifi.lab.LabDAO.model.Lab008SaveTemplate;
import com.ifi.lab.LabDAO.model.Lab008Simulation;

@Repository("lab008SimulationDAO")
@Transactional
public class Lab008SimulationDAOImpl implements Lab008SimulationDAO{
	@Autowired
	private SessionFactory sessionFactory;
	public Lab008Simulation findById(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM Lab008Simulation c WHERE c.id = :id";
		Query query = session.createQuery(hql);
		query.setInteger("id", id);
		@SuppressWarnings("unchecked")
		List<Object> objLst = query.list();
		if (objLst.size() > 0) {
			return (Lab008Simulation) objLst.get(0);
		} else {
			return null;
		}
	}

	public boolean saveOrUpdate(Lab008Simulation obj) {
		Session session = sessionFactory.getCurrentSession();
		session.save(obj);
		return true;
	}

	public List<Lab008Simulation> findSimulationLimitBySite(Integer siteId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM Lab008Simulation c WHERE c.siteId = :siteId ORDER BY c.dateExecute DESC";
		Query query = session.createQuery(hql);
		query.setInteger("siteId", siteId);
		query.setFirstResult(0);
		query.setMaxResults(3);
		@SuppressWarnings("unchecked")
		List<Lab008Simulation> list = query.list();
		return list;
	}

	public boolean deleteById(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		Lab008Simulation lab008Simulation = findById(id);
		if (lab008Simulation == null) {
			return false;
		}
		session.delete(lab008Simulation);
		return true;
	}



}
