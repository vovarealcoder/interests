package ru.vyatkin.interests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import ru.vyatkin.interests.db.entity.Category;
import ru.vyatkin.interests.db.service.CategoryService;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {InterestsApplication.class, DbConfig.class})
public class CategoryControllerTest {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGetAll() {
        categoryService.insertCategory(new Category(null, "test1", "test2", null, null));
        ResponseEntity<List<Category>> responseEntity = restTemplate.exchange("/category/all", HttpMethod.GET,
                null, new ParameterizedTypeReference<List<Category>>() {
                });
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        Assertions.assertFalse(responseEntity.getBody().isEmpty());
    }

    @Test
    public void testGetId() {
        Category category = categoryService.insertCategory(new Category(null, "test2", "tes32", null, null));
        ResponseEntity<Category> responseEntity = restTemplate.exchange("/category/{id}", HttpMethod.GET,
                null, Category.class, category.getId());
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals("test2", responseEntity.getBody().getName());
    }

    @Test
    public void testGetParentId() {
        Category category = categoryService.insertCategory(new Category(null, "test3", "tes332", null, null));
        Category categoryChild = categoryService.insertCategory(new Category(null, "tes33", "tes33", category, null));
        ResponseEntity<List<Category>> responseEntity = restTemplate.exchange("/category/parent/{id}",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Category>>() {
                }, category.getId());
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        Assertions.assertFalse(responseEntity.getBody().isEmpty());
    }
}
