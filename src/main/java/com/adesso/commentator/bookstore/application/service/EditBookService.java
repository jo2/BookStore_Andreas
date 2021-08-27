package com.adesso.commentator.bookstore.application.service;

import com.adesso.commentator.bookstore.application.port.in.usecase.EditBookUseCase;
import com.adesso.commentator.bookstore.application.port.out.EditBookPort;
import com.adesso.commentator.bookstore.domain.Book;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
@AllArgsConstructor
public class EditBookService implements EditBookUseCase {

    private final EditBookPort editBookPort;

    @Override
    public Book editBook(@Valid Book book) {
        return editBookPort.editBook(book);
    }
}
