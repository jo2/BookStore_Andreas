package com.adesso.commentator.bookstore.adapter.in.web;

import com.adesso.commentator.bookstore.application.port.in.query.ReadBillsQuery;
import com.adesso.commentator.bookstore.application.port.in.query.ReadBooksQuery;
import com.adesso.commentator.bookstore.application.port.in.usecase.BuyBooksUseCase;
import com.adesso.commentator.bookstore.application.port.in.usecase.CreateBookUseCase;
import com.adesso.commentator.bookstore.application.port.in.usecase.DeleteBookUseCase;
import com.adesso.commentator.bookstore.application.port.in.usecase.EditBookUseCase;
import com.adesso.commentator.bookstore.application.service.ReadBooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

    @GetMapping({"/index.html", "/index"})
    public String homepage(Model model) {
        model.addAttribute("allBooks",readBooksQuery.readAllBooks());
        return "index";
    }

    @GetMapping({"/accounting.html", "/accounting"})
    public String accounting(Model model) {
        model.addAttribute("allBills", readBillsQuery.readAllBills());
        return "accounting";
    }

}
