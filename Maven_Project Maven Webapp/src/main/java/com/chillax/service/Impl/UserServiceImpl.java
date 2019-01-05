	package com.chillax.service.Impl;
 
import java.util.List;
 





import java.util.Map;

import javax.annotation.Resource;
 









import org.springframework.stereotype.Service;
 









import com.chillax.dao.BaseEntityDao;
import com.chillax.dao.EntityDao;
import com.chillax.dao.IUserDao;
import com.chillax.dao.SearchDao;
import com.chillax.dto.BetUser;
import com.chillax.dto.User;
import com.chillax.service.IUserService;

 
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
	public List<User> getAllUser() {
		return userDao.getAllUser();
	}
	
	@Override
	public int deleteUser(String account){
		String condition = "users.account = '"+account+"'";
		String baseEntity = "betsoftware.users";
		baseEntityDao.deleteByCondition(condition, baseEntity);
		if(	baseEntityDao.deleteByCondition(condition, baseEntity)==1)
			return 1;
		else {
			return 0;
		}		
	}
	@Override
	public List<Map<String,Object>> getAllBetUser(){
		String[] properties = { "us.account","us.username", "us.cellphone","bet.domain","bet.account as betaccount"};
		String baseEntity = "betsoftware.users us";	
		String joinEntity = " join betsoftware.betaccount bet on us.account = bet.supid";
		
		return  searchDao.searchForeign(properties, baseEntity, joinEntity, null, null, null);	
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
  	public int delete(){
  		String condition = "";
  		String table = "";
  		int result = baseEntityDao.deleteByCondition(condition, table);
  		return 0;
  	}
	
	

}
