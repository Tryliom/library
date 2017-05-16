package me.alexishaldy.classes;

import java.util.Date;

public class Renter {
	private Book book;
	private Date takenDate;
	private Date returnDateMax;
	private Date realReturnDate;
	
	public Renter(Book book, Date takenDate, Date returnDateMax) {
		this.book = book;
		this.takenDate = takenDate;
		this.returnDateMax = returnDateMax;
	}

	public Date getRealReturnDate() {
		return realReturnDate;
	}

	public void setRealReturnDate(Date realReturnDate) {
		this.realReturnDate = realReturnDate;
	}

	public Book getBook() {
		return book;
	}

	public Date getTakenDate() {
		return takenDate;
	}

	public Date getReturnDateMax() {
		return returnDateMax;
	}
	
	
	
}
