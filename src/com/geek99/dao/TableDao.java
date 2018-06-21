package com.geek99.dao;

import java.util.List;

public interface TableDao {
	public List<Table> getAllTables();
	public List<Table> getEmptyTables();
	public List<Table> getEatingTables();
	public int union(int tid1,int tid2);
	public int change(int tid1,int tid2);
}
