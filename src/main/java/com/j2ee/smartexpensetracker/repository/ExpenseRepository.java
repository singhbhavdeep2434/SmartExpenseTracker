package com.j2ee.smartexpensetracker.repository;

import com.j2ee.smartexpensetracker.model.Expense;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends MongoRepository<Expense, String> {

    Expense findExpenseById(String id);
}
