package com.example.categoryMicroService.Service;


import com.example.categoryMicroService.Repository.CategoryRepository;
import com.example.categoryMicroService.model.Category;
import com.example.categoryMicroService.model.CategoryResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper mapper;

    public CategoryResponse getCategoryById(int categoryId) throws Exception {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);

        if (categoryOptional.isPresent()) {
            return mapper.map(categoryOptional.get(), CategoryResponse.class);
        } else {
            // Handle the case where the category is not found
            // You can throw an exception or return null based on your requirements
            throw new Exception("Category not found with ID: " + categoryId);
        }
    }

    public boolean categoryExists(int categoryId) {
        return categoryRepository.existsById(categoryId);
    }

}
