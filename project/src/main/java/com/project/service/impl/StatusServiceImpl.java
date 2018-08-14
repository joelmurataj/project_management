package com.project.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.converters.StatusConverter;
import com.project.dao.StatusDao;
import com.project.dto.StatusDto;
import com.project.entity.Status;
import com.project.service.StatusService;

@Service("statusService")
public class StatusServiceImpl implements StatusService{

	@Autowired
	private StatusDao statusDao;
	
	@Override
	public ArrayList<StatusDto> getAllStatus() {
		ArrayList<StatusDto> statusDtoList = new ArrayList<StatusDto>();
		ArrayList<Status> statusList = statusDao.getAllStatus();
		for (int i = 0; i < statusList.size(); i++) {
			statusDtoList.add(StatusConverter.toStatusDto(statusList.get(i)));
		}
		
		return statusDtoList;
	
	}

}
