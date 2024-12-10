package Mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.Files.BookModel.Book;
import com.Files.BookModel.DTOs.BookChangeDto;
import com.Files.BookModel.DTOs.BookPriceChange;
import com.Files.BookModel.DTOs.BookQuantityChange;
import com.Files.BookModel.DTOs.BookRequestDto;
import com.Files.BookModel.DTOs.BookResponseDto;
import com.Files.BookModel.Repos.BookRepo;


@Component
public class Mapper {

	@Autowired
	BookRepo bookRepo;

	public BookResponseDto bookToDto(Book book) {
		BookResponseDto bookResponseDto=new BookResponseDto();
		bookResponseDto.setB_id(book.getB_id());
		bookResponseDto.setB_name(book.getB_name());
		bookResponseDto.setB_author(book.getB_author());
		bookResponseDto.setB_price(book.getB_price());
		bookResponseDto.setB_quantity(book.getB_quantity());
		bookResponseDto.setImage(book.getImage());
		return bookResponseDto;
	}

	public Book DtoToBook(BookRequestDto bookRequestDto) {
		Book book=new Book();
		book.setB_name(bookRequestDto.getB_name());
		book.setB_author(bookRequestDto.getB_author());
		book.setB_description(bookRequestDto.getB_description());
		book.setB_price(bookRequestDto.getB_price());
		book.setB_quantity(bookRequestDto.getB_quantity());
		bookRepo.save(book);

		return book;

	}


	public Book ChangeBook(BookChangeDto bookChangeDto) {
		Book book=new Book();
		book.setB_author(bookChangeDto.getB_author());
		book.setB_description(bookChangeDto.getB_description());
		book.setB_name(bookChangeDto.getB_name());
		bookRepo.save(book);

		return book;
	}

	public Book ChangeQuantity(BookQuantityChange quantityChange) {
		Book book=new Book();
		book.setB_quantity(quantityChange.getB_quantity());
		bookRepo.save(book);

		return book;
	}

	public Book ChangePrice(BookPriceChange priceChange) {
		Book book=new Book();
		book.setB_price(priceChange.getB_price());
		bookRepo.save(book);

		return book;
	}



}
