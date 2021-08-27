package com.adesso.commentator.bookstore.application.service;

import com.adesso.commentator.bookstore.application.port.in.usecase.CreateBookUseCase;
import com.adesso.commentator.bookstore.application.port.out.CreateBookPort;
import com.adesso.commentator.bookstore.domain.Book;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
@AllArgsConstructor
public class CreateBookService implements CreateBookUseCase {

    private final CreateBookPort createBookPort;

    @Override
    public Book createBook(@Valid Book book) {
        return createBookPort.createBook(book);
    }
}
