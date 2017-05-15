package me.alexishaldy.classes;

public class Book {
	private String title;
	private String author;	
	private int date;
	private String desc;
	private int edition;
	private String editional;
	private String userId;
	private String id;
	
	public Book(String title, String author, int date, String desc, int edition, String editional) {
		this.title = title;
		this.author = author;
		this.date = date;
		this.desc = desc;
		this.edition = edition;
		this.editional = editional;
		this.generateId();
	}
	
	public Book(String title, String author, int date, String desc, int edition, String editional, String userId) {
		this.title = title;
		this.author = author;
		this.date = date;
		this.desc = desc;
		this.edition = edition;
		this.editional = editional;
		this.userId = userId;
		this.generateId();
	}

	public String generateId() {
		try {
			this.id = title.substring(0, 1).toUpperCase()+author.substring(0, 1).toUpperCase()+desc.substring(0, 3).toUpperCase()+Math.round(Math.random()*1000000);
		} catch (Exception e) {
			this.id = title.substring(0, 1).toUpperCase()+author.substring(0, 1).toUpperCase()+desc.substring(0, 1).toUpperCase()+Math.round(Math.random()*1000000);
		}
		return this.id;
	}

	public int getDate() {
		return date;
	}
	
	public String getAuthor() {
		return author;
	}

	public String getTitle() {
		return title;
	}

	public String getUserId() {
		return userId;
	}

	public String getDesc() {
		return desc;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getEdition() {
		return edition;
	}

	public String getId() {
		return id;
	}
	
}
