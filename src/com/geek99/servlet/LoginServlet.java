package com.geek99.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.geek99.dao.User;
import com.geek99.dao.UserDao;
import com.geek99.dao.UserDaoImpl;
import com.google.gson.Gson;

/**
 *  implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		System.out.println(username+","+password);
		UserDao dao = new UserDaoImpl();
		User u = dao.login(username,password);
		PrintWriter out = response.getWriter();
		System.out.println("doPost");
		
		if(u!=null){
			// Gson
			// json
			Gson gson = new Gson();
			String json = gson.toJson(u);
			out.print(json);
		}else{
			out.print("-1");
		}
	}

}
