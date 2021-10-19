package com.kh.spring.book.model.dto;

public class Book {
	
	private String bkIdx;
	private String title;
	private String author;
	private int rentCnt;
	
	public Book() {
		// TODO Auto-generated constructor stub
	}

	public String getBkIdx() {
		return bkIdx;
	}

	public void setBkIdx(String bkIdx) {
		this.bkIdx = bkIdx;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getRentCnt() {
		return rentCnt;
	}

	public void setRentCnt(int rentCnt) {
		this.rentCnt = rentCnt;
	}

	@Override
	public String toString() {
		return "book [bkIdx=" + bkIdx + ", title=" + title + ", author=" + author + ", rentCnt=" + rentCnt + "]";
	}
	
	
	

}
