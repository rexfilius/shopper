package github.com.rexfilius.shopper.modules.category.repo;

import github.com.rexfilius.shopper.modules.category.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByCategoryName(String categoryName);
}
