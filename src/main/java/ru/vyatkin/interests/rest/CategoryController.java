package ru.vyatkin.interests.rest;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.vyatkin.interests.db.entity.Category;
import ru.vyatkin.interests.db.service.CategoryService;
import ru.vyatkin.interests.rest.model.CategoryDTO;
import ru.vyatkin.interests.rest.model.common.RequestStatus;
import ru.vyatkin.interests.rest.model.common.ResponseDTO;

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
    public ResponseDTO<List<CategoryDTO>> getAll() {
        List<CategoryDTO> categoryDTOS = categoryService
                .getAllCategories()
                .stream()
                .map(CategoryDTO::toDTO)
                .collect(Collectors.toList());
        return new ResponseDTO<>(RequestStatus.OK, "Success", categoryDTOS);
    }

    @GetMapping("/{id}")
    public ResponseDTO<CategoryDTO> get(@PathVariable("id") Long id) {
        CategoryDTO categoryDTO = CategoryDTO.toDTO(categoryService.findCategory(id)
                .orElseThrow(ResourceNotFoundException::new));
        return new ResponseDTO<>(RequestStatus.OK, "Success", categoryDTO);
    }

    @GetMapping("/parent/{id}")
    public ResponseDTO<List<CategoryDTO>> getParent(@PathVariable("id") Long id) {
        List<CategoryDTO> categoryDTOS = categoryService.findCategoryByParent(id)
                .stream().map(CategoryDTO::toDTO)
                .collect(Collectors.toList());
        return new ResponseDTO<>(RequestStatus.OK, "Success", categoryDTOS);
    }

}
