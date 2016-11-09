package com.ifi.lab.LabDAO.dao.impl.caloon;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ifi.lab.LabDAO.dao.caloon.CaloonConsommationRangeDAO;
import com.ifi.lab.LabDAO.model.caloon.CaloonConsommationRange;
@Repository("caloonConsommationRangeDAO")
@Transactional

public class CaloonConsommationRangeDAOImpl implements CaloonConsommationRangeDAO{
	@Autowired
	private SessionFactory sessionFactory;

	public List<CaloonConsommationRange> getAll() {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM CaloonConsommationRange";
		Query query = session.createQuery(hql);
		@SuppressWarnings("unchecked")
		List<CaloonConsommationRange> list = query.list();
		if (list == null || list.size() == 0) {
			return null;
		}
		return list;
	}

	public CaloonConsommationRange getById(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM CaloonConsommationRange c WHERE c.id = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		@SuppressWarnings("unchecked")
		List<CaloonConsommationRange> listlab = (List<CaloonConsommationRange>) query.list();
		if (listlab != null && listlab.size() > 0) {
			return listlab.get(0);
		} else {
			return null;
		}
	}

	public Integer createRange(CaloonConsommationRange obj) {
		Session session = sessionFactory.getCurrentSession();
		session.save(obj);
		return obj.getId();
	}

	public Boolean saveOrUpdate(CaloonConsommationRange obj) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(obj);
		return true;
	}
	public Boolean deleteRangeById(Integer id) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "DELETE FROM CaloonConsommationRange c WHERE c.id = :id";
		Query query = session.createQuery(hql);
		query.setParameter("id", id);
		if(query.executeUpdate()<0){
			return false;
		}
		return true;
	}

}
