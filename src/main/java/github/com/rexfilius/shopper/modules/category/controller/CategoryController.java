package github.com.rexfilius.shopper.modules.category.controller;

import github.com.rexfilius.shopper.modules.category.model.CategoryDto;
import github.com.rexfilius.shopper.modules.category.model.CategoryResponse;
import github.com.rexfilius.shopper.modules.category.service.CategoryService;
import github.com.rexfilius.shopper.utils.AppConstant;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponse> getAllCategories(
            @RequestParam(defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(defaultValue = AppConstant.SORT_BY, required = false) String sortBy,
            @RequestParam(defaultValue = AppConstant.SORT_ORDER, required = false) String sortOrder
    ) {
        CategoryResponse categoryResponse = categoryService
                .getAllCategories(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
    }

    @PostMapping("/public/categories")
    public ResponseEntity<CategoryDto> createCategory(
            @Valid @RequestBody CategoryDto categoryDto
    ) {
        CategoryDto dto = categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDto> deleteCategory(
            @PathVariable Long categoryId
    ) {
        CategoryDto categoryDto = categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }


    @PutMapping("/public/categories/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(
            @Valid @RequestBody CategoryDto categoryDto,
            @PathVariable Long categoryId
    ) {
        CategoryDto dto = categoryService.updateCategory(categoryDto, categoryId);
        return new ResponseEntity<>(dto, HttpStatus.OK);

    }
}
