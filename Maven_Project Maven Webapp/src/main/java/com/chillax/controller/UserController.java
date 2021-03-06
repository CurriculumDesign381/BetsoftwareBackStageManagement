package com.chillax.controller;
 
import java.util.List;
 

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
 
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
 
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.chillax.dto.User;
import com.chillax.service.IUserService;
 
@Controller
@RequestMapping("/user")
public class UserController {
	@Resource
	private IUserService userService;
	
//	@RequestMapping("/userList")
//	public String userList(HttpServletRequest request,Model model){
//		List<User> uList = userService.getAllUser();
//		model.addAttribute("uList", uList);
//	
//		return "userList";
//	}
	
	@RequestMapping("/showUser")
	public String showUser(HttpServletRequest request,Model model){
		int userId = Integer.parseInt(request.getParameter("id"));
		User user = userService.getUserById(userId);
		model.addAttribute("user", user);
		return "showUser";
	}
	
	/**
	 * 获取后台管理数据
	 * @param directoryNo
	 * @return
	 */
	@RequestMapping(value="/getAllBetUser", method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getAllbetUser(String account){
		return JSONArray.toJSONString(userService.getAllBetUser( account));
	}
	
	/**
	 * 获取后台软件用户
	 * @param account
	 * @return
	 */
	@RequestMapping(value="/getAllUser", method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public String getAllUser(String account){
		return JSONArray.toJSONString(userService.getAllUser());
	}
	/**
	 * 修改后台软件用户
	 * @param account
	 * @return
	 */
	@RequestMapping(value="/updateUser", method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public String updateUser(String username,String cellphone,String account,String oldAccount){
		return JSONArray.toJSONString(userService.update(username, cellphone, account, oldAccount));
	}
	
	/**
	 * 增加后台软件用户
	 * @param account
	 * @return
	 */
	@RequestMapping(value="/addAccount", method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public String addAccount(String username,String password, String cellphone,String account){
		return JSONArray.toJSONString(userService.addAccount(username, password, cellphone, account));
	}	
	/**
	 *删除用户
	 * @param directoryNo
	 * @return
	 */
	@RequestMapping(value="/deleteUser", method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public int deleteUser(String account){
		return userService.deleteUser(account);
	}
	
	@RequestMapping("/addUserUI")
	public String addUserUI(){
		return "addUser";
	}
	
	@RequestMapping(value="/searchDataByname", method=RequestMethod.GET,produces="application/json;charset=UTF-8")
	@ResponseBody
	public List<Map<String, Object>> searchDataByname(String name){
		return userService.searchdata(name);
	}
	
	
	@RequestMapping("/addUser")
	public String addUser(HttpServletRequest request,Model model){
		User user = new User();
		user.setName(String.valueOf(request.getParameter("name")));
		user.setPassword(String.valueOf(request.getParameter("password")));
		user.setAge(Integer.parseInt(String.valueOf(request.getParameter("age"))));
		userService.addUser(user);
		return "redirect:/user/userList";
	}
}
