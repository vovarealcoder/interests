package ru.vyatkin.interests.rest;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.vyatkin.interests.db.entity.Category;
import ru.vyatkin.interests.db.service.CategoryService;
import ru.vyatkin.interests.rest.model.CategoryDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/all")
    public List<CategoryDTO> getAll() {
        return categoryService.getAllCategories().stream().map(CategoryDTO::toDTO).collect(Collectors.toList());
    }
    @GetMapping("/{id}")
    public CategoryDTO get(@PathVariable("id") Long id){
        return CategoryDTO.toDTO(categoryService.findCategory(id).orElseThrow(ResourceNotFoundException::new));
    }
    @GetMapping("/parent/{id}")
    public List<CategoryDTO> getParent(@PathVariable("id") Long id){
        return categoryService.findCategoryByParent(id).stream().map(CategoryDTO::toDTO).collect(Collectors.toList());
    }

}
