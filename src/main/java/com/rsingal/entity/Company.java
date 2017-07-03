package com.rsingal.entity;

public class Company {

	private Integer companyId;
	private String companyName;

	public Company() {
	}

	public Company(String companyName) {
		this.companyName = companyName;
	}

	@Override
	public String toString() {
		return "Company [companyId = " + companyId + ", companyName = " + companyName + "]";
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
}
