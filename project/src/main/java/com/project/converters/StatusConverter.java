package com.project.converters;

import com.project.dto.StatusDto;
import com.project.entity.Status;

public class StatusConverter {
	public static StatusDto toStatusDto(Status status) {
		try {
			if (status != null) {
				StatusDto statusDto = new StatusDto();
				statusDto.setId(status.getId());
				statusDto.setName(status.getName());
				return statusDto;
			} else
				return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
