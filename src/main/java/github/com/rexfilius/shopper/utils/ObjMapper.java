package github.com.rexfilius.shopper.utils;

import github.com.rexfilius.shopper.modules.category.model.CategoryDto;
import github.com.rexfilius.shopper.modules.category.model.entity.Category;
import github.com.rexfilius.shopper.modules.product.model.ProductDto;
import github.com.rexfilius.shopper.modules.product.model.entity.Product;

public class ObjMapper {
    public static CategoryDto modelToDto(Category category) {
        CategoryDto dto = new CategoryDto();
        dto.setCategoryId(category.getCategoryId());
        dto.setCategoryName(category.getCategoryName());
        return dto;
    }

    public static Category dtoToModel(CategoryDto dto) {
        Category category = new Category();
        category.setCategoryId(dto.getCategoryId());
        category.setCategoryName(dto.getCategoryName());
        return category;
    }

    public static ProductDto modelToDto(Product product) {
        ProductDto dto = new ProductDto();
        dto.setProductId(product.getProductId());
        dto.setProductName(product.getProductName());
        dto.setDescription(product.getDescription());
        dto.setImage(product.getImage());
        dto.setQuantity(product.getQuantity());
        dto.setPrice(product.getPrice());
        dto.setDiscount(product.getDiscount());
        dto.setSpecialPrice(product.getSpecialPrice());
        return dto;
    }

    public static Product modelToDto(ProductDto dto) {
        Product product = new Product();
        product.setProductId(dto.getProductId());
        product.setProductName(dto.getProductName());
        product.setDescription(dto.getDescription());
        product.setImage(dto.getImage());
        product.setQuantity(dto.getQuantity());
        product.setPrice(dto.getPrice());
        product.setDiscount(dto.getDiscount());
        product.setSpecialPrice(dto.getSpecialPrice());
        return product;
    }
}
