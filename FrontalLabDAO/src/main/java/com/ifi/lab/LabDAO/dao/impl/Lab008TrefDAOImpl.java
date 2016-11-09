package com.ifi.lab.LabDAO.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ifi.lab.LabDAO.dao.Lab008TrefDAO;
import com.ifi.lab.LabDAO.model.Lab008Tref;

/**
 * Created by nmha on 06/04/2016.
 */
@Repository("lab008TrefDAO")
@Transactional
public class Lab008TrefDAOImpl implements Lab008TrefDAO {
	@Autowired
	private SessionFactory sessionFactory;

	public boolean deleteAll() {
		Session session = sessionFactory.getCurrentSession();
		String hql = "DELETE FROM Lab008Tref";
		Query query = session.createQuery(hql);		
		query.executeUpdate();
		return true;
	}

	public Integer create(ArrayList<Lab008Tref> objList) {
		Session session = sessionFactory.getCurrentSession();
		
		int numberRecord = 0;
		if(objList != null && objList.size() > 0){
		//	Transaction tx = session.beginTransaction();
			for(int i = 0; i < objList.size(); i++){
				if(objList.get(i) != null){
					session.save(objList.get(i));
					numberRecord = numberRecord + 1;	
				}				
			}			
		//	tx.commit();
		//	session.close();			
		}
		
		return numberRecord;
	}


	public List<Lab008Tref> getAllData() {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM Lab008Tref t ORDER BY t.applicableDate DESC";
		Query query = session.createQuery(hql);		
		List<Lab008Tref> list = query.list();
		return list;
	}
}
