package com.online_store.product_service.api.category.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.online_store.product_service.api.category.dto.CategoryRequest;
import com.online_store.product_service.api.category.dto.CategoryResponse;
import com.online_store.product_service.api.category.dto.CategoryTreeResponse;
import com.online_store.product_service.api.category.model.Category;
import com.online_store.product_service.api.category.repository.CategoryRepository;
import com.online_store.product_service.client.upload.UploadClient;
import com.online_store.product_service.client.upload.UploadResponse;
import com.online_store.product_service.common.response.ApiResponse;
import com.online_store.product_service.common.utils.UtilsService;

import jakarta.transaction.Transactional;

@Service
public class CategoryService {
    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    private final CategoryRepository repository;
    private final UploadClient uploadClient;
    private final UtilsService utilsService;

    public CategoryService(CategoryRepository repository,
            UploadClient uploadClient,
            UtilsService utilsService) {
        this.repository = repository;
        this.uploadClient = uploadClient;
        this.utilsService = utilsService;
    }

    @Transactional
    public String createCategory(CategoryRequest dto,
            MultipartFile imageFile,
            MultipartFile iconFile) {
        logger.info("Attempting to create a new category: {}", dto.name());

        utilsService.checkImageFileType(imageFile);
        utilsService.checkImageFileType(iconFile);

        Long imageId = uploadFile(imageFile);
        Long iconId = uploadFile(iconFile);

        Category category = createCategoryMapper(dto, imageId, iconId);

        configureCategoryHierarchy(category, dto.parent());

        repository.save(category);

        logger.info("Category '{}' created successfully with ID {}.", category.getName(), category.getId());
        return "Category created successfully.";
    }

    public List<CategoryResponse> listLeafCategories() {
        logger.info("Fetching all leaf categories.");
        return repository.findLeafCategories().stream()
                .map(this::mapCategoryToResponseDto)
                .collect(Collectors.toList());
    }

    public List<CategoryTreeResponse> getCategoryTree() {
        logger.info("Building and fetching the category tree.");
        List<Category> allCategories = repository.findAll();

        Map<Long, CategoryTreeResponse> categoryDTOMap = allCategories.stream()
                .map(this::mapCategoryTreeToResponseDto)
                .collect(Collectors.toMap(CategoryTreeResponse::getId, dto -> dto));

        List<CategoryTreeResponse> rootCategories = new ArrayList<>();

        allCategories.forEach(category -> {
            CategoryTreeResponse currentCategoryDto = categoryDTOMap.get(category.getId());

            if (category.getParent() != null) {
                CategoryTreeResponse parentDTO = categoryDTOMap.get(category.getParent().getId());
                if (parentDTO != null) {
                    if (parentDTO.getChildren() == null) {
                        parentDTO.setChildren(new ArrayList<>());
                    }
                    parentDTO.getChildren().add(currentCategoryDto);
                }
            } else {
                rootCategories.add(currentCategoryDto);
            }
        });
        logger.info("Category tree with {} root categories built successfully.", rootCategories.size());
        return rootCategories;
    }

    public Category findCategoryById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Parent category not found with ID: {}", id);
                    return new RuntimeException("Parent category not found with ID: " + id);
                });
    }

    private void configureCategoryHierarchy(Category newCategory, Long parentId) {
        if (parentId == null) {
            handleRootCategory(newCategory);
        } else {
            handleChildCategory(newCategory, parentId);
        }
    }

    private void handleRootCategory(Category newCategory) {
        Long maxRight = repository.findMaxRightValue();
        int newLeft = (maxRight == null) ? 1 : maxRight.intValue() + 1;
        newCategory.setLeftValue(newLeft);
        newCategory.setRightValue(newLeft + 1);
        logger.info("Setting '{}' as a new root category.", newCategory.getName());
    }

    private void handleChildCategory(Category newCategory, Long parentId) {
        Category parentCategory = findCategoryById(parentId);

        repository.incrementRightValuesGreaterThanOrEqualTo(parentCategory.getRightValue(), 2);
        repository.incrementLeftValuesGreaterThan(parentCategory.getRightValue(), 2);

        newCategory.setLeftValue(parentCategory.getRightValue());
        newCategory.setRightValue(parentCategory.getRightValue() + 1);
        newCategory.setParent(parentCategory);
        logger.info("Setting '{}' as a child of category '{}'.", newCategory.getName(), parentCategory.getName());
    }

    private Long uploadFile(MultipartFile file) {
        ApiResponse<UploadResponse> upload = uploadClient.uploadFile(file);
        return upload.data().upload();
    }

    private Category createCategoryMapper(CategoryRequest dto,
            Long imageId,
            Long iconId) {
        return new Category(
                dto.name(),
                dto.description(),
                imageId,
                iconId);
    }

    public CategoryResponse mapCategoryToResponseDto(Category dto) {
        Long parentId = (dto.getParent() != null) ? dto.getParent().getId() : null;

        return new CategoryResponse(
                dto.getId(),
                dto.getName(),
                dto.getDescription(),
                dto.getImage(),
                dto.getIcon(),
                dto.getLeftValue(),
                dto.getRightValue(),
                parentId);
    }

    public CategoryTreeResponse mapCategoryTreeToResponseDto(Category dto) {
        return new CategoryTreeResponse(
                dto.getId(),
                dto.getName(),
                dto.getDescription(),
                dto.getImage(),
                dto.getIcon(),
                null);

    }
}
