package com.Files.BookModel.Repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Files.BookModel.Book;

@Repository
public interface BookRepo extends JpaRepository<Book, Long>{

}
