package com.adesso.commentator.bookstore.application.service;

import com.adesso.commentator.bookstore.application.port.in.usecase.EditBookUseCase;
import com.adesso.commentator.bookstore.application.port.out.EditBookPort;
import com.adesso.commentator.bookstore.application.port.out.ReadBooksPort;
import com.adesso.commentator.bookstore.domain.Book;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
@AllArgsConstructor
public class EditBookService implements EditBookUseCase {

    private final EditBookPort editBookPort;
    private final ReadBooksPort readBooksPort;

    @Override
    public Book editBook(@Valid Book book) {
        if (readBooksPort.readIdByTitleAndAuthor(book.getTitle(), book.getAuthor()) != book.getId()) {
            throw new IllegalArgumentException("Combination of Title and Author must be unique");
        }
        return editBookPort.editBook(book);
    }
}
