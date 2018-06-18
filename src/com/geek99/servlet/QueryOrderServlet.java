package com.geek99.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.geek99.dao.OrderDao;
import com.geek99.dao.OrderDaoImpl;
import com.geek99.dao.OrderDetail2;
import com.geek99.dao.QueryOrder;

/**
 * implementation class QueryOrderSerlvet
 */
public class QueryOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QueryOrderServlet() {
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
		// TODO Auto-generated method stub
		OrderDao dao = new OrderDaoImpl();
		String stid = request.getParameter("tid");
		int tid = Integer.parseInt(stid);
		List<OrderDetail2> list = dao.queryOrderDetail(tid);
		PrintWriter out = response.getWriter();
		QueryOrder qo = dao.queryOrder(tid);
		out.print("<html>");
		out.print("<body>");

		out.print("<b>");
		out.print(qo.getCtime());
		out.print("</b>&nbsp;&nbsp;&nbsp;&nbsp;");

		out.print("<b>");
		out.print(qo.getUsername());
		out.print("</b>&nbsp;&nbsp;&nbsp;&nbsp;");

		out.print("<b>");
		out.print(qo.getTid());
		out.print("</b>&nbsp;&nbsp;&nbsp;&nbsp;");

		out.print("<b>");
		out.print(qo.getPersonNum());
		out.print("</b>");

		// for each
		out.print("<table>");
		out.print("<th>");

		out.print("<td>");
		out.print("Name");
		out.print("</td>");

		out.print("<td>");
		out.print("Price");
		out.print("</td>");

		out.print("<td>");
		out.print("Num");
		out.print("</td>");

		out.print("<td>");
		out.print("Total");
		out.print("</td>");

		out.print("<td>");
		out.print("Description");
		out.print("</td>");

		out.print("</th>");

		for (OrderDetail2 od : list) {
			out.print("<tr>");
			out.print("<td>");
			out.print(od.getName());
			out.print("</td>");

			out.print("<td>");
			out.print(od.getPrice());
			out.print("</td>");

			out.print("<td>");
			out.print(od.getNum());
			out.print("</td>");

			out.print("<td>");
			out.print(od.getTotal());
			out.print("</td>");

			out.print("<td>");
			String s = (od.getDescription() == null || od.getDescription()
					.equals("")) ? "" : od.getDescription();
			out.print(s);
			out.print("</td>");
			out.print("</tr>");
		}
		out.print("<table>");

		out.print("</body>");
		out.print("</html>"); 
		
	}

}
