package com.adesso.commentator.bookstore.adapter.in.web;

import com.adesso.commentator.bookstore.MockData;
import com.adesso.commentator.bookstore.adapter.out.Mapper;
import com.adesso.commentator.bookstore.adapter.out.repositories.BillRepository;
import com.adesso.commentator.bookstore.adapter.out.repositories.BillingBookRepository;
import com.adesso.commentator.bookstore.adapter.out.repositories.BookRepository;
import com.adesso.commentator.bookstore.domain.Bill;
import com.adesso.commentator.bookstore.domain.Book;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class PageControllerIntegrationTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private BillingBookRepository billingBookRepository;


    @BeforeEach
    public void init() {
        List<Book> books = MockData.getMockedBooks();
        bookRepository.saveAll(books.stream().map(Mapper::toDto).collect(Collectors.toList()));

        List<Bill> bills = MockData.getMockedBills();
        List<com.adesso.commentator.bookstore.adapter.out.entities.Bill> billdto =bills.stream().map(Mapper::toDto).collect(Collectors.toList());
        billingBookRepository.saveAll(MockData.getMockedBillingBooks().stream().map(Mapper::toDto).collect(Collectors.toList()));
        billRepository.saveAll(billdto);
    }

    @AfterEach
    public void cleanUp(@Autowired EntityManager em) {
        bookRepository.deleteAll();
        billRepository.deleteAll();
        billingBookRepository.deleteAll();
        em.createNativeQuery("ALTER TABLE BILL ALTER COLUMN BILL_ID RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE BOOK ALTER COLUMN BOOK_ID RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE BILLING_BOOK ALTER COLUMN BILLING_BOOK_ID RESTART WITH 1").executeUpdate();
    }

    @Test
    public void index() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<h2>BookStore</h2>")))
                .andExpect(content().string(containsString("ti")))
                .andExpect(content().string(containsString("Hello World")))
                .andExpect(content().string(containsString("Error 418")))
                .andExpect(content().string(containsString("au")))
                .andExpect(content().string(containsString("404")))
                .andExpect(content().string(containsString("1999")))
                .andExpect(content().string(containsString("42")));

    }

    @Test
    public void accounting() throws Exception {
        mvc.perform(get("/accounting"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<h2>Accounting</h2>")))
                .andExpect(content().string(containsString("ti by au")))
                .andExpect(content().string(containsString("1.0")))
                .andExpect(content().string(containsString("2")));
    }

    @Test
    public void addBookGet() throws Exception {
        mvc.perform(get("/add_book"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Book Title")))
                .andExpect(content().string(containsString("Book Author")))
                .andExpect(content().string(containsString("Book Price")));


    }

    @Test
    public void addBookPost_valid() throws Exception {
       mvc.perform(post("/add_book")
                        .contentType("application/x-www-form-urlencoded")
                        .content("title=abc&author=def&publicationYear=2020&price=25.0&stockAmount=12"))
                .andExpect(status().is(302));

        assertThat(bookRepository.count()).isEqualTo(4L);
        assertThat(bookRepository.findById(4L)).isPresent();
        assertThat(bookRepository.findById(4L).get())
                .isEqualTo(new com.adesso.commentator.bookstore.adapter.out.entities.Book(4L, "abc", "def", 25.0, 2020, 12));
    }

    @Test
    public void addBookPost_authorTitleNotUnique() throws Exception {
        mvc.perform(post("/add_book")
                        .contentType("application/x-www-form-urlencoded")
                        .content("title=ti&author=au&publicationYear=2020&price=25.0&stockAmount=12"))
                .andExpect(status().is(302));

        assertThat(bookRepository.count()).isEqualTo(3L);
        assertThat(bookRepository.findById(4L)).isEmpty();
    }

    @Test
    public void bookById() throws Exception {
        mvc.perform(get("/book/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("ti")))
                .andExpect(content().string(containsString("written by au")))
                .andExpect(content().string(containsString("1.0")))
                .andExpect(content().string(containsString("Only 4 left")));

    }

}
