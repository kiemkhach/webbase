package com.ifi.lab.LabDAO.dao.impl;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ifi.lab.LabDAO.dao.MViewGatewayDAO;
import com.ifi.lab.LabDAO.model.MViewGateway;

@Repository("mViewGatewayDAO")
@Transactional
public class MViewGatewayDAOImpl implements MViewGatewayDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public boolean save(MViewGateway obj) {
		try {
			Session session = sessionFactory.getCurrentSession();
			session.saveOrUpdate(obj);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean saveAll(List<MViewGateway> listObj) {
		try {
			Session session = sessionFactory.getCurrentSession();
			for (MViewGateway obj : listObj) {
				session.saveOrUpdate(obj);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean delete(MViewGateway obj) {
		try {
			Session session = sessionFactory.getCurrentSession();
			session.delete(obj);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public MViewGateway findById(Integer id) {
		try {
			Session session = sessionFactory.getCurrentSession();
			String hql = "FROM MViewGateway m WHERE m.id = :id";
			Query query = session.createQuery(hql);
			query.setParameter("id", id);
			@SuppressWarnings("unchecked")
			List<MViewGateway> listObj = (List<MViewGateway>) query.list();
			if (listObj != null && listObj.size() > 0) {
				return listObj.get(0);
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public MViewGateway findByGatewayIdAndViewId(Integer gatewayId,
			Integer viewId) {
		try {
			Session session = sessionFactory.getCurrentSession();
			String hql = "FROM MViewGateway m WHERE m.gatewayId = :gatewayId AND m.viewId = :viewId";
			Query query = session.createQuery(hql);
			query.setParameter("gatewayId", gatewayId);
			query.setParameter("viewId", viewId);
			@SuppressWarnings("unchecked")
			List<MViewGateway> listObj = (List<MViewGateway>) query.list();
			if (listObj != null && listObj.size() > 0) {
				return listObj.get(0);
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public MViewGateway findByViewId(Integer viewId) {
		try {
			Session session = sessionFactory.getCurrentSession();
			String hql = "FROM MViewGateway m WHERE m.viewId = :viewId";
			Query query = session.createQuery(hql);
			query.setParameter("viewId", viewId);
			@SuppressWarnings("unchecked")
			List<MViewGateway> listObj = (List<MViewGateway>) query.list();
			if (listObj != null && listObj.size() > 0) {
				return listObj.get(0);
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Integer countByViewId(Integer viewId) {
		Integer result = 0;
		try {
			Session session = sessionFactory.getCurrentSession();
			String sql = "SELECT COUNT(*) FROM m_view_gateway mvg "
					+ "LEFT JOIN m_view mv ON mv.id = mvg.view_id "
					+ "LEFT JOIN monitor_gateway mg ON mg.id = mvg.gateway_id "
					+ "WHERE mvg.view_id = :viewId AND mg.active = 1";
			Query query = session.createSQLQuery(sql);
			query.setParameter("viewId", viewId);
			result =  ((BigInteger) query.uniqueResult()).intValue();
		} catch (Exception e) {
			result = 0;
			e.printStackTrace();
		}
		return result;
	}
	
	public int deleteGateWayOfView(Integer viewId, List<Integer> gatewayIdLst) {
		Session session = sessionFactory.getCurrentSession();
		StringBuilder sqlSB = new StringBuilder();
		sqlSB.append("DELETE FROM MViewGateway m WHERE m.viewId = ? ");

		if (gatewayIdLst != null && !gatewayIdLst.isEmpty()) {
			int gatewayIdLstSizeMinus = gatewayIdLst.size() - 1;
			sqlSB.append("AND m.gatewayId IN (");
			for (int i = 0; i < gatewayIdLstSizeMinus; i++) {
				sqlSB.append("?,");
			}
			sqlSB.append("?)");
		}
		Query query = session.createQuery(sqlSB.toString());
		query.setParameter(0, viewId);
		if (gatewayIdLst != null && !gatewayIdLst.isEmpty()) {
			for (int i = 0; i < gatewayIdLst.size(); i++) {
				query.setParameter(i + 1, gatewayIdLst.get(i));
			}
		}
		return query.executeUpdate();
	}
}
