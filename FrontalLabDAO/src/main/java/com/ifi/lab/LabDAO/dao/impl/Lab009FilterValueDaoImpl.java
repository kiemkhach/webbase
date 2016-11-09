package com.ifi.lab.LabDAO.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ifi.lab.LabDAO.dao.Lab009FilterValueDao;
import com.ifi.lab.LabDAO.model.Lab009FilterValue;

@Repository("lab009FilterValueDao")
@Transactional
public class Lab009FilterValueDaoImpl implements Lab009FilterValueDao {

	@Autowired
	private SessionFactory sessionFactory;

	public List<Lab009FilterValue> findByFilterID(Integer filterId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM Lab009FilterValue u WHERE u.filterId = :filterId";
		Query query = session.createQuery(hql);
		query.setParameter("filterId", filterId);
		@SuppressWarnings("unchecked")
		List<Lab009FilterValue> result = query.list();
		return result;
	}
}
