package com.geek99.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class OrderDaoImpl implements OrderDao{

	@Override
	public int addOrder(Order o) {
//		CREATE TABLE `ordertbl` (
//				  `id` int(11) NOT NULL AUTO_INCREMENT,
//				  `ctime` varchar(20) DEFAULT NULL,
//				  `uid` int(11) DEFAULT NULL,
//				  `tid` int(11) DEFAULT NULL,
//				  `description` varchar(20) DEFAULT NULL,
//				  `personNum` int(11) DEFAULT NULL,
//				  `isPay` int(11) DEFAULT '0' COMMENT '0:Î´½áËã 1: ½áËã',
//				  PRIMARY KEY (`id`)
//				)DEFAULT CHARSET=gbk;
		String sql = "insert into OrderTbl(ctime,uid,tid,description,personNum,isPay) values(?,?,?,?,?,?) ";
		Connection conn = ConnectionUtil.open();
		
		try {
			conn.setAutoCommit(false);
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, o.getCtime());
			pstmt.setInt(2, o.getUid());
			pstmt.setInt(3,o.getTid());
			pstmt.setString(4, o.getDesc());
			pstmt.setInt(5, o.getPersonNum());
			pstmt.setInt(6, 0);
			pstmt.executeUpdate();
			
			String sql2 = "select max(id) from OrderTbl";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql2);
			if(rs.next()){
				int oid = rs.getInt(1);
				List<MenuTemp> list = o.getList();
				String sql3 = "insert into OrderDetailTbl(oid,mid,num,description)values(?,?,?,?) ";
				PreparedStatement pstmt2 = conn.prepareStatement(sql3);
				for(MenuTemp mt:list){
//					 `oid` int(11) DEFAULT NULL,
//					  `mid` int(11) DEFAULT NULL,
//					  `num` int(11) DEFAULT NULL,
//					  `description` varchar(20) DEFAULT NULL,
					pstmt2.setInt(1, oid);
					pstmt2.setInt(2, mt.getMid());
					pstmt2.setInt(3, mt.getNum());
					pstmt2.setString(4, mt.getDesc());
					pstmt2.executeUpdate();
				}
			}
			
			String sql4 = "update TableTbl set flag = 1 where id = ?";
			PreparedStatement pstmt3 = conn.prepareStatement(sql4);
			pstmt3.setInt(1, o.getTid());
			pstmt3.executeUpdate();
			
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return 0;
		}finally{
			ConnectionUtil.close(conn);
		}
		
		return 1;
	}
	
	@Override
	public QueryOrder queryOrder(int tid) {
		String sql = " select ot.`ctime`," + " ut.`username`," + " ot.`tid`,"
				+ " ot.`personNum`" + " from orderTbl as ot"
				+ " left join userTbl as ut on ot.`uid` = ut.id"
				+ " where ot.`id` ="
				+ " (select id from OrderTbl where tid = ? and isPay = 0) ";
		Connection conn = ConnectionUtil.open();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, tid);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				String ctime = rs.getString(1);
				String username = rs.getString(2);
				// int tid = rs.getInt(3);
				int personNum = rs.getInt(4);

				QueryOrder qo = new QueryOrder();
				qo.setCtime(ctime);
				qo.setUsername(username);
				qo.setTid(tid);
				qo.setPersonNum(personNum);
				return qo;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionUtil.close(conn);
		}
		return null;
	}
	
	
	@Override
	public List<OrderDetail2> queryOrderDetail(int tid) {
		String sql = "   select mt.`name`, "
				+ "    mt.`price`, "
				+ "    odt.`num`, "
				+ "    mt.price * odt.num as total, "
				+ "    odt.`description` "
				+ "    from orderdetailTbl as odt "
				+ "    left join menuTbl as mt on odt.`mid` = mt.id "
				+ "    where odt.`oid` = (select id from orderTbl where tid = ? and ispay=0) "
				+ "    union "
				+ "    select '', "
				+ "    '', "
				+ "    '', "
				+ "    sum(mt.price * odt.num) as total1, "
				+ "    '' "
				+ "    from orderdetailTbl as odt "
				+ "    left join menuTbl as mt on odt.`mid` = mt.id "
				+ "     where odt.`oid` = (select id from orderTbl where tid = ? and ispay=0)";

		Connection conn = ConnectionUtil.open();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, tid);
			pstmt.setInt(2, tid);
			ResultSet rs = pstmt.executeQuery();
			List<OrderDetail2> list = new ArrayList<OrderDetail2>();
			while (rs.next()) {
				String name = rs.getString(1);
				int price = rs.getInt(2);
				int num = rs.getInt(3);
				int total = rs.getInt(4);
				String description = rs.getString(5);

				OrderDetail2 od = new OrderDetail2();
				
				od.setName(name);
				od.setDescription(description);
				od.setPrice(price);
				od.setNum(num);
				od.setTotal(total);

				list.add(od);
			}

			return list;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionUtil.close(conn);
		}
		return null;
	}

	@Override
	public int pay(int tid) {
		String sql = "update OrderTbl set isPay = 1 where tid = ? ";
		Connection conn = ConnectionUtil.open();
		
		try {
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, tid);
			
			pstmt.executeUpdate();
			
			String sql4 = "update TableTbl set flag = 0 where id = ?";
			PreparedStatement pstmt3 = conn.prepareStatement(sql4);
			pstmt3.setInt(1, tid);
			pstmt3.executeUpdate();
			
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return 0;
		}finally{
			ConnectionUtil.close(conn);
		}
		
		return 1;
	}


}