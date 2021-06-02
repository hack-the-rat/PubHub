package examples.pubhub.dao;

import java.util.List;
import examples.pubhub.model.Tag;
import examples.pubhub.utilities.DAOUtilities;
import examples.pubhub.model.Book;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/* ================================================================================================
 * This class implements the methods established by the available TagDAO interface. Many of the functionalities are derived from the BookDAO interface and its implementing class.
 * ================================================================================================
 */
public class TagDAOImpl implements TagDAO {

	Connection connection = null;// Our connection to the database
	PreparedStatement stmt = null;	// We use prepared statements to help protect against SQL injection
	
	/*	================================================================================================
	 *  Add tag will allow class to associate a single tag object with an ISBN in the database.
	 *  ================================================================================================
	 */	
	@Override
	public boolean addTag(Tag tag, String isbn13) {
		try {
		// Attempt to get a connection to DB.
			connection = DAOUtilities.getConnection();
			String sql = "INSERT INTO BOOK_TAGS VALUES (?, ?)"; // SQL statement for our query
			stmt = connection.prepareStatement(sql);
		
			stmt.setString(1, tag.getName()); // getter for private name variable of tag object.
			stmt.setString(2, isbn13); 
			
			// Simply, if update is successful, return true. False otherwise.
			if (stmt.executeUpdate() != 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeResources(); // close Resource method at the very bottom.
		}
	}
	
	/* ================================================================================================
	 * This method will delete the tag for only the given book isbn13.
	 * ================================================================================================
	 */
	@Override
	public boolean removeTag(Tag tag, String isbn13) {
		try {
			connection = DAOUtilities.getConnection();
			String sql  = "DELETE FROM book_tags WHERE tag_name = ? AND isbn_13 = ? ";
			stmt = connection.prepareStatement(sql);
			
			stmt.setString(1, tag.getName());
			stmt.setString(2, isbn13);
			
			if (stmt.executeUpdate() != 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeResources();
		}
	}

	/*  ================================================================================================
	 *  The following two methods will query the DB for all tags. 
	 *  
	 *  * One will grab all available tags across all books in the database.
	 *  * The second will grab all tags associated to a given isbn13.
	 *  * Both methods return a List of Tag objects.
	 *  ================================================================================================
	 */
	public List<Tag> getAllTags() {
		//Simply, creates the collection, or container, for the tag objects.
		List<Tag> tagList = new ArrayList<>();

		try {
			Connection connection = DAOUtilities.getConnection(); // Gets database connection from DAO manager
			//SQL Statement to retrieve all tags from DB and order them alphabetically.
			String sql = "SELECT DISTINCT tag_name FROM book_tags ORDER BY tag_name"; 
			stmt = connection.prepareStatement(sql);
			
			ResultSet rs = stmt.executeQuery(); // Queries the database and saves data to rs.
			
			// While the result set contains results, continue iterating the following:
			while(rs.next()) {
				//Create a new tag object to save the information.
				Tag tag = new Tag();
				
				// get the tag_name column of the indicated record from the query and save to the created POJO.
				tag.setName(rs.getString("tag_name"));
				// tag.setID(rs.getInt("tag_id")); Not yet useful. TBD if will use.
				
				// Add the tag object to the tag list.
				tagList.add(tag);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		// Return the taglist provided by the DB.
		return tagList;
	}
	
	@Override
	public List<Tag> getAllTags(String isbn13) {
		//Simply, creates the collection, or container, for the tag objects.
		List<Tag> tagList = new ArrayList<>();

		try {
			Connection connection = DAOUtilities.getConnection(); // Gets database connection from manager
			String sql = "SELECT * FROM book_tags WHERE isbn_13=? ORDER BY book_tags"; //Statement to retrieve all tags for a given book
			stmt = connection.prepareStatement(sql);
			
			stmt.setString(1, isbn13);
			
			ResultSet rs = stmt.executeQuery(); // Queries the database and saves data to rs.
			
			// While the result set contains results, continue iterating the following:
			while(rs.next()) {
				//Create a new tag object to save the information.
				Tag tag = new Tag();
				
				// get the tag_name column of the indicated record from the query and save to the created POJO.
				tag.setName(rs.getString("tag_name"));
				// tag.setID(rs.getInt("tag_id")); Not yet useful. TBD if will use.
				
				// Add the tag object to the tag list.
				tagList.add(tag);
				
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		// Return the taglist provided by the DB.
		return tagList;
	}
	/*  ================================================================================================
	 *  Now we have some functionality to retrieve any books in the DB associated with a given tag.
	 *  ================================================================================================
	 */ 
	@Override
	public List<Book> getAllBooks(String tag_name) {
		// Because this method retrieves information about books, it returns a list of book objects.
		List<Book> bookList = new ArrayList<>();
		
		try {
			Connection connection = DAOUtilities.getConnection();
			/*
			 *  The DB has two tables. This query joins the tables using ISBN_13 to only reveal any book titles for a given tag name. It does not show the ISBN or any other data.
			 */
			String sql =  "	SELECT * FROM BOOKS INNER JOIN BOOK_TAGS ON BOOKS.ISBN_13 = BOOK_TAGS.ISBN_13 WHERE tag_name = ? ORDER BY books.title";
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, tag_name); 			// pass on the tag_name argument.
			
			ResultSet rs = stmt.executeQuery();		// Queries the database and saves it to a result set
			
			while (rs.next()) {
				// Creates a new book object for every book pulled
				Book nBook = new Book();
				nBook.setIsbn13(rs.getString("isbn_13")); 		// Gets the isbn_13 from the DB and saves to the book object
				nBook.setTitle(rs.getString("title"));			// title
				nBook.setAuthor(rs.getString("author"));		// author
				nBook.setPublishDate(rs.getDate("publish_date").toLocalDate());
				nBook.setPrice(rs.getDouble("price"));			// price
				nBook.setContent(rs.getBytes("content"));		// Content retrieved in (kilo)bytes.
				
				bookList.add(nBook);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		
		return bookList;
	}
	
	/* ================================================================================================
	 * Update tag will change the name of a tag for all records with the old tag. Currently, the application does not use this method. when updating tags, it simply uses the add/remove tag methods above.
	 * ================================================================================================
	 */
	@Override
	public boolean updateTag(String newTag, String oldTag) {
		try {
			connection = DAOUtilities.getConnection();
			String sql = "UPDATE book_tags SET tag_name = ? WHERE tag_name = ? "; 
			stmt = connection.prepareStatement(sql);
			
			stmt.setString(1, newTag);
			stmt.setString(2, oldTag);
			
			if (stmt.executeUpdate() != 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeResources();
		}
	}
	/*------------------------------------------------------------------------------------------------*/

	// Closing all resources is important, to prevent memory leaks. Ideally, you really want to close them in the reverse-order you open them
	private void closeResources() {
		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException e) {
			System.out.println("Could not close statement!");
			e.printStackTrace();
		}
		
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			System.out.println("Could not close connection!");
			e.printStackTrace();
		}
	}
}