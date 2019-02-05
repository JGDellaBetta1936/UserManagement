package com.tutorialspoint;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/*
 * TODO seperate out puts, updates, deletes, reads.  reusing same code and trying to force it to work is getting messy.
 */
@Path("/UserService")

public class UserService {
	UserDao userDao = new UserDao();

	@GET
	@Path("/users")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getUsers() {
		return userDao.getAllUsers();
	}

	@GET
	@Path("/users/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getUserByID(@PathParam("id") Integer id) {
		return userDao.getUserByID(id);
	}
	
	@POST
	@Path("/users/{id}/{name}/{age}")
	/**
	 * Note, since table is defined with primary key as auto-increment, no need to pass pathparam of ID, even thought it's present
	 * @param id			*** See note above, really not needed at this point.
	 * @param name
	 * @param age
	 * @param servletResponse
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	@Produces(MediaType.APPLICATION_XML)
	public String createUser(@PathParam("id") Integer id, @PathParam("name") String name, @PathParam("age") Integer age,
			@Context HttpServletResponse servletResponse) throws IOException, ClassNotFoundException, SQLException {
		int result = 0;
		User tempUser = new User(name, age);
		if (userDao.addUser(tempUser)!=1) {
			result = -1;
		}
		if (result == 0) {
			return UserDao.SUCCESS_RESULT;
		}
		else {
			return UserDao.FAILURE_RESULT;	
		}
		
	}

	@PUT
	@Path("/users/{id}/{name}/{age}")
	@Produces(MediaType.APPLICATION_XML)
	public String updateUser(@PathParam("id") Integer id, @PathParam("name") String name, @PathParam("age") Integer age,
			@Context HttpServletResponse servletResponse) throws IOException, ClassNotFoundException, SQLException {
		User user = new User(name, age);
		user.setId(id);
		int result = userDao.updateUser(user);
		if (result == 1) {
			return UserDao.SUCCESS_RESULT;
		}
		return UserDao.FAILURE_RESULT;
	}

	@DELETE
	@Path("/users/{userid}")
	@Produces(MediaType.APPLICATION_XML)
	public String deleteUser(@PathParam("userid") Integer userid) throws ClassNotFoundException, SQLException {
		int result = userDao.deleteUser(userid);
		if (result == 1) {
			return UserDao.SUCCESS_RESULT;
		}
		return UserDao.FAILURE_RESULT;
	}

	@GET
	@Path("/info/{username}")
	/*
	 * example url test =
	 * http://localhost:8080/UserManagement/rest/UserService/info/test and a test
	 * and a test no params are put into Postman, but on the url
	 */
	public Response responseMsg(@PathParam("username") String username) {

		String output = "This all the info about " + username;
		return Response.status(200).entity(output).build();

	}
}