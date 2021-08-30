package com.adesso.commentator.bookstore.application.service;

import com.adesso.commentator.bookstore.MockData;
import com.adesso.commentator.bookstore.application.port.out.CreateBookPort;
import com.adesso.commentator.bookstore.application.port.out.ReadBooksPort;
import com.adesso.commentator.bookstore.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class CreateBookServiceTest {

    @Mock
    public CreateBookPort createBookPort;

    @Mock
    public ReadBooksPort readBooksPort;

    @InjectMocks
    public CreateBookService createBookService;

    @Test
    public void createBook_valid() {
        Book book = MockData.getMockedBook();

        when(readBooksPort.existsBookByTitleAndAuthor("ti", "au")).thenReturn(false);

        when(createBookPort.createBook(book)).thenReturn(book);

        assertThat(createBookService.createBook(book)).isEqualTo(book);
    }

    @Test
    public void createBook_duplicateBook() {
        Book book = MockData.getMockedBook();

        when(readBooksPort.existsBookByTitleAndAuthor("ti", "au")).thenReturn(true);

        when(createBookPort.createBook(book)).thenReturn(book);

        assertThatThrownBy(() -> createBookService.createBook(book))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("The combination of author and title must be unique");

    }
}
