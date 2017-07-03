package com.rsingal.entity;

public class EmpTrngMappingExtended {

	private EmpTrngMappingExtendedId empTrngMappingExtId;
	Integer status;

	public EmpTrngMappingExtended() {
	}

	public EmpTrngMappingExtended(EmpTrngMappingExtendedId empTrngMappingExtId, Integer status) {
		this.empTrngMappingExtId = empTrngMappingExtId;
		this.status = status;
	}

	@Override
	public String toString() {
		return "EmpTrngMappingExtended [empTrngMappingExtendedId = " + empTrngMappingExtId + ", status = " + (status == 1 ? "Training Done" : "Training Pending") + "]";
	}

	public EmpTrngMappingExtendedId getEmpTrngMappingExtId() {
		return empTrngMappingExtId;
	}

	public void setEmpTrngMappingExtId(EmpTrngMappingExtendedId empTrngMappingExtId) {
		this.empTrngMappingExtId = empTrngMappingExtId;
	}

	public Employee getEmployee() {
		return empTrngMappingExtId.getEmployee();
	}

	public void setEmployee(Employee employee) {
		empTrngMappingExtId.setEmployee(employee);
	}

	public Training getTraining() {
		return empTrngMappingExtId.getTraining();
	}

	public void setTraining(Training training) {
		empTrngMappingExtId.setTraining(training);
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
