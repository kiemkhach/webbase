package com.ifi.lab.LabDAO.dao.impl.Idex;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ifi.lab.LabDAO.dao.Idex.IdexCostDAO;
import com.ifi.lab.LabDAO.model.Idex.IdexCost;
import com.ifi.lab.LabDAO.model.Idex.IdexCostDetail;

@Repository("idexCostDAO")
@Transactional
public class IdexCostDAOImpl implements IdexCostDAO {
	@Autowired
	private SessionFactory sessionFactory;
	
	public Map<Integer,List<IdexCostDetail>> getDetailByInstallation(Integer installationId, Date fromDate, Date toDate){
		Session session = sessionFactory.getCurrentSession();
		StringBuilder sb = new StringBuilder();
		sb.append("FROM IdexCostDetail d "
				+ "LEFT JOIN fetch d.idexCost i "
				+ "WHERE d.idexCost.idexInstallationId = :installationId");
		if(fromDate != null){
			sb.append(" AND d.detailDate >= :fromDate");
		}
		if(toDate != null){
			sb.append(" AND d.detailDate <= :toDate");
		}
		sb.append(" ORDER BY d.detailDate");
		Query query = session.createQuery(sb.toString());
		query.setInteger("installationId", installationId);
		if(fromDate != null){
			query.setParameter("fromDate", fromDate);
		}
		if(toDate != null){
			query.setParameter("toDate", toDate);
		}
		
		@SuppressWarnings("unchecked")
		List<IdexCostDetail> detailLst = query.list();
		Map<Integer,List<IdexCostDetail>> map = new HashMap<Integer, List<IdexCostDetail>>();
		for(IdexCostDetail idexCostDetail: detailLst){
			List<IdexCostDetail> lst = null;
			Integer key = idexCostDetail.getIdexCost().getIdexCostId();
			if(map.containsKey(key)){
				lst = map.get(key);
			}else{
				lst = new ArrayList<IdexCostDetail>();
			}
			lst.add(idexCostDetail);
			
			map.put(key, lst);
		}
		return map;
	}

	public List<IdexCost> findByInstallation(Integer installationId) {
		Session session = sessionFactory.getCurrentSession();
		StringBuilder sb = new StringBuilder();
		sb.append("FROM IdexCost i "
				+ "LEFT JOIN fetch i.idexSiteIn s1 "
				+ "LEFT JOIN fetch i.idexEnergySupplier u "
				+ "LEFT JOIN fetch u.idexEnergyType t "
				+ "WHERE i.idexInstallationId = :installationId");
		Query query = session.createQuery(sb.toString());
		query.setParameter("installationId", installationId);
		@SuppressWarnings("unchecked")
		List<IdexCost> list = query.list();
		return list;
	}

	public List<IdexCost> getAll() {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM  com.ifi.lab.LabDAO.model.Idex.IdexCost";
		Query query = session.createQuery(hql);
		@SuppressWarnings("unchecked")
		List<IdexCost> idexCostList = query.list();
		if (idexCostList == null || idexCostList.size() == 0) {
			return null;
		}
		return idexCostList;
	}

	public List<IdexCost> findByInstallationId(Integer installationId) {
		Session session = sessionFactory.getCurrentSession();
		StringBuilder sb = new StringBuilder();
		sb.append("FROM IdexCost i "
				+ "WHERE i.idexInstallationId = :installationId");
		sb.append(" ORDER BY i.name");
		Query query = session.createQuery(sb.toString());
		query.setParameter("installationId", installationId);
		@SuppressWarnings("unchecked")
		List<IdexCost> list = query.list();
		return list;
	}

	public boolean save(IdexCost obj) {
		Session session = sessionFactory.getCurrentSession();
		try{
			session.save(obj);
			return true;
		}catch (Exception e) {
			return false;
		}
	}

	public boolean delete(Integer installationId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "DELETE FROM IdexCost c WHERE c.idexInstallationId = :idexInstallationId";
		Query query = session.createQuery(hql);
		query.setInteger("idexInstallationId", installationId);
		int d = query.executeUpdate();
		if(d<0){
			return false;
		}else{
			return true;
		}
	}

	public boolean deleteById(int idexCostId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "DELETE FROM IdexCost c WHERE c.idexCostId = :idexCostId";
		Query query = session.createQuery(hql);
		query.setInteger("idexCostId", idexCostId);
		int d = query.executeUpdate();
		if(d<0){
			return false;
		}else{
			return true;
		}
	}
}
