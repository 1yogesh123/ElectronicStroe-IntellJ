package com.yog.electronicstore.Service;


import com.yog.electronicstore.Dtos.PageableResponse;
import com.yog.electronicstore.Dtos.ProductDto;

import java.util.List;

public interface ProductService {

    ProductDto create(ProductDto productDto);

    ProductDto update(ProductDto productDto, String productId);

    void delete(String productId);
    ProductDto get(String productId);
    PageableResponse<ProductDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir);
    PageableResponse<ProductDto> getAllLive(int pageNumber,int pageSize,String sortBy,String sortDir);

    PageableResponse<ProductDto> searchByTitle(String subTitle,int pageNumber,int pageSize,String sortBy,String sortDir);



}
