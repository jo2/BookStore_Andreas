package com.adesso.commentator.bookstore.adapter.in.web;

import com.adesso.commentator.bookstore.application.port.in.query.ReadBillsQuery;
import com.adesso.commentator.bookstore.application.port.in.query.ReadBooksQuery;
import com.adesso.commentator.bookstore.application.port.in.usecase.BuyBooksUseCase;
import com.adesso.commentator.bookstore.application.port.in.usecase.CreateBookUseCase;
import com.adesso.commentator.bookstore.application.port.in.usecase.DeleteBookUseCase;
import com.adesso.commentator.bookstore.application.port.in.usecase.EditBookUseCase;
import com.adesso.commentator.bookstore.domain.Bill;
import com.adesso.commentator.bookstore.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/")
public class ApiController {

    @Autowired
    private ReadBooksQuery readBooksQuery;

    @Autowired
    private ReadBillsQuery readBillsQuery;

    @Autowired
    private BuyBooksUseCase buyBooksUseCase;

    @Autowired
    private CreateBookUseCase createBookUseCase;

    @Autowired
    private DeleteBookUseCase deleteBookUseCase;

    @Autowired
    private EditBookUseCase editBookUseCase;

    @GetMapping("books/all")
    public List<Book> getAllBooks() {
        return readBooksQuery.readAllBooks();
    }

    @GetMapping("book/{id}")
    public Book getBookById(@PathVariable("id") long id) {
        return readBooksQuery.readBookById(id);
    }

    @PostMapping("book/create")
    public Book createBook(@RequestBody @Valid Book book) {
        return createBookUseCase.createBook(book);
    }

    @PostMapping("books/edit")
    public Book editBook(@RequestBody @Valid Book book) {
        return editBookUseCase.editBook(book);
    }

    @PostMapping("bill/create")
    public Bill buyBooks(@RequestBody @Valid Bill bill) {
        return buyBooksUseCase.buyBooks(bill);
    }

    @GetMapping("bill/all")
    public List<Bill> getAllBills() {
        return readBillsQuery.readAllBills();
    }

}
