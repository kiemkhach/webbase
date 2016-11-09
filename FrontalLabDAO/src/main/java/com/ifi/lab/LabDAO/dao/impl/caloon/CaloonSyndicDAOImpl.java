package com.ifi.lab.LabDAO.dao.impl.caloon;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ifi.lab.LabDAO.dao.caloon.CaloonSyndicDAO;
import com.ifi.lab.LabDAO.model.caloon.CaloonConsommationRange;
import com.ifi.lab.LabDAO.model.caloon.CaloonSyndic;
import com.ifi.lab.LabDAO.model.caloon.CaloonUser;

@Repository("caloonSyndicDAOImpl")
@Transactional
public class CaloonSyndicDAOImpl implements CaloonSyndicDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	public CaloonSyndic findById(Integer caloonSyndicId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM CaloonSyndic c WHERE c.id = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", caloonSyndicId);
		@SuppressWarnings("unchecked")
		List<CaloonSyndic> listlab = (List<CaloonSyndic>) query.list();
		if (listlab != null && listlab.size() > 0) {
			return listlab.get(0);
		} else {
			return null;
		}
	}
	
	public CaloonSyndic findDefalutByUser(Integer caloonUserId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM CaloonSyndic c WHERE c.caloonUserId = :caloonUserId AND isDefaultSyndic = true";
		Query query = session.createQuery(hql);
		query.setParameter("caloonUserId", caloonUserId);
		@SuppressWarnings("unchecked")
		List<CaloonSyndic> listlab = (List<CaloonSyndic>) query.list();
		if (listlab != null && listlab.size() > 0) {
			return listlab.get(0);
		} else {
			return null;
		}
	}
	
	public List<CaloonSyndic> findByUser(Integer caloonUserId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM CaloonSyndic c WHERE c.caloonUserId = :caloonUserId";
		Query query = session.createQuery(hql);
		query.setParameter("caloonUserId", caloonUserId);
		return (List<CaloonSyndic>) query.list();
	}

	public Integer createSyndic(CaloonSyndic obj) {
		Session session = sessionFactory.getCurrentSession();
		session.save(obj);
		//session.flush();
		return obj.getId();
	}

	public Boolean deleteSyndic(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "DELETE FROM CaloonSyndic c WHERE c.id = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		if(query.executeUpdate()<0){
			return false;
		}
		return true;
	}
	
	public List<CaloonConsommationRange> getAllConsommationRange() {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM CaloonConsommationRange c ORDER BY orderBy";
		Query query = session.createQuery(hql);
		return query.list();
	}
	
	public CaloonConsommationRange getConsommationRangeById(Integer id ) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM CaloonConsommationRange c WHERE id = :id ORDER BY orderBy";
		Query query = session.createQuery(hql);
		query.setInteger("id", id);
		List<CaloonConsommationRange> lst = query.list();
		if(!lst.isEmpty()){
			return lst.get(0);
		}else{
			return null;
		}
	}
	
	public List<CaloonSyndic> searchByKey(String key) {
		Session session = sessionFactory.getCurrentSession();
		StringBuilder sb = new StringBuilder();
		sb.append("FROM CaloonSyndic c");
		if (key != null) {
			sb.append(" WHERE UPPER(name) like :key OR UPPER(address) like :key");
		}
		sb.append(" ORDER BY name");
		Query query = session.createQuery(sb.toString());
		if (key != null) {
			query.setString("key", "%"+key.toUpperCase()+"%");
		}

		List<CaloonSyndic> lst = query.list();
		return lst;
	}
	
	public List<CaloonSyndic> getAll() {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM CaloonSyndic";
		Query query = session.createQuery(hql);
		@SuppressWarnings("unchecked")
		List<CaloonSyndic> list = query.list();
		if (list == null || list.size() == 0) {
			return null;
		}
		return list;
	}

	public Boolean saveOrUpdate(CaloonSyndic obj) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(obj);
		return true;
	}

}
