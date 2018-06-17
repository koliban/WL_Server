package com.geek99.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class MenuDaoImpl implements MenuDao{

	@Override
	public List<Menu> listAllMenu() {

		String sql = "select id,tid,price,name,description from MenuTbl ";
		Connection conn = ConnectionUtil.open();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			List<Menu> list = new ArrayList<Menu>();
			while(rs.next()){
				int id = rs.getInt(1);
				int tid = rs.getInt(2);
				int price = rs.getInt(3);
				String name = rs.getString(4);
				String desc = rs.getString(5);
				
				Menu m = new Menu();
				
				m.setId(id);
				m.setTid(tid);
				m.setName(name);
				m.setPrice(price);
				m.setDesc(desc);
				
				list.add(m);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			ConnectionUtil.close(conn);
		}
		return null;
	}

	
}

