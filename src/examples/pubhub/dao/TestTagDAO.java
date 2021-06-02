package examples.pubhub.dao;

import java.util.List;
import examples.pubhub.model.*;
import examples.pubhub.utilities.*;

public class TestTagDAO {

	public static void main(String[] args) {
		// new instance of DAO class
		TagDAO bigdao = DAOUtilities.getTagDAO();
		// Create a new list of tags to hold the information read by the getAllTags for a book of a given isbn
		List<Tag> tagList = bigdao.getAllTags();
		
		// Simply iterate through the list of tag objects and print out their names.
		for (int i = 0; i < tagList.size(); i++) {
				Tag t = tagList.get(i);
				System.out.println(t);
		}
	}
}
