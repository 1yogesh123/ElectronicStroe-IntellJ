package com.yog.electronicstore.Repository;

import com.yog.electronicstore.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category,String> {
}

