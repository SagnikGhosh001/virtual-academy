package com.smsv2.smsv2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.smsv2.smsv2.DTO.AssignmentDTO;
import com.smsv2.smsv2.DTO.BookDTO;
import com.smsv2.smsv2.entity.Book;
import com.smsv2.smsv2.entity.Dept;
import com.smsv2.smsv2.entity.Sem;

public interface BookService {
	// get all Book
	ResponseEntity<List<Book>> getAllBook();

	// get Book by id
	ResponseEntity<Optional<Book>> getAllBookById(int id);

	// get Book by sub id
	ResponseEntity<List<Book>> getAllBookBySubId(int subId);

	ResponseEntity<String> uploadFile(int id, MultipartFile file);
	byte[] downloadFile(int id);

	// add new Book
	ResponseEntity<?> addBook(BookDTO book);

	// update Book
	ResponseEntity<?> updateBook(int id, BookDTO book);

	

	// delete a Book
	ResponseEntity<?> delteBookById(int id,BookDTO book);

	// delete all Dept
	ResponseEntity<?> deleteAllBook(BookDTO book);
}
