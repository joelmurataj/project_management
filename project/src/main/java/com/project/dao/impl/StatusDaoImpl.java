package com.project.dao.impl;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.project.dao.StatusDao;
import com.project.entity.Status;

@Repository(value = "statusDao")
@Scope("singleton")
@Component
public class StatusDaoImpl implements StatusDao{

	@PersistenceContext
	EntityManager entityManager;
	
	@Override
	public ArrayList<Status> getAllStatus() {
		try {
			ArrayList<Status> status = (ArrayList<Status>) entityManager
					.createQuery("select status from Status status where active=0",Status.class)
					.getResultList();
			
			return status;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
