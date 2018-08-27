package com.project.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.project.dao.StatusDao;
import com.project.entity.Status;

@Repository(value = "statusDao")
public class StatusDaoImpl implements StatusDao {
	private static final Logger logger = LogManager.getLogger(StatusDaoImpl.class.getName());

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public List<Status> getAllStatus() {
		try {
			logger.debug("finding all status");
			ArrayList<Status> status = (ArrayList<Status>) entityManager
					.createQuery("select status from Status status where active=0", Status.class).getResultList();
			if (status.isEmpty()) {
				logger.debug("no status found");
				return null;
			} else {
				logger.debug("list of status retrieved:" + status);
				return status;
			}

		} catch (RuntimeException e) {
			logger.error("error finding status:" + e.getMessage());
			return null;
		}
	}

}
