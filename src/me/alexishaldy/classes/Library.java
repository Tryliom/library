package me.alexishaldy.classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class Library {
	private static Library instance;
	private String name; // adresse CIF
	private String adress;
	private String CIF;
	private HashMap<String, Book> bookList = new HashMap<String, Book>();
	private ArrayList<User> userList = new ArrayList<>();
	private ArrayList<Renter> renterList = new ArrayList<>();
	private String lastId;
	
	/**
	 * @return Create un singleton of Library
	 * 
	*/
	public static Library getLibrary() {
		if (instance==null)
			instance = new Library("Library", "null", "null");
		return instance;
	}
	


	private Library(String name, String adress, String cIF) {
		this.name = name;
		this.adress = adress;
		lastId = "";
		CIF = cIF;
	}
	
	public static void initLibrary(String name, String adress, String cIF) {
		instance = new Library(name, adress, cIF);
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * List book
	 * @param type Can choose a type of sorting for books
	 * 
	*/
	public String listRenterBook() {
		String s = "";
		for (int j=0;j<getRenterList().size();j++) {
			Renter r = getRenterList().get(j);
			Book b = r.getBook();
			if (r.getRealReturnDate()==null)
			s+="\nN°"+j+":\nTitle: "+b.getTitle()+"\tAuthor: "+b.getAuthor()+"\nYear: "+b.getDate()+"\tDescription: "+b.getDesc()+"\nNuméro d'édition: "+b.getEdition()+"\tÉditional: "+b.getEditional()+"\n"
					+ "ISBN: "+b.getIsbn()+"\tDate d'emprunt:"+r.getTakenDate()+"\nDate de retour:"+r.getReturnDateMax()+"\n";
		}
		
		return s;
	}
	
	/**
	 * List book
	 * @param type Can choose a type of sorting for books
	 * 
	*/
	public String listAllBook(SortType...type) {
		if (type.length>0) {
			String s = "Book list sorted by "+type[0].name()+":";
			if (type[0].equals(SortType.Number)) {
				int i = 1;
				for (Book b : bookList.values()) {
					s+="\nN°"+i+":\nTitle: "+b.getTitle()+"\tAuthor: "+b.getAuthor()+"\nYear: "+b.getDate()+"\tDescription: "+b.getDesc()+"\nNuméro d'édition: "+b.getEdition()+"\tÉditional: "+b.getEditional()+"\n"
							+ "ISBN: "+b.getIsbn()+"\n";
					i++;
				}		
			}	
			if (type[0].equals(SortType.year)) {
				List<Book> tmpBook = new ArrayList<Book>(bookList.values());

			    Collections.sort(tmpBook, new Comparator<Book>() {

			        public int compare(Book o1, Book o2) {
			        	if (o1.getDate() == o2.getDate())
			        		return 0;
			        	if (o1.getDate() < o2.getDate())
			        		return -1;
			        	return 1;
			        }


			    });
			    int i = 1;
				for (Book b : tmpBook) {
						s+="\nN°"+i+":\nTitle: "+b.getTitle()+"\tAuthor: "+b.getAuthor()+"\nYear: "+b.getDate()+"\tDescription: "+b.getDesc()+"\nNuméro d'édition: "+b.getEdition()+"\tÉditional: "+b.getEditional()+"\n"
								+ "ISBN: "+b.getIsbn()+"\n";
						i++;
				}	
			}
			return s;
		} else {
			String s = "Book list:";
			int i = 1;
			for (Book b : bookList.values()) {
				s+="\nN°"+i+":\nTitle: "+b.getTitle()+"\tAuthor: "+b.getAuthor()+"\nYear: "+b.getDate()+"\tDescription: "+b.getDesc()+"\nNuméro d'édition: "+b.getEdition()+"\tÉditional: "+b.getEditional()+"\n"
						+ "ISBN: "+b.getIsbn()+"\n";
				i++;
			}		
			return s;
		}
	}

	/**
	 * Return a book
	 * @param type Can choose a type of book search
	 * 
	*/
	public Book getBook(SortType type, String arg[]) {		
		try {
			if (type.equals(SortType.title)) {
				for (Book b : bookList.values()) {
						if (b.getTitle().equalsIgnoreCase(arg[0])) {
							return b;
						}
				}		
			}	
			
			if (type.equals(SortType.author)) {
				for (Book b : bookList.values()) {
						if (b.getAuthor().equalsIgnoreCase(arg[0])) {
							return b;
						}
				}		
			}
			
			if (type.equals(SortType.year)) {
				for (Book b : bookList.values()) {
						if (b.getDate()==Integer.parseInt(arg[0])) {
							return b;
						}
				}		
			}
			
			if (type.equals(SortType.title_author)) {
				for (Book b : bookList.values()) {
						if (b.getTitle().equalsIgnoreCase(arg[0]) && b.getAuthor().equalsIgnoreCase(arg[1])) {
							return b;
						}
				}		
			}
			
			if (type.equals(SortType.title_author_numedition)) {
				for (Book b : bookList.values()) {
						if (b.getTitle().equalsIgnoreCase(arg[0]) && b.getAuthor().equalsIgnoreCase(arg[1]) && b.getEdition()==Integer.parseInt(arg[1])) {
							return b;
						}
				}		
			}
			//TODO: Remake desc match
			if (type.equals(SortType.desc)) {
				// String regex = "^["+arg[0].substring(0, 1).toUpperCase()+arg[0].substring(0, 1).toLowerCase()+"]"+arg[0].substring(1, arg[0].length())+"$";
				for (Book b : bookList.values()) {
						if (b.getDesc().equalsIgnoreCase(arg[0])) {
							return b;
						}
				}		
			}
			
		} catch (Exception e) {}
		return null;
	}
	
	/**
	 * @param book Add the book to the Library
	 * 
	*/
	public void addBook(Book book) {
		this.bookList.put(book.getId(), book);
		lastId=book.getId();
	}
	
	/**
	 * @param title If not title specified the last book created is deleted
	 * 
	*/
	public void removeBook(String... title) {
		if (title.length>0)
			for (int i=0;i<title.length;i++)
				for (Book b : bookList.values()) {
					if (b.getTitle().equalsIgnoreCase(title[i])) {
						bookList.remove(b.getId());
						return;
					}
				}
		else {
			bookList.remove(lastId);
		}
			
	}
	
	/**
	 * @param b Remove this book
	 * 
	*/
	public Boolean removeBook(Book b) {
		if (bookList.remove(b) != null) {
			return true;
		}
		return false;
	}
	
	public void addUser(User u) {
		u.generateId();
		userList.add(u);
	}
	
	/**
	 * @param name Search by Username
	 * 
	*/
	public User getUserByUsername(String name) {
		for (int i=0;i<userList.size();i++) {
			if (userList.get(i).getUsername().equalsIgnoreCase(name)) {
				return userList.get(i);
			}
		}
		
		return null;
	}
	
	/**
	 * @param name Search by id
	 * 
	*/
	public User getUserById(String id) {
		for (int i=0;i<userList.size();i++) {
			if (userList.get(i).getIdentityId().equalsIgnoreCase(id)) {
				return userList.get(i);
			}
		}
		
		return null;
	}
	
	/**
	 * @param user The user we will delete
	 * 
	*/
	public boolean removeUser(User user) {
		if (userList.remove(user))
			return true;		
		return false;
	}
	
	/**
	 * @return Display User in String format and his books if he owned them
	 * 
	*/
	public String listUserAndBooks() {
		String s = "User list:";
		for (int i=0;i<userList.size();i++) {
			s+="\n\nUser "+userList.get(i).getIdentityId()+":\n";
			s+="Name: "+userList.get(i).getName()+"\nLast Name: "+userList.get(i).getLastName()+"\nEmail: "+userList.get(i).getEmail()+"\nTel: "+userList.get(i).getTel();
			for (Book b : bookList.values()) {
				if (b.getUserId()!=null && b.getUserId().equals(userList.get(i).getIdentityId())) {
					s+="\n\tBook taken:\n\nTitle: "+b.getTitle()+"\tAuthor: "+b.getAuthor()+"\nYear: "+b.getDate()+"\tDescription: "+b.getDesc()+"\nNuméro d'édition: "+b.getEdition()+"\tÉditional: "+b.getEditional()+"\n"
						+ "ISBN: "+b.getIsbn()+"\n";
				}
			}
		}
		
		return s;
	}

	public HashMap<String, Book> getBookList() {
		return bookList;
	}

	public ArrayList<Renter> getRenterList() {
		return renterList;
	}
}
