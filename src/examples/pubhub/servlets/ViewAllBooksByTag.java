package examples.pubhub.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import examples.pubhub.dao.*;
import examples.pubhub.model.*;
import examples.pubhub.utilities.DAOUtilities;

/* ================================================================================================
 * Servlet implementation class for ViewAllBooksByTags. This will handle retrieval of books from the database that have a given tag associated with them. The jsp will display the data as buttons a user can click.
 * ================================================================================================
 */
/**
 * Servlet implementation class ViewAllBooksByTag
 */
@WebServlet("/TagSearch")
public class ViewAllBooksByTag extends HttpServlet {

	private static final long serialVersionUID = 1L;
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String tag = request.getParameter("tag");
		
		TagDAO tdao = DAOUtilities.getTagDAO();
		List<Book> bookList = tdao.getAllBooks(tag); // uses DAO to query the DB for all books w/ given tag.
		
		request.setAttribute("books", bookList);
		request.setAttribute("tag", tag); // Note, tag STRING not a tag obj.
		request.getRequestDispatcher("TagSearch.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}