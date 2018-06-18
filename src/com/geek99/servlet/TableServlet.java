package com.geek99.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.geek99.dao.Table;
import com.geek99.dao.TableDao;
import com.geek99.dao.TableDaoImpl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 *  implementation class TableServlet
 */
@WebServlet("/TableServlet")
public class TableServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TableServlet() {
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
		String flag = request.getParameter("flag");
		TableDao dao = new TableDaoImpl();
		List<Table> list;
		if(flag!=null&&flag.equals("0")){
			list = dao.getEmptyTables();
		}else if(flag!=null&&flag.equals("1")){
			list = dao.getEatingTables();
		}else{
			list = dao.getAllTables();
		}
		// json
		Gson gson = new Gson();
		Type type = new TypeToken<List<Table>>(){}.getType();
		String json = gson.toJson(list, type);
		PrintWriter out = response.getWriter();
		out.print(json);
		
	}

}
