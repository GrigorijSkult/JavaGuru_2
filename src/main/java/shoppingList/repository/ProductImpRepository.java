package shoppingList.repository;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import shoppingList.domain.ProductEntity;

import java.util.*;

@Repository
@Profile("inmemory")
public class ProductImpRepository implements ProductRepository {

    private long newProductId = 1;
    private final Map<Long, ProductEntity> productsDB = new HashMap<>();

    @Override
    public ProductEntity addProduct(ProductEntity newProductEntity) {
        ProductEntity productEntityCopy = new ProductEntity(newProductId, newProductEntity.getProductName(),
                newProductEntity.getProductRegularPrice(), newProductEntity.getProductCategory(), newProductEntity.getProductDiscount(),
                newProductEntity.getProductDescription());
        productsDB.put(newProductId, productEntityCopy);
        newProductId++;
        return productEntityCopy;
    }

    @Override
    public Optional<ProductEntity> removeProductByID(Long id) {
        return Optional.ofNullable(productsDB.remove(id));
    }

    @Override
    public List<ProductEntity> listOfAllProducts() {
        return new ArrayList<>(productsDB.values());
    }

    @Override
    public Optional<ProductEntity> findProductByID(Long id) {
        return Optional.ofNullable(productsDB.get(id));
    }

    @Override
    public Optional<ProductEntity> updateProduct(Long id, ProductEntity updatedProduct) {
        productsDB.replace(id, updatedProduct);
        return Optional.of(updatedProduct);
    }

    @Override
    public boolean existsByName(ProductEntity productEntity) {
        for (ProductEntity value : productsDB.values()) {
            if (value.getProductName().equals(productEntity.getProductName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean existsById(Long id) {
        return productsDB.containsKey(id);
    }
}
