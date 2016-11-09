package com.ifi.lab.LabDAO.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ifi.lab.LabDAO.dao.Lab009ProviderDAO;
import com.ifi.lab.LabDAO.model.Lab009LotConsommation;
import com.ifi.lab.LabDAO.model.Lab009Provider;

@Repository("lab009Provider")
@Transactional
public class Lab009ProviderDAOImpl implements Lab009ProviderDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public List<Lab009Provider> getAllData() {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Lab009Provider> listLab = session.createQuery("FROM Lab009Provider").list();
		return listLab;
	}

	public Lab009Provider findById(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM Lab009Provider u WHERE u.id = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		@SuppressWarnings("unchecked")
		List<Lab009Provider> result = query.list();
		if (result != null && result.size() > 0) {
			Lab009Provider obj = (Lab009Provider) result.get(0);
			return obj;
		} else {
			return null;
		}
	}

	public Integer createPro(Lab009Provider obj) {
		Session session = sessionFactory.getCurrentSession();
		session.save(obj);
		return obj.getId();
	}

	public boolean delete(int id) {
		Session session = sessionFactory.getCurrentSession();
		Lab009Provider lab009Provider = this.findById(id);
		if (lab009Provider == null) {
			return false;
		}
		session.delete(lab009Provider);
		return true;
	}

	public boolean updateProvider(Lab009Provider lab009Provider) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(lab009Provider);
		//LOGGER.info("Insert ConfigPerial success");
		return true;
	}

}
