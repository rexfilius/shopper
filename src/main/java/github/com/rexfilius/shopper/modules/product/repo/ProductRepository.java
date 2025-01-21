package github.com.rexfilius.shopper.modules.product.repo;

import github.com.rexfilius.shopper.modules.category.model.entity.Category;
import github.com.rexfilius.shopper.modules.product.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryOrderByPriceAsc(Category category);

    List<Product> findByProductNameLikeIgnoreCase(String productName);
}
