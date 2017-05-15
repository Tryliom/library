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
	
	public String getUsername() {
		return username;
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
					if (b.getUserId()!=null && ((sub.equals(SubBook.Id) && bookRef[i].equals(b.getId())) || (sub.equals(SubBook.Year) && Utils.getInt(bookRef[i])==b.getDate()))) {
						
						count++;
					} else if (b.getUserId()!=null && ((sub.equals(SubBook.Title) && bookRef[i].equals(b.getTitle())) || (sub.equals(SubBook.Author) && bookRef[i].equals(b.getAuthor())))) {
						b.setUserId(null);
						count++;
					}
				}
			}
		} else {
			for (Book b : lib.getBookList().values()) {
				if (b.getUserId()!=null && b.getUserId().equals(this)) {
					b.setUserId(null);
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
					if (b.getUserId()==null && ((sub.equals(SubBook.Id) && bookRef[i].equals(b.getId())) || (sub.equals(SubBook.Year) && Utils.getInt(bookRef[i])==b.getDate()))) {
						b.setUserId(this.getIdentityId());
						count++;
					} else if (b.getUserId()==null && ((sub.equals(SubBook.Title) && bookRef[i].equals(b.getTitle())) || (sub.equals(SubBook.Author) && bookRef[i].equals(b.getAuthor())))) {
						b.setUserId(this.getIdentityId());
						count++;
					}
				}
			}
		} else {
			for (Book b : lib.getBookList().values()) {
				if (b.getUserId()==null) {
					b.setUserId(this.getIdentityId());
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
