package me.alexishaldy.classes;
/**
 * This class is used for the method to get all books from a site or file
 * @author Alexis Haldy
 *
 */
public class Book {
	private String title;
	private String desc;
	private String author;
	/**
	 * Date in years
	 */
	private int date;
	private String editeur;
	/**
	 * Number of edition
	 */
	private int edition;
	
	/**
	 * Create a book
	 * @param title		Title of book
	 * @param desc		Description of book
	 * @param author	Author of book
	 * @param date		Date in years of book
	 * @param editeur	Editor of book
	 * @param edition	Number of edition of book
	 */
	public Book(String title, String desc, String author, int date, String editeur, int edition) {
		super();
		this.title = title;
		this.desc = desc;
		this.author = author;
		this.date = date;
		this.editeur = editeur;
		this.edition = edition;
	}
	
	/**
	 * Get title of book
	 * @return	Current title of book
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Set/change title of book
	 * @param title	New title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Get description of book
	 * @return Current description of book
	 */
	public String getDesc() {
		return desc;
	}
	
	/**
	 * Set/change description of book
	 * @param desc	New description
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	/**
	 * Get author of book
	 * @return	Current author of book
	 */
	public String getAuthor() {
		return author;
	}
	
	/**
	 * Set/change author of book
	 * @param author New author
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	
	/**
	 * Get date in years of book
	 * @return	Current date of book
	 */
	public int getDate() {
		return date;
	}
	
	/**
	 * Set/change date in years of book
	 * @param date	New date in years
	 */
	public void setDate(int date) {
		this.date = date;
	}
	
	/**
	 * Get editor
	 * @return	Current editor of book
	 */
	public String getEditeur() {
		return editeur;
	}
	
	/**
	 * Set/change editor
	 * @param editeur	New editor of book
	 */
	public void setEditeur(String editeur) {
		this.editeur = editeur;
	}
	
	/**
	 * Get num of edition
	 * @return	Number of edition of book
	 */
	public int getEdition() {
		return edition;
	}
	
	/**
	 * Set/change edition
	 * @param edition	New edition of book
	 */
	public void setEdition(int edition) {
		this.edition = edition;
	}
	
	
}




















