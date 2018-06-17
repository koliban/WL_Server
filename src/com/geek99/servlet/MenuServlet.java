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

import com.geek99.dao.MenuDao;
import com.geek99.dao.Menu;
import com.geek99.dao.MenuDaoImpl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 *  implementation class TableServlet
 */
@WebServlet("/MenuServlet")
public class MenuServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MenuServlet() {
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
		//String flag = request.getParameter("flag");
		response.setContentType("text/json");
		response.setCharacterEncoding("utf-8");
		MenuDao dao = new MenuDaoImpl();
		List<Menu> list = dao.listAllMenu();
		Gson gson = new Gson();
		Type type = new TypeToken<List<Menu>>(){}.getType();
		String json = gson.toJson(list, type);
		System.out.println(json);
		PrintWriter out = response.getWriter();
		out.print(json);
	}

}
