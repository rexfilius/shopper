package github.com.rexfilius.shopper.modules.product.service;

import github.com.rexfilius.shopper.modules.category.model.entity.Category;
import github.com.rexfilius.shopper.modules.category.repo.CategoryRepository;
import github.com.rexfilius.shopper.modules.exceptions.ApiException;
import github.com.rexfilius.shopper.modules.exceptions.ResourceNotFoundException;
import github.com.rexfilius.shopper.modules.files.FileService;
import github.com.rexfilius.shopper.modules.product.model.ProductDto;
import github.com.rexfilius.shopper.modules.product.model.ProductResponse;
import github.com.rexfilius.shopper.modules.product.model.entity.Product;
import github.com.rexfilius.shopper.modules.product.repo.ProductRepository;
import github.com.rexfilius.shopper.utils.ObjMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    @Value("${project.image}")
    private String path;

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final FileService fileService;

    public ProductService(
            CategoryRepository categoryRepository,
            ProductRepository productRepository,
            FileService fileService
    ) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.fileService = fileService;
    }

    public ProductDto addProduct(Long categoryId, ProductDto productDto) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        Product product = ObjMapper.modelToDto(productDto);
        product.setCategory(category);
        product.setImage("default.png");

        double specialP = product.getPrice() - ((product.getDiscount() * 0.01) * product.getPrice());
        product.setPrice(specialP);

        Product newProduct = productRepository.save(product);
        return ObjMapper.modelToDto(newProduct);
    }

    public ProductResponse getAllProducts(
            Integer pageNumber,
            Integer pageSize,
            String sortBy,
            String sortOrder
    ) {
        Sort sort = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> productPage = productRepository.findAll(pageable);

        List<Product> products = productPage.getContent();
        if (products.isEmpty()) {
            throw new ApiException("No products found");
        }

        List<ProductDto> productDtos = products.stream()
                .map(ObjMapper::modelToDto)
                .toList();
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDtos);
        productResponse.setPageNumber(productPage.getNumber());
        productResponse.setPageSize(productPage.getSize());
        productResponse.setTotalPages(productPage.getTotalPages());
        productResponse.setTotalElements(productPage.getTotalElements());
        productResponse.setLastPage(productPage.isLast());
        //
        return productResponse;
    }

    public ProductResponse searchByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        List<Product> products = productRepository.findByCategoryOrderByPriceAsc(category);

        List<ProductDto> productDtos = products.stream()
                .map(ObjMapper::modelToDto)
                .toList();
        return new ProductResponse(productDtos);
    }

    public ProductResponse searchProductByKeyword(String keyword) {
        List<Product> products = productRepository.findByProductNameLikeIgnoreCase(keyword);

        List<ProductDto> productDtos = products.stream()
                .map(ObjMapper::modelToDto)
                .toList();
        return new ProductResponse(productDtos);
    }

    public ProductDto updateProduct(Long id, ProductDto productDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
        product.setPrice(productDto.getPrice());
        product.setDiscount(productDto.getDiscount());
        product.setProductName(productDto.getProductName());
        product.setDescription(productDto.getDescription());
        product.setQuantity(productDto.getQuantity());
        product.setSpecialPrice(productDto.getSpecialPrice());

        Product updatedProduct = productRepository.save(product);
        return ObjMapper.modelToDto(updatedProduct);
    }

    public ProductDto deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));
        productRepository.delete(product);
        return ObjMapper.modelToDto(product);
    }

    public ProductDto updateProductImage(Long productId, MultipartFile image) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", productId));

        String fileName;
        try {
            fileName = fileService.uploadImage(path, image);
        } catch (IOException e) {
            throw new ApiException("Failed to upload image");
        }

        product.setImage(fileName);
        Product updatedProduct = productRepository.save(product);
        return ObjMapper.modelToDto(updatedProduct);
    }


}
