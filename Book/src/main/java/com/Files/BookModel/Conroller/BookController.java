package com.Files.BookModel.Conroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Files.BookModel.DTOs.BookChangeDto;
import com.Files.BookModel.DTOs.BookPriceChange;
import com.Files.BookModel.DTOs.BookQuantityChange;
import com.Files.BookModel.DTOs.BookRequestDto;
import com.Files.BookModel.DTOs.BookResponseDto;
import com.Files.BookModel.Service.BookService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/books")
public class BookController {

	@Autowired
	BookService bookService;

	@GetMapping
	public ResponseEntity<String> Home(@RequestHeader("Authorization") String authHeader) {
		return new ResponseEntity<String>("Hi user you can acess books now",HttpStatus.OK);
	}

	@PostMapping(value = "/add", consumes = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE, produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BookResponseDto> add(@RequestHeader("Authorization") String authHeader,
			@RequestPart("bookRequestDto") BookRequestDto bookRequestDto,
			@RequestPart("logo") MultipartFile logo) {
		try {

			return new ResponseEntity<>(bookService.add(bookRequestDto, logo), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@GetMapping("/{id}")
	public ResponseEntity<BookResponseDto> showbook(@Valid @PathVariable  long id) throws Exception {

		BookResponseDto response = bookService.showbook(id);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}


	@GetMapping("/show")
	public ResponseEntity<List<BookResponseDto>> show(@RequestHeader("Authorization") String authHeader) {
		return new ResponseEntity<>( bookService.show(),HttpStatus.OK);
	} 

	@PutMapping("/updateBook/{id}")
	public ResponseEntity<BookResponseDto> updateBook(@RequestHeader("Authorization") String authHeader,@PathVariable long id,@RequestBody BookChangeDto bookChangeDto) throws Exception {
		return new ResponseEntity<>(bookService.updateBook(id,bookChangeDto),HttpStatus.OK);
	}

	@PutMapping("/updatePrice/{id}")
	public ResponseEntity<BookResponseDto> updatePrice(@RequestHeader("Authorization") String authHeader,@PathVariable long id,@RequestBody BookPriceChange bookPriceChange)throws Exception {
		return new ResponseEntity<>( bookService.updatePrice(id,bookPriceChange),HttpStatus.OK);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<BookResponseDto> updateQuantity(@PathVariable long id,@RequestParam long bookQuantityChange) throws Exception {
		return new ResponseEntity<>( bookService.updateQuantity(id,bookQuantityChange),HttpStatus.OK);
	}

	@PutMapping("/updateQuantity/{id}")
	public ResponseEntity<BookResponseDto> updateQuantityByBody(@RequestHeader("Authorization") String authHeader,@PathVariable long id,@RequestBody BookQuantityChange bookQuantityChange)throws Exception {
		return new ResponseEntity<>( bookService.updateQuantitybody(id,bookQuantityChange),HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> delete(@RequestHeader("Authorization") String authHeader,@PathVariable long id) {
		return new ResponseEntity<String>(bookService.delete(id),HttpStatus.OK);
	}

	@DeleteMapping("/deleteAll")
	public  ResponseEntity<String> deleteall(@RequestHeader("Authorization") String authHeader) {
		return new ResponseEntity<String>( bookService.deleteall(),HttpStatus.OK);
	} 


}
