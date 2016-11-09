package com.ifi.lab.LabDAO.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ifi.lab.LabDAO.dao.UserLabDAO;
import com.ifi.lab.LabDAO.model.UserLab;

@Repository("userLabDAO")
@Transactional
public class UserLabDAOImpl implements UserLabDAO {

	@Autowired
	private SessionFactory sessionFactory;

	// public void setSessionFactory(SessionFactory sessionFactory) {
	// this.sessionFactory = sessionFactory;
	// }

	public boolean save(UserLab obj) {
		try {
			Session session = sessionFactory.getCurrentSession();
			// Transaction tx = session.beginTransaction();
			session.persist(obj);
			// tx.commit();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean saveAll(List<UserLab> listObj) {
		Session session = null;
		session = sessionFactory.getCurrentSession();
		// tx = session.beginTransaction();
		for (UserLab obj : listObj) {
			session.persist(obj);
		}
		// tx.commit();
		return true;
	}

	public boolean update(UserLab obj) {
		try {
			Session session = sessionFactory.getCurrentSession();
			// Transaction tx = session.beginTransaction();
			session.update(obj);
			// tx.commit();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean delete(UserLab obj) {
		try {
			Session session = sessionFactory.getCurrentSession();
			// Transaction tx = session.beginTransaction();
			session.delete(obj);
			// tx.commit();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteAll(List<UserLab> listObj) {
		Session session = null;
		session = sessionFactory.getCurrentSession();
		// tx = session.beginTransaction();
		for (UserLab obj : listObj) {
			session.delete(obj);
		}
		// tx.commit();
		return true;
	}

	public List<UserLab> getAllUserLab() {
		Session session = sessionFactory.getCurrentSession();
		List<UserLab> userLabs = session.createQuery("FROM UserLab").list();

		return userLabs;
	}

	public UserLab findById(int userId, int labId, int siteId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session
				.createQuery("FROM UserLab WHERE userId = :userId AND labId = :labId AND siteId = :siteId");
		query.setParameter("userId", userId);
		query.setParameter("labId", labId);
		query.setParameter("siteId", siteId);
		List<UserLab> result = query.list();

		if (result != null && result.size() > 0) {
			UserLab obj = (UserLab) result.get(0);
			return obj;
		} else {
			return null;
		}
	}

	public List<UserLab> findByLabAndSite(int labId, int siteId) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM UserLab WHERE labId = :labId AND siteId = :siteId");
		query.setParameter("labId", labId);
		query.setParameter("siteId", siteId);
		List<UserLab> result = query.list();
		return result;
	}

	public int findByUserAndLab(String userName, String labName) {
		Session session = sessionFactory.getCurrentSession();
		String sql = "SELECT ul.siteid FROM user_lab ul " + "LEFT JOIN lab l ON l.id = ul.labId "
				+ "LEFT JOIN user u ON u.id = ul.userId " + "WHERE u.username = :userName and l.name = :labName";
		Query query = session.createSQLQuery(sql);
		query.setParameter("userName", userName);
		query.setParameter("labName", labName);
		@SuppressWarnings("unchecked")
		List<Integer> listTemp = query.list();

		if (listTemp != null && listTemp.size() > 0 && listTemp.get(0) != null) {
			Integer rs = (Integer) listTemp.get(0);
			if (rs == null) {
				return 0;
			} else {
				return rs;
			}
		}
		return -1;

	}

	public List<UserLab> getByUserAndLab(List<String> userLst, int labId) {
		Session session = sessionFactory.getCurrentSession();
		StringBuilder sb = new StringBuilder();
		sb.append("FROM UserLab l WHERE l.labId = ? and l.userId in (select id from User where username in (");
		int sizeMinus = userLst.size() - 1;
		for (int i = 0; i < sizeMinus; i++) {
			sb.append("?,");
		}
		sb.append("?))");
		Query query = session.createQuery(sb.toString());
		query.setParameter(0, labId);
		for (int i = 0; i < userLst.size(); i++) {
			query.setParameter(i + 1, userLst.get(i));
		}
		List<UserLab> result = query.list();
		return result;
	}

	public boolean updateSiteId(Integer labId, Integer oldSiteId, Integer newSiteId) {
		Session session = sessionFactory.getCurrentSession();
		String sql = "Update UserLab set siteId = :newSiteId where labId = :labId and siteId = :oldSiteId";
		Query query = session.createQuery(sql);
		query.setParameter("newSiteId", newSiteId);
		query.setParameter("labId", labId);
		query.setParameter("oldSiteId", oldSiteId);
		int result = query.executeUpdate();
		if (result > 0) {
			return true;
		} else {
			return false;
		}

	}

	public List<UserLab> getByUserAndLab(int userId, int labId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM UserLab ul WHERE ul.userId = :userId AND ul.labId = :labId";
		Query query = session.createQuery(hql);
		query.setParameter("userId", userId);
		query.setParameter("labId", labId);
		List<UserLab> list = query.list();
		return list;
	}

	public List<UserLab> getByUserAndLab(String userName, String labName) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM UserLab ul WHERE ul.userId in (SELECT id FROM User Where username = :username) AND ul.labId in (SELECT id FROM Lab WHERE name = :labName)";

		Query query = session.createQuery(hql);
		query.setString("username", userName);
		query.setString("labName", labName);
		@SuppressWarnings("unchecked")
		List<UserLab> list = query.list();
		return list;
	}
}
