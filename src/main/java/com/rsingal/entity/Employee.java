package com.rsingal.entity;

import java.util.HashSet;
import java.util.Set;

public class Employee {

	private Integer employeeId;
	private String employeeName;
	private Address address;
	private Company company;
	private Set<Account> accounts = new HashSet<Account>();
	private Set<Training> trainings = new HashSet<Training>();
	private Set<EmpTrngMappingExtended> empTrngMappingExts = new HashSet<EmpTrngMappingExtended>();

	public Employee() {
	}

	public Employee(String name) {
		this.employeeName = name;
	}

	@Override
	public String toString() {
		return "\n  Employee [employeeId = " + employeeId + ", employeeName = " + employeeName + //
				"\n    address = " + address + //
				"\n    company = " + company + //
				"\n    accounts = " + accounts + //
				"\n    trainings = " + trainings + //
				"\n    empTrngMappingExts = " + empTrngMappingExts + //
				"]";
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer id) {
		this.employeeId = id;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String name) {
		this.employeeName = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Set<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
	}

	public Set<Training> getTrainings() {
		return trainings;
	}

	public void setTrainings(Set<Training> trainings) {
		this.trainings = trainings;
	}

	public Set<EmpTrngMappingExtended> getEmpTrngMappingExts() {
		return empTrngMappingExts;
	}

	public void setEmpTrngMappingExts(Set<EmpTrngMappingExtended> empTrngMappingExts) {
		this.empTrngMappingExts = empTrngMappingExts;
	}
}
