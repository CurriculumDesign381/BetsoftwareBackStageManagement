package com.chillax.dto;

import com.chillax.util.Entity;



public class users extends Entity{
	String account ;
	String password;
	String cellphone;
	String username;
	public String getCellphone() {
		return cellphone;
	}
	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCellphoneString() {
		return cellphone;
	}
	public void setCellphoneString(String cellphone) {
		this.cellphone = cellphone;
	}
	
	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return "users";
	}

	@Override
	public String getPrimaryKey() {
		// TODO Auto-generated method stub
		return "account";
	}

}
