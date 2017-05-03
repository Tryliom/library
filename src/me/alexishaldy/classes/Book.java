package me.alexishaldy.classes;

public class Book {
	private String title;
	private String author;
	private int number;
	private int date;
	private boolean taken;
	private User owner;
	
	public Book(String title, String author, int date) {
		this.setTitle(title);
		this.setAuthor(author);
		this.setDate(date);
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int id) {
		this.number = id;
	}
	
	public void addNumber() {
		this.number++;
	}
	
	public void remNumber() {
		this.number--;
	}

	public int getDate() {
		return date;
	}

	public void setDate(int date) {
		this.date = date;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isTaken() {
		return taken;
	}

	public void setTaken(boolean taken) {
		this.taken = taken;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}
	
	
}
