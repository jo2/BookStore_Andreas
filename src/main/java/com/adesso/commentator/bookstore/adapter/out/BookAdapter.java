package com.adesso.commentator.bookstore.adapter.out;

import com.adesso.commentator.bookstore.adapter.out.repositories.BookRepository;
import com.adesso.commentator.bookstore.application.port.out.CreateBookPort;
import com.adesso.commentator.bookstore.application.port.out.DeleteBookPort;
import com.adesso.commentator.bookstore.application.port.out.ReadBooksPort;
import com.adesso.commentator.bookstore.domain.Book;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component("Book adapter")
public class BookAdapter implements ReadBooksPort, CreateBookPort, DeleteBookPort {

    private final BookRepository repository;

    @Override
    public Book createBook(Book book) {
        return toDomain(repository.save(toDto(book)));
    }

    @Override
    public void deleteBookById(long bookId) {
        repository.deleteById(bookId);
    }

    @Override
    public List<Book> readAllBooks() {
        List<com.adesso.commentator.bookstore.adapter.out.entities.Book>
                books = new ArrayList<>();
        repository.findAll().forEach(books::add);
        return books.stream()
                .map(BookAdapter::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Book readBookById(long bookId) {
        return repository.findById(bookId)
                .map(BookAdapter::toDomain)
                .orElse(null);
    }

    private static Book toDomain(com.adesso.commentator.bookstore.adapter.out.entities.Book bookDto) {
        return new Book(
                bookDto.id,
                bookDto.title,
                bookDto.author,
                bookDto.price,
                bookDto.publicationYear,
                bookDto.stockAmount
        );
    }

    private static com.adesso.commentator.bookstore.adapter.out.entities.Book toDto(Book book) {
        return new com.adesso.commentator.bookstore.adapter.out.entities.Book(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getPrice(),
                book.getPublicationYear(),
                book.getStockAmount()
        );

    }
}
