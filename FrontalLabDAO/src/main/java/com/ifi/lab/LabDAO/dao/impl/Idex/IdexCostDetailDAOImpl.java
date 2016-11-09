package com.ifi.lab.LabDAO.dao.impl.Idex;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ifi.lab.LabDAO.dao.Idex.IdexCostDetailDAO;
import com.ifi.lab.LabDAO.model.Idex.IdexCost;
import com.ifi.lab.LabDAO.model.Idex.IdexCostDetail;
@Repository("idexCostDetailDAO")
@Transactional
public class IdexCostDetailDAOImpl implements IdexCostDetailDAO{
	@Autowired
	private SessionFactory sessionFactory;
	public List<IdexCostDetail> findByIdexCostId(int idexCostId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM IdexCostDetail d "
				+ "LEFT JOIN fetch d.idexCost i "
				+ "WHERE i.idexCostId = :idexCostId";
		Query query = session.createQuery(hql);
		query.setParameter("idexCostId", idexCostId);
		@SuppressWarnings("unchecked")
		List<IdexCostDetail> idexCostDetailList = query.list();
		if (idexCostDetailList == null || idexCostDetailList.size() == 0) {
			return null;
		}
		return idexCostDetailList;
	}

	public List<IdexCostDetail> getAll() {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM IdexCostDetail";
		Query query = session.createQuery(hql);
		@SuppressWarnings("unchecked")
		List<IdexCostDetail> idexCostDetailList = query.list();
		if (idexCostDetailList == null || idexCostDetailList.size() == 0) {
			return null;
		}
		return idexCostDetailList;
	}

	public boolean insertList(List<IdexCostDetail> idexCostDetailList) {
		Session session = sessionFactory.getCurrentSession();
		try{
			for (int i=0; i < idexCostDetailList.size(); i++) {
				session.save(idexCostDetailList.get(i));
				if(i % 50 == 0){
					session.flush();
					session.clear();
				}
			}
			return true;
		}catch(Exception e){
			return false;
		}
	}

	public boolean deleteByIdexCostId(int idexCostId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "DELETE FROM IdexCostDetail d "
				+ "WHERE d.idexCost.idexCostId = :idexCostId";
		try{
			Query query = session.createQuery(hql);
			query.setParameter("idexCostId", idexCostId);
			query.executeUpdate();
			return true;
		}catch(Exception e){
			return false;
		}
	}
	public boolean delete(Integer installationId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "DELETE FROM IdexCostDetail c WHERE c.idexCost.idexCostId "
				+ " in ( SELECT ic.idexCostId FROM IdexCost ic WHERE "
				+ " ic.idexInstallationId = :idexInstallationId )";
		Query query = session.createQuery(hql);
		query.setInteger("idexInstallationId", installationId);
		int d = query.executeUpdate();
		if(d<0){
			return false;
		}else{
			return true;
		}
	}
}
