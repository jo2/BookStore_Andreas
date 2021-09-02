package com.adesso.commentator.bookstore.adapter.out;

import com.adesso.commentator.bookstore.adapter.in.dto.BillBookDto;
import com.adesso.commentator.bookstore.application.port.out.CartPort;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component("CartAdapter")
@AllArgsConstructor
public class CartAdapter implements CartPort {

    private final HashMap<String, List<BillBookDto>> carts;

    public CartAdapter() {
        carts = new HashMap<>();
    }

    @Override
    public void addToCart(BillBookDto book, String user) {
        if (!carts.containsKey(user)){
            carts.put(user, new ArrayList<>());
        }
        carts.get(user).add(book);
    }

    @Override
    public void removeFromCart(BillBookDto book, String user) {
        carts.get(user).removeIf((b) -> book.getId().equals(b.getId()));
    }

    public List<BillBookDto> getCart(String user) {
        return carts.get(user);
    }

    @Override
    public void clearCart(String user) {
        carts.remove(user);
    }
}
