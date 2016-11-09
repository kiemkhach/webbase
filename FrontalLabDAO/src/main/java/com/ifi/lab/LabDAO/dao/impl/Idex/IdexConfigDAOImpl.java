package com.ifi.lab.LabDAO.dao.impl.Idex;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ifi.lab.LabDAO.dao.Idex.IdexConfigDAO;
import com.ifi.lab.LabDAO.model.ConfigLab009;
import com.ifi.lab.LabDAO.model.Idex.IdexConfig;

@Repository("idexConfigDAO")
@Transactional
public class IdexConfigDAOImpl implements IdexConfigDAO {
	@Autowired
	private SessionFactory sessionFactory;

	public IdexConfig getByInstalltion(Integer installationId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM IdexConfig WHERE installationId = :installationId";
		Query query = session.createQuery(hql);
		query.setParameter("installationId", installationId);
		@SuppressWarnings("unchecked")
		List<IdexConfig> list = query.list();
		if(!list.isEmpty()){
			return list.get(0);
		}
		return new IdexConfig() ;
	}

	public List<IdexConfig> getAll() {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("unchecked")
		List<IdexConfig> listLab010Idex = session.createQuery("FROM IdexConfig").list();
		return listLab010Idex;
	}

	public boolean saveOrUpdate(IdexConfig idexConfig) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(idexConfig);
		//LOGGER.info("Insert ConfigPerial success");
		return true;
	}

}
