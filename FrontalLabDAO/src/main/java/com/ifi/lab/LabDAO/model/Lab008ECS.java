package com.ifi.lab.LabDAO.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "lab008_ecs")
public class Lab008ECS {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "config_lab008_v2_id")
	private Integer configLab008V2ID;
	@Column(name = "name")
	private String name;
//	@Column(name = "temperature_module")
//	private String temperatureModule;
//	@Column(name = "comsommation_module")
//	private String comsommationModule;
	@Column(name = "moduleCSM")
	private String moduleCSM;
	// 1: Depart ECS(KW); 2:Temperature in house(T); 3: out mix bottle(T); 4: out ProductECS(T); 5: in ProductECS(T)
	@Column(name = "type")
	private Integer type;

//	public Lab008ECS(String name, String comsommationModule) {
//		super();
//		this.name = name;
//		this.comsommationModule = comsommationModule;
//	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getConfigLab008V2ID() {
		return configLab008V2ID;
	}

	public void setConfigLab008V2ID(Integer configLab008V2ID) {
		this.configLab008V2ID = configLab008V2ID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getModuleCSM() {
		return moduleCSM;
	}

	public void setModuleCSM(String moduleCSM) {
		this.moduleCSM = moduleCSM;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}
