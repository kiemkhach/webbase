package com.ifi.lab.LabDAO.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ifi.lab.LabDAO.dao.Lab009EnergyTypeDao;
import com.ifi.lab.LabDAO.model.Lab009EnergyType;

@Repository("lab009EnergyTypeDao")
@Transactional
public class Lab009EnergyTypeDaoImpl implements Lab009EnergyTypeDao{

	@Autowired
	private SessionFactory sessionFactory;

	public List<Lab009EnergyType> getAll() {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Lab009EnergyType> listLab = session.createQuery("FROM Lab009EnergyType").list();
		return listLab;
	}
	
	public List<Lab009EnergyType> getAllConsommation(){
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Lab009EnergyType> listLab = session.createQuery("FROM Lab009EnergyType WHERE category != 2").list();
		return listLab;
	}
	
	public Lab009EnergyType findByID(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM Lab009EnergyType u WHERE u.id = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		@SuppressWarnings("unchecked")
		List<Lab009EnergyType> result = query.list();
		if (result != null && result.size() > 0) {
			Lab009EnergyType obj = (Lab009EnergyType) result.get(0);
			return obj;
		} else {
			return null;
		}
	}

	public boolean delete(int id) {
		Session session = sessionFactory.getCurrentSession();
		Lab009EnergyType configLab009EnergyType = this.findByID(id);
		if (configLab009EnergyType == null) {
			return false;
		}
		session.delete(configLab009EnergyType);
		return true;
	}
	public Integer createEnergy(Lab009EnergyType obj) {
		Session session = sessionFactory.getCurrentSession();
		session.save(obj);
		return obj.getId();
	}
	public boolean updateEnergyType(Lab009EnergyType lab009Energy) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(lab009Energy);
		//LOGGER.info("Insert ConfigPerial success");
		return true;
	}
	

}
