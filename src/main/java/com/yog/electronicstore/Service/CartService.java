package com.yog.electronicstore.Service;

import com.yog.electronicstore.Dtos.AddItemTOCartRequest;
import com.yog.electronicstore.Dtos.CartDto;

public interface CartService {
    CartDto addItemToCart(String userId, AddItemTOCartRequest request);
    void removeItemfromCart(String userId, int cartItem);
    void clearCart(String userId);
    CartDto getCartByUser(String userId);

}