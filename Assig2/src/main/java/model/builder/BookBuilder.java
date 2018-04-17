package model.builder;

import model.Book;

public class BookBuilder {

	private Book book;
	
	public BookBuilder(){
		this.book = new Book();
	}
	
	public BookBuilder setTitle(String title){
		book.setTitle(title);
		return this;
	}
	public BookBuilder seAuthor(String author){
		book.setAuthor(author);
		return this;
	}
	public BookBuilder setGenre(String genre){
		book.setGenre(genre);
		return this;
	}
	public BookBuilder setPrice(double price){
		book.setPrice(price);
		return this;
	}
	public Book build(){
		return this.book;
	}
}
