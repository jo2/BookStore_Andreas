package com.adesso.commentator.bookstore.adapter.in.web;

import com.adesso.commentator.bookstore.adapter.in.dto.BillBookDto;
import com.adesso.commentator.bookstore.application.port.in.mapper.Mapper;
import com.adesso.commentator.bookstore.application.port.in.query.GetCartQuery;
import com.adesso.commentator.bookstore.application.port.in.query.ReadBillsQuery;
import com.adesso.commentator.bookstore.application.port.in.query.ReadBooksQuery;
import com.adesso.commentator.bookstore.application.port.in.usecase.*;
import com.adesso.commentator.bookstore.domain.Bill;
import com.adesso.commentator.bookstore.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PageController {

    @Autowired
    ReadBooksQuery readBooksQuery;

    @Autowired
    ReadBillsQuery readBillsQuery;

    @Autowired
    BuyBooksUseCase buyBooksUseCase;

    @Autowired
    CreateBookUseCase createBookUseCase;

    @Autowired
    DeleteBookUseCase deleteBookUseCase;

    @Autowired
    EditBookUseCase editBookUseCase;

    @Autowired
    AddToCartUseCase addToCartUseCase;

    @Autowired
    RemoveFromCartUseCase removeFromCartUseCase;

    @Autowired
    GetCartQuery getCartQuery;

    @Autowired
    Mapper mapper;

    @GetMapping({"/index.html", "/index", "/"})
    public String homepage(Model model) {
        model.addAttribute("allBooks",readBooksQuery.readAllBooks());
        return "index";
    }

    @GetMapping({"/accounting.html", "/accounting"})
    public String accounting(Model model) {
        model.addAttribute("allBills", readBillsQuery.readAllBills());
        return "accounting";
    }

    @GetMapping({"/add_book.html", "/add_book"})
    public String addBookPage(Model model) {
        return "add_book";
    }

    @PostMapping({"/add_book.html", "/add_book"})
    public String createBook(@ModelAttribute @Valid Book book, Model model, RedirectAttributes attributes) {
        try {
            createBookUseCase.createBook(book);
            model.addAttribute("success", "true");
            return "redirect:/";
        } catch(Exception e) {
            attributes.addFlashAttribute("book", book);
            model.addAttribute("book", book);
            model.addAttribute("error", e.getLocalizedMessage());
        }
        return "redirect:/add_book";
    }

    @GetMapping("book/{id}")
    public String bookById(@PathVariable("id") long id, Model model) {
        model.addAttribute("book", readBooksQuery.readBookById(id));
        return "book";
    }

    @PostMapping("book/delete/{id}")
    public String deleteBook(@PathVariable("id") long id, Model model) {
        deleteBookUseCase.deleteBookById(id);
        return "redirect:/index";
    }

    @GetMapping({"book/edit/{id}"})
    public String getEditBook(@PathVariable("id") long id, Model model, RedirectAttributes attributes) {
        model.addAttribute("book", readBooksQuery.readBookById(id));
        return "edit_book";
    }

    @PostMapping("/book/edit/{id}")
    public String postEditBook(@PathVariable("id") long id, @ModelAttribute @Valid Book book, Model model, RedirectAttributes attributes) {
        book.setId(id);
        editBookUseCase.editBook(book);
        return "redirect:/index";
    }

    @PostMapping("/cart/add/{id}")
    public String shoppingCartAdd(@PathVariable("id") long id, Model model, HttpSession session) {
        addToCartUseCase.addToCart(new BillBookDto(id, 1, 0), session.getId());
        return "redirect:/index";
    }

    @PostMapping("/cart/remove/{id}")
    public String shoppingCartRemove(@PathVariable("id") long id, Model model, HttpSession session) {
        removeFromCartUseCase.removeFromCart(new BillBookDto(id, 1, 0), session.getId());
        return "redirect:/shopping_cart";
    }

    @GetMapping({"cart", "shopping_cart", "/shopping_cart.html"})
    public String shoppingCart(Model model, HttpSession session) {
        List<BillBookDto> cart = getCartQuery.getCart(session.getId());
        model.addAttribute("cart", cart.stream().map(mapper::toDomain).collect(Collectors.toList()));
        return "shopping_cart";
    }

}
