package com.mg.dao.impl;

import java.util.HashMap;
import java.util.Map;

import com.mg.dao.core.DaoFactory;
import com.mg.model.Users;

public class UserDAO extends GenericDaoImpl<Users> {

	private static final long serialVersionUID = 1L;

	public UserDAO() {
		super(Users.class);
	}

	public Users findUserByName(String userName){
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("username", userName);    

		return findOneResult(" select distinct u " +
							 " from Users u " +
							 " left join fetch u.userAddresses ua " +
							 " where u.login = :username " +
							 " and ( (u.typeCode = 'USER' and ua.typeCode = 'SHIPPING') or u.typeCode = 'ADMIN') ", parameters);
	}

	public static void main(String[] args) {
		/*System.setProperty(Context.INITIAL_CONTEXT_FACTORY, javaURLContextFactory.class.getName());
		System.setProperty(Context.URL_PKG_PREFIXES, "org.apache.naming");
		InitialContext ic = new InitialContext();

		ic.createSubcontext("java:");
		ic.createSubcontext("java:comp");
		ic.createSubcontext("java:comp/env");
		ic.createSubcontext("java:comp/env/jdbc");

		EmbeddedDataSource ds = new EmbeddedDataSource();
		ds.setDatabaseName("tutorialDB");
		// tell Derby to create the database if it does not already exist
		ds.setCreateDatabase("create");

		ic.bind("java:comp/env/jdbc/tutorialDS", ds);
		
		
		final Properties p = new Properties();
        p.put("movieDatabase", "new://Resource?type=DataSource");
        p.put("movieDatabase.JdbcDriver", "org.hsqldb.jdbcDriver");
        p.put("movieDatabase.JdbcUrl", "jdbc:hsqldb:mem:moviedb");

        final Context context = EJBContainer.createEJBContainer(p).getContext();
        */
        
		
		//UserDAO userDAO = new UserDAO();
		UserDAO userDAO = DaoFactory.getDAO(UserDAO.class);
		Users user = userDAO.findUserByName("1");

	}

	@Override
	public void joinTransaction() {
		// TODO Auto-generated method stub
		
	}

	public Users findEntityById(Integer id) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id", id);    

		return  findOneResult(" select distinct u " +
							  " from Users u " +
							  " left join fetch u.userAddresses ua " +
							  " where u.id = :id " +
							  " and ua.typeCode = 'SHIPPING'", parameters);
	}

}
