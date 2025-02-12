package com.j2ee.smartexpensetracker.controller;

import com.j2ee.smartexpensetracker.model.Expense;
import com.j2ee.smartexpensetracker.model.ExpenseCategory;
import com.j2ee.smartexpensetracker.repository.CategoryRepository;
import com.j2ee.smartexpensetracker.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class ExpenseController {

//    @Autowired
//    private ExpenseRepository expenseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private final ExpenseRepository expenseRepository;

    public ExpenseController(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

//    @GetMapping("/")
//    public String showDashboard(Model model) {
//
//    }

    @GetMapping("/expenses")
    public String listExpenses(Model model) {
        // Get all expenses
        List<Expense> expenses = expenseRepository.findAll();

        // Calculate total expenses by category
        Map<String, Double> expensesByCategory = expenses.stream()
                .collect(Collectors.groupingBy(
                        expense -> expense.getCategory().getDisplayName(), // Corrected to use category name
                        Collectors.summingDouble(Expense::getAmount)
                ));




        // Visualization part

       // List<Expense> expenses = expenseRepository.findAll();

        // Calculate total expense per category
        Map<String, Double> categoryAmount = new HashMap<>();
        for (Expense e : expenses) {
            categoryAmount.put(e.getCategory().name(),
                    categoryAmount.getOrDefault(e.getCategory().name(), 0.0) + e.getAmount());
        }

        // Calculate total expense per month
        Map<String, Double> monthlyExpense = new TreeMap<>();
        for (Expense e : expenses) {
            String month = e.getDate().getMonth().name();
            monthlyExpense.put(month, monthlyExpense.getOrDefault(month, 0.0) + e.getAmount());
        }

        // Sort months in order
        List<String> monthNames = Arrays.stream(Month.values())
                .map(Enum::name)
                .collect(Collectors.toList());

        Map<String, Double> sortedMonthlyExpense = new LinkedHashMap<>();
        for (String month : monthNames) {
            if (monthlyExpense.containsKey(month)) {
                sortedMonthlyExpense.put(month, monthlyExpense.get(month));
            }
        }

        double totalExpenses = 0;

        for (Expense e : expenses) {
            totalExpenses += e.getAmount();
        }


        // Add data to the model
//        model.addAttribute("expenses", expenses);
//        model.addAttribute("categories", expensesByCategory.keySet());
//        model.addAttribute("amounts", expensesByCategory.values());


        // Pass data to Thymeleaf
        model.addAttribute("categories", new ArrayList<>(categoryAmount.keySet()));
        model.addAttribute("amounts", new ArrayList<>(categoryAmount.values()));
        model.addAttribute("months", new ArrayList<>(sortedMonthlyExpense.keySet()));
        model.addAttribute("monthlyData", new ArrayList<>(sortedMonthlyExpense.values()));
        model.addAttribute("expenses", expenses);
        model.addAttribute("totalExpenses", (int)totalExpenses);

//        return "dashboard";

        return "expenses";
    }

    // CREATE
    @GetMapping("/add-expense")
    public String showAddForm(Model model) {
        model.addAttribute("expense", new Expense());

        // Add categories and subcategories to the model for the form
        List<ExpenseCategory> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);

        return "add-expense";
    }

    @PostMapping("/add-expense")
    public String addExpense(@ModelAttribute("expense") Expense expense) {
        expenseRepository.save(expense);
        return "redirect:/expenses";
    }

    // EDIT
    @GetMapping("/edit-expense/{id}")
    public String showEditForm(@PathVariable String id, Model model) {
        Expense expense = expenseRepository.findExpenseById(id);
        model.addAttribute("expense", expense);

        // Add categories and subcategories to the model for editing
        List<ExpenseCategory> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);

        return "edit-expense";
    }

    @PostMapping("/edit-expense/{id}")
    public String editExpense(@ModelAttribute("expense") Expense expense) {
        expenseRepository.save(expense);
        return "redirect:/expenses";
    }

    // DELETE
    @GetMapping("/delete-expense/{id}")
    public String deleteExpense(@PathVariable String id) {
        Expense expense = expenseRepository.findExpenseById(id);
        if (expense != null) {
            expenseRepository.delete(expense);
        }
        return "redirect:/expenses";
    }
}



//package com.j2ee.smartexpensetracker.controller;
//
//import com.j2ee.smartexpensetracker.model.Expense;
//import com.j2ee.smartexpensetracker.repository.ExpenseRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Controller
//public class ExpenseController {
//
//    @Autowired
//    private ExpenseRepository expenseRepository;
//
//
//    @GetMapping("/expenses")
//    public String listExpenses(Model model) {
//        // Get all expenses
//        List<Expense> expenses = expenseRepository.findAll();
//
//        // Calculate total expenses by category
//        Map<String, Double> expensesByCategory = expenses.stream()
//                .collect(Collectors.groupingBy(Expense::getCategory, Collectors.summingDouble(Expense::getAmount)));
//
//        // Add data to the model
//        model.addAttribute("expenses", expenses);
//        model.addAttribute("categories", expensesByCategory.keySet());
//        model.addAttribute("amounts", expensesByCategory.values());
//
//        return "expenses";
//    }
//
//
//    // READ ALL
////    @GetMapping("/expenses")
////    public String expenses(Model model) {
////        model.addAttribute("expenses", expenseRepository.findAll());
////        return "expenses";
////    }
//
//    // CREATE
//    @GetMapping("/add-expense")
//    public String showAddForm(Model model) {
//        model.addAttribute("expense", new Expense());
//        return "add-expense";
//    }
//
//    @PostMapping("/add-expense")
//    public String addExpense(@ModelAttribute("expense") Expense expense) {
//        expenseRepository.save(expense);
//        return "redirect:/expenses";
//    }
//
//
//    // EDIT
//    @GetMapping("/edit-expense/{id}")
//    public String showEditForm(@PathVariable String id, Model model) {
//        Expense expense = expenseRepository.findExpenseById(id);
//        model.addAttribute("expense", expense);
//        return "edit-expense";
//    }
//
//    @PostMapping("/edit-expense/{id}")
//    public String editExpense(@ModelAttribute("expense") Expense expense) {
//        expenseRepository.save(expense);
//        return "redirect:/expenses";
//    }
//
//
//    // DELETE
//    @GetMapping("delete-expense/{id}")
//    public String deleteExpense(@ModelAttribute("expense") Expense expense) {
//        expenseRepository.delete(expense);
//        return "redirect:/expenses";
//    }
//
//}
