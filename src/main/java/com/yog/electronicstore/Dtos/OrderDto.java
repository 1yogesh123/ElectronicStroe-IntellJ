/**
package com.yog.electronicstore.Dtos;



 import lombok.*;
 import javax.persistence.*;
 import javax.validation.constraints.NotBlank;
 import javax.validation.constraints.NotNull;
 import java.util.ArrayList;
 import java.util.Date;
 import java.util.List;

 @Builder
 @Setter
 @Getter
 @AllArgsConstructor
 @NoArgsConstructor
 public class OrderDto {
 private String orderId;
 private String orderStatus = "PENDING";
 private String paymentStatus = "NOTPAID";

 private int orderAmount;
 @NotBlank(message = "Billing Address is required !!")
 private String billingAddress;
 @NotBlank(message = "Billing Phone is required !!")
 private String billingPhone;
 @NotBlank(message = "Billing Name is required !!")
 private String billingName;

 private Date orderDate;

 private Date delieveryDate;

 private UserDto user;

 private List<OrderItemDto> items = new ArrayList<>();
 }

 **/
