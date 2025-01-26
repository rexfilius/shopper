package github.com.rexfilius.shopper.modules.product.controller;

import github.com.rexfilius.shopper.modules.product.model.ProductDto;
import github.com.rexfilius.shopper.modules.product.model.ProductResponse;
import github.com.rexfilius.shopper.modules.product.service.ProductService;
import github.com.rexfilius.shopper.utils.AppConstant;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDto> addProduct(
            @RequestBody ProductDto productDto,
            @PathVariable Long categoryId
    ) {
        return ResponseEntity.ok(productService.addProduct(categoryId, productDto));
    }

    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getAllProducts(
            @RequestParam(defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(defaultValue = AppConstant.SORT_BY, required = false) String sortBy,
            @RequestParam(defaultValue = AppConstant.SORT_ORDER, required = false) String sortOrder
    ) {
        return ResponseEntity.ok(productService.getAllProducts(
                pageNumber, pageSize, sortBy, sortOrder));
    }

    @GetMapping("/public/categories/{categoryId}/products")
    public ResponseEntity<ProductResponse> getProductsByCategory(
            @PathVariable Long categoryId
    ) {
        return ResponseEntity.ok(productService.searchByCategory(categoryId));
    }

    @GetMapping("/public/products/keyword/{keyword}")
    public ResponseEntity<ProductResponse> getProductsByKeyword(
            @PathVariable String keyword
    ) {
        ProductResponse productResponse = productService.searchProductByKeyword(keyword);
        return ResponseEntity.ok(productResponse);
    }

    @PutMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDto> updateProduct(
            @RequestBody ProductDto productDto,
            @PathVariable Long productId
    ) {
        return ResponseEntity.ok(productService.updateProduct(productId, productDto));
    }

    @DeleteMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDto> deleteProduct(@PathVariable Long productId) {
        ProductDto productDto = productService.deleteProduct(productId);
        return ResponseEntity.ok(productDto);
    }

    @PutMapping("/products/{productId}/image")
    public ResponseEntity<ProductDto> updateProductImage(
            @PathVariable Long productId,
            @RequestParam MultipartFile image) {
        return ResponseEntity.ok(productService.updateProductImage(productId, image));
    }
}
