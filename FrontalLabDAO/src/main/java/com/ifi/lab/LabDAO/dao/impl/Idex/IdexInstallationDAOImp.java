package com.ifi.lab.LabDAO.dao.impl.Idex;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ifi.common.bean.Idex.InstallationOneExelRowData;
import com.ifi.common.bean.Idex.NodeInfo;
import com.ifi.common.bean.Idex.IdexConstant.IdexConstant;
import com.ifi.common.util.FrontalKey;
import com.ifi.common.util.LabUtils;
import com.ifi.lab.LabDAO.dao.Idex.IdexBoilerDAO;
import com.ifi.lab.LabDAO.dao.Idex.IdexCounterDAO;
import com.ifi.lab.LabDAO.dao.Idex.IdexEnergySupplierDAO;
import com.ifi.lab.LabDAO.dao.Idex.IdexInstallationDAO;
import com.ifi.lab.LabDAO.dao.Idex.IdexSiteDAO;
import com.ifi.lab.LabDAO.model.Idex.IdexBoiler;
import com.ifi.lab.LabDAO.model.Idex.IdexCounter;
import com.ifi.lab.LabDAO.model.Idex.IdexEnergySupplier;
import com.ifi.lab.LabDAO.model.Idex.IdexEnergyType;
import com.ifi.lab.LabDAO.model.Idex.IdexInstallation;
import com.ifi.lab.LabDAO.model.Idex.IdexSite;
import com.ifi.lab.LabDAO.model.Idex.IdexValue;

@Repository("idexInstallationDAO")
@Transactional
public class IdexInstallationDAOImp implements IdexInstallationDAO {
	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private IdexEnergySupplierDAO idexEnergySupplierDAO;

	@Autowired
	private IdexSiteDAO idexSiteDAO;

	@Autowired

	private IdexBoilerDAO idexBoilerDAO;
	@Autowired
	private IdexCounterDAO idexCounterDAO;

	public IdexInstallation findById(Integer installationId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM IdexInstallation i WHERE idexInstallationId = :idexInstallationId";
		Query query = session.createQuery(hql);
		query.setParameter("idexInstallationId", installationId);
		@SuppressWarnings("unchecked")
		List<IdexInstallation> list = query.list();
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	public NodeInfo getNode(Integer insallationId, Integer nodeId, Integer type) {
		NodeInfo result = new NodeInfo();
		List<InstallationOneExelRowData> listRow = new ArrayList<InstallationOneExelRowData>();
		String parentStr = "";

		if (type == null || type <= 0) {
			type = 1;
		}
		Session session = sessionFactory.getCurrentSession();
		switch (type) {
		case IdexConstant.INSTATLLATION_TYPE:
			String sql1 = "SELECT i.lng, i.lat, i.name FROM idex_installation i WHERE i.idex_installation_id=:iId";
			Query query1 = session.createSQLQuery(sql1);
			query1.setInteger("iId", insallationId);
			@SuppressWarnings("unchecked")
			List<Object[]> rows1 = query1.list();
			if (rows1 != null && rows1.size() > 0) {
				for (Object[] row : rows1) {
					InstallationOneExelRowData oneRow1 = new InstallationOneExelRowData();
					oneRow1.setName("lng");
					oneRow1.setValue(row[0] != null ? row[0].toString() : "");
					listRow.add(oneRow1);

					InstallationOneExelRowData oneRow2 = new InstallationOneExelRowData();
					oneRow2.setName("lat");
					oneRow2.setValue(row[1] != null ? row[1].toString() : "");
					listRow.add(oneRow2);

					parentStr = row[2] != null ? row[2].toString() : "";

					break;
				}
			}
			break;

		case IdexConstant.COUNTER_TYPE:

			IdexCounter idexCounter = idexCounterDAO.findParentById(nodeId);
			if (idexCounter.getIsSubModule() != null
					&& idexCounter.getIsSubModule().equals(IdexConstant.LAB010_COMPTEUR_IS_SUBMODULE)) {
				parentStr = idexCounter.getIdexCounter().getName();
			} else {
				if (IdexConstant.LAB010_COUPTER_PRIMAY.equals(idexCounter.getCounterType())) {
					parentStr = idexCounter.getIdexEnergySupplier().getName();
				} else if (IdexConstant.LAB010_COUPTER_SORTIE.equals(idexCounter.getCounterType())) {
					parentStr = idexCounter.getIdexBoiler().getName();
				}else{
					parentStr = idexCounter.getIdexSite().getName();
				}
			}
			break;
			
		case IdexConstant.BOILER_TYPE:
			String sql3 = "SELECT bo.idex_counter_id,co.name FROM idex_boiler bo "
					+ "LEFT JOIN idex_counter co ON co.idex_counter_id = bo.idex_counter_id "
					+ "WHERE bo.idex_boiler_id =:id AND bo.idex_installation_id=:iId";
			Query query3 = session.createSQLQuery(sql3);
			query3.setInteger("id", nodeId);
			query3.setInteger("iId", insallationId);
			@SuppressWarnings("unchecked")
			List<Object[]> rows3 = query3.list();
			if (rows3 != null && rows3.size() > 0) {
				for (Object[] arr : rows3) {
					parentStr = (String) arr[1];
					break;
				}
			}

			break;
		case IdexConstant.SITE_TYPE:
			String sql4 = "SELECT si.first_parent_id,co.name FROM idex_site si "
					+ "LEFT JOIN idex_counter co ON si.first_parent_id = co.idex_counter_id "
					+ "WHERE si.idex_site_id =:id AND si.idex_installation_id=:iId";
			Query query4 = session.createSQLQuery(sql4);
			query4.setInteger("id", nodeId);
			query4.setInteger("iId", insallationId);
			@SuppressWarnings("unchecked")
			List<Object[]> rows4 = query4.list();
			if (rows4 != null && rows4.size() > 0) {
				for (Object[] arr : rows4) {
					parentStr = (String) arr[1];
					break;
				}
			}

			break;
		case IdexConstant.ENERGY_SUPPLIER_TYPE:
			String sql5 = "SELECT su.idex_installation_id,e.name FROM idex_energy_supplier su "
					+ "LEFT JOIN idex_energy_type e ON su.idex_energy_type_id = e.idex_energy_type_id "
					+ "WHERE su.idex_energy_supplier_id =:id AND su.idex_installation_id=:iId";
			Query query5 = session.createSQLQuery(sql5);
			query5.setInteger("id", nodeId);
			query5.setInteger("iId", insallationId);
			@SuppressWarnings("unchecked")
			List<Object[]> arr = query5.list();
			if (arr != null && arr.size() > 0) {
				for (Object[] row : arr) {
					parentStr = (String) row[1];
					break;
				}
			}
			break;
		default:
			break;
		}

		result.setParentName(parentStr);

		String sql = "SELECT iK.idex_key_id, iK.`name`, "
				+ "iV.`value`, iV.`comment`,iV.`idex_value_id`,iK.key "
				+ "from idex_key iK INNER JOIN idex_value iV ON iK.idex_key_id = iV.idex_key_id "
				+ "WHERE iK.type =:value1 and iV.idex_id =:value2 ORDER BY iK.order_number";
		Query query = session.createSQLQuery(sql);
//		if (nodeId == null || nodeId <= 0) {
//			nodeId = 1;
//		}
		query.setInteger("value1", type);
		query.setInteger("value2", nodeId);

		@SuppressWarnings("unchecked")
		List<Object[]> rows = query.list();
		if (rows != null && rows.size() > 0) {
			for (Object[] row : rows) {
				InstallationOneExelRowData oneRow = new InstallationOneExelRowData();
				oneRow.setRowIndex(row[0] != null ? Integer.parseInt(row[0].toString().trim()) : 0);
				oneRow.setName(row[1] != null ? row[1].toString() : "");
				oneRow.setValue(row[2] != null ? row[2].toString() : "");
				oneRow.setComment(row[3] != null ? row[3].toString() : "");
				oneRow.setIdexRowValue((Integer) row[4]);
				oneRow.setKeyTypeId((Integer) row[5]);
				listRow.add(oneRow);
			}
			result.setData(listRow);
		}

		return result;
	}

	public int addNode(NodeInfo nodeInfo, Integer insallationId, Integer type, Integer parentId) {
		int result = -1;
		Session session = sessionFactory.getCurrentSession();
		String currentDate = LabUtils.convertDateByFormat(LabUtils.getCurrentDay(),FrontalKey.DATE_SLASH_FORMAT);
		switch (type) {
		case IdexConstant.INSTATLLATION_TYPE:
			IdexInstallation record = new IdexInstallation();
			if (nodeInfo.getNodeId() != null && nodeInfo.getNodeId() > 0) {
				record = findById(insallationId);
			}else if(insallationId > 0){
				record = findById(insallationId);
			}
			for (InstallationOneExelRowData rowData : nodeInfo.getData()) {
				if (rowData.getName() != null && rowData.getName().equalsIgnoreCase("name")) {
					record.setName(rowData.getValue());
				} else if (rowData.getName() != null && rowData.getName().equalsIgnoreCase("lng")) {
					record.setLng(
							rowData.getValue() != null ? Float.parseFloat(rowData.getValue().toString().trim()) : 0);
				} else if (rowData.getName() != null && rowData.getName().equalsIgnoreCase("lat")) {
					record.setLat(
							rowData.getValue() != null ? Float.parseFloat(rowData.getValue().toString().trim()) : 0);
				}
				if(rowData.getKeyTypeId() != null){
					if(rowData.getKeyTypeId().equals(IdexConstant.KEY_DATE_CREATE)){
						if(insallationId == null || insallationId == 0){
							rowData.setValue(currentDate);
						}
					}else if(rowData.getKeyTypeId().equals(IdexConstant.KEY_DATE_MODIFY)){
						rowData.setValue(currentDate);
					}else if(rowData.getKeyTypeId().equals(IdexConstant.KEY_USER_MODIFY)){
						rowData.setValue("idex");
					}
				}
			}

			session.saveOrUpdate(record);
			result = record.getIdexInstallationId();
			break;

		case IdexConstant.COUNTER_TYPE:
			IdexCounter record1 = new IdexCounter();
			if (nodeInfo.getNodeId() != null && nodeInfo.getNodeId() > 0) {
				record1 = idexCounterDAO.findById(nodeInfo.getNodeId());
			}
			for (InstallationOneExelRowData rowData : nodeInfo.getData()) {
				if(rowData.getName() != null && rowData.getName().equalsIgnoreCase("Coefficient de conversion")){
					Float value = null;
					try{
						value = Float.parseFloat(rowData.getValue());
					}catch(NumberFormatException nfe){
						
					}
					if(value != null){
						record1.setCoefficientDeConversion(value);
					}
				}else if (rowData.getName() != null && rowData.getName().equalsIgnoreCase("name")) {
					record1.setName(rowData.getValue());
				} else if (rowData.getName() != null && rowData.getName().equalsIgnoreCase("counter_type")) {
					record1.setCounterType(
							rowData.getValue() != null ? Integer.parseInt(rowData.getValue().toString().trim()) : 0);
				} else if (rowData.getName() != null && rowData.getName().equalsIgnoreCase("is_sub_module")) {
					record1.setIsSubModule(
							rowData.getValue() != null ? Integer.parseInt(rowData.getValue().toString().trim()) : 0);
				} else if (rowData.getName() != null && rowData.getName().equalsIgnoreCase("idex_energy_supplier_id")) {
					IdexEnergySupplier su = new IdexEnergySupplier();
					su.setIdexEnergySupplierId(
							rowData.getValue() != null ? Integer.parseInt(rowData.getValue().toString().trim()) : 0);
					record1.setIdexEnergySupplier(su);
				} else if (rowData.getName() != null && rowData.getName().equalsIgnoreCase("idex_boiler_id")) {
					IdexBoiler bo = new IdexBoiler();
					bo.setIdexBoilerId(
							rowData.getValue() != null ? Integer.parseInt(rowData.getValue().toString().trim()) : 0);
					record1.setIdexBoiler(bo);
				} else if (rowData.getName() != null && rowData.getName().equalsIgnoreCase("idex_site_id")) {
					IdexSite si = new IdexSite();
					si.setIdexSiteId(
							rowData.getValue() != null ? Integer.parseInt(rowData.getValue().toString().trim()) : 0);
					record1.setIdexSite(si);
				} else if (rowData.getName() != null && rowData.getName().equalsIgnoreCase("counter_parent_id")) {
					IdexCounter co = new IdexCounter();
					co.setIdexCounterId(parentId);
					record1.setIdexCounter(co);
				}

			}

			if (parentId > 0) {
				IdexCounter idexCounter = new IdexCounter();
				idexCounter.setIdexCounterId(parentId);
				record1.setIdexCounter(idexCounter);
			}

			IdexInstallation in = new IdexInstallation();
			in.setIdexInstallationId(insallationId);
			record1.setIdexInstallation(in);

			session.saveOrUpdate(record1);
			result = record1.getIdexCounterId();

			break;

		case IdexConstant.BOILER_TYPE:
			IdexBoiler record2 = new IdexBoiler();
			if (nodeInfo.getNodeId() != null && nodeInfo.getNodeId() > 0) {
				record2 = idexBoilerDAO.findById(nodeInfo.getNodeId());
			}
			for (InstallationOneExelRowData rowData : nodeInfo.getData()) {
				if (rowData.getName() != null && rowData.getName().equalsIgnoreCase("name")) {
					record2.setName(rowData.getValue());
				} else if (rowData.getName() != null && rowData.getName().equalsIgnoreCase("idex_counter_id")) {
					IdexCounter co = new IdexCounter();
					co.setIdexCounterId(
							(rowData.getValue() != null ? Integer.parseInt(rowData.getValue().toString().trim()) : 0));
					record2.setIdexCounter(co);
				} else if (rowData.getName() != null && rowData.getName().equalsIgnoreCase("logo")) {
					record2.setLogo(rowData.getValue() != null ? rowData.getValue().toString() : "");
				}

			}

			if (parentId > 0) {
				IdexCounter idexCounter = new IdexCounter();
				idexCounter.setIdexCounterId(parentId);
				record2.setIdexCounter(idexCounter);
			}

			IdexInstallation in2 = new IdexInstallation();
			in2.setIdexInstallationId(insallationId);
			record2.setIdexInstallation(in2);

			session.saveOrUpdate(record2);
			result = record2.getIdexBoilerId();
			break;
		case IdexConstant.SITE_TYPE:
			IdexSite record3 = new IdexSite();
			if (nodeInfo.getNodeId() != null && nodeInfo.getNodeId() > 0) {
				record3 = idexSiteDAO.findById(nodeInfo.getNodeId());
			}
			for (InstallationOneExelRowData rowData : nodeInfo.getData()) {
				if (rowData.getName() != null && rowData.getName().equalsIgnoreCase("name")) {
					record3.setName(rowData.getValue());
				} else if (rowData.getName() != null && rowData.getName().equalsIgnoreCase("first_parent_id")) {
					record3.setFirstParentId(
							rowData.getValue() != null ? Integer.parseInt(rowData.getValue().toString().trim()) : 0);
				} else if (rowData.getName() != null && rowData.getName().equalsIgnoreCase("logo")) {
					record3.setLogo(rowData.getValue() != null ? rowData.getValue().toString() : "");
				}
			}

			if (parentId > 0) {
				record3.setFirstParentId(parentId);
			}

			IdexInstallation in3 = new IdexInstallation();
			in3.setIdexInstallationId(insallationId);
			record3.setIdexInstallation(in3);

			session.saveOrUpdate(record3);
			result = record3.getIdexSiteId();
			break;
		case IdexConstant.ENERGY_SUPPLIER_TYPE:
			IdexEnergySupplier record4 = new IdexEnergySupplier();
			if (nodeInfo.getNodeId() != null && nodeInfo.getNodeId() > 0) {
				record4 = idexEnergySupplierDAO.findById(nodeInfo.getNodeId());
			}
			for (InstallationOneExelRowData rowData : nodeInfo.getData()) {
				if (rowData.getName() != null && rowData.getName().equalsIgnoreCase("name")) {
					record4.setName(rowData.getValue());
				} else if (rowData.getName() != null && rowData.getName().equalsIgnoreCase("idex_energy_supplier_id")) {
					record4.setIdexEnergySupplierId(
							rowData.getValue() != null ? Integer.parseInt(rowData.getValue().toString().trim()) : 0);
				} else if (rowData.getName() != null && rowData.getName().equalsIgnoreCase("order_by")) {
					record4.setOrderBy(
							rowData.getValue() != null ? Integer.parseInt(rowData.getValue().toString().trim()) : 0);
				} else if (rowData.getName() != null && rowData.getName().equalsIgnoreCase("idex_energy_type_id")) {
					IdexEnergyType energyType = new IdexEnergyType();
					energyType.setIdexEnergyTypeId(
							rowData.getValue() != null ? Integer.parseInt(rowData.getValue().toString().trim()) : 0);
					record4.setIdexEnergyType(energyType);
				}
			}

			IdexInstallation in4 = new IdexInstallation();
			in4.setIdexInstallationId(insallationId);
			record4.setIdexInstallation(in4);

			session.saveOrUpdate(record4);
			result = record4.getIdexEnergySupplierId();
			break;
		default:
			break;
		}

		// Insert into Idex_Key and Idex_Value
		int batchSize = 50;
		int noInserted = 0;
		for (InstallationOneExelRowData rowData : nodeInfo.getData()) {
			// IdexKey key = new IdexKey();
			int idex_key_id = rowData.getKeyId();

			if (idex_key_id > 0) {
				String idex_value_value = rowData.getValue() != null ? rowData.getValue().toString() : "";
				Integer idexRowValue = rowData.getIdexRowValue();
				IdexValue value = new IdexValue();
				value.setIdexKeyId(idex_key_id);
				value.setValue(idex_value_value);
				value.setIdexId(result);
				if (idexRowValue != null && idexRowValue > 0) {
					value.setIdexValueId(idexRowValue);
				}

				session.saveOrUpdate(value);
				noInserted++;
				if (noInserted % batchSize == 0 && noInserted > 0) {
					session.flush();
					session.clear();
				}
			}

		}

		return result;
	}

	public List<IdexInstallation> getAll() {
		Session session = sessionFactory.getCurrentSession();
		String hql = "FROM IdexInstallation";
		Query query = session.createQuery(hql);
		@SuppressWarnings("unchecked")
		List<IdexInstallation> list = query.list();
		return list;
	}

	public Integer deleteNode(Integer installationId, Integer nodeId, Integer type) {
		Session session = sessionFactory.getCurrentSession();

		if (installationId > 0 && nodeId > 0 && type > 0) {
			switch (type) {
			case IdexConstant.INSTATLLATION_TYPE:
				IdexInstallation in = new IdexInstallation();
				in.setIdexInstallationId(installationId);
				session.delete(in);
				nodeId = installationId;
				break;
			case IdexConstant.ENERGY_SUPPLIER_TYPE:
				IdexEnergySupplier su = new IdexEnergySupplier();
				su.setIdexEnergySupplierId(nodeId);
				session.delete(su);
				break;
			case IdexConstant.COUNTER_TYPE:
				IdexCounter co = new IdexCounter();
				co.setIdexCounterId(nodeId);
				session.delete(co);
				break;
			case IdexConstant.BOILER_TYPE:
				IdexBoiler bo = new IdexBoiler();
				bo.setIdexBoilerId(nodeId);
				session.delete(bo);
				break;
			case IdexConstant.SITE_TYPE:
				IdexSite si = new IdexSite();
				si.setIdexSiteId(nodeId);
				session.delete(si);
				break;
			default:
				break;
			}
		}
		return nodeId;
	}

	public boolean delete(Integer installationId) {
		Session session = sessionFactory.getCurrentSession();
		IdexInstallation idex = findById(installationId);
		if (idex == null) {
			return false;
		}
		session.delete(idex);
		return true;
	}
	
	public boolean save(IdexInstallation idexInstallation) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(idexInstallation);
		return true;
	}
}
