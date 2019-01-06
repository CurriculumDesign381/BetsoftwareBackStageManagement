package com.chillax.service;
 
import java.util.List;
 

import java.util.Map;

import com.chillax.dto.users;
import com.chillax.dto.User;
 
public interface IUserService {
	
	public User getUserById(int userId);
 
	public void insertUser(User user);
 
	public void addUser(User user);
 
	public List<Map<String,Object>> getAllUser();
	public List<Map<String,Object>> getAllBetUser(String softwareAccount);
	public int deleteUser(String account);
	public List<Map<String, Object>> searchdata(String name);

    public int update(String username,String cellphone,String account,String oldAccount);
    
 		
		
	
	
	
	
}
