package com.chillax.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.chillax.dao.EntityDao;



public class TableCreator {
	private EntityDao dao;
	private boolean reBuild;
	private String scanPackage;
	private static Map<String,String> entitys;

	public void setDao(EntityDao dao) {
		this.dao = dao;
	}

	public void setReBuild(boolean reBuild) {
		this.reBuild = reBuild;
	}

	public void setScanPackage(String scanPackage) {
		this.scanPackage = scanPackage;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void init() {
		ClassFilter filter = new EntityFilter();
		List<Class> classes = ClassUtils.scanPackage(scanPackage,
				filter);
		entitys = new HashMap<String, String>();
		for(Class clazz:classes) {
			entitys.put(Entity.getTableName(clazz),Entity.getPrimaryKey(clazz));
		}
		
		if(!reBuild) {
			return;
		}
		
		for (Class temp : classes) {
			String tableName = Entity.getTableName(temp);
			String primaryKey = Entity.getPrimaryKey(temp);

			String sql = "CREATE TABLE " + tableName + " ("
					+ primaryKey + " varchar(20) primary key";
			Method[] methods = temp.getDeclaredMethods();
			Set<String> filedSqls = new HashSet<String>();
			
			filedSqls.add(primaryKey + " varchar(20) primary key");
			
			for (Method method : methods) {
				String methodName = method.getName();
				Class returnYype = method.getReturnType();

				String sqlType = Entity.getSQlType(returnYype);
				if (methodName.startsWith("get")
						&& !methodName.equals("getTableName")
						&& !methodName.equals("getPrimaryKey")) {
					// 首字母小�?
					String filedName = methodName.substring(3, 4).toLowerCase()
							+ methodName.substring(4);
					if(!primaryKey.equals(filedName)) {
						sql += "," + filedName + " " + sqlType;
						filedSqls.add(filedName + " " + sqlType);
					}
					
				}
				
			}
			sql += ")";
			System.out.println(sql);
			dao.createtable(tableName, filedSqls);
		}
	}
	
	public static String getPrimaryKey(String table) {
		return entitys.get(table);
	}
}
