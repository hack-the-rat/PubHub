package examples.pubhub.model;

/* ================================================================================================
 * This class is for any tag object created. It identifies each tag as a unique entity with a name, check status, and a possible id.
 * ================================================================================================
 */
public class Tag {

	private String name;
	private int id; // Created an id variable, for possible future usage. For example, if we want to have a default "bank" of tags.
	private boolean check; // This variable saves the status of a tag object, if applicable, when associated with a book.
	public String javacheck=""; // This is a string variable to support the HTML form and determine whether the checbox is checked.
	
	// Default, no-arg constructor
	public Tag() {
		this.name = null;
		this.id = -1;
	}
	
	// Parameter in case only a name is provided.
	public Tag(String s) {
		this.name = s;
	}
	
	// Parameter when both name and check condition provided
	public Tag(String s, boolean c) {
		this.name = s;
		this.check = c;
		if(c) {
			this.javacheck = "checked";
		} else {
			this.javacheck ="";
		}
	}
	
	// Name getter and setter
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	// ID setter and getter
	public int getID() {
		return id;
	}
	
	// ID setter
	public void setID(int a) {
		this.id = a;
	}
	
	// getter for check status
	public boolean getCheck() {
		return check;
	}
	
	// setter for check status. Note, this also method will also change the javacheck variable.
	public void setCheck(boolean c) {
		this.check = c;
		if(c) {
			this.javacheck = "checked";
		} else {
			this.javacheck ="";
		}
	}

	public String getJavacheck() {
		return javacheck;
	}

	public void setJavacheck(String javacheck) {
		this.javacheck = javacheck;
	}

	@Override
	public String toString() {
		return this.name;
	}
}