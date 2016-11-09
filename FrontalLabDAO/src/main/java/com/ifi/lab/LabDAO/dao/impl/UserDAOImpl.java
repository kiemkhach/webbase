package com.ifi.lab.LabDAO.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ifi.lab.LabDAO.dao.UserDAO;
import com.ifi.lab.LabDAO.model.User;

@Repository("userDAO")
@Transactional
public class UserDAOImpl implements UserDAO {

	@Autowired
	private SessionFactory sessionFactory;

	// public void setSessionFactory(SessionFactory sessionFactory) {
	// this.sessionFactory = sessionFactory;
	// }

	public boolean save(User obj) {
		// try {
		Session session = sessionFactory.getCurrentSession();
		// Transaction tx = session.beginTransaction();
		session.persist(obj);
		// tx.commit();
		return true;
		// } catch (Exception e) {
		// e.printStackTrace();
		// return false;
		// }
	}

	public boolean saveAll(List<User> listObj) {
		Session session = null;
		// Transaction tx = null;
		// try {
		session = sessionFactory.getCurrentSession();
		// tx = session.beginTransaction();
		for (User obj : listObj) {
			session.persist(obj);
		}
		// tx.commit();
		return true;
		// } catch (RuntimeException e) {
		// try {
		// tx.rollback();
		// } catch (RuntimeException rbe) {
		// System.out.println("Couldn’t roll back transaction :" + rbe);
		// }
		// } finally {
		// if (session != null) {
		//
		// }
		// }
		// return false;
	}

	public boolean update(User obj) {
		// try {
		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		session.update(obj);
		tx.commit();
		//
		return true;
		// } catch (Exception e) {
		// e.printStackTrace();
		// return false;
		// }
	}

	public boolean delete(int userId) {
		// try {
		Session session = sessionFactory.getCurrentSession();
		// Transaction tx = session.beginTransaction();
		User obj = findById(userId);
		session.delete(obj);
		// tx.commit();
		return true;
		// } catch (Exception e) {
		// e.printStackTrace();
		// return false;
		// }
	}

	public List<User> getAllUser() {
		Session session = sessionFactory.getCurrentSession();
		List<User> listUser = session.createQuery("FROM User").list();

		return listUser;
	}

	public User findById(int id) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM User u WHERE u.id = :userId";
		Query query = session.createQuery(hql);
		query.setParameter("userId", id);
		List result = query.list();

		if (result != null && result.size() > 0) {
			User obj = (User) result.get(0);
			return obj;
		} else {
			return null;
		}
	}

	public User findByUserName(String username) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM User u WHERE u.username = :username";
		Query query = session.createQuery(hql);
		query.setParameter("username", username);
		List result = query.list();

		if (result != null && result.size() > 0) {
			User obj = (User) result.get(0);
			return obj;
		} else {
			return null;
		}
	}

	public List<String> getUsersInLab(String labName, Integer siteId) {
		Session session = sessionFactory.getCurrentSession();
		String sql = "SELECT username FROM user u "
				+ "LEFT JOIN user_lab ul ON ul.userId = u.id "
				+ "LEFT JOIN lab l ON l.id = ul.labId "
				+ "WHERE l.name = :labName AND ul.siteId = :siteId";
		Query query = session.createSQLQuery(sql);
		query.setParameter("siteId", siteId);
		query.setParameter("labName", labName);
		@SuppressWarnings("unchecked")
		List<String> listUser = query.list();
		return listUser;
	}
}
