package com.yog.electronicstore.Dtos;

import com.yog.electronicstore.Entity.CartItem;

import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CartDto {
    private String cartId;
    private Date createdAt;
    @OneToOne
    private UserDto user;
    @OneToMany(mappedBy = "cart")
    private List<CartItem> items=new ArrayList<>();
}
