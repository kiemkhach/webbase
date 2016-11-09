package com.ifi.lab.LabDAO.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ifi.lab.LabDAO.dao.Lab008SaveTemplateDAO;
import com.ifi.lab.LabDAO.model.Lab008SaveTemplate;

@Repository("lab008SaveTemplateDAO")
@Transactional
public class Lab008SaveTemplateDAOImpl implements Lab008SaveTemplateDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public Lab008SaveTemplate findById(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM Lab008SaveTemplate c WHERE c.id = :id";
		Query query = session.createQuery(hql);
		query.setInteger("id", id);
		@SuppressWarnings("unchecked")
		List<Object> objLst = query.list();
		if (objLst.size() > 0) {
			return (Lab008SaveTemplate) objLst.get(0);
		} else {
			return null;
		}
	}

	public boolean delete(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		Lab008SaveTemplate configLab = findById(id);
		if (configLab == null) {
			return false;
		}
		session.delete(configLab);
		return true;
	}

	public List<Lab008SaveTemplate> findBySiteId(Integer siteId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM Lab008SaveTemplate c WHERE c.siteId = :siteId";
		Query query = session.createQuery(hql);
		query.setInteger("siteId", siteId);
		@SuppressWarnings("unchecked")
		List<Lab008SaveTemplate> list = query.list();
		return list;
	}

	public boolean saveOrUpdate(Lab008SaveTemplate obj) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(obj);
		return true;
	}

	public boolean findByDate(Date fromDate, Date toDate, Integer siteId, Integer id) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM Lab008SaveTemplate WHERE siteId = ? "
				+ "AND ( (fromDate <= ? AND toDate >= ?  )  OR  (fromDate <= ? AND toDate >= ?  )"
				+ " OR (fromDate >=? AND toDate <= ?) ) ";
		if (id != null && id > 0) {
			hql += " AND id != ? ";
		}
		Query query = session.createQuery(hql);
		int index = 0;
		query.setInteger(index++, siteId);
		query.setDate(index++, fromDate);
		query.setDate(index++, fromDate);
		query.setDate(index++, toDate);
		query.setDate(index++, toDate);
		query.setDate(index++, fromDate);
		query.setDate(index++, toDate);
		if (id != null && id > 0) {
			query.setInteger(index++, id);
		}
		@SuppressWarnings("unchecked")
		List<Lab008SaveTemplate> list = query.list();
		if (list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}
}
