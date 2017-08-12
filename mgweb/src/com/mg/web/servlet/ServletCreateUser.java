package com.mg.web.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mg.dao.core.DaoFactory;
import com.mg.dao.impl.UserDAO;
import com.mg.model.Users;

public class ServletCreateUser extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8874240028737170294L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		
		System.out.println("+++ ServletCreateUser");

		UserDAO userDAO = DaoFactory.getDAO(UserDAO.class);
		/*ScUsers user = new ScUsers();
		user.setUsername("1");
		user.setPassword("1");
		user.setLanguageid(1);
		userDAO.save(user);
		userDAO.commit();*/
		Users user1 = userDAO.findUserByName("1");
		System.out.println("<<< ServletCreateUser");
	}
}
