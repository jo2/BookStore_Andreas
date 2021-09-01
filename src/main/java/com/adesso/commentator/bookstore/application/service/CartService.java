package com.adesso.commentator.bookstore.application.service;

import com.adesso.commentator.bookstore.adapter.in.dto.BillBookDto;
import com.adesso.commentator.bookstore.application.port.in.query.GetCartQuery;
import com.adesso.commentator.bookstore.application.port.in.usecase.AddToCartUseCase;
import com.adesso.commentator.bookstore.application.port.in.usecase.RemoveFromCartUseCase;
import com.adesso.commentator.bookstore.application.port.out.CartPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService implements AddToCartUseCase, RemoveFromCartUseCase, GetCartQuery {
    @Autowired
    private CartPort cartPort;

    @Override
    public void addToCart(BillBookDto book, String user) {
        cartPort.addToCart(book, user);
    }

    @Override
    public void removeFromCart(BillBookDto book, String user) {
        cartPort.removeFromCart(book, user);
    }

    @Override
    public List<BillBookDto> getCart(String user) {
        return cartPort.getCart(user);
    }
}
