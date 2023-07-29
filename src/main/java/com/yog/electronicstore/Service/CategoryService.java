package com.yog.electronicstore.Service;
import com.yog.electronicstore.Dtos.CategoryDto;
import com.yog.electronicstore.Dtos.PageableResponse;

import java.util.List;

public interface CategoryService {

    CategoryDto create(CategoryDto categoryDto);

    CategoryDto getcategory(String categoryId);

    CategoryDto updatecategory(CategoryDto categoryDto, String categoryId);

    PageableResponse<CategoryDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir);

    void deletecategory(String categoryId);

}
