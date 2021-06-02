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
 * Servlet implementation class for ViewBookDetailsServlet. This servlet class was included in the Pubhub 100 starter base kit. I added a bit of code to retrieve some information from the database to display alongside book details. This servlet will pull the tags for the selected book and will check "true" for any tags found in the entirety of the DB to display to users what they can select.
 * ================================================================================================
 */

// This is a "View" servlet, and has been named accordingly. All it does is send the user to a new JSP page but it also takes the opportunity to populate the session or request with additional data as needed.
@WebServlet("/ViewBookDetails")
public class ViewBookDetailsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// The bookDetails.jsp page needs to have the details of the selected book saved to the request, otherwise it won't know what details to display. Ergo, we need to fetch those details before we actually redirect the user.
		String isbn13 = request.getParameter("isbn13");
		
		// Using the static method in the utilities class, it allows us to create new objects to query from the DB
		BookDAO dao = DAOUtilities.getBookDAO();
		TagDAO tDao = DAOUtilities.getTagDAO();
		// Takes this implementation and creates a new book object with the values returned by the method.
		Book book = dao.getBookByISBN(isbn13); // Need modifications to bring in tags as well. 
		List<Tag> tagList = tDao.getAllTags(isbn13); // Gets all tags for this particular book in a list
		List<Tag> allTags = tDao.getAllTags();
		
		// Compares the tag lists and removes present tags from the list of all tags that will be read as unchecked by the JSP. Probably not the best way of doing this. Should revisit.
		for (int j = 0; j < tagList.size(); j++) {	
			String s1 = tagList.get(j).getName();
			for (int i = 0; i < allTags.size(); i++) {
				String s2 = allTags.get(i).getName();
				if(s1.equalsIgnoreCase(s2)) {
					allTags.get(i).setCheck(true);
				}
			}
		}
		
		request.setAttribute("book", book);
		request.setAttribute("tags", allTags);

		// We can use a forward here, because if a user wants to refresh their browser on this page, it will just show them the most recent details for their book. There's no risk of data miss-handling here.
		request.getRequestDispatcher("bookDetails.jsp").forward(request, response);
	}
}