/**
package com.yog.electronicstore.Dtos;




/**

 import lombok.*;
 import javax.persistence.*;
 import javax.validation.constraints.NotBlank;
 import javax.validation.constraints.NotNull;
 import javax.validation.constraints.Size;

 @Builder
 @Setter
 @Getter
 @AllArgsConstructor
 @NoArgsConstructor
 public class OrderItemDto  {

 private Integer orderItemId;

 @NotBlank(message = " orderItem quantity is required .....")
 private int quantity;

 private int totalPrize;

 private Product product;
 }
 **/

