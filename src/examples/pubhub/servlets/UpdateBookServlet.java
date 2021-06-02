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
 * Servlet implementation class for UpdateBookServlet. The portion pertaining the updating book details was written by Revature and was a portion of the base start pack for this project. I added some functionality to handle adding and removing  tags from a particular book retrieved from the database, all within the same webpage to update the book details. It felt a litte odd redirecting the user to  a different webpage just to update the tags, so I chose to
 * ================================================================================================
 */
@WebServlet("/UpdateBook")
public class UpdateBookServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
    
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean isSuccess= false;
		String isbn13 = request.getParameter("isbn13");
		// dao and tdao will allow us to make changes to books and tags respectively.
		BookDAO dao = DAOUtilities.getBookDAO();
		TagDAO tdao = DAOUtilities.getTagDAO();
		Book book = dao.getBookByISBN(isbn13);
		// This retrieves a list of all tags available in the databse. The JSP displays all tags in the DB and only sets the relevant tags to a checked status for a certain book. This is to allow user to easily check any new tags they wish to add on that same page.
		List<Tag> allTags = tdao.getAllTags(); 
		List<Tag> currTags =  tdao.getAllTags(isbn13); // Retrieves a list of the current tags for a book in the db.
		
		if(book != null) {
			// The only fields we want to be updatable are title, author and price. A new ISBN has to be applied for, and a new edition of a book needs to be re-published.
			book.setTitle(request.getParameter("title"));
			book.setAuthor(request.getParameter("author"));
			book.setPrice(Double.parseDouble(request.getParameter("price")));
			// The parameter returned from this name in the jsp will be a string.
			// The method could return a null string, which would break part of the code ahead. So we create an empty dummy string.
			String tTag = request.getParameter("ttags").toLowerCase();
			String [] empArr = {""};
			// This method returns the actual values that are checked by the user in a string array.
			String[] stag = request.getParameterValues("tags");
			// a simply boolean variable to determine whether or not the user has decided to not have any tags with a book.
			boolean empty = false;
			if(stag == null) {   
				// If the string array is null, we'll instead say it's an empty array.
				empty = true; 
				stag = empArr;
			}
			
			/*	================================================================================================
			 * CHECKBOX TAGS: 
			 * 
			 * The next few blocks apply only to the checkbox part of the form. This webpage allows the user to update the tags using tags other users have input for their books or they have the option to create their own custom tags by typing them in.
			 * 
			 * If the number of tags proposed by the check boxes is larger than the tags already in the DB, the user is clearly trying to add a tag to a book. Because the JSP will accept the allTags list with the request, we iterate through that array and scan for any of the new, proposed tag array "stag."  
			 * 
			 * Thought: Could be updated to iterate through currTags and only update the DB if we find a new tag that wasn't there before.
			 * ================================================================================================
			 */ 
			if(stag.length > currTags.size() || currTags.isEmpty()) {
				for (int j = 0; j < allTags.size(); j++) {	
					String s = allTags.get(j).toString();
					for (int i = 0; i < stag.length; i++) {	
						// If we find a match in both arrays, create a new tag object with initialized parameters. Note that the second parameter is a boolean in the Tag class that tells us whether the object is checked or not. If they match, we want it checked.
						if(s.equalsIgnoreCase(stag[i])) {
							Tag tag = new Tag(stag[i], true);
							// This line adds the tag to the DB. If it exists already, the primary key constraint in SQL. Will not allow it to add another entry. Is there a better way to code this to check if a pre-existing entry is there? 
							tdao.addTag(tag, isbn13); 
						}
					}
					
				}
			}
			
			/*	================================================================================================
			 * If the length of the "stag" array is shorter, clearly, the user is attempting to remove tags from a book. This time, the loops iterate through the current tags and the new tags. In order to remove tags, we first clear the "check" variable on each of the tags and only set it to true if both containers have the tag. 
			 * ================================================================================================
			 */
			else if(stag.length < currTags.size() || empty) {	
				
				for (int i = 0; i < currTags.size(); i++) {
					//clears all checkboxes in the current tag list
					currTags.get(i).setCheck(false);
					String s = currTags.get(i).toString();
					for (int j = 0; j < stag.length; j++) {	
						if(s.contentEquals(stag[j])) {	
							// if it finds a match, set checkbox to true and exit the nested loop.
							currTags.get(i).setCheck(true);
							break;
						} 
					}
					// If a tag isn't found in the "stag" array, its checked status remains "false," so we remove that tag from the associated book in the DB.
					if (currTags.get(i).getCheck() == false) {
						tdao.removeTag(currTags.get(i), isbn13);
					}			
				}
			} 
			
			/* ================================================================================================
			 * TEXT BOX TAGS:
			 * 
			 * The next few blocks allow the user to input their own custom tags separated by commas. White spaces are removed and we use string manipulation to isolate tags by commas and the while loop iterates as long as commas are present.
			 * ================================================================================================
			 */
			
			if (tTag.isEmpty()==false) {
				tTag.replaceAll("\\s+", "");
				String a;
				String b;
				while(tTag.contains(",")) {
					a = tTag.substring(0, tTag.indexOf(",")); // This substring will become the new tag.
					b = tTag.substring(tTag.indexOf(",")+1); // The remainder of the substring.
					Tag tag = new Tag(a, true); //Creates a new tag using the new substring and checks it.
					tdao.addTag(tag, isbn13); // Add this custom tag to the DB.
					tTag = b; // b becomes the new tTag to be iterated over.
				}
				// If a user inputs a single tag or after the while loop is complete, the values will no longer have commas.
				// This next block ensures single tags are still added to the DB, including the one leftover by the substring that survives the while-loop.
				if(tTag.contains(",") == false) {
					Tag tag = new Tag(tTag, true);
					tdao.addTag(tag, isbn13);
				}
			}
			
			// Commented this out because it seems redundant. the Redirect to the webpage pulls DB info from another servlet.
			//request.setAttribute("tags", allTags);
			request.setAttribute("book", book);
			isSuccess = dao.updateBook(book);
		} else {
			//ASSERT: couldn't find book with isbn. Update failed.
			isSuccess = false;
		}
		
		if(isSuccess) {
			request.getSession().setAttribute("message", "Book successfully updated");
			request.getSession().setAttribute("messageClass", "alert-success");
			response.sendRedirect("ViewBookDetails?isbn13=" + isbn13);
		} else {
			request.getSession().setAttribute("message", "There was a problem updating this book");
			request.getSession().setAttribute("messageClass", "alert-danger");
			request.getRequestDispatcher("bookDetails.jsp").forward(request, response);
		}
	}
}