package com.smsv2.smsv2.serviceimpl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.smsv2.smsv2.DTO.BookDTO;
import com.smsv2.smsv2.dao.BookDao;
import com.smsv2.smsv2.dao.DeptDao;
import com.smsv2.smsv2.dao.SemDao;
import com.smsv2.smsv2.dao.SubDao;
import com.smsv2.smsv2.entity.Attendence;
import com.smsv2.smsv2.entity.Book;
import com.smsv2.smsv2.entity.Dept;
import com.smsv2.smsv2.entity.Sem;
import com.smsv2.smsv2.entity.Sub;
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

	@Override
	public List<Book> getAllBook() {
		return bookdao.findAll();
	}

	@Override
	public Optional<Book> getAllBookById(int id) {
		Optional<Book> book = bookdao.findById(id);
		if (book.isEmpty()) {
			throw new ResourceNotFoundException("book","id",id);
		}
		return book;
	}

	@Override
	public List<Book> getAllBookBySubId(int deptId) {

		return bookdao.findBySubId(deptId);
	}

	@Override
	public void addBook(BookDTO bookDTO) {
		Sub sub = subdao.findById(bookDTO.getSubId()).orElseThrow(() -> new ResourceNotFoundException("sub","id",bookDTO.getSubId()));
		Book book = new Book();
		book.setSub(sub);
		book.setLink(bookDTO.getLink());
		book.setName(bookDTO.getName());
		book.setDeptname(sub.getDept().getDeptname());
		book.setSemname(sub.getSem().getSemname());
		book.setSubname(sub.getSubname());
		bookdao.save(book);

	}

	@Override
	public void updateBook(int id, BookDTO bookDTO) {
		Book existBook = bookdao.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("book","id",id));

		existBook.setLink(bookDTO.getLink());
		existBook.setName(bookDTO.getName());
		bookdao.save(existBook);

	}

	@Override
	public void delteBookById(int id) {
		Book existBook = bookdao.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("book","id",id));
		bookdao.delete(existBook);

	}

	@Override
	public void deleteAllBook() {
		bookdao.deleteAll();

	}

	@Override
	public String uploadFile(int id, MultipartFile file) {
		try {
			Book book = bookdao.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("book","id",id));
			book.setPdf(file.getBytes()); // Assuming 'pdf' field can hold PDF bytes
			bookdao.save(book);
			return "File uploaded successfully";
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
