/**
package com.yog.electronicstore.Service;


import com.yog.electronicstore.Dtos.CreateOrderRequest;
import com.yog.electronicstore.Dtos.PageableResponse;
import org.springframework.data.domain.jaxb.SpringDataJaxb;

import java.util.List;

public interface OrderService  {
    SpringDataJaxb.OrderDto createOrder(CreateOrderRequest createOrderRequest);
    //remove order
    void removeOrder(String orderId);
    //get All orders of specific user
    List<SpringDataJaxb.OrderDto> getAllOrdersOfUser(String userId);
    //get All orders
    PageableResponse<SpringDataJaxb.OrderDto> getAllOrders(int pageNumber , int pageSize, String sortBy , String sortDir);
    //update order method assignment
}
 **/

