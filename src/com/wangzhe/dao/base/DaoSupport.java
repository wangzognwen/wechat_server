package com.wangzhe.dao.base;

import java.util.List;
import java.util.Map;

import com.wangzhe.util.Paging;


public interface DaoSupport<T> {
	public T getObjById(int id); 
	public List<T> getAll();
	public T getTByParams(T t);
	public List<T> getAllByParams(T t);
	public T getTByParams(Map<String, Object> parms);
	public List<T> getAllByParams(Map<String, Object> parmas);
	public void updateObj(T t);
	public boolean addObj(T t);
	public void deleteObj(int id);	
	public List<T> getObjByPaging(Paging<T> paging);
	public Class<?> getSuperClassGenricType();
}
