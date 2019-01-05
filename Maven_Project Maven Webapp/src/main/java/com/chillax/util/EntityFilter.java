package com.chillax.util;




public class EntityFilter implements ClassFilter{

	@SuppressWarnings("rawtypes")
	public boolean accept(Class clazz) {
		return clazz.getSuperclass().equals(Entity.class);
	}

}
