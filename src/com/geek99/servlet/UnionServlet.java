package com.geek99.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.geek99.dao.TableDao;
import com.geek99.dao.TableDaoImpl;

/**
 * Servlet implementation class UnionServlet
 */
public class UnionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UnionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String stid1 = request.getParameter("tid1");
		String stid2 = request.getParameter("tid2");
		int tid1 = Integer.parseInt(stid1);
		int tid2 = Integer.parseInt(stid2);
		TableDao dao = new TableDaoImpl();
		int r = dao.union(tid1, tid2);
		PrintWriter out = response.getWriter();
		if(r==0){
			out.print("failure");
		}else{
			out.print("Success");
		}
	}

}
