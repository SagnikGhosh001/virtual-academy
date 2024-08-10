package com.smsv2.smsv2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.web.multipart.MultipartFile;

import com.smsv2.smsv2.DTO.AssignmentDTO;
import com.smsv2.smsv2.DTO.BookDTO;
import com.smsv2.smsv2.entity.Book;
import com.smsv2.smsv2.entity.Dept;
import com.smsv2.smsv2.entity.Sem;

public interface BookService {
	// get all Book
	List<Book> getAllBook();

	// get Book by id
	Optional<Book> getAllBookById(int id);

	// get Book by sub id
	List<Book> getAllBookBySubId(int subId);

	String uploadFile(int id, MultipartFile file);
    byte[] downloadFile(int id);

	// add new Book
	void addBook(BookDTO book);

	// update Book
	void updateBook(int id, BookDTO book);

	

	// delete a Book
	void delteBookById(int id,BookDTO book);

	// delete all Dept
	void deleteAllBook(BookDTO book);
}
