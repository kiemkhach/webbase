package com.ifi.lab.LabDAO.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "submodule_indus")
public class SubModuleIndus {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "key_module")
	private String keyModule;
	@Column(name = "tail_module")
	private String tailModule;
	@Column(name = "module")
	private String module;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getKeyModule() {
		return keyModule;
	}

	public void setKeyModule(String keyModule) {
		this.keyModule = keyModule;
	}

	public String getTailModule() {
		return tailModule;
	}

	public void setTailModule(String tailModule) {
		this.tailModule = tailModule;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

}
