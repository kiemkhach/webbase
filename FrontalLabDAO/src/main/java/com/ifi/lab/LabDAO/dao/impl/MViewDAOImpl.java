package com.ifi.lab.LabDAO.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ifi.lab.LabDAO.dao.MViewDAO;
import com.ifi.lab.LabDAO.model.MView;

@Repository("mViewDAO")
@Transactional
public class MViewDAOImpl implements MViewDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public boolean save(MView obj) {
		try {
			Session session = sessionFactory.getCurrentSession();
			session.saveOrUpdate(obj);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean delete(MView obj) {
		try {
			Session session = sessionFactory.getCurrentSession();
			session.delete(obj);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteAll(List<MView> listObj) {
		try {
			Session session = sessionFactory.getCurrentSession();
			for (MView obj : listObj) {
				session.delete(obj);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public MView findById(Integer id) {
		try {
			Session session = sessionFactory.getCurrentSession();
			String hql = "FROM MView m WHERE m.id = :id";
			Query query = session.createQuery(hql);
			query.setParameter("id", id);
			@SuppressWarnings("unchecked")
			List<MView> listObj = (List<MView>) query.list();
			if (listObj != null && listObj.size() > 0) {
				return listObj.get(0);
			} else {
				return new MView();
			}			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<MView> findAll() {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<MView> listObj = session.createQuery("FROM MView").list();
		return listObj;
	}

}
