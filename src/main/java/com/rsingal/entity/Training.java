package com.rsingal.entity;

public class Training {

	private Integer trainingId;
	private String trainingName;

	public Training() {
	}

	public Training(String trainingName) {
		this.trainingName = trainingName;
	}

	@Override
	public String toString() {
		return "Training [trainingId = " + trainingId + ", trainingName = " + trainingName + "]";
	}

	public Integer getTrainingId() {
		return trainingId;
	}

	public void setTrainingId(Integer trainingId) {
		this.trainingId = trainingId;
	}

	public String getTrainingName() {
		return trainingName;
	}

	public void setTrainingName(String trainingName) {
		this.trainingName = trainingName;
	}
}
