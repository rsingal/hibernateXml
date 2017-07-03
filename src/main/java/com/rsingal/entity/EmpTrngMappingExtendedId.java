package com.rsingal.entity;

import java.io.Serializable;

public class EmpTrngMappingExtendedId implements Serializable {

	private static final long serialVersionUID = -495126222416294773L;
	private Employee employee;
	private Training training;

	public EmpTrngMappingExtendedId() {
	}

	public EmpTrngMappingExtendedId(Employee employee, Training training) {
		this.employee = employee;
		this.training = training;
	}

	@Override
	public String toString() {
		return "EmpTrngMappingExtendedId [employeeId =" + employee.getEmployeeId() + ", trainingId = " + training.getTrainingId() + ", trainingName = " + training.getTrainingName() + "]";
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Training getTraining() {
		return training;
	}

	public void setTraining(Training training) {
		this.training = training;
	}
}
