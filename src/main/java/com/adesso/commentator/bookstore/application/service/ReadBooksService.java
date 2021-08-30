package com.adesso.commentator.bookstore.application.service;

import com.adesso.commentator.bookstore.application.port.in.query.ReadBooksQuery;
import com.adesso.commentator.bookstore.application.port.out.ReadBooksPort;
import com.adesso.commentator.bookstore.domain.Book;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ReadBooksService implements ReadBooksQuery {

    private final ReadBooksPort readBooksPort;

    @Override
    public Book readBookById(long id) {
        return readBooksPort.readBookById(id);
    }

    @Override
    public List<Book> readAllBooks() {
        return readBooksPort.readAllBooks();
    }

    @Override
    public boolean existsBookByTitleAndAuthor(String title, String author) {
        return readBooksPort.existsBookByTitleAndAuthor(title, author);
    }
}
