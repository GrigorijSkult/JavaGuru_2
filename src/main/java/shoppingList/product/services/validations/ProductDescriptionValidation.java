package shoppingList.product.services.validations;

import org.springframework.stereotype.Component;
import shoppingList.product.dto.ProductDto;
import shoppingList.product.services.validations.exception.ProductValidationException;

@Component
public class ProductDescriptionValidation implements ValidationRule<ProductDto> {

    @Override
    public void validate(ProductDto productDto) {
        if (!(productDto.getDescription() == null || productDto.getDescription().isEmpty())
                && productDto.getDescription().length() > 100) {
            throw new ProductValidationException("Product`s description should be shorter then 100 symbols");
        }
    }

}