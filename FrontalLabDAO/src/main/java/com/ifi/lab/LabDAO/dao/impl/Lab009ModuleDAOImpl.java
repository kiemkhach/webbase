package com.ifi.lab.LabDAO.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ifi.lab.LabDAO.dao.Lab009ModuleDAO;
import com.ifi.lab.LabDAO.model.Lab009Module;

@Repository("lab009ModuleDAO")
@Transactional
public class Lab009ModuleDAOImpl implements Lab009ModuleDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(Lab009ModuleDAOImpl.class);

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<Lab009Module> getAllData() {
		List<Lab009Module> listModule = null;
		Session session = sessionFactory.getCurrentSession();
		try {
			Criteria criteria = session.createCriteria(Lab009Module.class);
			listModule = criteria.list();
			if (listModule != null && listModule.isEmpty()) {
				listModule = null;
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return listModule;
	}

	public Lab009Module findById(Integer id) {
		Lab009Module module = null;
		Session session = sessionFactory.getCurrentSession();
		try {
			module = (Lab009Module) session.get(Lab009Module.class, id);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return module;
	}

	public Integer create(Lab009Module obj) {
		Session session = sessionFactory.getCurrentSession();
		session.save(obj);
		return obj.getId();
	}

	public boolean update(Lab009Module obj) {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.update(obj);
			return true;
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return false;
		}
	}

	public boolean delete(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		try {
			Lab009Module module = this.findById(id);
			session.delete(module);
			return true;
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return false;
		}
	}

	public Map<Integer, Lab009Module> getAllMapLab009ModuleNotWater() {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Lab009Module> listModule = session.createQuery("FROM Lab009Module Where energyType.category != 2").list();
		Map<Integer, Lab009Module> map = new HashMap<Integer, Lab009Module>();
		for (Lab009Module module : listModule) {
			map.put(module.getModuleId(), module);
		}
		return map;
	}
	
	public Map<Integer, Lab009Module> getAllMapLab009Module() {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Lab009Module> listModule = session.createQuery("FROM Lab009Module").list();
		Map<Integer, Lab009Module> map = new HashMap<Integer, Lab009Module>();
		for (Lab009Module module : listModule) {
			map.put(module.getModuleId(), module);
		}
		return map;
	}

}