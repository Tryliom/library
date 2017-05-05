package me.alexishaldy.classes;

public class User {
	private String name;
	private String lastName;
	private int id;
	
	public User(String name, String lastName) {
		super();
		this.name = name;
		this.lastName = lastName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int generateId() {
		this.id = (int) Math.round(Math.random()*1000000);
		return this.id;
	}
	
	public String returnBook(SubBook sub, String...bookRef) {
		Library lib = Library.getLibrary();
		int count = 0;
		if (bookRef.length>0) {			
			for (int i=0;i<bookRef.length;i++) {
				for (Book b : lib.getBookList().values()) {
					if (!b.isTaken() && ((sub.equals(SubBook.Id) && getInt(bookRef[i])==b.getId()) || (sub.equals(SubBook.Year) && getInt(bookRef[i])==b.getDate()))) {
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
					if (!b.isTaken() && ((sub.equals(SubBook.Id) && getInt(bookRef[i])==b.getId()) || (sub.equals(SubBook.Year) && getInt(bookRef[i])==b.getDate()))) {
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
	
	public int getInt(String i) {
		try {
			int result = Integer.parseInt(i);
			return result;
		} catch (Exception e) {
			return -1;
		}
	}
	
}
