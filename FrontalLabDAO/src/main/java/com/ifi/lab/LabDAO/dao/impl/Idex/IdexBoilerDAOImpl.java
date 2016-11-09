package com.ifi.lab.LabDAO.dao.impl.Idex;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ifi.common.bean.Idex.IdexBoilerInfo;
import com.ifi.lab.LabDAO.dao.Idex.IdexBoilerDAO;
import com.ifi.lab.LabDAO.model.Idex.IdexBoiler;
import com.ifi.lab.LabDAO.model.Idex.IdexInstallation;

@Repository("idexBoilerDAO")
@Transactional
public class IdexBoilerDAOImpl implements IdexBoilerDAO {
	@Autowired
	private SessionFactory sessionFactory;

	public List<IdexBoiler> findByInstallation(Integer installationId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM IdexBoiler i "
				+ "LEFT JOIN fetch i.idexCounter c1  "
				+ "LEFT JOIN fetch c1.idexCounter c2 "
				+ " WHERE i.idexInstallation.idexInstallationId = :installationId";
		Query query = session.createQuery(hql);
		query.setParameter("installationId", installationId);
		@SuppressWarnings("unchecked")
		List<IdexBoiler> list = query.list();
		return list;
	}

	public int addBoiler(IdexBoilerInfo idexBoiler) {
		Session session = sessionFactory.getCurrentSession();

		IdexBoiler boi = new IdexBoiler();
		IdexInstallation installation = new IdexInstallation();

		installation.setIdexInstallationId(idexBoiler.getIdex_installation_id());
		boi.setIdexInstallation(installation);

		boi.setName(idexBoiler.getName());
		session.persist(boi);

		System.out.println("boilerId: ............ " + boi.getIdexBoilerId());

		return boi.getIdexBoilerId();
	}

	public Long getNumberBoiler() {
		Session session = sessionFactory.getCurrentSession();
		String hql = "SELECT COUNT(*) FROM IdexBoiler ";
		Query query  =session.createQuery(hql);
		return (Long) query.uniqueResult();
	}
	
	public IdexBoiler findById(Integer idexBoilerId){
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM IdexBoiler i WHERE i.idexBoilerId = :idexBoilerId";
		Query query = session.createQuery(hql);
		query.setInteger("idexBoilerId", idexBoilerId);
		@SuppressWarnings("unchecked")
		List<IdexBoiler> list = query.list();
		if(!list.isEmpty()){
			return list.get(0);
		}else{
			return null;
		}
	}

	public boolean delete(Integer installationId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "DELETE FROM IdexBoiler c WHERE c.idexInstallation.idexInstallationId = :idexInstallationId";
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
