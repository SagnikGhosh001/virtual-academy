package com.smsv2.smsv2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.smsv2.smsv2.DTO.BookDTO;
import com.smsv2.smsv2.service.BookService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/book")
public class BookController {

	@Autowired
	private BookService bookservice;

	@GetMapping("/allbooks")
	public ResponseEntity<?> allBooks() {
		return new ResponseEntity<>(bookservice.getAllBook(), HttpStatus.OK);
	}

	@GetMapping("/booksbyId/{id}")
	public ResponseEntity<?> booksbyId(@PathVariable("id") int id) {
		return new ResponseEntity<>(bookservice.getAllBookById(id), HttpStatus.OK);
	}

	@GetMapping("/booksbySubId/{subid}")
	public ResponseEntity<?> booksbySemId(@PathVariable("subid") int subid) {
		return new ResponseEntity<>(bookservice.getAllBookBySubId(subid), HttpStatus.OK);
	}

	@PostMapping("/uploadpdf/{id}/{role}")
	public ResponseEntity<String> uploadPdf(@PathVariable("role") String role, @PathVariable("id") int id,
			@RequestParam("file") MultipartFile file) {
		if (role.equals("teacher") || role.equals("hod") || role.equals("pic")) {

			String message = bookservice.uploadFile(id, file);
			return ResponseEntity.status(HttpStatus.OK).body(message);
		} else {
			return new ResponseEntity<String>("you are not allowed for this action", HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("/downloadpdf/{id}")
	public ResponseEntity<byte[]> downloadPdf(@PathVariable("id") int id) {
		byte[] fileData = bookservice.downloadFile(id);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF); // Set content type to PDF
		headers.setContentDispositionFormData("attachment", "attendence_document.pdf"); // Set filename for download

		return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
	}

	@PostMapping("/addbook")
	public ResponseEntity<?> addbook(@RequestBody BookDTO bookDTO) {

		this.bookservice.addBook(bookDTO);
		return new ResponseEntity<>(HttpStatus.CREATED);

	}

	@PutMapping("/updateBook/{id}")
	public ResponseEntity<?> updateBook(@PathVariable("id") int id, @RequestBody BookDTO bookDTO) {
		this.bookservice.updateBook(id, bookDTO);
		return new ResponseEntity<>(HttpStatus.OK);

	}

	@DeleteMapping("/deleteBook/{id}")
	public ResponseEntity<?> deleteBook(@PathVariable("id") int id, @RequestBody BookDTO bookDTO) {
		this.bookservice.delteBookById(id, bookDTO);
		return new ResponseEntity<>(HttpStatus.OK);

	}

	@DeleteMapping("/deleteAllBook")
	public ResponseEntity<?> deleteAllBook(@RequestBody BookDTO bookDTO) {
		this.bookservice.deleteAllBook(bookDTO);
		return new ResponseEntity<>(HttpStatus.OK);

	}
}
