package com.ifi.lab.LabDAO.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ifi.lab.LabDAO.dao.Lab009FilterDao;
import com.ifi.lab.LabDAO.model.Lab009Filter;

@Repository("lab009FilterDao")
@Transactional
public class Lab009FilterDaoImpl implements Lab009FilterDao {

	@Autowired
	private SessionFactory sessionFactory;

	public List<Lab009Filter> getAll() {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Lab009Filter> listLab = session.createQuery("FROM Lab009Filter").list();
		return listLab;
	}

	public List<Lab009Filter> findByIDs(List<Integer> idLst) {
		if (idLst == null || idLst.isEmpty()) {
			return null;
		}
		Session session = sessionFactory.getCurrentSession();
		StringBuilder sb = new StringBuilder();
		sb.append("FROM Lab009Filter u WHERE id in (");
		int sizeMinus = idLst.size() - 1;
		for (int i = 0; i < sizeMinus; i++) {
			sb.append("?,");
		}
		sb.append("?)");

		Query query = session.createQuery(sb.toString());
		for (int i = 0; i < idLst.size(); i++) {
			query.setParameter(i, idLst.get(i));
		}

		@SuppressWarnings("unchecked")
		List<Lab009Filter> result = query.list();
		return result;
	}

	public Integer create(Lab009Filter filter) {
		Session session = sessionFactory.getCurrentSession();
		session.save(filter);
		return filter.getId();
	}


}
