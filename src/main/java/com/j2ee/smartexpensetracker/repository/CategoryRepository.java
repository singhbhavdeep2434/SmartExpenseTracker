package com.j2ee.smartexpensetracker.repository;

import com.j2ee.smartexpensetracker.model.ExpenseCategory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends MongoRepository<ExpenseCategory, String> {
}
