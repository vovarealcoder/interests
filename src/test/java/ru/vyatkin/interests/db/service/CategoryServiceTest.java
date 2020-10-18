package ru.vyatkin.interests.db.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.vyatkin.interests.DbConfig;
import ru.vyatkin.interests.InterestsApplication;
import ru.vyatkin.interests.db.entity.Category;
import ru.vyatkin.interests.db.entity.Picture;
import ru.vyatkin.interests.db.entity.PictureObject;
import ru.vyatkin.interests.db.entity.PictureSize;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

@SpringBootTest(classes = {InterestsApplication.class, DbConfig.class})
class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;


    @BeforeEach
    public void clear() {
        categoryService.deleteAll();
    }

    @Test
    void contextLoads() {
    }

    @Test
    public void findAllTest() {
        Category category = new Category();
        category.setName("test_name");
        category.setDescription("desc");
        categoryService.insertCategory(category);
        Collection<Category> allCategories = categoryService.getAllCategories();
        Assertions.assertFalse(allCategories.isEmpty());
    }

    @Test
    public void insertTest() {
        Category category = new Category();
        category.setName("test_name");
        category.setDescription("desc");
        Category category0 = categoryService.insertCategory(category);
        Optional<Category> optionalCat = categoryService.findCategory(category0.getId());
        Category catSelect = optionalCat.get();
        Assertions.assertEquals("test_name", catSelect.getName());
        Assertions.assertEquals("desc", catSelect.getDescription());
    }

    @Test
    public void deleteTest() {
        Category category = new Category();
        category.setName("test_name");
        category.setDescription("desc");
        Category insertCategory = categoryService.insertCategory(category);
        Assertions.assertTrue(categoryService.exists(insertCategory.getId()));
        categoryService.deleteCategory(insertCategory.getId());
        Assertions.assertFalse(categoryService.exists(insertCategory.getId()));
    }

    @Test
    public void updateTest() {
        Category category = new Category();
        category.setName("test_name");
        category.setDescription("desc");
        Category insertCategory = categoryService.insertCategory(category);
        Optional<Category> selected = categoryService.findCategory(insertCategory.getId());
        Category selected0 = selected.get();
        Assertions.assertEquals("test_name", selected0.getName());
        selected0.setName("new");
        categoryService.updateCategory(selected0);
        Optional<Category> newCat = categoryService.findCategory(selected0.getId());
        Category newCat0 = newCat.get();
        Assertions.assertEquals("new", newCat0.getName());
    }

    @Test
    public void parentTest() {
        Category parentCategory = new Category();
        parentCategory.setName("test_name_p");
        parentCategory.setDescription("desc_p");
        Category parentCategoryIns = categoryService.insertCategory(parentCategory);

        Category category = new Category();
        category.setName("test_name");
        category.setDescription("desc");
        category.setParent(parentCategoryIns);
        Category insertCategory = categoryService.insertCategory(category);
        Optional<Category> optionalCategory = categoryService.findCategory(insertCategory.getId());
        Category category0 = optionalCategory.get();
        Assertions.assertEquals("test_name_p", category0.getParent().getName());
        Assertions.assertEquals("test_name", category0.getName());
    }

    @Test
    public void findByParentTest() {
        Category parentCategory = new Category();
        parentCategory.setName("test_name_p");
        parentCategory.setDescription("desc_p");
        Category parentCategoryIns = categoryService.insertCategory(parentCategory);

        Category category = new Category();
        category.setName("test_name");
        category.setDescription("desc");
        category.setParent(parentCategoryIns);
        categoryService.insertCategory(category);
        Collection<Category> optionalCategory = categoryService.findCategoryByParent(parentCategory.getId());
        Category next = optionalCategory.iterator().next();
        Assertions.assertEquals("test_name_p", next.getParent().getName());
        Assertions.assertEquals("test_name", next.getName());
    }

    @Test
    public void pictureTest() {
        Category category = new Category();
        category.setName("test_name");
        category.setDescription("desc");
        PictureObject pictureObject = new PictureObject();
        Picture picture = new Picture(null, "url1", PictureSize.P, 200, 300, pictureObject);
        Picture picture1 = new Picture(null, "url2", PictureSize.S, 300, 300, pictureObject);
        pictureObject.getPictures().addAll(Arrays.asList(picture, picture1));
        category.setPictureObject(pictureObject);
        Category insertCategory = categoryService.insertCategory(category);
        Optional<Category> categorySelected = categoryService.findCategory(insertCategory.getId());
        Category category0 = categorySelected.get();
        Assertions.assertNotNull(category0.getPictureObject());
        Assertions.assertEquals("url1", category0.getPictureObject().getPictures().get(0).getFilename());
        Assertions.assertEquals("url2", category0.getPictureObject().getPictures().get(1).getFilename());
    }

}
