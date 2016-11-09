package com.ifi.lab.LabDAO.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ifi.lab.LabDAO.dao.Lab009LotConsommationDAO;
import com.ifi.lab.LabDAO.model.Lab009LotConsommation;

@Repository("lab009LotConsommation")
@Transactional
public class Lab009LotConsommationImpl implements Lab009LotConsommationDAO {

	@Autowired
	private SessionFactory sessionFactory;


	public List<Lab009LotConsommation> getAllData() {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Lab009LotConsommation> listLab = session.createQuery("FROM Lab009LotConsommation").list();
		return listLab;
	}
	
	public List<Lab009LotConsommation> getAllLotActive() {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Lab009LotConsommation> listLab = session.createQuery("FROM Lab009LotConsommation WHERE isActive = true").list();
		return listLab;
	}


	public Integer createLot(Lab009LotConsommation obj) {
			Session session = sessionFactory.getCurrentSession();
			session.save(obj);
			return obj.getId();
	}


	public Lab009LotConsommation findById(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM Lab009LotConsommation c WHERE c.id = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		@SuppressWarnings("unchecked")
		List<Lab009LotConsommation> listlab = (List<Lab009LotConsommation>) query.list();
		if (listlab != null && listlab.size() > 0) {
			return listlab.get(0);
		} else {
			return null;
		}
	}


	public boolean delete(int id) {
		Session session = sessionFactory.getCurrentSession();
		Lab009LotConsommation configLab009LotConsommation = this.findById(id);
		if (configLab009LotConsommation == null) {
			return false;
		}
		session.delete(configLab009LotConsommation);
		return true;
	}
	public boolean updateLotConsommation(Lab009LotConsommation lab009LotConsommation) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(lab009LotConsommation);
		//LOGGER.info("Insert ConfigPerial success");
		return true;
	}

}