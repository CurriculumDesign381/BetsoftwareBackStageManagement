	package com.chillax.service.Impl;
 
import java.util.List;
 





import java.util.Map;

import javax.annotation.Resource;
 











import org.springframework.stereotype.Service;
 











import com.chillax.dao.BaseEntityDao;
import com.chillax.dao.EntityDao;
import com.chillax.dao.IUserDao;
import com.chillax.dao.SearchDao;
import com.chillax.dto.users;
import com.chillax.dto.User;
import com.chillax.service.IUserService;
import com.chillax.util.Entity;

 
@Service("userService")
public class UserServiceImpl implements IUserService {
	@Resource
	private IUserDao userDao;
	@Resource(name = "searchDao")
	SearchDao searchDao;
	@Resource(name = "baseEntityDao")
	BaseEntityDao baseEntityDao;
	
	public User getUserById(int userId) {
		return userDao.queryByPrimaryKey(userId);
	}
 
	public void insertUser(User user) {
		userDao.insertUser(user);
	}
 
	public void addUser(User user) {
		userDao.insertUser(user);
	}
 
	@Override
	public List<Map<String, Object>> getAllUser() {
		String[] properties = { "us.account","us.username", "us.cellphone"};
		String baseEntity = "betsoftware.users us";
		String condition = null;
		List<Map<String, Object>> list = baseEntityDao.findByCondition(properties, condition, baseEntity);
		return list;
		
		
	}
	
	@Override
	public int deleteUser(String account){
		String condition = "users.account = '"+account+"'";
		String baseEntity = "betsoftware.users";
		baseEntityDao.deleteByCondition(condition, baseEntity);
		if(	baseEntityDao.deleteByCondition(condition, baseEntity)==1)
			return 1;
		else {
			return 1;
		}		
	}
	@Override
	public List<Map<String,Object>> getAllBetUser(String account){
		String[] properties = { "us.account","us.username", "us.cellphone","bet.domain","bet.account as betaccount"};
		String baseEntity = "betsoftware.users us";	
		String joinEntity = " join betsoftware.betaccount bet on us.account = bet.supid";
		String conditionString = "us.account = '"+account+"'";
		
		return  searchDao.searchForeign(properties, baseEntity, joinEntity, null, null, conditionString);
	}
	
	@Override
	public List<Map<String, Object>> searchdata(String name){
		String[] properties = { "us.account","us.username", "us.cellphone","bet.domain","bet.account as betaccount"};
		String baseEntity = "betsoftware.users us";	
		String joinEntity = " join betsoftware.betaccount bet on us.account = bet.supid";
		String condition ="us.username = '"+name+"'";
		List<Map<String, Object>> list = searchDao.searchForeign(properties, baseEntity, joinEntity, null, null, condition);
		return list;
	}
 

	@Override
	public int update(String username, String cellphone, String account,String oldAccount) {
		// TODO Auto-generated method stub
		String[] properties = { "us.account","us.username", "us.cellphone","bet.domain","bet.account as betaccount"};
		String baseEntity = "betsoftware.users us";	
		String condition = "account ='"+oldAccount+"'";
		Entity users = null ;
		
		baseEntityDao.updatePropByID(users, condition);
		return 0;
	}
  	
  	
	
	

}
