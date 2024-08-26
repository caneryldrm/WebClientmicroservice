package com.example.categoryMicroService.controller;

import com.example.categoryMicroService.Service.CategoryService;
import com.example.categoryMicroService.model.CategoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/{categoryId}")
    private ResponseEntity<CategoryResponse> getCategoryDetails(@PathVariable("categoryId") int categoryId) throws Exception {
        CategoryResponse category = categoryService.getCategoryById(categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(category);
    }

    @GetMapping("/{categoryId}/validate")
    private ResponseEntity<Boolean> validateCategory(@PathVariable("categoryId") int categoryId) {
        boolean exists = categoryService.categoryExists(categoryId);
        return ResponseEntity.status(HttpStatus.OK).body(exists);
    }

}
