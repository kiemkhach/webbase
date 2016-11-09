package com.ifi.lab.LabDAO.dao.caloon;

import java.util.List;

import com.ifi.lab.LabDAO.model.caloon.CaloonConsommationRange;

public interface CaloonConsommationRangeDAO {
	List<CaloonConsommationRange> getAll();
	CaloonConsommationRange getById(Integer id);
	Integer createRange(CaloonConsommationRange obj);
	Boolean saveOrUpdate(CaloonConsommationRange obj);
	Boolean deleteRangeById(Integer id);
}
