package com.ifi.lab.LabDAO.dao.impl.Idex;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ifi.lab.LabDAO.dao.Idex.IdexCounterDAO;
import com.ifi.lab.LabDAO.model.Lab009Module;
import com.ifi.lab.LabDAO.model.Idex.IdexCounter;
import com.ifi.lab.LabDAO.model.Idex.IdexEnergySupplier;
@Repository("idexCounterDAO")
@Transactional
public class IdexCounterDAOImpl implements IdexCounterDAO{
	@Autowired
	private SessionFactory sessionFactory;
	public List<IdexCounter> findAllByInstallation(Integer installationId){
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM IdexCounter i "
				+ "LEFT JOIN fetch i.idexBoiler e "
				+ "LEFT JOIN fetch i.idexEnergySupplier e "
				+ "LEFT JOIN fetch e.idexEnergyType "
				+ "LEFT JOIN fetch i.idexCounter c1 "
				+ "WHERE i.idexInstallation.idexInstallationId = :installationId";
		Query query = session.createQuery(hql);
		query.setInteger("installationId", installationId);
		@SuppressWarnings("unchecked")
		List<IdexCounter> list = query.list();
		return list;
	}
	
	public List<IdexCounter> findByInstallation(Integer installationId){
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM IdexCounter i WHERE i.idexInstallation.idexInstallationId = :installationId";
		Query query = session.createQuery(hql);
		query.setInteger("installationId", installationId);
		@SuppressWarnings("unchecked")
		List<IdexCounter> list = query.list();
		return list;
	}
	
	public boolean updateCoefficient(int counterId, float coefficient) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "update IdexCounter c set c.coefficientEnergetique = :coefficient "
				+ "WHERE c.idexCounterId = :counterId";
		try {
			Query query = session.createQuery(hql);
			query.setParameter("coefficient", coefficient);
			query.setParameter("counterId", counterId);
			int result = query.executeUpdate();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Long getNumberCounter() {
		Session session = sessionFactory.getCurrentSession();
		String hql = "SELECT COUNT(*) FROM IdexCounter ";
		Query query  =session.createQuery(hql);
		return (Long) query.uniqueResult();
	}
	
	public IdexCounter findById(Integer idexCounterId){
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM IdexCounter i WHERE i.idexCounterId = :idexCounterId";
		Query query = session.createQuery(hql);
		query.setInteger("idexCounterId", idexCounterId);
		@SuppressWarnings("unchecked")
		List<IdexCounter> list = query.list();
		if(!list.isEmpty()){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	public IdexCounter findParentById(Integer idexCounterId){
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM IdexCounter i "
				+ "LEFT JOIN fetch i.idexBoiler "
				+ "LEFT JOIN fetch i.idexEnergySupplier "
				+ "LEFT JOIN fetch i.idexSite "
				+ "LEFT JOIN fetch i.idexCounter "
				+ "WHERE i.idexCounterId = :idexCounterId";
		Query query = session.createQuery(hql);
		query.setInteger("idexCounterId", idexCounterId);
		@SuppressWarnings("unchecked")
		List<IdexCounter> list = query.list();
		if(!list.isEmpty()){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	public int saveIdexCounter(IdexCounter idexCounter) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(idexCounter);
		return idexCounter.getIdexCounterId();
	}

	public boolean delete(Integer installationId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "DELETE FROM IdexCounter c WHERE c.idexInstallation.idexInstallationId = :idexInstallationId";
		Query query = session.createQuery(hql);
		query.setInteger("idexInstallationId", installationId);
		int d = query.executeUpdate();
		if(d<0){
			return false;
		}else{
			return true;
		}
	}

	public boolean deleteByType(Integer type, Integer installationId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "	DELETE FROM idex_counter "
				+ " 	WHERE idex_counter.counter_type = :counterType "
				+ "		AND idex_counter.idex_installation_id = :idexInstallationId ";
		Query query = session.createSQLQuery(hql);
		query.setInteger("counterType", type);
		query.setInteger("idexInstallationId", installationId);
		int d = query.executeUpdate();
		if(d<0){
			return false;
		}else{
			return true;
		}
	}

	public boolean deleteCounterSite(Integer installationId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "DELETE FROM idex_counter_site "
				+ "	WHERE idex_counter_site.idex_site_id "
				+ "	in (SELECT idex_site.idex_site_id "
				+ " FROM idex_site WHERE idex_site.idex_installation_id = :idexInstallationId ) ";
		Query query = session.createSQLQuery(hql);
		query.setInteger("idexInstallationId", installationId);
		int d = query.executeUpdate();
		if(d<0){
			return false;
		}else{
			return true;
		}
	}

}
