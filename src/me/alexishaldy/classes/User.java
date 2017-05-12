package me.alexishaldy.classes;

import me.alexishaldy.util.Utils;

public class User {
	private String username;
	private String name;
	private String lastName;
	private String identityId;
	private String email;
	private String tel;
	
	

	public User(String username, String name, String lastName, String email, String tel) {
		super();
		this.username = username;
		this.name = name;
		this.lastName = lastName;
		this.email = email;
		this.tel = tel;
		this.generateId();
	}

	public String getName() {
		return name;
	}

	public String getLastName() {
		return lastName;
	}

	public String getIdentityId() {
		return identityId;
	}
	
	public String generateId() {
		this.identityId = this.username+"_"+(int) Math.round(Math.random()*1000000);
		return this.identityId;
	}
	
	public String returnBook(SubBook sub, String...bookRef) {
		Library lib = Library.getLibrary();
		int count = 0;
		if (bookRef.length>0) {			
			for (int i=0;i<bookRef.length;i++) {
				for (Book b : lib.getBookList().values()) {
					if (!b.isTaken() && ((sub.equals(SubBook.Id) && Utils.getInt(bookRef[i])==b.getId()) || (sub.equals(SubBook.Year) && Utils.getInt(bookRef[i])==b.getDate()))) {
						b.setTaken(false);
						b.setOwner(null);
						count++;
					} else if (!b.isTaken() && ((sub.equals(SubBook.Title) && bookRef[i].equals(b.getTitle())) || (sub.equals(SubBook.Author) && bookRef[i].equals(b.getAuthor())))) {
						b.setTaken(false);
						b.setOwner(null);
						count++;
					}
				}
			}
		} else {
			for (Book b : lib.getBookList().values()) {
				if (b.isTaken() && b.getOwner().equals(this)) {
					b.setTaken(false);
					b.setOwner(null);
					count++;
				}
			}
		}
		if (count==0)
			return "No books taken with this name";
		else
			return count+" book(s) returned";
	}
	
	public String takenBook(SubBook sub, String...bookRef) {
		Library lib = Library.getLibrary();
		int count = 0;
		if (bookRef.length>0) {			
			for (int i=0;i<bookRef.length;i++) {
				for (Book b : lib.getBookList().values()) {
					if (!b.isTaken() && ((sub.equals(SubBook.Id) && Utils.getInt(bookRef[i])==b.getId()) || (sub.equals(SubBook.Year) && Utils.getInt(bookRef[i])==b.getDate()))) {
						b.setTaken(true);
						b.setOwner(this);
						count++;
					} else if (!b.isTaken() && ((sub.equals(SubBook.Title) && bookRef[i].equals(b.getTitle())) || (sub.equals(SubBook.Author) && bookRef[i].equals(b.getAuthor())))) {
						b.setTaken(true);
						b.setOwner(this);
						count++;
					}
				}
			}
		} else {
			for (Book b : lib.getBookList().values()) {
				if (!b.isTaken()) {
					b.setTaken(true);
					b.setOwner(this);
					count++;
				}
			}
		}
		if (count==0)
			return "No books can be taken with this name";
		else
			return count+" book(s) taken";
	}
	
}
