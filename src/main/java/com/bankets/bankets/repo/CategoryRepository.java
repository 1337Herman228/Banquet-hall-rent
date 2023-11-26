package com.bankets.bankets.repo;

import com.bankets.bankets.models.Categories;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Categories,Long> {
}
