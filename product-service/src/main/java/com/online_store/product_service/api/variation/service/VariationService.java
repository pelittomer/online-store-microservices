package com.online_store.product_service.api.variation.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.online_store.product_service.api.category.model.Category;
import com.online_store.product_service.api.category.service.CategoryService;
import com.online_store.product_service.api.variation.dto.VariationOptionResponse;
import com.online_store.product_service.api.variation.dto.VariationRequest;
import com.online_store.product_service.api.variation.dto.VariationResponse;
import com.online_store.product_service.api.variation.model.Variation;
import com.online_store.product_service.api.variation.model.VariationOption;
import com.online_store.product_service.api.variation.repository.VariationRepository;

@Service
public class VariationService {
    private static final Logger logger = LoggerFactory.getLogger(VariationService.class);
    private final VariationRepository repository;
    private final CategoryService categoryService;

    public VariationService(VariationRepository repository,
            CategoryService categoryService) {
        this.repository = repository;
        this.categoryService = categoryService;
    }

    public String addVariation(VariationRequest dto) {
           logger.info("Adding new variation with name: '{}' for category ID: {}", dto.name(), dto.category());
        Category category = categoryService.findCategoryById(dto.category());
        Variation variation = createVariationMapper(dto, category);
        repository.save(variation);

         logger.info("Variation created successfully with ID: {}", variation.getId());
        return "Variation created successfully.";
    }

    public List<VariationResponse> listVariations(Long categoryId) {
        logger.info("Listing variations. Category ID is present: {}", categoryId);

        Category category = categoryService.findCategoryById(categoryId);

        return repository.findByCategory(category).stream()
                .map(this::mapVariationToResponseDto)
                .collect(Collectors.toList());
    }

    public Variation createVariationMapper(VariationRequest dto, Category category) {
        Variation variation = new Variation(dto.name(), category);
        dto.options().forEach(optionName -> {
            VariationOption option = new VariationOption(optionName);
            variation.addOption(option);
        });

        return variation;
    }

    private VariationResponse mapVariationToResponseDto(Variation dto) {
        List<VariationOptionResponse> variationOptions = dto.getOptions().stream()
                .map(this::mapVariationOptionToResponseDto).toList();
        return new VariationResponse(
                dto.getId(),
                dto.getName(),
                variationOptions);
    }

    private VariationOptionResponse mapVariationOptionToResponseDto(VariationOption dto) {
        return new VariationOptionResponse(
                dto.getId(),
                dto.getName());
    }

}
