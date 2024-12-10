package com.Files.BookModel.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Files.BookModel.Book;
import com.Files.BookModel.DTOs.BookChangeDto;
import com.Files.BookModel.DTOs.BookPriceChange;
import com.Files.BookModel.DTOs.BookQuantityChange;
import com.Files.BookModel.DTOs.BookRequestDto;
import com.Files.BookModel.DTOs.BookResponseDto;
import com.Files.BookModel.ExceptionHandler.BookNotFoundException;
import com.Files.BookModel.Repos.BookRepo;

 import Mapper.Mapper;
 
@Service
public class BookService {


	@Autowired
	BookRepo bookRepo;

	@Autowired
	Mapper mapper;

	public BookResponseDto add(BookRequestDto bookRequestDto,MultipartFile logo) throws IOException {
		String imageName=logo.getOriginalFilename();


		Book dtoToBook = mapper.DtoToBook(bookRequestDto);
		dtoToBook.setImage(imageName);
		Book save = bookRepo.save(dtoToBook);

		return mapper.bookToDto(save);
	}

	
	public List<BookResponseDto> show() {
		List<Book> all = bookRepo.findAll();
		return all.stream().map(mapper::bookToDto).collect(Collectors.toList());
	}


	public String delete(long id) {
		bookRepo.deleteById(id);
		return "Book Deleted Successfully";
	}

	public String deleteall() {
		bookRepo.deleteAll();
		return "All Books Deleted Successfully";
	}

	public BookResponseDto showbook(long id) throws BookNotFoundException {
	    Book book = bookRepo.findById(id)
	            .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));
	    return mapper.bookToDto(book);
	}


	public BookResponseDto updateBook(long id, BookChangeDto bookChangeDto) throws Exception {
		Book book = bookRepo.findById(id).orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));
		if(book!=null) {
		book.setB_author(bookChangeDto.getB_author());
		book.setB_description(bookChangeDto.getB_description());
		book.setB_name(bookChangeDto.getB_name());
		bookRepo.save(book);
		return mapper.bookToDto(book);
	}else {
		throw new BookNotFoundException("Book not found with id: "+id);		 
	}
	}

	public BookResponseDto updatePrice(long id, BookPriceChange bookPriceChange) throws Exception {
		Book book = bookRepo.findById(id).orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));
		if(book!=null) {
		book.setB_price(bookPriceChange.getB_price());
		return mapper.bookToDto(book);
	}else {
		throw new BookNotFoundException("Book not found with id: "+id);		 
	}
	}

	public BookResponseDto updateQuantity(long id, long bookQuantityChange) throws Exception {
	    Book book = bookRepo.findById(id).orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));
	    if(book!=null) {
	    	 book.setB_quantity(bookQuantityChange);
	 	    bookRepo.save(book);
	 	    return mapper.bookToDto(book);
 		}else {
			throw new BookNotFoundException("Book not found with id: "+id);		 
		}
		}




	public BookResponseDto updateQuantitybody(long id, BookQuantityChange bookQuantityChange) throws Exception {
		Book book = bookRepo.findById(id).orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));
		if(book!=null) {
		book.setB_quantity(bookQuantityChange.getB_quantity());
		bookRepo.save(book);
		return mapper.bookToDto(book);
	}else {
		throw new BookNotFoundException("Book not found with id: "+id);		 
	}
	}}

