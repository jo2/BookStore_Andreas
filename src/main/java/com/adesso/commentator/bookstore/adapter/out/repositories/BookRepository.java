package com.adesso.commentator.bookstore.adapter.out.repositories;

import com.adesso.commentator.bookstore.adapter.out.entities.Book;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Long> {

}
