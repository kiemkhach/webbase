package com.ifi.lab.LabDAO.dao.Idex;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ifi.lab.LabDAO.model.Idex.IdexCostDetail;;

public interface IdexCostDetailDAO {
	List<IdexCostDetail> findByIdexCostId(int idexCostId);
	List<IdexCostDetail> getAll();
	boolean insertList(List<IdexCostDetail> idexCostDetailList);
	boolean deleteByIdexCostId(int idexCostId);
	boolean delete(Integer installationId);
}
