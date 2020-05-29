package shoppingList.services.validations;

import shoppingList.domain.ProductEntity;
import shoppingList.dto.ProductDto;
import shoppingList.services.validations.exception.ProductValidationException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProductValidationService implements ValidationRule<ProductEntity> {

    private final Set<ValidationRule<ProductEntity>> validationRules = new HashSet<>();
    private final List<String> errorLogs = new ArrayList<>();

    public ProductValidationService() {
        validationRules.add(new ProductNameValidation());
        validationRules.add(new ProductPriceValidation());
        validationRules.add(new ProductCategoryValidation());
        validationRules.add(new ProductDiscountValidation());
        validationRules.add(new ProductDescriptionValidation());
    }

    @Override
    public void validate(ProductDto productDto) {
        errorLogs.clear();
        for (ValidationRule<ProductEntity> rule : validationRules) {
            try {
                rule.validate(productDto);
            } catch (ProductValidationException e) {
                errorLogs.add(e.getMessage());
            }
        }
        if (!errorLogs.isEmpty()) {
            throw new ProductValidationException(errorLogs.toString());
        }
    }
}
