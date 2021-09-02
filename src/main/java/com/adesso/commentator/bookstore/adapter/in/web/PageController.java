package com.adesso.commentator.bookstore.adapter.in.web;

import com.adesso.commentator.bookstore.adapter.in.dto.BillBookDto;
import com.adesso.commentator.bookstore.adapter.in.dto.BillDto;
import com.adesso.commentator.bookstore.application.port.in.mapper.Mapper;
import com.adesso.commentator.bookstore.application.port.in.query.GetCartQuery;
import com.adesso.commentator.bookstore.application.port.in.query.ReadBillsQuery;
import com.adesso.commentator.bookstore.application.port.in.query.ReadBooksQuery;
import com.adesso.commentator.bookstore.application.port.in.usecase.*;
import com.adesso.commentator.bookstore.domain.Bill;
import com.adesso.commentator.bookstore.domain.BillingBook;
import com.adesso.commentator.bookstore.domain.Book;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
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
    ModifyCartUseCase modifyCartUseCase;

    @Autowired
    ClearCartUseCase clearCartUseCase;

    @Autowired
    Mapper mapper;

    Logger log = LoggerFactory.getLogger(PageController.class);

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
            attributes.addFlashAttribute("error", e.getLocalizedMessage());
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
        try {
            editBookUseCase.editBook(book);
            return "redirect:/index";
        } catch(Exception e) {
            attributes.addFlashAttribute("book", book);
            attributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/book/edit/"+id;
        }
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

    @PostMapping("/cart/modify")
    public String modifyCart(@ModelAttribute @Valid BillBookDto book, Model model, HttpSession session) {
        log.info(book.toString());
        modifyCartUseCase.modifyCart(book, session.getId());

        return "redirect:/shopping_cart";
    }

    @GetMapping({"cart", "shopping_cart", "/shopping_cart.html"})
    public String shoppingCart(Model model, HttpSession session) {
        List<BillBookDto> cart = getCartQuery.getCart(session.getId());
        if (cart != null) model.addAttribute("cart", new ShoppingCart(cart.stream().map(mapper::toDomain).collect(Collectors.toList())));
        return "shopping_cart";
    }

    @PostMapping("/cart/buy")
    public String buy(HttpSession session) {
        BillDto bill = new BillDto();
        bill.setBooks(getCartQuery.getCart(session.getId()));
        buyBooksUseCase.buyBooks(mapper.toDomain(bill));
        return "redirect:/accounting";
    }

    @PostMapping("/cart/clear")
    public String clearCart(HttpSession session) {
        clearCartUseCase.clearCart(session.getId());
        return "redirect:/cart";
    }

    @Data
    private static class ShoppingCart {

        List<BillingBook> books;
        double totalAmount;

        public ShoppingCart(List<BillingBook> books) {
            this.books = books;
            this.totalAmount = books.stream().mapToDouble(BillingBook::getTotalAmount).sum();
        }
    }

}
