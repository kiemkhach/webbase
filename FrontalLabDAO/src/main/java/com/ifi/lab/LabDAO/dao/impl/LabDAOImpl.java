package com.ifi.lab.LabDAO.dao.impl;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ifi.common.bean.LabSubcription;
import com.ifi.lab.LabDAO.dao.LabDAO;
import com.ifi.lab.LabDAO.model.Lab;
import com.ifi.lab.LabDAO.utils.MappingObject;

@Repository("labDAO")
@Transactional
public class LabDAOImpl implements LabDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public boolean save(Lab obj) {
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

	public boolean saveAll(List<Lab> listObj) {
		Session session = null;
		// Transaction tx = null;
		// try {
		session = sessionFactory.getCurrentSession();
		// tx = session.beginTransaction();
		for (Lab obj : listObj) {
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
		// session.close();
		// }
		// }
		// return false;
	}

	public boolean update(Lab obj) {
		// try {
		Session session = sessionFactory.getCurrentSession();
		// Transaction tx = session.beginTransaction();
		session.update(obj);
		// tx.commit();
		return true;
		// } catch (Exception e) {
		// e.printStackTrace();
		// return false;
		// }
	}

	public boolean delete(int labId) {
		// try {
		Session session = sessionFactory.getCurrentSession();
		// Transaction tx = session.beginTransaction();
		Serializable id = Integer.valueOf(labId);
		Object obj = session.load(Lab.class, id);
		if (obj != null) {
			session.delete(obj);
			return true;
		}
		// tx.commit();
		return false;
		// } catch (Exception e) {
		// e.printStackTrace();
		// return false;
		// }
	}

	public List<Lab> getAllLab() {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<Lab> listLab = session.createQuery("FROM Lab").list();
		return listLab;
	}

	public Lab findByName(String name) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM Lab u WHERE u.name = :name";
		Query query = session.createQuery(hql);
		query.setParameter("name", name);
		List result = query.list();
		if (result != null && result.size() > 0) {
			Lab obj = (Lab) result.get(0);
			return obj;
		} else {
			return null;
		}
	}

	// public List<Lab> findByUserCSMId(List<Integer> listUserCSMId) {
	// Session session = sessionFactory.getCurrentSession();
	// String sql = "SELECT lab.* FROM lab " + "INNER JOIN (user_lab INNER JOIN
	// user ON user_lab.userId = user.id) "
	// + "ON lab.id = user_lab.labId " + "WHERE user_csm_id = :userCsmId";
	// List<Lab> labs = new ArrayList<Lab>();
	// for (Integer userCsmId : listUserCSMId) {
	// Query query = session.createSQLQuery(sql);
	// query.setParameter("userCsmId", userCsmId);
	// @SuppressWarnings("unchecked")
	// List<Object[]> listTemp = query.list();
	// List<Lab> listLab = null;
	// if (listTemp != null && listTemp.size() > 0) {
	// listLab = MappingObject.mappingObjectToLab(listTemp);
	// }
	// if (listLab != null) {
	// labs.addAll(listLab);
	// }
	// }
	// return labs;
	// }

	public Lab findById(int id) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM Lab u WHERE u.id = :labId";
		Query query = session.createQuery(hql);
		query.setParameter("labId", id);
		List result = query.list();
		if (result != null && result.size() > 0) {
			Lab obj = (Lab) result.get(0);
			return obj;
		} else {
			return null;
		}
	}

	public List<Lab> findByUserName(List<String> listUsername) {
		Session session = sessionFactory.getCurrentSession();
		String sql = "SELECT lab.id,lab.url,lab.uri,lab.name,user_lab.siteId FROM lab "
				+ "INNER JOIN (user_lab INNER JOIN user ON user_lab.userId = user.id) " + "ON lab.id = user_lab.labId "
				+ "WHERE user.username = :username";
		List<Lab> labs = new ArrayList<Lab>();
		for (String username : listUsername) {
			Query query = session.createSQLQuery(sql);
			query.setParameter("username", username);
			@SuppressWarnings("unchecked")
			List<Object[]> listTemp = query.list();
			List<Lab> listLab = null;
			if (listTemp != null && listTemp.size() > 0) {
				listLab = MappingObject.mappingObjectToLab(listTemp);
			}
			if (listLab != null) {
				labs.addAll(listLab);
			}
		}
		return labs;
	}
	
	/**
	 * Get All Subcription Of User Lst
	 * 
	 */
	public List<Lab> getSubcriptionByUser(List<String> listUsername) {
		Session session = sessionFactory.getCurrentSession();
		StringBuilder sqlSB = new StringBuilder();
		sqlSB.append("SELECT l.Id labId,ul.siteId siteId,l.url url,l.uri uri " + "FROM lab l "
				+ "INNER JOIN user_lab ul ON l.id = ul.labId " + "INNER JOIN user u ON ul.userId = u.id "
				+ "WHERE u.username IN (");
		int sizeMinus = listUsername.size() - 1;
		for (int i = 0; i < sizeMinus; i++) {
			sqlSB.append("?,");
		}
		sqlSB.append("?)");
		Query query = session.createSQLQuery(sqlSB.toString());
		for (int i = 0; i < listUsername.size(); i++) {
			query.setParameter(i, listUsername.get(i));
		}

		List<Lab> labLst = new ArrayList<Lab>();
		List<Object[]> listTemp = query.list();

		for (Object[] row : listTemp) {
			Lab lab = new Lab();
			// labid
			int labId = Integer.parseInt(row[0].toString());
			// siteId
			int siteId = Integer.parseInt(row[1].toString());
			// url
			String url = row[2].toString();
			// uri
			String uri = row[3].toString();

			lab.setId(labId);
			lab.setSiteId(siteId);
			lab.setUri(uri);
			lab.setUrl(url);
			labLst.add(lab);
		}

		return labLst;
	}
	
	public List<Lab> getAllLabByLstUser(List<String> userNameLst) {
		Session session = sessionFactory.getCurrentSession();
		StringBuilder hqlSB = new StringBuilder();
		hqlSB.append(
				"FROM Lab l WHERE l.id in (select labId from UserLab where userId in (select id from User where username in (");
		int sizeMinus = userNameLst.size() - 1;
		for (int i = 0; i < sizeMinus; i++) {
			hqlSB.append("?,");
		}
		hqlSB.append("?)))");

		Query query = session.createQuery(hqlSB.toString());
		for (int i = 0; i < userNameLst.size(); i++) {
			query.setParameter(i, userNameLst.get(i));
		}
		List<Lab> result = query.list();
		return result;
	}

	public String getlogoPath(String labId) {
		Session session = sessionFactory.getCurrentSession();
		String sql = "SELECT logo_path FROM lab WHERE id = :labId ";
		Query query = session.createSQLQuery(sql);
		query.setParameter("labId", labId);
		return (String) query.uniqueResult();
	}

	public String getSiteName(String tableName, String columnName, String columnKey, Integer siteId) {
		try {
			Session session = sessionFactory.getCurrentSession();
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT ");
			sb.append(columnName);
			sb.append(" FROM ");
			sb.append(tableName);
			sb.append(" WHERE  ");
			sb.append(columnKey);
			sb.append(" =:siteId ");
			Query query = session.createSQLQuery(sb.toString());
			query.setParameter("siteId", siteId);
			return (String) query.uniqueResult();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Check user can access to Lab
	 */
	public boolean checkLabAccess(List<String> userNameLst) {
		boolean ok = false;
		if (userNameLst == null || userNameLst.isEmpty()) {
			return ok;
		}
		Session session = sessionFactory.getCurrentSession();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT COUNT(*) FROM user_lab ul LEFT JOIN user u ON ul.userId = u.id WHERE username in( ");
		int sizeMinus = userNameLst.size() - 1;
		for (int i = 0; i < sizeMinus; i++) {
			sb.append("?,");
		}
		sb.append("?)");
		Query query = session.createSQLQuery(sb.toString());
		for (int i = 0; i < userNameLst.size(); i++) {
			query.setParameter(i, userNameLst.get(i));
		}
		BigInteger rs = (BigInteger) query.uniqueResult();
		if (rs != null && rs.intValue() > 0) {
			ok = true;
		}
		return ok;
	}
	
	public List<LabSubcription> getLabSubcription(String columnKey, String columnSite, String tableSite,
			List<Integer> siteIDLst) {

		Session session = sessionFactory.getCurrentSession();
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT " + columnKey + "," + columnSite + " FROM " + tableSite + " WHERE " + columnKey + " IN (");
		int sizeMinus = siteIDLst.size() - 1;
		for (int i = 0; i < sizeMinus; i++) {
			sb.append("?,");
		}
		sb.append("?)");
		Query query = session.createSQLQuery(sb.toString());
		for (int i = 0; i < siteIDLst.size(); i++) {
			query.setParameter(i, siteIDLst.get(i));
		}
		List<LabSubcription> lstRs = new ArrayList<LabSubcription>();
		List<Object[]> lst = query.list();
		for(Object[] arr: lst){
			LabSubcription sub = new LabSubcription();
			sub.setSubID((Integer)arr[0]);
			sub.setSubName((String)arr[1]);
			lstRs.add(sub);
		}
		return lstRs;
		
	}
}
