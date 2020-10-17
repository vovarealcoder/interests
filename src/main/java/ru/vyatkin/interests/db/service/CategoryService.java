package ru.vyatkin.interests.db.service;

import org.springframework.stereotype.Service;
import ru.vyatkin.interests.db.entity.Category;
import ru.vyatkin.interests.db.repository.CategoryRepository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Collection<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category insertCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category updateCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Optional<Category> findCategory(Long categoryId) {
        return categoryRepository.findById(categoryId);
    }

    public Collection<Category> findCategoryByParent(Long parentId) {
        Optional<Category> parent = categoryRepository.findById(parentId);
        return categoryRepository.findByParent(parent.orElseThrow(NoSuchElementException::new));
    }

    public void deleteCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    public boolean exists(Long categoryId) {
        return categoryRepository.existsById(categoryId);
    }

    public void deleteAll() {
        categoryRepository.deleteAll();
    }
}
