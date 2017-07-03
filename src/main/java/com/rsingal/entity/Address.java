package com.rsingal.entity;

public class Address {

	private Integer addressId;
	private String address;

	public Address() {
	}

	public Address(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Address [addressId = " + addressId + ", address = " + address + "]";
	}

	public Integer getAddressId() {
		return addressId;
	}

	public void setAddressId(Integer id) {
		this.addressId = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
