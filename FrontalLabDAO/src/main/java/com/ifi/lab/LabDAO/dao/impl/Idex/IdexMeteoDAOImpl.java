package com.ifi.lab.LabDAO.dao.impl.Idex;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ifi.lab.LabDAO.dao.Idex.IdexMeteoDAO;
import com.ifi.lab.LabDAO.model.Idex.IdexMeteo;
import com.ifi.lab.LabDAO.model.Idex.IdexReleves;

@Repository("idexMeteoDAO")
@Transactional
public class IdexMeteoDAOImpl implements IdexMeteoDAO {
	@Autowired
	private SessionFactory sessionFactory;

	public Integer sumByInstallation(Integer installationId, Date fromDate, Date toDate) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "SELECT SUM(reel) FROM IdexMeteo WHERE idexInstallationId = :installationId AND date >= :fromDate AND date <= :toDate ";
		Query query = session.createQuery(hql);
		query.setParameter("installationId", installationId);
		query.setParameter("fromDate", fromDate);
		query.setParameter("toDate", toDate);
		Object obj = query.uniqueResult();
		if (obj != null) {
			return ((Long) query.uniqueResult()).intValue();
		} else {
			return 0;
		}
	}

	public List<IdexMeteo> findByInstallation(Integer idexInstallationId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM IdexMeteo m WHERE m.idexInstallationId = :idexInstallationId";
		Query query = session.createQuery(hql);
		query.setParameter("idexInstallationId", idexInstallationId);
		@SuppressWarnings("unchecked")
		List<IdexMeteo> idexMeteoList = query.list();
		if (idexMeteoList == null || idexMeteoList.size() == 0) {
			return null;
		}
		return idexMeteoList;
	}

	public List<IdexMeteo> getAll() {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM IdexMeteo m";
		Query query = session.createQuery(hql);
		@SuppressWarnings("unchecked")
		List<IdexMeteo> idexMeteoList = query.list();
		if (idexMeteoList == null || idexMeteoList.size() == 0) {
			return null;
		}
		return idexMeteoList;
	}

	public boolean deleteByInstallation(Integer installationId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "DELETE FROM IdexMeteo m "
				+ "WHERE m.idexInstallationId = :idexInstallationId";
		try{
			Query query = session.createQuery(hql);
			query.setParameter("idexInstallationId", installationId);
			query.executeUpdate();
			return true;
		}catch(Exception e){
			return false;
		}
	}

	public boolean saveMeteoList(List<IdexMeteo> idexMeteoList) {
		Session session = sessionFactory.getCurrentSession();
		try{
			for (int i=0; i < idexMeteoList.size(); i++) {
				session.save(idexMeteoList.get(i));
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

}
