package com.adesso.commentator.bookstore.application.service;

import com.adesso.commentator.bookstore.MockData;
import com.adesso.commentator.bookstore.application.port.out.EditBookPort;
import com.adesso.commentator.bookstore.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class EditBookServiceTest {

    @Mock
    public EditBookPort editBookPort;

    @InjectMocks
    public EditBookService editBookService;

    @Test
    public void editBook() {
        Book book = MockData.getMockedBook();

        editBookService.editBook(book);

        verify(editBookPort).editBook(book);
    }
}
