package com.adesso.commentator.bookstore.adapter.out;

import com.adesso.commentator.bookstore.adapter.in.dto.BillBookDto;
import com.adesso.commentator.bookstore.application.port.out.CartPort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component("CartAdapter")
public class CartAdapter implements CartPort {

    private final HashMap<String, List<BillBookDto>> carts = new HashMap<>();

    @Override
    public void addToCart(BillBookDto book, String user) {
        if (!carts.containsKey(user)){
            carts.put(user, new ArrayList<>());
        }
        carts.get(user).add(book);
    }

    @Override
    public void removeFromCart(BillBookDto book, String user) {
        carts.get(user).remove(book);
    }

    public List<BillBookDto> getCart(String user) {
        return carts.get(user);
    }
}
