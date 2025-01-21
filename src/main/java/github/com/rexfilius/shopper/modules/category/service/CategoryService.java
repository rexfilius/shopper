package github.com.rexfilius.shopper.modules.category.service;

import github.com.rexfilius.shopper.modules.category.model.CategoryDto;
import github.com.rexfilius.shopper.modules.category.model.CategoryResponse;
import github.com.rexfilius.shopper.modules.category.model.entity.Category;
import github.com.rexfilius.shopper.modules.category.repo.CategoryRepository;
import github.com.rexfilius.shopper.modules.exceptions.ApiException;
import github.com.rexfilius.shopper.modules.exceptions.ResourceNotFoundException;
import github.com.rexfilius.shopper.utils.ObjMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryResponse getAllCategories(
            Integer pageNumber,
            Integer pageSize,
            String sortBy,
            String sortOrder
    ) {
        Sort sort = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        //
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Category> categoryPage = categoryRepository.findAll(pageable);
        //
        List<Category> categories = categoryPage.getContent();
        if (categories.isEmpty()) {
            throw new ApiException("No categories found");
        }
        //
        List<CategoryDto> categoryDtoList = categories.stream()
                .map(ObjMapper::modelToDto)
                .collect(Collectors.toList());
        //
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setCategories(categoryDtoList);
        categoryResponse.setPageNumber(categoryPage.getNumber());
        categoryResponse.setPageSize(categoryPage.getSize());
        categoryResponse.setTotalPages(categoryPage.getTotalPages());
        categoryResponse.setTotalElements(categoryPage.getTotalElements());
        categoryResponse.setLastPage(categoryPage.isLast());
        //
        return categoryResponse;
    }

    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category categoryFromDB = categoryRepository
                .findByCategoryName(categoryDto.getCategoryName());
        if (categoryFromDB != null) {
            throw new ApiException("Category with the name " + categoryDto.getCategoryName() + " already exists");
        }
        //
        Category saveCategory = ObjMapper.dtoToModel(categoryDto);
        categoryRepository.save(saveCategory);
        return ObjMapper.modelToDto(saveCategory);
    }

    public CategoryDto deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("Category", "id", categoryId));
        categoryRepository.delete(category);
        return ObjMapper.modelToDto(category);
    }

    public CategoryDto updateCategory(
            CategoryDto categoryDto, Long categoryId
    ) {
        Category categoryFromDB = categoryRepository
                .findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));

        categoryFromDB.setCategoryName(categoryDto.getCategoryName());
        Category updatedCategory = categoryRepository.save(categoryFromDB);
        return ObjMapper.modelToDto(updatedCategory);
    }
}
