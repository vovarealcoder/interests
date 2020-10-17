package ru.vyatkin.interests.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vyatkin.interests.db.entity.Category;

import java.util.Collection;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Collection<Category> findByParent(Category parent);
}
