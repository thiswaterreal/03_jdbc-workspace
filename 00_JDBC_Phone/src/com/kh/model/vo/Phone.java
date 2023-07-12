package com.kh.model.vo;

public class Phone {

	// 필드부
	private int userNono;
	private String userName;
	private int age;
	private String address;
	private String phoneNum;
	
	// 생성자부
	public Phone() {};
	// --(5 전체)
	public Phone(int userNono, String userName, int age, String address, String phoneNum) {
		super();
		this.userNono = userNono;
		this.userName = userName;
		this.age = age;
		this.address = address;
		this.phoneNum = phoneNum;
	}
	// --(4)
	public Phone(String userName, int age, String address, String phoneNum) {
		super();
		this.userName = userName;
		this.age = age;
		this.address = address;
		this.phoneNum = phoneNum;
	}
	
	

	
	// 메소드부
	public int getUserNono() {
		return userNono;
	}

	public void setUserNono(int userNono) {
		this.userNono = userNono;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	@Override
	public String toString() {
		return "Phone [userNono=" + userNono + ", userName=" + userName + ", age=" + age + ", address=" + address
				+ ", phoneNum=" + phoneNum + "]";
	};
		
		
	

		
		
		
	
	
}
