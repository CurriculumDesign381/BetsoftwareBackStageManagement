package com.chillax.service;
 
import java.util.List;
 

import java.util.Map;

import com.chillax.dto.BetUser;
import com.chillax.dto.User;
 
public interface IUserService {
	
	public User getUserById(int userId);
 
	public void insertUser(User user);
 
	public void addUser(User user);
 
	public List<User> getAllUser();
	public List<Map<String,Object>> getAllBetUser();
	public int deleteUser(String account);
	public List<Map<String, Object>> searchdata(String name);
    public  int delete();
		
		
	
	
	
	
}
