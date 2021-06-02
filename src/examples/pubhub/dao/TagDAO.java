package examples.pubhub.dao;

import java.util.List;
import examples.pubhub.model.Book;
import examples.pubhub.model.Tag;

/* ================================================================================================
 * TagDAO interface to add tags, remove tags, get all tags for one book, and get all books with a given tag. This will help with SQL CRUD to CREATE, READ, UPDATE, DELETE records.
 * ================================================================================================
 */

public interface TagDAO {

	public boolean addTag(Tag tag, String isbn13);
	public boolean removeTag(Tag tag, String isbn13);
	public List<Tag> getAllTags();
	public List<Tag> getAllTags(String isbn13);
	public List<Book> getAllBooks(String tag_name); 
	
	public boolean updateTag(String newTag, String oldTag); // updating an existing tag's name. Possibly not used.
}