package com.adesso.commentator.bookstore.application.service;

import com.adesso.commentator.bookstore.MockData;
import com.adesso.commentator.bookstore.application.port.out.ReadBooksPort;
import com.adesso.commentator.bookstore.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class ReadBooksServiceTest {

    @Mock
    public ReadBooksPort readBooksPort;

    @InjectMocks
    public ReadBooksService readBooksService;

    @Test
    public void readBookById() {
        Book book = MockData.getMockedBook();
        when(readBooksPort.readBookById(book.getId())).thenReturn(book);

        assertThat(readBooksService.readBookById(book.getId())).isEqualTo(book);
    }

    @Test
    public void readAllBooks() {
        List<Book> books = MockData.getMockedBooks();
        when(readBooksPort.readAllBooks()).thenReturn(books);

        assertThat(readBooksService.readAllBooks()).containsAll(books);
    }

    @Test
    public void existsBookByTitleAndAuthor_True() {
        String title = "Hello World", author = "Programmer";

        when(readBooksPort.existsBookByTitleAndAuthor(title, author)).thenReturn(true);

        assertThat(readBooksService.existsBookByTitleAndAuthor(title, author)).isTrue();
    }

    @Test
    public void existsBookByTitleAndAuthor_False() {
        String title = "Hello World", author = "Programmer";

        when(readBooksPort.existsBookByTitleAndAuthor(title, author)).thenReturn(false);

        assertThat(readBooksService.existsBookByTitleAndAuthor(title, author)).isFalse();
    }
}
