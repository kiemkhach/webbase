package com.ifi.lab.LabDAO.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ifi.common.util.FrontalKey;
import com.ifi.lab.LabDAO.dao.ConfigLab008V2DAO;
import com.ifi.lab.LabDAO.model.ConfigLab008V2;
import com.ifi.lab.LabDAO.model.Lab008ECS;

/**
 * Created by Kim Anh.
 */
@Repository("lab008V2DAO")
@Transactional
public class ConfigLab008DAOV2Impl implements ConfigLab008V2DAO {
	@Autowired
	private SessionFactory sessionFactory;

	public ConfigLab008V2 findBySiteId(Integer siteId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM ConfigLab008V2 c WHERE c.siteId = :siteId";
		Query query = session.createQuery(hql);
		query.setInteger("siteId", siteId);
		@SuppressWarnings("unchecked")
		List<Object> objLst = query.list();
		if (objLst.size() > 0) {
			return (ConfigLab008V2) query.list().get(0);
		} else {
			return null;
		}
	}

	public List<Lab008ECS> getAllECSBySiteId(Integer siteId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM Lab008ECS c WHERE "
				+ "c.configLab008V2ID = (SELECT id FROM ConfigLab008V2 WHERE siteId = :siteId) ";
		Query query = session.createQuery(hql);
		query.setInteger("siteId", siteId);
		@SuppressWarnings("unchecked")
		List<Lab008ECS> list = query.list();
		if (list == null || list.size() == 0) {
			return null;
		}
		return list;
	}

	public List<Lab008ECS> findECSBySiteId(Integer configLab008V2ID) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM Lab008ECS c WHERE c.configLab008V2ID = :configLab008V2ID";
		Query query = session.createQuery(hql);
		query.setInteger("configLab008V2ID", configLab008V2ID);
		@SuppressWarnings("unchecked")
		List<Lab008ECS> list = query.list();
		if (list == null || list.size() == 0) {
			return null;
		}
		return list;
	}

	public boolean delete(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		ConfigLab008V2 configLab = findById(id);
		if (configLab == null) {
			return false;
		}
		session.delete(configLab);
		return true;
	}

	public Integer getMaxSiteId() {
		Session session = sessionFactory.getCurrentSession();
		/* String sql = "SELECT MAX(site_id) FROM ConfigLab008V2"; */
		String sql = "SELECT MAX(site_id) FROM config_lab008_v2";
		Query query = session.createSQLQuery(sql);
		Object obj = query.list().get(0);
		Integer maxSiteId = 0;
		if (obj != null) {
			maxSiteId = (Integer) obj;
		}
		return maxSiteId;
	}

	public ConfigLab008V2 findById(int id) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM ConfigLab008V2 c WHERE c.id = :id";
		Query query = session.createQuery(hql);
		query.setInteger("id", id);
		@SuppressWarnings("unchecked")
		List<Object> objLst = query.list();
		if (objLst.size() > 0) {
			return (ConfigLab008V2) objLst.get(0);
		} else {
			return null;
		}
	}

	public boolean saveOrUpdate(ConfigLab008V2 obj) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(obj);
		return true;
	}

	public List<ConfigLab008V2> getAllConfig() {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM ConfigLab008V2";
		Query query = session.createQuery(hql);
		@SuppressWarnings("unchecked")
		List<ConfigLab008V2> list = query.list();
		if (list == null || list.size() == 0) {
			return null;
		}
		return list;
	}

	public List<Lab008ECS> findECSBySiteIdAndType(Integer siteId, Integer type) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM Lab008ECS c WHERE "
				+ "c.configLab008V2ID = (SELECT id FROM ConfigLab008V2 WHERE siteId = :siteId) " + "AND type = :type";
		Query query = session.createQuery(hql);
		query.setInteger("siteId", siteId);
		query.setInteger("type", type);
		@SuppressWarnings("unchecked")
		List<Lab008ECS> list = query.list();
		if (list == null || list.size() == 0) {
			return null;
		}
		return list;
	}

	public List<Lab008ECS> findECSBySiteIdAndType(Integer siteId, List<Integer> typeLst) {
		Session session = sessionFactory.getCurrentSession();
		StringBuilder hqlSb = new StringBuilder("FROM Lab008ECS c WHERE "
				+ "c.configLab008V2ID = (SELECT id FROM ConfigLab008V2 WHERE siteId = ?) " + "AND type IN(");
		int sizeMinus = typeLst.size() - 1;
		for (int i = 0; i < sizeMinus; i++) {
			hqlSb.append("?,");
		}
		hqlSb.append("?)");
		Query query = session.createQuery(hqlSb.toString());
		int count = 0;
		query.setInteger(count++, siteId);
		for (int i = 0; i < typeLst.size(); i++) {
			query.setInteger(count++, typeLst.get(i));
		}

		@SuppressWarnings("unchecked")
		List<Lab008ECS> list = query.list();
		return list;
	}

	public Integer getBoilType(Integer siteId) {
		Session session = sessionFactory.getCurrentSession();
		/* String sql = "SELECT MAX(site_id) FROM ConfigLab008V2"; */
		String sql = "SELECT boilerType FROM ConfigLab008V2 WHERE siteId = :siteId";
		Query query = session.createQuery(sql);
		query.setInteger("siteId", siteId);
		Object obj = query.list().get(0);
		Integer boilType = 0;
		if (obj != null) {
			boilType = (Integer) obj;
		}
		return boilType;
	}

	public List<ConfigLab008V2> getConfigByUserExcept(String userName, int configLabID) {
		Session session = sessionFactory.getCurrentSession();
		StringBuilder sb = new StringBuilder();
		sb.append("FROM ConfigLab008V2 WHERE siteId IN (" + "SELECT siteId FROM UserLab WHERE "
				+ "labId = (SELECT id From Lab WHERE name = ?) "
				+ "AND userId = (SELECT id From User WHERE username = ?)) and id != ?");
		Query query = session.createQuery(sb.toString());
		int count = 0;
		query.setString(count++, FrontalKey.LAB_NAME_008);
		query.setString(count++, userName);
		query.setInteger(count++, configLabID);

		@SuppressWarnings("unchecked")
		List<ConfigLab008V2> list = query.list();
		return list;
	}

	public List<Lab008ECS> findECSByType(Integer type) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM Lab008ECS c WHERE c.type = :type";
		Query query = session.createQuery(hql);
		query.setInteger("type", type);
		@SuppressWarnings("unchecked")
		List<Lab008ECS> list = query.list();
		if (list == null || list.size() == 0) {
			return null;
		}
		return list;
	}
}
