package shoppingList.product.services;

import org.springframework.stereotype.Service;
import shoppingList.product.domain.ProductEntity;
import shoppingList.product.dto.ProductDto;
import shoppingList.product.mappers.ProductMapper;
import shoppingList.product.repository.ProductRepository;
import shoppingList.product.services.validations.ProductValidationService;
import shoppingList.product.services.validations.exception.ProductNotFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements TemplateService<ProductDto> {

    private final ProductRepository productRepository;
    private final ProductValidationService validationService;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductValidationService validationService, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.validationService = validationService;
        this.productMapper = productMapper;
    }

    @Override
    public ProductDto addProductService(ProductDto newProductDto) {
        validationService.validate(newProductDto);
        ProductEntity newProductEntity = productMapper.productToEntity(newProductDto);
        ProductEntity savedProductEntity = productRepository.addProduct(newProductEntity);
        return productMapper.productToDto(savedProductEntity);
    }

    @Override
    public boolean removeProductByIDService(Long id) throws ProductNotFoundException {
        if (productRepository.existsById(id)) {
            productRepository.removeProductByID(id);
            return true;
        } else {
            throw new ProductNotFoundException(id);
        }
    }

    @Override
    public List<ProductDto> listOfAllProductsService() {
        if (productRepository.listOfAllProducts().isEmpty()) {
            System.out.println("DataBase is empty");
            return Collections.emptyList();
        } else {
            System.out.println("Product DataBase:");
            List<ProductDto> allProductDtoList = new ArrayList<>();
            for (ProductEntity value : productRepository.listOfAllProducts()) {
                allProductDtoList.add(productMapper.productToDto(value));
            }
            return allProductDtoList;
        }
    }

    @Override
    public ProductDto findProductByID(Long id) throws ProductNotFoundException {
        return productMapper.productToDto((Optional.ofNullable(productRepository.findProductByID(id)
                .orElseThrow(() -> new ProductNotFoundException(id)))).get());
    }

    @Override
    public ProductDto updateProductService(Long id, ProductDto updatedProductDto) throws ProductNotFoundException {
        if (productRepository.existsById(id)) {
            validationService.validate(updatedProductDto);
            ProductEntity updatedProductEntity = productMapper.productToEntity(updatedProductDto);
            return productMapper.productToDto((productRepository.updateProduct(id, updatedProductEntity)));
        } else {
            throw new ProductNotFoundException(id);
        }
    }
}
