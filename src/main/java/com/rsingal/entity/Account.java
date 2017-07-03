package com.rsingal.entity;

public class Account {

	private Integer accountId;
	private String bankName;
	private Integer accountNumber;
	private Employee employee;

	public Account() {
	}

	public Account(String bankName, Integer accountNumber) {
		this.bankName = bankName;
		this.accountNumber = accountNumber;
	}

	@Override
	public String toString() {
		return "Account [accountId = " + accountId + ", bankName = " + bankName + ", accountNumber = " + accountNumber + ", employeeId = " + employee.getEmployeeId() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accountNumber == null) ? 0 : accountNumber.hashCode());
		result = prime * result + ((bankName == null) ? 0 : bankName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (accountNumber == null) {
			if (other.accountNumber != null)
				return false;
		} else if (!accountNumber.equals(other.accountNumber))
			return false;
		if (bankName == null) {
			if (other.bankName != null)
				return false;
		} else if (!bankName.equals(other.bankName))
			return false;
		return true;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer id) {
		this.accountId = id;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public Integer getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(Integer accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
}
