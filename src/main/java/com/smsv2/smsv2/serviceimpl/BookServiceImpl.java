package com.smsv2.smsv2.serviceimpl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.smsv2.smsv2.DTO.BookDTO;
import com.smsv2.smsv2.dao.BookDao;
import com.smsv2.smsv2.dao.DeptDao;
import com.smsv2.smsv2.dao.SemDao;
import com.smsv2.smsv2.dao.SubDao;
import com.smsv2.smsv2.dao.TeacherDao;
import com.smsv2.smsv2.entity.Attendence;
import com.smsv2.smsv2.entity.Book;
import com.smsv2.smsv2.entity.Dept;
import com.smsv2.smsv2.entity.Sem;
import com.smsv2.smsv2.entity.Sub;
import com.smsv2.smsv2.entity.Teacher;
import com.smsv2.smsv2.exception.ResourceBadRequestException;
import com.smsv2.smsv2.exception.ResourceNotFoundException;
import com.smsv2.smsv2.service.BookService;

@Transactional
@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookDao bookdao;

	@Autowired
	private DeptDao deptdao;
	@Autowired
	private SubDao subdao;
	@Autowired
	private TeacherDao teacherdao;

	@Override
	public ResponseEntity<List<Book>> getAllBook() {
		List<Book> book= bookdao.findAll();
		return new ResponseEntity<>(book,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Optional<Book>> getAllBookById(int id) {
		Optional<Book> book = bookdao.findById(id);
		if (book.isEmpty()) {
			throw new ResourceNotFoundException("book","id",id);
		}
		return new ResponseEntity<>(book,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<Book>> getAllBookBySubId(int subid) {

		List<Book> book= bookdao.findBySubId(subid);
		return new ResponseEntity<>(book,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> addBook(BookDTO bookDTO) {
		Sub sub = subdao.findById(bookDTO.getSubId()).orElseThrow(() -> new ResourceNotFoundException("sub","id",bookDTO.getSubId()));
		Teacher teacher=teacherdao.findById(bookDTO.getUserid())
				.orElseThrow(() -> new ResourceNotFoundException("teacher", "id", bookDTO.getUserid()));
			Book book = new Book();
			book.setSub(sub);
			book.setLink(bookDTO.getLink());
			book.setName(bookDTO.getName());
			book.setDeptname(sub.getDept().getDeptname());
			book.setSemname(sub.getSem().getSemname());
			book.setSubname(sub.getSubname());
			bookdao.save(book);
			return new ResponseEntity<>(book,HttpStatus.CREATED);
		

	}

	@Override
	public ResponseEntity<?> updateBook(int id, BookDTO bookDTO) {
		Book existBook = bookdao.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("book","id",id));
		Teacher teacher=teacherdao.findById(bookDTO.getUserid())
				.orElseThrow(() -> new ResourceNotFoundException("teacher", "id", bookDTO.getUserid()));
			existBook.setLink(bookDTO.getLink());
			existBook.setName(bookDTO.getName());
			bookdao.save(existBook);
		
			return new ResponseEntity<>(existBook,HttpStatus.OK);

	}

	@Override
	public ResponseEntity<?> delteBookById(int id,BookDTO bookDTO) {
		Book existBook = bookdao.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("book","id",id));
		Teacher teacher=teacherdao.findById(bookDTO.getUserid())
				.orElseThrow(() -> new ResourceNotFoundException("teacher", "id", bookDTO.getUserid()));
			bookdao.delete(existBook);
			return new ResponseEntity<>(HttpStatus.OK);
		
		
	}

	@Override
	public ResponseEntity<?> deleteAllBook(BookDTO bookDTO) {
		Teacher teacher=teacherdao.findById(bookDTO.getUserid())
				.orElseThrow(() -> new ResourceNotFoundException("teacher", "id", bookDTO.getUserid()));
		if (teacher.getRole().equals("pic") ) {
			bookdao.deleteAll();
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			throw new ResourceBadRequestException("your role should be teacher or pic or hod");
		}
		

	}

	@Override
	public ResponseEntity<String> uploadFile(int id, MultipartFile file) {
		try {
			Book book = bookdao.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("book","id",id));
			book.setPdf(file.getBytes()); // Assuming 'pdf' field can hold PDF bytes
			bookdao.save(book);
			String msg= "File uploaded successfully";
			return new ResponseEntity<>(msg,HttpStatus.OK);
		} catch (IOException e) {
			throw new RuntimeException("Failed to upload file", e);
		}
	}

	@Override
	public byte[] downloadFile(int id) {
		Book book = bookdao.findById(id).orElseThrow(() -> new ResourceNotFoundException("book","id",id));
		return book.getPdf();
		
	}
}
