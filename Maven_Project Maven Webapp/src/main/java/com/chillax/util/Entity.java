package com.chillax.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public abstract class Entity implements Serializable{

	private static final long serialVersionUID = 1L;

	public void setProperties(Map<String, Object> properties) {
		Field[] fields = this.getClass().getDeclaredFields();
		for(Field field:fields) {
			field.setAccessible(true);
			String fieldName = field.getName();
			try {
				if(this.getClass().getDeclaredMethod("get"+fieldName.toUpperCase().charAt(0)+fieldName.substring(1))==null) {
					continue;
				}
			} catch (NoSuchMethodException e1) {
				continue;
			}
			Object value = properties.get(fieldName.toUpperCase());
			if(!isBaseDataType(field.getType())&&value!=null) {
				try {
					ByteArrayInputStream bi = new ByteArrayInputStream(
							(byte[]) value);
					ObjectInputStream oi = new ObjectInputStream(bi);
					value = oi.readObject();
					bi.close();
					oi.close();
				} catch (Exception e) {
					System.out.println("translation" + e.getMessage());
					e.printStackTrace();
				}
			}
			try {
				if(value!=null) {
					field.set(this, value);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public Set<Entry<String, Object>> getProperties() {
		return toMap().entrySet();
	}

	public abstract String getTableName();

	public abstract String getPrimaryKey();

	public static <T extends Entity> String getTableName(Class<T> t) {
		{
			try {
				return t.newInstance().getTableName();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static <T extends Entity> String getPrimaryKey(Class<T> t) {
		{
			try {
				return t.newInstance().getPrimaryKey();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static boolean isBaseDataType(@SuppressWarnings("rawtypes") Class clazz) {
		if (clazz.isPrimitive()||(clazz.getGenericSuperclass()!=null&&clazz.getGenericSuperclass().equals(Number.class)) || clazz.equals(String.class)
				|| clazz.equals(Date.class)) {
			return true;
		}
		return false;
	}
	
	public static String getSQlType(Class<?> clazz) {
		String type = clazz.getSimpleName();
		if("int".equals(type)) {
			return "int";
		}
		
		if("float".equals(type)) {
			return "float";
		}
		
		if("double".equals(type)) {
			return "double";
		}
		
		if("String".equals(type)) {
			return "varchar(50)";
		}
		
		if("Date".equals(type)) {
			return "date";
		}
		return "blob(0)";
	}
	
	public Map<String,Object> toMap() {
		Map<String,Object> temp = new HashMap<String,Object>();
		Field[] fields = this.getClass().getDeclaredFields();
		for(Field field:fields) {
			field.setAccessible(true);
			String fieldName = field.getName();
			
			try {
				if(this.getClass().getDeclaredMethod("get"+fieldName.toUpperCase().charAt(0)+fieldName.substring(1))==null) {
					continue;
				}
			} catch (NoSuchMethodException e1) {
				continue;
			}
			Object value = null;
			try {
				value = field.get(this);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if(!isBaseDataType(field.getType())&&value!=null) {
				byte[] bytes = null;
				try {
					ByteArrayOutputStream bo = new ByteArrayOutputStream();
					ObjectOutputStream oo = new ObjectOutputStream(bo);
					oo.writeObject(value);
					bytes = bo.toByteArray();
					bo.close();
					oo.close();
				} catch (Exception e) {
					System.out.println("translation" + e.getMessage());
					e.printStackTrace();
				}
				value = bytes;
			}
			if(value!=null) {
				temp.put(fieldName, value);
			}
		}
		return temp;
	}
	private void writeObject(ObjectOutputStream os) throws IOException {     
	      os.defaultWriteObject();//java对象序列化默认操�?    
	      os.writeObject(toMap());
	}
	    
	  @SuppressWarnings("unchecked")
	private void readObject(ObjectInputStream is) throws IOException, ClassNotFoundException {     
	      is.defaultReadObject();//java对象反序列化默认操作     
	     Map<String,Object> temp = (Map<String,Object>)is.readObject();
	     setProperties(temp);
	  }   
}
