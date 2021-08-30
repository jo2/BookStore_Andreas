package com.adesso.commentator.bookstore.application.service;

import com.adesso.commentator.bookstore.application.port.out.DeleteBookPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class DeleteBookServiceTest {

    @Mock
    public DeleteBookPort deleteBookPort;

    @InjectMocks
    public DeleteBookService deleteBookService;

    @Test
    public void deleteBookById() {
        deleteBookService.deleteBookById(1);

        verify(deleteBookPort).deleteBookById(1);
    }

}
