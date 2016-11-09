package com.ifi.lab.LabDAO.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ifi.lab.LabDAO.dao.BouyguesDAO;
import com.ifi.lab.LabDAO.model.Bouygues;

@Repository("bouyguesDAO")
@Transactional
public class BouyguesDAOImpl implements BouyguesDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	public boolean save(Bouygues obj) {
//		Session session = null;
//		try {
			Session session = sessionFactory.getCurrentSession();
//			Transaction tx = session.beginTransaction();
			session.saveOrUpdate(obj);
//			tx.commit();
			return true;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		} finally {
//			if (session != null) {
//				session.close();
//			}
//		}
	}

	public boolean update(Bouygues obj) {
//		Session session = null;
//		try {
			Session session = sessionFactory.getCurrentSession();
//			Transaction tx = session.beginTransaction();
			session.update(obj);
//			tx.commit();
			return true;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		} finally {
//			if (session != null) {
//				session.close();
//			}
//		}
	}

	public boolean delete(int id) {
//		Session session = null;
//		try {
			Session session = sessionFactory.getCurrentSession();
//			Transaction tx = session.beginTransaction();
			Bouygues bouygues = findById(id);
			if (bouygues == null) {
				return false;
			}
			session.delete(bouygues);
//			tx.commit();
			return true;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		} finally {
//			if (session != null) {
//				session.close();
//			}
//		}
	}

	public List<Bouygues> getAllBouygues() {
//		Session session = null;
//		try {
			Session session = sessionFactory.getCurrentSession();
			String hql = "FROM Bouygues";
			Query query = session.createQuery(hql);
			List<Bouygues> listBouygues = query.list();
			if (listBouygues == null || listBouygues.size() == 0) {
				return null;
			}
			return listBouygues;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		} finally {
//			if (session != null) {
//				session.close();
//			}
//		}
	}

	public Bouygues findById(int id) {
//		Session session = null;
//		try {
			Session session = sessionFactory.getCurrentSession();
			String hql = "FROM Bouygues b WHERE b.id = :id";
			Query query = session.createQuery(hql);
			query.setParameter("id", id);
			Bouygues bouygues = (Bouygues) query.list().get(0);
			return bouygues;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		} finally {
//			if (session != null) {
//				session.close();
//			}
//		}
	}

	public Bouygues findBySiteId(int siteId) {
//		try {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM Bouygues b WHERE b.siteId = :siteId";
		Query query = session.createQuery(hql);
		query.setParameter("siteId", siteId);
		List rs = query.list();
		if (rs != null && !rs.isEmpty()) {
			return (Bouygues) query.list().get(0);
		} else
			return null;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
	}

	public boolean saveOrUpdate(Bouygues obj) {
//		try {
			Session session = sessionFactory.getCurrentSession();
//			Transaction tx = session.beginTransaction();
			session.saveOrUpdate(obj);
//			tx.commit();
			return true;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
	}
	
	public Integer getMaxSiteId() {
		Session session = sessionFactory.getCurrentSession();
		String sql = "SELECT MAX(siteId) FROM config_lab002";
		Query query = session.createSQLQuery(sql);
		Object obj = query.list().get(0);
		Integer maxSiteId = 0;
		if (obj != null) {
			maxSiteId = (Integer) obj;
		}
		return maxSiteId;
	}

}
