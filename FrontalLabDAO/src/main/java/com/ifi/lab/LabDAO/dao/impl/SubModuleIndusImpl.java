package com.ifi.lab.LabDAO.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ifi.lab.LabDAO.dao.SubModuleIndusDAO;

public class SubModuleIndusImpl implements SubModuleIndusDAO{

	@Autowired
	private SessionFactory sessionFactory;
	public List<String> getSubmoduleByTail(String tailModule) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM SubModuleIndus b WHERE b.tailModule = :tailModule";
		Query query = session.createQuery(hql);
		query.setParameter("tailModule", tailModule);
		@SuppressWarnings("unchecked")
		List<String> listKey = (List<String>) query.list();
		return listKey;
	}

}
