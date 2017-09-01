package me.alexishaldy.classes;

public class Book {
	private String title;
	private String desc;
	private String author;
	private int date;
	private String editeur;
	private int edition;
	
	public Book(String title, String desc, String author, int date, String editeur, int edition) {
		super();
		this.title = title;
		this.desc = desc;
		this.author = author;
		this.date = date;
		this.editeur = editeur;
		this.edition = edition;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public int getDate() {
		return date;
	}
	public void setDate(int date) {
		this.date = date;
	}
	public String getEditeur() {
		return editeur;
	}
	public void setEditeur(String editeur) {
		this.editeur = editeur;
	}
	public int getEdition() {
		return edition;
	}
	public void setEdition(int edition) {
		this.edition = edition;
	}
	
	
}
