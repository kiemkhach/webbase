package com.ifi.lab.LabDAO.dao.impl.Idex;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ifi.lab.LabDAO.dao.Idex.IdexKeyDAO;
import com.ifi.lab.LabDAO.model.Idex.IdexKey;

@Repository("idexKeyDAO")
@Transactional
public class IdexKeyDAOImpl implements IdexKeyDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public List<IdexKey> getAll() {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM IdexKey";
		Query query = session.createQuery(hql);
		List<IdexKey> list = query.list();
		if (list == null || list.size() == 0) {
			return null;
		}
		return list;
	}
	
	public List<IdexKey> findByType(Integer type){
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM IdexKey WHERE type = :type ORDER BY orderNumber";
		Query query = session.createQuery(hql);
		query.setInteger("type", type);
		List<IdexKey> list = query.list();
		return list;
	}
}
